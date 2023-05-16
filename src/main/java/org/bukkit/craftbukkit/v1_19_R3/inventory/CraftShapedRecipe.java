package org.bukkit.craftbukkit.v1_19_R3.inventory;

import java.util.Map;
import net.minecraft.core.NonNullList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.item.crafting.ShapedRecipes;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

public class CraftShapedRecipe extends ShapedRecipe implements CraftRecipe {
   private ShapedRecipes recipe;

   public CraftShapedRecipe(NamespacedKey key, ItemStack result) {
      super(key, result);
   }

   public CraftShapedRecipe(ItemStack result, ShapedRecipes recipe) {
      this(CraftNamespacedKey.fromMinecraft(recipe.e()), result);
      this.recipe = recipe;
   }

   public static CraftShapedRecipe fromBukkitRecipe(ShapedRecipe recipe) {
      if (recipe instanceof CraftShapedRecipe) {
         return (CraftShapedRecipe)recipe;
      } else {
         CraftShapedRecipe ret = new CraftShapedRecipe(recipe.getKey(), recipe.getResult());
         ret.setGroup(recipe.getGroup());
         ret.setCategory(recipe.getCategory());
         String[] shape = recipe.getShape();
         ret.shape(shape);
         Map<Character, RecipeChoice> ingredientMap = recipe.getChoiceMap();

         for(char c : ingredientMap.keySet()) {
            RecipeChoice stack = (RecipeChoice)ingredientMap.get(c);
            if (stack != null) {
               ret.setIngredient(c, stack);
            }
         }

         return ret;
      }
   }

   @Override
   public void addToCraftingManager() {
      String[] shape = this.getShape();
      Map<Character, RecipeChoice> ingred = this.getChoiceMap();
      int width = shape[0].length();
      NonNullList<RecipeItemStack> data = NonNullList.a(shape.length * width, RecipeItemStack.a);

      for(int i = 0; i < shape.length; ++i) {
         String row = shape[i];

         for(int j = 0; j < row.length(); ++j) {
            data.set(i * width + j, this.toNMS((RecipeChoice)ingred.get(row.charAt(j)), false));
         }
      }

      MinecraftServer.getServer()
         .aE()
         .addRecipe(
            new ShapedRecipes(
               CraftNamespacedKey.toMinecraft(this.getKey()),
               this.getGroup(),
               CraftRecipe.getCategory(this.getCategory()),
               width,
               shape.length,
               data,
               CraftItemStack.asNMSCopy(this.getResult())
            )
         );
   }
}
