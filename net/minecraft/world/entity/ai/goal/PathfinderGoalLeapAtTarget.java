package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalLeapAtTarget extends PathfinderGoal {
   private final EntityInsentient a;
   private EntityLiving b;
   private final float c;

   public PathfinderGoalLeapAtTarget(EntityInsentient var0, float var1) {
      this.a = var0;
      this.c = var1;
      this.a(EnumSet.of(PathfinderGoal.Type.c, PathfinderGoal.Type.a));
   }

   @Override
   public boolean a() {
      if (this.a.bM()) {
         return false;
      } else {
         this.b = this.a.P_();
         if (this.b == null) {
            return false;
         } else {
            double var0 = this.a.f(this.b);
            if (var0 < 4.0 || var0 > 16.0) {
               return false;
            } else if (!this.a.ax()) {
               return false;
            } else {
               return this.a.dZ().a(b(5)) == 0;
            }
         }
      }
   }

   @Override
   public boolean b() {
      return !this.a.ax();
   }

   @Override
   public void c() {
      Vec3D var0 = this.a.dj();
      Vec3D var1 = new Vec3D(this.b.dl() - this.a.dl(), 0.0, this.b.dr() - this.a.dr());
      if (var1.g() > 1.0E-7) {
         var1 = var1.d().a(0.4).e(var0.a(0.2));
      }

      this.a.o(var1.c, (double)this.c, var1.e);
   }
}
