package net.minecraft.server.network;

import com.mojang.logging.LogUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import net.minecraft.server.MinecraftServer;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.server.ServerListPingEvent;
import org.slf4j.Logger;

public class LegacyPingHandler extends ChannelInboundHandlerAdapter {
   private static final Logger b = LogUtils.getLogger();
   public static final int a = 127;
   private final ServerConnection c;

   public LegacyPingHandler(ServerConnection serverconnection) {
      this.c = serverconnection;
   }

   public void channelRead(ChannelHandlerContext channelhandlercontext, Object object) {
      ByteBuf bytebuf = (ByteBuf)object;
      bytebuf.markReaderIndex();
      boolean flag = true;

      try {
         try {
            if (bytebuf.readUnsignedByte() != 254) {
               return;
            }

            InetSocketAddress inetsocketaddress = (InetSocketAddress)channelhandlercontext.channel().remoteAddress();
            MinecraftServer minecraftserver = this.c.d();
            int i = bytebuf.readableBytes();
            ServerListPingEvent event = CraftEventFactory.callServerListPingEvent(
               minecraftserver.server, inetsocketaddress.getAddress(), minecraftserver.aa(), minecraftserver.H(), minecraftserver.I()
            );
            switch(i) {
               case 0: {
                  b.debug("Ping: (<1.3.x) from {}:{}", inetsocketaddress.getAddress(), inetsocketaddress.getPort());
                  String s = String.format(Locale.ROOT, "%s§%d§%d", event.getMotd(), event.getNumPlayers(), event.getMaxPlayers());
                  this.a(channelhandlercontext, this.a(s));
                  break;
               }
               case 1: {
                  if (bytebuf.readUnsignedByte() != 1) {
                     return;
                  }

                  b.debug("Ping: (1.4-1.5.x) from {}:{}", inetsocketaddress.getAddress(), inetsocketaddress.getPort());
                  String s = String.format(
                     Locale.ROOT,
                     "§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d",
                     127,
                     minecraftserver.G(),
                     event.getMotd(),
                     event.getNumPlayers(),
                     event.getMaxPlayers()
                  );
                  this.a(channelhandlercontext, this.a(s));
                  break;
               }
               default:
                  boolean flag1 = bytebuf.readUnsignedByte() == 1;
                  flag1 &= bytebuf.readUnsignedByte() == 250;
                  flag1 &= "MC|PingHost".equals(new String(bytebuf.readBytes(bytebuf.readShort() * 2).array(), StandardCharsets.UTF_16BE));
                  int j = bytebuf.readUnsignedShort();
                  flag1 &= bytebuf.readUnsignedByte() >= 73;
                  flag1 &= 3 + bytebuf.readBytes(bytebuf.readShort() * 2).array().length + 4 == j;
                  flag1 &= bytebuf.readInt() <= 65535;
                  flag1 &= bytebuf.readableBytes() == 0;
                  if (!flag1) {
                     return;
                  }

                  b.debug("Ping: (1.6) from {}:{}", inetsocketaddress.getAddress(), inetsocketaddress.getPort());
                  String s1 = String.format(
                     Locale.ROOT,
                     "§1\u0000%d\u0000%s\u0000%s\u0000%d\u0000%d",
                     127,
                     minecraftserver.G(),
                     event.getMotd(),
                     event.getNumPlayers(),
                     event.getMaxPlayers()
                  );
                  ByteBuf bytebuf1 = this.a(s1);

                  try {
                     this.a(channelhandlercontext, bytebuf1);
                  } finally {
                     bytebuf1.release();
                  }
            }

            bytebuf.release();
            flag = false;
         } catch (RuntimeException var23) {
         }
      } finally {
         if (flag) {
            bytebuf.resetReaderIndex();
            channelhandlercontext.channel().pipeline().remove("legacy_query");
            channelhandlercontext.fireChannelRead(object);
         }
      }
   }

   private void a(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf) {
      channelhandlercontext.pipeline().firstContext().writeAndFlush(bytebuf).addListener(ChannelFutureListener.CLOSE);
   }

   private ByteBuf a(String s) {
      ByteBuf bytebuf = Unpooled.buffer();
      bytebuf.writeByte(255);
      char[] achar = s.toCharArray();
      bytebuf.writeShort(achar.length);

      for(char c0 : achar) {
         bytebuf.writeChar(c0);
      }

      return bytebuf;
   }
}
