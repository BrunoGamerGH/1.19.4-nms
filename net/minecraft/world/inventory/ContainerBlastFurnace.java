package net.minecraft.world.inventory;

import net.minecraft.world.IInventory;
import net.minecraft.world.entity.player.PlayerInventory;
import net.minecraft.world.item.crafting.Recipes;

public class ContainerBlastFurnace extends ContainerFurnace {
   public ContainerBlastFurnace(int var0, PlayerInventory var1) {
      super(Containers.j, Recipes.c, RecipeBookType.c, var0, var1);
   }

   public ContainerBlastFurnace(int var0, PlayerInventory var1, IInventory var2, IContainerProperties var3) {
      super(Containers.j, Recipes.c, RecipeBookType.c, var0, var1, var2, var3);
   }
}
