package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.monster.IRangedEntity;
import net.minecraft.world.entity.projectile.ProjectileHelper;
import net.minecraft.world.item.ItemBow;
import net.minecraft.world.item.Items;

public class PathfinderGoalBowShoot<T extends EntityMonster & IRangedEntity> extends PathfinderGoal {
   private final T a;
   private final double b;
   private int c;
   private final float d;
   private int e = -1;
   private int f;
   private boolean g;
   private boolean h;
   private int i = -1;

   public PathfinderGoalBowShoot(T var0, double var1, int var3, float var4) {
      this.a = var0;
      this.b = var1;
      this.c = var3;
      this.d = var4 * var4;
      this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
   }

   public void c(int var0) {
      this.c = var0;
   }

   @Override
   public boolean a() {
      return this.a.P_() == null ? false : this.h();
   }

   protected boolean h() {
      return this.a.b(Items.nC);
   }

   @Override
   public boolean b() {
      return (this.a() || !this.a.G().l()) && this.h();
   }

   @Override
   public void c() {
      super.c();
      this.a.v(true);
   }

   @Override
   public void d() {
      super.d();
      this.a.v(false);
      this.f = 0;
      this.e = -1;
      this.a.fk();
   }

   @Override
   public boolean J_() {
      return true;
   }

   @Override
   public void e() {
      EntityLiving var0 = this.a.P_();
      if (var0 != null) {
         double var1 = this.a.i(var0.dl(), var0.dn(), var0.dr());
         boolean var3 = this.a.I().a(var0);
         boolean var4 = this.f > 0;
         if (var3 != var4) {
            this.f = 0;
         }

         if (var3) {
            ++this.f;
         } else {
            --this.f;
         }

         if (!(var1 > (double)this.d) && this.f >= 20) {
            this.a.G().n();
            ++this.i;
         } else {
            this.a.G().a(var0, this.b);
            this.i = -1;
         }

         if (this.i >= 20) {
            if ((double)this.a.dZ().i() < 0.3) {
               this.g = !this.g;
            }

            if ((double)this.a.dZ().i() < 0.3) {
               this.h = !this.h;
            }

            this.i = 0;
         }

         if (this.i > -1) {
            if (var1 > (double)(this.d * 0.75F)) {
               this.h = false;
            } else if (var1 < (double)(this.d * 0.25F)) {
               this.h = true;
            }

            this.a.D().a(this.h ? -0.5F : 0.5F, this.g ? 0.5F : -0.5F);
            this.a.a(var0, 30.0F, 30.0F);
         } else {
            this.a.C().a(var0, 30.0F, 30.0F);
         }

         if (this.a.fe()) {
            if (!var3 && this.f < -60) {
               this.a.fk();
            } else if (var3) {
               int var5 = this.a.fi();
               if (var5 >= 20) {
                  this.a.fk();
                  this.a.a(var0, ItemBow.a(var5));
                  this.e = this.c;
               }
            }
         } else if (--this.e <= 0 && this.f >= -60) {
            this.a.c(ProjectileHelper.a(this.a, Items.nC));
         }
      }
   }
}
