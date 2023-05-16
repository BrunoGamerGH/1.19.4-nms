package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.world.IInventory;
import org.bukkit.inventory.ItemStack;

public class CraftResultInventory extends CraftInventory {
   private final IInventory resultInventory;

   public CraftResultInventory(IInventory inventory, IInventory resultInventory) {
      super(inventory);
      this.resultInventory = resultInventory;
   }

   public IInventory getResultInventory() {
      return this.resultInventory;
   }

   public IInventory getIngredientsInventory() {
      return this.inventory;
   }

   @Override
   public ItemStack getItem(int slot) {
      if (slot < this.getIngredientsInventory().b()) {
         net.minecraft.world.item.ItemStack item = this.getIngredientsInventory().a(slot);
         return item.b() ? null : CraftItemStack.asCraftMirror(item);
      } else {
         net.minecraft.world.item.ItemStack item = this.getResultInventory().a(slot - this.getIngredientsInventory().b());
         return item.b() ? null : CraftItemStack.asCraftMirror(item);
      }
   }

   @Override
   public void setItem(int index, ItemStack item) {
      if (index < this.getIngredientsInventory().b()) {
         this.getIngredientsInventory().a(index, CraftItemStack.asNMSCopy(item));
      } else {
         this.getResultInventory().a(index - this.getIngredientsInventory().b(), CraftItemStack.asNMSCopy(item));
      }
   }

   @Override
   public int getSize() {
      return this.getResultInventory().b() + this.getIngredientsInventory().b();
   }
}
