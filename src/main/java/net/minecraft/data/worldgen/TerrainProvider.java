package net.minecraft.data.worldgen;

import net.minecraft.util.CubicSpline;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ToFloatFunction;
import net.minecraft.world.level.levelgen.NoiseRouterData;

public class TerrainProvider {
   private static final float a = -0.51F;
   private static final float b = -0.4F;
   private static final float c = 0.1F;
   private static final float d = -0.15F;
   private static final ToFloatFunction<Float> e = ToFloatFunction.a;
   private static final ToFloatFunction<Float> f = ToFloatFunction.a(var0 -> var0 < 0.0F ? var0 : var0 * 2.0F);
   private static final ToFloatFunction<Float> g = ToFloatFunction.a(var0 -> 1.25F - 6.25F / (var0 + 5.0F));
   private static final ToFloatFunction<Float> h = ToFloatFunction.a(var0 -> var0 * 2.0F);

   public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> a(I var0, I var1, I var2, boolean var3) {
      ToFloatFunction<Float> var4 = var3 ? f : e;
      CubicSpline<C, I> var5 = a(var1, var2, -0.15F, 0.0F, 0.0F, 0.1F, 0.0F, -0.03F, false, false, var4);
      CubicSpline<C, I> var6 = a(var1, var2, -0.1F, 0.03F, 0.1F, 0.1F, 0.01F, -0.03F, false, false, var4);
      CubicSpline<C, I> var7 = a(var1, var2, -0.1F, 0.03F, 0.1F, 0.7F, 0.01F, -0.03F, true, true, var4);
      CubicSpline<C, I> var8 = a(var1, var2, -0.05F, 0.03F, 0.1F, 1.0F, 0.01F, 0.01F, true, true, var4);
      return CubicSpline.<C, I>a(var0, var4)
         .a(-1.1F, 0.044F)
         .a(-1.02F, -0.2222F)
         .a(-0.51F, -0.2222F)
         .a(-0.44F, -0.12F)
         .a(-0.18F, -0.12F)
         .a(-0.16F, var5)
         .a(-0.15F, var5)
         .a(-0.1F, var6)
         .a(0.25F, var7)
         .a(1.0F, var8)
         .a();
   }

   public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> a(I var0, I var1, I var2, I var3, boolean var4) {
      ToFloatFunction<Float> var5 = var4 ? g : e;
      return CubicSpline.<C, I>a(var0, e)
         .a(-0.19F, 3.95F)
         .a(-0.15F, a(var1, var2, var3, 6.25F, true, e))
         .a(-0.1F, a(var1, var2, var3, 5.47F, true, var5))
         .a(0.03F, a(var1, var2, var3, 5.08F, true, var5))
         .a(0.06F, a(var1, var2, var3, 4.69F, false, var5))
         .a();
   }

   public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> b(I var0, I var1, I var2, I var3, boolean var4) {
      ToFloatFunction<Float> var5 = var4 ? h : e;
      float var6 = 0.65F;
      return CubicSpline.<C, I>a(var0, var5)
         .a(-0.11F, 0.0F)
         .a(0.03F, a(var1, var2, var3, 1.0F, 0.5F, 0.0F, 0.0F, var5))
         .a(0.65F, a(var1, var2, var3, 1.0F, 1.0F, 1.0F, 0.0F, var5))
         .a();
   }

   private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> a(
      I var0, I var1, I var2, float var3, float var4, float var5, float var6, ToFloatFunction<Float> var7
   ) {
      float var8 = -0.5775F;
      CubicSpline<C, I> var9 = a(var1, var2, var3, var5, var7);
      CubicSpline<C, I> var10 = a(var1, var2, var4, var6, var7);
      return CubicSpline.<C, I>a(var0, var7).a(-1.0F, var9).a(-0.78F, var10).a(-0.5775F, var10).a(-0.375F, 0.0F).a();
   }

   private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> a(I var0, I var1, float var2, float var3, ToFloatFunction<Float> var4) {
      float var5 = NoiseRouterData.a(0.4F);
      float var6 = NoiseRouterData.a(0.56666666F);
      float var7 = (var5 + var6) / 2.0F;
      CubicSpline.b<C, I> var8 = CubicSpline.a(var1, var4);
      var8.a(var5, 0.0F);
      if (var3 > 0.0F) {
         var8.a(var7, a(var0, var3, var4));
      } else {
         var8.a(var7, 0.0F);
      }

      if (var2 > 0.0F) {
         var8.a(1.0F, a(var0, var2, var4));
      } else {
         var8.a(1.0F, 0.0F);
      }

      return var8.a();
   }

   private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> a(I var0, float var1, ToFloatFunction<Float> var2) {
      float var3 = 0.63F * var1;
      float var4 = 0.3F * var1;
      return CubicSpline.<C, I>a(var0, var2).a(-0.01F, var3).a(0.01F, var4).a();
   }

   private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> a(I var0, I var1, I var2, float var3, boolean var4, ToFloatFunction<Float> var5) {
      CubicSpline<C, I> var6 = CubicSpline.<C, I>a(var1, var5).a(-0.2F, 6.3F).a(0.2F, var3).a();
      CubicSpline.b<C, I> var7 = CubicSpline.<C, I>a(var0, var5)
         .a(-0.6F, var6)
         .a(-0.5F, CubicSpline.<C, I>a(var1, var5).a(-0.05F, 6.3F).a(0.05F, 2.67F).a())
         .a(-0.35F, var6)
         .a(-0.25F, var6)
         .a(-0.1F, CubicSpline.<C, I>a(var1, var5).a(-0.05F, 2.67F).a(0.05F, 6.3F).a())
         .a(0.03F, var6);
      if (var4) {
         CubicSpline<C, I> var8 = CubicSpline.<C, I>a(var1, var5).a(0.0F, var3).a(0.1F, 0.625F).a();
         CubicSpline<C, I> var9 = CubicSpline.<C, I>a(var2, var5).a(-0.9F, var3).a(-0.69F, var8).a();
         var7.a(0.35F, var3).a(0.45F, var9).a(0.55F, var9).a(0.62F, var3);
      } else {
         CubicSpline<C, I> var8 = CubicSpline.<C, I>a(var2, var5).a(-0.7F, var6).a(-0.15F, 1.37F).a();
         CubicSpline<C, I> var9 = CubicSpline.<C, I>a(var2, var5).a(0.45F, var6).a(0.7F, 1.56F).a();
         var7.a(0.05F, var9).a(0.4F, var9).a(0.45F, var8).a(0.55F, var8).a(0.58F, var3);
      }

      return var7.a();
   }

   private static float a(float var0, float var1, float var2, float var3) {
      return (var1 - var0) / (var3 - var2);
   }

   private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> a(I var0, float var1, boolean var2, ToFloatFunction<Float> var3) {
      CubicSpline.b<C, I> var4 = CubicSpline.a(var0, var3);
      float var5 = -0.7F;
      float var6 = -1.0F;
      float var7 = a(-1.0F, var1, -0.7F);
      float var8 = 1.0F;
      float var9 = a(1.0F, var1, -0.7F);
      float var10 = a(var1);
      float var11 = -0.65F;
      if (-0.65F < var10 && var10 < 1.0F) {
         float var12 = a(-0.65F, var1, -0.7F);
         float var13 = -0.75F;
         float var14 = a(-0.75F, var1, -0.7F);
         float var15 = a(var7, var14, -1.0F, -0.75F);
         var4.a(-1.0F, var7, var15);
         var4.a(-0.75F, var14);
         var4.a(-0.65F, var12);
         float var16 = a(var10, var1, -0.7F);
         float var17 = a(var16, var9, var10, 1.0F);
         float var18 = 0.01F;
         var4.a(var10 - 0.01F, var16);
         var4.a(var10, var16, var17);
         var4.a(1.0F, var9, var17);
      } else {
         float var12 = a(var7, var9, -1.0F, 1.0F);
         if (var2) {
            var4.a(-1.0F, Math.max(0.2F, var7));
            var4.a(0.0F, MathHelper.i(0.5F, var7, var9), var12);
         } else {
            var4.a(-1.0F, var7, var12);
         }

         var4.a(1.0F, var9, var12);
      }

      return var4.a();
   }

   private static float a(float var0, float var1, float var2) {
      float var3 = 1.17F;
      float var4 = 0.46082947F;
      float var5 = 1.0F - (1.0F - var1) * 0.5F;
      float var6 = 0.5F * (1.0F - var1);
      float var7 = (var0 + 1.17F) * 0.46082947F;
      float var8 = var7 * var5 - var6;
      return var0 < var2 ? Math.max(var8, -0.2222F) : Math.max(var8, 0.0F);
   }

   private static float a(float var0) {
      float var1 = 1.17F;
      float var2 = 0.46082947F;
      float var3 = 1.0F - (1.0F - var0) * 0.5F;
      float var4 = 0.5F * (1.0F - var0);
      return var4 / (0.46082947F * var3) - 1.17F;
   }

   public static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> a(
      I var0, I var1, float var2, float var3, float var4, float var5, float var6, float var7, boolean var8, boolean var9, ToFloatFunction<Float> var10
   ) {
      float var11 = 0.6F;
      float var12 = 0.5F;
      float var13 = 0.5F;
      CubicSpline<C, I> var14 = a(var1, MathHelper.i(var5, 0.6F, 1.5F), var9, var10);
      CubicSpline<C, I> var15 = a(var1, MathHelper.i(var5, 0.6F, 1.0F), var9, var10);
      CubicSpline<C, I> var16 = a(var1, var5, var9, var10);
      CubicSpline<C, I> var17 = a(var1, var2 - 0.15F, 0.5F * var5, MathHelper.i(0.5F, 0.5F, 0.5F) * var5, 0.5F * var5, 0.6F * var5, 0.5F, var10);
      CubicSpline<C, I> var18 = a(var1, var2, var6 * var5, var3 * var5, 0.5F * var5, 0.6F * var5, 0.5F, var10);
      CubicSpline<C, I> var19 = a(var1, var2, var6, var6, var3, var4, 0.5F, var10);
      CubicSpline<C, I> var20 = a(var1, var2, var6, var6, var3, var4, 0.5F, var10);
      CubicSpline<C, I> var21 = CubicSpline.<C, I>a(var1, var10).a(-1.0F, var2).a(-0.4F, var19).a(0.0F, var4 + 0.07F).a();
      CubicSpline<C, I> var22 = a(var1, -0.02F, var7, var7, var3, var4, 0.0F, var10);
      CubicSpline.b<C, I> var23 = CubicSpline.<C, I>a(var0, var10)
         .a(-0.85F, var14)
         .a(-0.7F, var15)
         .a(-0.4F, var16)
         .a(-0.35F, var17)
         .a(-0.1F, var18)
         .a(0.2F, var19);
      if (var8) {
         var23.a(0.4F, var20).a(0.45F, var21).a(0.55F, var21).a(0.58F, var20);
      }

      var23.a(0.7F, var22);
      return var23.a();
   }

   private static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> a(
      I var0, float var1, float var2, float var3, float var4, float var5, float var6, ToFloatFunction<Float> var7
   ) {
      float var8 = Math.max(0.5F * (var2 - var1), var6);
      float var9 = 5.0F * (var3 - var2);
      return CubicSpline.<C, I>a(var0, var7)
         .a(-1.0F, var1, var8)
         .a(-0.4F, var2, Math.min(var8, var9))
         .a(0.0F, var3, var9)
         .a(0.4F, var4, 2.0F * (var4 - var3))
         .a(1.0F, var5, 0.7F * (var5 - var4))
         .a();
   }
}
