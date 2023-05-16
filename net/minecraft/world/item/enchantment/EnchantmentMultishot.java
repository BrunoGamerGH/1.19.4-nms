package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;

public class EnchantmentMultishot extends Enchantment {
   public EnchantmentMultishot(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.m, var1);
   }

   @Override
   public int a(int var0) {
      return 20;
   }

   @Override
   public int b(int var0) {
      return 50;
   }

   @Override
   public boolean a(Enchantment var0) {
      return super.a(var0) && var0 != Enchantments.K;
   }
}
