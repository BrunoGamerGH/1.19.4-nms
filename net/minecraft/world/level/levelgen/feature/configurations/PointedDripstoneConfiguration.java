package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class PointedDripstoneConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<PointedDripstoneConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_taller_dripstone").orElse(0.2F).forGetter(var0x -> var0x.b),
               Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_directional_spread").orElse(0.7F).forGetter(var0x -> var0x.c),
               Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius2").orElse(0.5F).forGetter(var0x -> var0x.d),
               Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spread_radius3").orElse(0.5F).forGetter(var0x -> var0x.e)
            )
            .apply(var0, PointedDripstoneConfiguration::new)
   );
   public final float b;
   public final float c;
   public final float d;
   public final float e;

   public PointedDripstoneConfiguration(float var0, float var1, float var2, float var3) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
   }
}
