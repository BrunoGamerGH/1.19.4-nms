package net.minecraft.data.worldgen.placement;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.EndFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public class EndPlacements {
   public static final ResourceKey<PlacedFeature> a = PlacementUtils.a("end_spike");
   public static final ResourceKey<PlacedFeature> b = PlacementUtils.a("end_gateway_return");
   public static final ResourceKey<PlacedFeature> c = PlacementUtils.a("chorus_plant");
   public static final ResourceKey<PlacedFeature> d = PlacementUtils.a("end_island_decorated");

   public static void a(BootstapContext<PlacedFeature> var0) {
      HolderGetter<WorldGenFeatureConfigured<?, ?>> var1 = var0.a(Registries.aq);
      Holder<WorldGenFeatureConfigured<?, ?>> var2 = var1.b(EndFeatures.a);
      Holder<WorldGenFeatureConfigured<?, ?>> var3 = var1.b(EndFeatures.b);
      Holder<WorldGenFeatureConfigured<?, ?>> var4 = var1.b(EndFeatures.d);
      Holder<WorldGenFeatureConfigured<?, ?>> var5 = var1.b(EndFeatures.e);
      PlacementUtils.a(var0, a, var2, BiomeFilter.a());
      PlacementUtils.a(
         var0, b, var3, RarityFilter.a(700), InSquarePlacement.a(), PlacementUtils.a, RandomOffsetPlacement.a(UniformInt.a(3, 9)), BiomeFilter.a()
      );
      PlacementUtils.a(var0, c, var4, CountPlacement.a(UniformInt.a(0, 4)), InSquarePlacement.a(), PlacementUtils.a, BiomeFilter.a());
      PlacementUtils.a(
         var0,
         d,
         var5,
         RarityFilter.a(14),
         PlacementUtils.a(1, 0.25F, 1),
         InSquarePlacement.a(),
         HeightRangePlacement.a(VerticalAnchor.a(55), VerticalAnchor.a(70)),
         BiomeFilter.a()
      );
   }
}
