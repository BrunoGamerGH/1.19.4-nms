package net.minecraft.world.entity.ai.goal;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.ai.navigation.Navigation;
import net.minecraft.world.entity.ai.util.PathfinderGoalUtil;
import net.minecraft.world.level.block.BlockDoor;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.level.pathfinder.PathPoint;

public abstract class PathfinderGoalDoorInteract extends PathfinderGoal {
   protected EntityInsentient d;
   protected BlockPosition e = BlockPosition.b;
   protected boolean f;
   private boolean a;
   private float b;
   private float c;

   public PathfinderGoalDoorInteract(EntityInsentient var0) {
      this.d = var0;
      if (!PathfinderGoalUtil.a(var0)) {
         throw new IllegalArgumentException("Unsupported mob type for DoorInteractGoal");
      }
   }

   protected boolean h() {
      if (!this.f) {
         return false;
      } else {
         IBlockData var0 = this.d.H.a_(this.e);
         if (!(var0.b() instanceof BlockDoor)) {
            this.f = false;
            return false;
         } else {
            return var0.c(BlockDoor.b);
         }
      }
   }

   protected void a(boolean var0) {
      if (this.f) {
         IBlockData var1 = this.d.H.a_(this.e);
         if (var1.b() instanceof BlockDoor) {
            ((BlockDoor)var1.b()).a(this.d, this.d.H, var1, this.e, var0);
         }
      }
   }

   @Override
   public boolean a() {
      if (!PathfinderGoalUtil.a(this.d)) {
         return false;
      } else if (!this.d.O) {
         return false;
      } else {
         Navigation var0 = (Navigation)this.d.G();
         PathEntity var1 = var0.j();
         if (var1 != null && !var1.c() && var0.f()) {
            for(int var2 = 0; var2 < Math.min(var1.f() + 2, var1.e()); ++var2) {
               PathPoint var3 = var1.a(var2);
               this.e = new BlockPosition(var3.a, var3.b + 1, var3.c);
               if (!(this.d.i((double)this.e.u(), this.d.dn(), (double)this.e.w()) > 2.25)) {
                  this.f = BlockDoor.a(this.d.H, this.e);
                  if (this.f) {
                     return true;
                  }
               }
            }

            this.e = this.d.dg().c();
            this.f = BlockDoor.a(this.d.H, this.e);
            return this.f;
         } else {
            return false;
         }
      }
   }

   @Override
   public boolean b() {
      return !this.a;
   }

   @Override
   public void c() {
      this.a = false;
      this.b = (float)((double)this.e.u() + 0.5 - this.d.dl());
      this.c = (float)((double)this.e.w() + 0.5 - this.d.dr());
   }

   @Override
   public boolean J_() {
      return true;
   }

   @Override
   public void e() {
      float var0 = (float)((double)this.e.u() + 0.5 - this.d.dl());
      float var1 = (float)((double)this.e.w() + 0.5 - this.d.dr());
      float var2 = this.b * var0 + this.c * var1;
      if (var2 < 0.0F) {
         this.a = true;
      }
   }
}
