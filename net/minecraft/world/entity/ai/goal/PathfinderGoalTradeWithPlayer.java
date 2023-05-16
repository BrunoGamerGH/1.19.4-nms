package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.player.EntityHuman;

public class PathfinderGoalTradeWithPlayer extends PathfinderGoal {
   private final EntityVillagerAbstract a;

   public PathfinderGoalTradeWithPlayer(EntityVillagerAbstract var0) {
      this.a = var0;
      this.a(EnumSet.of(PathfinderGoal.Type.c, PathfinderGoal.Type.a));
   }

   @Override
   public boolean a() {
      if (!this.a.bq()) {
         return false;
      } else if (this.a.aT()) {
         return false;
      } else if (!this.a.ax()) {
         return false;
      } else if (this.a.S) {
         return false;
      } else {
         EntityHuman var0 = this.a.fS();
         if (var0 == null) {
            return false;
         } else if (this.a.f(var0) > 16.0) {
            return false;
         } else {
            return var0.bP != null;
         }
      }
   }

   @Override
   public void c() {
      this.a.G().n();
   }

   @Override
   public void d() {
      this.a.e(null);
   }
}
