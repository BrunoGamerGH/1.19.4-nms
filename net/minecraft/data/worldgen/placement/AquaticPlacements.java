package net.minecraft.data.worldgen.placement;

import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CarvingMaskPlacement;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseBasedCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class AquaticPlacements {
   public static final ResourceKey<PlacedFeature> a = PlacementUtils.a("seagrass_warm");
   public static final ResourceKey<PlacedFeature> b = PlacementUtils.a("seagrass_normal");
   public static final ResourceKey<PlacedFeature> c = PlacementUtils.a("seagrass_cold");
   public static final ResourceKey<PlacedFeature> d = PlacementUtils.a("seagrass_river");
   public static final ResourceKey<PlacedFeature> e = PlacementUtils.a("seagrass_swamp");
   public static final ResourceKey<PlacedFeature> f = PlacementUtils.a("seagrass_deep_warm");
   public static final ResourceKey<PlacedFeature> g = PlacementUtils.a("seagrass_deep");
   public static final ResourceKey<PlacedFeature> h = PlacementUtils.a("seagrass_deep_cold");
   public static final ResourceKey<PlacedFeature> i = PlacementUtils.a("seagrass_simple");
   public static final ResourceKey<PlacedFeature> j = PlacementUtils.a("sea_pickle");
   public static final ResourceKey<PlacedFeature> k = PlacementUtils.a("kelp_cold");
   public static final ResourceKey<PlacedFeature> l = PlacementUtils.a("kelp_warm");
   public static final ResourceKey<PlacedFeature> m = PlacementUtils.a("warm_ocean_vegetation");

   private static List<PlacementModifier> a(int var0) {
      return List.of(InSquarePlacement.a(), PlacementUtils.b, CountPlacement.a(var0), BiomeFilter.a());
   }

   public static void a(BootstapContext<PlacedFeature> var0) {
      HolderGetter<WorldGenFeatureConfigured<?, ?>> var1 = var0.a(Registries.aq);
      Holder.c<WorldGenFeatureConfigured<?, ?>> var2 = var1.b(AquaticFeatures.a);
      Holder.c<WorldGenFeatureConfigured<?, ?>> var3 = var1.b(AquaticFeatures.b);
      Holder.c<WorldGenFeatureConfigured<?, ?>> var4 = var1.b(AquaticFeatures.c);
      Holder.c<WorldGenFeatureConfigured<?, ?>> var5 = var1.b(AquaticFeatures.d);
      Holder.c<WorldGenFeatureConfigured<?, ?>> var6 = var1.b(AquaticFeatures.f);
      Holder.c<WorldGenFeatureConfigured<?, ?>> var7 = var1.b(AquaticFeatures.e);
      Holder.c<WorldGenFeatureConfigured<?, ?>> var8 = var1.b(AquaticFeatures.g);
      Holder.c<WorldGenFeatureConfigured<?, ?>> var9 = var1.b(AquaticFeatures.h);
      PlacementUtils.a(var0, a, var2, a(80));
      PlacementUtils.a(var0, b, var2, a(48));
      PlacementUtils.a(var0, c, var2, a(32));
      PlacementUtils.a(var0, d, var3, a(48));
      PlacementUtils.a(var0, e, var4, a(64));
      PlacementUtils.a(var0, f, var5, a(80));
      PlacementUtils.a(var0, g, var5, a(48));
      PlacementUtils.a(var0, h, var5, a(40));
      PlacementUtils.a(
         var0,
         i,
         var6,
         CarvingMaskPlacement.a(WorldGenStage.Features.b),
         RarityFilter.a(10),
         BlockPredicateFilter.a(
            BlockPredicate.a(
               BlockPredicate.a(EnumDirection.a.q(), Blocks.b), BlockPredicate.a(BlockPosition.b, Blocks.G), BlockPredicate.a(EnumDirection.b.q(), Blocks.G)
            )
         ),
         BiomeFilter.a()
      );
      PlacementUtils.a(var0, j, var7, RarityFilter.a(16), InSquarePlacement.a(), PlacementUtils.b, BiomeFilter.a());
      PlacementUtils.a(var0, k, var8, NoiseBasedCountPlacement.a(120, 80.0, 0.0), InSquarePlacement.a(), PlacementUtils.b, BiomeFilter.a());
      PlacementUtils.a(var0, l, var8, NoiseBasedCountPlacement.a(80, 80.0, 0.0), InSquarePlacement.a(), PlacementUtils.b, BiomeFilter.a());
      PlacementUtils.a(var0, m, var9, NoiseBasedCountPlacement.a(20, 400.0, 0.0), InSquarePlacement.a(), PlacementUtils.b, BiomeFilter.a());
   }
}
