package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.IDyeable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDye;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;

public class RecipeArmorDye extends IRecipeComplex {
   public RecipeArmorDye(MinecraftKey var0, CraftingBookCategory var1) {
      super(var0, var1);
   }

   public boolean a(InventoryCrafting var0, World var1) {
      ItemStack var2 = ItemStack.b;
      List<ItemStack> var3 = Lists.newArrayList();

      for(int var4 = 0; var4 < var0.b(); ++var4) {
         ItemStack var5 = var0.a(var4);
         if (!var5.b()) {
            if (var5.c() instanceof IDyeable) {
               if (!var2.b()) {
                  return false;
               }

               var2 = var5;
            } else {
               if (!(var5.c() instanceof ItemDye)) {
                  return false;
               }

               var3.add(var5);
            }
         }
      }

      return !var2.b() && !var3.isEmpty();
   }

   public ItemStack a(InventoryCrafting var0, IRegistryCustom var1) {
      List<ItemDye> var2 = Lists.newArrayList();
      ItemStack var3 = ItemStack.b;

      for(int var4 = 0; var4 < var0.b(); ++var4) {
         ItemStack var5 = var0.a(var4);
         if (!var5.b()) {
            Item var6 = var5.c();
            if (var6 instanceof IDyeable) {
               if (!var3.b()) {
                  return ItemStack.b;
               }

               var3 = var5.o();
            } else {
               if (!(var6 instanceof ItemDye)) {
                  return ItemStack.b;
               }

               var2.add((ItemDye)var6);
            }
         }
      }

      return !var3.b() && !var2.isEmpty() ? IDyeable.a(var3, var2) : ItemStack.b;
   }

   @Override
   public boolean a(int var0, int var1) {
      return var0 * var1 >= 2;
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.c;
   }
}
