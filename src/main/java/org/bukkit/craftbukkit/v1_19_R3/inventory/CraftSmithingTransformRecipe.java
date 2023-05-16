package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.server.MinecraftServer;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.SmithingTransformRecipe;

public class CraftSmithingTransformRecipe extends SmithingTransformRecipe implements CraftRecipe {
   public CraftSmithingTransformRecipe(NamespacedKey key, ItemStack result, RecipeChoice template, RecipeChoice base, RecipeChoice addition) {
      super(key, result, template, base, addition);
   }

   public static CraftSmithingTransformRecipe fromBukkitRecipe(SmithingTransformRecipe recipe) {
      return recipe instanceof CraftSmithingTransformRecipe
         ? (CraftSmithingTransformRecipe)recipe
         : new CraftSmithingTransformRecipe(recipe.getKey(), recipe.getResult(), recipe.getTemplate(), recipe.getBase(), recipe.getAddition());
   }

   @Override
   public void addToCraftingManager() {
      ItemStack result = this.getResult();
      MinecraftServer.getServer()
         .aE()
         .addRecipe(
            new net.minecraft.world.item.crafting.SmithingTransformRecipe(
               CraftNamespacedKey.toMinecraft(this.getKey()),
               this.toNMS(this.getTemplate(), true),
               this.toNMS(this.getBase(), true),
               this.toNMS(this.getAddition(), true),
               CraftItemStack.asNMSCopy(result)
            )
         );
   }
}
