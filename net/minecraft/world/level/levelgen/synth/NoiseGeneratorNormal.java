package net.minecraft.world.level.levelgen.synth;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import java.util.List;
import net.minecraft.SystemUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.util.RandomSource;

public class NoiseGeneratorNormal {
   private static final double a = 1.0181268882175227;
   private static final double b = 0.3333333333333333;
   private final double c;
   private final NoiseGeneratorOctaves d;
   private final NoiseGeneratorOctaves e;
   private final double f;
   private final NoiseGeneratorNormal.a g;

   @Deprecated
   public static NoiseGeneratorNormal a(RandomSource var0, NoiseGeneratorNormal.a var1) {
      return new NoiseGeneratorNormal(var0, var1, false);
   }

   public static NoiseGeneratorNormal a(RandomSource var0, int var1, double... var2) {
      return b(var0, new NoiseGeneratorNormal.a(var1, new DoubleArrayList(var2)));
   }

   public static NoiseGeneratorNormal b(RandomSource var0, NoiseGeneratorNormal.a var1) {
      return new NoiseGeneratorNormal(var0, var1, true);
   }

   private NoiseGeneratorNormal(RandomSource var0, NoiseGeneratorNormal.a var1, boolean var2) {
      int var3 = var1.c;
      DoubleList var4 = var1.d;
      this.g = var1;
      if (var2) {
         this.d = NoiseGeneratorOctaves.b(var0, var3, var4);
         this.e = NoiseGeneratorOctaves.b(var0, var3, var4);
      } else {
         this.d = NoiseGeneratorOctaves.a(var0, var3, var4);
         this.e = NoiseGeneratorOctaves.a(var0, var3, var4);
      }

      int var5 = Integer.MAX_VALUE;
      int var6 = Integer.MIN_VALUE;
      DoubleListIterator var7 = var4.iterator();

      while(var7.hasNext()) {
         int var8 = var7.nextIndex();
         double var9 = var7.nextDouble();
         if (var9 != 0.0) {
            var5 = Math.min(var5, var8);
            var6 = Math.max(var6, var8);
         }
      }

      this.c = 0.16666666666666666 / a(var6 - var5);
      this.f = (this.d.a() + this.e.a()) * this.c;
   }

   public double a() {
      return this.f;
   }

   private static double a(int var0) {
      return 0.1 * (1.0 + 1.0 / (double)(var0 + 1));
   }

   public double a(double var0, double var2, double var4) {
      double var6 = var0 * 1.0181268882175227;
      double var8 = var2 * 1.0181268882175227;
      double var10 = var4 * 1.0181268882175227;
      return (this.d.a(var0, var2, var4) + this.e.a(var6, var8, var10)) * this.c;
   }

   public NoiseGeneratorNormal.a b() {
      return this.g;
   }

   @VisibleForTesting
   public void a(StringBuilder var0) {
      var0.append("NormalNoise {");
      var0.append("first: ");
      this.d.a(var0);
      var0.append(", second: ");
      this.e.a(var0);
      var0.append("}");
   }

   public static record a(int firstOctave, DoubleList amplitudes) {
      final int c;
      final DoubleList d;
      public static final Codec<NoiseGeneratorNormal.a> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.INT.fieldOf("firstOctave").forGetter(NoiseGeneratorNormal.a::a),
                  Codec.DOUBLE.listOf().fieldOf("amplitudes").forGetter(NoiseGeneratorNormal.a::b)
               )
               .apply(var0, NoiseGeneratorNormal.a::new)
      );
      public static final Codec<Holder<NoiseGeneratorNormal.a>> b = RegistryFileCodec.a(Registries.av, a);

      public a(int var0, List<Double> var1) {
         this(var0, new DoubleArrayList(var1));
      }

      public a(int var0, double var1, double... var3) {
         this(var0, SystemUtils.a(new DoubleArrayList(var3), var2x -> var2x.add(0, var1)));
      }

      public a(int var0, DoubleList var1) {
         this.c = var0;
         this.d = var1;
      }

      public int a() {
         return this.c;
      }

      public DoubleList b() {
         return this.d;
      }
   }
}
