package net.minecraft.world.item.crafting;

import net.minecraft.core.IRegistryCustom;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemWorldMap;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.saveddata.maps.WorldMap;

public class RecipeMapExtend extends ShapedRecipes {
   public RecipeMapExtend(MinecraftKey var0, CraftingBookCategory var1) {
      super(
         var0,
         "",
         var1,
         3,
         3,
         NonNullList.a(
            RecipeItemStack.a,
            RecipeItemStack.a(Items.pW),
            RecipeItemStack.a(Items.pW),
            RecipeItemStack.a(Items.pW),
            RecipeItemStack.a(Items.pW),
            RecipeItemStack.a(Items.rb),
            RecipeItemStack.a(Items.pW),
            RecipeItemStack.a(Items.pW),
            RecipeItemStack.a(Items.pW),
            RecipeItemStack.a(Items.pW)
         ),
         new ItemStack(Items.tl)
      );
   }

   @Override
   public boolean a(InventoryCrafting var0, World var1) {
      if (!super.a(var0, var1)) {
         return false;
      } else {
         ItemStack var2 = ItemStack.b;

         for(int var3 = 0; var3 < var0.b() && var2.b(); ++var3) {
            ItemStack var4 = var0.a(var3);
            if (var4.a(Items.rb)) {
               var2 = var4;
            }
         }

         if (var2.b()) {
            return false;
         } else {
            WorldMap var3 = ItemWorldMap.a(var2, var1);
            if (var3 == null) {
               return false;
            } else if (var3.e()) {
               return false;
            } else {
               return var3.f < 4;
            }
         }
      }
   }

   @Override
   public ItemStack a(InventoryCrafting var0, IRegistryCustom var1) {
      ItemStack var2 = ItemStack.b;

      for(int var3 = 0; var3 < var0.b() && var2.b(); ++var3) {
         ItemStack var4 = var0.a(var3);
         if (var4.a(Items.rb)) {
            var2 = var4;
         }
      }

      var2 = var2.o();
      var2.f(1);
      var2.v().a("map_scale_direction", 1);
      return var2;
   }

   @Override
   public boolean ah_() {
      return true;
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.f;
   }
}
