package net.minecraft.data.worldgen.placement;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;

public class VegetationPlacements {
   public static final ResourceKey<PlacedFeature> a = PlacementUtils.a("bamboo_light");
   public static final ResourceKey<PlacedFeature> b = PlacementUtils.a("bamboo");
   public static final ResourceKey<PlacedFeature> c = PlacementUtils.a("vines");
   public static final ResourceKey<PlacedFeature> d = PlacementUtils.a("patch_sunflower");
   public static final ResourceKey<PlacedFeature> e = PlacementUtils.a("patch_pumpkin");
   public static final ResourceKey<PlacedFeature> f = PlacementUtils.a("patch_grass_plain");
   public static final ResourceKey<PlacedFeature> g = PlacementUtils.a("patch_grass_forest");
   public static final ResourceKey<PlacedFeature> h = PlacementUtils.a("patch_grass_badlands");
   public static final ResourceKey<PlacedFeature> i = PlacementUtils.a("patch_grass_savanna");
   public static final ResourceKey<PlacedFeature> j = PlacementUtils.a("patch_grass_normal");
   public static final ResourceKey<PlacedFeature> k = PlacementUtils.a("patch_grass_taiga_2");
   public static final ResourceKey<PlacedFeature> l = PlacementUtils.a("patch_grass_taiga");
   public static final ResourceKey<PlacedFeature> m = PlacementUtils.a("patch_grass_jungle");
   public static final ResourceKey<PlacedFeature> n = PlacementUtils.a("grass_bonemeal");
   public static final ResourceKey<PlacedFeature> o = PlacementUtils.a("patch_dead_bush_2");
   public static final ResourceKey<PlacedFeature> p = PlacementUtils.a("patch_dead_bush");
   public static final ResourceKey<PlacedFeature> q = PlacementUtils.a("patch_dead_bush_badlands");
   public static final ResourceKey<PlacedFeature> r = PlacementUtils.a("patch_melon");
   public static final ResourceKey<PlacedFeature> s = PlacementUtils.a("patch_melon_sparse");
   public static final ResourceKey<PlacedFeature> t = PlacementUtils.a("patch_berry_common");
   public static final ResourceKey<PlacedFeature> u = PlacementUtils.a("patch_berry_rare");
   public static final ResourceKey<PlacedFeature> v = PlacementUtils.a("patch_waterlily");
   public static final ResourceKey<PlacedFeature> w = PlacementUtils.a("patch_tall_grass_2");
   public static final ResourceKey<PlacedFeature> x = PlacementUtils.a("patch_tall_grass");
   public static final ResourceKey<PlacedFeature> y = PlacementUtils.a("patch_large_fern");
   public static final ResourceKey<PlacedFeature> z = PlacementUtils.a("patch_cactus_desert");
   public static final ResourceKey<PlacedFeature> A = PlacementUtils.a("patch_cactus_decorated");
   public static final ResourceKey<PlacedFeature> B = PlacementUtils.a("patch_sugar_cane_swamp");
   public static final ResourceKey<PlacedFeature> C = PlacementUtils.a("patch_sugar_cane_desert");
   public static final ResourceKey<PlacedFeature> D = PlacementUtils.a("patch_sugar_cane_badlands");
   public static final ResourceKey<PlacedFeature> E = PlacementUtils.a("patch_sugar_cane");
   public static final ResourceKey<PlacedFeature> F = PlacementUtils.a("brown_mushroom_nether");
   public static final ResourceKey<PlacedFeature> G = PlacementUtils.a("red_mushroom_nether");
   public static final ResourceKey<PlacedFeature> H = PlacementUtils.a("brown_mushroom_normal");
   public static final ResourceKey<PlacedFeature> I = PlacementUtils.a("red_mushroom_normal");
   public static final ResourceKey<PlacedFeature> J = PlacementUtils.a("brown_mushroom_taiga");
   public static final ResourceKey<PlacedFeature> K = PlacementUtils.a("red_mushroom_taiga");
   public static final ResourceKey<PlacedFeature> L = PlacementUtils.a("brown_mushroom_old_growth");
   public static final ResourceKey<PlacedFeature> M = PlacementUtils.a("red_mushroom_old_growth");
   public static final ResourceKey<PlacedFeature> N = PlacementUtils.a("brown_mushroom_swamp");
   public static final ResourceKey<PlacedFeature> O = PlacementUtils.a("red_mushroom_swamp");
   public static final ResourceKey<PlacedFeature> P = PlacementUtils.a("flower_warm");
   public static final ResourceKey<PlacedFeature> Q = PlacementUtils.a("flower_default");
   public static final ResourceKey<PlacedFeature> R = PlacementUtils.a("flower_flower_forest");
   public static final ResourceKey<PlacedFeature> S = PlacementUtils.a("flower_swamp");
   public static final ResourceKey<PlacedFeature> T = PlacementUtils.a("flower_plains");
   public static final ResourceKey<PlacedFeature> U = PlacementUtils.a("flower_meadow");
   public static final ResourceKey<PlacedFeature> V = PlacementUtils.a("flower_cherry");
   public static final ResourceKey<PlacedFeature> W = PlacementUtils.a("trees_plains");
   public static final ResourceKey<PlacedFeature> X = PlacementUtils.a("dark_forest_vegetation");
   public static final ResourceKey<PlacedFeature> Y = PlacementUtils.a("flower_forest_flowers");
   public static final ResourceKey<PlacedFeature> Z = PlacementUtils.a("forest_flowers");
   public static final ResourceKey<PlacedFeature> aa = PlacementUtils.a("trees_flower_forest");
   public static final ResourceKey<PlacedFeature> ab = PlacementUtils.a("trees_meadow");
   public static final ResourceKey<PlacedFeature> ac = PlacementUtils.a("trees_cherry");
   public static final ResourceKey<PlacedFeature> ad = PlacementUtils.a("trees_taiga");
   public static final ResourceKey<PlacedFeature> ae = PlacementUtils.a("trees_grove");
   public static final ResourceKey<PlacedFeature> af = PlacementUtils.a("trees_badlands");
   public static final ResourceKey<PlacedFeature> ag = PlacementUtils.a("trees_snowy");
   public static final ResourceKey<PlacedFeature> ah = PlacementUtils.a("trees_swamp");
   public static final ResourceKey<PlacedFeature> ai = PlacementUtils.a("trees_windswept_savanna");
   public static final ResourceKey<PlacedFeature> aj = PlacementUtils.a("trees_savanna");
   public static final ResourceKey<PlacedFeature> ak = PlacementUtils.a("birch_tall");
   public static final ResourceKey<PlacedFeature> al = PlacementUtils.a("trees_birch");
   public static final ResourceKey<PlacedFeature> am = PlacementUtils.a("trees_windswept_forest");
   public static final ResourceKey<PlacedFeature> an = PlacementUtils.a("trees_windswept_hills");
   public static final ResourceKey<PlacedFeature> ao = PlacementUtils.a("trees_water");
   public static final ResourceKey<PlacedFeature> ap = PlacementUtils.a("trees_birch_and_oak");
   public static final ResourceKey<PlacedFeature> aq = PlacementUtils.a("trees_sparse_jungle");
   public static final ResourceKey<PlacedFeature> ar = PlacementUtils.a("trees_old_growth_spruce_taiga");
   public static final ResourceKey<PlacedFeature> as = PlacementUtils.a("trees_old_growth_pine_taiga");
   public static final ResourceKey<PlacedFeature> at = PlacementUtils.a("trees_jungle");
   public static final ResourceKey<PlacedFeature> au = PlacementUtils.a("bamboo_vegetation");
   public static final ResourceKey<PlacedFeature> av = PlacementUtils.a("mushroom_island_vegetation");
   public static final ResourceKey<PlacedFeature> aw = PlacementUtils.a("trees_mangrove");
   private static final PlacementModifier ax = SurfaceWaterDepthFilter.a(0);

   public static List<PlacementModifier> a(int var0) {
      return List.of(CountPlacement.a(var0), InSquarePlacement.a(), PlacementUtils.c, BiomeFilter.a());
   }

   private static List<PlacementModifier> a(int var0, @Nullable PlacementModifier var1) {
      Builder<PlacementModifier> var2 = ImmutableList.builder();
      if (var1 != null) {
         var2.add(var1);
      }

      if (var0 != 0) {
         var2.add(RarityFilter.a(var0));
      }

      var2.add(InSquarePlacement.a());
      var2.add(PlacementUtils.a);
      var2.add(BiomeFilter.a());
      return var2.build();
   }

   private static Builder<PlacementModifier> b(PlacementModifier var0) {
      return ImmutableList.builder().add(var0).add(InSquarePlacement.a()).add(ax).add(PlacementUtils.d).add(BiomeFilter.a());
   }

   public static List<PlacementModifier> a(PlacementModifier var0) {
      return b(var0).build();
   }

   public static List<PlacementModifier> a(PlacementModifier var0, Block var1) {
      return b(var0).add(BlockPredicateFilter.a(BlockPredicate.a(var1.o(), BlockPosition.b))).build();
   }

   public static void a(BootstapContext<PlacedFeature> var0) {
      HolderGetter<WorldGenFeatureConfigured<?, ?>> var1 = var0.a(Registries.aq);
      Holder<WorldGenFeatureConfigured<?, ?>> var2 = var1.b(VegetationFeatures.a);
      Holder<WorldGenFeatureConfigured<?, ?>> var3 = var1.b(VegetationFeatures.b);
      Holder<WorldGenFeatureConfigured<?, ?>> var4 = var1.b(VegetationFeatures.c);
      Holder<WorldGenFeatureConfigured<?, ?>> var5 = var1.b(VegetationFeatures.f);
      Holder<WorldGenFeatureConfigured<?, ?>> var6 = var1.b(VegetationFeatures.g);
      Holder<WorldGenFeatureConfigured<?, ?>> var7 = var1.b(VegetationFeatures.j);
      Holder<WorldGenFeatureConfigured<?, ?>> var8 = var1.b(VegetationFeatures.i);
      Holder<WorldGenFeatureConfigured<?, ?>> var9 = var1.b(VegetationFeatures.k);
      Holder<WorldGenFeatureConfigured<?, ?>> var10 = var1.b(VegetationFeatures.l);
      Holder<WorldGenFeatureConfigured<?, ?>> var11 = var1.b(VegetationFeatures.m);
      Holder<WorldGenFeatureConfigured<?, ?>> var12 = var1.b(VegetationFeatures.n);
      Holder<WorldGenFeatureConfigured<?, ?>> var13 = var1.b(VegetationFeatures.h);
      Holder<WorldGenFeatureConfigured<?, ?>> var14 = var1.b(VegetationFeatures.o);
      Holder<WorldGenFeatureConfigured<?, ?>> var15 = var1.b(VegetationFeatures.p);
      Holder<WorldGenFeatureConfigured<?, ?>> var16 = var1.b(VegetationFeatures.q);
      Holder<WorldGenFeatureConfigured<?, ?>> var17 = var1.b(VegetationFeatures.r);
      Holder<WorldGenFeatureConfigured<?, ?>> var18 = var1.b(VegetationFeatures.s);
      Holder<WorldGenFeatureConfigured<?, ?>> var19 = var1.b(VegetationFeatures.d);
      Holder<WorldGenFeatureConfigured<?, ?>> var20 = var1.b(VegetationFeatures.e);
      Holder<WorldGenFeatureConfigured<?, ?>> var21 = var1.b(VegetationFeatures.t);
      Holder<WorldGenFeatureConfigured<?, ?>> var22 = var1.b(VegetationFeatures.u);
      Holder<WorldGenFeatureConfigured<?, ?>> var23 = var1.b(VegetationFeatures.v);
      Holder<WorldGenFeatureConfigured<?, ?>> var24 = var1.b(VegetationFeatures.w);
      Holder<WorldGenFeatureConfigured<?, ?>> var25 = var1.b(VegetationFeatures.x);
      Holder<WorldGenFeatureConfigured<?, ?>> var26 = var1.b(VegetationFeatures.y);
      Holder<WorldGenFeatureConfigured<?, ?>> var27 = var1.b(VegetationFeatures.K);
      Holder<WorldGenFeatureConfigured<?, ?>> var28 = var1.b(VegetationFeatures.A);
      Holder<WorldGenFeatureConfigured<?, ?>> var29 = var1.b(VegetationFeatures.z);
      Holder<WorldGenFeatureConfigured<?, ?>> var30 = var1.b(VegetationFeatures.B);
      Holder<WorldGenFeatureConfigured<?, ?>> var31 = var1.b(VegetationFeatures.C);
      Holder<WorldGenFeatureConfigured<?, ?>> var32 = var1.b(VegetationFeatures.D);
      Holder<WorldGenFeatureConfigured<?, ?>> var33 = var1.b(VegetationFeatures.E);
      Holder<WorldGenFeatureConfigured<?, ?>> var34 = var1.b(TreeFeatures.g);
      Holder<WorldGenFeatureConfigured<?, ?>> var35 = var1.b(TreeFeatures.k);
      Holder<WorldGenFeatureConfigured<?, ?>> var36 = var1.b(TreeFeatures.K);
      Holder<WorldGenFeatureConfigured<?, ?>> var37 = var1.b(TreeFeatures.u);
      Holder<WorldGenFeatureConfigured<?, ?>> var38 = var1.b(VegetationFeatures.F);
      Holder<WorldGenFeatureConfigured<?, ?>> var39 = var1.b(VegetationFeatures.G);
      Holder<WorldGenFeatureConfigured<?, ?>> var40 = var1.b(TreeFeatures.D);
      Holder<WorldGenFeatureConfigured<?, ?>> var41 = var1.b(VegetationFeatures.H);
      Holder<WorldGenFeatureConfigured<?, ?>> var42 = var1.b(VegetationFeatures.I);
      Holder<WorldGenFeatureConfigured<?, ?>> var43 = var1.b(VegetationFeatures.J);
      Holder<WorldGenFeatureConfigured<?, ?>> var44 = var1.b(VegetationFeatures.L);
      Holder<WorldGenFeatureConfigured<?, ?>> var45 = var1.b(VegetationFeatures.M);
      Holder<WorldGenFeatureConfigured<?, ?>> var46 = var1.b(VegetationFeatures.N);
      Holder<WorldGenFeatureConfigured<?, ?>> var47 = var1.b(VegetationFeatures.O);
      Holder<WorldGenFeatureConfigured<?, ?>> var48 = var1.b(VegetationFeatures.P);
      Holder<WorldGenFeatureConfigured<?, ?>> var49 = var1.b(VegetationFeatures.Q);
      Holder<WorldGenFeatureConfigured<?, ?>> var50 = var1.b(VegetationFeatures.R);
      PlacementUtils.a(var0, a, var2, RarityFilter.a(4), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, b, var3, NoiseBasedCountPlacement.a(160, 80.0, 0.3), InSquarePlacement.a(), PlacementUtils.c, BiomeFilter.a());
      PlacementUtils.a(
         var0, c, var4, CountPlacement.a(127), InSquarePlacement.a(), HeightRangePlacement.a(VerticalAnchor.a(64), VerticalAnchor.a(100)), BiomeFilter.a()
      );
      PlacementUtils.a(var0, d, var5, RarityFilter.a(3), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, e, var6, RarityFilter.a(300), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, f, var7, NoiseThresholdCountPlacement.a(-0.8, 5, 10), InSquarePlacement.a(), PlacementUtils.c, BiomeFilter.a());
      PlacementUtils.a(var0, g, var7, a(2));
      PlacementUtils.a(var0, h, var7, InSquarePlacement.a(), PlacementUtils.c, BiomeFilter.a());
      PlacementUtils.a(var0, i, var7, a(20));
      PlacementUtils.a(var0, j, var7, a(5));
      PlacementUtils.a(var0, k, var8, InSquarePlacement.a(), PlacementUtils.c, BiomeFilter.a());
      PlacementUtils.a(var0, l, var8, a(7));
      PlacementUtils.a(var0, m, var9, a(25));
      PlacementUtils.a(var0, n, var10, PlacementUtils.a());
      PlacementUtils.a(var0, o, var11, a(2));
      PlacementUtils.a(var0, p, var11, InSquarePlacement.a(), PlacementUtils.c, BiomeFilter.a());
      PlacementUtils.a(var0, q, var11, a(20));
      PlacementUtils.a(var0, r, var12, RarityFilter.a(6), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, s, var12, RarityFilter.a(64), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, t, var13, RarityFilter.a(32), InSquarePlacement.a(), PlacementUtils.c, BiomeFilter.a());
      PlacementUtils.a(var0, u, var13, RarityFilter.a(384), InSquarePlacement.a(), PlacementUtils.c, BiomeFilter.a());
      PlacementUtils.a(var0, v, var14, a(4));
      PlacementUtils.a(
         var0, w, var15, NoiseThresholdCountPlacement.a(-0.8, 0, 7), RarityFilter.a(32), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a()
      );
      PlacementUtils.a(var0, x, var15, RarityFilter.a(5), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, y, var16, RarityFilter.a(5), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, z, var17, RarityFilter.a(6), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, A, var17, RarityFilter.a(13), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, B, var18, RarityFilter.a(3), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, C, var18, InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, D, var18, RarityFilter.a(5), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, E, var18, RarityFilter.a(6), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, F, var19, RarityFilter.a(2), InSquarePlacement.a(), PlacementUtils.e, BiomeFilter.a());
      PlacementUtils.a(var0, G, var20, RarityFilter.a(2), InSquarePlacement.a(), PlacementUtils.e, BiomeFilter.a());
      PlacementUtils.a(var0, H, var19, a(256, null));
      PlacementUtils.a(var0, I, var20, a(512, null));
      PlacementUtils.a(var0, J, var19, a(4, null));
      PlacementUtils.a(var0, K, var20, a(256, null));
      PlacementUtils.a(var0, L, var19, a(4, CountPlacement.a(3)));
      PlacementUtils.a(var0, M, var20, a(171, null));
      PlacementUtils.a(var0, N, var19, a(0, CountPlacement.a(2)));
      PlacementUtils.a(var0, O, var20, a(64, null));
      PlacementUtils.a(var0, P, var21, RarityFilter.a(16), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, Q, var21, RarityFilter.a(32), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, R, var22, CountPlacement.a(3), RarityFilter.a(2), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, S, var23, RarityFilter.a(32), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(
         var0, T, var24, NoiseThresholdCountPlacement.a(-0.8, 15, 4), RarityFilter.a(32), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a()
      );
      PlacementUtils.a(var0, V, var26, NoiseThresholdCountPlacement.a(-0.8, 5, 10), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(var0, U, var25, InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementModifier var51 = SurfaceWaterDepthFilter.a(0);
      PlacementUtils.a(
         var0,
         W,
         var27,
         PlacementUtils.a(0, 0.05F, 1),
         InSquarePlacement.a(),
         var51,
         PlacementUtils.d,
         BlockPredicateFilter.a(BlockPredicate.a(Blocks.x.o(), BlockPosition.b)),
         BiomeFilter.a()
      );
      PlacementUtils.a(var0, X, var28, CountPlacement.a(16), InSquarePlacement.a(), var51, PlacementUtils.d, BiomeFilter.a());
      PlacementUtils.a(
         var0,
         Y,
         var29,
         RarityFilter.a(7),
         InSquarePlacement.a(),
         PlacementUtils.a,
         CountPlacement.a(ClampedInt.a(UniformInt.a(-1, 3), 0, 3)),
         BiomeFilter.a()
      );
      PlacementUtils.a(
         var0,
         Z,
         var29,
         RarityFilter.a(7),
         InSquarePlacement.a(),
         PlacementUtils.a,
         CountPlacement.a(ClampedInt.a(UniformInt.a(-3, 1), 0, 1)),
         BiomeFilter.a()
      );
      PlacementUtils.a(var0, aa, var30, a(PlacementUtils.a(6, 0.1F, 1)));
      PlacementUtils.a(var0, ab, var31, a(RarityFilter.a(100)));
      PlacementUtils.a(var0, ac, var36, a(PlacementUtils.a(10, 0.1F, 1), Blocks.C));
      PlacementUtils.a(var0, ad, var32, a(PlacementUtils.a(10, 0.1F, 1)));
      PlacementUtils.a(var0, ae, var33, a(PlacementUtils.a(10, 0.1F, 1)));
      PlacementUtils.a(var0, af, var34, a(PlacementUtils.a(5, 0.1F, 1), Blocks.x));
      PlacementUtils.a(var0, ag, var35, a(PlacementUtils.a(0, 0.1F, 1), Blocks.y));
      PlacementUtils.a(
         var0,
         ah,
         var37,
         PlacementUtils.a(2, 0.1F, 1),
         InSquarePlacement.a(),
         SurfaceWaterDepthFilter.a(2),
         PlacementUtils.d,
         BiomeFilter.a(),
         BlockPredicateFilter.a(BlockPredicate.a(Blocks.x.o(), BlockPosition.b))
      );
      PlacementUtils.a(var0, ai, var38, a(PlacementUtils.a(2, 0.1F, 1)));
      PlacementUtils.a(var0, aj, var38, a(PlacementUtils.a(1, 0.1F, 1)));
      PlacementUtils.a(var0, ak, var39, a(PlacementUtils.a(10, 0.1F, 1)));
      PlacementUtils.a(var0, al, var40, a(PlacementUtils.a(10, 0.1F, 1), Blocks.z));
      PlacementUtils.a(var0, am, var41, a(PlacementUtils.a(3, 0.1F, 1)));
      PlacementUtils.a(var0, an, var41, a(PlacementUtils.a(0, 0.1F, 1)));
      PlacementUtils.a(var0, ao, var42, a(PlacementUtils.a(0, 0.1F, 1)));
      PlacementUtils.a(var0, ap, var43, a(PlacementUtils.a(10, 0.1F, 1)));
      PlacementUtils.a(var0, aq, var44, a(PlacementUtils.a(2, 0.1F, 1)));
      PlacementUtils.a(var0, ar, var45, a(PlacementUtils.a(10, 0.1F, 1)));
      PlacementUtils.a(var0, as, var46, a(PlacementUtils.a(10, 0.1F, 1)));
      PlacementUtils.a(var0, at, var47, a(PlacementUtils.a(50, 0.1F, 1)));
      PlacementUtils.a(var0, au, var48, a(PlacementUtils.a(30, 0.1F, 1)));
      PlacementUtils.a(var0, av, var49, InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(
         var0,
         aw,
         var50,
         CountPlacement.a(25),
         InSquarePlacement.a(),
         SurfaceWaterDepthFilter.a(5),
         PlacementUtils.d,
         BiomeFilter.a(),
         BlockPredicateFilter.a(BlockPredicate.a(Blocks.E.o(), BlockPosition.b))
      );
   }
}
