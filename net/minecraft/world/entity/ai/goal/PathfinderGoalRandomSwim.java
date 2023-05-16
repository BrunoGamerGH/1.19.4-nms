package net.minecraft.world.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.behavior.BehaviorUtil;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalRandomSwim extends PathfinderGoalRandomStroll {
   public PathfinderGoalRandomSwim(EntityCreature var0, double var1, int var3) {
      super(var0, var1, var3);
   }

   @Nullable
   @Override
   protected Vec3D h() {
      return BehaviorUtil.a(this.b, 10, 7);
   }
}
