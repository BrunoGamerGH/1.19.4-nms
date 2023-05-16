package net.minecraft.network.protocol;

import io.netty.util.AttributeKey;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.network.PacketListener;

public interface BundlerInfo {
   AttributeKey<BundlerInfo.b> a = AttributeKey.valueOf("bundler");
   int b = 4096;
   BundlerInfo c = new BundlerInfo() {
      @Override
      public void a(Packet<?> var0, Consumer<Packet<?>> var1) {
         var1.accept(var0);
      }

      @Nullable
      @Override
      public BundlerInfo.a a(Packet<?> var0) {
         return null;
      }
   };

   static <T extends PacketListener, P extends BundlePacket<T>> BundlerInfo a(
      final Class<P> var0, final Function<Iterable<Packet<T>>, P> var1, final BundleDelimiterPacket<T> var2
   ) {
      return new BundlerInfo() {
         @Override
         public void a(Packet<?> var0x, Consumer<Packet<?>> var1x) {
            if (var0.getClass() == var0) {
               P var2 = (P)var0;
               var1.accept(var2);
               var2.a().forEach(var1);
               var1.accept(var2);
            } else {
               var1.accept(var0);
            }
         }

         @Nullable
         @Override
         public BundlerInfo.a a(Packet<?> var0x) {
            return var0 == var2 ? new BundlerInfo.a() {
               private final List<Packet<T>> b = new ArrayList<>();

               @Nullable
               @Override
               public Packet<?> a(Packet<?> var0x) {
                  if (var0 == var2) {
                     return var1.apply(this.b);
                  } else if (this.b.size() >= 4096) {
                     throw new IllegalStateException("Too many packets in a bundle");
                  } else {
                     this.b.add(var0);
                     return null;
                  }
               }
            } : null;
         }
      };
   }

   void a(Packet<?> var1, Consumer<Packet<?>> var2);

   @Nullable
   BundlerInfo.a a(Packet<?> var1);

   public interface a {
      @Nullable
      Packet<?> a(Packet<?> var1);
   }

   public interface b {
      BundlerInfo a(EnumProtocolDirection var1);
   }
}
