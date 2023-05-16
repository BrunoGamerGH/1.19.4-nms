package net.minecraft.data.worldgen.features;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureBlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProviderRotatedBlock;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProviderWeighted;

public class PileFeatures {
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> a = FeatureUtils.a("pile_hay");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> b = FeatureUtils.a("pile_melon");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> c = FeatureUtils.a("pile_snow");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> d = FeatureUtils.a("pile_ice");
   public static final ResourceKey<WorldGenFeatureConfigured<?, ?>> e = FeatureUtils.a("pile_pumpkin");

   public static void a(BootstapContext<WorldGenFeatureConfigured<?, ?>> var0) {
      FeatureUtils.a(var0, a, WorldGenerator.k, new WorldGenFeatureBlockPileConfiguration(new WorldGenFeatureStateProviderRotatedBlock(Blocks.ii)));
      FeatureUtils.a(var0, b, WorldGenerator.k, new WorldGenFeatureBlockPileConfiguration(WorldGenFeatureStateProvider.a(Blocks.eZ)));
      FeatureUtils.a(var0, c, WorldGenerator.k, new WorldGenFeatureBlockPileConfiguration(WorldGenFeatureStateProvider.a(Blocks.dM)));
      FeatureUtils.a(
         var0,
         d,
         WorldGenerator.k,
         new WorldGenFeatureBlockPileConfiguration(
            new WorldGenFeatureStateProviderWeighted(SimpleWeightedRandomList.<IBlockData>a().a(Blocks.mS.o(), 1).a(Blocks.iB.o(), 5))
         )
      );
      FeatureUtils.a(
         var0,
         e,
         WorldGenerator.k,
         new WorldGenFeatureBlockPileConfiguration(
            new WorldGenFeatureStateProviderWeighted(SimpleWeightedRandomList.<IBlockData>a().a(Blocks.dU.o(), 19).a(Blocks.ef.o(), 1))
         )
      );
   }
}
