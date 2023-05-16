package net.minecraft.data.worldgen.placement;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class OrePlacements {
   public static final ResourceKey<PlacedFeature> a = PlacementUtils.a("ore_magma");
   public static final ResourceKey<PlacedFeature> b = PlacementUtils.a("ore_soul_sand");
   public static final ResourceKey<PlacedFeature> c = PlacementUtils.a("ore_gold_deltas");
   public static final ResourceKey<PlacedFeature> d = PlacementUtils.a("ore_quartz_deltas");
   public static final ResourceKey<PlacedFeature> e = PlacementUtils.a("ore_gold_nether");
   public static final ResourceKey<PlacedFeature> f = PlacementUtils.a("ore_quartz_nether");
   public static final ResourceKey<PlacedFeature> g = PlacementUtils.a("ore_gravel_nether");
   public static final ResourceKey<PlacedFeature> h = PlacementUtils.a("ore_blackstone");
   public static final ResourceKey<PlacedFeature> i = PlacementUtils.a("ore_dirt");
   public static final ResourceKey<PlacedFeature> j = PlacementUtils.a("ore_gravel");
   public static final ResourceKey<PlacedFeature> k = PlacementUtils.a("ore_granite_upper");
   public static final ResourceKey<PlacedFeature> l = PlacementUtils.a("ore_granite_lower");
   public static final ResourceKey<PlacedFeature> m = PlacementUtils.a("ore_diorite_upper");
   public static final ResourceKey<PlacedFeature> n = PlacementUtils.a("ore_diorite_lower");
   public static final ResourceKey<PlacedFeature> o = PlacementUtils.a("ore_andesite_upper");
   public static final ResourceKey<PlacedFeature> p = PlacementUtils.a("ore_andesite_lower");
   public static final ResourceKey<PlacedFeature> q = PlacementUtils.a("ore_tuff");
   public static final ResourceKey<PlacedFeature> r = PlacementUtils.a("ore_coal_upper");
   public static final ResourceKey<PlacedFeature> s = PlacementUtils.a("ore_coal_lower");
   public static final ResourceKey<PlacedFeature> t = PlacementUtils.a("ore_iron_upper");
   public static final ResourceKey<PlacedFeature> u = PlacementUtils.a("ore_iron_middle");
   public static final ResourceKey<PlacedFeature> v = PlacementUtils.a("ore_iron_small");
   public static final ResourceKey<PlacedFeature> w = PlacementUtils.a("ore_gold_extra");
   public static final ResourceKey<PlacedFeature> x = PlacementUtils.a("ore_gold");
   public static final ResourceKey<PlacedFeature> y = PlacementUtils.a("ore_gold_lower");
   public static final ResourceKey<PlacedFeature> z = PlacementUtils.a("ore_redstone");
   public static final ResourceKey<PlacedFeature> A = PlacementUtils.a("ore_redstone_lower");
   public static final ResourceKey<PlacedFeature> B = PlacementUtils.a("ore_diamond");
   public static final ResourceKey<PlacedFeature> C = PlacementUtils.a("ore_diamond_large");
   public static final ResourceKey<PlacedFeature> D = PlacementUtils.a("ore_diamond_buried");
   public static final ResourceKey<PlacedFeature> E = PlacementUtils.a("ore_lapis");
   public static final ResourceKey<PlacedFeature> F = PlacementUtils.a("ore_lapis_buried");
   public static final ResourceKey<PlacedFeature> G = PlacementUtils.a("ore_infested");
   public static final ResourceKey<PlacedFeature> H = PlacementUtils.a("ore_emerald");
   public static final ResourceKey<PlacedFeature> I = PlacementUtils.a("ore_ancient_debris_large");
   public static final ResourceKey<PlacedFeature> J = PlacementUtils.a("ore_debris_small");
   public static final ResourceKey<PlacedFeature> K = PlacementUtils.a("ore_copper");
   public static final ResourceKey<PlacedFeature> L = PlacementUtils.a("ore_copper_large");
   public static final ResourceKey<PlacedFeature> M = PlacementUtils.a("ore_clay");

   private static List<PlacementModifier> a(PlacementModifier var0, PlacementModifier var1) {
      return List.of(var0, InSquarePlacement.a(), var1, BiomeFilter.a());
   }

   private static List<PlacementModifier> a(int var0, PlacementModifier var1) {
      return a(CountPlacement.a(var0), var1);
   }

   private static List<PlacementModifier> b(int var0, PlacementModifier var1) {
      return a(RarityFilter.a(var0), var1);
   }

   public static void a(BootstapContext<PlacedFeature> var0) {
      HolderGetter<WorldGenFeatureConfigured<?, ?>> var1 = var0.a(Registries.aq);
      Holder<WorldGenFeatureConfigured<?, ?>> var2 = var1.b(OreFeatures.a);
      Holder<WorldGenFeatureConfigured<?, ?>> var3 = var1.b(OreFeatures.b);
      Holder<WorldGenFeatureConfigured<?, ?>> var4 = var1.b(OreFeatures.c);
      Holder<WorldGenFeatureConfigured<?, ?>> var5 = var1.b(OreFeatures.d);
      Holder<WorldGenFeatureConfigured<?, ?>> var6 = var1.b(OreFeatures.e);
      Holder<WorldGenFeatureConfigured<?, ?>> var7 = var1.b(OreFeatures.f);
      Holder<WorldGenFeatureConfigured<?, ?>> var8 = var1.b(OreFeatures.g);
      Holder<WorldGenFeatureConfigured<?, ?>> var9 = var1.b(OreFeatures.h);
      Holder<WorldGenFeatureConfigured<?, ?>> var10 = var1.b(OreFeatures.i);
      Holder<WorldGenFeatureConfigured<?, ?>> var11 = var1.b(OreFeatures.j);
      Holder<WorldGenFeatureConfigured<?, ?>> var12 = var1.b(OreFeatures.k);
      Holder<WorldGenFeatureConfigured<?, ?>> var13 = var1.b(OreFeatures.l);
      Holder<WorldGenFeatureConfigured<?, ?>> var14 = var1.b(OreFeatures.m);
      Holder<WorldGenFeatureConfigured<?, ?>> var15 = var1.b(OreFeatures.n);
      Holder<WorldGenFeatureConfigured<?, ?>> var16 = var1.b(OreFeatures.o);
      Holder<WorldGenFeatureConfigured<?, ?>> var17 = var1.b(OreFeatures.p);
      Holder<WorldGenFeatureConfigured<?, ?>> var18 = var1.b(OreFeatures.q);
      Holder<WorldGenFeatureConfigured<?, ?>> var19 = var1.b(OreFeatures.r);
      Holder<WorldGenFeatureConfigured<?, ?>> var20 = var1.b(OreFeatures.s);
      Holder<WorldGenFeatureConfigured<?, ?>> var21 = var1.b(OreFeatures.t);
      Holder<WorldGenFeatureConfigured<?, ?>> var22 = var1.b(OreFeatures.u);
      Holder<WorldGenFeatureConfigured<?, ?>> var23 = var1.b(OreFeatures.v);
      Holder<WorldGenFeatureConfigured<?, ?>> var24 = var1.b(OreFeatures.w);
      Holder<WorldGenFeatureConfigured<?, ?>> var25 = var1.b(OreFeatures.x);
      Holder<WorldGenFeatureConfigured<?, ?>> var26 = var1.b(OreFeatures.y);
      Holder<WorldGenFeatureConfigured<?, ?>> var27 = var1.b(OreFeatures.z);
      Holder<WorldGenFeatureConfigured<?, ?>> var28 = var1.b(OreFeatures.A);
      Holder<WorldGenFeatureConfigured<?, ?>> var29 = var1.b(OreFeatures.B);
      Holder<WorldGenFeatureConfigured<?, ?>> var30 = var1.b(OreFeatures.C);
      Holder<WorldGenFeatureConfigured<?, ?>> var31 = var1.b(OreFeatures.D);
      Holder<WorldGenFeatureConfigured<?, ?>> var32 = var1.b(OreFeatures.E);
      PlacementUtils.a(var0, a, var2, a(4, HeightRangePlacement.a(VerticalAnchor.a(27), VerticalAnchor.a(36))));
      PlacementUtils.a(var0, b, var3, a(12, HeightRangePlacement.a(VerticalAnchor.a(), VerticalAnchor.a(31))));
      PlacementUtils.a(var0, c, var4, a(20, PlacementUtils.f));
      PlacementUtils.a(var0, d, var5, a(32, PlacementUtils.f));
      PlacementUtils.a(var0, e, var4, a(10, PlacementUtils.f));
      PlacementUtils.a(var0, f, var5, a(16, PlacementUtils.f));
      PlacementUtils.a(var0, g, var6, a(2, HeightRangePlacement.a(VerticalAnchor.a(5), VerticalAnchor.a(41))));
      PlacementUtils.a(var0, h, var7, a(2, HeightRangePlacement.a(VerticalAnchor.a(5), VerticalAnchor.a(31))));
      PlacementUtils.a(var0, i, var8, a(7, HeightRangePlacement.a(VerticalAnchor.a(0), VerticalAnchor.a(160))));
      PlacementUtils.a(var0, j, var9, a(14, HeightRangePlacement.a(VerticalAnchor.a(), VerticalAnchor.b())));
      PlacementUtils.a(var0, k, var10, b(6, HeightRangePlacement.a(VerticalAnchor.a(64), VerticalAnchor.a(128))));
      PlacementUtils.a(var0, l, var10, a(2, HeightRangePlacement.a(VerticalAnchor.a(0), VerticalAnchor.a(60))));
      PlacementUtils.a(var0, m, var11, b(6, HeightRangePlacement.a(VerticalAnchor.a(64), VerticalAnchor.a(128))));
      PlacementUtils.a(var0, n, var11, a(2, HeightRangePlacement.a(VerticalAnchor.a(0), VerticalAnchor.a(60))));
      PlacementUtils.a(var0, o, var12, b(6, HeightRangePlacement.a(VerticalAnchor.a(64), VerticalAnchor.a(128))));
      PlacementUtils.a(var0, p, var12, a(2, HeightRangePlacement.a(VerticalAnchor.a(0), VerticalAnchor.a(60))));
      PlacementUtils.a(var0, q, var13, a(2, HeightRangePlacement.a(VerticalAnchor.a(), VerticalAnchor.a(0))));
      PlacementUtils.a(var0, r, var14, a(30, HeightRangePlacement.a(VerticalAnchor.a(136), VerticalAnchor.b())));
      PlacementUtils.a(var0, s, var15, a(20, HeightRangePlacement.b(VerticalAnchor.a(0), VerticalAnchor.a(192))));
      PlacementUtils.a(var0, t, var16, a(90, HeightRangePlacement.b(VerticalAnchor.a(80), VerticalAnchor.a(384))));
      PlacementUtils.a(var0, u, var16, a(10, HeightRangePlacement.b(VerticalAnchor.a(-24), VerticalAnchor.a(56))));
      PlacementUtils.a(var0, v, var17, a(10, HeightRangePlacement.a(VerticalAnchor.a(), VerticalAnchor.a(72))));
      PlacementUtils.a(var0, w, var18, a(50, HeightRangePlacement.a(VerticalAnchor.a(32), VerticalAnchor.a(256))));
      PlacementUtils.a(var0, x, var19, a(4, HeightRangePlacement.b(VerticalAnchor.a(-64), VerticalAnchor.a(32))));
      PlacementUtils.a(var0, y, var19, a(CountPlacement.a(UniformInt.a(0, 1)), HeightRangePlacement.a(VerticalAnchor.a(-64), VerticalAnchor.a(-48))));
      PlacementUtils.a(var0, z, var20, a(4, HeightRangePlacement.a(VerticalAnchor.a(), VerticalAnchor.a(15))));
      PlacementUtils.a(var0, A, var20, a(8, HeightRangePlacement.b(VerticalAnchor.b(-32), VerticalAnchor.b(32))));
      PlacementUtils.a(var0, B, var21, a(7, HeightRangePlacement.b(VerticalAnchor.b(-80), VerticalAnchor.b(80))));
      PlacementUtils.a(var0, C, var22, b(9, HeightRangePlacement.b(VerticalAnchor.b(-80), VerticalAnchor.b(80))));
      PlacementUtils.a(var0, D, var23, a(4, HeightRangePlacement.b(VerticalAnchor.b(-80), VerticalAnchor.b(80))));
      PlacementUtils.a(var0, E, var24, a(2, HeightRangePlacement.b(VerticalAnchor.a(-32), VerticalAnchor.a(32))));
      PlacementUtils.a(var0, F, var25, a(4, HeightRangePlacement.a(VerticalAnchor.a(), VerticalAnchor.a(64))));
      PlacementUtils.a(var0, G, var26, a(14, HeightRangePlacement.a(VerticalAnchor.a(), VerticalAnchor.a(63))));
      PlacementUtils.a(var0, H, var27, a(100, HeightRangePlacement.b(VerticalAnchor.a(-16), VerticalAnchor.a(480))));
      PlacementUtils.a(var0, I, var28, InSquarePlacement.a(), HeightRangePlacement.b(VerticalAnchor.a(8), VerticalAnchor.a(24)), BiomeFilter.a());
      PlacementUtils.a(var0, J, var29, InSquarePlacement.a(), PlacementUtils.g, BiomeFilter.a());
      PlacementUtils.a(var0, K, var30, a(16, HeightRangePlacement.b(VerticalAnchor.a(-16), VerticalAnchor.a(112))));
      PlacementUtils.a(var0, L, var31, a(16, HeightRangePlacement.b(VerticalAnchor.a(-16), VerticalAnchor.a(112))));
      PlacementUtils.a(var0, M, var32, a(46, PlacementUtils.i));
   }
}
