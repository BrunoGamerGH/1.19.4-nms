package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.RecipeStonecutting;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.StonecuttingRecipe;

public class CraftStonecuttingRecipe extends StonecuttingRecipe implements CraftRecipe {
   public CraftStonecuttingRecipe(NamespacedKey key, ItemStack result, RecipeChoice source) {
      super(key, result, source);
   }

   public static CraftStonecuttingRecipe fromBukkitRecipe(StonecuttingRecipe recipe) {
      if (recipe instanceof CraftStonecuttingRecipe) {
         return (CraftStonecuttingRecipe)recipe;
      } else {
         CraftStonecuttingRecipe ret = new CraftStonecuttingRecipe(recipe.getKey(), recipe.getResult(), recipe.getInputChoice());
         ret.setGroup(recipe.getGroup());
         return ret;
      }
   }

   @Override
   public void addToCraftingManager() {
      ItemStack result = this.getResult();
      MinecraftServer.getServer()
         .aE()
         .addRecipe(
            new RecipeStonecutting(
               CraftNamespacedKey.toMinecraft(this.getKey()), this.getGroup(), this.toNMS(this.getInputChoice(), true), CraftItemStack.asNMSCopy(result)
            )
         );
   }
}
