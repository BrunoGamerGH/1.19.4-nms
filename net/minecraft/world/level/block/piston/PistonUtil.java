package net.minecraft.world.level.block.piston;

import net.minecraft.core.EnumDirection;
import net.minecraft.world.phys.AxisAlignedBB;

public class PistonUtil {
   public static AxisAlignedBB a(AxisAlignedBB var0, EnumDirection var1, double var2) {
      double var4 = var2 * (double)var1.f().a();
      double var6 = Math.min(var4, 0.0);
      double var8 = Math.max(var4, 0.0);
      switch(var1) {
         case e:
            return new AxisAlignedBB(var0.a + var6, var0.b, var0.c, var0.a + var8, var0.e, var0.f);
         case f:
            return new AxisAlignedBB(var0.d + var6, var0.b, var0.c, var0.d + var8, var0.e, var0.f);
         case a:
            return new AxisAlignedBB(var0.a, var0.b + var6, var0.c, var0.d, var0.b + var8, var0.f);
         case b:
         default:
            return new AxisAlignedBB(var0.a, var0.e + var6, var0.c, var0.d, var0.e + var8, var0.f);
         case c:
            return new AxisAlignedBB(var0.a, var0.b, var0.c + var6, var0.d, var0.e, var0.c + var8);
         case d:
            return new AxisAlignedBB(var0.a, var0.b, var0.f + var6, var0.d, var0.e, var0.f + var8);
      }
   }
}
