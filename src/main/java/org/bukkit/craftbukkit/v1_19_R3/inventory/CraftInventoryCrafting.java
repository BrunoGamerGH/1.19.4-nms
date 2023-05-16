package org.bukkit.craftbukkit.v1_19_R3.inventory;

import java.util.Arrays;
import java.util.List;
import net.minecraft.world.IInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.IRecipe;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Recipe;

public class CraftInventoryCrafting extends CraftInventory implements CraftingInventory {
   private final IInventory resultInventory;

   public CraftInventoryCrafting(IInventory inventory, IInventory resultInventory) {
      super(inventory);
      this.resultInventory = resultInventory;
   }

   public IInventory getResultInventory() {
      return this.resultInventory;
   }

   public IInventory getMatrixInventory() {
      return this.inventory;
   }

   @Override
   public int getSize() {
      return this.getResultInventory().b() + this.getMatrixInventory().b();
   }

   @Override
   public void setContents(org.bukkit.inventory.ItemStack[] items) {
      if (this.getSize() > items.length) {
         throw new IllegalArgumentException("Invalid inventory size; expected " + this.getSize() + " or less");
      } else {
         this.setContents(items[0], Arrays.copyOfRange(items, 1, items.length));
      }
   }

   @Override
   public org.bukkit.inventory.ItemStack[] getContents() {
      org.bukkit.inventory.ItemStack[] items = new org.bukkit.inventory.ItemStack[this.getSize()];
      List<ItemStack> mcResultItems = this.getResultInventory().getContents();
      int i = 0;

      for(i = 0; i < mcResultItems.size(); ++i) {
         items[i] = CraftItemStack.asCraftMirror(mcResultItems.get(i));
      }

      List<ItemStack> mcItems = this.getMatrixInventory().getContents();

      for(int j = 0; j < mcItems.size(); ++j) {
         items[i + j] = CraftItemStack.asCraftMirror(mcItems.get(j));
      }

      return items;
   }

   public void setContents(org.bukkit.inventory.ItemStack result, org.bukkit.inventory.ItemStack[] contents) {
      this.setResult(result);
      this.setMatrix(contents);
   }

   public CraftItemStack getItem(int index) {
      if (index < this.getResultInventory().b()) {
         ItemStack item = this.getResultInventory().a(index);
         return item.b() ? null : CraftItemStack.asCraftMirror(item);
      } else {
         ItemStack item = this.getMatrixInventory().a(index - this.getResultInventory().b());
         return item.b() ? null : CraftItemStack.asCraftMirror(item);
      }
   }

   @Override
   public void setItem(int index, org.bukkit.inventory.ItemStack item) {
      if (index < this.getResultInventory().b()) {
         this.getResultInventory().a(index, CraftItemStack.asNMSCopy(item));
      } else {
         this.getMatrixInventory().a(index - this.getResultInventory().b(), CraftItemStack.asNMSCopy(item));
      }
   }

   public org.bukkit.inventory.ItemStack[] getMatrix() {
      List<ItemStack> matrix = this.getMatrixInventory().getContents();
      return this.asCraftMirror(matrix);
   }

   public org.bukkit.inventory.ItemStack getResult() {
      ItemStack item = this.getResultInventory().a(0);
      return !item.b() ? CraftItemStack.asCraftMirror(item) : null;
   }

   public void setMatrix(org.bukkit.inventory.ItemStack[] contents) {
      if (this.getMatrixInventory().b() > contents.length) {
         throw new IllegalArgumentException("Invalid inventory size; expected " + this.getMatrixInventory().b() + " or less");
      } else {
         for(int i = 0; i < this.getMatrixInventory().b(); ++i) {
            if (i < contents.length) {
               this.getMatrixInventory().a(i, CraftItemStack.asNMSCopy(contents[i]));
            } else {
               this.getMatrixInventory().a(i, ItemStack.b);
            }
         }
      }
   }

   public void setResult(org.bukkit.inventory.ItemStack item) {
      List<ItemStack> contents = this.getResultInventory().getContents();
      contents.set(0, CraftItemStack.asNMSCopy(item));
   }

   public Recipe getRecipe() {
      IRecipe recipe = this.getInventory().getCurrentRecipe();
      return recipe == null ? null : recipe.toBukkitRecipe();
   }
}
