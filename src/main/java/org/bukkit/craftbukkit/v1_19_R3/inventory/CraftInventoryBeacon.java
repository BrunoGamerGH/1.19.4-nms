package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.world.IInventory;
import org.bukkit.inventory.BeaconInventory;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryBeacon extends CraftInventory implements BeaconInventory {
   public CraftInventoryBeacon(IInventory beacon) {
      super(beacon);
   }

   public void setItem(ItemStack item) {
      this.setItem(0, item);
   }

   public ItemStack getItem() {
      return this.getItem(0);
   }
}
