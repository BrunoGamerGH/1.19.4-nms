package net.minecraft.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.EncoderException;
import io.netty.handler.codec.MessageToMessageEncoder;
import java.util.List;
import net.minecraft.network.protocol.BundlerInfo;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.Packet;

public class PacketBundleUnpacker extends MessageToMessageEncoder<Packet<?>> {
   private final EnumProtocolDirection a;

   public PacketBundleUnpacker(EnumProtocolDirection var0) {
      this.a = var0;
   }

   protected void a(ChannelHandlerContext var0, Packet<?> var1, List<Object> var2) throws Exception {
      BundlerInfo.b var3 = (BundlerInfo.b)var0.channel().attr(BundlerInfo.a).get();
      if (var3 == null) {
         throw new EncoderException("Bundler not configured: " + var1);
      } else {
         var3.a(this.a).a(var1, var2::add);
      }
   }
}
