package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.world.IInventory;
import net.minecraft.world.inventory.InventoryCraftResult;
import net.minecraft.world.item.crafting.IRecipe;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.SmithingInventory;

public class CraftInventorySmithingNew extends CraftResultInventory implements SmithingInventory {
   private final Location location;

   public CraftInventorySmithingNew(Location location, IInventory inventory, InventoryCraftResult resultInventory) {
      super(inventory, resultInventory);
      this.location = location;
   }

   public InventoryCraftResult getResultInventory() {
      return (InventoryCraftResult)super.getResultInventory();
   }

   @Override
   public Location getLocation() {
      return this.location;
   }

   public ItemStack getResult() {
      return this.getItem(3);
   }

   public void setResult(ItemStack item) {
      this.setItem(3, item);
   }

   public Recipe getRecipe() {
      IRecipe recipe = this.getResultInventory().d();
      return recipe == null ? null : recipe.toBukkitRecipe();
   }
}
