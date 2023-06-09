package net.minecraft.data.worldgen.placement;

import java.util.List;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class TreePlacements {
   public static final ResourceKey<PlacedFeature> a = PlacementUtils.a("crimson_fungi");
   public static final ResourceKey<PlacedFeature> b = PlacementUtils.a("warped_fungi");
   public static final ResourceKey<PlacedFeature> c = PlacementUtils.a("oak_checked");
   public static final ResourceKey<PlacedFeature> d = PlacementUtils.a("dark_oak_checked");
   public static final ResourceKey<PlacedFeature> e = PlacementUtils.a("birch_checked");
   public static final ResourceKey<PlacedFeature> f = PlacementUtils.a("acacia_checked");
   public static final ResourceKey<PlacedFeature> g = PlacementUtils.a("spruce_checked");
   public static final ResourceKey<PlacedFeature> h = PlacementUtils.a("mangrove_checked");
   public static final ResourceKey<PlacedFeature> i = PlacementUtils.a("cherry_checked");
   public static final ResourceKey<PlacedFeature> j = PlacementUtils.a("pine_on_snow");
   public static final ResourceKey<PlacedFeature> k = PlacementUtils.a("spruce_on_snow");
   public static final ResourceKey<PlacedFeature> l = PlacementUtils.a("pine_checked");
   public static final ResourceKey<PlacedFeature> m = PlacementUtils.a("jungle_tree");
   public static final ResourceKey<PlacedFeature> n = PlacementUtils.a("fancy_oak_checked");
   public static final ResourceKey<PlacedFeature> o = PlacementUtils.a("mega_jungle_tree_checked");
   public static final ResourceKey<PlacedFeature> p = PlacementUtils.a("mega_spruce_checked");
   public static final ResourceKey<PlacedFeature> q = PlacementUtils.a("mega_pine_checked");
   public static final ResourceKey<PlacedFeature> r = PlacementUtils.a("tall_mangrove_checked");
   public static final ResourceKey<PlacedFeature> s = PlacementUtils.a("jungle_bush");
   public static final ResourceKey<PlacedFeature> t = PlacementUtils.a("super_birch_bees_0002");
   public static final ResourceKey<PlacedFeature> u = PlacementUtils.a("super_birch_bees");
   public static final ResourceKey<PlacedFeature> v = PlacementUtils.a("oak_bees_0002");
   public static final ResourceKey<PlacedFeature> w = PlacementUtils.a("oak_bees_002");
   public static final ResourceKey<PlacedFeature> x = PlacementUtils.a("birch_bees_0002");
   public static final ResourceKey<PlacedFeature> y = PlacementUtils.a("birch_bees_002");
   public static final ResourceKey<PlacedFeature> z = PlacementUtils.a("fancy_oak_bees_0002");
   public static final ResourceKey<PlacedFeature> A = PlacementUtils.a("fancy_oak_bees_002");
   public static final ResourceKey<PlacedFeature> B = PlacementUtils.a("fancy_oak_bees");
   public static final ResourceKey<PlacedFeature> C = PlacementUtils.a("cherry_bees_005");

   public static void a(BootstapContext<PlacedFeature> var0) {
      HolderGetter<WorldGenFeatureConfigured<?, ?>> var1 = var0.a(Registries.aq);
      Holder<WorldGenFeatureConfigured<?, ?>> var2 = var1.b(TreeFeatures.a);
      Holder<WorldGenFeatureConfigured<?, ?>> var3 = var1.b(TreeFeatures.c);
      Holder<WorldGenFeatureConfigured<?, ?>> var4 = var1.b(TreeFeatures.g);
      Holder<WorldGenFeatureConfigured<?, ?>> var5 = var1.b(TreeFeatures.h);
      Holder<WorldGenFeatureConfigured<?, ?>> var6 = var1.b(TreeFeatures.i);
      Holder<WorldGenFeatureConfigured<?, ?>> var7 = var1.b(TreeFeatures.j);
      Holder<WorldGenFeatureConfigured<?, ?>> var8 = var1.b(TreeFeatures.k);
      Holder<WorldGenFeatureConfigured<?, ?>> var9 = var1.b(TreeFeatures.x);
      Holder<WorldGenFeatureConfigured<?, ?>> var10 = var1.b(TreeFeatures.z);
      Holder<WorldGenFeatureConfigured<?, ?>> var11 = var1.b(TreeFeatures.l);
      Holder<WorldGenFeatureConfigured<?, ?>> var12 = var1.b(TreeFeatures.m);
      Holder<WorldGenFeatureConfigured<?, ?>> var13 = var1.b(TreeFeatures.n);
      Holder<WorldGenFeatureConfigured<?, ?>> var14 = var1.b(TreeFeatures.p);
      Holder<WorldGenFeatureConfigured<?, ?>> var15 = var1.b(TreeFeatures.q);
      Holder<WorldGenFeatureConfigured<?, ?>> var16 = var1.b(TreeFeatures.r);
      Holder<WorldGenFeatureConfigured<?, ?>> var17 = var1.b(TreeFeatures.y);
      Holder<WorldGenFeatureConfigured<?, ?>> var18 = var1.b(TreeFeatures.v);
      Holder<WorldGenFeatureConfigured<?, ?>> var19 = var1.b(TreeFeatures.s);
      Holder<WorldGenFeatureConfigured<?, ?>> var20 = var1.b(TreeFeatures.t);
      Holder<WorldGenFeatureConfigured<?, ?>> var21 = var1.b(TreeFeatures.A);
      Holder<WorldGenFeatureConfigured<?, ?>> var22 = var1.b(TreeFeatures.B);
      Holder<WorldGenFeatureConfigured<?, ?>> var23 = var1.b(TreeFeatures.D);
      Holder<WorldGenFeatureConfigured<?, ?>> var24 = var1.b(TreeFeatures.E);
      Holder<WorldGenFeatureConfigured<?, ?>> var25 = var1.b(TreeFeatures.G);
      Holder<WorldGenFeatureConfigured<?, ?>> var26 = var1.b(TreeFeatures.H);
      Holder<WorldGenFeatureConfigured<?, ?>> var27 = var1.b(TreeFeatures.J);
      Holder<WorldGenFeatureConfigured<?, ?>> var28 = var1.b(TreeFeatures.K);
      PlacementUtils.a(var0, a, var2, CountOnEveryLayerPlacement.a(8), BiomeFilter.a());
      PlacementUtils.a(var0, b, var3, CountOnEveryLayerPlacement.a(8), BiomeFilter.a());
      PlacementUtils.a(var0, c, var4, PlacementUtils.a(Blocks.x));
      PlacementUtils.a(var0, d, var5, PlacementUtils.a(Blocks.D));
      PlacementUtils.a(var0, e, var6, PlacementUtils.a(Blocks.z));
      PlacementUtils.a(var0, f, var7, PlacementUtils.a(Blocks.B));
      PlacementUtils.a(var0, g, var8, PlacementUtils.a(Blocks.y));
      PlacementUtils.a(var0, h, var9, PlacementUtils.a(Blocks.E));
      PlacementUtils.a(var0, i, var10, PlacementUtils.a(Blocks.C));
      BlockPredicate var29 = BlockPredicate.a(EnumDirection.a.q(), Blocks.dO, Blocks.qy);
      List<PlacementModifier> var30 = List.of(
         EnvironmentScanPlacement.a(EnumDirection.b, BlockPredicate.a(BlockPredicate.a(Blocks.qy)), 8), BlockPredicateFilter.a(var29)
      );
      PlacementUtils.a(var0, j, var11, var30);
      PlacementUtils.a(var0, k, var8, var30);
      PlacementUtils.a(var0, l, var11, PlacementUtils.a(Blocks.y));
      PlacementUtils.a(var0, m, var12, PlacementUtils.a(Blocks.A));
      PlacementUtils.a(var0, n, var13, PlacementUtils.a(Blocks.x));
      PlacementUtils.a(var0, o, var14, PlacementUtils.a(Blocks.A));
      PlacementUtils.a(var0, p, var15, PlacementUtils.a(Blocks.y));
      PlacementUtils.a(var0, q, var16, PlacementUtils.a(Blocks.y));
      PlacementUtils.a(var0, r, var17, PlacementUtils.a(Blocks.E));
      PlacementUtils.a(var0, s, var18, PlacementUtils.a(Blocks.x));
      PlacementUtils.a(var0, t, var19, PlacementUtils.a(Blocks.z));
      PlacementUtils.a(var0, u, var20, PlacementUtils.a(Blocks.z));
      PlacementUtils.a(var0, v, var21, PlacementUtils.a(Blocks.x));
      PlacementUtils.a(var0, w, var22, PlacementUtils.a(Blocks.x));
      PlacementUtils.a(var0, x, var23, PlacementUtils.a(Blocks.z));
      PlacementUtils.a(var0, y, var24, PlacementUtils.a(Blocks.z));
      PlacementUtils.a(var0, z, var25, PlacementUtils.a(Blocks.x));
      PlacementUtils.a(var0, A, var26, PlacementUtils.a(Blocks.x));
      PlacementUtils.a(var0, B, var27, PlacementUtils.a(Blocks.x));
      PlacementUtils.a(var0, C, var28, PlacementUtils.a(Blocks.C));
   }
}
