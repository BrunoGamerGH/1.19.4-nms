package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.player.EntityHuman;

public class PathfinderGoalLookAtPlayer extends PathfinderGoal {
   public static final float a = 0.02F;
   protected final EntityInsentient b;
   @Nullable
   protected Entity c;
   protected final float d;
   private int h;
   protected final float e;
   private final boolean i;
   protected final Class<? extends EntityLiving> f;
   protected final PathfinderTargetCondition g;

   public PathfinderGoalLookAtPlayer(EntityInsentient var0, Class<? extends EntityLiving> var1, float var2) {
      this(var0, var1, var2, 0.02F);
   }

   public PathfinderGoalLookAtPlayer(EntityInsentient var0, Class<? extends EntityLiving> var1, float var2, float var3) {
      this(var0, var1, var2, var3, false);
   }

   public PathfinderGoalLookAtPlayer(EntityInsentient var0, Class<? extends EntityLiving> var1, float var2, float var3, boolean var4) {
      this.b = var0;
      this.f = var1;
      this.d = var2;
      this.e = var3;
      this.i = var4;
      this.a(EnumSet.of(PathfinderGoal.Type.b));
      if (var1 == EntityHuman.class) {
         this.g = PathfinderTargetCondition.b().a((double)var2).a(var1x -> IEntitySelector.b(var0).test(var1x));
      } else {
         this.g = PathfinderTargetCondition.b().a((double)var2);
      }
   }

   @Override
   public boolean a() {
      if (this.b.dZ().i() >= this.e) {
         return false;
      } else {
         if (this.b.P_() != null) {
            this.c = this.b.P_();
         }

         if (this.f == EntityHuman.class) {
            this.c = this.b.H.a(this.g, this.b, this.b.dl(), this.b.dp(), this.b.dr());
         } else {
            this.c = this.b
               .H
               .a(this.b.H.a(this.f, this.b.cD().c((double)this.d, 3.0, (double)this.d), var0 -> true), this.g, this.b, this.b.dl(), this.b.dp(), this.b.dr());
         }

         return this.c != null;
      }
   }

   @Override
   public boolean b() {
      if (!this.c.bq()) {
         return false;
      } else if (this.b.f(this.c) > (double)(this.d * this.d)) {
         return false;
      } else {
         return this.h > 0;
      }
   }

   @Override
   public void c() {
      this.h = this.a(40 + this.b.dZ().a(40));
   }

   @Override
   public void d() {
      this.c = null;
   }

   @Override
   public void e() {
      if (this.c.bq()) {
         double var0 = this.i ? this.b.dp() : this.c.dp();
         this.b.C().a(this.c.dl(), var0, this.c.dr());
         --this.h;
      }
   }
}
