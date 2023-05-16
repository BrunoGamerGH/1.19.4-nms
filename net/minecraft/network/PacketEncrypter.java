package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import javax.crypto.Cipher;

public class PacketEncrypter extends MessageToByteEncoder<ByteBuf> {
   private final PacketEncryptionHandler a;

   public PacketEncrypter(Cipher var0) {
      this.a = new PacketEncryptionHandler(var0);
   }

   protected void a(ChannelHandlerContext var0, ByteBuf var1, ByteBuf var2) throws Exception {
      this.a.a(var1, var2);
   }
}
