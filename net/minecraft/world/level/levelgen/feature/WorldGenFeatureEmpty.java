package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;

public class WorldGenFeatureEmpty extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
   public WorldGenFeatureEmpty(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureEmptyConfiguration> var0) {
      return true;
   }
}
