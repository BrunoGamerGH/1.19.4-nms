package net.minecraft.data.worldgen.placement;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class NetherPlacements {
   public static final ResourceKey<PlacedFeature> a = PlacementUtils.a("delta");
   public static final ResourceKey<PlacedFeature> b = PlacementUtils.a("small_basalt_columns");
   public static final ResourceKey<PlacedFeature> c = PlacementUtils.a("large_basalt_columns");
   public static final ResourceKey<PlacedFeature> d = PlacementUtils.a("basalt_blobs");
   public static final ResourceKey<PlacedFeature> e = PlacementUtils.a("blackstone_blobs");
   public static final ResourceKey<PlacedFeature> f = PlacementUtils.a("glowstone_extra");
   public static final ResourceKey<PlacedFeature> g = PlacementUtils.a("glowstone");
   public static final ResourceKey<PlacedFeature> h = PlacementUtils.a("crimson_forest_vegetation");
   public static final ResourceKey<PlacedFeature> i = PlacementUtils.a("warped_forest_vegetation");
   public static final ResourceKey<PlacedFeature> j = PlacementUtils.a("nether_sprouts");
   public static final ResourceKey<PlacedFeature> k = PlacementUtils.a("twisting_vines");
   public static final ResourceKey<PlacedFeature> l = PlacementUtils.a("weeping_vines");
   public static final ResourceKey<PlacedFeature> m = PlacementUtils.a("patch_crimson_roots");
   public static final ResourceKey<PlacedFeature> n = PlacementUtils.a("basalt_pillar");
   public static final ResourceKey<PlacedFeature> o = PlacementUtils.a("spring_delta");
   public static final ResourceKey<PlacedFeature> p = PlacementUtils.a("spring_closed");
   public static final ResourceKey<PlacedFeature> q = PlacementUtils.a("spring_closed_double");
   public static final ResourceKey<PlacedFeature> r = PlacementUtils.a("spring_open");
   public static final ResourceKey<PlacedFeature> s = PlacementUtils.a("patch_soul_fire");
   public static final ResourceKey<PlacedFeature> t = PlacementUtils.a("patch_fire");

   public static void a(BootstapContext<PlacedFeature> var0) {
      HolderGetter<WorldGenFeatureConfigured<?, ?>> var1 = var0.a(Registries.aq);
      Holder<WorldGenFeatureConfigured<?, ?>> var2 = var1.b(NetherFeatures.a);
      Holder<WorldGenFeatureConfigured<?, ?>> var3 = var1.b(NetherFeatures.b);
      Holder<WorldGenFeatureConfigured<?, ?>> var4 = var1.b(NetherFeatures.c);
      Holder<WorldGenFeatureConfigured<?, ?>> var5 = var1.b(NetherFeatures.d);
      Holder<WorldGenFeatureConfigured<?, ?>> var6 = var1.b(NetherFeatures.e);
      Holder<WorldGenFeatureConfigured<?, ?>> var7 = var1.b(NetherFeatures.f);
      Holder<WorldGenFeatureConfigured<?, ?>> var8 = var1.b(NetherFeatures.g);
      Holder<WorldGenFeatureConfigured<?, ?>> var9 = var1.b(NetherFeatures.i);
      Holder<WorldGenFeatureConfigured<?, ?>> var10 = var1.b(NetherFeatures.k);
      Holder<WorldGenFeatureConfigured<?, ?>> var11 = var1.b(NetherFeatures.m);
      Holder<WorldGenFeatureConfigured<?, ?>> var12 = var1.b(NetherFeatures.o);
      Holder<WorldGenFeatureConfigured<?, ?>> var13 = var1.b(NetherFeatures.p);
      Holder<WorldGenFeatureConfigured<?, ?>> var14 = var1.b(NetherFeatures.q);
      Holder<WorldGenFeatureConfigured<?, ?>> var15 = var1.b(NetherFeatures.r);
      Holder<WorldGenFeatureConfigured<?, ?>> var16 = var1.b(NetherFeatures.s);
      Holder<WorldGenFeatureConfigured<?, ?>> var17 = var1.b(NetherFeatures.t);
      Holder<WorldGenFeatureConfigured<?, ?>> var18 = var1.b(NetherFeatures.v);
      Holder<WorldGenFeatureConfigured<?, ?>> var19 = var1.b(NetherFeatures.u);
      PlacementUtils.a(var0, a, var2, CountOnEveryLayerPlacement.a(40), BiomeFilter.a());
      PlacementUtils.a(var0, b, var3, CountOnEveryLayerPlacement.a(4), BiomeFilter.a());
      PlacementUtils.a(var0, c, var4, CountOnEveryLayerPlacement.a(2), BiomeFilter.a());
      PlacementUtils.a(var0, d, var5, CountPlacement.a(75), InSquarePlacement.a(), PlacementUtils.e, BiomeFilter.a());
      PlacementUtils.a(var0, e, var6, CountPlacement.a(25), InSquarePlacement.a(), PlacementUtils.e, BiomeFilter.a());
      PlacementUtils.a(var0, f, var7, CountPlacement.a(BiasedToBottomInt.a(0, 9)), InSquarePlacement.a(), PlacementUtils.h, BiomeFilter.a());
      PlacementUtils.a(var0, g, var7, CountPlacement.a(10), InSquarePlacement.a(), PlacementUtils.e, BiomeFilter.a());
      PlacementUtils.a(var0, h, var8, CountOnEveryLayerPlacement.a(6), BiomeFilter.a());
      PlacementUtils.a(var0, i, var9, CountOnEveryLayerPlacement.a(5), BiomeFilter.a());
      PlacementUtils.a(var0, j, var10, CountOnEveryLayerPlacement.a(4), BiomeFilter.a());
      PlacementUtils.a(var0, k, var11, CountPlacement.a(10), InSquarePlacement.a(), PlacementUtils.e, BiomeFilter.a());
      PlacementUtils.a(var0, l, var12, CountPlacement.a(10), InSquarePlacement.a(), PlacementUtils.e, BiomeFilter.a());
      PlacementUtils.a(var0, m, var13, PlacementUtils.e, BiomeFilter.a());
      PlacementUtils.a(var0, n, var14, CountPlacement.a(10), InSquarePlacement.a(), PlacementUtils.e, BiomeFilter.a());
      PlacementUtils.a(var0, o, var15, CountPlacement.a(16), InSquarePlacement.a(), PlacementUtils.h, BiomeFilter.a());
      PlacementUtils.a(var0, p, var16, CountPlacement.a(16), InSquarePlacement.a(), PlacementUtils.f, BiomeFilter.a());
      PlacementUtils.a(var0, q, var16, CountPlacement.a(32), InSquarePlacement.a(), PlacementUtils.f, BiomeFilter.a());
      PlacementUtils.a(var0, r, var17, CountPlacement.a(8), InSquarePlacement.a(), PlacementUtils.h, BiomeFilter.a());
      List<PlacementModifier> var20 = List.of(CountPlacement.a(UniformInt.a(0, 5)), InSquarePlacement.a(), PlacementUtils.h, BiomeFilter.a());
      PlacementUtils.a(var0, s, var18, var20);
      PlacementUtils.a(var0, t, var19, var20);
   }
}
