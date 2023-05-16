package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.world.ITileInventory;
import net.minecraft.world.InventoryLargeChest;
import net.minecraft.world.level.block.BlockChest;
import org.bukkit.Location;
import org.bukkit.block.DoubleChest;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CraftInventoryDoubleChest extends CraftInventory implements DoubleChestInventory {
   public ITileInventory tile;
   private final CraftInventory left;
   private final CraftInventory right;

   public CraftInventoryDoubleChest(BlockChest.DoubleInventory block) {
      super(block.inventorylargechest);
      this.tile = block;
      this.left = new CraftInventory(block.inventorylargechest.c);
      this.right = new CraftInventory(block.inventorylargechest.d);
   }

   public CraftInventoryDoubleChest(InventoryLargeChest largeChest) {
      super(largeChest);
      if (largeChest.c instanceof InventoryLargeChest) {
         this.left = new CraftInventoryDoubleChest((InventoryLargeChest)largeChest.c);
      } else {
         this.left = new CraftInventory(largeChest.c);
      }

      if (largeChest.d instanceof InventoryLargeChest) {
         this.right = new CraftInventoryDoubleChest((InventoryLargeChest)largeChest.d);
      } else {
         this.right = new CraftInventory(largeChest.d);
      }
   }

   public Inventory getLeftSide() {
      return this.left;
   }

   public Inventory getRightSide() {
      return this.right;
   }

   @Override
   public void setContents(ItemStack[] items) {
      if (this.getInventory().b() < items.length) {
         throw new IllegalArgumentException("Invalid inventory size; expected " + this.getInventory().b() + " or less");
      } else {
         ItemStack[] leftItems = new ItemStack[this.left.getSize()];
         ItemStack[] rightItems = new ItemStack[this.right.getSize()];
         System.arraycopy(items, 0, leftItems, 0, Math.min(this.left.getSize(), items.length));
         this.left.setContents(leftItems);
         if (items.length >= this.left.getSize()) {
            System.arraycopy(items, this.left.getSize(), rightItems, 0, Math.min(this.right.getSize(), items.length - this.left.getSize()));
            this.right.setContents(rightItems);
         }
      }
   }

   public DoubleChest getHolder() {
      return new DoubleChest(this);
   }

   @Override
   public Location getLocation() {
      return this.getLeftSide().getLocation().add(this.getRightSide().getLocation()).multiply(0.5);
   }
}
