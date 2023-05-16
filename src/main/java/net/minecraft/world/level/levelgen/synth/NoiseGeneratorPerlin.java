package net.minecraft.world.level.levelgen.synth;

import com.google.common.annotations.VisibleForTesting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;

public final class NoiseGeneratorPerlin {
   private static final float d = 1.0E-7F;
   private final byte[] e;
   public final double a;
   public final double b;
   public final double c;

   public NoiseGeneratorPerlin(RandomSource var0) {
      this.a = var0.j() * 256.0;
      this.b = var0.j() * 256.0;
      this.c = var0.j() * 256.0;
      this.e = new byte[256];

      for(int var1 = 0; var1 < 256; ++var1) {
         this.e[var1] = (byte)var1;
      }

      for(int var1 = 0; var1 < 256; ++var1) {
         int var2 = var0.a(256 - var1);
         byte var3 = this.e[var1];
         this.e[var1] = this.e[var1 + var2];
         this.e[var1 + var2] = var3;
      }
   }

   public double a(double var0, double var2, double var4) {
      return this.a(var0, var2, var4, 0.0, 0.0);
   }

   @Deprecated
   public double a(double var0, double var2, double var4, double var6, double var8) {
      double var10 = var0 + this.a;
      double var12 = var2 + this.b;
      double var14 = var4 + this.c;
      int var16 = MathHelper.a(var10);
      int var17 = MathHelper.a(var12);
      int var18 = MathHelper.a(var14);
      double var19 = var10 - (double)var16;
      double var21 = var12 - (double)var17;
      double var23 = var14 - (double)var18;
      double var25;
      if (var6 != 0.0) {
         double var27;
         if (var8 >= 0.0 && var8 < var21) {
            var27 = var8;
         } else {
            var27 = var21;
         }

         var25 = (double)MathHelper.a(var27 / var6 + 1.0E-7F) * var6;
      } else {
         var25 = 0.0;
      }

      return this.a(var16, var17, var18, var19, var21 - var25, var23, var21);
   }

   public double a(double var0, double var2, double var4, double[] var6) {
      double var7 = var0 + this.a;
      double var9 = var2 + this.b;
      double var11 = var4 + this.c;
      int var13 = MathHelper.a(var7);
      int var14 = MathHelper.a(var9);
      int var15 = MathHelper.a(var11);
      double var16 = var7 - (double)var13;
      double var18 = var9 - (double)var14;
      double var20 = var11 - (double)var15;
      return this.a(var13, var14, var15, var16, var18, var20, var6);
   }

   private static double a(int var0, double var1, double var3, double var5) {
      return NoiseGenerator3Handler.a(NoiseGenerator3Handler.a[var0 & 15], var1, var3, var5);
   }

   private int a(int var0) {
      return this.e[var0 & 0xFF] & 0xFF;
   }

   private double a(int var0, int var1, int var2, double var3, double var5, double var7, double var9) {
      int var11 = this.a(var0);
      int var12 = this.a(var0 + 1);
      int var13 = this.a(var11 + var1);
      int var14 = this.a(var11 + var1 + 1);
      int var15 = this.a(var12 + var1);
      int var16 = this.a(var12 + var1 + 1);
      double var17 = a(this.a(var13 + var2), var3, var5, var7);
      double var19 = a(this.a(var15 + var2), var3 - 1.0, var5, var7);
      double var21 = a(this.a(var14 + var2), var3, var5 - 1.0, var7);
      double var23 = a(this.a(var16 + var2), var3 - 1.0, var5 - 1.0, var7);
      double var25 = a(this.a(var13 + var2 + 1), var3, var5, var7 - 1.0);
      double var27 = a(this.a(var15 + var2 + 1), var3 - 1.0, var5, var7 - 1.0);
      double var29 = a(this.a(var14 + var2 + 1), var3, var5 - 1.0, var7 - 1.0);
      double var31 = a(this.a(var16 + var2 + 1), var3 - 1.0, var5 - 1.0, var7 - 1.0);
      double var33 = MathHelper.h(var3);
      double var35 = MathHelper.h(var9);
      double var37 = MathHelper.h(var7);
      return MathHelper.a(var33, var35, var37, var17, var19, var21, var23, var25, var27, var29, var31);
   }

   private double a(int var0, int var1, int var2, double var3, double var5, double var7, double[] var9) {
      int var10 = this.a(var0);
      int var11 = this.a(var0 + 1);
      int var12 = this.a(var10 + var1);
      int var13 = this.a(var10 + var1 + 1);
      int var14 = this.a(var11 + var1);
      int var15 = this.a(var11 + var1 + 1);
      int var16 = this.a(var12 + var2);
      int var17 = this.a(var14 + var2);
      int var18 = this.a(var13 + var2);
      int var19 = this.a(var15 + var2);
      int var20 = this.a(var12 + var2 + 1);
      int var21 = this.a(var14 + var2 + 1);
      int var22 = this.a(var13 + var2 + 1);
      int var23 = this.a(var15 + var2 + 1);
      int[] var24 = NoiseGenerator3Handler.a[var16 & 15];
      int[] var25 = NoiseGenerator3Handler.a[var17 & 15];
      int[] var26 = NoiseGenerator3Handler.a[var18 & 15];
      int[] var27 = NoiseGenerator3Handler.a[var19 & 15];
      int[] var28 = NoiseGenerator3Handler.a[var20 & 15];
      int[] var29 = NoiseGenerator3Handler.a[var21 & 15];
      int[] var30 = NoiseGenerator3Handler.a[var22 & 15];
      int[] var31 = NoiseGenerator3Handler.a[var23 & 15];
      double var32 = NoiseGenerator3Handler.a(var24, var3, var5, var7);
      double var34 = NoiseGenerator3Handler.a(var25, var3 - 1.0, var5, var7);
      double var36 = NoiseGenerator3Handler.a(var26, var3, var5 - 1.0, var7);
      double var38 = NoiseGenerator3Handler.a(var27, var3 - 1.0, var5 - 1.0, var7);
      double var40 = NoiseGenerator3Handler.a(var28, var3, var5, var7 - 1.0);
      double var42 = NoiseGenerator3Handler.a(var29, var3 - 1.0, var5, var7 - 1.0);
      double var44 = NoiseGenerator3Handler.a(var30, var3, var5 - 1.0, var7 - 1.0);
      double var46 = NoiseGenerator3Handler.a(var31, var3 - 1.0, var5 - 1.0, var7 - 1.0);
      double var48 = MathHelper.h(var3);
      double var50 = MathHelper.h(var5);
      double var52 = MathHelper.h(var7);
      double var54 = MathHelper.a(
         var48,
         var50,
         var52,
         (double)var24[0],
         (double)var25[0],
         (double)var26[0],
         (double)var27[0],
         (double)var28[0],
         (double)var29[0],
         (double)var30[0],
         (double)var31[0]
      );
      double var56 = MathHelper.a(
         var48,
         var50,
         var52,
         (double)var24[1],
         (double)var25[1],
         (double)var26[1],
         (double)var27[1],
         (double)var28[1],
         (double)var29[1],
         (double)var30[1],
         (double)var31[1]
      );
      double var58 = MathHelper.a(
         var48,
         var50,
         var52,
         (double)var24[2],
         (double)var25[2],
         (double)var26[2],
         (double)var27[2],
         (double)var28[2],
         (double)var29[2],
         (double)var30[2],
         (double)var31[2]
      );
      double var60 = MathHelper.a(var50, var52, var34 - var32, var38 - var36, var42 - var40, var46 - var44);
      double var62 = MathHelper.a(var52, var48, var36 - var32, var44 - var40, var38 - var34, var46 - var42);
      double var64 = MathHelper.a(var48, var50, var40 - var32, var42 - var34, var44 - var36, var46 - var38);
      double var66 = MathHelper.i(var3);
      double var68 = MathHelper.i(var5);
      double var70 = MathHelper.i(var7);
      double var72 = var54 + var66 * var60;
      double var74 = var56 + var68 * var62;
      double var76 = var58 + var70 * var64;
      var9[0] += var72;
      var9[1] += var74;
      var9[2] += var76;
      return MathHelper.a(var48, var50, var52, var32, var34, var36, var38, var40, var42, var44, var46);
   }

   @VisibleForTesting
   public void a(StringBuilder var0) {
      NoiseUtils.a(var0, this.a, this.b, this.c, this.e);
   }
}
