package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeBlasting;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;

public class CraftBlastingRecipe extends BlastingRecipe implements CraftRecipe {
   public CraftBlastingRecipe(NamespacedKey key, ItemStack result, RecipeChoice source, float experience, int cookingTime) {
      super(key, result, source, experience, cookingTime);
   }

   public static CraftBlastingRecipe fromBukkitRecipe(BlastingRecipe recipe) {
      if (recipe instanceof CraftBlastingRecipe) {
         return (CraftBlastingRecipe)recipe;
      } else {
         CraftBlastingRecipe ret = new CraftBlastingRecipe(
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
            new RecipeBlasting(
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
