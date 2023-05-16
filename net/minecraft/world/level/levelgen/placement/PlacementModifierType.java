package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public interface PlacementModifierType<P extends PlacementModifier> {
   PlacementModifierType<BlockPredicateFilter> a = a("block_predicate_filter", BlockPredicateFilter.a);
   PlacementModifierType<RarityFilter> b = a("rarity_filter", RarityFilter.a);
   PlacementModifierType<SurfaceRelativeThresholdFilter> c = a("surface_relative_threshold_filter", SurfaceRelativeThresholdFilter.a);
   PlacementModifierType<SurfaceWaterDepthFilter> d = a("surface_water_depth_filter", SurfaceWaterDepthFilter.a);
   PlacementModifierType<BiomeFilter> e = a("biome", BiomeFilter.a);
   PlacementModifierType<CountPlacement> f = a("count", CountPlacement.a);
   PlacementModifierType<NoiseBasedCountPlacement> g = a("noise_based_count", NoiseBasedCountPlacement.a);
   PlacementModifierType<NoiseThresholdCountPlacement> h = a("noise_threshold_count", NoiseThresholdCountPlacement.a);
   PlacementModifierType<CountOnEveryLayerPlacement> i = a("count_on_every_layer", CountOnEveryLayerPlacement.a);
   PlacementModifierType<EnvironmentScanPlacement> j = a("environment_scan", EnvironmentScanPlacement.a);
   PlacementModifierType<HeightmapPlacement> k = a("heightmap", HeightmapPlacement.a);
   PlacementModifierType<HeightRangePlacement> l = a("height_range", HeightRangePlacement.a);
   PlacementModifierType<InSquarePlacement> m = a("in_square", InSquarePlacement.a);
   PlacementModifierType<RandomOffsetPlacement> n = a("random_offset", RandomOffsetPlacement.a);
   PlacementModifierType<CarvingMaskPlacement> o = a("carving_mask", CarvingMaskPlacement.a);

   Codec<P> codec();

   private static <P extends PlacementModifier> PlacementModifierType<P> a(String var0, Codec<P> var1) {
      return IRegistry.a(BuiltInRegistries.U, var0, () -> var1);
   }
}
