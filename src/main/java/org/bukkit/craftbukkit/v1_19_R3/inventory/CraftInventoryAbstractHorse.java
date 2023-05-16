package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.world.IInventory;
import org.bukkit.inventory.AbstractHorseInventory;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryAbstractHorse extends CraftInventory implements AbstractHorseInventory {
   public CraftInventoryAbstractHorse(IInventory inventory) {
      super(inventory);
   }

   public ItemStack getSaddle() {
      return this.getItem(0);
   }

   public void setSaddle(ItemStack stack) {
      this.setItem(0, stack);
   }
}
