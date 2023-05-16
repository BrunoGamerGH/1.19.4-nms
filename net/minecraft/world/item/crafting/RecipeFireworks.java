package net.minecraft.world.item.crafting;

import net.minecraft.core.IRegistryCustom;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.inventory.InventoryCrafting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.World;

public class RecipeFireworks extends IRecipeComplex {
   private static final RecipeItemStack a = RecipeItemStack.a(Items.pW);
   private static final RecipeItemStack b = RecipeItemStack.a(Items.oC);
   private static final RecipeItemStack c = RecipeItemStack.a(Items.tx);

   public RecipeFireworks(MinecraftKey var0, CraftingBookCategory var1) {
      super(var0, var1);
   }

   public boolean a(InventoryCrafting var0, World var1) {
      boolean var2 = false;
      int var3 = 0;

      for(int var4 = 0; var4 < var0.b(); ++var4) {
         ItemStack var5 = var0.a(var4);
         if (!var5.b()) {
            if (a.a(var5)) {
               if (var2) {
                  return false;
               }

               var2 = true;
            } else if (b.a(var5)) {
               if (++var3 > 3) {
                  return false;
               }
            } else if (!c.a(var5)) {
               return false;
            }
         }
      }

      return var2 && var3 >= 1;
   }

   public ItemStack a(InventoryCrafting var0, IRegistryCustom var1) {
      ItemStack var2 = new ItemStack(Items.tw, 3);
      NBTTagCompound var3 = var2.a("Fireworks");
      NBTTagList var4 = new NBTTagList();
      int var5 = 0;

      for(int var6 = 0; var6 < var0.b(); ++var6) {
         ItemStack var7 = var0.a(var6);
         if (!var7.b()) {
            if (b.a(var7)) {
               ++var5;
            } else if (c.a(var7)) {
               NBTTagCompound var8 = var7.b("Explosion");
               if (var8 != null) {
                  var4.add(var8);
               }
            }
         }
      }

      var3.a("Flight", (byte)var5);
      if (!var4.isEmpty()) {
         var3.a("Explosions", var4);
      }

      return var2;
   }

   @Override
   public boolean a(int var0, int var1) {
      return var0 * var1 >= 2;
   }

   @Override
   public ItemStack a(IRegistryCustom var0) {
      return new ItemStack(Items.tw);
   }

   @Override
   public RecipeSerializer<?> ai_() {
      return RecipeSerializer.g;
   }
}
