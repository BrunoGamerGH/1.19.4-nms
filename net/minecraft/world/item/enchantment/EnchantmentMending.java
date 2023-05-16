package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;

public class EnchantmentMending extends Enchantment {
   public EnchantmentMending(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.j, var1);
   }

   @Override
   public int a(int var0) {
      return var0 * 25;
   }

   @Override
   public int b(int var0) {
      return this.a(var0) + 50;
   }

   @Override
   public boolean b() {
      return true;
   }
}
