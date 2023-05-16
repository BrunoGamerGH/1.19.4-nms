package net.minecraft.world.item.crafting;

import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemWrittenBook;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;

public class RecipeBookClone extends IRecipeComplex {
   public RecipeBookClone(MinecraftKey var0, CraftingBookCategory var1) {
      super(var0, var1);
   }

   public boolean a(InventoryCrafting var0, World var1) {
      int var2 = 0;
      ItemStack var3 = ItemStack.b;

      for(int var4 = 0; var4 < var0.b(); ++var4) {
         ItemStack var5 = var0.a(var4);
         if (!var5.b()) {
            if (var5.a(Items.td)) {
               if (!var3.b()) {
                  return false;
               }

               var3 = var5;
            } else {
               if (!var5.a(Items.tc)) {
                  return false;
               }

               ++var2;
            }
         }
      }

      return !var3.b() && var3.t() && var2 > 0;
   }

   public ItemStack a(InventoryCrafting var0, IRegistryCustom var1) {
      int var2 = 0;
      ItemStack var3 = ItemStack.b;

      for(int var4 = 0; var4 < var0.b(); ++var4) {
         ItemStack var5 = var0.a(var4);
         if (!var5.b()) {
            if (var5.a(Items.td)) {
               if (!var3.b()) {
                  return ItemStack.b;
               }

               var3 = var5;
            } else {
               if (!var5.a(Items.tc)) {
                  return ItemStack.b;
               }

               ++var2;
            }
         }
      }

      if (!var3.b() && var3.t() && var2 >= 1 && ItemWrittenBook.d(var3) < 2) {
         ItemStack var4 = new ItemStack(Items.td, var2);
         NBTTagCompound var5 = var3.u().h();
         var5.a("generation", ItemWrittenBook.d(var3) + 1);
         var4.c(var5);
         return var4;
      } else {
         return ItemStack.b;
      }
   }

   public NonNullList<ItemStack> a(InventoryCrafting var0) {
      NonNullList<ItemStack> var1 = NonNullList.a(var0.b(), ItemStack.b);

      for(int var2 = 0; var2 < var1.size(); ++var2) {
         ItemStack var3 = var0.a(var2);
         if (var3.c().t()) {
            var1.set(var2, new ItemStack(var3.c().s()));
         } else if (var3.c() instanceof ItemWrittenBook) {
            ItemStack var4 = var3.o();
            var4.f(1);
            var1.set(var2, var4);
            break;
         }
      }

      return var1;
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.d;
   }

   @Override
   public boolean a(int var0, int var1) {
      return var0 >= 3 && var1 >= 3;
   }
}
