package org.bukkit.craftbukkit.v1_19_R3.inventory;

import net.minecraft.core.IRegistryCustom;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.crafting.IRecipeComplex;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftNamespacedKey;
import org.bukkit.inventory.ComplexRecipe;
import org.bukkit.inventory.ItemStack;

public class CraftComplexRecipe implements CraftRecipe, ComplexRecipe {
   private final IRecipeComplex recipe;

   public CraftComplexRecipe(IRecipeComplex recipe) {
      this.recipe = recipe;
   }

   public ItemStack getResult() {
      return CraftItemStack.asCraftMirror(this.recipe.a(IRegistryCustom.b));
   }

   public NamespacedKey getKey() {
      return CraftNamespacedKey.fromMinecraft(this.recipe.e());
   }

   @Override
   public void addToCraftingManager() {
      MinecraftServer.getServer().aE().addRecipe(this.recipe);
   }
}
