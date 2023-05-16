package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;

public abstract class PathfinderGoalWaterJumpAbstract extends PathfinderGoal {
   public PathfinderGoalWaterJumpAbstract() {
      this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.c));
   }
}
