package net.minecraft.world.entity.ai.util;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.phys.Vec3D;

public class AirAndWaterRandomPos {
   @Nullable
   public static Vec3D a(EntityCreature var0, int var1, int var2, int var3, double var4, double var6, double var8) {
      boolean var10 = PathfinderGoalUtil.a(var0, var1);
      return RandomPositionGenerator.a(var0, () -> a(var0, var1, var2, var3, var4, var6, var8, var10));
   }

   @Nullable
   public static BlockPosition a(EntityCreature var0, int var1, int var2, int var3, double var4, double var6, double var8, boolean var10) {
      BlockPosition var11 = RandomPositionGenerator.a(var0.dZ(), var1, var2, var3, var4, var6, var8);
      if (var11 == null) {
         return null;
      } else {
         BlockPosition var12 = RandomPositionGenerator.a(var0, var1, var0.dZ(), var11);
         if (!PathfinderGoalUtil.a(var12, var0) && !PathfinderGoalUtil.a(var10, var0, var12)) {
            var12 = RandomPositionGenerator.a(var12, var0.H.ai(), var1x -> PathfinderGoalUtil.c(var0, var1x));
            return PathfinderGoalUtil.b(var0, var12) ? null : var12;
         } else {
            return null;
         }
      }
   }
}
