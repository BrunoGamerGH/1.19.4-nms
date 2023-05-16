package net.minecraft.network;

import com.mojang.logging.LogUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.profiling.jfr.JvmProfiler;
import org.slf4j.Logger;

public class PacketDecoder extends ByteToMessageDecoder {
   private static final Logger a = LogUtils.getLogger();
   private final EnumProtocolDirection b;

   public PacketDecoder(EnumProtocolDirection var0) {
      this.b = var0;
   }

   protected void decode(ChannelHandlerContext var0, ByteBuf var1, List<Object> var2) throws Exception {
      int var3 = var1.readableBytes();
      if (var3 != 0) {
         PacketDataSerializer var4 = new PacketDataSerializer(var1);
         int var5 = var4.m();
         Packet<?> var6 = ((EnumProtocol)var0.channel().attr(NetworkManager.e).get()).a(this.b, var5, var4);
         if (var6 == null) {
            throw new IOException("Bad packet id " + var5);
         } else {
            int var7 = ((EnumProtocol)var0.channel().attr(NetworkManager.e).get()).a();
            JvmProfiler.e.a(var7, var5, var0.channel().remoteAddress(), var3);
            if (var4.readableBytes() > 0) {
               throw new IOException(
                  "Packet "
                     + ((EnumProtocol)var0.channel().attr(NetworkManager.e).get()).a()
                     + "/"
                     + var5
                     + " ("
                     + var6.getClass().getSimpleName()
                     + ") was larger than I expected, found "
                     + var4.readableBytes()
                     + " bytes extra whilst reading packet "
                     + var5
               );
            } else {
               var2.add(var6);
               if (a.isDebugEnabled()) {
                  a.debug(NetworkManager.c, " IN: [{}:{}] {}", new Object[]{var0.channel().attr(NetworkManager.e).get(), var5, var6.getClass().getName()});
               }
            }
         }
      }
   }
}
