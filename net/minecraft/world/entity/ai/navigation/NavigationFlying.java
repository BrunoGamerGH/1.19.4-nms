package net.minecraft.world.entity.ai.navigation;

import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.game.PacketDebug;
import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.level.World;
import net.minecraft.world.level.pathfinder.PathEntity;
import net.minecraft.world.level.pathfinder.Pathfinder;
import net.minecraft.world.level.pathfinder.PathfinderFlying;
import net.minecraft.world.phys.Vec3D;

public class NavigationFlying extends NavigationAbstract {
   public NavigationFlying(EntityInsentient var0, World var1) {
      super(var0, var1);
   }

   @Override
   protected Pathfinder a(int var0) {
      this.o = new PathfinderFlying();
      this.o.a(true);
      return new Pathfinder(this.o, var0);
   }

   @Override
   protected boolean a(Vec3D var0, Vec3D var1) {
      return a(this.a, var0, var1, true);
   }

   @Override
   protected boolean a() {
      return this.q() && this.o() || !this.a.bL();
   }

   @Override
   protected Vec3D b() {
      return this.a.de();
   }

   @Override
   public PathEntity a(Entity var0, int var1) {
      return this.a(var0.dg(), var1);
   }

   @Override
   public void c() {
      ++this.e;
      if (this.m) {
         this.i();
      }

      if (!this.l()) {
         if (this.a()) {
            this.k();
         } else if (this.c != null && !this.c.c()) {
            Vec3D var0 = this.c.a(this.a);
            if (this.a.dk() == MathHelper.a(var0.c) && this.a.dm() == MathHelper.a(var0.d) && this.a.dq() == MathHelper.a(var0.e)) {
               this.c.a();
            }
         }

         PacketDebug.a(this.b, this.a, this.c, this.l);
         if (!this.l()) {
            Vec3D var0 = this.c.a(this.a);
            this.a.D().a(var0.c, var0.d, var0.e, this.d);
         }
      }
   }

   public void b(boolean var0) {
      this.o.b(var0);
   }

   public boolean d() {
      return this.o.d();
   }

   public void c(boolean var0) {
      this.o.a(var0);
   }

   public boolean e() {
      return this.o.d();
   }

   @Override
   public boolean a(BlockPosition var0) {
      return this.b.a_(var0).a(this.b, var0, this.a);
   }
}
