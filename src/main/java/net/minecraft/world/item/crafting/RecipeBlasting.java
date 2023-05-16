package net.minecraft.world.item.crafting;

import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftBlastingRecipe;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftRecipe;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.Recipe;

public class RecipeBlasting extends RecipeCooking {
   public RecipeBlasting(
      MinecraftKey minecraftkey, String s, CookingBookCategory cookingbookcategory, RecipeItemStack recipeitemstack, ItemStack itemstack, float f, int i
   ) {
      super(Recipes.c, minecraftkey, s, cookingbookcategory, recipeitemstack, itemstack, f, i);
   }

   @Override
   public ItemStack h() {
      return new ItemStack(Blocks.nS);
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.q;
   }

   @Override
   public Recipe toBukkitRecipe() {
      CraftItemStack result = CraftItemStack.asCraftMirror(this.e);
      CraftBlastingRecipe recipe = new CraftBlastingRecipe(CraftNamespacedKey.fromMinecraft(this.b), result, CraftRecipe.toBukkit(this.d), this.f, this.g);
      recipe.setGroup(this.c);
      recipe.setCategory(CraftRecipe.getCategory(this.g()));
      return recipe;
   }
}
