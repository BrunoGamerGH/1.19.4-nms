package net.minecraft.world.item.enchantment;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.MathHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;

public class EnchantmentProtection extends Enchantment {
   public final EnchantmentProtection.DamageType a;

   public EnchantmentProtection(Enchantment.Rarity var0, EnchantmentProtection.DamageType var1, EnumItemSlot... var2) {
      super(var0, var1 == EnchantmentProtection.DamageType.c ? EnchantmentSlotType.b : EnchantmentSlotType.a, var2);
      this.a = var1;
   }

   @Override
   public int a(int var0) {
      return this.a.a() + (var0 - 1) * this.a.b();
   }

   @Override
   public int b(int var0) {
      return this.a(var0) + this.a.b();
   }

   @Override
   public int a() {
      return 4;
   }

   @Override
   public int a(int var0, DamageSource var1) {
      if (var1.a(DamageTypeTags.d)) {
         return 0;
      } else if (this.a == EnchantmentProtection.DamageType.a) {
         return var0;
      } else if (this.a == EnchantmentProtection.DamageType.b && var1.a(DamageTypeTags.i)) {
         return var0 * 2;
      } else if (this.a == EnchantmentProtection.DamageType.c && var1.a(DamageTypeTags.m)) {
         return var0 * 3;
      } else if (this.a == EnchantmentProtection.DamageType.d && var1.a(DamageTypeTags.l)) {
         return var0 * 2;
      } else {
         return this.a == EnchantmentProtection.DamageType.e && var1.a(DamageTypeTags.j) ? var0 * 2 : 0;
      }
   }

   @Override
   public boolean a(Enchantment var0) {
      if (var0 instanceof EnchantmentProtection var1) {
         if (this.a == var1.a) {
            return false;
         } else {
            return this.a == EnchantmentProtection.DamageType.c || var1.a == EnchantmentProtection.DamageType.c;
         }
      } else {
         return super.a(var0);
      }
   }

   public static int a(EntityLiving var0, int var1) {
      int var2 = EnchantmentManager.a(Enchantments.b, var0);
      if (var2 > 0) {
         var1 -= MathHelper.d((float)var1 * (float)var2 * 0.15F);
      }

      return var1;
   }

   public static double a(EntityLiving var0, double var1) {
      int var3 = EnchantmentManager.a(Enchantments.d, var0);
      if (var3 > 0) {
         var1 *= MathHelper.a(1.0 - (double)var3 * 0.15, 0.0, 1.0);
      }

      return var1;
   }

   public static enum DamageType {
      a(1, 11),
      b(10, 8),
      c(5, 6),
      d(5, 8),
      e(3, 6);

      private final int f;
      private final int g;

      private DamageType(int var2, int var3) {
         this.f = var2;
         this.g = var3;
      }

      public int a() {
         return this.f;
      }

      public int b() {
         return this.g;
      }
   }
}
