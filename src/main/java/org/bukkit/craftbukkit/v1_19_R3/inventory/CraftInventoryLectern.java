package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.world.IInventory;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.level.block.entity.TileEntityLectern;
import org.bukkit.block.Lectern;
import org.bukkit.inventory.LecternInventory;

public class CraftInventoryLectern extends CraftInventory implements LecternInventory {
   public ITileInventory tile;

   public CraftInventoryLectern(IInventory inventory) {
      super(inventory);
      if (inventory instanceof TileEntityLectern.LecternInventory) {
         this.tile = ((TileEntityLectern.LecternInventory)inventory).getLectern();
      }
   }

   public Lectern getHolder() {
      return (Lectern)this.inventory.getOwner();
   }
}
