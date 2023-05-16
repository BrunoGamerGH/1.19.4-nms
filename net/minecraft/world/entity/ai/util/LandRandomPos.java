package net.minecraft.world.entity.ai.util;

import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.phys.Vec3D;

public class LandRandomPos {
   @Nullable
   public static Vec3D a(EntityCreature var0, int var1, int var2) {
      return a(var0, var1, var2, var0::f);
   }

   @Nullable
   public static Vec3D a(EntityCreature var0, int var1, int var2, ToDoubleFunction<BlockPosition> var3) {
      boolean var4 = PathfinderGoalUtil.a(var0, var1);
      return RandomPositionGenerator.a(() -> {
         BlockPosition var4x = RandomPositionGenerator.a(var0.dZ(), var1, var2);
         BlockPosition var5 = a(var0, var1, var4, var4x);
         return var5 == null ? null : a(var0, var5);
      }, var3);
   }

   @Nullable
   public static Vec3D a(EntityCreature var0, int var1, int var2, Vec3D var3) {
      Vec3D var4 = var3.a(var0.dl(), var0.dn(), var0.dr());
      boolean var5 = PathfinderGoalUtil.a(var0, var1);
      return a(var0, var1, var2, var4, var5);
   }

   @Nullable
   public static Vec3D b(EntityCreature var0, int var1, int var2, Vec3D var3) {
      Vec3D var4 = var0.de().d(var3);
      boolean var5 = PathfinderGoalUtil.a(var0, var1);
      return a(var0, var1, var2, var4, var5);
   }

   @Nullable
   private static Vec3D a(EntityCreature var0, int var1, int var2, Vec3D var3, boolean var4) {
      return RandomPositionGenerator.a(var0, () -> {
         BlockPosition var5 = RandomPositionGenerator.a(var0.dZ(), var1, var2, 0, var3.c, var3.e, (float) (Math.PI / 2));
         if (var5 == null) {
            return null;
         } else {
            BlockPosition var6 = a(var0, var1, var4, var5);
            return var6 == null ? null : a(var0, var6);
         }
      });
   }

   @Nullable
   public static BlockPosition a(EntityCreature var0, BlockPosition var1) {
      var1 = RandomPositionGenerator.a(var1, var0.H.ai(), var1x -> PathfinderGoalUtil.c(var0, var1x));
      return !PathfinderGoalUtil.a(var0, var1) && !PathfinderGoalUtil.b(var0, var1) ? var1 : null;
   }

   @Nullable
   public static BlockPosition a(EntityCreature var0, int var1, boolean var2, BlockPosition var3) {
      BlockPosition var4 = RandomPositionGenerator.a(var0, var1, var0.dZ(), var3);
      return !PathfinderGoalUtil.a(var4, var0) && !PathfinderGoalUtil.a(var2, var0, var4) && !PathfinderGoalUtil.a(var0.G(), var4) ? var4 : null;
   }
}
