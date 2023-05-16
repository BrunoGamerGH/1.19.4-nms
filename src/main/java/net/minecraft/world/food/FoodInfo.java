package net.minecraft.world.food;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.minecraft.world.effect.MobEffect;

public class FoodInfo {
   private final int a;
   private final float b;
   private final boolean c;
   private final boolean d;
   private final boolean e;
   private final List<Pair<MobEffect, Float>> f;

   FoodInfo(int var0, float var1, boolean var2, boolean var3, boolean var4, List<Pair<MobEffect, Float>> var5) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
   }

   public int a() {
      return this.a;
   }

   public float b() {
      return this.b;
   }

   public boolean c() {
      return this.c;
   }

   public boolean d() {
      return this.d;
   }

   public boolean e() {
      return this.e;
   }

   public List<Pair<MobEffect, Float>> f() {
      return this.f;
   }

   public static class a {
      private int a;
      private float b;
      private boolean c;
      private boolean d;
      private boolean e;
      private final List<Pair<MobEffect, Float>> f = Lists.newArrayList();

      public FoodInfo.a a(int var0) {
         this.a = var0;
         return this;
      }

      public FoodInfo.a a(float var0) {
         this.b = var0;
         return this;
      }

      public FoodInfo.a a() {
         this.c = true;
         return this;
      }

      public FoodInfo.a b() {
         this.d = true;
         return this;
      }

      public FoodInfo.a c() {
         this.e = true;
         return this;
      }

      public FoodInfo.a a(MobEffect var0, float var1) {
         this.f.add(Pair.of(var0, var1));
         return this;
      }

      public FoodInfo d() {
         return new FoodInfo(this.a, this.b, this.c, this.d, this.e, this.f);
      }
   }
}
