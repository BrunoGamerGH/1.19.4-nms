package net.minecraft.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.protocol.BundlerInfo;
import net.minecraft.network.protocol.EnumProtocolDirection;
import net.minecraft.network.protocol.Packet;

public class PacketBundlePacker extends MessageToMessageDecoder<Packet<?>> {
   @Nullable
   private BundlerInfo.a a;
   @Nullable
   private BundlerInfo b;
   private final EnumProtocolDirection c;

   public PacketBundlePacker(EnumProtocolDirection var0) {
      this.c = var0;
   }

   protected void a(ChannelHandlerContext var0, Packet<?> var1, List<Object> var2) throws Exception {
      BundlerInfo.b var3 = (BundlerInfo.b)var0.channel().attr(BundlerInfo.a).get();
      if (var3 == null) {
         throw new DecoderException("Bundler not configured: " + var1);
      } else {
         BundlerInfo var4 = var3.a(this.c);
         if (this.a != null) {
            if (this.b != var4) {
               throw new DecoderException("Bundler handler changed during bundling");
            }

            Packet<?> var5 = this.a.a(var1);
            if (var5 != null) {
               this.b = null;
               this.a = null;
               var2.add(var5);
            }
         } else {
            BundlerInfo.a var5 = var4.a(var1);
            if (var5 != null) {
               this.a = var5;
               this.b = var4;
            } else {
               var2.add(var1);
            }
         }
      }
   }
}
