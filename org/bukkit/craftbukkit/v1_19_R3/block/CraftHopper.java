package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.entity.TileEntityHopper;
import org.bukkit.World;
import org.bukkit.block.Hopper;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;

public class CraftHopper extends CraftLootable<TileEntityHopper> implements Hopper {
   public CraftHopper(World world, TileEntityHopper tileEntity) {
      super(world, tileEntity);
   }

   public Inventory getSnapshotInventory() {
      return new CraftInventory(this.getSnapshot());
   }

   public Inventory getInventory() {
      return (Inventory)(!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventory(this.getTileEntity()));
   }
}
