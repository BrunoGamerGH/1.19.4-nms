package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;

public class DripstoneClusterConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<DripstoneClusterConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").forGetter(var0x -> var0x.b),
               IntProvider.b(1, 128).fieldOf("height").forGetter(var0x -> var0x.c),
               IntProvider.b(1, 128).fieldOf("radius").forGetter(var0x -> var0x.d),
               Codec.intRange(0, 64).fieldOf("max_stalagmite_stalactite_height_diff").forGetter(var0x -> var0x.e),
               Codec.intRange(1, 64).fieldOf("height_deviation").forGetter(var0x -> var0x.f),
               IntProvider.b(0, 128).fieldOf("dripstone_block_layer_thickness").forGetter(var0x -> var0x.g),
               FloatProvider.a(0.0F, 2.0F).fieldOf("density").forGetter(var0x -> var0x.h),
               FloatProvider.a(0.0F, 2.0F).fieldOf("wetness").forGetter(var0x -> var0x.i),
               Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_dripstone_column_at_max_distance_from_center").forGetter(var0x -> var0x.j),
               Codec.intRange(1, 64).fieldOf("max_distance_from_edge_affecting_chance_of_dripstone_column").forGetter(var0x -> var0x.k),
               Codec.intRange(1, 64).fieldOf("max_distance_from_center_affecting_height_bias").forGetter(var0x -> var0x.l)
            )
            .apply(var0, DripstoneClusterConfiguration::new)
   );
   public final int b;
   public final IntProvider c;
   public final IntProvider d;
   public final int e;
   public final int f;
   public final IntProvider g;
   public final FloatProvider h;
   public final FloatProvider i;
   public final float j;
   public final int k;
   public final int l;

   public DripstoneClusterConfiguration(
      int var0,
      IntProvider var1,
      IntProvider var2,
      int var3,
      int var4,
      IntProvider var5,
      FloatProvider var6,
      FloatProvider var7,
      float var8,
      int var9,
      int var10
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
      this.k = var9;
      this.l = var10;
   }
}
