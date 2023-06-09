package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityDrowned;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.EntityType;

public class CraftDrowned extends CraftZombie implements Drowned {
   public CraftDrowned(CraftServer server, EntityDrowned entity) {
      super(server, entity);
   }

   public EntityDrowned getHandle() {
      return (EntityDrowned)this.entity;
   }

   @Override
   public String toString() {
      return "CraftDrowned";
   }

   @Override
   public EntityType getType() {
      return EntityType.DROWNED;
   }
}
