package net.minecraft.world.item.crafting;

import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.IInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftRecipe;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftStonecuttingRecipe;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.Recipe;

public class RecipeStonecutting extends RecipeSingleItem {
   public RecipeStonecutting(MinecraftKey minecraftkey, String s, RecipeItemStack recipeitemstack, ItemStack itemstack) {
      super(Recipes.f, RecipeSerializer.t, minecraftkey, s, recipeitemstack, itemstack);
   }

   @Override
   public boolean a(IInventory iinventory, World world) {
      return this.a.a(iinventory.a(0));
   }

   @Override
   public ItemStack h() {
      return new ItemStack(Blocks.nY);
   }

   @Override
   public Recipe toBukkitRecipe() {
      CraftItemStack result = CraftItemStack.asCraftMirror(this.b);
      CraftStonecuttingRecipe recipe = new CraftStonecuttingRecipe(CraftNamespacedKey.fromMinecraft(this.c), result, CraftRecipe.toBukkit(this.a));
      recipe.setGroup(this.d);
      return recipe;
   }
}
