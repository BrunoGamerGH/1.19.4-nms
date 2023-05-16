package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import org.bukkit.block.ChiseledBookshelf;
import org.bukkit.inventory.ChiseledBookshelfInventory;

public class CraftInventoryChiseledBookshelf extends CraftInventory implements ChiseledBookshelfInventory {
   public CraftInventoryChiseledBookshelf(ChiseledBookShelfBlockEntity inventory) {
      super(inventory);
   }

   public ChiseledBookshelf getHolder() {
      return (ChiseledBookshelf)this.inventory.getOwner();
   }
}
