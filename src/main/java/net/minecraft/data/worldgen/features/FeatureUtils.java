package net.minecraft.data.worldgen.features;

import java.util.List;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class FeatureUtils {
   public static void a(BootstapContext<WorldGenFeatureConfigured<?, ?>> var0) {
      AquaticFeatures.a(var0);
      CaveFeatures.a(var0);
      EndFeatures.a(var0);
      MiscOverworldFeatures.a(var0);
      NetherFeatures.a(var0);
      OreFeatures.a(var0);
      PileFeatures.a(var0);
      TreeFeatures.a(var0);
      VegetationFeatures.a(var0);
   }

   private static BlockPredicate a(List<Block> var0) {
      BlockPredicate var1;
      if (!var0.isEmpty()) {
         var1 = BlockPredicate.a(BlockPredicate.c, BlockPredicate.a(EnumDirection.a.q(), var0));
      } else {
         var1 = BlockPredicate.c;
      }

      return var1;
   }

   public static WorldGenFeatureRandomPatchConfiguration a(int var0, Holder<PlacedFeature> var1) {
      return new WorldGenFeatureRandomPatchConfiguration(var0, 7, 3, var1);
   }

   public static <FC extends WorldGenFeatureConfiguration, F extends WorldGenerator<FC>> WorldGenFeatureRandomPatchConfiguration a(
      F var0, FC var1, List<Block> var2, int var3
   ) {
      return a(var3, PlacementUtils.a(var0, var1, a(var2)));
   }

   public static <FC extends WorldGenFeatureConfiguration, F extends WorldGenerator<FC>> WorldGenFeatureRandomPatchConfiguration a(
      F var0, FC var1, List<Block> var2
   ) {
      return a(var0, var1, var2, 96);
   }

   public static <FC extends WorldGenFeatureConfiguration, F extends WorldGenerator<FC>> WorldGenFeatureRandomPatchConfiguration a(F var0, FC var1) {
      return a(var0, var1, List.of(), 96);
   }

   public static ResourceKey<WorldGenFeatureConfigured<?, ?>> a(String var0) {
      return ResourceKey.a(Registries.aq, new MinecraftKey(var0));
   }

   public static void a(
      BootstapContext<WorldGenFeatureConfigured<?, ?>> var0,
      ResourceKey<WorldGenFeatureConfigured<?, ?>> var1,
      WorldGenerator<WorldGenFeatureEmptyConfiguration> var2
   ) {
      a(var0, var1, var2, WorldGenFeatureConfiguration.m);
   }

   public static <FC extends WorldGenFeatureConfiguration, F extends WorldGenerator<FC>> void a(
      BootstapContext<WorldGenFeatureConfigured<?, ?>> var0, ResourceKey<WorldGenFeatureConfigured<?, ?>> var1, F var2, FC var3
   ) {
      var0.a(var1, new WorldGenFeatureConfigured(var2, var3));
   }
}
