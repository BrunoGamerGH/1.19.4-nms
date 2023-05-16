package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.npc.EntityVillagerTrader;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WanderingTrader;

public class CraftWanderingTrader extends CraftAbstractVillager implements WanderingTrader {
   public CraftWanderingTrader(CraftServer server, EntityVillagerTrader entity) {
      super(server, entity);
   }

   public EntityVillagerTrader getHandle() {
      return (EntityVillagerTrader)this.entity;
   }

   @Override
   public String toString() {
      return "CraftWanderingTrader";
   }

   @Override
   public EntityType getType() {
      return EntityType.WANDERING_TRADER;
   }

   public int getDespawnDelay() {
      return this.getHandle().gb();
   }

   public void setDespawnDelay(int despawnDelay) {
      this.getHandle().t(despawnDelay);
   }
}
