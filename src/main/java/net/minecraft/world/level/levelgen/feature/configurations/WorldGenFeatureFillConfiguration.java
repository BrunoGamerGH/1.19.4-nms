package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.dimension.DimensionManager;

public class WorldGenFeatureFillConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureFillConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.intRange(0, DimensionManager.c).fieldOf("height").forGetter(var0x -> var0x.b), IBlockData.b.fieldOf("state").forGetter(var0x -> var0x.c)
            )
            .apply(var0, WorldGenFeatureFillConfiguration::new)
   );
   public final int b;
   public final IBlockData c;

   public WorldGenFeatureFillConfiguration(int var0, IBlockData var1) {
      this.b = var0;
      this.c = var1;
   }
}
