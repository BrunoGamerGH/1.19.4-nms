package net.minecraft.world;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.item.ItemStack;

public class ContainerUtil {
   public static ItemStack a(List<ItemStack> var0, int var1, int var2) {
      return var1 >= 0 && var1 < var0.size() && !var0.get(var1).b() && var2 > 0 ? var0.get(var1).a(var2) : ItemStack.b;
   }

   public static ItemStack a(List<ItemStack> var0, int var1) {
      return var1 >= 0 && var1 < var0.size() ? var0.set(var1, ItemStack.b) : ItemStack.b;
   }

   public static NBTTagCompound a(NBTTagCompound var0, NonNullList<ItemStack> var1) {
      return a(var0, var1, true);
   }

   public static NBTTagCompound a(NBTTagCompound var0, NonNullList<ItemStack> var1, boolean var2) {
      NBTTagList var3 = new NBTTagList();

      for(int var4 = 0; var4 < var1.size(); ++var4) {
         ItemStack var5 = var1.get(var4);
         if (!var5.b()) {
            NBTTagCompound var6 = new NBTTagCompound();
            var6.a("Slot", (byte)var4);
            var5.b(var6);
            var3.add(var6);
         }
      }

      if (!var3.isEmpty() || var2) {
         var0.a("Items", var3);
      }

      return var0;
   }

   public static void b(NBTTagCompound var0, NonNullList<ItemStack> var1) {
      NBTTagList var2 = var0.c("Items", 10);

      for(int var3 = 0; var3 < var2.size(); ++var3) {
         NBTTagCompound var4 = var2.a(var3);
         int var5 = var4.f("Slot") & 255;
         if (var5 >= 0 && var5 < var1.size()) {
            var1.set(var5, ItemStack.a(var4));
         }
      }
   }

   public static int a(IInventory var0, Predicate<ItemStack> var1, int var2, boolean var3) {
      int var4 = 0;

      for(int var5 = 0; var5 < var0.b(); ++var5) {
         ItemStack var6 = var0.a(var5);
         int var7 = a(var6, var1, var2 - var4, var3);
         if (var7 > 0 && !var3 && var6.b()) {
            var0.a(var5, ItemStack.b);
         }

         var4 += var7;
      }

      return var4;
   }

   public static int a(ItemStack var0, Predicate<ItemStack> var1, int var2, boolean var3) {
      if (var0.b() || !var1.test(var0)) {
         return 0;
      } else if (var3) {
         return var0.K();
      } else {
         int var4 = var2 < 0 ? var0.K() : Math.min(var2, var0.K());
         var0.h(var4);
         return var4;
      }
   }
}
