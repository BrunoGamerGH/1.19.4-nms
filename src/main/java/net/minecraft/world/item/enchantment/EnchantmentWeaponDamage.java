package net.minecraft.world.item.enchantment;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.item.ItemAxe;
import net.minecraft.world.item.ItemStack;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;

public class EnchantmentWeaponDamage extends Enchantment {
   public static final int a = 0;
   public static final int b = 1;
   public static final int c = 2;
   private static final String[] g = new String[]{"all", "undead", "arthropods"};
   private static final int[] h = new int[]{1, 5, 5};
   private static final int[] i = new int[]{11, 8, 8};
   private static final int[] j = new int[]{20, 20, 20};
   public final int d;

   public EnchantmentWeaponDamage(Enchantment.Rarity enchantment_rarity, int i, EnumItemSlot... aenumitemslot) {
      super(enchantment_rarity, EnchantmentSlotType.f, aenumitemslot);
      this.d = i;
   }

   @Override
   public int a(int i) {
      return h[this.d] + (i - 1) * EnchantmentWeaponDamage.i[this.d];
   }

   @Override
   public int b(int i) {
      return this.a(i) + j[this.d];
   }

   @Override
   public int a() {
      return 5;
   }

   @Override
   public float a(int i, EnumMonsterType enummonstertype) {
      return this.d == 0
         ? 1.0F + (float)Math.max(0, i - 1) * 0.5F
         : (
            this.d == 1 && enummonstertype == EnumMonsterType.b
               ? (float)i * 2.5F
               : (this.d == 2 && enummonstertype == EnumMonsterType.c ? (float)i * 2.5F : 0.0F)
         );
   }

   @Override
   public boolean a(Enchantment enchantment) {
      return !(enchantment instanceof EnchantmentWeaponDamage);
   }

   @Override
   public boolean a(ItemStack itemstack) {
      return itemstack.c() instanceof ItemAxe ? true : super.a(itemstack);
   }

   @Override
   public void a(EntityLiving entityliving, Entity entity, int i) {
      if (entity instanceof EntityLiving entityliving1 && this.d == 2 && i > 0 && entityliving1.eJ() == EnumMonsterType.c) {
         int j = 20 + entityliving.dZ().a(10 * i);
         entityliving1.addEffect(new MobEffect(MobEffects.b, j, 3), Cause.ATTACK);
      }
   }
}
