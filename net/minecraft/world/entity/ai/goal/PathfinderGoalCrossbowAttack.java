package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.util.TimeRange;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.monster.EntityMonster;
import net.minecraft.world.entity.monster.ICrossbow;
import net.minecraft.world.entity.monster.IRangedEntity;
import net.minecraft.world.entity.projectile.ProjectileHelper;
import net.minecraft.world.item.ItemCrossbow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PathfinderGoalCrossbowAttack<T extends EntityMonster & IRangedEntity & ICrossbow> extends PathfinderGoal {
   public static final UniformInt a = TimeRange.a(1, 2);
   private final T b;
   private PathfinderGoalCrossbowAttack.State c = PathfinderGoalCrossbowAttack.State.a;
   private final double d;
   private final float e;
   private int f;
   private int g;
   private int h;

   public PathfinderGoalCrossbowAttack(T var0, double var1, float var3) {
      this.b = var0;
      this.d = var1;
      this.e = var3 * var3;
      this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
   }

   @Override
   public boolean a() {
      return this.i() && this.h();
   }

   private boolean h() {
      return this.b.b(Items.uT);
   }

   @Override
   public boolean b() {
      return this.i() && (this.a() || !this.b.G().l()) && this.h();
   }

   private boolean i() {
      return this.b.P_() != null && this.b.P_().bq();
   }

   @Override
   public void d() {
      super.d();
      this.b.v(false);
      this.b.i(null);
      this.f = 0;
      if (this.b.fe()) {
         this.b.fk();
         this.b.b(false);
         ItemCrossbow.a(this.b.fg(), false);
      }
   }

   @Override
   public boolean J_() {
      return true;
   }

   @Override
   public void e() {
      EntityLiving var0 = this.b.P_();
      if (var0 != null) {
         boolean var1 = this.b.I().a(var0);
         boolean var2 = this.f > 0;
         if (var1 != var2) {
            this.f = 0;
         }

         if (var1) {
            ++this.f;
         } else {
            --this.f;
         }

         double var3 = this.b.f(var0);
         boolean var5 = (var3 > (double)this.e || this.f < 5) && this.g == 0;
         if (var5) {
            --this.h;
            if (this.h <= 0) {
               this.b.G().a(var0, this.k() ? this.d : this.d * 0.5);
               this.h = a.a(this.b.dZ());
            }
         } else {
            this.h = 0;
            this.b.G().n();
         }

         this.b.C().a(var0, 30.0F, 30.0F);
         if (this.c == PathfinderGoalCrossbowAttack.State.a) {
            if (!var5) {
               this.b.c(ProjectileHelper.a(this.b, Items.uT));
               this.c = PathfinderGoalCrossbowAttack.State.b;
               this.b.b(true);
            }
         } else if (this.c == PathfinderGoalCrossbowAttack.State.b) {
            if (!this.b.fe()) {
               this.c = PathfinderGoalCrossbowAttack.State.a;
            }

            int var6 = this.b.fi();
            ItemStack var7 = this.b.fg();
            if (var6 >= ItemCrossbow.k(var7)) {
               this.b.fj();
               this.c = PathfinderGoalCrossbowAttack.State.c;
               this.g = 20 + this.b.dZ().a(20);
               this.b.b(false);
            }
         } else if (this.c == PathfinderGoalCrossbowAttack.State.c) {
            --this.g;
            if (this.g == 0) {
               this.c = PathfinderGoalCrossbowAttack.State.d;
            }
         } else if (this.c == PathfinderGoalCrossbowAttack.State.d && var1) {
            this.b.a(var0, 1.0F);
            ItemStack var6 = this.b.b(ProjectileHelper.a(this.b, Items.uT));
            ItemCrossbow.a(var6, false);
            this.c = PathfinderGoalCrossbowAttack.State.a;
         }
      }
   }

   private boolean k() {
      return this.c == PathfinderGoalCrossbowAttack.State.a;
   }

   static enum State {
      a,
      b,
      c,
      d;
   }
}
