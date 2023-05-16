package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;

public class WorldGenFeatureEmptyConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureEmptyConfiguration> a = Codec.unit(() -> WorldGenFeatureEmptyConfiguration.b);
   public static final WorldGenFeatureEmptyConfiguration b = new WorldGenFeatureEmptyConfiguration();
}
