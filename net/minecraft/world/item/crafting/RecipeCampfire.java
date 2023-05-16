package net.minecraft.world.item.crafting;

import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftCampfireRecipe;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftRecipe;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.Recipe;

public class RecipeCampfire extends RecipeCooking {
   public RecipeCampfire(
      MinecraftKey minecraftkey, String s, CookingBookCategory cookingbookcategory, RecipeItemStack recipeitemstack, ItemStack itemstack, float f, int i
   ) {
      super(Recipes.e, minecraftkey, s, cookingbookcategory, recipeitemstack, itemstack, f, i);
   }

   @Override
   public ItemStack h() {
      return new ItemStack(Blocks.oc);
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.s;
   }

   @Override
   public Recipe toBukkitRecipe() {
      CraftItemStack result = CraftItemStack.asCraftMirror(this.e);
      CraftCampfireRecipe recipe = new CraftCampfireRecipe(CraftNamespacedKey.fromMinecraft(this.b), result, CraftRecipe.toBukkit(this.d), this.f, this.g);
      recipe.setGroup(this.c);
      recipe.setCategory(CraftRecipe.getCategory(this.g()));
      return recipe;
   }
}
