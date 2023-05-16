package net.minecraft.world.damagesource;

import net.minecraft.util.MathHelper;

public class CombatMath {
   public static final float a = 20.0F;
   public static final float b = 25.0F;
   public static final float c = 2.0F;
   public static final float d = 0.2F;
   private static final int e = 4;

   public static float a(float var0, float var1, float var2) {
      float var3 = 2.0F + var2 / 4.0F;
      float var4 = MathHelper.a(var1 - var0 / var3, var1 * 0.2F, 20.0F);
      return var0 * (1.0F - var4 / 25.0F);
   }

   public static float a(float var0, float var1) {
      float var2 = MathHelper.a(var1, 0.0F, 20.0F);
      return var0 * (1.0F - var2 / 25.0F);
   }
}
