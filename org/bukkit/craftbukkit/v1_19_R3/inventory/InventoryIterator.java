package org.bukkit.craftbukkit.v1_19_R3.inventory;

import java.util.ListIterator;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryIterator implements ListIterator<ItemStack> {
   private final Inventory inventory;
   private int nextIndex;
   private Boolean lastDirection;

   InventoryIterator(Inventory craftInventory) {
      this.inventory = craftInventory;
      this.nextIndex = 0;
   }

   InventoryIterator(Inventory craftInventory, int index) {
      this.inventory = craftInventory;
      this.nextIndex = index;
   }

   @Override
   public boolean hasNext() {
      return this.nextIndex < this.inventory.getSize();
   }

   public ItemStack next() {
      this.lastDirection = true;
      return this.inventory.getItem(this.nextIndex++);
   }

   @Override
   public int nextIndex() {
      return this.nextIndex;
   }

   @Override
   public boolean hasPrevious() {
      return this.nextIndex > 0;
   }

   public ItemStack previous() {
      this.lastDirection = false;
      return this.inventory.getItem(--this.nextIndex);
   }

   @Override
   public int previousIndex() {
      return this.nextIndex - 1;
   }

   public void set(ItemStack item) {
      if (this.lastDirection == null) {
         throw new IllegalStateException("No current item!");
      } else {
         int i = this.lastDirection ? this.nextIndex - 1 : this.nextIndex;
         this.inventory.setItem(i, item);
      }
   }

   public void add(ItemStack item) {
      throw new UnsupportedOperationException("Can't change the size of an inventory!");
   }

   @Override
   public void remove() {
      throw new UnsupportedOperationException("Can't change the size of an inventory!");
   }
}
