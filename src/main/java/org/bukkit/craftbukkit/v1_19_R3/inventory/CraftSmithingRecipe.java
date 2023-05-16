package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.LegacyUpgradeRecipe;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingRecipe;

public class CraftSmithingRecipe extends SmithingRecipe implements CraftRecipe {
   public CraftSmithingRecipe(NamespacedKey key, ItemStack result, RecipeChoice base, RecipeChoice addition) {
      super(key, result, base, addition);
   }

   public static CraftSmithingRecipe fromBukkitRecipe(SmithingRecipe recipe) {
      return recipe instanceof CraftSmithingRecipe
         ? (CraftSmithingRecipe)recipe
         : new CraftSmithingRecipe(recipe.getKey(), recipe.getResult(), recipe.getBase(), recipe.getAddition());
   }

   @Override
   public void addToCraftingManager() {
      ItemStack result = this.getResult();
      MinecraftServer.getServer()
         .aE()
         .addRecipe(
            new LegacyUpgradeRecipe(
               CraftNamespacedKey.toMinecraft(this.getKey()),
               this.toNMS(this.getBase(), true),
               this.toNMS(this.getAddition(), true),
               CraftItemStack.asNMSCopy(result)
            )
         );
   }
}
