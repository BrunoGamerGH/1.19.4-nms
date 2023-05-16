package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.horse.EntityLlama;
import net.minecraft.world.entity.decoration.EntityLeash;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalLlamaFollow extends PathfinderGoal {
   public final EntityLlama a;
   private double b;
   private static final int c = 8;
   private int d;

   public PathfinderGoalLlamaFollow(EntityLlama var0, double var1) {
      this.a = var0;
      this.b = var1;
      this.a(EnumSet.of(PathfinderGoal.Type.a));
   }

   @Override
   public boolean a() {
      if (!this.a.fI() && !this.a.gG()) {
         List<Entity> var0 = this.a.H.a(this.a, this.a.cD().c(9.0, 4.0, 9.0), var0x -> {
            EntityTypes<?> var1x = var0x.ae();
            return var1x == EntityTypes.aj || var1x == EntityTypes.ba;
         });
         EntityLlama var1 = null;
         double var2 = Double.MAX_VALUE;

         for(Entity var5 : var0) {
            EntityLlama var6 = (EntityLlama)var5;
            if (var6.gG() && !var6.gF()) {
               double var7 = this.a.f(var6);
               if (!(var7 > var2)) {
                  var2 = var7;
                  var1 = var6;
               }
            }
         }

         if (var1 == null) {
            for(Entity var5 : var0) {
               EntityLlama var6 = (EntityLlama)var5;
               if (var6.fI() && !var6.gF()) {
                  double var7 = this.a.f(var6);
                  if (!(var7 > var2)) {
                     var2 = var7;
                     var1 = var6;
                  }
               }
            }
         }

         if (var1 == null) {
            return false;
         } else if (var2 < 4.0) {
            return false;
         } else if (!var1.fI() && !this.a(var1, 1)) {
            return false;
         } else {
            this.a.a(var1);
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean b() {
      if (this.a.gG() && this.a.gH().bq() && this.a(this.a, 0)) {
         double var0 = this.a.f(this.a.gH());
         if (var0 > 676.0) {
            if (this.b <= 3.0) {
               this.b *= 1.2;
               this.d = b(40);
               return true;
            }

            if (this.d == 0) {
               return false;
            }
         }

         if (this.d > 0) {
            --this.d;
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void d() {
      this.a.gE();
      this.b = 2.1;
   }

   @Override
   public void e() {
      if (this.a.gG()) {
         if (!(this.a.fJ() instanceof EntityLeash)) {
            EntityLlama var0 = this.a.gH();
            double var1 = (double)this.a.e(var0);
            float var3 = 2.0F;
            Vec3D var4 = new Vec3D(var0.dl() - this.a.dl(), var0.dn() - this.a.dn(), var0.dr() - this.a.dr()).d().a(Math.max(var1 - 2.0, 0.0));
            this.a.G().a(this.a.dl() + var4.c, this.a.dn() + var4.d, this.a.dr() + var4.e, this.b);
         }
      }
   }

   private boolean a(EntityLlama var0, int var1) {
      if (var1 > 8) {
         return false;
      } else if (var0.gG()) {
         return var0.gH().fI() ? true : this.a(var0.gH(), ++var1);
      } else {
         return false;
      }
   }
}
