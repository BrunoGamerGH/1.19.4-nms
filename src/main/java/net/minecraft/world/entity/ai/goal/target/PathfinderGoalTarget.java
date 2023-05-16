package net.minecraft.world.entity.ai.goal.target;

import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.entity.ai.goal.PathfinderGoal;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.level.pathfinder.PathPoint;
import net.minecraft.world.scores.ScoreboardTeamBase;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public abstract class PathfinderGoalTarget extends PathfinderGoal {
   private static final int a = 0;
   private static final int b = 1;
   private static final int c = 2;
   protected final EntityInsentient e;
   protected final boolean f;
   private final boolean d;
   private int i;
   private int j;
   private int k;
   @Nullable
   protected EntityLiving g;
   protected int h = 60;

   public PathfinderGoalTarget(EntityInsentient entityinsentient, boolean flag) {
      this(entityinsentient, flag, false);
   }

   public PathfinderGoalTarget(EntityInsentient entityinsentient, boolean flag, boolean flag1) {
      this.e = entityinsentient;
      this.f = flag;
      this.d = flag1;
   }

   @Override
   public boolean b() {
      EntityLiving entityliving = this.e.P_();
      if (entityliving == null) {
         entityliving = this.g;
      }

      if (entityliving == null) {
         return false;
      } else if (!this.e.c(entityliving)) {
         return false;
      } else {
         ScoreboardTeamBase scoreboardteambase = this.e.cb();
         ScoreboardTeamBase scoreboardteambase1 = entityliving.cb();
         if (scoreboardteambase != null && scoreboardteambase1 == scoreboardteambase) {
            return false;
         } else {
            double d0 = this.l();
            if (this.e.f(entityliving) > d0 * d0) {
               return false;
            } else {
               if (this.f) {
                  if (this.e.I().a(entityliving)) {
                     this.k = 0;
                  } else if (++this.k > b(this.h)) {
                     return false;
                  }
               }

               this.e.setTarget(entityliving, TargetReason.CLOSEST_ENTITY, true);
               return true;
            }
         }
      }
   }

   protected double l() {
      return this.e.b(GenericAttributes.b);
   }

   @Override
   public void c() {
      this.i = 0;
      this.j = 0;
      this.k = 0;
   }

   @Override
   public void d() {
      this.e.setTarget(null, TargetReason.FORGOT_TARGET, true);
      this.g = null;
   }

   protected boolean a(@Nullable EntityLiving entityliving, PathfinderTargetCondition pathfindertargetcondition) {
      if (entityliving == null) {
         return false;
      } else if (!pathfindertargetcondition.a(this.e, entityliving)) {
         return false;
      } else if (!this.e.a(entityliving.dg())) {
         return false;
      } else {
         if (this.d) {
            if (--this.j <= 0) {
               this.i = 0;
            }

            if (this.i == 0) {
               this.i = this.a(entityliving) ? 1 : 2;
            }

            if (this.i == 2) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean a(EntityLiving entityliving) {
      this.j = b(10 + this.e.dZ().a(5));
      PathEntity pathentity = this.e.G().a(entityliving, 0);
      if (pathentity == null) {
         return false;
      } else {
         PathPoint pathpoint = pathentity.d();
         if (pathpoint == null) {
            return false;
         } else {
            int i = pathpoint.a - entityliving.dk();
            int j = pathpoint.c - entityliving.dq();
            return (double)(i * i + j * j) <= 2.25;
         }
      }
   }

   public PathfinderGoalTarget c(int i) {
      this.h = i;
      return this;
   }
}
