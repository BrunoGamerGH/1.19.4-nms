package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.monster.EntityPillager;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pillager;
import org.bukkit.inventory.Inventory;

public class CraftPillager extends CraftIllager implements Pillager {
   public CraftPillager(CraftServer server, EntityPillager entity) {
      super(server, entity);
   }

   public EntityPillager getHandle() {
      return (EntityPillager)super.getHandle();
   }

   @Override
   public EntityType getType() {
      return EntityType.PILLAGER;
   }

   @Override
   public String toString() {
      return "CraftPillager";
   }

   public Inventory getInventory() {
      return new CraftInventory(this.getHandle().bU);
   }
}
