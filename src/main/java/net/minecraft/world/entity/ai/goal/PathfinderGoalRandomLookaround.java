package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.EntityInsentient;

public class PathfinderGoalRandomLookaround extends PathfinderGoal {
   private final EntityInsentient a;
   private double b;
   private double c;
   private int d;

   public PathfinderGoalRandomLookaround(EntityInsentient var0) {
      this.a = var0;
      this.a(EnumSet.of(PathfinderGoal.Type.a, PathfinderGoal.Type.b));
   }

   @Override
   public boolean a() {
      return this.a.dZ().i() < 0.02F;
   }

   @Override
   public boolean b() {
      return this.d >= 0;
   }

   @Override
   public void c() {
      double var0 = (Math.PI * 2) * this.a.dZ().j();
      this.b = Math.cos(var0);
      this.c = Math.sin(var0);
      this.d = 20 + this.a.dZ().a(20);
   }

   @Override
   public boolean J_() {
      return true;
   }

   @Override
   public void e() {
      --this.d;
      this.a.C().a(this.a.dl() + this.b, this.a.dp(), this.a.dr() + this.c);
   }
}
