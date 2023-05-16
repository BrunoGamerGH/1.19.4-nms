package net.minecraft.world.level.biome;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.SystemUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.INamable;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.carver.WorldGenCarverWrapper;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.slf4j.Logger;

public class BiomeSettingsGeneration {
   private static final Logger c = LogUtils.getLogger();
   public static final BiomeSettingsGeneration a = new BiomeSettingsGeneration(ImmutableMap.of(), ImmutableList.of());
   public static final MapCodec<BiomeSettingsGeneration> b = RecordCodecBuilder.mapCodec(
      var0 -> var0.group(
               Codec.simpleMap(
                     WorldGenStage.Features.c,
                     WorldGenCarverWrapper.c.promotePartial(SystemUtils.a("Carver: ", c::error)),
                     INamable.a(WorldGenStage.Features.values())
                  )
                  .fieldOf("carvers")
                  .forGetter(var0x -> var0x.d),
               PlacedFeature.d.promotePartial(SystemUtils.a("Features: ", c::error)).fieldOf("features").forGetter(var0x -> var0x.e)
            )
            .apply(var0, BiomeSettingsGeneration::new)
   );
   private final Map<WorldGenStage.Features, HolderSet<WorldGenCarverWrapper<?>>> d;
   private final List<HolderSet<PlacedFeature>> e;
   private final Supplier<List<WorldGenFeatureConfigured<?, ?>>> f;
   private final Supplier<Set<PlacedFeature>> g;

   BiomeSettingsGeneration(Map<WorldGenStage.Features, HolderSet<WorldGenCarverWrapper<?>>> var0, List<HolderSet<PlacedFeature>> var1) {
      this.d = var0;
      this.e = var1;
      this.f = Suppliers.memoize(
         () -> var1.stream()
               .flatMap(HolderSet::a)
               .map(Holder::a)
               .flatMap(PlacedFeature::a)
               .filter(var0xx -> var0xx.b() == WorldGenerator.h)
               .collect(ImmutableList.toImmutableList())
      );
      this.g = Suppliers.memoize(() -> var1.stream().flatMap(HolderSet::a).map(Holder::a).collect(Collectors.toSet()));
   }

   public Iterable<Holder<WorldGenCarverWrapper<?>>> a(WorldGenStage.Features var0) {
      return Objects.requireNonNullElseGet(this.d.get(var0), List::of);
   }

   public List<WorldGenFeatureConfigured<?, ?>> a() {
      return this.f.get();
   }

   public List<HolderSet<PlacedFeature>> b() {
      return this.e;
   }

   public boolean a(PlacedFeature var0) {
      return this.g.get().contains(var0);
   }

   public static class a extends BiomeSettingsGeneration.b {
      private final HolderGetter<PlacedFeature> a;
      private final HolderGetter<WorldGenCarverWrapper<?>> b;

      public a(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
         this.a = var0;
         this.b = var1;
      }

      public BiomeSettingsGeneration.a a(WorldGenStage.Decoration var0, ResourceKey<PlacedFeature> var1) {
         this.a(var0.ordinal(), this.a.b(var1));
         return this;
      }

      public BiomeSettingsGeneration.a a(WorldGenStage.Features var0, ResourceKey<WorldGenCarverWrapper<?>> var1) {
         this.a(var0, this.b.b(var1));
         return this;
      }
   }

   public static class b {
      private final Map<WorldGenStage.Features, List<Holder<WorldGenCarverWrapper<?>>>> a = Maps.newLinkedHashMap();
      private final List<List<Holder<PlacedFeature>>> b = Lists.newArrayList();

      public BiomeSettingsGeneration.b a(WorldGenStage.Decoration var0, Holder<PlacedFeature> var1) {
         return this.a(var0.ordinal(), var1);
      }

      public BiomeSettingsGeneration.b a(int var0, Holder<PlacedFeature> var1) {
         this.a(var0);
         this.b.get(var0).add(var1);
         return this;
      }

      public BiomeSettingsGeneration.b a(WorldGenStage.Features var0, Holder<WorldGenCarverWrapper<?>> var1) {
         this.a.computeIfAbsent(var0, var0x -> Lists.newArrayList()).add(var1);
         return this;
      }

      private void a(int var0) {
         while(this.b.size() <= var0) {
            this.b.add(Lists.newArrayList());
         }
      }

      public BiomeSettingsGeneration a() {
         return new BiomeSettingsGeneration(
            this.a.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, var0 -> HolderSet.a((List)var0.getValue()))),
            this.b.stream().map(HolderSet::a).collect(ImmutableList.toImmutableList())
         );
      }
   }
}
