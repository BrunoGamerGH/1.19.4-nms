package net.minecraft.core;

import java.util.Optional;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

public interface HolderGetter<T> {
   Optional<Holder.c<T>> a(ResourceKey<T> var1);

   default Holder.c<T> b(ResourceKey<T> var0) {
      return this.a(var0).orElseThrow(() -> new IllegalStateException("Missing element " + var0));
   }

   Optional<HolderSet.Named<T>> a(TagKey<T> var1);

   default HolderSet.Named<T> b(TagKey<T> var0) {
      return this.a(var0).orElseThrow(() -> new IllegalStateException("Missing tag " + var0));
   }

   public interface a {
      <T> Optional<HolderGetter<T>> a(ResourceKey<? extends IRegistry<? extends T>> var1);

      default <T> HolderGetter<T> b(ResourceKey<? extends IRegistry<? extends T>> var0) {
         return this.<T>a(var0).orElseThrow(() -> new IllegalStateException("Registry " + var0.a() + " not found"));
      }
   }
}
