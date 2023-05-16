package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EnchantmentDigging extends Enchantment {
   protected EnchantmentDigging(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.g, var1);
   }

   @Override
   public int a(int var0) {
      return 1 + 10 * (var0 - 1);
   }

   @Override
   public int b(int var0) {
      return super.a(var0) + 50;
   }

   @Override
   public int a() {
      return 5;
   }

   @Override
   public boolean a(ItemStack var0) {
      return var0.a(Items.rc) ? true : super.a(var0);
   }
}
