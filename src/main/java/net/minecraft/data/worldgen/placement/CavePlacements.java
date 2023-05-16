package net.minecraft.data.worldgen.placement;

import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;

public class CavePlacements {
   public static final ResourceKey<PlacedFeature> a = PlacementUtils.a("monster_room");
   public static final ResourceKey<PlacedFeature> b = PlacementUtils.a("monster_room_deep");
   public static final ResourceKey<PlacedFeature> c = PlacementUtils.a("fossil_upper");
   public static final ResourceKey<PlacedFeature> d = PlacementUtils.a("fossil_lower");
   public static final ResourceKey<PlacedFeature> e = PlacementUtils.a("dripstone_cluster");
   public static final ResourceKey<PlacedFeature> f = PlacementUtils.a("large_dripstone");
   public static final ResourceKey<PlacedFeature> g = PlacementUtils.a("pointed_dripstone");
   public static final ResourceKey<PlacedFeature> h = PlacementUtils.a("underwater_magma");
   public static final ResourceKey<PlacedFeature> i = PlacementUtils.a("glow_lichen");
   public static final ResourceKey<PlacedFeature> j = PlacementUtils.a("rooted_azalea_tree");
   public static final ResourceKey<PlacedFeature> k = PlacementUtils.a("cave_vines");
   public static final ResourceKey<PlacedFeature> l = PlacementUtils.a("lush_caves_vegetation");
   public static final ResourceKey<PlacedFeature> m = PlacementUtils.a("lush_caves_clay");
   public static final ResourceKey<PlacedFeature> n = PlacementUtils.a("lush_caves_ceiling_vegetation");
   public static final ResourceKey<PlacedFeature> o = PlacementUtils.a("spore_blossom");
   public static final ResourceKey<PlacedFeature> p = PlacementUtils.a("classic_vines_cave_feature");
   public static final ResourceKey<PlacedFeature> q = PlacementUtils.a("amethyst_geode");
   public static final ResourceKey<PlacedFeature> r = PlacementUtils.a("sculk_patch_deep_dark");
   public static final ResourceKey<PlacedFeature> s = PlacementUtils.a("sculk_patch_ancient_city");
   public static final ResourceKey<PlacedFeature> t = PlacementUtils.a("sculk_vein");

   public static void a(BootstapContext<PlacedFeature> var0) {
      HolderGetter<WorldGenFeatureConfigured<?, ?>> var1 = var0.a(Registries.aq);
      Holder<WorldGenFeatureConfigured<?, ?>> var2 = var1.b(CaveFeatures.a);
      Holder<WorldGenFeatureConfigured<?, ?>> var3 = var1.b(CaveFeatures.b);
      Holder<WorldGenFeatureConfigured<?, ?>> var4 = var1.b(CaveFeatures.c);
      Holder<WorldGenFeatureConfigured<?, ?>> var5 = var1.b(CaveFeatures.d);
      Holder<WorldGenFeatureConfigured<?, ?>> var6 = var1.b(CaveFeatures.e);
      Holder<WorldGenFeatureConfigured<?, ?>> var7 = var1.b(CaveFeatures.f);
      Holder<WorldGenFeatureConfigured<?, ?>> var8 = var1.b(CaveFeatures.g);
      Holder<WorldGenFeatureConfigured<?, ?>> var9 = var1.b(CaveFeatures.h);
      Holder<WorldGenFeatureConfigured<?, ?>> var10 = var1.b(CaveFeatures.i);
      Holder<WorldGenFeatureConfigured<?, ?>> var11 = var1.b(CaveFeatures.j);
      Holder<WorldGenFeatureConfigured<?, ?>> var12 = var1.b(CaveFeatures.m);
      Holder<WorldGenFeatureConfigured<?, ?>> var13 = var1.b(CaveFeatures.r);
      Holder<WorldGenFeatureConfigured<?, ?>> var14 = var1.b(CaveFeatures.s);
      Holder<WorldGenFeatureConfigured<?, ?>> var15 = var1.b(CaveFeatures.t);
      Holder<WorldGenFeatureConfigured<?, ?>> var16 = var1.b(VegetationFeatures.c);
      Holder<WorldGenFeatureConfigured<?, ?>> var17 = var1.b(CaveFeatures.u);
      Holder<WorldGenFeatureConfigured<?, ?>> var18 = var1.b(CaveFeatures.v);
      Holder<WorldGenFeatureConfigured<?, ?>> var19 = var1.b(CaveFeatures.w);
      Holder<WorldGenFeatureConfigured<?, ?>> var20 = var1.b(CaveFeatures.x);
      PlacementUtils.a(
         var0, a, var2, CountPlacement.a(10), InSquarePlacement.a(), HeightRangePlacement.a(VerticalAnchor.a(0), VerticalAnchor.b()), BiomeFilter.a()
      );
      PlacementUtils.a(
         var0, b, var2, CountPlacement.a(4), InSquarePlacement.a(), HeightRangePlacement.a(VerticalAnchor.b(6), VerticalAnchor.a(-1)), BiomeFilter.a()
      );
      PlacementUtils.a(
         var0, c, var3, RarityFilter.a(64), InSquarePlacement.a(), HeightRangePlacement.a(VerticalAnchor.a(0), VerticalAnchor.b()), BiomeFilter.a()
      );
      PlacementUtils.a(
         var0, d, var4, RarityFilter.a(64), InSquarePlacement.a(), HeightRangePlacement.a(VerticalAnchor.a(), VerticalAnchor.a(-8)), BiomeFilter.a()
      );
      PlacementUtils.a(var0, e, var5, CountPlacement.a(UniformInt.a(48, 96)), InSquarePlacement.a(), PlacementUtils.i, BiomeFilter.a());
      PlacementUtils.a(var0, f, var6, CountPlacement.a(UniformInt.a(10, 48)), InSquarePlacement.a(), PlacementUtils.i, BiomeFilter.a());
      PlacementUtils.a(
         var0,
         g,
         var7,
         CountPlacement.a(UniformInt.a(192, 256)),
         InSquarePlacement.a(),
         PlacementUtils.i,
         CountPlacement.a(UniformInt.a(1, 5)),
         RandomOffsetPlacement.a(ClampedNormalInt.a(0.0F, 3.0F, -10, 10), ClampedNormalInt.a(0.0F, 0.6F, -2, 2)),
         BiomeFilter.a()
      );
      PlacementUtils.a(
         var0,
         h,
         var8,
         CountPlacement.a(UniformInt.a(44, 52)),
         InSquarePlacement.a(),
         PlacementUtils.i,
         SurfaceRelativeThresholdFilter.a(HeightMap.Type.c, Integer.MIN_VALUE, -2),
         BiomeFilter.a()
      );
      PlacementUtils.a(
         var0,
         i,
         var9,
         CountPlacement.a(UniformInt.a(104, 157)),
         PlacementUtils.i,
         InSquarePlacement.a(),
         SurfaceRelativeThresholdFilter.a(HeightMap.Type.c, Integer.MIN_VALUE, -13),
         BiomeFilter.a()
      );
      PlacementUtils.a(
         var0,
         j,
         var10,
         CountPlacement.a(UniformInt.a(1, 2)),
         InSquarePlacement.a(),
         PlacementUtils.i,
         EnvironmentScanPlacement.a(EnumDirection.b, BlockPredicate.c(), BlockPredicate.c, 12),
         RandomOffsetPlacement.a(ConstantInt.a(-1)),
         BiomeFilter.a()
      );
      PlacementUtils.a(
         var0,
         k,
         var11,
         CountPlacement.a(188),
         InSquarePlacement.a(),
         PlacementUtils.i,
         EnvironmentScanPlacement.a(EnumDirection.b, BlockPredicate.a(EnumDirection.a), BlockPredicate.c, 12),
         RandomOffsetPlacement.a(ConstantInt.a(-1)),
         BiomeFilter.a()
      );
      PlacementUtils.a(
         var0,
         l,
         var12,
         CountPlacement.a(125),
         InSquarePlacement.a(),
         PlacementUtils.i,
         EnvironmentScanPlacement.a(EnumDirection.a, BlockPredicate.c(), BlockPredicate.c, 12),
         RandomOffsetPlacement.a(ConstantInt.a(1)),
         BiomeFilter.a()
      );
      PlacementUtils.a(
         var0,
         m,
         var13,
         CountPlacement.a(62),
         InSquarePlacement.a(),
         PlacementUtils.i,
         EnvironmentScanPlacement.a(EnumDirection.a, BlockPredicate.c(), BlockPredicate.c, 12),
         RandomOffsetPlacement.a(ConstantInt.a(1)),
         BiomeFilter.a()
      );
      PlacementUtils.a(
         var0,
         n,
         var14,
         CountPlacement.a(125),
         InSquarePlacement.a(),
         PlacementUtils.i,
         EnvironmentScanPlacement.a(EnumDirection.b, BlockPredicate.c(), BlockPredicate.c, 12),
         RandomOffsetPlacement.a(ConstantInt.a(-1)),
         BiomeFilter.a()
      );
      PlacementUtils.a(
         var0,
         o,
         var15,
         CountPlacement.a(25),
         InSquarePlacement.a(),
         PlacementUtils.i,
         EnvironmentScanPlacement.a(EnumDirection.b, BlockPredicate.c(), BlockPredicate.c, 12),
         RandomOffsetPlacement.a(ConstantInt.a(-1)),
         BiomeFilter.a()
      );
      PlacementUtils.a(var0, p, var16, CountPlacement.a(256), InSquarePlacement.a(), PlacementUtils.i, BiomeFilter.a());
      PlacementUtils.a(
         var0, q, var17, RarityFilter.a(24), InSquarePlacement.a(), HeightRangePlacement.a(VerticalAnchor.b(6), VerticalAnchor.a(30)), BiomeFilter.a()
      );
      PlacementUtils.a(var0, r, var18, CountPlacement.a(ConstantInt.a(256)), InSquarePlacement.a(), PlacementUtils.i, BiomeFilter.a());
      PlacementUtils.a(var0, s, var19);
      PlacementUtils.a(var0, t, var20, CountPlacement.a(UniformInt.a(204, 250)), InSquarePlacement.a(), PlacementUtils.i, BiomeFilter.a());
   }
}
