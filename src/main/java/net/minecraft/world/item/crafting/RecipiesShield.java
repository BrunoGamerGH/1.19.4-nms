package net.minecraft.world.item.crafting;

import net.minecraft.core.IRegistryCustom;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.ItemBanner;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntityTypes;

public class RecipiesShield extends IRecipeComplex {
   public RecipiesShield(MinecraftKey var0, CraftingBookCategory var1) {
      super(var0, var1);
   }

   public boolean a(InventoryCrafting var0, World var1) {
      ItemStack var2 = ItemStack.b;
      ItemStack var3 = ItemStack.b;

      for(int var4 = 0; var4 < var0.b(); ++var4) {
         ItemStack var5 = var0.a(var4);
         if (!var5.b()) {
            if (var5.c() instanceof ItemBanner) {
               if (!var3.b()) {
                  return false;
               }

               var3 = var5;
            } else {
               if (!var5.a(Items.ut)) {
                  return false;
               }

               if (!var2.b()) {
                  return false;
               }

               if (ItemBlock.a(var5) != null) {
                  return false;
               }

               var2 = var5;
            }
         }
      }

      return !var2.b() && !var3.b();
   }

   public ItemStack a(InventoryCrafting var0, IRegistryCustom var1) {
      ItemStack var2 = ItemStack.b;
      ItemStack var3 = ItemStack.b;

      for(int var4 = 0; var4 < var0.b(); ++var4) {
         ItemStack var5 = var0.a(var4);
         if (!var5.b()) {
            if (var5.c() instanceof ItemBanner) {
               var2 = var5;
            } else if (var5.a(Items.ut)) {
               var3 = var5.o();
            }
         }
      }

      if (var3.b()) {
         return var3;
      } else {
         NBTTagCompound var4 = ItemBlock.a(var2);
         NBTTagCompound var5 = var4 == null ? new NBTTagCompound() : var4.h();
         var5.a("Base", ((ItemBanner)var2.c()).b().a());
         ItemBlock.a(var3, TileEntityTypes.t, var5);
         return var3;
      }
   }

   @Override
   public boolean a(int var0, int var1) {
      return var0 * var1 >= 2;
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.l;
   }
}
