package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.server.MinecraftServer;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingTrimRecipe;

public class CraftSmithingTrimRecipe extends SmithingTrimRecipe implements CraftRecipe {
   public CraftSmithingTrimRecipe(NamespacedKey key, RecipeChoice template, RecipeChoice base, RecipeChoice addition) {
      super(key, template, base, addition);
   }

   public static CraftSmithingTrimRecipe fromBukkitRecipe(SmithingTrimRecipe recipe) {
      return recipe instanceof CraftSmithingTrimRecipe
         ? (CraftSmithingTrimRecipe)recipe
         : new CraftSmithingTrimRecipe(recipe.getKey(), recipe.getTemplate(), recipe.getBase(), recipe.getAddition());
   }

   @Override
   public void addToCraftingManager() {
      MinecraftServer.getServer()
         .aE()
         .addRecipe(
            new net.minecraft.world.item.crafting.SmithingTrimRecipe(
               CraftNamespacedKey.toMinecraft(this.getKey()),
               this.toNMS(this.getTemplate(), true),
               this.toNMS(this.getBase(), true),
               this.toNMS(this.getAddition(), true)
            )
         );
   }
}
