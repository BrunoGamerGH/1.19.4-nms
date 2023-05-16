package net.minecraft.network;

import com.mojang.logging.LogUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import org.slf4j.Logger;

public class PacketEncoder extends MessageToByteEncoder<Packet<?>> {
   private static final Logger a = LogUtils.getLogger();
   private final EnumProtocolDirection b;

   public PacketEncoder(EnumProtocolDirection var0) {
      this.b = var0;
   }

   protected void a(ChannelHandlerContext var0, Packet<?> var1, ByteBuf var2) throws Exception {
      EnumProtocol var3 = (EnumProtocol)var0.channel().attr(NetworkManager.e).get();
      if (var3 == null) {
         throw new RuntimeException("ConnectionProtocol unknown: " + var1);
      } else {
         int var4 = var3.a(this.b, var1);
         if (a.isDebugEnabled()) {
            a.debug(NetworkManager.d, "OUT: [{}:{}] {}", new Object[]{var0.channel().attr(NetworkManager.e).get(), var4, var1.getClass().getName()});
         }

         if (var4 == -1) {
            throw new IOException("Can't serialize unregistered packet");
         } else {
            PacketDataSerializer var5 = new PacketDataSerializer(var2);
            var5.d(var4);

            try {
               int var6 = var5.writerIndex();
               var1.a(var5);
               int var7 = var5.writerIndex() - var6;
               if (var7 > 8388608) {
                  throw new IllegalArgumentException("Packet too big (is " + var7 + ", should be less than 8388608): " + var1);
               } else {
                  int var8 = ((EnumProtocol)var0.channel().attr(NetworkManager.e).get()).a();
                  JvmProfiler.e.b(var8, var4, var0.channel().remoteAddress(), var7);
               }
            } catch (Throwable var10) {
               a.error("Error receiving packet {}", var4, var10);
               if (var1.b()) {
                  throw new SkipEncodeException(var10);
               } else {
                  throw var10;
               }
            }
         }
      }
   }
}
