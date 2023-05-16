package net.minecraft.resources;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.IRegistry;

public final class RegistryFixedCodec<E> implements Codec<Holder<E>> {
   private final ResourceKey<? extends IRegistry<E>> a;

   public static <E> RegistryFixedCodec<E> a(ResourceKey<? extends IRegistry<E>> var0) {
      return new RegistryFixedCodec<>(var0);
   }

   private RegistryFixedCodec(ResourceKey<? extends IRegistry<E>> var0) {
      this.a = var0;
   }

   public <T> DataResult<T> a(Holder<E> var0, DynamicOps<T> var1, T var2) {
      if (var1 instanceof RegistryOps var3) {
         Optional<HolderOwner<E>> var4 = var3.a(this.a);
         if (var4.isPresent()) {
            if (!var0.a(var4.get())) {
               return DataResult.error(() -> "Element " + var0 + " is not valid in current registry set");
            }

            return (DataResult<T>)var0.d()
               .map(
                  var2x -> MinecraftKey.a.encode(var2x.a(), var1, var2),
                  var0x -> DataResult.error(() -> "Elements from registry " + this.a + " can't be serialized to a value")
               );
         }
      }

      return DataResult.error(() -> "Can't access registry " + this.a);
   }

   public <T> DataResult<Pair<Holder<E>, T>> decode(DynamicOps<T> var0, T var1) {
      if (var0 instanceof RegistryOps var2) {
         Optional<HolderGetter<E>> var3 = var2.b(this.a);
         if (var3.isPresent()) {
            return MinecraftKey.a
               .decode(var0, var1)
               .flatMap(
                  var1x -> {
                     MinecraftKey var2x = (MinecraftKey)var1x.getFirst();
                     return ((DataResult)var3.get()
                           .a(ResourceKey.a(this.a, var2x))
                           .map(DataResult::success)
                           .orElseGet(() -> (T)DataResult.error(() -> "Failed to get element " + var2x)))
                        .map(var1xx -> Pair.of(var1xx, var1x.getSecond()))
                        .setLifecycle(Lifecycle.stable());
                  }
               );
         }
      }

      return DataResult.error(() -> "Can't access registry " + this.a);
   }

   @Override
   public String toString() {
      return "RegistryFixedCodec[" + this.a + "]";
   }
}
