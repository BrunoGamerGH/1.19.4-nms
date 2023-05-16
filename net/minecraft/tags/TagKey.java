package net.minecraft.tags;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Optional;
import net.minecraft.core.IRegistry;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;

public record TagKey<T>(ResourceKey<? extends IRegistry<T>> registry, MinecraftKey location) {
   private final ResourceKey<? extends IRegistry<T>> a;
   private final MinecraftKey b;
   private static final Interner<TagKey<?>> c = Interners.newWeakInterner();

   @Deprecated
   public TagKey(ResourceKey<? extends IRegistry<T>> var0, MinecraftKey var1) {
      this.a = var0;
      this.b = var1;
   }

   public static <T> Codec<TagKey<T>> a(ResourceKey<? extends IRegistry<T>> var0) {
      return MinecraftKey.a.xmap(var1 -> a(var0, var1), TagKey::b);
   }

   public static <T> Codec<TagKey<T>> b(ResourceKey<? extends IRegistry<T>> var0) {
      return Codec.STRING
         .comapFlatMap(
            var1 -> var1.startsWith("#") ? MinecraftKey.b(var1.substring(1)).map(var1x -> a(var0, var1x)) : DataResult.error(() -> "Not a tag id"),
            var0x -> "#" + var0x.b
         );
   }

   public static <T> TagKey<T> a(ResourceKey<? extends IRegistry<T>> var0, MinecraftKey var1) {
      return (TagKey<T>)c.intern(new TagKey<>(var0, var1));
   }

   public boolean c(ResourceKey<? extends IRegistry<?>> var0) {
      return this.a == var0;
   }

   public <E> Optional<TagKey<E>> d(ResourceKey<? extends IRegistry<E>> var0) {
      return this.c(var0) ? Optional.of(this) : Optional.empty();
   }

   @Override
   public String toString() {
      return "TagKey[" + this.a.a() + " / " + this.b + "]";
   }
}
