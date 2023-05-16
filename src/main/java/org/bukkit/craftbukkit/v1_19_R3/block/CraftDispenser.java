package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.level.block.BlockDispenser;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntityDispenser;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.projectiles.CraftBlockProjectileSource;
import org.bukkit.inventory.Inventory;
import org.bukkit.projectiles.BlockProjectileSource;

public class CraftDispenser extends CraftLootable<TileEntityDispenser> implements Dispenser {
   public CraftDispenser(World world, TileEntityDispenser tileEntity) {
      super(world, tileEntity);
   }

   public Inventory getSnapshotInventory() {
      return new CraftInventory(this.getSnapshot());
   }

   public Inventory getInventory() {
      return (Inventory)(!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventory(this.getTileEntity()));
   }

   public BlockProjectileSource getBlockProjectileSource() {
      Block block = this.getBlock();
      return block.getType() != Material.DISPENSER ? null : new CraftBlockProjectileSource((TileEntityDispenser)this.getTileEntityFromWorld());
   }

   public boolean dispense() {
      this.ensureNoWorldGeneration();
      Block block = this.getBlock();
      if (block.getType() == Material.DISPENSER) {
         CraftWorld world = (CraftWorld)this.getWorld();
         BlockDispenser dispense = (BlockDispenser)Blocks.aT;
         dispense.a(world.getHandle(), this.getPosition());
         return true;
      } else {
         return false;
      }
   }
}
