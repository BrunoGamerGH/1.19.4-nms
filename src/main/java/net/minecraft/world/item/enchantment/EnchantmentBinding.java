package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class EnchantmentBinding extends Enchantment {
   public EnchantmentBinding(Enchantment.Rarity var0, EnumItemSlot... var1) {
      super(var0, EnchantmentSlotType.l, var1);
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

   @Override
   public boolean a(ItemStack var0) {
      return !var0.a(Items.ut) && super.a(var0);
   }
}
