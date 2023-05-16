package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class PathfinderGoalDefendVillage extends PathfinderGoalTarget {
   private final EntityIronGolem a;
   @Nullable
   private EntityLiving b;
   private final PathfinderTargetCondition c = PathfinderTargetCondition.a().a(64.0);

   public PathfinderGoalDefendVillage(EntityIronGolem entityirongolem) {
      super(entityirongolem, false, true);
      this.a = entityirongolem;
      this.a(EnumSet.of(PathfinderGoal.Type.d));
   }

   @Override
   public boolean a() {
      AxisAlignedBB axisalignedbb = this.a.cD().c(10.0, 8.0, 10.0);
      List<? extends EntityLiving> list = this.a.H.a(EntityVillager.class, this.c, this.a, axisalignedbb);
      List<EntityHuman> list1 = this.a.H.a(this.c, this.a, axisalignedbb);

      for(EntityLiving entityliving : list) {
         EntityVillager entityvillager = (EntityVillager)entityliving;

         for(EntityHuman entityhuman : list1) {
            int i = entityvillager.f(entityhuman);
            if (i <= -100) {
               this.b = entityhuman;
            }
         }
      }

      if (this.b == null) {
         return false;
      } else {
         return !(this.b instanceof EntityHuman) || !this.b.F_() && !((EntityHuman)this.b).f();
      }
   }

   @Override
   public void c() {
      this.a.setTarget(this.b, TargetReason.DEFEND_VILLAGE, true);
      super.c();
   }
}
