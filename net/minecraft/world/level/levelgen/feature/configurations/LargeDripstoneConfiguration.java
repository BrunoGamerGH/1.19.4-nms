package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;

public class LargeDripstoneConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<LargeDripstoneConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").orElse(30).forGetter(var0x -> var0x.b),
               IntProvider.b(1, 60).fieldOf("column_radius").forGetter(var0x -> var0x.c),
               FloatProvider.a(0.0F, 20.0F).fieldOf("height_scale").forGetter(var0x -> var0x.d),
               Codec.floatRange(0.1F, 1.0F).fieldOf("max_column_radius_to_cave_height_ratio").forGetter(var0x -> var0x.e),
               FloatProvider.a(0.1F, 10.0F).fieldOf("stalactite_bluntness").forGetter(var0x -> var0x.f),
               FloatProvider.a(0.1F, 10.0F).fieldOf("stalagmite_bluntness").forGetter(var0x -> var0x.g),
               FloatProvider.a(0.0F, 2.0F).fieldOf("wind_speed").forGetter(var0x -> var0x.h),
               Codec.intRange(0, 100).fieldOf("min_radius_for_wind").forGetter(var0x -> var0x.i),
               Codec.floatRange(0.0F, 5.0F).fieldOf("min_bluntness_for_wind").forGetter(var0x -> var0x.j)
            )
            .apply(var0, LargeDripstoneConfiguration::new)
   );
   public final int b;
   public final IntProvider c;
   public final FloatProvider d;
   public final float e;
   public final FloatProvider f;
   public final FloatProvider g;
   public final FloatProvider h;
   public final int i;
   public final float j;

   public LargeDripstoneConfiguration(
      int var0, IntProvider var1, FloatProvider var2, float var3, FloatProvider var4, FloatProvider var5, FloatProvider var6, int var7, float var8
   ) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = var5;
      this.h = var6;
      this.i = var7;
      this.j = var8;
   }
}
