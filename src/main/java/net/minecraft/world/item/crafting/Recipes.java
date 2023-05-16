package net.minecraft.world.item.crafting;

import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.MinecraftKey;

public interface Recipes<T extends IRecipe<?>> {
   Recipes<RecipeCrafting> a = a("crafting");
   Recipes<FurnaceRecipe> b = a("smelting");
   Recipes<RecipeBlasting> c = a("blasting");
   Recipes<RecipeSmoking> d = a("smoking");
   Recipes<RecipeCampfire> e = a("campfire_cooking");
   Recipes<RecipeStonecutting> f = a("stonecutting");
   Recipes<SmithingRecipe> g = a("smithing");

   static <T extends IRecipe<?>> Recipes<T> a(final String var0) {
      return IRegistry.a(BuiltInRegistries.s, new MinecraftKey(var0), new Recipes<T>() {
         @Override
         public String toString() {
            return var0;
         }
      });
   }
}
