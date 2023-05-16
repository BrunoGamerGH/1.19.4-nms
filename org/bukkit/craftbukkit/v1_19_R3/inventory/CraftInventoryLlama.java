package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.world.IInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.LlamaInventory;

public class CraftInventoryLlama extends CraftInventoryAbstractHorse implements LlamaInventory {
   public CraftInventoryLlama(IInventory inventory) {
      super(inventory);
   }

   public ItemStack getDecor() {
      return this.getItem(1);
   }

   public void setDecor(ItemStack stack) {
      this.setItem(1, stack);
   }
}
