package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.world.EnumHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.targeting.PathfinderTargetCondition;
import net.minecraft.world.entity.animal.EntityWolf;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;

public class PathfinderGoalBeg extends PathfinderGoal {
   private final EntityWolf a;
   @Nullable
   private EntityHuman b;
   private final World c;
   private final float d;
   private int e;
   private final PathfinderTargetCondition f;

   public PathfinderGoalBeg(EntityWolf var0, float var1) {
      this.a = var0;
      this.c = var0.H;
      this.d = var1;
      this.f = PathfinderTargetCondition.b().a((double)var1);
      this.a(EnumSet.of(PathfinderGoal.Type.b));
   }

   @Override
   public boolean a() {
      this.b = this.c.a(this.f, this.a);
      return this.b == null ? false : this.a(this.b);
   }

   @Override
   public boolean b() {
      if (!this.b.bq()) {
         return false;
      } else if (this.a.f((Entity)this.b) > (double)(this.d * this.d)) {
         return false;
      } else {
         return this.e > 0 && this.a(this.b);
      }
   }

   @Override
   public void c() {
      this.a.A(true);
      this.e = this.a(40 + this.a.dZ().a(40));
   }

   @Override
   public void d() {
      this.a.A(false);
      this.b = null;
   }

   @Override
   public void e() {
      this.a.C().a(this.b.dl(), this.b.dp(), this.b.dr(), 10.0F, (float)this.a.V());
      --this.e;
   }

   private boolean a(EntityHuman var0) {
      for(EnumHand var4 : EnumHand.values()) {
         ItemStack var5 = var0.b(var4);
         if (this.a.q() && var5.a(Items.qH)) {
            return true;
         }

         if (this.a.m(var5)) {
            return true;
         }
      }

      return false;
   }
}
