package net.minecraft.world.level.levelgen.synth;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;

public class NoiseGeneratorOctaves {
   private static final int a = 33554432;
   private final NoiseGeneratorPerlin[] b;
   private final int c;
   private final DoubleList d;
   private final double e;
   private final double f;
   private final double g;

   @Deprecated
   public static NoiseGeneratorOctaves a(RandomSource var0, IntStream var1) {
      return new NoiseGeneratorOctaves(var0, a(new IntRBTreeSet(var1.boxed().collect(ImmutableList.toImmutableList()))), false);
   }

   @Deprecated
   public static NoiseGeneratorOctaves a(RandomSource var0, int var1, DoubleList var2) {
      return new NoiseGeneratorOctaves(var0, Pair.of(var1, var2), false);
   }

   public static NoiseGeneratorOctaves b(RandomSource var0, IntStream var1) {
      return a(var0, var1.boxed().collect(ImmutableList.toImmutableList()));
   }

   public static NoiseGeneratorOctaves a(RandomSource var0, List<Integer> var1) {
      return new NoiseGeneratorOctaves(var0, a(new IntRBTreeSet(var1)), true);
   }

   public static NoiseGeneratorOctaves a(RandomSource var0, int var1, double var2, double... var4) {
      DoubleArrayList var5 = new DoubleArrayList(var4);
      var5.add(0, var2);
      return new NoiseGeneratorOctaves(var0, Pair.of(var1, var5), true);
   }

   public static NoiseGeneratorOctaves b(RandomSource var0, int var1, DoubleList var2) {
      return new NoiseGeneratorOctaves(var0, Pair.of(var1, var2), true);
   }

   private static Pair<Integer, DoubleList> a(IntSortedSet var0) {
      if (var0.isEmpty()) {
         throw new IllegalArgumentException("Need some octaves!");
      } else {
         int var1 = -var0.firstInt();
         int var2 = var0.lastInt();
         int var3 = var1 + var2 + 1;
         if (var3 < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
         } else {
            DoubleList var4 = new DoubleArrayList(new double[var3]);
            IntBidirectionalIterator var5 = var0.iterator();

            while(var5.hasNext()) {
               int var6 = var5.nextInt();
               var4.set(var6 + var1, 1.0);
            }

            return Pair.of(-var1, var4);
         }
      }
   }

   protected NoiseGeneratorOctaves(RandomSource var0, Pair<Integer, DoubleList> var1, boolean var2) {
      this.c = var1.getFirst();
      this.d = (DoubleList)var1.getSecond();
      int var3 = this.d.size();
      int var4 = -this.c;
      this.b = new NoiseGeneratorPerlin[var3];
      if (var2) {
         PositionalRandomFactory var5 = var0.e();

         for(int var6 = 0; var6 < var3; ++var6) {
            if (this.d.getDouble(var6) != 0.0) {
               int var7 = this.c + var6;
               this.b[var6] = new NoiseGeneratorPerlin(var5.a("octave_" + var7));
            }
         }
      } else {
         NoiseGeneratorPerlin var5 = new NoiseGeneratorPerlin(var0);
         if (var4 >= 0 && var4 < var3) {
            double var6 = this.d.getDouble(var4);
            if (var6 != 0.0) {
               this.b[var4] = var5;
            }
         }

         for(int var6 = var4 - 1; var6 >= 0; --var6) {
            if (var6 < var3) {
               double var7 = this.d.getDouble(var6);
               if (var7 != 0.0) {
                  this.b[var6] = new NoiseGeneratorPerlin(var0);
               } else {
                  a(var0);
               }
            } else {
               a(var0);
            }
         }

         if (Arrays.stream(this.b).filter(Objects::nonNull).count() != this.d.stream().filter(var0x -> var0x != 0.0).count()) {
            throw new IllegalStateException("Failed to create correct number of noise levels for given non-zero amplitudes");
         }

         if (var4 < var3 - 1) {
            throw new IllegalArgumentException("Positive octaves are temporarily disabled");
         }
      }

      this.f = Math.pow(2.0, (double)(-var4));
      this.e = Math.pow(2.0, (double)(var3 - 1)) / (Math.pow(2.0, (double)var3) - 1.0);
      this.g = this.c(2.0);
   }

   protected double a() {
      return this.g;
   }

   private static void a(RandomSource var0) {
      var0.b(262);
   }

   public double a(double var0, double var2, double var4) {
      return this.a(var0, var2, var4, 0.0, 0.0, false);
   }

   @Deprecated
   public double a(double var0, double var2, double var4, double var6, double var8, boolean var10) {
      double var11 = 0.0;
      double var13 = this.f;
      double var15 = this.e;

      for(int var17 = 0; var17 < this.b.length; ++var17) {
         NoiseGeneratorPerlin var18 = this.b[var17];
         if (var18 != null) {
            double var19 = var18.a(b(var0 * var13), var10 ? -var18.b : b(var2 * var13), b(var4 * var13), var6 * var13, var8 * var13);
            var11 += this.d.getDouble(var17) * var19 * var15;
         }

         var13 *= 2.0;
         var15 /= 2.0;
      }

      return var11;
   }

   public double a(double var0) {
      return this.c(var0 + 2.0);
   }

   private double c(double var0) {
      double var2 = 0.0;
      double var4 = this.e;

      for(int var6 = 0; var6 < this.b.length; ++var6) {
         NoiseGeneratorPerlin var7 = this.b[var6];
         if (var7 != null) {
            var2 += this.d.getDouble(var6) * var0 * var4;
         }

         var4 /= 2.0;
      }

      return var2;
   }

   @Nullable
   public NoiseGeneratorPerlin a(int var0) {
      return this.b[this.b.length - 1 - var0];
   }

   public static double b(double var0) {
      return var0 - (double)MathHelper.b(var0 / 3.3554432E7 + 0.5) * 3.3554432E7;
   }

   protected int b() {
      return this.c;
   }

   protected DoubleList c() {
      return this.d;
   }

   @VisibleForTesting
   public void a(StringBuilder var0) {
      var0.append("PerlinNoise{");
      List<String> var1 = this.d.stream().map(var0x -> String.format(Locale.ROOT, "%.2f", var0x)).toList();
      var0.append("first octave: ").append(this.c).append(", amplitudes: ").append(var1).append(", noise levels: [");

      for(int var2 = 0; var2 < this.b.length; ++var2) {
         var0.append(var2).append(": ");
         NoiseGeneratorPerlin var3 = this.b[var2];
         if (var3 == null) {
            var0.append("null");
         } else {
            var3.a(var0);
         }

         var0.append(", ");
      }

      var0.append("]");
      var0.append("}");
   }
}
