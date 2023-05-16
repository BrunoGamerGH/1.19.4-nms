package net.minecraft.world.level.block.grower;

import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;

public class WorldGenTreeProviderOak extends WorldGenTreeProvider {
   @Override
   protected ResourceKey<WorldGenFeatureConfigured<?, ?>> a(RandomSource var0, boolean var1) {
      if (var0.a(10) == 0) {
         return var1 ? TreeFeatures.I : TreeFeatures.n;
      } else {
         return var1 ? TreeFeatures.C : TreeFeatures.g;
      }
   }
}
