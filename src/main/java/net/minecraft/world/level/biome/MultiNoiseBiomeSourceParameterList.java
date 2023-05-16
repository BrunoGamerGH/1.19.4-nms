package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;

public class MultiNoiseBiomeSourceParameterList {
   public static final Codec<MultiNoiseBiomeSourceParameterList> a = RecordCodecBuilder.create(
      var0 -> var0.group(MultiNoiseBiomeSourceParameterList.a.d.fieldOf("preset").forGetter(var0x -> var0x.c), RegistryOps.c(Registries.an))
            .apply(var0, MultiNoiseBiomeSourceParameterList::new)
   );
   public static final Codec<Holder<MultiNoiseBiomeSourceParameterList>> b = RegistryFileCodec.a(Registries.aE, a);
   private final MultiNoiseBiomeSourceParameterList.a c;
   private final Climate.c<Holder<BiomeBase>> d;

   public MultiNoiseBiomeSourceParameterList(MultiNoiseBiomeSourceParameterList.a var0, HolderGetter<BiomeBase> var1) {
      this.c = var0;
      this.d = var0.f.apply(var1::b);
   }

   public Climate.c<Holder<BiomeBase>> a() {
      return this.d;
   }

   public static Map<MultiNoiseBiomeSourceParameterList.a, Climate.c<ResourceKey<BiomeBase>>> b() {
      return MultiNoiseBiomeSourceParameterList.a.g.values().stream().collect(Collectors.toMap(var0 -> var0, var0 -> var0.c().apply(var0x -> var0x)));
   }

   public static record a(MinecraftKey id, MultiNoiseBiomeSourceParameterList.a.a provider) {
      private final MinecraftKey e;
      final MultiNoiseBiomeSourceParameterList.a.a f;
      public static final MultiNoiseBiomeSourceParameterList.a a = new MultiNoiseBiomeSourceParameterList.a(
         new MinecraftKey("nether"),
         new MultiNoiseBiomeSourceParameterList.a.a() {
            @Override
            public <T> Climate.c<T> apply(Function<ResourceKey<BiomeBase>, T> var0) {
               return new Climate.c<>(
                  List.of(
                     Pair.of(Climate.a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), var0.apply(Biomes.ac)),
                     Pair.of(Climate.a(0.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), var0.apply(Biomes.af)),
                     Pair.of(Climate.a(0.4F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F), var0.apply(Biomes.ae)),
                     Pair.of(Climate.a(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.375F), var0.apply(Biomes.ad)),
                     Pair.of(Climate.a(-0.5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.175F), var0.apply(Biomes.ag))
                  )
               );
            }
         }
      );
      public static final MultiNoiseBiomeSourceParameterList.a b = new MultiNoiseBiomeSourceParameterList.a(
         new MinecraftKey("overworld"), new MultiNoiseBiomeSourceParameterList.a.a() {
            @Override
            public <T> Climate.c<T> apply(Function<ResourceKey<BiomeBase>, T> var0) {
               return MultiNoiseBiomeSourceParameterList.a.a(var0, OverworldBiomeBuilder.a.a);
            }
         }
      );
      public static final MultiNoiseBiomeSourceParameterList.a c = new MultiNoiseBiomeSourceParameterList.a(
         new MinecraftKey("overworld_update_1_20"), new MultiNoiseBiomeSourceParameterList.a.a() {
            @Override
            public <T> Climate.c<T> apply(Function<ResourceKey<BiomeBase>, T> var0) {
               return MultiNoiseBiomeSourceParameterList.a.a(var0, OverworldBiomeBuilder.a.b);
            }
         }
      );
      static final Map<MinecraftKey, MultiNoiseBiomeSourceParameterList.a> g = Stream.of(a, b, c)
         .collect(Collectors.toMap(MultiNoiseBiomeSourceParameterList.a::b, var0 -> var0));
      public static final Codec<MultiNoiseBiomeSourceParameterList.a> d = MinecraftKey.a
         .flatXmap(
            var0 -> (DataResult)Optional.ofNullable(g.get(var0)).map(DataResult::success).orElseGet(() -> DataResult.error(() -> "Unknown preset: " + var0)),
            var0 -> DataResult.success(var0.e)
         );

      public a(MinecraftKey var0, MultiNoiseBiomeSourceParameterList.a.a var1) {
         this.e = var0;
         this.f = var1;
      }

      static <T> Climate.c<T> a(Function<ResourceKey<BiomeBase>, T> var0, OverworldBiomeBuilder.a var1) {
         Builder<Pair<Climate.d, T>> var2 = ImmutableList.builder();
         new OverworldBiomeBuilder(var1).a(var2x -> var2.add(var2x.mapSecond(var0)));
         return new Climate.c<>(var2.build());
      }

      public Stream<ResourceKey<BiomeBase>> a() {
         return this.f.apply(var0 -> var0).a().stream().<ResourceKey<BiomeBase>>map(Pair::getSecond).distinct();
      }

      public MinecraftKey b() {
         return this.e;
      }

      public MultiNoiseBiomeSourceParameterList.a.a c() {
         return this.f;
      }

      @FunctionalInterface
      interface a {
         <T> Climate.c<T> apply(Function<ResourceKey<BiomeBase>, T> var1);
      }
   }
}
