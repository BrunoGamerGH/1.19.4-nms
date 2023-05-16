package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import java.util.List;
import java.util.zip.Inflater;

public class PacketDecompressor extends ByteToMessageDecoder {
   public static final int a = 2097152;
   public static final int b = 8388608;
   private final Inflater c;
   private int d;
   private boolean e;

   public PacketDecompressor(int var0, boolean var1) {
      this.d = var0;
      this.e = var1;
      this.c = new Inflater();
   }

   protected void decode(ChannelHandlerContext var0, ByteBuf var1, List<Object> var2) throws Exception {
      if (var1.readableBytes() != 0) {
         PacketDataSerializer var3 = new PacketDataSerializer(var1);
         int var4 = var3.m();
         if (var4 == 0) {
            var2.add(var3.readBytes(var3.readableBytes()));
         } else {
            if (this.e) {
               if (var4 < this.d) {
                  throw new DecoderException("Badly compressed packet - size of " + var4 + " is below server threshold of " + this.d);
               }

               if (var4 > 8388608) {
                  throw new DecoderException("Badly compressed packet - size of " + var4 + " is larger than protocol maximum of 8388608");
               }
            }

            byte[] var5 = new byte[var3.readableBytes()];
            var3.readBytes(var5);
            this.c.setInput(var5);
            byte[] var6 = new byte[var4];
            this.c.inflate(var6);
            var2.add(Unpooled.wrappedBuffer(var6));
            this.c.reset();
         }
      }
   }

   public void a(int var0, boolean var1) {
      this.d = var0;
      this.e = var1;
   }
}
