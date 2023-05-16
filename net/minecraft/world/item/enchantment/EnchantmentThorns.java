package net.minecraft.world.item.enchantment;

import java.util.Map.Entry;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.item.ItemArmor;
import net.minecraft.world.item.ItemStack;

public class EnchantmentThorns extends Enchantment {
   private static final float a = 0.15F;

   public EnchantmentThorns(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.d, var1);
   }

   @Override
   public int a(int var0) {
      return 10 + 20 * (var0 - 1);
   }

   @Override
   public int b(int var0) {
      return super.a(var0) + 50;
   }

   @Override
   public int a() {
      return 3;
   }

   @Override
   public boolean a(ItemStack var0) {
      return var0.c() instanceof ItemArmor ? true : super.a(var0);
   }

   @Override
   public void b(EntityLiving var0, Entity var1, int var2) {
      RandomSource var3 = var0.dZ();
      Entry<EnumItemSlot, ItemStack> var4 = EnchantmentManager.b(Enchantments.h, var0);
      if (a(var2, var3)) {
         if (var1 != null) {
            var1.a(var0.dG().d(var0), (float)b(var2, var3));
         }

         if (var4 != null) {
            var4.getValue().a(2, var0, var1x -> var1x.d(var4.getKey()));
         }
      }
   }

   public static boolean a(int var0, RandomSource var1) {
      if (var0 <= 0) {
         return false;
      } else {
         return var1.i() < 0.15F * (float)var0;
      }
   }

   public static int b(int var0, RandomSource var1) {
      return var0 > 10 ? var0 - 10 : 1 + var1.a(4);
   }
}
