package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;

public class WorldGenFeatureBlockPileConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureBlockPileConfiguration> a = WorldGenFeatureStateProvider.a
      .fieldOf("state_provider")
      .xmap(WorldGenFeatureBlockPileConfiguration::new, var0 -> var0.b)
      .codec();
   public final WorldGenFeatureStateProvider b;

   public WorldGenFeatureBlockPileConfiguration(WorldGenFeatureStateProvider var0) {
      this.b = var0;
   }
}
