package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalMoveTowardsTarget extends PathfinderGoal {
   private final EntityCreature a;
   @Nullable
   private EntityLiving b;
   private double c;
   private double d;
   private double e;
   private final double f;
   private final float g;

   public PathfinderGoalMoveTowardsTarget(EntityCreature var0, double var1, float var3) {
      this.a = var0;
      this.f = var1;
      this.g = var3;
      this.a(EnumSet.of(PathfinderGoal.Type.a));
   }

   @Override
   public boolean a() {
      this.b = this.a.P_();
      if (this.b == null) {
         return false;
      } else if (this.b.f(this.a) > (double)(this.g * this.g)) {
         return false;
      } else {
         Vec3D var0 = DefaultRandomPos.a(this.a, 16, 7, this.b.de(), (float) (Math.PI / 2));
         if (var0 == null) {
            return false;
         } else {
            this.c = var0.c;
            this.d = var0.d;
            this.e = var0.e;
            return true;
         }
      }
   }

   @Override
   public boolean b() {
      return !this.a.G().l() && this.b.bq() && this.b.f(this.a) < (double)(this.g * this.g);
   }

   @Override
   public void d() {
      this.b = null;
   }

   @Override
   public void c() {
      this.a.G().a(this.c, this.d, this.e, this.f);
   }
}
