package net.minecraft.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;

public class RegistryCodecs {
   private static <T> MapCodec<RegistryCodecs.a<T>> a(ResourceKey<? extends IRegistry<T>> var0, MapCodec<T> var1) {
      return RecordCodecBuilder.mapCodec(
         var2 -> var2.group(
                  ResourceKey.a(var0).fieldOf("name").forGetter(RegistryCodecs.a::a),
                  Codec.INT.fieldOf("id").forGetter(RegistryCodecs.a::b),
                  var1.forGetter(RegistryCodecs.a::c)
               )
               .apply(var2, RegistryCodecs.a::new)
      );
   }

   public static <T> Codec<IRegistry<T>> a(ResourceKey<? extends IRegistry<T>> var0, Lifecycle var1, Codec<T> var2) {
      return a(var0, var2.fieldOf("element")).codec().listOf().xmap(var2x -> {
         IRegistryWritable<T> var3 = new RegistryMaterials<>(var0, var1);

         for(RegistryCodecs.a<T> var5 : var2x) {
            var3.b(var5.b(), var5.a(), var5.c(), var1);
         }

         return var3;
      }, var0x -> {
         Builder<RegistryCodecs.a<T>> var1x = ImmutableList.builder();

         for(T var3 : var0x) {
            var1x.add(new RegistryCodecs.a((ResourceKey<T>)var0x.c(var3).get(), var0x.a(var3), var3));
         }

         return var1x.build();
      });
   }

   public static <E> Codec<IRegistry<E>> b(ResourceKey<? extends IRegistry<E>> var0, Lifecycle var1, Codec<E> var2) {
      Codec<Map<ResourceKey<E>, E>> var3 = Codec.unboundedMap(ResourceKey.a(var0), var2);
      return var3.xmap(var2x -> {
         IRegistryWritable<E> var3x = new RegistryMaterials<>(var0, var1);
         var2x.forEach((var2xx, var3xx) -> var3x.a(var2xx, var3xx, var1));
         return var3x.l();
      }, var0x -> ImmutableMap.copyOf(var0x.g()));
   }

   public static <E> Codec<HolderSet<E>> a(ResourceKey<? extends IRegistry<E>> var0, Codec<E> var1) {
      return a(var0, var1, false);
   }

   public static <E> Codec<HolderSet<E>> a(ResourceKey<? extends IRegistry<E>> var0, Codec<E> var1, boolean var2) {
      return HolderSetCodec.a(var0, RegistryFileCodec.a(var0, var1), var2);
   }

   public static <E> Codec<HolderSet<E>> a(ResourceKey<? extends IRegistry<E>> var0) {
      return a(var0, false);
   }

   public static <E> Codec<HolderSet<E>> a(ResourceKey<? extends IRegistry<E>> var0, boolean var1) {
      return HolderSetCodec.a(var0, RegistryFixedCodec.a(var0), var1);
   }

   static record a<T>(ResourceKey<T> key, int id, T value) {
      private final ResourceKey<T> a;
      private final int b;
      private final T c;

      a(ResourceKey<T> var0, int var1, T var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }
   }
}
