package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.entity.TileEntityBrewingStand;
import org.bukkit.World;
import org.bukkit.block.BrewingStand;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryBrewer;
import org.bukkit.inventory.BrewerInventory;

public class CraftBrewingStand extends CraftContainer<TileEntityBrewingStand> implements BrewingStand {
   public CraftBrewingStand(World world, TileEntityBrewingStand tileEntity) {
      super(world, tileEntity);
   }

   public BrewerInventory getSnapshotInventory() {
      return new CraftInventoryBrewer(this.getSnapshot());
   }

   public BrewerInventory getInventory() {
      return (BrewerInventory)(!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventoryBrewer(this.getTileEntity()));
   }

   public int getBrewingTime() {
      return this.getSnapshot().n;
   }

   public void setBrewingTime(int brewTime) {
      this.getSnapshot().n = brewTime;
   }

   public int getFuelLevel() {
      return this.getSnapshot().t;
   }

   public void setFuelLevel(int level) {
      this.getSnapshot().t = level;
   }
}
