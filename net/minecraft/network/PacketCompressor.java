package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.zip.Deflater;

public class PacketCompressor extends MessageToByteEncoder<ByteBuf> {
   private final byte[] a = new byte[8192];
   private final Deflater b;
   private int c;

   public PacketCompressor(int var0) {
      this.c = var0;
      this.b = new Deflater();
   }

   protected void a(ChannelHandlerContext var0, ByteBuf var1, ByteBuf var2) {
      int var3 = var1.readableBytes();
      PacketDataSerializer var4 = new PacketDataSerializer(var2);
      if (var3 < this.c) {
         var4.d(0);
         var4.writeBytes(var1);
      } else {
         byte[] var5 = new byte[var3];
         var1.readBytes(var5);
         var4.d(var5.length);
         this.b.setInput(var5, 0, var3);
         this.b.finish();

         while(!this.b.finished()) {
            int var6 = this.b.deflate(this.a);
            var4.writeBytes(this.a, 0, var6);
         }

         this.b.reset();
      }
   }

   public int a() {
      return this.c;
   }

   public void a(int var0) {
      this.c = var0;
   }
}
