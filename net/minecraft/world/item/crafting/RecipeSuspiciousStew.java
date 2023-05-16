package net.minecraft.world.item.crafting;

import net.minecraft.core.IRegistryCustom;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.tags.TagsItem;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemSuspiciousStew;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SuspiciousEffectHolder;

public class RecipeSuspiciousStew extends IRecipeComplex {
   public RecipeSuspiciousStew(MinecraftKey var0, CraftingBookCategory var1) {
      super(var0, var1);
   }

   public boolean a(InventoryCrafting var0, World var1) {
      boolean var2 = false;
      boolean var3 = false;
      boolean var4 = false;
      boolean var5 = false;

      for(int var6 = 0; var6 < var0.b(); ++var6) {
         ItemStack var7 = var0.a(var6);
         if (!var7.b()) {
            if (var7.a(Blocks.ce.k()) && !var4) {
               var4 = true;
            } else if (var7.a(Blocks.cf.k()) && !var3) {
               var3 = true;
            } else if (var7.a(TagsItem.N) && !var2) {
               var2 = true;
            } else {
               if (!var7.a(Items.oy) || var5) {
                  return false;
               }

               var5 = true;
            }
         }
      }

      return var2 && var4 && var3 && var5;
   }

   public ItemStack a(InventoryCrafting var0, IRegistryCustom var1) {
      ItemStack var2 = new ItemStack(Items.uU, 1);

      for(int var3 = 0; var3 < var0.b(); ++var3) {
         ItemStack var4 = var0.a(var3);
         if (!var4.b()) {
            SuspiciousEffectHolder var5 = SuspiciousEffectHolder.a(var4.c());
            if (var5 != null) {
               ItemSuspiciousStew.a(var2, var5.b(), var5.c());
               break;
            }
         }
      }

      return var2;
   }

   @Override
   public boolean a(int var0, int var1) {
      return var0 >= 2 && var1 >= 2;
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.n;
   }
}
