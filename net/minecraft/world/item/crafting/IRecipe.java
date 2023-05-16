package net.minecraft.world.item.crafting;

import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.IInventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.inventory.Recipe;

public interface IRecipe<C extends IInventory> {
   boolean a(C var1, World var2);

   ItemStack a(C var1, IRegistryCustom var2);

   boolean a(int var1, int var2);

   ItemStack a(IRegistryCustom var1);

   default NonNullList<ItemStack> a(C c0) {
      NonNullList<ItemStack> nonnulllist = NonNullList.a(c0.b(), ItemStack.b);

      for(int i = 0; i < nonnulllist.size(); ++i) {
         Item item = c0.a(i).c();
         if (item.t()) {
            nonnulllist.set(i, new ItemStack(item.s()));
         }
      }

      return nonnulllist;
   }

   default NonNullList<RecipeItemStack> a() {
      return NonNullList.a();
   }

   default boolean ah_() {
      return false;
   }

   default boolean i() {
      return true;
   }

   default String c() {
      return "";
   }

   default ItemStack h() {
      return new ItemStack(Blocks.cz);
   }

   MinecraftKey e();

   RecipeSerializer<?> ai_();

   Recipes<?> f();

   default boolean aj_() {
      NonNullList<RecipeItemStack> nonnulllist = this.a();
      return nonnulllist.isEmpty() || nonnulllist.stream().anyMatch(recipeitemstack -> recipeitemstack.a().length == 0);
   }

   Recipe toBukkitRecipe();
}
