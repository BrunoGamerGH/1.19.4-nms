package org.bukkit.craftbukkit.v1_19_R3.entity;

import net.minecraft.world.entity.vehicle.EntityMinecartHopper;
import org.bukkit.craftbukkit.v1_19_R3.CraftServer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.inventory.Inventory;

public final class CraftMinecartHopper extends CraftMinecartContainer implements HopperMinecart {
   private final CraftInventory inventory;

   public CraftMinecartHopper(CraftServer server, EntityMinecartHopper entity) {
      super(server, entity);
      this.inventory = new CraftInventory(entity);
   }

   @Override
   public String toString() {
      return "CraftMinecartHopper{inventory=" + this.inventory + 125;
   }

   public EntityType getType() {
      return EntityType.MINECART_HOPPER;
   }

   public Inventory getInventory() {
      return this.inventory;
   }

   public boolean isEnabled() {
      return ((EntityMinecartHopper)this.getHandle()).E();
   }

   public void setEnabled(boolean enabled) {
      ((EntityMinecartHopper)this.getHandle()).p(enabled);
   }
}
