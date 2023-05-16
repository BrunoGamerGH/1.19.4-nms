package net.minecraft.world.entity.ai.goal;

import net.minecraft.world.entity.monster.EntityZombie;

public class PathfinderGoalZombieAttack extends PathfinderGoalMeleeAttack {
   private final EntityZombie b;
   private int c;

   public PathfinderGoalZombieAttack(EntityZombie var0, double var1, boolean var3) {
      super(var0, var1, var3);
      this.b = var0;
   }

   @Override
   public void c() {
      super.c();
      this.c = 0;
   }

   @Override
   public void d() {
      super.d();
      this.b.v(false);
   }

   @Override
   public void e() {
      super.e();
      ++this.c;
      if (this.c >= 5 && this.k() < this.l() / 2) {
         this.b.v(true);
      } else {
         this.b.v(false);
      }
   }
}
