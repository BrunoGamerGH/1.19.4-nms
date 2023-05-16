package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;

public class EnchantmentTridentLoyalty extends Enchantment {
   public EnchantmentTridentLoyalty(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.i, var1);
   }

   @Override
   public int a(int var0) {
      return 5 + var0 * 7;
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
