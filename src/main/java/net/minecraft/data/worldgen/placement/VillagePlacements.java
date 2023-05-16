package net.minecraft.data.worldgen.placement;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.PileFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class VillagePlacements {
   public static final ResourceKey<PlacedFeature> a = PlacementUtils.a("pile_hay");
   public static final ResourceKey<PlacedFeature> b = PlacementUtils.a("pile_melon");
   public static final ResourceKey<PlacedFeature> c = PlacementUtils.a("pile_snow");
   public static final ResourceKey<PlacedFeature> d = PlacementUtils.a("pile_ice");
   public static final ResourceKey<PlacedFeature> e = PlacementUtils.a("pile_pumpkin");
   public static final ResourceKey<PlacedFeature> f = PlacementUtils.a("oak");
   public static final ResourceKey<PlacedFeature> g = PlacementUtils.a("acacia");
   public static final ResourceKey<PlacedFeature> h = PlacementUtils.a("spruce");
   public static final ResourceKey<PlacedFeature> i = PlacementUtils.a("pine");
   public static final ResourceKey<PlacedFeature> j = PlacementUtils.a("patch_cactus");
   public static final ResourceKey<PlacedFeature> k = PlacementUtils.a("flower_plain");
   public static final ResourceKey<PlacedFeature> l = PlacementUtils.a("patch_taiga_grass");
   public static final ResourceKey<PlacedFeature> m = PlacementUtils.a("patch_berry_bush");

   public static void a(BootstapContext<PlacedFeature> var0) {
      HolderGetter<WorldGenFeatureConfigured<?, ?>> var1 = var0.a(Registries.aq);
      Holder<WorldGenFeatureConfigured<?, ?>> var2 = var1.b(PileFeatures.a);
      Holder<WorldGenFeatureConfigured<?, ?>> var3 = var1.b(PileFeatures.b);
      Holder<WorldGenFeatureConfigured<?, ?>> var4 = var1.b(PileFeatures.c);
      Holder<WorldGenFeatureConfigured<?, ?>> var5 = var1.b(PileFeatures.d);
      Holder<WorldGenFeatureConfigured<?, ?>> var6 = var1.b(PileFeatures.e);
      Holder<WorldGenFeatureConfigured<?, ?>> var7 = var1.b(TreeFeatures.g);
      Holder<WorldGenFeatureConfigured<?, ?>> var8 = var1.b(TreeFeatures.j);
      Holder<WorldGenFeatureConfigured<?, ?>> var9 = var1.b(TreeFeatures.k);
      Holder<WorldGenFeatureConfigured<?, ?>> var10 = var1.b(TreeFeatures.l);
      Holder<WorldGenFeatureConfigured<?, ?>> var11 = var1.b(VegetationFeatures.r);
      Holder<WorldGenFeatureConfigured<?, ?>> var12 = var1.b(VegetationFeatures.w);
      Holder<WorldGenFeatureConfigured<?, ?>> var13 = var1.b(VegetationFeatures.i);
      Holder<WorldGenFeatureConfigured<?, ?>> var14 = var1.b(VegetationFeatures.h);
      PlacementUtils.a(var0, a, var2);
      PlacementUtils.a(var0, b, var3);
      PlacementUtils.a(var0, c, var4);
      PlacementUtils.a(var0, d, var5);
      PlacementUtils.a(var0, e, var6);
      PlacementUtils.a(var0, f, var7, PlacementUtils.a(Blocks.x));
      PlacementUtils.a(var0, g, var8, PlacementUtils.a(Blocks.B));
      PlacementUtils.a(var0, h, var9, PlacementUtils.a(Blocks.y));
      PlacementUtils.a(var0, i, var10, PlacementUtils.a(Blocks.y));
      PlacementUtils.a(var0, j, var11);
      PlacementUtils.a(var0, k, var12);
      PlacementUtils.a(var0, l, var13);
      PlacementUtils.a(var0, m, var14);
   }
}
