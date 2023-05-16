package net.minecraft.world.item.enchantment;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.EnumChatFormat;
import net.minecraft.SystemUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.network.chat.IChatMutableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.item.ItemStack;

public abstract class Enchantment {
   private final EnumItemSlot[] a;
   private final Enchantment.Rarity b;
   public final EnchantmentSlotType e;
   @Nullable
   protected String f;

   @Nullable
   public static Enchantment c(int var0) {
      return BuiltInRegistries.g.a(var0);
   }

   protected Enchantment(Enchantment.Rarity var0, EnchantmentSlotType var1, EnumItemSlot[] var2) {
      this.b = var0;
      this.e = var1;
      this.a = var2;
   }

   public Map<EnumItemSlot, ItemStack> a(EntityLiving var0) {
      Map<EnumItemSlot, ItemStack> var1 = Maps.newEnumMap(EnumItemSlot.class);

      for(EnumItemSlot var5 : this.a) {
         ItemStack var6 = var0.c(var5);
         if (!var6.b()) {
            var1.put(var5, var6);
         }
      }

      return var1;
   }

   public Enchantment.Rarity d() {
      return this.b;
   }

   public int e() {
      return 1;
   }

   public int a() {
      return 1;
   }

   public int a(int var0) {
      return 1 + var0 * 10;
   }

   public int b(int var0) {
      return this.a(var0) + 5;
   }

   public int a(int var0, DamageSource var1) {
      return 0;
   }

   public float a(int var0, EnumMonsterType var1) {
      return 0.0F;
   }

   public final boolean b(Enchantment var0) {
      return this.a(var0) && var0.a(this);
   }

   protected boolean a(Enchantment var0) {
      return this != var0;
   }

   protected String f() {
      if (this.f == null) {
         this.f = SystemUtils.a("enchantment", BuiltInRegistries.g.b(this));
      }

      return this.f;
   }

   public String g() {
      return this.f();
   }

   public IChatBaseComponent d(int var0) {
      IChatMutableComponent var1 = IChatBaseComponent.c(this.g());
      if (this.c()) {
         var1.a(EnumChatFormat.m);
      } else {
         var1.a(EnumChatFormat.h);
      }

      if (var0 != 1 || this.a() != 1) {
         var1.b(CommonComponents.q).b(IChatBaseComponent.c("enchantment.level." + var0));
      }

      return var1;
   }

   public boolean a(ItemStack var0) {
      return this.e.a(var0.c());
   }

   public void a(EntityLiving var0, Entity var1, int var2) {
   }

   public void b(EntityLiving var0, Entity var1, int var2) {
   }

   public boolean b() {
      return false;
   }

   public boolean c() {
      return false;
   }

   public boolean h() {
      return true;
   }

   public boolean i() {
      return true;
   }

   public static enum Rarity {
      a(10),
      b(5),
      c(2),
      d(1);

      private final int e;

      private Rarity(int var2) {
         this.e = var2;
      }

      public int a() {
         return this.e;
      }
   }
}
