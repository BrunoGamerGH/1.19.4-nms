package net.minecraft.world.entity.ai.util;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.phys.Vec3D;

public class HoverRandomPos {
   @Nullable
   public static Vec3D a(EntityCreature var0, int var1, int var2, double var3, double var5, float var7, int var8, int var9) {
      boolean var10 = PathfinderGoalUtil.a(var0, var1);
      return RandomPositionGenerator.a(var0, () -> {
         BlockPosition var11 = RandomPositionGenerator.a(var0.dZ(), var1, var2, 0, var3, var5, (double)var7);
         if (var11 == null) {
            return null;
         } else {
            BlockPosition var12 = LandRandomPos.a(var0, var1, var10, var11);
            if (var12 == null) {
               return null;
            } else {
               var12 = RandomPositionGenerator.a(var12, var0.dZ().a(var8 - var9 + 1) + var9, var0.H.ai(), var1xx -> PathfinderGoalUtil.c(var0, var1xx));
               return !PathfinderGoalUtil.a(var0, var12) && !PathfinderGoalUtil.b(var0, var12) ? var12 : null;
            }
         }
      });
   }
}
