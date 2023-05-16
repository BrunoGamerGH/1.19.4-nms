package org.bukkit.craftbukkit.v1_19_R3.inventory;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.RecipeItemStack;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftMagicNumbers;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.RecipeChoice.ExactChoice;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;

public interface CraftRecipe extends Recipe {
   void addToCraftingManager();

   default RecipeItemStack toNMS(RecipeChoice bukkit, boolean requireNotEmpty) {
      RecipeItemStack stack;
      if (bukkit == null) {
         stack = RecipeItemStack.a;
      } else if (bukkit instanceof MaterialChoice) {
         stack = new RecipeItemStack(
            ((MaterialChoice)bukkit).getChoices().stream().map(mat -> new RecipeItemStack.StackProvider(CraftItemStack.asNMSCopy(new ItemStack(mat))))
         );
      } else {
         if (!(bukkit instanceof ExactChoice)) {
            throw new IllegalArgumentException("Unknown recipe stack instance " + bukkit);
         }

         stack = new RecipeItemStack(((ExactChoice)bukkit).getChoices().stream().map(mat -> new RecipeItemStack.StackProvider(CraftItemStack.asNMSCopy(mat))));
         stack.exact = true;
      }

      stack.a();
      if (requireNotEmpty && stack.c.length == 0) {
         throw new IllegalArgumentException("Recipe requires at least one non-air choice!");
      } else {
         return stack;
      }
   }

   static RecipeChoice toBukkit(RecipeItemStack list) {
      list.a();
      if (list.c.length == 0) {
         return null;
      } else if (list.exact) {
         List<ItemStack> choices = new ArrayList(list.c.length);

         for(net.minecraft.world.item.ItemStack i : list.c) {
            choices.add(CraftItemStack.asBukkitCopy(i));
         }

         return new ExactChoice(choices);
      } else {
         List<Material> choices = new ArrayList(list.c.length);

         for(net.minecraft.world.item.ItemStack i : list.c) {
            choices.add(CraftMagicNumbers.getMaterial(i.c()));
         }

         return new MaterialChoice(choices);
      }
   }

   static CraftingBookCategory getCategory(org.bukkit.inventory.recipe.CraftingBookCategory bukkit) {
      return CraftingBookCategory.valueOf(bukkit.name());
   }

   static org.bukkit.inventory.recipe.CraftingBookCategory getCategory(CraftingBookCategory nms) {
      return org.bukkit.inventory.recipe.CraftingBookCategory.valueOf(nms.name());
   }

   static CookingBookCategory getCategory(org.bukkit.inventory.recipe.CookingBookCategory bukkit) {
      return CookingBookCategory.valueOf(bukkit.name());
   }

   static org.bukkit.inventory.recipe.CookingBookCategory getCategory(CookingBookCategory nms) {
      return org.bukkit.inventory.recipe.CookingBookCategory.valueOf(nms.name());
   }
}
