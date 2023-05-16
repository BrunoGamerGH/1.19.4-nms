package net.minecraft.world.entity.ai.navigation;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityInsentient;
import net.minecraft.world.level.World;
import net.minecraft.world.level.pathfinder.AmphibiousNodeEvaluator;
import net.minecraft.world.level.pathfinder.Pathfinder;
import net.minecraft.world.phys.Vec3D;

public class AmphibiousPathNavigation extends NavigationAbstract {
   public AmphibiousPathNavigation(EntityInsentient var0, World var1) {
      super(var0, var1);
   }

   @Override
   protected Pathfinder a(int var0) {
      this.o = new AmphibiousNodeEvaluator(false);
      this.o.a(true);
      return new Pathfinder(this.o, var0);
   }

   @Override
   protected boolean a() {
      return true;
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
      return this.o() ? a(this.a, var0, var1, false) : false;
   }

   @Override
   public boolean a(BlockPosition var0) {
      return !this.b.a_(var0.d()).h();
   }

   @Override
   public void a(boolean var0) {
   }
}
