package net.minecraft.world.item.enchantment;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.item.ItemArmor;
import net.minecraft.world.item.ItemStack;

public class EnchantmentDurability extends Enchantment {
   protected EnchantmentDurability(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.j, var1);
   }

   @Override
   public int a(int var0) {
      return 5 + (var0 - 1) * 8;
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
      return var0.h() ? true : super.a(var0);
   }

   public static boolean a(ItemStack var0, int var1, RandomSource var2) {
      if (var0.c() instanceof ItemArmor && var2.i() < 0.6F) {
         return false;
      } else {
         return var2.a(var1 + 1) > 0;
      }
   }
}
