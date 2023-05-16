package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;

public class EnchantmentDepthStrider extends Enchantment {
   public EnchantmentDepthStrider(Enchantment.Rarity var0, EnumItemSlot... var1) {
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
   public int a() {
      return 3;
   }

   @Override
   public boolean a(Enchantment var0) {
      return super.a(var0) && var0 != Enchantments.j;
   }
}
