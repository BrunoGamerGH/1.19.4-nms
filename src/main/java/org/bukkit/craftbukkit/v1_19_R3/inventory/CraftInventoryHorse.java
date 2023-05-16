package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.world.IInventory;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryHorse extends CraftInventoryAbstractHorse implements HorseInventory {
   public CraftInventoryHorse(IInventory inventory) {
      super(inventory);
   }

   public ItemStack getArmor() {
      return this.getItem(1);
   }

   public void setArmor(ItemStack stack) {
      this.setItem(1, stack);
   }
}
