package net.minecraft.world.item.crafting;

import net.minecraft.core.IRegistryCustom;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtil;
import net.minecraft.world.level.World;

public class RecipeTippedArrow extends IRecipeComplex {
   public RecipeTippedArrow(MinecraftKey var0, CraftingBookCategory var1) {
      super(var0, var1);
   }

   public boolean a(InventoryCrafting var0, World var1) {
      if (var0.g() == 3 && var0.f() == 3) {
         for(int var2 = 0; var2 < var0.g(); ++var2) {
            for(int var3 = 0; var3 < var0.f(); ++var3) {
               ItemStack var4 = var0.a(var2 + var3 * var0.g());
               if (var4.b()) {
                  return false;
               }

               if (var2 == 1 && var3 == 1) {
                  if (!var4.a(Items.us)) {
                     return false;
                  }
               } else if (!var4.a(Items.nD)) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public ItemStack a(InventoryCrafting var0, IRegistryCustom var1) {
      ItemStack var2 = var0.a(1 + var0.g());
      if (!var2.a(Items.us)) {
         return ItemStack.b;
      } else {
         ItemStack var3 = new ItemStack(Items.ur, 8);
         PotionUtil.a(var3, PotionUtil.d(var2));
         PotionUtil.a(var3, PotionUtil.b(var2));
         return var3;
      }
   }

   @Override
   public boolean a(int var0, int var1) {
      return var0 >= 2 && var1 >= 2;
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.j;
   }
}
