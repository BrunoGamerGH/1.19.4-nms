package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeSmoking;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmokingRecipe;

public class CraftSmokingRecipe extends SmokingRecipe implements CraftRecipe {
   public CraftSmokingRecipe(NamespacedKey key, ItemStack result, RecipeChoice source, float experience, int cookingTime) {
      super(key, result, source, experience, cookingTime);
   }

   public static CraftSmokingRecipe fromBukkitRecipe(SmokingRecipe recipe) {
      if (recipe instanceof CraftSmokingRecipe) {
         return (CraftSmokingRecipe)recipe;
      } else {
         CraftSmokingRecipe ret = new CraftSmokingRecipe(
            recipe.getKey(), recipe.getResult(), recipe.getInputChoice(), recipe.getExperience(), recipe.getCookingTime()
         );
         ret.setGroup(recipe.getGroup());
         ret.setCategory(recipe.getCategory());
         return ret;
      }
   }

   @Override
   public void addToCraftingManager() {
      ItemStack result = this.getResult();
      MinecraftServer.getServer()
         .aE()
         .addRecipe(
            new RecipeSmoking(
               CraftNamespacedKey.toMinecraft(this.getKey()),
               this.getGroup(),
               CraftRecipe.getCategory(this.getCategory()),
               this.toNMS(this.getInputChoice(), true),
               CraftItemStack.asNMSCopy(result),
               this.getExperience(),
               this.getCookingTime()
            )
         );
   }
}
