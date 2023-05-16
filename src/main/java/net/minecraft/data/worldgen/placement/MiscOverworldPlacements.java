package net.minecraft.data.worldgen.placement;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.heightproviders.VeryBiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import net.minecraft.world.level.material.FluidTypes;

public class MiscOverworldPlacements {
   public static final ResourceKey<PlacedFeature> a = PlacementUtils.a("ice_spike");
   public static final ResourceKey<PlacedFeature> b = PlacementUtils.a("ice_patch");
   public static final ResourceKey<PlacedFeature> c = PlacementUtils.a("forest_rock");
   public static final ResourceKey<PlacedFeature> d = PlacementUtils.a("iceberg_packed");
   public static final ResourceKey<PlacedFeature> e = PlacementUtils.a("iceberg_blue");
   public static final ResourceKey<PlacedFeature> f = PlacementUtils.a("blue_ice");
   public static final ResourceKey<PlacedFeature> g = PlacementUtils.a("lake_lava_underground");
   public static final ResourceKey<PlacedFeature> h = PlacementUtils.a("lake_lava_surface");
   public static final ResourceKey<PlacedFeature> i = PlacementUtils.a("disk_clay");
   public static final ResourceKey<PlacedFeature> j = PlacementUtils.a("disk_gravel");
   public static final ResourceKey<PlacedFeature> k = PlacementUtils.a("disk_sand");
   public static final ResourceKey<PlacedFeature> l = PlacementUtils.a("disk_grass");
   public static final ResourceKey<PlacedFeature> m = PlacementUtils.a("freeze_top_layer");
   public static final ResourceKey<PlacedFeature> n = PlacementUtils.a("void_start_platform");
   public static final ResourceKey<PlacedFeature> o = PlacementUtils.a("desert_well");
   public static final ResourceKey<PlacedFeature> p = PlacementUtils.a("spring_lava");
   public static final ResourceKey<PlacedFeature> q = PlacementUtils.a("spring_lava_frozen");
   public static final ResourceKey<PlacedFeature> r = PlacementUtils.a("spring_water");

   public static void a(BootstapContext<PlacedFeature> var0) {
      HolderGetter<WorldGenFeatureConfigured<?, ?>> var1 = var0.a(Registries.aq);
      Holder<WorldGenFeatureConfigured<?, ?>> var2 = var1.b(MiscOverworldFeatures.a);
      Holder<WorldGenFeatureConfigured<?, ?>> var3 = var1.b(MiscOverworldFeatures.b);
      Holder<WorldGenFeatureConfigured<?, ?>> var4 = var1.b(MiscOverworldFeatures.c);
      Holder<WorldGenFeatureConfigured<?, ?>> var5 = var1.b(MiscOverworldFeatures.d);
      Holder<WorldGenFeatureConfigured<?, ?>> var6 = var1.b(MiscOverworldFeatures.e);
      Holder<WorldGenFeatureConfigured<?, ?>> var7 = var1.b(MiscOverworldFeatures.f);
      Holder<WorldGenFeatureConfigured<?, ?>> var8 = var1.b(MiscOverworldFeatures.g);
      Holder<WorldGenFeatureConfigured<?, ?>> var9 = var1.b(MiscOverworldFeatures.h);
      Holder<WorldGenFeatureConfigured<?, ?>> var10 = var1.b(MiscOverworldFeatures.i);
      Holder<WorldGenFeatureConfigured<?, ?>> var11 = var1.b(MiscOverworldFeatures.j);
      Holder<WorldGenFeatureConfigured<?, ?>> var12 = var1.b(MiscOverworldFeatures.l);
      Holder<WorldGenFeatureConfigured<?, ?>> var13 = var1.b(MiscOverworldFeatures.k);
      Holder<WorldGenFeatureConfigured<?, ?>> var14 = var1.b(MiscOverworldFeatures.n);
      Holder<WorldGenFeatureConfigured<?, ?>> var15 = var1.b(MiscOverworldFeatures.o);
      Holder<WorldGenFeatureConfigured<?, ?>> var16 = var1.b(MiscOverworldFeatures.p);
      Holder<WorldGenFeatureConfigured<?, ?>> var17 = var1.b(MiscOverworldFeatures.q);
      Holder<WorldGenFeatureConfigured<?, ?>> var18 = var1.b(MiscOverworldFeatures.r);
      PlacementUtils.a(var0, a, var2, CountPlacement.a(3), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(
         var0,
         b,
         var3,
         CountPlacement.a(2),
         InSquarePlacement.a(),
         PlacementUtils.a,
         RandomOffsetPlacement.a(ConstantInt.a(-1)),
         BlockPredicateFilter.a(BlockPredicate.a(Blocks.dO)),
         BiomeFilter.a()
      );
      PlacementUtils.a(var0, c, var4, CountPlacement.a(2), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, e, var6, RarityFilter.a(200), InSquarePlacement.a(), BiomeFilter.a());
      PlacementUtils.a(var0, d, var5, RarityFilter.a(16), InSquarePlacement.a(), BiomeFilter.a());
      PlacementUtils.a(
         var0,
         f,
         var7,
         CountPlacement.a(UniformInt.a(0, 19)),
         InSquarePlacement.a(),
         HeightRangePlacement.a(VerticalAnchor.a(30), VerticalAnchor.a(61)),
         BiomeFilter.a()
      );
      PlacementUtils.a(
         var0,
         g,
         var8,
         RarityFilter.a(9),
         InSquarePlacement.a(),
         HeightRangePlacement.a(UniformHeight.a(VerticalAnchor.a(0), VerticalAnchor.b())),
         EnvironmentScanPlacement.a(EnumDirection.a, BlockPredicate.a(BlockPredicate.a(BlockPredicate.c), BlockPredicate.d(new BlockPosition(0, -5, 0))), 32),
         SurfaceRelativeThresholdFilter.a(HeightMap.Type.c, Integer.MIN_VALUE, -5),
         BiomeFilter.a()
      );
      PlacementUtils.a(var0, h, var8, RarityFilter.a(200), InSquarePlacement.a(), PlacementUtils.c, BiomeFilter.a());
      PlacementUtils.a(var0, i, var9, InSquarePlacement.a(), PlacementUtils.b, BlockPredicateFilter.a(BlockPredicate.a(FluidTypes.c)), BiomeFilter.a());
      PlacementUtils.a(var0, j, var10, InSquarePlacement.a(), PlacementUtils.b, BlockPredicateFilter.a(BlockPredicate.a(FluidTypes.c)), BiomeFilter.a());
      PlacementUtils.a(
         var0, k, var11, CountPlacement.a(3), InSquarePlacement.a(), PlacementUtils.b, BlockPredicateFilter.a(BlockPredicate.a(FluidTypes.c)), BiomeFilter.a()
      );
      PlacementUtils.a(
         var0,
         l,
         var12,
         CountPlacement.a(1),
         InSquarePlacement.a(),
         PlacementUtils.b,
         RandomOffsetPlacement.a(ConstantInt.a(-1)),
         BlockPredicateFilter.a(BlockPredicate.a(Blocks.rC)),
         BiomeFilter.a()
      );
      PlacementUtils.a(var0, m, var13, BiomeFilter.a());
      PlacementUtils.a(var0, n, var14, BiomeFilter.a());
      PlacementUtils.a(var0, o, var15, RarityFilter.a(1000), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(
         var0,
         p,
         var16,
         CountPlacement.a(20),
         InSquarePlacement.a(),
         HeightRangePlacement.a(VeryBiasedToBottomHeight.a(VerticalAnchor.a(), VerticalAnchor.c(8), 8)),
         BiomeFilter.a()
      );
      PlacementUtils.a(
         var0,
         q,
         var17,
         CountPlacement.a(20),
         InSquarePlacement.a(),
         HeightRangePlacement.a(VeryBiasedToBottomHeight.a(VerticalAnchor.a(), VerticalAnchor.c(8), 8)),
         BiomeFilter.a()
      );
      PlacementUtils.a(
         var0, r, var18, CountPlacement.a(25), InSquarePlacement.a(), HeightRangePlacement.a(VerticalAnchor.a(), VerticalAnchor.a(192)), BiomeFilter.a()
      );
   }
}
