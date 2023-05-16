package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;

public class GeodeConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<Double> a = Codec.doubleRange(0.0, 1.0);
   public static final Codec<GeodeConfiguration> b = RecordCodecBuilder.create(
      var0 -> var0.group(
               GeodeBlockSettings.i.fieldOf("blocks").forGetter(var0x -> var0x.c),
               GeodeLayerSettings.a.fieldOf("layers").forGetter(var0x -> var0x.d),
               GeodeCrackSettings.a.fieldOf("crack").forGetter(var0x -> var0x.e),
               a.fieldOf("use_potential_placements_chance").orElse(0.35).forGetter(var0x -> var0x.f),
               a.fieldOf("use_alternate_layer0_chance").orElse(0.0).forGetter(var0x -> var0x.g),
               Codec.BOOL.fieldOf("placements_require_layer0_alternate").orElse(true).forGetter(var0x -> var0x.h),
               IntProvider.b(1, 20).fieldOf("outer_wall_distance").orElse(UniformInt.a(4, 5)).forGetter(var0x -> var0x.i),
               IntProvider.b(1, 20).fieldOf("distribution_points").orElse(UniformInt.a(3, 4)).forGetter(var0x -> var0x.j),
               IntProvider.b(0, 10).fieldOf("point_offset").orElse(UniformInt.a(1, 2)).forGetter(var0x -> var0x.k),
               Codec.INT.fieldOf("min_gen_offset").orElse(-16).forGetter(var0x -> var0x.l),
               Codec.INT.fieldOf("max_gen_offset").orElse(16).forGetter(var0x -> var0x.n),
               a.fieldOf("noise_multiplier").orElse(0.05).forGetter(var0x -> var0x.o),
               Codec.INT.fieldOf("invalid_blocks_threshold").forGetter(var0x -> var0x.p)
            )
            .apply(var0, GeodeConfiguration::new)
   );
   public final GeodeBlockSettings c;
   public final GeodeLayerSettings d;
   public final GeodeCrackSettings e;
   public final double f;
   public final double g;
   public final boolean h;
   public final IntProvider i;
   public final IntProvider j;
   public final IntProvider k;
   public final int l;
   public final int n;
   public final double o;
   public final int p;

   public GeodeConfiguration(
      GeodeBlockSettings var0,
      GeodeLayerSettings var1,
      GeodeCrackSettings var2,
      double var3,
      double var5,
      boolean var7,
      IntProvider var8,
      IntProvider var9,
      IntProvider var10,
      int var11,
      int var12,
      double var13,
      int var15
   ) {
      this.c = var0;
      this.d = var1;
      this.e = var2;
      this.f = var3;
      this.g = var5;
      this.h = var7;
      this.i = var8;
      this.j = var9;
      this.k = var10;
      this.l = var11;
      this.n = var12;
      this.o = var13;
      this.p = var15;
   }
}
