package net.minecraft.world.level.block.grower;

import javax.annotation.Nullable;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;

public class MangroveTreeGrower extends WorldGenTreeProvider {
   private final float a;

   public MangroveTreeGrower(float var0) {
      this.a = var0;
   }

   @Nullable
   @Override
   protected ResourceKey<WorldGenFeatureConfigured<?, ?>> a(RandomSource var0, boolean var1) {
      return var0.i() < this.a ? TreeFeatures.y : TreeFeatures.x;
   }
}
