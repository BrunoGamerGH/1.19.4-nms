package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;

public class EnchantmentSilkTouch extends Enchantment {
   protected EnchantmentSilkTouch(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.g, var1);
   }

   @Override
   public int a(int var0) {
      return 15;
   }

   @Override
   public int b(int var0) {
      return super.a(var0) + 50;
   }

   @Override
   public boolean a(Enchantment var0) {
      return super.a(var0) && var0 != Enchantments.x;
   }
}
