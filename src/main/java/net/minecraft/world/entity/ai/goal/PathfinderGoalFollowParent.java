package net.minecraft.world.entity.ai.goal;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.world.entity.animal.EntityAnimal;

public class PathfinderGoalFollowParent extends PathfinderGoal {
   public static final int a = 8;
   public static final int b = 4;
   public static final int c = 3;
   private final EntityAnimal d;
   @Nullable
   private EntityAnimal e;
   private final double f;
   private int g;

   public PathfinderGoalFollowParent(EntityAnimal var0, double var1) {
      this.d = var0;
      this.f = var1;
   }

   @Override
   public boolean a() {
      if (this.d.h() >= 0) {
         return false;
      } else {
         List<? extends EntityAnimal> var0 = this.d.H.a(this.d.getClass(), this.d.cD().c(8.0, 4.0, 8.0));
         EntityAnimal var1 = null;
         double var2 = Double.MAX_VALUE;

         for(EntityAnimal var5 : var0) {
            if (var5.h() >= 0) {
               double var6 = this.d.f(var5);
               if (!(var6 > var2)) {
                  var2 = var6;
                  var1 = var5;
               }
            }
         }

         if (var1 == null) {
            return false;
         } else if (var2 < 9.0) {
            return false;
         } else {
            this.e = var1;
            return true;
         }
      }
   }

   @Override
   public boolean b() {
      if (this.d.h() >= 0) {
         return false;
      } else if (!this.e.bq()) {
         return false;
      } else {
         double var0 = this.d.f(this.e);
         return !(var0 < 9.0) && !(var0 > 256.0);
      }
   }

   @Override
   public void c() {
      this.g = 0;
   }

   @Override
   public void d() {
      this.e = null;
   }

   @Override
   public void e() {
      if (--this.g <= 0) {
         this.g = this.a(10);
         this.d.G().a(this.e, this.f);
      }
   }
}
