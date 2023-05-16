package net.minecraft.world.entity.ai.util;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.phys.Vec3D;

public class AirRandomPos {
   @Nullable
   public static Vec3D a(EntityCreature var0, int var1, int var2, int var3, Vec3D var4, double var5) {
      Vec3D var7 = var4.a(var0.dl(), var0.dn(), var0.dr());
      boolean var8 = PathfinderGoalUtil.a(var0, var1);
      return RandomPositionGenerator.a(var0, () -> {
         BlockPosition var8x = AirAndWaterRandomPos.a(var0, var1, var2, var3, var7.c, var7.e, var5, var8);
         return var8x != null && !PathfinderGoalUtil.a(var0, var8x) ? var8x : null;
      });
   }
}
