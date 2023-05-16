package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import javax.crypto.Cipher;

public class PacketDecrypter extends MessageToMessageDecoder<ByteBuf> {
   private final PacketEncryptionHandler a;

   public PacketDecrypter(Cipher var0) {
      this.a = new PacketEncryptionHandler(var0);
   }

   protected void a(ChannelHandlerContext var0, ByteBuf var1, List<Object> var2) throws Exception {
      var2.add(this.a.a(var0, var1));
   }
}
