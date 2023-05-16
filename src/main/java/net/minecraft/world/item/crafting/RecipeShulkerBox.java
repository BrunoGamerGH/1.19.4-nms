package net.minecraft.world.item.crafting;

import net.minecraft.core.IRegistryCustom;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDye;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockShulkerBox;

public class RecipeShulkerBox extends IRecipeComplex {
   public RecipeShulkerBox(MinecraftKey var0, CraftingBookCategory var1) {
      super(var0, var1);
   }

   public boolean a(InventoryCrafting var0, World var1) {
      int var2 = 0;
      int var3 = 0;

      for(int var4 = 0; var4 < var0.b(); ++var4) {
         ItemStack var5 = var0.a(var4);
         if (!var5.b()) {
            if (Block.a(var5.c()) instanceof BlockShulkerBox) {
               ++var2;
            } else {
               if (!(var5.c() instanceof ItemDye)) {
                  return false;
               }

               ++var3;
            }

            if (var3 > 1 || var2 > 1) {
               return false;
            }
         }
      }

      return var2 == 1 && var3 == 1;
   }

   public ItemStack a(InventoryCrafting var0, IRegistryCustom var1) {
      ItemStack var2 = ItemStack.b;
      ItemDye var3 = (ItemDye)Items.qq;

      for(int var4 = 0; var4 < var0.b(); ++var4) {
         ItemStack var5 = var0.a(var4);
         if (!var5.b()) {
            Item var6 = var5.c();
            if (Block.a(var6) instanceof BlockShulkerBox) {
               var2 = var5;
            } else if (var6 instanceof ItemDye) {
               var3 = (ItemDye)var6;
            }
         }
      }

      ItemStack var4 = BlockShulkerBox.b(var3.d());
      if (var2.t()) {
         var4.c(var2.u().h());
      }

      return var4;
   }

   @Override
   public boolean a(int var0, int var1) {
      return var0 * var1 >= 2;
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.m;
   }
}
