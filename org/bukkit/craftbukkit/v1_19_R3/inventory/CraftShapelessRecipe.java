package org.bukkit.craftbukkit.v1_19_R3.inventory;

import java.util.List;
import net.minecraft.core.NonNullList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeItemStack;
import net.minecraft.world.item.crafting.ShapelessRecipes;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapelessRecipe;

public class CraftShapelessRecipe extends ShapelessRecipe implements CraftRecipe {
   private ShapelessRecipes recipe;

   public CraftShapelessRecipe(NamespacedKey key, ItemStack result) {
      super(key, result);
   }

   public CraftShapelessRecipe(ItemStack result, ShapelessRecipes recipe) {
      this(CraftNamespacedKey.fromMinecraft(recipe.e()), result);
      this.recipe = recipe;
   }

   public static CraftShapelessRecipe fromBukkitRecipe(ShapelessRecipe recipe) {
      if (recipe instanceof CraftShapelessRecipe) {
         return (CraftShapelessRecipe)recipe;
      } else {
         CraftShapelessRecipe ret = new CraftShapelessRecipe(recipe.getKey(), recipe.getResult());
         ret.setGroup(recipe.getGroup());
         ret.setCategory(recipe.getCategory());

         for(RecipeChoice ingred : recipe.getChoiceList()) {
            ret.addIngredient(ingred);
         }

         return ret;
      }
   }

   @Override
   public void addToCraftingManager() {
      List<RecipeChoice> ingred = this.getChoiceList();
      NonNullList<RecipeItemStack> data = NonNullList.a(ingred.size(), RecipeItemStack.a);

      for(int i = 0; i < ingred.size(); ++i) {
         data.set(i, this.toNMS((RecipeChoice)ingred.get(i), true));
      }

      MinecraftServer.getServer()
         .aE()
         .addRecipe(
            new ShapelessRecipes(
               CraftNamespacedKey.toMinecraft(this.getKey()),
               this.getGroup(),
               CraftRecipe.getCategory(this.getCategory()),
               CraftItemStack.asNMSCopy(this.getResult()),
               data
            )
         );
   }
}
