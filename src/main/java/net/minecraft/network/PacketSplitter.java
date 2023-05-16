package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;

public class PacketSplitter extends ByteToMessageDecoder {
   protected void decode(ChannelHandlerContext var0, ByteBuf var1, List<Object> var2) {
      var1.markReaderIndex();
      byte[] var3 = new byte[3];

      for(int var4 = 0; var4 < var3.length; ++var4) {
         if (!var1.isReadable()) {
            var1.resetReaderIndex();
            return;
         }

         var3[var4] = var1.readByte();
         if (var3[var4] >= 0) {
            PacketDataSerializer var5 = new PacketDataSerializer(Unpooled.wrappedBuffer(var3));

            try {
               int var6 = var5.m();
               if (var1.readableBytes() >= var6) {
                  var2.add(var1.readBytes(var6));
                  return;
               }

               var1.resetReaderIndex();
            } finally {
               var5.release();
            }

            return;
         }
      }

      throw new CorruptedFrameException("length wider than 21-bit");
   }
}
