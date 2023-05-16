package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.monster.IRangedEntity;

public class PathfinderGoalArrowAttack extends PathfinderGoal {
   private final EntityInsentient a;
   private final IRangedEntity b;
   @Nullable
   private EntityLiving c;
   private int d = -1;
   private final double e;
   private int f;
   private final int g;
   private final int h;
   private final float i;
   private final float j;

   public PathfinderGoalArrowAttack(IRangedEntity var0, double var1, int var3, float var4) {
      this(var0, var1, var3, var3, var4);
   }

   public PathfinderGoalArrowAttack(IRangedEntity var0, double var1, int var3, int var4, float var5) {
      if (!(var0 instanceof EntityLiving)) {
         throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
      } else {
         this.b = var0;
         this.a = (EntityInsentient)var0;
         this.e = var1;
         this.g = var3;
         this.h = var4;
         this.i = var5;
         this.j = var5 * var5;
         this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
      }
   }

   @Override
   public boolean a() {
      EntityLiving var0 = this.a.P_();
      if (var0 != null && var0.bq()) {
         this.c = var0;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean b() {
      return this.a() || this.c.bq() && !this.a.G().l();
   }

   @Override
   public void d() {
      this.c = null;
      this.f = 0;
      this.d = -1;
   }

   @Override
   public boolean J_() {
      return true;
   }

   @Override
   public void e() {
      double var0 = this.a.i(this.c.dl(), this.c.dn(), this.c.dr());
      boolean var2 = this.a.I().a(this.c);
      if (var2) {
         ++this.f;
      } else {
         this.f = 0;
      }

      if (!(var0 > (double)this.j) && this.f >= 5) {
         this.a.G().n();
      } else {
         this.a.G().a(this.c, this.e);
      }

      this.a.C().a(this.c, 30.0F, 30.0F);
      if (--this.d == 0) {
         if (!var2) {
            return;
         }

         float var3 = (float)Math.sqrt(var0) / this.i;
         float var4 = MathHelper.a(var3, 0.1F, 1.0F);
         this.b.a(this.c, var4);
         this.d = MathHelper.d(var3 * (float)(this.h - this.g) + (float)this.g);
      } else if (this.d < 0) {
         this.d = MathHelper.a(MathHelper.d(Math.sqrt(var0) / (double)this.i, (double)this.g, (double)this.h));
      }
   }
}
