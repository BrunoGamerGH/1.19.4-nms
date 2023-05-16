package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;

public class EnchantmentLure extends Enchantment {
   protected EnchantmentLure(Enchantment.Rarity var0, EnchantmentSlotType var1, EnumItemSlot... var2) {
      super(var0, var1, var2);
   }

   @Override
   public int a(int var0) {
      return 15 + (var0 - 1) * 9;
   }

   @Override
   public int b(int var0) {
      return super.a(var0) + 50;
   }

   @Override
   public int a() {
      return 3;
   }
}
