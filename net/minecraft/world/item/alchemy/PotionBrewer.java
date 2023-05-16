package net.minecraft.world.item.alchemy;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeItemStack;

public class PotionBrewer {
   public static final int a = 20;
   private static final List<PotionBrewer.PredicatedCombination<PotionRegistry>> b = Lists.newArrayList();
   private static final List<PotionBrewer.PredicatedCombination<Item>> c = Lists.newArrayList();
   private static final List<RecipeItemStack> d = Lists.newArrayList();
   private static final Predicate<ItemStack> e = var0 -> {
      for(RecipeItemStack var2 : d) {
         if (var2.a(var0)) {
            return true;
         }
      }

      return false;
   };

   public static boolean a(ItemStack var0) {
      return b(var0) || c(var0);
   }

   protected static boolean b(ItemStack var0) {
      int var1 = 0;

      for(int var2 = c.size(); var1 < var2; ++var1) {
         if (c.get(var1).b.a(var0)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean c(ItemStack var0) {
      int var1 = 0;

      for(int var2 = b.size(); var1 < var2; ++var1) {
         if (b.get(var1).b.a(var0)) {
            return true;
         }
      }

      return false;
   }

   public static boolean a(PotionRegistry var0) {
      int var1 = 0;

      for(int var2 = b.size(); var1 < var2; ++var1) {
         if (b.get(var1).c == var0) {
            return true;
         }
      }

      return false;
   }

   public static boolean a(ItemStack var0, ItemStack var1) {
      if (!e.test(var0)) {
         return false;
      } else {
         return b(var0, var1) || c(var0, var1);
      }
   }

   protected static boolean b(ItemStack var0, ItemStack var1) {
      Item var2 = var0.c();
      int var3 = 0;

      for(int var4 = c.size(); var3 < var4; ++var3) {
         PotionBrewer.PredicatedCombination<Item> var5 = c.get(var3);
         if (var5.a == var2 && var5.b.a(var1)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean c(ItemStack var0, ItemStack var1) {
      PotionRegistry var2 = PotionUtil.d(var0);
      int var3 = 0;

      for(int var4 = b.size(); var3 < var4; ++var3) {
         PotionBrewer.PredicatedCombination<PotionRegistry> var5 = b.get(var3);
         if (var5.a == var2 && var5.b.a(var1)) {
            return true;
         }
      }

      return false;
   }

   public static ItemStack d(ItemStack var0, ItemStack var1) {
      if (!var1.b()) {
         PotionRegistry var2 = PotionUtil.d(var1);
         Item var3 = var1.c();
         int var4 = 0;

         for(int var5 = c.size(); var4 < var5; ++var4) {
            PotionBrewer.PredicatedCombination<Item> var6 = c.get(var4);
            if (var6.a == var3 && var6.b.a(var0)) {
               return PotionUtil.a(new ItemStack(var6.c), var2);
            }
         }

         var4 = 0;

         for(int var5 = b.size(); var4 < var5; ++var4) {
            PotionBrewer.PredicatedCombination<PotionRegistry> var6 = b.get(var4);
            if (var6.a == var2 && var6.b.a(var0)) {
               return PotionUtil.a(new ItemStack(var3), var6.c);
            }
         }
      }

      return var1;
   }

   public static void a() {
      a(Items.rr);
      a(Items.up);
      a(Items.us);
      a(Items.rr, Items.oC, Items.up);
      a(Items.up, Items.uo, Items.us);
      a(Potions.c, Items.rA, Potions.d);
      a(Potions.c, Items.ro, Potions.d);
      a(Potions.c, Items.tF, Potions.d);
      a(Potions.c, Items.rv, Potions.d);
      a(Potions.c, Items.rt, Potions.d);
      a(Potions.c, Items.qI, Potions.d);
      a(Potions.c, Items.rw, Potions.d);
      a(Potions.c, Items.qg, Potions.e);
      a(Potions.c, Items.li, Potions.d);
      a(Potions.c, Items.rq, Potions.f);
      a(Potions.f, Items.tm, Potions.g);
      a(Potions.g, Items.li, Potions.h);
      a(Potions.g, Items.ru, Potions.i);
      a(Potions.h, Items.ru, Potions.j);
      a(Potions.i, Items.li, Potions.j);
      a(Potions.f, Items.rw, Potions.n);
      a(Potions.n, Items.li, Potions.o);
      a(Potions.f, Items.tF, Potions.k);
      a(Potions.k, Items.li, Potions.l);
      a(Potions.k, Items.qg, Potions.m);
      a(Potions.k, Items.ru, Potions.s);
      a(Potions.l, Items.ru, Potions.t);
      a(Potions.s, Items.li, Potions.t);
      a(Potions.s, Items.qg, Potions.u);
      a(Potions.f, Items.ny, Potions.v);
      a(Potions.v, Items.li, Potions.w);
      a(Potions.v, Items.qg, Potions.x);
      a(Potions.p, Items.ru, Potions.s);
      a(Potions.q, Items.ru, Potions.t);
      a(Potions.f, Items.qI, Potions.p);
      a(Potions.p, Items.li, Potions.q);
      a(Potions.p, Items.qg, Potions.r);
      a(Potions.f, Items.qk, Potions.y);
      a(Potions.y, Items.li, Potions.z);
      a(Potions.f, Items.rA, Potions.A);
      a(Potions.A, Items.qg, Potions.B);
      a(Potions.A, Items.ru, Potions.C);
      a(Potions.B, Items.ru, Potions.D);
      a(Potions.C, Items.qg, Potions.D);
      a(Potions.E, Items.ru, Potions.C);
      a(Potions.F, Items.ru, Potions.C);
      a(Potions.G, Items.ru, Potions.D);
      a(Potions.f, Items.rt, Potions.E);
      a(Potions.E, Items.li, Potions.F);
      a(Potions.E, Items.qg, Potions.G);
      a(Potions.f, Items.ro, Potions.H);
      a(Potions.H, Items.li, Potions.I);
      a(Potions.H, Items.qg, Potions.J);
      a(Potions.f, Items.rv, Potions.K);
      a(Potions.K, Items.li, Potions.L);
      a(Potions.K, Items.qg, Potions.M);
      a(Potions.c, Items.ru, Potions.N);
      a(Potions.N, Items.li, Potions.O);
      a(Potions.f, Items.uQ, Potions.Q);
      a(Potions.Q, Items.li, Potions.R);
   }

   private static void a(Item var0, Item var1, Item var2) {
      if (!(var0 instanceof ItemPotion)) {
         throw new IllegalArgumentException("Expected a potion, got: " + BuiltInRegistries.i.b(var0));
      } else if (!(var2 instanceof ItemPotion)) {
         throw new IllegalArgumentException("Expected a potion, got: " + BuiltInRegistries.i.b(var2));
      } else {
         c.add(new PotionBrewer.PredicatedCombination<>(var0, RecipeItemStack.a(var1), var2));
      }
   }

   private static void a(Item var0) {
      if (!(var0 instanceof ItemPotion)) {
         throw new IllegalArgumentException("Expected a potion, got: " + BuiltInRegistries.i.b(var0));
      } else {
         d.add(RecipeItemStack.a(var0));
      }
   }

   private static void a(PotionRegistry var0, Item var1, PotionRegistry var2) {
      b.add(new PotionBrewer.PredicatedCombination<>(var0, RecipeItemStack.a(var1), var2));
   }

   static class PredicatedCombination<T> {
      final T a;
      final RecipeItemStack b;
      final T c;

      public PredicatedCombination(T var0, RecipeItemStack var1, T var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }
   }
}
