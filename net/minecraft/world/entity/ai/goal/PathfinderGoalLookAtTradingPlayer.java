package net.minecraft.world.entity.ai.goal;

import net.minecraft.world.entity.npc.EntityVillagerAbstract;
import net.minecraft.world.entity.player.EntityHuman;

public class PathfinderGoalLookAtTradingPlayer extends PathfinderGoalLookAtPlayer {
   private final EntityVillagerAbstract h;

   public PathfinderGoalLookAtTradingPlayer(EntityVillagerAbstract var0) {
      super(var0, EntityHuman.class, 8.0F);
      this.h = var0;
   }

   @Override
   public boolean a() {
      if (this.h.fT()) {
         this.c = this.h.fS();
         return true;
      } else {
         return false;
      }
   }
}
