package net.minecraft.world.entity.ai.goal;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EnumMoveType;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.vehicle.EntityBoat;
import net.minecraft.world.phys.Vec3D;

public class PathfinderGoalFollowBoat extends PathfinderGoal {
   private int a;
   private final EntityCreature b;
   @Nullable
   private EntityHuman c;
   private PathfinderGoalBoat d;

   public PathfinderGoalFollowBoat(EntityCreature var0) {
      this.b = var0;
   }

   @Override
   public boolean a() {
      List<EntityBoat> var0 = this.b.H.a(EntityBoat.class, this.b.cD().g(5.0));
      boolean var1 = false;

      for(EntityBoat var3 : var0) {
         Entity var4 = var3.cK();
         if (var4 instanceof EntityHuman && (MathHelper.e(((EntityHuman)var4).bj) > 0.0F || MathHelper.e(((EntityHuman)var4).bl) > 0.0F)) {
            var1 = true;
            break;
         }
      }

      return this.c != null && (MathHelper.e(this.c.bj) > 0.0F || MathHelper.e(this.c.bl) > 0.0F) || var1;
   }

   @Override
   public boolean I_() {
      return true;
   }

   @Override
   public boolean b() {
      return this.c != null && this.c.bL() && (MathHelper.e(this.c.bj) > 0.0F || MathHelper.e(this.c.bl) > 0.0F);
   }

   @Override
   public void c() {
      for(EntityBoat var2 : this.b.H.a(EntityBoat.class, this.b.cD().g(5.0))) {
         if (var2.cK() != null && var2.cK() instanceof EntityHuman) {
            this.c = (EntityHuman)var2.cK();
            break;
         }
      }

      this.a = 0;
      this.d = PathfinderGoalBoat.a;
   }

   @Override
   public void d() {
      this.c = null;
   }

   @Override
   public void e() {
      boolean var0 = MathHelper.e(this.c.bj) > 0.0F || MathHelper.e(this.c.bl) > 0.0F;
      float var1 = this.d == PathfinderGoalBoat.b ? (var0 ? 0.01F : 0.0F) : 0.015F;
      this.b.a(var1, new Vec3D((double)this.b.bj, (double)this.b.bk, (double)this.b.bl));
      this.b.a(EnumMoveType.a, this.b.dj());
      if (--this.a <= 0) {
         this.a = this.a(10);
         if (this.d == PathfinderGoalBoat.a) {
            BlockPosition var2 = this.c.dg().a(this.c.cA().g());
            var2 = var2.b(0, -1, 0);
            this.b.G().a((double)var2.u(), (double)var2.v(), (double)var2.w(), 1.0);
            if (this.b.e(this.c) < 4.0F) {
               this.a = 0;
               this.d = PathfinderGoalBoat.b;
            }
         } else if (this.d == PathfinderGoalBoat.b) {
            EnumDirection var2 = this.c.cB();
            BlockPosition var3 = this.c.dg().a(var2, 10);
            this.b.G().a((double)var3.u(), (double)(var3.v() - 1), (double)var3.w(), 1.0);
            if (this.b.e(this.c) > 12.0F) {
               this.a = 0;
               this.d = PathfinderGoalBoat.a;
            }
         }
      }
   }
}
