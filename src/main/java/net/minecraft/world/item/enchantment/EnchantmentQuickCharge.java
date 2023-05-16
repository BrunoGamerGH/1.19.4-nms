package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;

public class EnchantmentQuickCharge extends Enchantment {
   public EnchantmentQuickCharge(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.m, var1);
   }

   @Override
   public int a(int var0) {
      return 12 + (var0 - 1) * 20;
   }

   @Override
   public int b(int var0) {
      return 50;
   }

   @Override
   public int a() {
      return 3;
   }
}
