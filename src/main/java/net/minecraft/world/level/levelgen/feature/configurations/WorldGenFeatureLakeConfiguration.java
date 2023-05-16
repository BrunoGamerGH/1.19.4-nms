package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.IBlockData;

public class WorldGenFeatureLakeConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureLakeConfiguration> a = IBlockData.b
      .fieldOf("state")
      .xmap(WorldGenFeatureLakeConfiguration::new, var0 -> var0.b)
      .codec();
   public final IBlockData b;

   public WorldGenFeatureLakeConfiguration(IBlockData var0) {
      this.b = var0;
   }
}
