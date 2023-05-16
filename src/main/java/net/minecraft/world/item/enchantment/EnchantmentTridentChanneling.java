package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;

public class EnchantmentTridentChanneling extends Enchantment {
   public EnchantmentTridentChanneling(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.i, var1);
   }

   @Override
   public int a(int var0) {
      return 25;
   }

   @Override
   public int b(int var0) {
      return 50;
   }
}
