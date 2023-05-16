package net.minecraft.world.item;

import java.util.List;
import net.minecraft.nbt.NBTTagCompound;

public interface IDyeable {
   String m_ = "color";
   String n_ = "display";
   int o_ = 10511680;

   default boolean a(ItemStack var0) {
      NBTTagCompound var1 = var0.b("display");
      return var1 != null && var1.b("color", 99);
   }

   default int e_(ItemStack var0) {
      NBTTagCompound var1 = var0.b("display");
      return var1 != null && var1.b("color", 99) ? var1.h("color") : 10511680;
   }

   default void f_(ItemStack var0) {
      NBTTagCompound var1 = var0.b("display");
      if (var1 != null && var1.e("color")) {
         var1.r("color");
      }
   }

   default void a(ItemStack var0, int var1) {
      var0.a("display").a("color", var1);
   }

   static ItemStack a(ItemStack var0, List<ItemDye> var1) {
      ItemStack var2 = ItemStack.b;
      int[] var3 = new int[3];
      int var4 = 0;
      int var5 = 0;
      IDyeable var6 = null;
      Item var7 = var0.c();
      if (var7 instanceof IDyeable) {
         var6 = (IDyeable)var7;
         var2 = var0.o();
         var2.f(1);
         if (var6.a(var0)) {
            int var8 = var6.e_(var2);
            float var9 = (float)(var8 >> 16 & 0xFF) / 255.0F;
            float var10 = (float)(var8 >> 8 & 0xFF) / 255.0F;
            float var11 = (float)(var8 & 0xFF) / 255.0F;
            var4 += (int)(Math.max(var9, Math.max(var10, var11)) * 255.0F);
            var3[0] += (int)(var9 * 255.0F);
            var3[1] += (int)(var10 * 255.0F);
            var3[2] += (int)(var11 * 255.0F);
            ++var5;
         }

         for(ItemDye var9 : var1) {
            float[] var10 = var9.d().d();
            int var11 = (int)(var10[0] * 255.0F);
            int var12 = (int)(var10[1] * 255.0F);
            int var13 = (int)(var10[2] * 255.0F);
            var4 += Math.max(var11, Math.max(var12, var13));
            var3[0] += var11;
            var3[1] += var12;
            var3[2] += var13;
            ++var5;
         }
      }

      if (var6 == null) {
         return ItemStack.b;
      } else {
         int var8 = var3[0] / var5;
         int var9 = var3[1] / var5;
         int var10 = var3[2] / var5;
         float var11 = (float)var4 / (float)var5;
         float var12 = (float)Math.max(var8, Math.max(var9, var10));
         var8 = (int)((float)var8 * var11 / var12);
         var9 = (int)((float)var9 * var11 / var12);
         var10 = (int)((float)var10 * var11 / var12);
         int var26 = (var8 << 8) + var9;
         var26 = (var26 << 8) + var10;
         var6.a(var2, var26);
         return var2;
      }
   }
}
