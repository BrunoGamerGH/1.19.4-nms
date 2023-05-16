package net.minecraft.world.entity.ai.util;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.phys.Vec3D;

public class DefaultRandomPos {
   @Nullable
   public static Vec3D a(EntityCreature var0, int var1, int var2) {
      boolean var3 = PathfinderGoalUtil.a(var0, var1);
      return RandomPositionGenerator.a(var0, () -> {
         BlockPosition var4 = RandomPositionGenerator.a(var0.dZ(), var1, var2);
         return a(var0, var1, var3, var4);
      });
   }

   @Nullable
   public static Vec3D a(EntityCreature var0, int var1, int var2, Vec3D var3, double var4) {
      Vec3D var6 = var3.a(var0.dl(), var0.dn(), var0.dr());
      boolean var7 = PathfinderGoalUtil.a(var0, var1);
      return RandomPositionGenerator.a(var0, () -> {
         BlockPosition var7x = RandomPositionGenerator.a(var0.dZ(), var1, var2, 0, var6.c, var6.e, var4);
         return var7x == null ? null : a(var0, var1, var7, var7x);
      });
   }

   @Nullable
   public static Vec3D a(EntityCreature var0, int var1, int var2, Vec3D var3) {
      Vec3D var4 = var0.de().d(var3);
      boolean var5 = PathfinderGoalUtil.a(var0, var1);
      return RandomPositionGenerator.a(var0, () -> {
         BlockPosition var5x = RandomPositionGenerator.a(var0.dZ(), var1, var2, 0, var4.c, var4.e, (float) (Math.PI / 2));
         return var5x == null ? null : a(var0, var1, var5, var5x);
      });
   }

   @Nullable
   private static BlockPosition a(EntityCreature var0, int var1, boolean var2, BlockPosition var3) {
      BlockPosition var4 = RandomPositionGenerator.a(var0, var1, var0.dZ(), var3);
      return !PathfinderGoalUtil.a(var4, var0)
            && !PathfinderGoalUtil.a(var2, var0, var4)
            && !PathfinderGoalUtil.a(var0.G(), var4)
            && !PathfinderGoalUtil.b(var0, var4)
         ? var4
         : null;
   }
}
