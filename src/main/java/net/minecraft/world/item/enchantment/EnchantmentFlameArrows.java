package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;

public class EnchantmentFlameArrows extends Enchantment {
   public EnchantmentFlameArrows(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.k, var1);
   }

   @Override
   public int a(int var0) {
      return 20;
   }

   @Override
   public int b(int var0) {
      return 50;
   }
}
