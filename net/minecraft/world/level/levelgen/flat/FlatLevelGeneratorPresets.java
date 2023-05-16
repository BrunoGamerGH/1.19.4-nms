package net.minecraft.world.level.levelgen.flat;

import com.google.common.collect.ImmutableSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.IMaterial;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.StructureSet;

public class FlatLevelGeneratorPresets {
   public static final ResourceKey<FlatLevelGeneratorPreset> a = a("classic_flat");
   public static final ResourceKey<FlatLevelGeneratorPreset> b = a("tunnelers_dream");
   public static final ResourceKey<FlatLevelGeneratorPreset> c = a("water_world");
   public static final ResourceKey<FlatLevelGeneratorPreset> d = a("overworld");
   public static final ResourceKey<FlatLevelGeneratorPreset> e = a("snowy_kingdom");
   public static final ResourceKey<FlatLevelGeneratorPreset> f = a("bottomless_pit");
   public static final ResourceKey<FlatLevelGeneratorPreset> g = a("desert");
   public static final ResourceKey<FlatLevelGeneratorPreset> h = a("redstone_ready");
   public static final ResourceKey<FlatLevelGeneratorPreset> i = a("the_void");

   public static void a(BootstapContext<FlatLevelGeneratorPreset> var0) {
      new FlatLevelGeneratorPresets.a(var0).a();
   }

   private static ResourceKey<FlatLevelGeneratorPreset> a(String var0) {
      return ResourceKey.a(Registries.at, new MinecraftKey(var0));
   }

   static class a {
      private final BootstapContext<FlatLevelGeneratorPreset> a;

      a(BootstapContext<FlatLevelGeneratorPreset> var0) {
         this.a = var0;
      }

      private void a(
         ResourceKey<FlatLevelGeneratorPreset> var0,
         IMaterial var1,
         ResourceKey<BiomeBase> var2,
         Set<ResourceKey<StructureSet>> var3,
         boolean var4,
         boolean var5,
         WorldGenFlatLayerInfo... var6
      ) {
         HolderGetter<StructureSet> var7 = this.a.a(Registries.az);
         HolderGetter<PlacedFeature> var8 = this.a.a(Registries.aw);
         HolderGetter<BiomeBase> var9 = this.a.a(Registries.an);
         HolderSet.a<StructureSet> var10 = HolderSet.a(var3.stream().map(var7::b).collect(Collectors.toList()));
         GeneratorSettingsFlat var11 = new GeneratorSettingsFlat(Optional.of(var10), var9.b(var2), GeneratorSettingsFlat.b(var8));
         if (var4) {
            var11.a();
         }

         if (var5) {
            var11.b();
         }

         for(int var12 = var6.length - 1; var12 >= 0; --var12) {
            var11.e().add(var6[var12]);
         }

         this.a.a(var0, new FlatLevelGeneratorPreset(var1.k().j(), var11));
      }

      public void a() {
         this.a(
            FlatLevelGeneratorPresets.a,
            Blocks.i,
            Biomes.b,
            ImmutableSet.of(BuiltinStructureSets.a),
            false,
            false,
            new WorldGenFlatLayerInfo(1, Blocks.i),
            new WorldGenFlatLayerInfo(2, Blocks.j),
            new WorldGenFlatLayerInfo(1, Blocks.F)
         );
         this.a(
            FlatLevelGeneratorPresets.b,
            Blocks.b,
            Biomes.t,
            ImmutableSet.of(BuiltinStructureSets.j, BuiltinStructureSets.r),
            true,
            false,
            new WorldGenFlatLayerInfo(1, Blocks.i),
            new WorldGenFlatLayerInfo(5, Blocks.j),
            new WorldGenFlatLayerInfo(230, Blocks.b),
            new WorldGenFlatLayerInfo(1, Blocks.F)
         );
         this.a(
            FlatLevelGeneratorPresets.c,
            Items.pH,
            Biomes.T,
            ImmutableSet.of(BuiltinStructureSets.m, BuiltinStructureSets.l, BuiltinStructureSets.g),
            false,
            false,
            new WorldGenFlatLayerInfo(90, Blocks.G),
            new WorldGenFlatLayerInfo(5, Blocks.L),
            new WorldGenFlatLayerInfo(5, Blocks.j),
            new WorldGenFlatLayerInfo(5, Blocks.b),
            new WorldGenFlatLayerInfo(64, Blocks.rD),
            new WorldGenFlatLayerInfo(1, Blocks.F)
         );
         this.a(
            FlatLevelGeneratorPresets.d,
            Blocks.bs,
            Biomes.b,
            ImmutableSet.of(BuiltinStructureSets.a, BuiltinStructureSets.j, BuiltinStructureSets.f, BuiltinStructureSets.k, BuiltinStructureSets.r),
            true,
            true,
            new WorldGenFlatLayerInfo(1, Blocks.i),
            new WorldGenFlatLayerInfo(3, Blocks.j),
            new WorldGenFlatLayerInfo(59, Blocks.b),
            new WorldGenFlatLayerInfo(1, Blocks.F)
         );
         this.a(
            FlatLevelGeneratorPresets.e,
            Blocks.dM,
            Biomes.d,
            ImmutableSet.of(BuiltinStructureSets.a, BuiltinStructureSets.c),
            false,
            false,
            new WorldGenFlatLayerInfo(1, Blocks.dM),
            new WorldGenFlatLayerInfo(1, Blocks.i),
            new WorldGenFlatLayerInfo(3, Blocks.j),
            new WorldGenFlatLayerInfo(59, Blocks.b),
            new WorldGenFlatLayerInfo(1, Blocks.F)
         );
         this.a(
            FlatLevelGeneratorPresets.f,
            Items.oB,
            Biomes.b,
            ImmutableSet.of(BuiltinStructureSets.a),
            false,
            false,
            new WorldGenFlatLayerInfo(1, Blocks.i),
            new WorldGenFlatLayerInfo(3, Blocks.j),
            new WorldGenFlatLayerInfo(2, Blocks.m)
         );
         this.a(
            FlatLevelGeneratorPresets.g,
            Blocks.I,
            Biomes.f,
            ImmutableSet.of(BuiltinStructureSets.a, BuiltinStructureSets.b, BuiltinStructureSets.j, BuiltinStructureSets.r),
            true,
            false,
            new WorldGenFlatLayerInfo(8, Blocks.I),
            new WorldGenFlatLayerInfo(52, Blocks.aU),
            new WorldGenFlatLayerInfo(3, Blocks.b),
            new WorldGenFlatLayerInfo(1, Blocks.F)
         );
         this.a(
            FlatLevelGeneratorPresets.h,
            Items.li,
            Biomes.f,
            ImmutableSet.of(),
            false,
            false,
            new WorldGenFlatLayerInfo(116, Blocks.aU),
            new WorldGenFlatLayerInfo(3, Blocks.b),
            new WorldGenFlatLayerInfo(1, Blocks.F)
         );
         this.a(FlatLevelGeneratorPresets.i, Blocks.hV, Biomes.a, ImmutableSet.of(), true, false, new WorldGenFlatLayerInfo(1, Blocks.a));
      }
   }
}
