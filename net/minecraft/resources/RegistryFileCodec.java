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

public final class RegistryFileCodec<E> implements Codec<Holder<E>> {
   private final ResourceKey<? extends IRegistry<E>> a;
   private final Codec<E> b;
   private final boolean c;

   public static <E> RegistryFileCodec<E> a(ResourceKey<? extends IRegistry<E>> var0, Codec<E> var1) {
      return a(var0, var1, true);
   }

   public static <E> RegistryFileCodec<E> a(ResourceKey<? extends IRegistry<E>> var0, Codec<E> var1, boolean var2) {
      return new RegistryFileCodec<>(var0, var1, var2);
   }

   private RegistryFileCodec(ResourceKey<? extends IRegistry<E>> var0, Codec<E> var1, boolean var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public <T> DataResult<T> a(Holder<E> var0, DynamicOps<T> var1, T var2) {
      if (var1 instanceof RegistryOps var3) {
         Optional<HolderOwner<E>> var4 = var3.a(this.a);
         if (var4.isPresent()) {
            if (!var0.a(var4.get())) {
               return DataResult.error(() -> "Element " + var0 + " is not valid in current registry set");
            }

            return (DataResult<T>)var0.d().map(var2x -> MinecraftKey.a.encode(var2x.a(), var1, var2), var2x -> this.b.encode(var2x, var1, var2));
         }
      }

      return this.b.encode(var0.a(), var1, var2);
   }

   public <T> DataResult<Pair<Holder<E>, T>> decode(DynamicOps<T> var0, T var1) {
      if (var0 instanceof RegistryOps var2) {
         Optional<HolderGetter<E>> var3 = var2.b(this.a);
         if (var3.isEmpty()) {
            return DataResult.error(() -> "Registry does not exist: " + this.a);
         } else {
            HolderGetter<E> var4 = var3.get();
            DataResult<Pair<MinecraftKey, T>> var5 = MinecraftKey.a.decode(var0, var1);
            if (var5.result().isEmpty()) {
               return !this.c
                  ? DataResult.error(() -> "Inline definitions not allowed here")
                  : this.b.decode(var0, var1).map(var0x -> var0x.mapFirst(Holder::a));
            } else {
               Pair<MinecraftKey, T> var6 = (Pair)var5.result().get();
               ResourceKey<E> var7 = ResourceKey.a(this.a, (MinecraftKey)var6.getFirst());
               return ((DataResult)var4.a(var7).map(DataResult::success).orElseGet(() -> (T)DataResult.error(() -> "Failed to get element " + var7)))
                  .map(var1x -> Pair.of(var1x, var6.getSecond()))
                  .setLifecycle(Lifecycle.stable());
            }
         }
      } else {
         return this.b.decode(var0, var1).map(var0x -> var0x.mapFirst(Holder::a));
      }
   }

   @Override
   public String toString() {
      return "RegistryFileCodec[" + this.a + " " + this.b + "]";
   }
}
