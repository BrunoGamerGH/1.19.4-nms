package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.server.MinecraftServer;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

public class CraftFurnaceRecipe extends FurnaceRecipe implements CraftRecipe {
   public CraftFurnaceRecipe(NamespacedKey key, ItemStack result, RecipeChoice source, float experience, int cookingTime) {
      super(key, result, source, experience, cookingTime);
   }

   public static CraftFurnaceRecipe fromBukkitRecipe(FurnaceRecipe recipe) {
      if (recipe instanceof CraftFurnaceRecipe) {
         return (CraftFurnaceRecipe)recipe;
      } else {
         CraftFurnaceRecipe ret = new CraftFurnaceRecipe(
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
            new net.minecraft.world.item.crafting.FurnaceRecipe(
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
