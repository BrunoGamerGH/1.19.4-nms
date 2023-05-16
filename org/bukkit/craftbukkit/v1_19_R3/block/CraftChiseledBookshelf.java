package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import org.bukkit.World;
import org.bukkit.block.ChiseledBookshelf;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryChiseledBookshelf;
import org.bukkit.inventory.ChiseledBookshelfInventory;

public class CraftChiseledBookshelf extends CraftBlockEntityState<ChiseledBookShelfBlockEntity> implements ChiseledBookshelf {
   public CraftChiseledBookshelf(World world, ChiseledBookShelfBlockEntity tileEntity) {
      super(world, tileEntity);
   }

   public int getLastInteractedSlot() {
      return this.getSnapshot().g();
   }

   public void setLastInteractedSlot(int lastInteractedSlot) {
      this.getSnapshot().f = lastInteractedSlot;
   }

   public ChiseledBookshelfInventory getSnapshotInventory() {
      return new CraftInventoryChiseledBookshelf(this.getSnapshot());
   }

   public ChiseledBookshelfInventory getInventory() {
      return (ChiseledBookshelfInventory)(!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventoryChiseledBookshelf(this.getTileEntity()));
   }
}
