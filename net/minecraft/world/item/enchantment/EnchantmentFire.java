package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;

public class EnchantmentFire extends Enchantment {
   protected EnchantmentFire(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.f, var1);
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
      return 2;
   }
}
