package net.minecraft.util;

import java.util.Locale;
import java.util.UUID;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import net.minecraft.SystemUtils;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.Vec3D;
import org.apache.commons.lang3.math.NumberUtils;

public class MathHelper {
   private static final long h = 61440L;
   private static final long i = 16384L;
   private static final long j = -4611686018427387904L;
   private static final long k = Long.MIN_VALUE;
   public static final float a = (float) Math.PI;
   public static final float b = (float) (Math.PI / 2);
   public static final float c = (float) (Math.PI * 2);
   public static final float d = (float) (Math.PI / 180.0);
   public static final float e = 180.0F / (float)Math.PI;
   public static final float f = 1.0E-5F;
   public static final float g = c(2.0F);
   private static final float l = 10430.378F;
   private static final float[] m = SystemUtils.a(new float[65536], var0x -> {
      for(int var1x = 0; var1x < var0x.length; ++var1x) {
         var0x[var1x] = (float)Math.sin((double)var1x * Math.PI * 2.0 / 65536.0);
      }
   });
   private static final RandomSource n = RandomSource.b();
   private static final int[] o = new int[]{
      0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9
   };
   private static final double p = 0.16666666666666666;
   private static final int q = 8;
   private static final int r = 257;
   private static final double s = Double.longBitsToDouble(4805340802404319232L);
   private static final double[] t = new double[257];
   private static final double[] u = new double[257];

   public static float a(float var0) {
      return m[(int)(var0 * 10430.378F) & 65535];
   }

   public static float b(float var0) {
      return m[(int)(var0 * 10430.378F + 16384.0F) & 65535];
   }

   public static float c(float var0) {
      return (float)Math.sqrt((double)var0);
   }

   public static int d(float var0) {
      int var1 = (int)var0;
      return var0 < (float)var1 ? var1 - 1 : var1;
   }

   public static int a(double var0) {
      int var2 = (int)var0;
      return var0 < (double)var2 ? var2 - 1 : var2;
   }

   public static long b(double var0) {
      long var2 = (long)var0;
      return var0 < (double)var2 ? var2 - 1L : var2;
   }

   public static float e(float var0) {
      return Math.abs(var0);
   }

   public static int a(int var0) {
      return Math.abs(var0);
   }

   public static int f(float var0) {
      int var1 = (int)var0;
      return var0 > (float)var1 ? var1 + 1 : var1;
   }

   public static int c(double var0) {
      int var2 = (int)var0;
      return var0 > (double)var2 ? var2 + 1 : var2;
   }

   public static int a(int var0, int var1, int var2) {
      return Math.min(Math.max(var0, var1), var2);
   }

   public static float a(float var0, float var1, float var2) {
      return var0 < var1 ? var1 : Math.min(var0, var2);
   }

   public static double a(double var0, double var2, double var4) {
      return var0 < var2 ? var2 : Math.min(var0, var4);
   }

   public static double b(double var0, double var2, double var4) {
      if (var4 < 0.0) {
         return var0;
      } else {
         return var4 > 1.0 ? var2 : d(var4, var0, var2);
      }
   }

   public static float b(float var0, float var1, float var2) {
      if (var2 < 0.0F) {
         return var0;
      } else {
         return var2 > 1.0F ? var1 : i(var2, var0, var1);
      }
   }

   public static double a(double var0, double var2) {
      if (var0 < 0.0) {
         var0 = -var0;
      }

      if (var2 < 0.0) {
         var2 = -var2;
      }

      return Math.max(var0, var2);
   }

   public static int a(int var0, int var1) {
      return Math.floorDiv(var0, var1);
   }

   public static int a(RandomSource var0, int var1, int var2) {
      return var1 >= var2 ? var1 : var0.a(var2 - var1 + 1) + var1;
   }

   public static float a(RandomSource var0, float var1, float var2) {
      return var1 >= var2 ? var1 : var0.i() * (var2 - var1) + var1;
   }

   public static double a(RandomSource var0, double var1, double var3) {
      return var1 >= var3 ? var1 : var0.j() * (var3 - var1) + var1;
   }

   public static boolean a(float var0, float var1) {
      return Math.abs(var1 - var0) < 1.0E-5F;
   }

   public static boolean b(double var0, double var2) {
      return Math.abs(var2 - var0) < 1.0E-5F;
   }

   public static int b(int var0, int var1) {
      return Math.floorMod(var0, var1);
   }

   public static float b(float var0, float var1) {
      return (var0 % var1 + var1) % var1;
   }

   public static double c(double var0, double var2) {
      return (var0 % var2 + var2) % var2;
   }

   public static boolean c(int var0, int var1) {
      return var0 % var1 == 0;
   }

   public static int b(int var0) {
      int var1 = var0 % 360;
      if (var1 >= 180) {
         var1 -= 360;
      }

      if (var1 < -180) {
         var1 += 360;
      }

      return var1;
   }

   public static float g(float var0) {
      float var1 = var0 % 360.0F;
      if (var1 >= 180.0F) {
         var1 -= 360.0F;
      }

      if (var1 < -180.0F) {
         var1 += 360.0F;
      }

      return var1;
   }

   public static double d(double var0) {
      double var2 = var0 % 360.0;
      if (var2 >= 180.0) {
         var2 -= 360.0;
      }

      if (var2 < -180.0) {
         var2 += 360.0;
      }

      return var2;
   }

   public static float c(float var0, float var1) {
      return g(var1 - var0);
   }

   public static float d(float var0, float var1) {
      return e(c(var0, var1));
   }

   public static float c(float var0, float var1, float var2) {
      float var3 = c(var0, var1);
      float var4 = a(var3, -var2, var2);
      return var1 - var4;
   }

   public static float d(float var0, float var1, float var2) {
      var2 = e(var2);
      return var0 < var1 ? a(var0 + var2, var0, var1) : a(var0 - var2, var1, var0);
   }

   public static float e(float var0, float var1, float var2) {
      float var3 = c(var0, var1);
      return d(var0, var0 + var3, var2);
   }

   public static int a(String var0, int var1) {
      return NumberUtils.toInt(var0, var1);
   }

   public static int c(int var0) {
      int var1 = var0 - 1;
      var1 |= var1 >> 1;
      var1 |= var1 >> 2;
      var1 |= var1 >> 4;
      var1 |= var1 >> 8;
      var1 |= var1 >> 16;
      return var1 + 1;
   }

   public static boolean d(int var0) {
      return var0 != 0 && (var0 & var0 - 1) == 0;
   }

   public static int e(int var0) {
      var0 = d(var0) ? var0 : c(var0);
      return o[(int)((long)var0 * 125613361L >> 27) & 31];
   }

   public static int f(int var0) {
      return e(var0) - (d(var0) ? 0 : 1);
   }

   public static int f(float var0, float var1, float var2) {
      return ColorUtil.b.a(0, d(var0 * 255.0F), d(var1 * 255.0F), d(var2 * 255.0F));
   }

   public static float h(float var0) {
      return var0 - (float)d(var0);
   }

   public static double e(double var0) {
      return var0 - (double)b(var0);
   }

   @Deprecated
   public static long a(BaseBlockPosition var0) {
      return b(var0.u(), var0.v(), var0.w());
   }

   @Deprecated
   public static long b(int var0, int var1, int var2) {
      long var3 = (long)(var0 * 3129871) ^ (long)var2 * 116129781L ^ (long)var1;
      var3 = var3 * var3 * 42317861L + var3 * 11L;
      return var3 >> 16;
   }

   public static UUID a(RandomSource var0) {
      long var1 = var0.g() & -61441L | 16384L;
      long var3 = var0.g() & 4611686018427387903L | Long.MIN_VALUE;
      return new UUID(var1, var3);
   }

   public static UUID a() {
      return a(n);
   }

   public static double c(double var0, double var2, double var4) {
      return (var0 - var2) / (var4 - var2);
   }

   public static float g(float var0, float var1, float var2) {
      return (var0 - var1) / (var2 - var1);
   }

   public static boolean a(Vec3D var0, Vec3D var1, AxisAlignedBB var2) {
      double var3 = (var2.a + var2.d) * 0.5;
      double var5 = (var2.d - var2.a) * 0.5;
      double var7 = var0.c - var3;
      if (Math.abs(var7) > var5 && var7 * var1.c >= 0.0) {
         return false;
      } else {
         double var9 = (var2.b + var2.e) * 0.5;
         double var11 = (var2.e - var2.b) * 0.5;
         double var13 = var0.d - var9;
         if (Math.abs(var13) > var11 && var13 * var1.d >= 0.0) {
            return false;
         } else {
            double var15 = (var2.c + var2.f) * 0.5;
            double var17 = (var2.f - var2.c) * 0.5;
            double var19 = var0.e - var15;
            if (Math.abs(var19) > var17 && var19 * var1.e >= 0.0) {
               return false;
            } else {
               double var21 = Math.abs(var1.c);
               double var23 = Math.abs(var1.d);
               double var25 = Math.abs(var1.e);
               double var27 = var1.d * var19 - var1.e * var13;
               if (Math.abs(var27) > var11 * var25 + var17 * var23) {
                  return false;
               } else {
                  var27 = var1.e * var7 - var1.c * var19;
                  if (Math.abs(var27) > var5 * var25 + var17 * var21) {
                     return false;
                  } else {
                     var27 = var1.c * var13 - var1.d * var7;
                     return Math.abs(var27) < var5 * var23 + var11 * var21;
                  }
               }
            }
         }
      }
   }

   public static double d(double var0, double var2) {
      double var4 = var2 * var2 + var0 * var0;
      if (Double.isNaN(var4)) {
         return Double.NaN;
      } else {
         boolean var6 = var0 < 0.0;
         if (var6) {
            var0 = -var0;
         }

         boolean var7 = var2 < 0.0;
         if (var7) {
            var2 = -var2;
         }

         boolean var8 = var0 > var2;
         if (var8) {
            double var9 = var2;
            var2 = var0;
            var0 = var9;
         }

         double var9 = g(var4);
         var2 *= var9;
         var0 *= var9;
         double var11 = s + var0;
         int var13 = (int)Double.doubleToRawLongBits(var11);
         double var14 = t[var13];
         double var16 = u[var13];
         double var18 = var11 - s;
         double var20 = var0 * var16 - var2 * var18;
         double var22 = (6.0 + var20 * var20) * var20 * 0.16666666666666666;
         double var24 = var14 + var22;
         if (var8) {
            var24 = (Math.PI / 2) - var24;
         }

         if (var7) {
            var24 = Math.PI - var24;
         }

         if (var6) {
            var24 = -var24;
         }

         return var24;
      }
   }

   public static float i(float var0) {
      return org.joml.Math.invsqrt(var0);
   }

   public static double f(double var0) {
      return org.joml.Math.invsqrt(var0);
   }

   @Deprecated
   public static double g(double var0) {
      double var2 = 0.5 * var0;
      long var4 = Double.doubleToRawLongBits(var0);
      var4 = 6910469410427058090L - (var4 >> 1);
      var0 = Double.longBitsToDouble(var4);
      return var0 * (1.5 - var2 * var0 * var0);
   }

   public static float j(float var0) {
      int var1 = Float.floatToIntBits(var0);
      var1 = 1419967116 - var1 / 3;
      float var2 = Float.intBitsToFloat(var1);
      var2 = 0.6666667F * var2 + 1.0F / (3.0F * var2 * var2 * var0);
      return 0.6666667F * var2 + 1.0F / (3.0F * var2 * var2 * var0);
   }

   public static int h(float var0, float var1, float var2) {
      int var3 = (int)(var0 * 6.0F) % 6;
      float var4 = var0 * 6.0F - (float)var3;
      float var5 = var2 * (1.0F - var1);
      float var6 = var2 * (1.0F - var4 * var1);
      float var7 = var2 * (1.0F - (1.0F - var4) * var1);
      float var8;
      float var9;
      float var10;
      switch(var3) {
         case 0:
            var8 = var2;
            var9 = var7;
            var10 = var5;
            break;
         case 1:
            var8 = var6;
            var9 = var2;
            var10 = var5;
            break;
         case 2:
            var8 = var5;
            var9 = var2;
            var10 = var7;
            break;
         case 3:
            var8 = var5;
            var9 = var6;
            var10 = var2;
            break;
         case 4:
            var8 = var7;
            var9 = var5;
            var10 = var2;
            break;
         case 5:
            var8 = var2;
            var9 = var5;
            var10 = var6;
            break;
         default:
            throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + var0 + ", " + var1 + ", " + var2);
      }

      return ColorUtil.b.a(0, a((int)(var8 * 255.0F), 0, 255), a((int)(var9 * 255.0F), 0, 255), a((int)(var10 * 255.0F), 0, 255));
   }

   public static int g(int var0) {
      var0 ^= var0 >>> 16;
      var0 *= -2048144789;
      var0 ^= var0 >>> 13;
      var0 *= -1028477387;
      return var0 ^ var0 >>> 16;
   }

   public static int a(int var0, int var1, IntPredicate var2) {
      int var3 = var1 - var0;

      while(var3 > 0) {
         int var4 = var3 / 2;
         int var5 = var0 + var4;
         if (var2.test(var5)) {
            var3 = var4;
         } else {
            var0 = var5 + 1;
            var3 -= var4 + 1;
         }
      }

      return var0;
   }

   public static int a(float var0, int var1, int var2) {
      return var1 + d(var0 * (float)(var2 - var1));
   }

   public static float i(float var0, float var1, float var2) {
      return var1 + var0 * (var2 - var1);
   }

   public static double d(double var0, double var2, double var4) {
      return var2 + var0 * (var4 - var2);
   }

   public static double a(double var0, double var2, double var4, double var6, double var8, double var10) {
      return d(var2, d(var0, var4, var6), d(var0, var8, var10));
   }

   public static double a(
      double var0, double var2, double var4, double var6, double var8, double var10, double var12, double var14, double var16, double var18, double var20
   ) {
      return d(var4, a(var0, var2, var6, var8, var10, var12), a(var0, var2, var14, var16, var18, var20));
   }

   public static float a(float var0, float var1, float var2, float var3, float var4) {
      return 0.5F
         * (
            2.0F * var2
               + (var3 - var1) * var0
               + (2.0F * var1 - 5.0F * var2 + 4.0F * var3 - var4) * var0 * var0
               + (3.0F * var2 - var1 - 3.0F * var3 + var4) * var0 * var0 * var0
         );
   }

   public static double h(double var0) {
      return var0 * var0 * var0 * (var0 * (var0 * 6.0 - 15.0) + 10.0);
   }

   public static double i(double var0) {
      return 30.0 * var0 * var0 * (var0 - 1.0) * (var0 - 1.0);
   }

   public static int j(double var0) {
      if (var0 == 0.0) {
         return 0;
      } else {
         return var0 > 0.0 ? 1 : -1;
      }
   }

   public static float j(float var0, float var1, float var2) {
      return var1 + var0 * g(var2 - var1);
   }

   public static float e(float var0, float var1) {
      return (Math.abs(var0 % var1 - var1 * 0.5F) - var1 * 0.25F) / (var1 * 0.25F);
   }

   public static float k(float var0) {
      return var0 * var0;
   }

   public static double k(double var0) {
      return var0 * var0;
   }

   public static int h(int var0) {
      return var0 * var0;
   }

   public static long a(long var0) {
      return var0 * var0;
   }

   public static double a(double var0, double var2, double var4, double var6, double var8) {
      return b(var6, var8, c(var0, var2, var4));
   }

   public static float b(float var0, float var1, float var2, float var3, float var4) {
      return b(var3, var4, g(var0, var1, var2));
   }

   public static double b(double var0, double var2, double var4, double var6, double var8) {
      return d(c(var0, var2, var4), var6, var8);
   }

   public static float c(float var0, float var1, float var2, float var3, float var4) {
      return i(g(var0, var1, var2), var3, var4);
   }

   public static double l(double var0) {
      return var0 + (2.0 * RandomSource.a((long)a(var0 * 3000.0)).j() - 1.0) * 1.0E-7 / 2.0;
   }

   public static int d(int var0, int var1) {
      return e(var0, var1) * var1;
   }

   public static int e(int var0, int var1) {
      return -Math.floorDiv(-var0, var1);
   }

   public static int b(RandomSource var0, int var1, int var2) {
      return var0.a(var2 - var1 + 1) + var1;
   }

   public static float b(RandomSource var0, float var1, float var2) {
      return var0.i() * (var2 - var1) + var1;
   }

   public static float c(RandomSource var0, float var1, float var2) {
      return var1 + (float)var0.k() * var2;
   }

   public static double e(double var0, double var2) {
      return var0 * var0 + var2 * var2;
   }

   public static double f(double var0, double var2) {
      return Math.sqrt(e(var0, var2));
   }

   public static double e(double var0, double var2, double var4) {
      return var0 * var0 + var2 * var2 + var4 * var4;
   }

   public static double f(double var0, double var2, double var4) {
      return Math.sqrt(e(var0, var2, var4));
   }

   public static int a(double var0, int var2) {
      return a(var0 / (double)var2) * var2;
   }

   public static IntStream c(int var0, int var1, int var2) {
      return a(var0, var1, var2, 1);
   }

   public static IntStream a(int var0, int var1, int var2, int var3) {
      if (var1 > var2) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "upperbound %d expected to be > lowerBound %d", var2, var1));
      } else if (var3 < 1) {
         throw new IllegalArgumentException(String.format(Locale.ROOT, "steps expected to be >= 1, was %d", var3));
      } else {
         return var0 >= var1 && var0 <= var2 ? IntStream.iterate(var0, var3x -> {
            int var4 = Math.abs(var0 - var3x);
            return var0 - var4 >= var1 || var0 + var4 <= var2;
         }, var4 -> {
            boolean var5 = var4 <= var0;
            int var6 = Math.abs(var0 - var4);
            boolean var7 = var0 + var6 + var3 <= var2;
            if (!var5 || !var7) {
               int var8 = var0 - var6 - (var5 ? var3 : 0);
               if (var8 >= var1) {
                  return var8;
               }
            }

            return var0 + var6 + var3;
         }) : IntStream.empty();
      }
   }

   static {
      for(int var0 = 0; var0 < 257; ++var0) {
         double var1 = (double)var0 / 256.0;
         double var3 = Math.asin(var1);
         u[var0] = Math.cos(var3);
         t[var0] = var3;
      }
   }
}
