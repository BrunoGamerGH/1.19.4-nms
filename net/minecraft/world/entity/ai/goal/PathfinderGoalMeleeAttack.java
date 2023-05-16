package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.pathfinder.PathEntity;

public class PathfinderGoalMeleeAttack extends PathfinderGoal {
   protected final EntityCreature a;
   private final double b;
   private final boolean c;
   private PathEntity d;
   private double e;
   private double f;
   private double g;
   private int h;
   private int i;
   private final int j = 20;
   private long k;
   private static final long l = 20L;

   public PathfinderGoalMeleeAttack(EntityCreature var0, double var1, boolean var3) {
      this.a = var0;
      this.b = var1;
      this.c = var3;
      this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
   }

   @Override
   public boolean a() {
      long var0 = this.a.H.U();
      if (var0 - this.k < 20L) {
         return false;
      } else {
         this.k = var0;
         EntityLiving var2 = this.a.P_();
         if (var2 == null) {
            return false;
         } else if (!var2.bq()) {
            return false;
         } else {
            this.d = this.a.G().a(var2, 0);
            if (this.d != null) {
               return true;
            } else {
               return this.a(var2) >= this.a.i(var2.dl(), var2.dn(), var2.dr());
            }
         }
      }
   }

   @Override
   public boolean b() {
      EntityLiving var0 = this.a.P_();
      if (var0 == null) {
         return false;
      } else if (!var0.bq()) {
         return false;
      } else if (!this.c) {
         return !this.a.G().l();
      } else if (!this.a.a(var0.dg())) {
         return false;
      } else {
         return !(var0 instanceof EntityHuman) || !var0.F_() && !((EntityHuman)var0).f();
      }
   }

   @Override
   public void c() {
      this.a.G().a(this.d, this.b);
      this.a.v(true);
      this.h = 0;
      this.i = 0;
   }

   @Override
   public void d() {
      EntityLiving var0 = this.a.P_();
      if (!IEntitySelector.e.test(var0)) {
         this.a.i(null);
      }

      this.a.v(false);
      this.a.G().n();
   }

   @Override
   public boolean J_() {
      return true;
   }

   @Override
   public void e() {
      EntityLiving var0 = this.a.P_();
      if (var0 != null) {
         this.a.C().a(var0, 30.0F, 30.0F);
         double var1 = this.a.k(var0);
         this.h = Math.max(this.h - 1, 0);
         if ((this.c || this.a.I().a(var0))
            && this.h <= 0
            && (this.e == 0.0 && this.f == 0.0 && this.g == 0.0 || var0.i(this.e, this.f, this.g) >= 1.0 || this.a.dZ().i() < 0.05F)) {
            this.e = var0.dl();
            this.f = var0.dn();
            this.g = var0.dr();
            this.h = 4 + this.a.dZ().a(7);
            if (var1 > 1024.0) {
               this.h += 10;
            } else if (var1 > 256.0) {
               this.h += 5;
            }

            if (!this.a.G().a(var0, this.b)) {
               this.h += 15;
            }

            this.h = this.a(this.h);
         }

         this.i = Math.max(this.i - 1, 0);
         this.a(var0, var1);
      }
   }

   protected void a(EntityLiving var0, double var1) {
      double var3 = this.a(var0);
      if (var1 <= var3 && this.i <= 0) {
         this.h();
         this.a.a(EnumHand.a);
         this.a.z(var0);
      }
   }

   protected void h() {
      this.i = this.a(20);
   }

   protected boolean i() {
      return this.i <= 0;
   }

   protected int k() {
      return this.i;
   }

   protected int l() {
      return this.a(20);
   }

   protected double a(EntityLiving var0) {
      return (double)(this.a.dc() * 2.0F * this.a.dc() * 2.0F + var0.dc());
   }
}
