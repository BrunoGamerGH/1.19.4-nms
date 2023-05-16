package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;

public class EnchantmentVanishing extends Enchantment {
   public EnchantmentVanishing(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.n, var1);
   }

   @Override
   public int a(int var0) {
      return 25;
   }

   @Override
   public int b(int var0) {
      return 50;
   }

   @Override
   public boolean b() {
      return true;
   }

   @Override
   public boolean c() {
      return true;
   }
}
