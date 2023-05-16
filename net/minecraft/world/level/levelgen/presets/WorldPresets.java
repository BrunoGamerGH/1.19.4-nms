package net.minecraft.world.level.levelgen.presets;

import java.util.Map;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.IRegistry;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterLists;
import net.minecraft.world.level.biome.WorldChunkManager;
import net.minecraft.world.level.biome.WorldChunkManagerHell;
import net.minecraft.world.level.biome.WorldChunkManagerMultiNoise;
import net.minecraft.world.level.biome.WorldChunkManagerTheEnd;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.levelgen.ChunkGeneratorAbstract;
import net.minecraft.world.level.levelgen.ChunkProviderDebug;
import net.minecraft.world.level.levelgen.ChunkProviderFlat;
import net.minecraft.world.level.levelgen.GeneratorSettingBase;
import net.minecraft.world.level.levelgen.WorldDimensions;
import net.minecraft.world.level.levelgen.flat.GeneratorSettingsFlat;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.StructureSet;

public class WorldPresets {
   public static final ResourceKey<WorldPreset> a = a("normal");
   public static final ResourceKey<WorldPreset> b = a("flat");
   public static final ResourceKey<WorldPreset> c = a("large_biomes");
   public static final ResourceKey<WorldPreset> d = a("amplified");
   public static final ResourceKey<WorldPreset> e = a("single_biome_surface");
   public static final ResourceKey<WorldPreset> f = a("debug_all_block_states");

   public static void a(BootstapContext<WorldPreset> var0) {
      new WorldPresets.a(var0).a();
   }

   private static ResourceKey<WorldPreset> a(String var0) {
      return ResourceKey.a(Registries.aD, new MinecraftKey(var0));
   }

   public static Optional<ResourceKey<WorldPreset>> a(IRegistry<WorldDimension> var0) {
      return var0.d(WorldDimension.b).flatMap(var0x -> {
         ChunkGenerator var1 = var0x.b();
         if (var1 instanceof ChunkProviderFlat) {
            return Optional.of(b);
         } else {
            return var1 instanceof ChunkProviderDebug ? Optional.of(f) : Optional.empty();
         }
      });
   }

   public static WorldDimensions a(IRegistryCustom var0) {
      return var0.d(Registries.aD).f(a).a().a();
   }

   public static WorldDimension b(IRegistryCustom var0) {
      return var0.d(Registries.aD).f(a).a().b().orElseThrow();
   }

   static class a {
      private final BootstapContext<WorldPreset> a;
      private final HolderGetter<GeneratorSettingBase> b;
      private final HolderGetter<BiomeBase> c;
      private final HolderGetter<PlacedFeature> d;
      private final HolderGetter<StructureSet> e;
      private final HolderGetter<MultiNoiseBiomeSourceParameterList> f;
      private final Holder<DimensionManager> g;
      private final WorldDimension h;
      private final WorldDimension i;

      a(BootstapContext<WorldPreset> var0) {
         this.a = var0;
         HolderGetter<DimensionManager> var1 = var0.a(Registries.as);
         this.b = var0.a(Registries.au);
         this.c = var0.a(Registries.an);
         this.d = var0.a(Registries.aw);
         this.e = var0.a(Registries.az);
         this.f = var0.a(Registries.aE);
         this.g = var1.b(BuiltinDimensionTypes.a);
         Holder<DimensionManager> var2 = var1.b(BuiltinDimensionTypes.b);
         Holder<GeneratorSettingBase> var3 = this.b.b(GeneratorSettingBase.f);
         Holder.c<MultiNoiseBiomeSourceParameterList> var4 = this.f.b(MultiNoiseBiomeSourceParameterLists.a);
         this.h = new WorldDimension(var2, new ChunkGeneratorAbstract(WorldChunkManagerMultiNoise.a(var4), var3));
         Holder<DimensionManager> var5 = var1.b(BuiltinDimensionTypes.c);
         Holder<GeneratorSettingBase> var6 = this.b.b(GeneratorSettingBase.g);
         this.i = new WorldDimension(var5, new ChunkGeneratorAbstract(WorldChunkManagerTheEnd.a(this.c), var6));
      }

      private WorldDimension a(ChunkGenerator var0) {
         return new WorldDimension(this.g, var0);
      }

      private WorldDimension a(WorldChunkManager var0, Holder<GeneratorSettingBase> var1) {
         return this.a(new ChunkGeneratorAbstract(var0, var1));
      }

      private WorldPreset a(WorldDimension var0) {
         return new WorldPreset(Map.of(WorldDimension.b, var0, WorldDimension.c, this.h, WorldDimension.d, this.i));
      }

      private void a(ResourceKey<WorldPreset> var0, WorldDimension var1) {
         this.a.a(var0, this.a(var1));
      }

      private void a(WorldChunkManager var0) {
         Holder<GeneratorSettingBase> var1 = this.b.b(GeneratorSettingBase.c);
         this.a(WorldPresets.a, this.a(var0, var1));
         Holder<GeneratorSettingBase> var2 = this.b.b(GeneratorSettingBase.d);
         this.a(WorldPresets.c, this.a(var0, var2));
         Holder<GeneratorSettingBase> var3 = this.b.b(GeneratorSettingBase.e);
         this.a(WorldPresets.d, this.a(var0, var3));
      }

      public void a() {
         Holder.c<MultiNoiseBiomeSourceParameterList> var0 = this.f.b(MultiNoiseBiomeSourceParameterLists.b);
         this.a(WorldChunkManagerMultiNoise.a(var0));
         Holder<GeneratorSettingBase> var1 = this.b.b(GeneratorSettingBase.c);
         Holder.c<BiomeBase> var2 = this.c.b(Biomes.b);
         this.a(WorldPresets.e, this.a(new WorldChunkManagerHell(var2), var1));
         this.a(WorldPresets.b, this.a(new ChunkProviderFlat(GeneratorSettingsFlat.a(this.c, this.e, this.d))));
         this.a(WorldPresets.f, this.a(new ChunkProviderDebug(var2)));
      }
   }
}
