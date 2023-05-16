package net.minecraft.world.item.crafting;

import net.minecraft.core.IRegistryCustom;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;

public class RecipeMapClone extends IRecipeComplex {
   public RecipeMapClone(MinecraftKey var0, CraftingBookCategory var1) {
      super(var0, var1);
   }

   public boolean a(InventoryCrafting var0, World var1) {
      int var2 = 0;
      ItemStack var3 = ItemStack.b;

      for(int var4 = 0; var4 < var0.b(); ++var4) {
         ItemStack var5 = var0.a(var4);
         if (!var5.b()) {
            if (var5.a(Items.rb)) {
               if (!var3.b()) {
                  return false;
               }

               var3 = var5;
            } else {
               if (!var5.a(Items.tl)) {
                  return false;
               }

               ++var2;
            }
         }
      }

      return !var3.b() && var2 > 0;
   }

   public ItemStack a(InventoryCrafting var0, IRegistryCustom var1) {
      int var2 = 0;
      ItemStack var3 = ItemStack.b;

      for(int var4 = 0; var4 < var0.b(); ++var4) {
         ItemStack var5 = var0.a(var4);
         if (!var5.b()) {
            if (var5.a(Items.rb)) {
               if (!var3.b()) {
                  return ItemStack.b;
               }

               var3 = var5;
            } else {
               if (!var5.a(Items.tl)) {
                  return ItemStack.b;
               }

               ++var2;
            }
         }
      }

      if (!var3.b() && var2 >= 1) {
         ItemStack var4 = var3.o();
         var4.f(var2 + 1);
         return var4;
      } else {
         return ItemStack.b;
      }
   }

   @Override
   public boolean a(int var0, int var1) {
      return var0 >= 3 && var1 >= 3;
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.e;
   }
}
