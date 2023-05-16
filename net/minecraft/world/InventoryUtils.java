package net.minecraft.world;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;

public class InventoryUtils {
   public static void a(World var0, BlockPosition var1, IInventory var2) {
      a(var0, (double)var1.u(), (double)var1.v(), (double)var1.w(), var2);
   }

   public static void a(World var0, Entity var1, IInventory var2) {
      a(var0, var1.dl(), var1.dn(), var1.dr(), var2);
   }

   private static void a(World var0, double var1, double var3, double var5, IInventory var7) {
      for(int var8 = 0; var8 < var7.b(); ++var8) {
         a(var0, var1, var3, var5, var7.a(var8));
      }
   }

   public static void a(World var0, BlockPosition var1, NonNullList<ItemStack> var2) {
      var2.forEach(var2x -> a(var0, (double)var1.u(), (double)var1.v(), (double)var1.w(), var2x));
   }

   public static void a(World var0, double var1, double var3, double var5, ItemStack var7) {
      double var8 = (double)EntityTypes.ad.k();
      double var10 = 1.0 - var8;
      double var12 = var8 / 2.0;
      double var14 = Math.floor(var1) + var0.z.j() * var10 + var12;
      double var16 = Math.floor(var3) + var0.z.j() * var10;
      double var18 = Math.floor(var5) + var0.z.j() * var10 + var12;

      while(!var7.b()) {
         EntityItem var20 = new EntityItem(var0, var14, var16, var18, var7.a(var0.z.a(21) + 10));
         float var21 = 0.05F;
         var20.o(var0.z.a(0.0, 0.11485000171139836), var0.z.a(0.2, 0.11485000171139836), var0.z.a(0.0, 0.11485000171139836));
         var0.b(var20);
      }
   }
}
