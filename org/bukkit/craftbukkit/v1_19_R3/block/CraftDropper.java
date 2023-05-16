package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.BlockDropper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntityDropper;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;

public class CraftDropper extends CraftLootable<TileEntityDropper> implements Dropper {
   public CraftDropper(World world, TileEntityDropper tileEntity) {
      super(world, tileEntity);
   }

   public Inventory getSnapshotInventory() {
      return new CraftInventory(this.getSnapshot());
   }

   public Inventory getInventory() {
      return (Inventory)(!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventory(this.getTileEntity()));
   }

   public void drop() {
      this.ensureNoWorldGeneration();
      Block block = this.getBlock();
      if (block.getType() == Material.DROPPER) {
         CraftWorld world = (CraftWorld)this.getWorld();
         BlockDropper drop = (BlockDropper)Blocks.hh;
         drop.a(world.getHandle(), this.getPosition());
      }
   }
}
