package net.minecraft.advancements;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class AdvancementTree {
   private final Advancement a;
   @Nullable
   private final AdvancementTree b;
   @Nullable
   private final AdvancementTree c;
   private final int d;
   private final List<AdvancementTree> e = Lists.newArrayList();
   private AdvancementTree f;
   @Nullable
   private AdvancementTree g;
   private int h;
   private float i;
   private float j;
   private float k;
   private float l;

   public AdvancementTree(Advancement var0, @Nullable AdvancementTree var1, @Nullable AdvancementTree var2, int var3, int var4) {
      if (var0.d() == null) {
         throw new IllegalArgumentException("Can't position an invisible advancement!");
      } else {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.f = this;
         this.h = var4;
         this.i = -1.0F;
         AdvancementTree var5 = null;

         for(Advancement var7 : var0.f()) {
            var5 = this.a(var7, var5);
         }
      }
   }

   @Nullable
   private AdvancementTree a(Advancement var0, @Nullable AdvancementTree var1) {
      if (var0.d() != null) {
         var1 = new AdvancementTree(var0, this, var1, this.e.size() + 1, this.h + 1);
         this.e.add(var1);
      } else {
         for(Advancement var3 : var0.f()) {
            var1 = this.a(var3, var1);
         }
      }

      return var1;
   }

   private void a() {
      if (this.e.isEmpty()) {
         if (this.c != null) {
            this.i = this.c.i + 1.0F;
         } else {
            this.i = 0.0F;
         }
      } else {
         AdvancementTree var0 = null;

         for(AdvancementTree var2 : this.e) {
            var2.a();
            var0 = var2.a(var0 == null ? var2 : var0);
         }

         this.b();
         float var1 = (this.e.get(0).i + this.e.get(this.e.size() - 1).i) / 2.0F;
         if (this.c != null) {
            this.i = this.c.i + 1.0F;
            this.j = this.i - var1;
         } else {
            this.i = var1;
         }
      }
   }

   private float a(float var0, int var1, float var2) {
      this.i += var0;
      this.h = var1;
      if (this.i < var2) {
         var2 = this.i;
      }

      for(AdvancementTree var4 : this.e) {
         var2 = var4.a(var0 + this.j, var1 + 1, var2);
      }

      return var2;
   }

   private void a(float var0) {
      this.i += var0;

      for(AdvancementTree var2 : this.e) {
         var2.a(var0);
      }
   }

   private void b() {
      float var0 = 0.0F;
      float var1 = 0.0F;

      for(int var2 = this.e.size() - 1; var2 >= 0; --var2) {
         AdvancementTree var3 = this.e.get(var2);
         var3.i += var0;
         var3.j += var0;
         var1 += var3.k;
         var0 += var3.l + var1;
      }
   }

   @Nullable
   private AdvancementTree c() {
      if (this.g != null) {
         return this.g;
      } else {
         return !this.e.isEmpty() ? this.e.get(0) : null;
      }
   }

   @Nullable
   private AdvancementTree d() {
      if (this.g != null) {
         return this.g;
      } else {
         return !this.e.isEmpty() ? this.e.get(this.e.size() - 1) : null;
      }
   }

   private AdvancementTree a(AdvancementTree var0) {
      if (this.c == null) {
         return var0;
      } else {
         AdvancementTree var1 = this;
         AdvancementTree var2 = this;
         AdvancementTree var3 = this.c;
         AdvancementTree var4 = this.b.e.get(0);
         float var5 = this.j;
         float var6 = this.j;
         float var7 = var3.j;

         float var8;
         for(var8 = var4.j; var3.d() != null && var1.c() != null; var6 += var2.j) {
            var3 = var3.d();
            var1 = var1.c();
            var4 = var4.c();
            var2 = var2.d();
            var2.f = this;
            float var9 = var3.i + var7 - (var1.i + var5) + 1.0F;
            if (var9 > 0.0F) {
               var3.a(this, var0).a(this, var9);
               var5 += var9;
               var6 += var9;
            }

            var7 += var3.j;
            var5 += var1.j;
            var8 += var4.j;
         }

         if (var3.d() != null && var2.d() == null) {
            var2.g = var3.d();
            var2.j += var7 - var6;
         } else {
            if (var1.c() != null && var4.c() == null) {
               var4.g = var1.c();
               var4.j += var5 - var8;
            }

            var0 = this;
         }

         return var0;
      }
   }

   private void a(AdvancementTree var0, float var1) {
      float var2 = (float)(var0.d - this.d);
      if (var2 != 0.0F) {
         var0.k -= var1 / var2;
         this.k += var1 / var2;
      }

      var0.l += var1;
      var0.i += var1;
      var0.j += var1;
   }

   private AdvancementTree a(AdvancementTree var0, AdvancementTree var1) {
      return this.f != null && var0.b.e.contains(this.f) ? this.f : var1;
   }

   private void e() {
      if (this.a.d() != null) {
         this.a.d().a((float)this.h, this.i);
      }

      if (!this.e.isEmpty()) {
         for(AdvancementTree var1 : this.e) {
            var1.e();
         }
      }
   }

   public static void a(Advancement var0) {
      if (var0.d() == null) {
         throw new IllegalArgumentException("Can't position children of an invisible root!");
      } else {
         AdvancementTree var1 = new AdvancementTree(var0, null, null, 1, 0);
         var1.a();
         float var2 = var1.a(0.0F, 0, var1.i);
         if (var2 < 0.0F) {
            var1.a(-var2);
         }

         var1.e();
      }
   }
}
