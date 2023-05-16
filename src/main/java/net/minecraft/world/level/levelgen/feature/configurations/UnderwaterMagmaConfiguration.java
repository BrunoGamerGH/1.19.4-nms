package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class UnderwaterMagmaConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<UnderwaterMagmaConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.intRange(0, 512).fieldOf("floor_search_range").forGetter(var0x -> var0x.b),
               Codec.intRange(0, 64).fieldOf("placement_radius_around_floor").forGetter(var0x -> var0x.c),
               Codec.floatRange(0.0F, 1.0F).fieldOf("placement_probability_per_valid_position").forGetter(var0x -> var0x.d)
            )
            .apply(var0, UnderwaterMagmaConfiguration::new)
   );
   public final int b;
   public final int c;
   public final float d;

   public UnderwaterMagmaConfiguration(int var0, int var1, float var2) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
   }
}
