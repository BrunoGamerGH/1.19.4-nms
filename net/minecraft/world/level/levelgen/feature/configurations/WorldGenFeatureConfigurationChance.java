package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class WorldGenFeatureConfigurationChance implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureConfigurationChance> k = RecordCodecBuilder.create(
      var0 -> var0.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(var0x -> var0x.l)).apply(var0, WorldGenFeatureConfigurationChance::new)
   );
   public final float l;

   public WorldGenFeatureConfigurationChance(float var0) {
      this.l = var0;
   }
}
