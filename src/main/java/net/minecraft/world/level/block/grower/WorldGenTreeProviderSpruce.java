package net.minecraft.world.level.block.grower;

import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;

public class WorldGenTreeProviderSpruce extends WorldGenMegaTreeProvider {
   @Override
   protected ResourceKey<WorldGenFeatureConfigured<?, ?>> a(RandomSource var0, boolean var1) {
      return TreeFeatures.k;
   }

   @Override
   protected ResourceKey<WorldGenFeatureConfigured<?, ?>> a(RandomSource var0) {
      return var0.h() ? TreeFeatures.q : TreeFeatures.r;
   }
}
