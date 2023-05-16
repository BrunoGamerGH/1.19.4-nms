package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;

public class EnchantmentOxygen extends Enchantment {
   public EnchantmentOxygen(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.e, var1);
   }

   @Override
   public int a(int var0) {
      return 10 * var0;
   }

   @Override
   public int b(int var0) {
      return this.a(var0) + 30;
   }

   @Override
   public int a() {
      return 3;
   }
}
