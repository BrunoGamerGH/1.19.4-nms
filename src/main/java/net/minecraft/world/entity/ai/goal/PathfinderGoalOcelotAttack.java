package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;

public class PathfinderGoalOcelotAttack extends PathfinderGoal {
   private final EntityInsentient a;
   private EntityLiving b;
   private int c;

   public PathfinderGoalOcelotAttack(EntityInsentient var0) {
      this.a = var0;
      this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
   }

   @Override
   public boolean a() {
      EntityLiving var0 = this.a.P_();
      if (var0 == null) {
         return false;
      } else {
         this.b = var0;
         return true;
      }
   }

   @Override
   public boolean b() {
      if (!this.b.bq()) {
         return false;
      } else if (this.a.f(this.b) > 225.0) {
         return false;
      } else {
         return !this.a.G().l() || this.a();
      }
   }

   @Override
   public void d() {
      this.b = null;
      this.a.G().n();
   }

   @Override
   public boolean J_() {
      return true;
   }

   @Override
   public void e() {
      this.a.C().a(this.b, 30.0F, 30.0F);
      double var0 = (double)(this.a.dc() * 2.0F * this.a.dc() * 2.0F);
      double var2 = this.a.i(this.b.dl(), this.b.dn(), this.b.dr());
      double var4 = 0.8;
      if (var2 > var0 && var2 < 16.0) {
         var4 = 1.33;
      } else if (var2 < 225.0) {
         var4 = 0.6;
      }

      this.a.G().a(this.b, var4);
      this.c = Math.max(this.c - 1, 0);
      if (!(var2 > var0)) {
         if (this.c <= 0) {
            this.c = 20;
            this.a.z(this.b);
         }
      }
   }
}
