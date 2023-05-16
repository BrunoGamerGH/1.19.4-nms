package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;

public class EnchantmentSoulSpeed extends Enchantment {
   public EnchantmentSoulSpeed(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.b, var1);
   }

   @Override
   public int a(int var0) {
      return var0 * 10;
   }

   @Override
   public int b(int var0) {
      return this.a(var0) + 15;
   }

   @Override
   public boolean b() {
      return true;
   }

   @Override
   public boolean h() {
      return false;
   }

   @Override
   public boolean i() {
      return false;
   }

   @Override
   public int a() {
      return 3;
   }
}
