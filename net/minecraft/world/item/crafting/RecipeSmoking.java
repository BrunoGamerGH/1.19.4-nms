package net.minecraft.world.item.crafting;

import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftRecipe;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftSmokingRecipe;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.Recipe;

public class RecipeSmoking extends RecipeCooking {
   public RecipeSmoking(
      MinecraftKey minecraftkey, String s, CookingBookCategory cookingbookcategory, RecipeItemStack recipeitemstack, ItemStack itemstack, float f, int i
   ) {
      super(Recipes.d, minecraftkey, s, cookingbookcategory, recipeitemstack, itemstack, f, i);
   }

   @Override
   public ItemStack h() {
      return new ItemStack(Blocks.nR);
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.r;
   }

   @Override
   public Recipe toBukkitRecipe() {
      CraftItemStack result = CraftItemStack.asCraftMirror(this.e);
      CraftSmokingRecipe recipe = new CraftSmokingRecipe(CraftNamespacedKey.fromMinecraft(this.b), result, CraftRecipe.toBukkit(this.d), this.f, this.g);
      recipe.setGroup(this.c);
      recipe.setCategory(CraftRecipe.getCategory(this.g()));
      return recipe;
   }
}
