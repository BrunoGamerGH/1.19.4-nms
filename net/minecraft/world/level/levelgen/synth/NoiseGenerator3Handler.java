package net.minecraft.world.level.levelgen.synth;

import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;

public class NoiseGenerator3Handler {
   protected static final int[][] a = new int[][]{
      {1, 1, 0},
      {-1, 1, 0},
      {1, -1, 0},
      {-1, -1, 0},
      {1, 0, 1},
      {-1, 0, 1},
      {1, 0, -1},
      {-1, 0, -1},
      {0, 1, 1},
      {0, -1, 1},
      {0, 1, -1},
      {0, -1, -1},
      {1, 1, 0},
      {0, -1, 1},
      {-1, 1, 0},
      {0, -1, -1}
   };
   private static final double e = Math.sqrt(3.0);
   private static final double f = 0.5 * (e - 1.0);
   private static final double g = (3.0 - e) / 6.0;
   private final int[] h = new int[512];
   public final double b;
   public final double c;
   public final double d;

   public NoiseGenerator3Handler(RandomSource var0) {
      this.b = var0.j() * 256.0;
      this.c = var0.j() * 256.0;
      this.d = var0.j() * 256.0;
      int var1 = 0;

      while(var1 < 256) {
         this.h[var1] = var1++;
      }

      for(int var1x = 0; var1x < 256; ++var1x) {
         int var2 = var0.a(256 - var1x);
         int var3 = this.h[var1x];
         this.h[var1x] = this.h[var2 + var1x];
         this.h[var2 + var1x] = var3;
      }
   }

   private int a(int var0) {
      return this.h[var0 & 0xFF];
   }

   protected static double a(int[] var0, double var1, double var3, double var5) {
      return (double)var0[0] * var1 + (double)var0[1] * var3 + (double)var0[2] * var5;
   }

   private double a(int var0, double var1, double var3, double var5, double var7) {
      double var11 = var7 - var1 * var1 - var3 * var3 - var5 * var5;
      double var9;
      if (var11 < 0.0) {
         var9 = 0.0;
      } else {
         var11 *= var11;
         var9 = var11 * var11 * a(a[var0], var1, var3, var5);
      }

      return var9;
   }

   public double a(double var0, double var2) {
      double var4 = (var0 + var2) * f;
      int var6 = MathHelper.a(var0 + var4);
      int var7 = MathHelper.a(var2 + var4);
      double var8 = (double)(var6 + var7) * g;
      double var10 = (double)var6 - var8;
      double var12 = (double)var7 - var8;
      double var14 = var0 - var10;
      double var16 = var2 - var12;
      int var18;
      int var19;
      if (var14 > var16) {
         var18 = 1;
         var19 = 0;
      } else {
         var18 = 0;
         var19 = 1;
      }

      double var20 = var14 - (double)var18 + g;
      double var22 = var16 - (double)var19 + g;
      double var24 = var14 - 1.0 + 2.0 * g;
      double var26 = var16 - 1.0 + 2.0 * g;
      int var28 = var6 & 0xFF;
      int var29 = var7 & 0xFF;
      int var30 = this.a(var28 + this.a(var29)) % 12;
      int var31 = this.a(var28 + var18 + this.a(var29 + var19)) % 12;
      int var32 = this.a(var28 + 1 + this.a(var29 + 1)) % 12;
      double var33 = this.a(var30, var14, var16, 0.0, 0.5);
      double var35 = this.a(var31, var20, var22, 0.0, 0.5);
      double var37 = this.a(var32, var24, var26, 0.0, 0.5);
      return 70.0 * (var33 + var35 + var37);
   }

   public double a(double var0, double var2, double var4) {
      double var6 = 0.3333333333333333;
      double var8 = (var0 + var2 + var4) * 0.3333333333333333;
      int var10 = MathHelper.a(var0 + var8);
      int var11 = MathHelper.a(var2 + var8);
      int var12 = MathHelper.a(var4 + var8);
      double var13 = 0.16666666666666666;
      double var15 = (double)(var10 + var11 + var12) * 0.16666666666666666;
      double var17 = (double)var10 - var15;
      double var19 = (double)var11 - var15;
      double var21 = (double)var12 - var15;
      double var23 = var0 - var17;
      double var25 = var2 - var19;
      double var27 = var4 - var21;
      int var29;
      int var30;
      int var31;
      int var32;
      int var33;
      int var34;
      if (var23 >= var25) {
         if (var25 >= var27) {
            var29 = 1;
            var30 = 0;
            var31 = 0;
            var32 = 1;
            var33 = 1;
            var34 = 0;
         } else if (var23 >= var27) {
            var29 = 1;
            var30 = 0;
            var31 = 0;
            var32 = 1;
            var33 = 0;
            var34 = 1;
         } else {
            var29 = 0;
            var30 = 0;
            var31 = 1;
            var32 = 1;
            var33 = 0;
            var34 = 1;
         }
      } else if (var25 < var27) {
         var29 = 0;
         var30 = 0;
         var31 = 1;
         var32 = 0;
         var33 = 1;
         var34 = 1;
      } else if (var23 < var27) {
         var29 = 0;
         var30 = 1;
         var31 = 0;
         var32 = 0;
         var33 = 1;
         var34 = 1;
      } else {
         var29 = 0;
         var30 = 1;
         var31 = 0;
         var32 = 1;
         var33 = 1;
         var34 = 0;
      }

      double var35 = var23 - (double)var29 + 0.16666666666666666;
      double var37 = var25 - (double)var30 + 0.16666666666666666;
      double var39 = var27 - (double)var31 + 0.16666666666666666;
      double var41 = var23 - (double)var32 + 0.3333333333333333;
      double var43 = var25 - (double)var33 + 0.3333333333333333;
      double var45 = var27 - (double)var34 + 0.3333333333333333;
      double var47 = var23 - 1.0 + 0.5;
      double var49 = var25 - 1.0 + 0.5;
      double var51 = var27 - 1.0 + 0.5;
      int var53 = var10 & 0xFF;
      int var54 = var11 & 0xFF;
      int var55 = var12 & 0xFF;
      int var56 = this.a(var53 + this.a(var54 + this.a(var55))) % 12;
      int var57 = this.a(var53 + var29 + this.a(var54 + var30 + this.a(var55 + var31))) % 12;
      int var58 = this.a(var53 + var32 + this.a(var54 + var33 + this.a(var55 + var34))) % 12;
      int var59 = this.a(var53 + 1 + this.a(var54 + 1 + this.a(var55 + 1))) % 12;
      double var60 = this.a(var56, var23, var25, var27, 0.6);
      double var62 = this.a(var57, var35, var37, var39, 0.6);
      double var64 = this.a(var58, var41, var43, var45, 0.6);
      double var66 = this.a(var59, var47, var49, var51, 0.6);
      return 32.0 * (var60 + var62 + var64 + var66);
   }
}
