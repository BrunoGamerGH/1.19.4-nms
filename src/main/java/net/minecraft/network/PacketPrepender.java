package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToByteEncoder;

@Sharable
public class PacketPrepender extends MessageToByteEncoder<ByteBuf> {
   private static final int a = 3;

   protected void a(ChannelHandlerContext var0, ByteBuf var1, ByteBuf var2) {
      int var3 = var1.readableBytes();
      int var4 = PacketDataSerializer.a(var3);
      if (var4 > 3) {
         throw new IllegalArgumentException("unable to fit " + var3 + " into 3");
      } else {
         PacketDataSerializer var5 = new PacketDataSerializer(var2);
         var5.ensureWritable(var4 + var3);
         var5.d(var3);
         var5.writeBytes(var1, var1.readerIndex(), var3);
      }
   }
}
