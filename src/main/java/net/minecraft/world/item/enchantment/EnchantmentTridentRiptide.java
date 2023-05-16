package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;

public class EnchantmentTridentRiptide extends Enchantment {
   public EnchantmentTridentRiptide(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.i, var1);
   }

   @Override
   public int a(int var0) {
      return 10 + var0 * 7;
   }

   @Override
   public int b(int var0) {
      return 50;
   }

   @Override
   public int a() {
      return 3;
   }

   @Override
   public boolean a(Enchantment var0) {
      return super.a(var0) && var0 != Enchantments.E && var0 != Enchantments.H;
   }
}
