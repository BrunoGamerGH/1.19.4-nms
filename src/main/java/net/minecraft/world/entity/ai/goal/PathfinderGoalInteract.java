package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;

public class PathfinderGoalInteract extends PathfinderGoalLookAtPlayer {
   public PathfinderGoalInteract(EntityInsentient var0, Class<? extends EntityLiving> var1, float var2) {
      super(var0, var1, var2);
      this.a(EnumSet.of(PathfinderGoal.Type.b, PathfinderGoal.Type.a));
   }

   public PathfinderGoalInteract(EntityInsentient var0, Class<? extends EntityLiving> var1, float var2, float var3) {
      super(var0, var1, var2, var3);
      this.a(EnumSet.of(PathfinderGoal.Type.b, PathfinderGoal.Type.a));
   }
}
