package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.world.ITileInventory;
import net.minecraft.world.level.block.BlockChest;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntityChest;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryDoubleChest;
import org.bukkit.inventory.Inventory;

public class CraftChest extends CraftLootable<TileEntityChest> implements Chest {
   public CraftChest(World world, TileEntityChest tileEntity) {
      super(world, tileEntity);
   }

   public Inventory getSnapshotInventory() {
      return new CraftInventory(this.getSnapshot());
   }

   public Inventory getBlockInventory() {
      return (Inventory)(!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventory(this.getTileEntity()));
   }

   public Inventory getInventory() {
      CraftInventory inventory = (CraftInventory)this.getBlockInventory();
      if (this.isPlaced() && !this.isWorldGeneration()) {
         CraftWorld world = (CraftWorld)this.getWorld();
         BlockChest blockChest = (BlockChest)(this.getType() == Material.CHEST ? Blocks.cu : Blocks.gU);
         ITileInventory nms = blockChest.getMenuProvider(this.data, world.getHandle(), this.getPosition(), true);
         if (nms instanceof BlockChest.DoubleInventory) {
            inventory = new CraftInventoryDoubleChest((BlockChest.DoubleInventory)nms);
         }

         return inventory;
      } else {
         return inventory;
      }
   }

   public void open() {
      this.requirePlaced();
      if (!this.getTileEntity().g.opened && this.getWorldHandle() instanceof net.minecraft.world.level.World) {
         IBlockData block = this.getTileEntity().q();
         int openCount = this.getTileEntity().g.a();
         this.getTileEntity().g.onAPIOpen((net.minecraft.world.level.World)this.getWorldHandle(), this.getPosition(), block);
         this.getTileEntity()
            .g
            .openerAPICountChanged((net.minecraft.world.level.World)this.getWorldHandle(), this.getPosition(), block, openCount, openCount + 1);
      }

      this.getTileEntity().g.opened = true;
   }

   public void close() {
      this.requirePlaced();
      if (this.getTileEntity().g.opened && this.getWorldHandle() instanceof net.minecraft.world.level.World) {
         IBlockData block = this.getTileEntity().q();
         int openCount = this.getTileEntity().g.a();
         this.getTileEntity().g.onAPIClose((net.minecraft.world.level.World)this.getWorldHandle(), this.getPosition(), block);
         this.getTileEntity().g.openerAPICountChanged((net.minecraft.world.level.World)this.getWorldHandle(), this.getPosition(), block, openCount, 0);
      }

      this.getTileEntity().g.opened = false;
   }
}
