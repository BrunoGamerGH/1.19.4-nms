package net.minecraft.world.level.levelgen.feature.configurations;

import java.util.stream.Stream;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;

public interface WorldGenFeatureConfiguration {
   WorldGenFeatureEmptyConfiguration m = WorldGenFeatureEmptyConfiguration.b;

   default Stream<WorldGenFeatureConfigured<?, ?>> e() {
      return Stream.empty();
   }
}
