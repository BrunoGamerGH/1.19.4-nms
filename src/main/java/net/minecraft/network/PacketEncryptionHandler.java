package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class PacketEncryptionHandler {
   private final Cipher a;
   private byte[] b = new byte[0];
   private byte[] c = new byte[0];

   protected PacketEncryptionHandler(Cipher var0) {
      this.a = var0;
   }

   private byte[] a(ByteBuf var0) {
      int var1 = var0.readableBytes();
      if (this.b.length < var1) {
         this.b = new byte[var1];
      }

      var0.readBytes(this.b, 0, var1);
      return this.b;
   }

   protected ByteBuf a(ChannelHandlerContext var0, ByteBuf var1) throws ShortBufferException {
      int var2 = var1.readableBytes();
      byte[] var3 = this.a(var1);
      ByteBuf var4 = var0.alloc().heapBuffer(this.a.getOutputSize(var2));
      var4.writerIndex(this.a.update(var3, 0, var2, var4.array(), var4.arrayOffset()));
      return var4;
   }

   protected void a(ByteBuf var0, ByteBuf var1) throws ShortBufferException {
      int var2 = var0.readableBytes();
      byte[] var3 = this.a(var0);
      int var4 = this.a.getOutputSize(var2);
      if (this.c.length < var4) {
         this.c = new byte[var4];
      }

      var1.writeBytes(this.c, 0, this.a.update(var3, 0, var2, this.c));
   }
}
