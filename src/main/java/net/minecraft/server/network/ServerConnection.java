package net.minecraft.server.network;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.logging.LogUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.NetworkManagerServer;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.game.PacketPlayOutKickDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.LazyInitVar;
import org.slf4j.Logger;
import org.spigotmc.SpigotConfig;

public class ServerConnection {
   private static final Logger d = LogUtils.getLogger();
   public static final LazyInitVar<NioEventLoopGroup> a = new LazyInitVar(
      () -> new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Server IO #%d").setDaemon(true).build())
   );
   public static final LazyInitVar<EpollEventLoopGroup> b = new LazyInitVar(
      () -> new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Server IO #%d").setDaemon(true).build())
   );
   final MinecraftServer e;
   public volatile boolean c;
   private final List<ChannelFuture> f = Collections.synchronizedList(Lists.newArrayList());
   final List<NetworkManager> g = Collections.synchronizedList(Lists.newArrayList());

   public ServerConnection(MinecraftServer minecraftserver) {
      this.e = minecraftserver;
      this.c = true;
   }

   public void a(@Nullable InetAddress inetaddress, int i) throws IOException {
      List var4 = this.f;
      synchronized(this.f) {
         Class oclass;
         LazyInitVar lazyinitvar;
         if (Epoll.isAvailable() && this.e.n()) {
            oclass = EpollServerSocketChannel.class;
            lazyinitvar = b;
            d.info("Using epoll channel type");
         } else {
            oclass = NioServerSocketChannel.class;
            lazyinitvar = a;
            d.info("Using default channel type");
         }

         this.f
            .add(
               ((ServerBootstrap)((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(oclass))
                        .childHandler(
                           new ChannelInitializer<Channel>() {
                              protected void initChannel(Channel channel) {
                                 try {
                                    channel.config().setOption(ChannelOption.TCP_NODELAY, true);
                                 } catch (ChannelException var5) {
                                 }
                  
                                 ChannelPipeline channelpipeline = channel.pipeline()
                                    .addLast("timeout", new ReadTimeoutHandler(30))
                                    .addLast("legacy_query", new LegacyPingHandler(ServerConnection.this));
                                 NetworkManager.a(channelpipeline, EnumProtocolDirection.a);
                                 int j = ServerConnection.this.e.m();
                                 NetworkManager object = (NetworkManager)(j > 0 ? new NetworkManagerServer(j) : new NetworkManager(EnumProtocolDirection.a));
                                 ServerConnection.this.g.add(object);
                                 channelpipeline.addLast("packet_handler", object);
                                 object.a(new HandshakeListener(ServerConnection.this.e, object));
                              }
                           }
                        )
                        .group((EventLoopGroup)lazyinitvar.a())
                        .localAddress(inetaddress, i))
                     .option(ChannelOption.AUTO_READ, false))
                  .bind()
                  .syncUninterruptibly()
            );
      }
   }

   public void acceptConnections() {
      synchronized(this.f) {
         for(ChannelFuture future : this.f) {
            future.channel().config().setAutoRead(true);
         }
      }
   }

   public SocketAddress a() {
      List var3 = this.f;
      ChannelFuture channelfuture;
      synchronized(this.f) {
         channelfuture = ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().channel(LocalServerChannel.class))
               .childHandler(new ChannelInitializer<Channel>() {
                  protected void initChannel(Channel channel) {
                     NetworkManager networkmanager = new NetworkManager(EnumProtocolDirection.a);
                     networkmanager.a(new MemoryServerHandshakePacketListenerImpl(ServerConnection.this.e, networkmanager));
                     ServerConnection.this.g.add(networkmanager);
                     ChannelPipeline channelpipeline = channel.pipeline();
                     channelpipeline.addLast("packet_handler", networkmanager);
                  }
               })
               .group((EventLoopGroup)a.a())
               .localAddress(LocalAddress.ANY))
            .bind()
            .syncUninterruptibly();
         this.f.add(channelfuture);
      }

      return channelfuture.channel().localAddress();
   }

   public void b() {
      this.c = false;

      for(ChannelFuture channelfuture : this.f) {
         try {
            channelfuture.channel().close().sync();
         } catch (InterruptedException var4) {
            d.error("Interrupted whilst closing channel");
         }
      }
   }

   public void c() {
      List var2 = this.g;
      synchronized(this.g) {
         if (SpigotConfig.playerShuffle > 0 && MinecraftServer.currentTick % SpigotConfig.playerShuffle == 0) {
            Collections.shuffle(this.g);
         }

         Iterator iterator = this.g.iterator();

         while(iterator.hasNext()) {
            NetworkManager networkmanager = (NetworkManager)iterator.next();
            if (!networkmanager.i()) {
               if (networkmanager.h()) {
                  try {
                     networkmanager.a();
                  } catch (Exception var7) {
                     if (networkmanager.d()) {
                        throw new ReportedException(CrashReport.a(var7, "Ticking memory connection"));
                     }

                     d.warn("Failed to handle packet for {}", networkmanager.c(), var7);
                     IChatMutableComponent ichatmutablecomponent = IChatBaseComponent.b("Internal server error");
                     networkmanager.a(
                        new PacketPlayOutKickDisconnect(ichatmutablecomponent), PacketSendListener.a(() -> networkmanager.a(ichatmutablecomponent))
                     );
                     networkmanager.l();
                  }
               } else if (!networkmanager.preparing) {
                  iterator.remove();
                  networkmanager.m();
               }
            }
         }
      }
   }

   public MinecraftServer d() {
      return this.e;
   }

   public List<NetworkManager> e() {
      return this.g;
   }

   private static class LatencySimulator extends ChannelInboundHandlerAdapter {
      private static final Timer a = new HashedWheelTimer();
      private final int b;
      private final int c;
      private final List<ServerConnection.LatencySimulator.DelayedMessage> d = Lists.newArrayList();

      public LatencySimulator(int i, int j) {
         this.b = i;
         this.c = j;
      }

      public void channelRead(ChannelHandlerContext channelhandlercontext, Object object) {
         this.a(channelhandlercontext, object);
      }

      private void a(ChannelHandlerContext channelhandlercontext, Object object) {
         int i = this.b + (int)(Math.random() * (double)this.c);
         this.d.add(new ServerConnection.LatencySimulator.DelayedMessage(channelhandlercontext, object));
         a.newTimeout(this::a, (long)i, TimeUnit.MILLISECONDS);
      }

      private void a(Timeout timeout) {
         ServerConnection.LatencySimulator.DelayedMessage serverconnection_latencysimulator_delayedmessage = this.d.remove(0);
         serverconnection_latencysimulator_delayedmessage.a.fireChannelRead(serverconnection_latencysimulator_delayedmessage.b);
      }

      private static class DelayedMessage {
         public final ChannelHandlerContext a;
         public final Object b;

         public DelayedMessage(ChannelHandlerContext channelhandlercontext, Object object) {
            this.a = channelhandlercontext;
            this.b = object;
         }
      }
   }
}
