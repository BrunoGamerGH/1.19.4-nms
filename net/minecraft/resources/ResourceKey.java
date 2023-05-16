package net.minecraft.resources;

import com.google.common.collect.MapMaker;
import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public class ResourceKey<T> {
   private static final ConcurrentMap<ResourceKey.a, ResourceKey<?>> a = new MapMaker().weakValues().makeMap();
   private final MinecraftKey b;
   private final MinecraftKey c;

   public static <T> Codec<ResourceKey<T>> a(ResourceKey<? extends IRegistry<T>> var0) {
      return MinecraftKey.a.xmap(var1 -> a(var0, var1), ResourceKey::a);
   }

   public static <T> ResourceKey<T> a(ResourceKey<? extends IRegistry<T>> var0, MinecraftKey var1) {
      return a(var0.c, var1);
   }

   public static <T> ResourceKey<IRegistry<T>> a(MinecraftKey var0) {
      return a(BuiltInRegistries.a, var0);
   }

   private static <T> ResourceKey<T> a(MinecraftKey var0, MinecraftKey var1) {
      return (ResourceKey<T>)a.computeIfAbsent(new ResourceKey.a(var0, var1), var0x -> new ResourceKey(var0x.a, var0x.b));
   }

   private ResourceKey(MinecraftKey var0, MinecraftKey var1) {
      this.b = var0;
      this.c = var1;
   }

   @Override
   public String toString() {
      return "ResourceKey[" + this.b + " / " + this.c + "]";
   }

   public boolean b(ResourceKey<? extends IRegistry<?>> var0) {
      return this.b.equals(var0.a());
   }

   public <E> Optional<ResourceKey<E>> c(ResourceKey<? extends IRegistry<E>> var0) {
      return this.b(var0) ? Optional.of(this) : Optional.empty();
   }

   public MinecraftKey a() {
      return this.c;
   }

   public MinecraftKey b() {
      return this.b;
   }

   static record a(MinecraftKey registry, MinecraftKey location) {
      final MinecraftKey a;
      final MinecraftKey b;

      a(MinecraftKey var0, MinecraftKey var1) {
         this.a = var0;
         this.b = var1;
      }
   }
}
