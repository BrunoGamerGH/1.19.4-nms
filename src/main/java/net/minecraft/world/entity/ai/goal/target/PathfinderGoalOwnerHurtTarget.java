package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTameableAnimal;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class PathfinderGoalOwnerHurtTarget extends PathfinderGoalTarget {
   private final EntityTameableAnimal a;
   private EntityLiving b;
   private int c;

   public PathfinderGoalOwnerHurtTarget(EntityTameableAnimal entitytameableanimal) {
      super(entitytameableanimal, false);
      this.a = entitytameableanimal;
      this.a(EnumSet.of(PathfinderGoal.Type.d));
   }

   @Override
   public boolean a() {
      if (this.a.q() && !this.a.fS()) {
         EntityLiving entityliving = this.a.H_();
         if (entityliving == null) {
            return false;
         } else {
            this.b = entityliving.ec();
            int i = entityliving.ed();
            return i != this.c && this.a(this.b, PathfinderTargetCondition.a) && this.a.a(this.b, entityliving);
         }
      } else {
         return false;
      }
   }

   @Override
   public void c() {
      this.e.setTarget(this.b, TargetReason.OWNER_ATTACKED_TARGET, true);
      EntityLiving entityliving = this.a.H_();
      if (entityliving != null) {
         this.c = entityliving.ed();
      }

      super.c();
   }
}
