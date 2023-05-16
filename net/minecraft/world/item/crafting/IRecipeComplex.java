package net.minecraft.world.item.crafting;

import net.minecraft.core.IRegistryCustom;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftComplexRecipe;
import org.bukkit.inventory.Recipe;

public abstract class IRecipeComplex implements RecipeCrafting {
   private final MinecraftKey a;
   private final CraftingBookCategory b;

   public IRecipeComplex(MinecraftKey minecraftkey, CraftingBookCategory craftingbookcategory) {
      this.a = minecraftkey;
      this.b = craftingbookcategory;
   }

   @Override
   public MinecraftKey e() {
      return this.a;
   }

   @Override
   public boolean ah_() {
      return true;
   }

   @Override
   public ItemStack a(IRegistryCustom iregistrycustom) {
      return ItemStack.b;
   }

   @Override
   public CraftingBookCategory d() {
      return this.b;
   }

   @Override
   public Recipe toBukkitRecipe() {
      return new CraftComplexRecipe(this);
   }
}
