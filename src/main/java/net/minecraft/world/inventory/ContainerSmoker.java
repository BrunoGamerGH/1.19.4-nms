package net.minecraft.world.inventory;

import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.crafting.Recipes;

public class ContainerSmoker extends ContainerFurnace {
   public ContainerSmoker(int var0, PlayerInventory var1) {
      super(Containers.w, Recipes.d, RecipeBookType.d, var0, var1);
   }

   public ContainerSmoker(int var0, PlayerInventory var1, IInventory var2, IContainerProperties var3) {
      super(Containers.w, Recipes.d, RecipeBookType.d, var0, var1, var2, var3);
   }
}
