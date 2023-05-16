package net.minecraft.world.item.crafting;

import net.minecraft.world.inventory.InventoryCrafting;

public interface RecipeCrafting extends IRecipe<InventoryCrafting> {
   @Override
   default Recipes<?> f() {
      return Recipes.a;
   }

   CraftingBookCategory d();
}
