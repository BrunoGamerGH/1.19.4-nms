package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTameableAnimal;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.AxisAlignedBB;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class PathfinderGoalHurtByTarget extends PathfinderGoalTarget {
   private static final PathfinderTargetCondition a = PathfinderTargetCondition.a().d().e();
   private static final int b = 10;
   private boolean c;
   private int d;
   private final Class<?>[] i;
   @Nullable
   private Class<?>[] j;

   public PathfinderGoalHurtByTarget(EntityCreature entitycreature, Class<?>... aclass) {
      super(entitycreature, true);
      this.i = aclass;
      this.a(EnumSet.of(PathfinderGoal.Type.d));
   }

   @Override
   public boolean a() {
      int i = this.e.eb();
      EntityLiving entityliving = this.e.ea();
      if (i != this.d && entityliving != null) {
         if (entityliving.ae() == EntityTypes.bt && this.e.H.W().b(GameRules.K)) {
            return false;
         } else {
            for(Class<?> oclass : this.i) {
               if (oclass.isAssignableFrom(entityliving.getClass())) {
                  return false;
               }
            }

            return this.a(entityliving, a);
         }
      } else {
         return false;
      }
   }

   public PathfinderGoalHurtByTarget a(Class<?>... aclass) {
      this.c = true;
      this.j = aclass;
      return this;
   }

   @Override
   public void c() {
      this.e.setTarget(this.e.ea(), TargetReason.TARGET_ATTACKED_ENTITY, true);
      this.g = this.e.P_();
      this.d = this.e.eb();
      this.h = 300;
      if (this.c) {
         this.h();
      }

      super.c();
   }

   protected void h() {
      double d0 = this.l();
      AxisAlignedBB axisalignedbb = AxisAlignedBB.a(this.e.de()).c(d0, 10.0, d0);
      List<? extends EntityInsentient> list = this.e.H.a(this.e.getClass(), axisalignedbb, IEntitySelector.f);
      Iterator iterator = list.iterator();

      while(true) {
         EntityInsentient entityinsentient;
         while(true) {
            if (!iterator.hasNext()) {
               return;
            }

            entityinsentient = (EntityInsentient)iterator.next();
            if (this.e != entityinsentient
               && entityinsentient.P_() == null
               && (!(this.e instanceof EntityTameableAnimal) || ((EntityTameableAnimal)this.e).H_() == ((EntityTameableAnimal)entityinsentient).H_())
               && !entityinsentient.p(this.e.ea())) {
               if (this.j == null) {
                  break;
               }

               boolean flag = false;

               for(Class<?> oclass : this.j) {
                  if (entityinsentient.getClass() == oclass) {
                     flag = true;
                     break;
                  }
               }

               if (!flag) {
                  break;
               }
            }
         }

         this.a(entityinsentient, this.e.ea());
      }
   }

   protected void a(EntityInsentient entityinsentient, EntityLiving entityliving) {
      entityinsentient.setTarget(entityliving, TargetReason.TARGET_ATTACKED_NEARBY_ENTITY, true);
   }
}
