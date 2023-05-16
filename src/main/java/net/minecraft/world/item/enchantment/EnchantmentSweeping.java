package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;

public class EnchantmentSweeping extends Enchantment {
   public EnchantmentSweeping(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.f, var1);
   }

   @Override
   public int a(int var0) {
      return 5 + (var0 - 1) * 9;
   }

   @Override
   public int b(int var0) {
      return this.a(var0) + 15;
   }

   @Override
   public int a() {
      return 3;
   }

   public static float e(int var0) {
      return 1.0F - 1.0F / (float)(var0 + 1);
   }
}
