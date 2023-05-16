package org.bukkit.craftbukkit.v1_19_R3.block;

import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.level.block.BlockBarrel;
import net.minecraft.world.level.block.entity.TileEntityBarrel;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.World;
import org.bukkit.block.Barrel;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;

public class CraftBarrel extends CraftLootable<TileEntityBarrel> implements Barrel {
   public CraftBarrel(World world, TileEntityBarrel tileEntity) {
      super(world, tileEntity);
   }

   public Inventory getSnapshotInventory() {
      return new CraftInventory(this.getSnapshot());
   }

   public Inventory getInventory() {
      return (Inventory)(!this.isPlaced() ? this.getSnapshotInventory() : new CraftInventory(this.getTileEntity()));
   }

   public void open() {
      this.requirePlaced();
      if (!this.getTileEntity().f.opened) {
         IBlockData blockData = this.getTileEntity().q();
         boolean open = blockData.c(BlockBarrel.b);
         if (!open) {
            this.getTileEntity().a(blockData, true);
            if (this.getWorldHandle() instanceof net.minecraft.world.level.World) {
               this.getTileEntity().a(blockData, SoundEffects.bj);
            }
         }
      }

      this.getTileEntity().f.opened = true;
   }

   public void close() {
      this.requirePlaced();
      if (this.getTileEntity().f.opened) {
         IBlockData blockData = this.getTileEntity().q();
         this.getTileEntity().a(blockData, false);
         if (this.getWorldHandle() instanceof net.minecraft.world.level.World) {
            this.getTileEntity().a(blockData, SoundEffects.bi);
         }
      }

      this.getTileEntity().f.opened = false;
   }
}
