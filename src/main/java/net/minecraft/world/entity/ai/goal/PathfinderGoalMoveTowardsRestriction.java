package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalMoveTowardsRestriction extends PathfinderGoal {
   private final EntityCreature a;
   private double b;
   private double c;
   private double d;
   private final double e;

   public PathfinderGoalMoveTowardsRestriction(EntityCreature var0, double var1) {
      this.a = var0;
      this.e = var1;
      this.a(EnumSet.of(PathfinderGoal.Type.a));
   }

   @Override
   public boolean a() {
      if (this.a.fC()) {
         return false;
      } else {
         Vec3D var0 = DefaultRandomPos.a(this.a, 16, 7, Vec3D.c(this.a.fD()), (float) (Math.PI / 2));
         if (var0 == null) {
            return false;
         } else {
            this.b = var0.c;
            this.c = var0.d;
            this.d = var0.e;
            return true;
         }
      }
   }

   @Override
   public boolean b() {
      return !this.a.G().l();
   }

   @Override
   public void c() {
      this.a.G().a(this.b, this.c, this.d, this.e);
   }
}
