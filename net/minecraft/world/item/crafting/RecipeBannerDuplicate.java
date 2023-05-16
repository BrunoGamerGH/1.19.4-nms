package net.minecraft.world.item.crafting;

import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemBanner;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntityBanner;

public class RecipeBannerDuplicate extends IRecipeComplex {
   public RecipeBannerDuplicate(MinecraftKey var0, CraftingBookCategory var1) {
      super(var0, var1);
   }

   public boolean a(InventoryCrafting var0, World var1) {
      EnumColor var2 = null;
      ItemStack var3 = null;
      ItemStack var4 = null;

      for(int var5 = 0; var5 < var0.b(); ++var5) {
         ItemStack var6 = var0.a(var5);
         if (!var6.b()) {
            Item var7 = var6.c();
            if (!(var7 instanceof ItemBanner)) {
               return false;
            }

            ItemBanner var8 = (ItemBanner)var7;
            if (var2 == null) {
               var2 = var8.b();
            } else if (var2 != var8.b()) {
               return false;
            }

            int var9 = TileEntityBanner.c(var6);
            if (var9 > 6) {
               return false;
            }

            if (var9 > 0) {
               if (var3 != null) {
                  return false;
               }

               var3 = var6;
            } else {
               if (var4 != null) {
                  return false;
               }

               var4 = var6;
            }
         }
      }

      return var3 != null && var4 != null;
   }

   public ItemStack a(InventoryCrafting var0, IRegistryCustom var1) {
      for(int var2 = 0; var2 < var0.b(); ++var2) {
         ItemStack var3 = var0.a(var2);
         if (!var3.b()) {
            int var4 = TileEntityBanner.c(var3);
            if (var4 > 0 && var4 <= 6) {
               ItemStack var5 = var3.o();
               var5.f(1);
               return var5;
            }
         }
      }

      return ItemStack.b;
   }

   public NonNullList<ItemStack> a(InventoryCrafting var0) {
      NonNullList<ItemStack> var1 = NonNullList.a(var0.b(), ItemStack.b);

      for(int var2 = 0; var2 < var1.size(); ++var2) {
         ItemStack var3 = var0.a(var2);
         if (!var3.b()) {
            if (var3.c().t()) {
               var1.set(var2, new ItemStack(var3.c().s()));
            } else if (var3.t() && TileEntityBanner.c(var3) > 0) {
               ItemStack var4 = var3.o();
               var4.f(1);
               var1.set(var2, var4);
            }
         }
      }

      return var1;
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.k;
   }

   @Override
   public boolean a(int var0, int var1) {
      return var0 * var1 >= 2;
   }
}
