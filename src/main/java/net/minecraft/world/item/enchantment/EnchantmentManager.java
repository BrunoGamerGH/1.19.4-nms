package net.minecraft.world.item.enchantment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom2;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumItemSlot;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemEnchantedBook;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

public class EnchantmentManager {
   private static final String a = "id";
   private static final String b = "lvl";
   private static final float c = 0.15F;

   public static NBTTagCompound a(@Nullable MinecraftKey var0, int var1) {
      NBTTagCompound var2 = new NBTTagCompound();
      var2.a("id", String.valueOf(var0));
      var2.a("lvl", (short)var1);
      return var2;
   }

   public static void a(NBTTagCompound var0, int var1) {
      var0.a("lvl", (short)var1);
   }

   public static int a(NBTTagCompound var0) {
      return MathHelper.a(var0.h("lvl"), 0, 255);
   }

   @Nullable
   public static MinecraftKey b(NBTTagCompound var0) {
      return MinecraftKey.a(var0.l("id"));
   }

   @Nullable
   public static MinecraftKey a(Enchantment var0) {
      return BuiltInRegistries.g.b(var0);
   }

   public static int a(Enchantment var0, ItemStack var1) {
      if (var1.b()) {
         return 0;
      } else {
         MinecraftKey var2 = a(var0);
         NBTTagList var3 = var1.w();

         for(int var4 = 0; var4 < var3.size(); ++var4) {
            NBTTagCompound var5 = var3.a(var4);
            MinecraftKey var6 = b(var5);
            if (var6 != null && var6.equals(var2)) {
               return a(var5);
            }
         }

         return 0;
      }
   }

   public static Map<Enchantment, Integer> a(ItemStack var0) {
      NBTTagList var1 = var0.a(Items.ty) ? ItemEnchantedBook.d(var0) : var0.w();
      return a(var1);
   }

   public static Map<Enchantment, Integer> a(NBTTagList var0) {
      Map<Enchantment, Integer> var1 = Maps.newLinkedHashMap();

      for(int var2 = 0; var2 < var0.size(); ++var2) {
         NBTTagCompound var3 = var0.a(var2);
         BuiltInRegistries.g.b(b(var3)).ifPresent(var2x -> var1.put(var2x, a(var3)));
      }

      return var1;
   }

   public static void a(Map<Enchantment, Integer> var0, ItemStack var1) {
      NBTTagList var2 = new NBTTagList();

      for(Entry<Enchantment, Integer> var4 : var0.entrySet()) {
         Enchantment var5 = var4.getKey();
         if (var5 != null) {
            int var6 = var4.getValue();
            var2.add(a(a(var5), var6));
            if (var1.a(Items.ty)) {
               ItemEnchantedBook.a(var1, new WeightedRandomEnchant(var5, var6));
            }
         }
      }

      if (var2.isEmpty()) {
         var1.c("Enchantments");
      } else if (!var1.a(Items.ty)) {
         var1.a("Enchantments", var2);
      }
   }

   private static void a(EnchantmentManager.a var0, ItemStack var1) {
      if (!var1.b()) {
         NBTTagList var2 = var1.w();

         for(int var3 = 0; var3 < var2.size(); ++var3) {
            NBTTagCompound var4 = var2.a(var3);
            BuiltInRegistries.g.b(b(var4)).ifPresent(var2x -> var0.accept(var2x, a(var4)));
         }
      }
   }

   private static void a(EnchantmentManager.a var0, Iterable<ItemStack> var1) {
      for(ItemStack var3 : var1) {
         a(var0, var3);
      }
   }

   public static int a(Iterable<ItemStack> var0, DamageSource var1) {
      MutableInt var2 = new MutableInt();
      a((var2x, var3) -> var2.add(var2x.a(var3, var1)), var0);
      return var2.intValue();
   }

   public static float a(ItemStack var0, EnumMonsterType var1) {
      MutableFloat var2 = new MutableFloat();
      a((var2x, var3) -> var2.add(var2x.a(var3, var1)), var0);
      return var2.floatValue();
   }

   public static float a(EntityLiving var0) {
      int var1 = a(Enchantments.t, var0);
      return var1 > 0 ? EnchantmentSweeping.e(var1) : 0.0F;
   }

   public static void a(EntityLiving var0, Entity var1) {
      EnchantmentManager.a var2 = (var2x, var3) -> var2x.b(var0, var1, var3);
      if (var0 != null) {
         a(var2, var0.bJ());
      }

      if (var1 instanceof EntityHuman) {
         a(var2, var0.eK());
      }
   }

   public static void b(EntityLiving var0, Entity var1) {
      EnchantmentManager.a var2 = (var2x, var3) -> var2x.a(var0, var1, var3);
      if (var0 != null) {
         a(var2, var0.bJ());
      }

      if (var0 instanceof EntityHuman) {
         a(var2, var0.eK());
      }
   }

   public static int a(Enchantment var0, EntityLiving var1) {
      Iterable<ItemStack> var2 = var0.a(var1).values();
      if (var2 == null) {
         return 0;
      } else {
         int var3 = 0;

         for(ItemStack var5 : var2) {
            int var6 = a(var0, var5);
            if (var6 > var3) {
               var3 = var6;
            }
         }

         return var3;
      }
   }

   public static float b(EntityLiving var0) {
      return (float)a(Enchantments.m, var0) * 0.15F;
   }

   public static int c(EntityLiving var0) {
      return a(Enchantments.q, var0);
   }

   public static int d(EntityLiving var0) {
      return a(Enchantments.r, var0);
   }

   public static int e(EntityLiving var0) {
      return a(Enchantments.f, var0);
   }

   public static int f(EntityLiving var0) {
      return a(Enchantments.i, var0);
   }

   public static int g(EntityLiving var0) {
      return a(Enchantments.u, var0);
   }

   public static int b(ItemStack var0) {
      return a(Enchantments.C, var0);
   }

   public static int c(ItemStack var0) {
      return a(Enchantments.D, var0);
   }

   public static int h(EntityLiving var0) {
      return a(Enchantments.s, var0);
   }

   public static boolean i(EntityLiving var0) {
      return a(Enchantments.g, var0) > 0;
   }

   public static boolean j(EntityLiving var0) {
      return a(Enchantments.j, var0) > 0;
   }

   public static boolean k(EntityLiving var0) {
      return a(Enchantments.l, var0) > 0;
   }

   public static boolean d(ItemStack var0) {
      return a(Enchantments.k, var0) > 0;
   }

   public static boolean e(ItemStack var0) {
      return a(Enchantments.M, var0) > 0;
   }

   public static boolean f(ItemStack var0) {
      return a(Enchantments.v, var0) > 0;
   }

   public static int g(ItemStack var0) {
      return a(Enchantments.E, var0);
   }

   public static int h(ItemStack var0) {
      return a(Enchantments.G, var0);
   }

   public static boolean i(ItemStack var0) {
      return a(Enchantments.H, var0) > 0;
   }

   @Nullable
   public static Entry<EnumItemSlot, ItemStack> b(Enchantment var0, EntityLiving var1) {
      return a(var0, var1, var0x -> true);
   }

   @Nullable
   public static Entry<EnumItemSlot, ItemStack> a(Enchantment var0, EntityLiving var1, Predicate<ItemStack> var2) {
      Map<EnumItemSlot, ItemStack> var3 = var0.a(var1);
      if (var3.isEmpty()) {
         return null;
      } else {
         List<Entry<EnumItemSlot, ItemStack>> var4 = Lists.newArrayList();

         for(Entry<EnumItemSlot, ItemStack> var6 : var3.entrySet()) {
            ItemStack var7 = var6.getValue();
            if (!var7.b() && a(var0, var7) > 0 && var2.test(var7)) {
               var4.add(var6);
            }
         }

         return var4.isEmpty() ? null : var4.get(var1.dZ().a(var4.size()));
      }
   }

   public static int a(RandomSource var0, int var1, int var2, ItemStack var3) {
      Item var4 = var3.c();
      int var5 = var4.c();
      if (var5 <= 0) {
         return 0;
      } else {
         if (var2 > 15) {
            var2 = 15;
         }

         int var6 = var0.a(8) + 1 + (var2 >> 1) + var0.a(var2 + 1);
         if (var1 == 0) {
            return Math.max(var6 / 3, 1);
         } else {
            return var1 == 1 ? var6 * 2 / 3 + 1 : Math.max(var6, var2 * 2);
         }
      }
   }

   public static ItemStack a(RandomSource var0, ItemStack var1, int var2, boolean var3) {
      List<WeightedRandomEnchant> var4 = b(var0, var1, var2, var3);
      boolean var5 = var1.a(Items.pX);
      if (var5) {
         var1 = new ItemStack(Items.ty);
      }

      for(WeightedRandomEnchant var7 : var4) {
         if (var5) {
            ItemEnchantedBook.a(var1, var7);
         } else {
            var1.a(var7.a, var7.b);
         }
      }

      return var1;
   }

   public static List<WeightedRandomEnchant> b(RandomSource var0, ItemStack var1, int var2, boolean var3) {
      List<WeightedRandomEnchant> var4 = Lists.newArrayList();
      Item var5 = var1.c();
      int var6 = var5.c();
      if (var6 <= 0) {
         return var4;
      } else {
         var2 += 1 + var0.a(var6 / 4 + 1) + var0.a(var6 / 4 + 1);
         float var7 = (var0.i() + var0.i() - 1.0F) * 0.15F;
         var2 = MathHelper.a(Math.round((float)var2 + (float)var2 * var7), 1, Integer.MAX_VALUE);
         List<WeightedRandomEnchant> var8 = a(var2, var1, var3);
         if (!var8.isEmpty()) {
            WeightedRandom2.a(var0, var8).ifPresent(var4::add);

            while(var0.a(50) <= var2) {
               if (!var4.isEmpty()) {
                  a(var8, SystemUtils.a(var4));
               }

               if (var8.isEmpty()) {
                  break;
               }

               WeightedRandom2.a(var0, var8).ifPresent(var4::add);
               var2 /= 2;
            }
         }

         return var4;
      }
   }

   public static void a(List<WeightedRandomEnchant> var0, WeightedRandomEnchant var1) {
      Iterator<WeightedRandomEnchant> var2 = var0.iterator();

      while(var2.hasNext()) {
         if (!var1.a.b(var2.next().a)) {
            var2.remove();
         }
      }
   }

   public static boolean a(Collection<Enchantment> var0, Enchantment var1) {
      for(Enchantment var3 : var0) {
         if (!var3.b(var1)) {
            return false;
         }
      }

      return true;
   }

   public static List<WeightedRandomEnchant> a(int var0, ItemStack var1, boolean var2) {
      List<WeightedRandomEnchant> var3 = Lists.newArrayList();
      Item var4 = var1.c();
      boolean var5 = var1.a(Items.pX);

      for(Enchantment var7 : BuiltInRegistries.g) {
         if ((!var7.b() || var2) && var7.i() && (var7.e.a(var4) || var5)) {
            for(int var8 = var7.a(); var8 > var7.e() - 1; --var8) {
               if (var0 >= var7.a(var8) && var0 <= var7.b(var8)) {
                  var3.add(new WeightedRandomEnchant(var7, var8));
                  break;
               }
            }
         }
      }

      return var3;
   }

   @FunctionalInterface
   interface a {
      void accept(Enchantment var1, int var2);
   }
}
