package net.minecraft.world.level.levelgen.synth;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import java.util.stream.IntStream;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;

public class BlendedNoise implements DensityFunction.d {
   private static final Codec<Double> e = Codec.doubleRange(0.001, 1000.0);
   private static final MapCodec<BlendedNoise> f = RecordCodecBuilder.mapCodec(
      var0 -> var0.group(
               e.fieldOf("xz_scale").forGetter(var0x -> var0x.p),
               e.fieldOf("y_scale").forGetter(var0x -> var0x.q),
               e.fieldOf("xz_factor").forGetter(var0x -> var0x.l),
               e.fieldOf("y_factor").forGetter(var0x -> var0x.m),
               Codec.doubleRange(1.0, 8.0).fieldOf("smear_scale_multiplier").forGetter(var0x -> var0x.n)
            )
            .apply(var0, BlendedNoise::a)
   );
   public static final KeyDispatchDataCodec<BlendedNoise> a = KeyDispatchDataCodec.a(f);
   private final NoiseGeneratorOctaves g;
   private final NoiseGeneratorOctaves h;
   private final NoiseGeneratorOctaves i;
   private final double j;
   private final double k;
   private final double l;
   private final double m;
   private final double n;
   private final double o;
   private final double p;
   private final double q;

   public static BlendedNoise a(double var0, double var2, double var4, double var6, double var8) {
      return new BlendedNoise(new XoroshiroRandomSource(0L), var0, var2, var4, var6, var8);
   }

   private BlendedNoise(
      NoiseGeneratorOctaves var0, NoiseGeneratorOctaves var1, NoiseGeneratorOctaves var2, double var3, double var5, double var7, double var9, double var11
   ) {
      this.g = var0;
      this.h = var1;
      this.i = var2;
      this.p = var3;
      this.q = var5;
      this.l = var7;
      this.m = var9;
      this.n = var11;
      this.j = 684.412 * this.p;
      this.k = 684.412 * this.q;
      this.o = var0.a(this.k);
   }

   @VisibleForTesting
   public BlendedNoise(RandomSource var0, double var1, double var3, double var5, double var7, double var9) {
      this(
         NoiseGeneratorOctaves.a(var0, IntStream.rangeClosed(-15, 0)),
         NoiseGeneratorOctaves.a(var0, IntStream.rangeClosed(-15, 0)),
         NoiseGeneratorOctaves.a(var0, IntStream.rangeClosed(-7, 0)),
         var1,
         var3,
         var5,
         var7,
         var9
      );
   }

   public BlendedNoise a(RandomSource var0) {
      return new BlendedNoise(var0, this.p, this.q, this.l, this.m, this.n);
   }

   @Override
   public double a(DensityFunction.b var0) {
      double var1 = (double)var0.a() * this.j;
      double var3 = (double)var0.b() * this.k;
      double var5 = (double)var0.c() * this.j;
      double var7 = var1 / this.l;
      double var9 = var3 / this.m;
      double var11 = var5 / this.l;
      double var13 = this.k * this.n;
      double var15 = var13 / this.m;
      double var17 = 0.0;
      double var19 = 0.0;
      double var21 = 0.0;
      boolean var23 = true;
      double var24 = 1.0;

      for(int var26 = 0; var26 < 8; ++var26) {
         NoiseGeneratorPerlin var27 = this.i.a(var26);
         if (var27 != null) {
            var21 += var27.a(
                  NoiseGeneratorOctaves.b(var7 * var24),
                  NoiseGeneratorOctaves.b(var9 * var24),
                  NoiseGeneratorOctaves.b(var11 * var24),
                  var15 * var24,
                  var9 * var24
               )
               / var24;
         }

         var24 /= 2.0;
      }

      double var26 = (var21 / 10.0 + 1.0) / 2.0;
      boolean var28 = var26 >= 1.0;
      boolean var29 = var26 <= 0.0;
      var24 = 1.0;

      for(int var30 = 0; var30 < 16; ++var30) {
         double var31 = NoiseGeneratorOctaves.b(var1 * var24);
         double var33 = NoiseGeneratorOctaves.b(var3 * var24);
         double var35 = NoiseGeneratorOctaves.b(var5 * var24);
         double var37 = var13 * var24;
         if (!var28) {
            NoiseGeneratorPerlin var39 = this.g.a(var30);
            if (var39 != null) {
               var17 += var39.a(var31, var33, var35, var37, var3 * var24) / var24;
            }
         }

         if (!var29) {
            NoiseGeneratorPerlin var39 = this.h.a(var30);
            if (var39 != null) {
               var19 += var39.a(var31, var33, var35, var37, var3 * var24) / var24;
            }
         }

         var24 /= 2.0;
      }

      return MathHelper.b(var17 / 512.0, var19 / 512.0, var26) / 128.0;
   }

   @Override
   public double a() {
      return -this.b();
   }

   @Override
   public double b() {
      return this.o;
   }

   @VisibleForTesting
   public void a(StringBuilder var0) {
      var0.append("BlendedNoise{minLimitNoise=");
      this.g.a(var0);
      var0.append(", maxLimitNoise=");
      this.h.a(var0);
      var0.append(", mainNoise=");
      this.i.a(var0);
      var0.append(
            String.format(
               Locale.ROOT,
               ", xzScale=%.3f, yScale=%.3f, xzMainScale=%.3f, yMainScale=%.3f, cellWidth=4, cellHeight=8",
               684.412,
               684.412,
               8.555150000000001,
               4.277575000000001
            )
         )
         .append('}');
   }

   @Override
   public KeyDispatchDataCodec<? extends DensityFunction> c() {
      return a;
   }
}
