package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.world.IInventory;
import org.bukkit.inventory.CartographyInventory;

public class CraftInventoryCartography extends CraftResultInventory implements CartographyInventory {
   public CraftInventoryCartography(IInventory inventory, IInventory resultInventory) {
      super(inventory, resultInventory);
   }
}
