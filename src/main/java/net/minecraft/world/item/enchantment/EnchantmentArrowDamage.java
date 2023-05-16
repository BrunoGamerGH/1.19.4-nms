package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;

public class EnchantmentArrowDamage extends Enchantment {
   public EnchantmentArrowDamage(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.k, var1);
   }

   @Override
   public int a(int var0) {
      return 1 + (var0 - 1) * 10;
   }

   @Override
   public int b(int var0) {
      return this.a(var0) + 15;
   }

   @Override
   public int a() {
      return 5;
   }
}
