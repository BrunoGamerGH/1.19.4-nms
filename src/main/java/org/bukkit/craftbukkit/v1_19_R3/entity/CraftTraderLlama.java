package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.animal.horse.EntityLlamaTrader;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TraderLlama;

public class CraftTraderLlama extends CraftLlama implements TraderLlama {
   public CraftTraderLlama(CraftServer server, EntityLlamaTrader entity) {
      super(server, entity);
   }

   public EntityLlamaTrader getHandle() {
      return (EntityLlamaTrader)super.getHandle();
   }

   @Override
   public String toString() {
      return "CraftTraderLlama";
   }

   @Override
   public EntityType getType() {
      return EntityType.TRADER_LLAMA;
   }
}
