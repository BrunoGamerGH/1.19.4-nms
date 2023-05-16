package net.minecraft.world.item.crafting;

import net.minecraft.world.IInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public interface SmithingRecipe extends IRecipe<IInventory> {
   @Override
   default Recipes<?> f() {
      return Recipes.g;
   }

   @Override
   default boolean a(int var0, int var1) {
      return var0 >= 3 && var1 >= 1;
   }

   @Override
   default ItemStack h() {
      return new ItemStack(Blocks.nX);
   }

   boolean a(ItemStack var1);

   boolean b(ItemStack var1);

   boolean c(ItemStack var1);
}
