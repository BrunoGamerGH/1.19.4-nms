package net.minecraft.data.worldgen.placement;

import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class PlacementUtils {
   public static final PlacementModifier a = HeightmapPlacement.a(HeightMap.Type.e);
   public static final PlacementModifier b = HeightmapPlacement.a(HeightMap.Type.c);
   public static final PlacementModifier c = HeightmapPlacement.a(HeightMap.Type.a);
   public static final PlacementModifier d = HeightmapPlacement.a(HeightMap.Type.d);
   public static final PlacementModifier e = HeightRangePlacement.a(VerticalAnchor.a(), VerticalAnchor.b());
   public static final PlacementModifier f = HeightRangePlacement.a(VerticalAnchor.b(10), VerticalAnchor.c(10));
   public static final PlacementModifier g = HeightRangePlacement.a(VerticalAnchor.b(8), VerticalAnchor.c(8));
   public static final PlacementModifier h = HeightRangePlacement.a(VerticalAnchor.b(4), VerticalAnchor.c(4));
   public static final PlacementModifier i = HeightRangePlacement.a(VerticalAnchor.a(), VerticalAnchor.a(256));

   public static void a(BootstapContext<PlacedFeature> var0) {
      AquaticPlacements.a(var0);
      CavePlacements.a(var0);
      EndPlacements.a(var0);
      MiscOverworldPlacements.a(var0);
      NetherPlacements.a(var0);
      OrePlacements.a(var0);
      TreePlacements.a(var0);
      VegetationPlacements.a(var0);
      VillagePlacements.a(var0);
   }

   public static ResourceKey<PlacedFeature> a(String var0) {
      return ResourceKey.a(Registries.aw, new MinecraftKey(var0));
   }

   public static void a(
      BootstapContext<PlacedFeature> var0, ResourceKey<PlacedFeature> var1, Holder<WorldGenFeatureConfigured<?, ?>> var2, List<PlacementModifier> var3
   ) {
      var0.a(var1, new PlacedFeature(var2, List.copyOf(var3)));
   }

   public static void a(
      BootstapContext<PlacedFeature> var0, ResourceKey<PlacedFeature> var1, Holder<WorldGenFeatureConfigured<?, ?>> var2, PlacementModifier... var3
   ) {
      a(var0, var1, var2, List.of(var3));
   }

   public static PlacementModifier a(int var0, float var1, int var2) {
      float var3 = 1.0F / var1;
      if (Math.abs(var3 - (float)((int)var3)) > 1.0E-5F) {
         throw new IllegalStateException("Chance data cannot be represented as list weight");
      } else {
         SimpleWeightedRandomList<IntProvider> var4 = SimpleWeightedRandomList.<IntProvider>a()
            .a(ConstantInt.a(var0), (int)var3 - 1)
            .a(ConstantInt.a(var0 + var2), 1)
            .a();
         return CountPlacement.a(new WeightedListInt(var4));
      }
   }

   public static PlacementFilter a() {
      return BlockPredicateFilter.a(BlockPredicate.c);
   }

   public static BlockPredicateFilter a(Block var0) {
      return BlockPredicateFilter.a(BlockPredicate.a(var0.o(), BlockPosition.b));
   }

   public static Holder<PlacedFeature> a(Holder<WorldGenFeatureConfigured<?, ?>> var0, PlacementModifier... var1) {
      return Holder.a(new PlacedFeature(var0, List.of(var1)));
   }

   public static <FC extends WorldGenFeatureConfiguration, F extends WorldGenerator<FC>> Holder<PlacedFeature> a(F var0, FC var1, PlacementModifier... var2) {
      return a(Holder.a(new WorldGenFeatureConfigured(var0, var1)), var2);
   }

   public static <FC extends WorldGenFeatureConfiguration, F extends WorldGenerator<FC>> Holder<PlacedFeature> a(F var0, FC var1) {
      return a(var0, var1, BlockPredicate.c);
   }

   public static <FC extends WorldGenFeatureConfiguration, F extends WorldGenerator<FC>> Holder<PlacedFeature> a(F var0, FC var1, BlockPredicate var2) {
      return a(var0, var1, BlockPredicateFilter.a(var2));
   }
}
