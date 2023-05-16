package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeCampfire;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

public class CraftCampfireRecipe extends CampfireRecipe implements CraftRecipe {
   public CraftCampfireRecipe(NamespacedKey key, ItemStack result, RecipeChoice source, float experience, int cookingTime) {
      super(key, result, source, experience, cookingTime);
   }

   public static CraftCampfireRecipe fromBukkitRecipe(CampfireRecipe recipe) {
      if (recipe instanceof CraftCampfireRecipe) {
         return (CraftCampfireRecipe)recipe;
      } else {
         CraftCampfireRecipe ret = new CraftCampfireRecipe(
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
            new RecipeCampfire(
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
