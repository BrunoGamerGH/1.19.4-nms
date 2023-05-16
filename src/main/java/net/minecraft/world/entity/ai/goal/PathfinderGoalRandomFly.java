package net.minecraft.world.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalRandomFly extends PathfinderGoalRandomStrollLand {
   public PathfinderGoalRandomFly(EntityCreature var0, double var1) {
      super(var0, var1);
   }

   @Nullable
   @Override
   protected Vec3D h() {
      Vec3D var0 = this.b.j(0.0F);
      int var1 = 8;
      Vec3D var2 = HoverRandomPos.a(this.b, 8, 7, var0.c, var0.e, (float) (Math.PI / 2), 3, 1);
      return var2 != null ? var2 : AirAndWaterRandomPos.a(this.b, 8, 4, -2, var0.c, var0.e, (float) (Math.PI / 2));
   }
}
