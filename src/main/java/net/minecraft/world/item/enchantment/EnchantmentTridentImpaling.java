package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMonsterType;

public class EnchantmentTridentImpaling extends Enchantment {
   public EnchantmentTridentImpaling(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.i, var1);
   }

   @Override
   public int a(int var0) {
      return 1 + (var0 - 1) * 8;
   }

   @Override
   public int b(int var0) {
      return this.a(var0) + 20;
   }

   @Override
   public int a() {
      return 5;
   }

   @Override
   public float a(int var0, EnumMonsterType var1) {
      return var1 == EnumMonsterType.e ? (float)var0 * 2.5F : 0.0F;
   }
}
