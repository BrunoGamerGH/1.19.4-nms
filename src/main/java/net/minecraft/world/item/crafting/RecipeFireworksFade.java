package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.IRegistryCustom;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDye;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;

public class RecipeFireworksFade extends IRecipeComplex {
   private static final RecipeItemStack a = RecipeItemStack.a(Items.tx);

   public RecipeFireworksFade(MinecraftKey var0, CraftingBookCategory var1) {
      super(var0, var1);
   }

   public boolean a(InventoryCrafting var0, World var1) {
      boolean var2 = false;
      boolean var3 = false;

      for(int var4 = 0; var4 < var0.b(); ++var4) {
         ItemStack var5 = var0.a(var4);
         if (!var5.b()) {
            if (var5.c() instanceof ItemDye) {
               var2 = true;
            } else {
               if (!a.a(var5)) {
                  return false;
               }

               if (var3) {
                  return false;
               }

               var3 = true;
            }
         }
      }

      return var3 && var2;
   }

   public ItemStack a(InventoryCrafting var0, IRegistryCustom var1) {
      List<Integer> var2 = Lists.newArrayList();
      ItemStack var3 = null;

      for(int var4 = 0; var4 < var0.b(); ++var4) {
         ItemStack var5 = var0.a(var4);
         Item var6 = var5.c();
         if (var6 instanceof ItemDye) {
            var2.add(((ItemDye)var6).d().f());
         } else if (a.a(var5)) {
            var3 = var5.o();
            var3.f(1);
         }
      }

      if (var3 != null && !var2.isEmpty()) {
         var3.a("Explosion").b("FadeColors", var2);
         return var3;
      } else {
         return ItemStack.b;
      }
   }

   @Override
   public boolean a(int var0, int var1) {
      return var0 * var1 >= 2;
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.i;
   }
}
