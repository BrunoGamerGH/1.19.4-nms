package net.minecraft.network;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.authlib.properties.Property;
import com.mojang.logging.LogUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.TimeoutException;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.RejectedExecutionException;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import net.minecraft.SystemUtils;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.protocol.BundlerInfo;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutKickDisconnect;
import net.minecraft.network.protocol.login.PacketLoginOutDisconnect;
import net.minecraft.server.CancelledPacketHandleException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.LazyInitVar;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class NetworkManager extends SimpleChannelInboundHandler<Packet<?>> {
   private static final float i = 0.75F;
   private static final Logger j = LogUtils.getLogger();
   public static final Marker a = MarkerFactory.getMarker("NETWORK");
   public static final Marker b = SystemUtils.a(MarkerFactory.getMarker("NETWORK_PACKETS"), marker -> marker.add(a));
   public static final Marker c = SystemUtils.a(MarkerFactory.getMarker("PACKET_RECEIVED"), marker -> marker.add(b));
   public static final Marker d = SystemUtils.a(MarkerFactory.getMarker("PACKET_SENT"), marker -> marker.add(b));
   public static final AttributeKey<EnumProtocol> e = AttributeKey.valueOf("protocol");
   public static final LazyInitVar<NioEventLoopGroup> f = new LazyInitVar(
      () -> new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build())
   );
   public static final LazyInitVar<EpollEventLoopGroup> g = new LazyInitVar(
      () -> new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Client IO #%d").setDaemon(true).build())
   );
   public static final LazyInitVar<DefaultEventLoopGroup> h = new LazyInitVar(
      () -> new DefaultEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Client IO #%d").setDaemon(true).build())
   );
   private final EnumProtocolDirection k;
   private final Queue<NetworkManager.QueuedPacket> l = Queues.newConcurrentLinkedQueue();
   public Channel m;
   public SocketAddress n;
   public UUID spoofedUUID;
   public Property[] spoofedProfile;
   public boolean preparing = true;
   private PacketListener o;
   private IChatBaseComponent p;
   private boolean q;
   private boolean r;
   private int s;
   private int t;
   private float u;
   private float v;
   private int w;
   private boolean x;
   public String hostname = "";

   public NetworkManager(EnumProtocolDirection enumprotocoldirection) {
      this.k = enumprotocoldirection;
   }

   public void channelActive(ChannelHandlerContext channelhandlercontext) throws Exception {
      super.channelActive(channelhandlercontext);
      this.m = channelhandlercontext.channel();
      this.n = this.m.remoteAddress();
      this.preparing = false;

      try {
         this.a(EnumProtocol.a);
      } catch (Throwable var3) {
         j.error(LogUtils.FATAL_MARKER, "Failed to change protocol to handshake", var3);
      }
   }

   public void a(EnumProtocol enumprotocol) {
      this.m.attr(e).set(enumprotocol);
      this.m.attr(BundlerInfo.a).set(enumprotocol);
      this.m.config().setAutoRead(true);
      j.debug("Enabled auto read");
   }

   public void channelInactive(ChannelHandlerContext channelhandlercontext) {
      this.a(IChatBaseComponent.c("disconnect.endOfStream"));
   }

   public void exceptionCaught(ChannelHandlerContext channelhandlercontext, Throwable throwable) {
      if (throwable instanceof SkipEncodeException) {
         j.debug("Skipping packet due to errors", throwable.getCause());
      } else {
         boolean flag = !this.x;
         this.x = true;
         if (this.m.isOpen()) {
            if (throwable instanceof TimeoutException) {
               j.debug("Timeout", throwable);
               this.a(IChatBaseComponent.c("disconnect.timeout"));
            } else {
               IChatMutableComponent ichatmutablecomponent = IChatBaseComponent.a("disconnect.genericReason", "Internal Exception: " + throwable);
               if (flag) {
                  j.debug("Failed to sent packet", throwable);
                  EnumProtocol enumprotocol = this.p();
                  Packet<?> packet = (Packet<?>)(enumprotocol == EnumProtocol.d
                     ? new PacketLoginOutDisconnect(ichatmutablecomponent)
                     : new PacketPlayOutKickDisconnect(ichatmutablecomponent));
                  this.a(packet, PacketSendListener.a(() -> this.a(ichatmutablecomponent)));
                  this.l();
               } else {
                  j.debug("Double fault", throwable);
                  this.a(ichatmutablecomponent);
               }
            }
         }
      }

      if (MinecraftServer.getServer().isDebugging()) {
         throwable.printStackTrace();
      }
   }

   protected void a(ChannelHandlerContext channelhandlercontext, Packet<?> packet) {
      if (this.m.isOpen()) {
         try {
            a(packet, this.o);
         } catch (CancelledPacketHandleException var4) {
         } catch (RejectedExecutionException var5) {
            this.a(IChatBaseComponent.c("multiplayer.disconnect.server_shutdown"));
         } catch (ClassCastException var6) {
            j.error("Received {} that couldn't be processed", packet.getClass(), var6);
            this.a(IChatBaseComponent.c("multiplayer.disconnect.invalid_packet"));
         }

         ++this.s;
      }
   }

   private static <T extends PacketListener> void a(Packet<T> packet, PacketListener packetlistener) {
      packet.a((T)packetlistener);
   }

   public void a(PacketListener packetlistener) {
      Validate.notNull(packetlistener, "packetListener", new Object[0]);
      this.o = packetlistener;
   }

   public void a(Packet<?> packet) {
      this.a(packet, null);
   }

   public void a(Packet<?> packet, @Nullable PacketSendListener packetsendlistener) {
      if (this.h()) {
         this.q();
         this.b(packet, packetsendlistener);
      } else {
         this.l.add(new NetworkManager.QueuedPacket(packet, packetsendlistener));
      }
   }

   private void b(Packet<?> packet, @Nullable PacketSendListener packetsendlistener) {
      EnumProtocol enumprotocol = EnumProtocol.a(packet);
      EnumProtocol enumprotocol1 = this.p();
      ++this.t;
      if (enumprotocol1 != enumprotocol) {
         if (enumprotocol == null) {
            throw new IllegalStateException("Encountered packet without set protocol: " + packet);
         }

         j.debug("Disabled auto read");
         this.m.config().setAutoRead(false);
      }

      if (this.m.eventLoop().inEventLoop()) {
         this.a(packet, packetsendlistener, enumprotocol, enumprotocol1);
      } else {
         this.m.eventLoop().execute(() -> this.a(packet, packetsendlistener, enumprotocol, enumprotocol1));
      }
   }

   private void a(Packet<?> packet, @Nullable PacketSendListener packetsendlistener, EnumProtocol enumprotocol, EnumProtocol enumprotocol1) {
      if (enumprotocol != enumprotocol1) {
         this.a(enumprotocol);
      }

      ChannelFuture channelfuture = this.m.writeAndFlush(packet);
      if (packetsendlistener != null) {
         channelfuture.addListener(future -> {
            if (future.isSuccess()) {
               packetsendlistener.a();
            } else {
               Packet<?> packet1 = packetsendlistener.b();
               if (packet1 != null) {
                  ChannelFuture channelfuture1 = this.m.writeAndFlush(packet1);
                  channelfuture1.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
               }
            }
         });
      }

      channelfuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
   }

   private EnumProtocol p() {
      return (EnumProtocol)this.m.attr(e).get();
   }

   private void q() {
      if (this.m != null && this.m.isOpen()) {
         Queue var2 = this.l;
         NetworkManager.QueuedPacket networkmanager_queuedpacket;
         synchronized(this.l) {
            while((networkmanager_queuedpacket = this.l.poll()) != null) {
               this.b(networkmanager_queuedpacket.a, networkmanager_queuedpacket.b);
            }
         }
      }
   }

   public void a() {
      this.q();
      PacketListener packetlistener = this.o;
      if (packetlistener instanceof TickablePacketListener tickablepacketlistener) {
         tickablepacketlistener.c();
      }

      if (!this.h() && !this.r) {
         this.m();
      }

      if (this.m != null) {
         this.m.flush();
      }

      if (this.w++ % 20 == 0) {
         this.b();
      }
   }

   protected void b() {
      this.v = MathHelper.i(0.75F, (float)this.t, this.v);
      this.u = MathHelper.i(0.75F, (float)this.s, this.u);
      this.t = 0;
      this.s = 0;
   }

   public SocketAddress c() {
      return this.n;
   }

   public void a(IChatBaseComponent ichatbasecomponent) {
      this.preparing = false;
      if (this.m.isOpen()) {
         this.m.close();
         this.p = ichatbasecomponent;
      }
   }

   public boolean d() {
      return this.m instanceof LocalChannel || this.m instanceof LocalServerChannel;
   }

   public EnumProtocolDirection e() {
      return this.k;
   }

   public EnumProtocolDirection f() {
      return this.k.a();
   }

   public static NetworkManager a(InetSocketAddress inetsocketaddress, boolean flag) {
      final NetworkManager networkmanager = new NetworkManager(EnumProtocolDirection.b);
      Class oclass;
      LazyInitVar lazyinitvar;
      if (Epoll.isAvailable() && flag) {
         oclass = EpollSocketChannel.class;
         lazyinitvar = g;
      } else {
         oclass = NioSocketChannel.class;
         lazyinitvar = f;
      }

      ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)lazyinitvar.a())).handler(new ChannelInitializer<Channel>() {
         protected void initChannel(Channel channel) {
            try {
               channel.config().setOption(ChannelOption.TCP_NODELAY, true);
            } catch (ChannelException var3) {
            }

            ChannelPipeline channelpipeline = channel.pipeline().addLast("timeout", new ReadTimeoutHandler(30));
            NetworkManager.a(channelpipeline, EnumProtocolDirection.b);
            channelpipeline.addLast("packet_handler", networkmanager);
         }
      })).channel(oclass)).connect(inetsocketaddress.getAddress(), inetsocketaddress.getPort()).syncUninterruptibly();
      return networkmanager;
   }

   public static void a(ChannelPipeline channelpipeline, EnumProtocolDirection enumprotocoldirection) {
      EnumProtocolDirection enumprotocoldirection1 = enumprotocoldirection.a();
      channelpipeline.addLast("splitter", new PacketSplitter())
         .addLast("decoder", new PacketDecoder(enumprotocoldirection))
         .addLast("prepender", new PacketPrepender())
         .addLast("encoder", new PacketEncoder(enumprotocoldirection1))
         .addLast("unbundler", new PacketBundleUnpacker(enumprotocoldirection1))
         .addLast("bundler", new PacketBundlePacker(enumprotocoldirection));
   }

   public static NetworkManager a(SocketAddress socketaddress) {
      final NetworkManager networkmanager = new NetworkManager(EnumProtocolDirection.b);
      ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)h.a())).handler(new ChannelInitializer<Channel>() {
         protected void initChannel(Channel channel) {
            ChannelPipeline channelpipeline = channel.pipeline();
            channelpipeline.addLast("packet_handler", networkmanager);
         }
      })).channel(LocalChannel.class)).connect(socketaddress).syncUninterruptibly();
      return networkmanager;
   }

   public void a(Cipher cipher, Cipher cipher1) {
      this.q = true;
      this.m.pipeline().addBefore("splitter", "decrypt", new PacketDecrypter(cipher));
      this.m.pipeline().addBefore("prepender", "encrypt", new PacketEncrypter(cipher1));
   }

   public boolean g() {
      return this.q;
   }

   public boolean h() {
      return this.m != null && this.m.isOpen();
   }

   public boolean i() {
      return this.m == null;
   }

   public PacketListener j() {
      return this.o;
   }

   @Nullable
   public IChatBaseComponent k() {
      return this.p;
   }

   public void l() {
      this.m.config().setAutoRead(false);
   }

   public void a(int i, boolean flag) {
      if (i >= 0) {
         if (this.m.pipeline().get("decompress") instanceof PacketDecompressor) {
            ((PacketDecompressor)this.m.pipeline().get("decompress")).a(i, flag);
         } else {
            this.m.pipeline().addBefore("decoder", "decompress", new PacketDecompressor(i, flag));
         }

         if (this.m.pipeline().get("compress") instanceof PacketCompressor) {
            ((PacketCompressor)this.m.pipeline().get("compress")).a(i);
         } else {
            this.m.pipeline().addBefore("encoder", "compress", new PacketCompressor(i));
         }
      } else {
         if (this.m.pipeline().get("decompress") instanceof PacketDecompressor) {
            this.m.pipeline().remove("decompress");
         }

         if (this.m.pipeline().get("compress") instanceof PacketCompressor) {
            this.m.pipeline().remove("compress");
         }
      }
   }

   public void m() {
      if (this.m != null && !this.m.isOpen()) {
         if (this.r) {
            j.warn("handleDisconnection() called twice");
         } else {
            this.r = true;
            if (this.k() != null) {
               this.j().a(this.k());
            } else if (this.j() != null) {
               this.j().a(IChatBaseComponent.c("multiplayer.disconnect.generic"));
            }

            this.l.clear();
         }
      }
   }

   public float n() {
      return this.u;
   }

   public float o() {
      return this.v;
   }

   private static class QueuedPacket {
      final Packet<?> a;
      @Nullable
      final PacketSendListener b;

      public QueuedPacket(Packet<?> packet, @Nullable PacketSendListener packetsendlistener) {
         this.a = packet;
         this.b = packetsendlistener;
      }
   }
}
