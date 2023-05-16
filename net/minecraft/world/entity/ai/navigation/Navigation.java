package net.minecraft.world.entity.ai.navigation;

import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.level.pathfinder.PathPoint;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.Pathfinder;
import net.minecraft.world.level.pathfinder.PathfinderNormal;
import net.minecraft.world.phys.Vec3D;

public class Navigation extends NavigationAbstract {
   private boolean p;

   public Navigation(EntityInsentient var0, World var1) {
      super(var0, var1);
   }

   @Override
   protected Pathfinder a(int var0) {
      this.o = new PathfinderNormal();
      this.o.a(true);
      return new Pathfinder(this.o, var0);
   }

   @Override
   protected boolean a() {
      return this.a.ax() || this.o() || this.a.bL();
   }

   @Override
   protected Vec3D b() {
      return new Vec3D(this.a.dl(), (double)this.t(), this.a.dr());
   }

   @Override
   public PathEntity a(BlockPosition var0, int var1) {
      if (this.b.a_(var0).h()) {
         BlockPosition var2 = var0.d();

         while(var2.v() > this.b.v_() && this.b.a_(var2).h()) {
            var2 = var2.d();
         }

         if (var2.v() > this.b.v_()) {
            return super.a(var2.c(), var1);
         }

         while(var2.v() < this.b.ai() && this.b.a_(var2).h()) {
            var2 = var2.c();
         }

         var0 = var2;
      }

      if (!this.b.a_(var0).d().b()) {
         return super.a(var0, var1);
      } else {
         BlockPosition var2 = var0.c();

         while(var2.v() < this.b.ai() && this.b.a_(var2).d().b()) {
            var2 = var2.c();
         }

         return super.a(var2, var1);
      }
   }

   @Override
   public PathEntity a(Entity var0, int var1) {
      return this.a(var0.dg(), var1);
   }

   private int t() {
      if (this.a.aT() && this.q()) {
         int var0 = this.a.dm();
         IBlockData var1 = this.b.a_(BlockPosition.a(this.a.dl(), (double)var0, this.a.dr()));
         int var2 = 0;

         while(var1.a(Blocks.G)) {
            var1 = this.b.a_(BlockPosition.a(this.a.dl(), (double)(++var0), this.a.dr()));
            if (++var2 > 16) {
               return this.a.dm();
            }
         }

         return var0;
      } else {
         return MathHelper.a(this.a.dn() + 0.5);
      }
   }

   @Override
   protected void K_() {
      super.K_();
      if (this.p) {
         if (this.b.g(BlockPosition.a(this.a.dl(), this.a.dn() + 0.5, this.a.dr()))) {
            return;
         }

         for(int var0 = 0; var0 < this.c.e(); ++var0) {
            PathPoint var1 = this.c.a(var0);
            if (this.b.g(new BlockPosition(var1.a, var1.b, var1.c))) {
               this.c.b(var0);
               return;
            }
         }
      }
   }

   protected boolean a(PathType var0) {
      if (var0 == PathType.j) {
         return false;
      } else if (var0 == PathType.i) {
         return false;
      } else {
         return var0 != PathType.b;
      }
   }

   public void b(boolean var0) {
      this.o.b(var0);
   }

   public boolean e() {
      return this.o.d();
   }

   public void c(boolean var0) {
      this.o.a(var0);
   }

   public boolean f() {
      return this.o.d();
   }

   public void d(boolean var0) {
      this.p = var0;
   }

   public void e(boolean var0) {
      this.o.d(var0);
   }
}
