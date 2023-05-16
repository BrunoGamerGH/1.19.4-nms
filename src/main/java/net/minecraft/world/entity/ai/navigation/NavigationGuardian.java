package net.minecraft.world.entity.ai.navigation;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.World;
import net.minecraft.world.level.pathfinder.Pathfinder;
import net.minecraft.world.level.pathfinder.PathfinderWater;
import net.minecraft.world.phys.Vec3D;

public class NavigationGuardian extends NavigationAbstract {
   private boolean p;

   public NavigationGuardian(EntityInsentient var0, World var1) {
      super(var0, var1);
   }

   @Override
   protected Pathfinder a(int var0) {
      this.p = this.a.ae() == EntityTypes.v;
      this.o = new PathfinderWater(this.p);
      return new Pathfinder(this.o, var0);
   }

   @Override
   protected boolean a() {
      return this.p || this.o();
   }

   @Override
   protected Vec3D b() {
      return new Vec3D(this.a.dl(), this.a.e(0.5), this.a.dr());
   }

   @Override
   protected double a(Vec3D var0) {
      return var0.d;
   }

   @Override
   protected boolean a(Vec3D var0, Vec3D var1) {
      return a(this.a, var0, var1, false);
   }

   @Override
   public boolean a(BlockPosition var0) {
      return !this.b.a_(var0).i(this.b, var0);
   }

   @Override
   public void a(boolean var0) {
   }
}
