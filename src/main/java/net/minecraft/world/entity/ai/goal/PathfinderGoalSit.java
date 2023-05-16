package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTameableAnimal;

public class PathfinderGoalSit extends PathfinderGoal {
   private final EntityTameableAnimal a;

   public PathfinderGoalSit(EntityTameableAnimal entitytameableanimal) {
      this.a = entitytameableanimal;
      this.a(EnumSet.of(PathfinderGoal.Type.c, PathfinderGoal.Type.a));
   }

   @Override
   public boolean b() {
      return this.a.fS();
   }

   @Override
   public boolean a() {
      if (!this.a.q()) {
         return this.a.fS() && this.a.P_() == null;
      } else if (this.a.aW()) {
         return false;
      } else if (!this.a.ax()) {
         return false;
      } else {
         EntityLiving entityliving = this.a.H_();
         return entityliving == null ? true : (this.a.f(entityliving) < 144.0 && entityliving.ea() != null ? false : this.a.fS());
      }
   }

   @Override
   public void c() {
      this.a.G().n();
      this.a.y(true);
   }

   @Override
   public void d() {
      this.a.y(false);
   }
}
