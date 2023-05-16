package net.minecraft.data.worldgen.features;

import net.minecraft.core.HolderSet;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenDecoratorFrequencyConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfigurationChance;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandom2;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;

public class AquaticFeatures {
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> a = FeatureUtils.a("seagrass_short");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> b = FeatureUtils.a("seagrass_slightly_less_short");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> c = FeatureUtils.a("seagrass_mid");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> d = FeatureUtils.a("seagrass_tall");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> e = FeatureUtils.a("sea_pickle");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> f = FeatureUtils.a("seagrass_simple");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> g = FeatureUtils.a("kelp");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> h = FeatureUtils.a("warm_ocean_vegetation");

   public static void a(BootstapContext<WorldGenFeatureConfigured<?, ?>> var0) {
      FeatureUtils.a(var0, a, WorldGenerator.N, new WorldGenFeatureConfigurationChance(0.3F));
      FeatureUtils.a(var0, b, WorldGenerator.N, new WorldGenFeatureConfigurationChance(0.4F));
      FeatureUtils.a(var0, c, WorldGenerator.N, new WorldGenFeatureConfigurationChance(0.6F));
      FeatureUtils.a(var0, d, WorldGenerator.N, new WorldGenFeatureConfigurationChance(0.8F));
      FeatureUtils.a(var0, e, WorldGenerator.S, new WorldGenDecoratorFrequencyConfiguration(20));
      FeatureUtils.a(var0, f, WorldGenerator.T, new WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider.a(Blocks.bv)));
      FeatureUtils.a(var0, g, WorldGenerator.O);
      FeatureUtils.a(
         var0,
         h,
         WorldGenerator.ah,
         new WorldGenFeatureRandom2(
            HolderSet.a(
               PlacementUtils.a(WorldGenerator.P, WorldGenFeatureConfiguration.m),
               PlacementUtils.a(WorldGenerator.R, WorldGenFeatureConfiguration.m),
               PlacementUtils.a(WorldGenerator.Q, WorldGenFeatureConfiguration.m)
            )
         )
      );
   }
}
