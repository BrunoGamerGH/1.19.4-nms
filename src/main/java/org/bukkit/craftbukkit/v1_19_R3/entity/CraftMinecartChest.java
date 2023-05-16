package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.vehicle.EntityMinecartChest;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.inventory.Inventory;

public class CraftMinecartChest extends CraftMinecartContainer implements StorageMinecart {
   private final CraftInventory inventory;

   public CraftMinecartChest(CraftServer server, EntityMinecartChest entity) {
      super(server, entity);
      this.inventory = new CraftInventory(entity);
   }

   public Inventory getInventory() {
      return this.inventory;
   }

   @Override
   public String toString() {
      return "CraftMinecartChest{inventory=" + this.inventory + 125;
   }

   public EntityType getType() {
      return EntityType.MINECART_CHEST;
   }
}
