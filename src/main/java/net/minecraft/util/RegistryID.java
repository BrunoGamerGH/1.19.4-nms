package net.minecraft.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;

public class RegistryID<K> implements Registry<K> {
   private static final int b = -1;
   private static final Object c = null;
   private static final float d = 0.8F;
   private K[] e;
   private int[] f;
   private K[] g;
   private int h;
   private int i;

   private RegistryID(int var0) {
      this.e = (K[])(new Object[var0]);
      this.f = new int[var0];
      this.g = (K[])(new Object[var0]);
   }

   private RegistryID(K[] var0, int[] var1, K[] var2, int var3, int var4) {
      this.e = var0;
      this.f = var1;
      this.g = var2;
      this.h = var3;
      this.i = var4;
   }

   public static <A> RegistryID<A> c(int var0) {
      return new RegistryID<>((int)((float)var0 / 0.8F));
   }

   @Override
   public int a(@Nullable K var0) {
      return this.e(this.b(var0, this.d(var0)));
   }

   @Nullable
   @Override
   public K a(int var0) {
      return var0 >= 0 && var0 < this.g.length ? this.g[var0] : null;
   }

   private int e(int var0) {
      return var0 == -1 ? -1 : this.f[var0];
   }

   public boolean b(K var0) {
      return this.a(var0) != -1;
   }

   public boolean d(int var0) {
      return this.a(var0) != null;
   }

   public int c(K var0) {
      int var1 = this.d();
      this.a(var0, var1);
      return var1;
   }

   private int d() {
      while(this.h < this.g.length && this.g[this.h] != null) {
         ++this.h;
      }

      return this.h;
   }

   private void f(int var0) {
      K[] var1 = this.e;
      int[] var2 = this.f;
      RegistryID<K> var3 = new RegistryID<>(var0);

      for(int var4 = 0; var4 < var1.length; ++var4) {
         if (var1[var4] != null) {
            var3.a(var1[var4], var2[var4]);
         }
      }

      this.e = var3.e;
      this.f = var3.f;
      this.g = var3.g;
      this.h = var3.h;
      this.i = var3.i;
   }

   public void a(K var0, int var1) {
      int var2 = Math.max(var1, this.i + 1);
      if ((float)var2 >= (float)this.e.length * 0.8F) {
         int var3 = this.e.length << 1;

         while(var3 < var1) {
            var3 <<= 1;
         }

         this.f(var3);
      }

      int var3 = this.g(this.d(var0));
      this.e[var3] = var0;
      this.f[var3] = var1;
      this.g[var1] = var0;
      ++this.i;
      if (var1 == this.h) {
         ++this.h;
      }
   }

   private int d(@Nullable K var0) {
      return (MathHelper.g(System.identityHashCode(var0)) & 2147483647) % this.e.length;
   }

   private int b(@Nullable K var0, int var1) {
      for(int var2 = var1; var2 < this.e.length; ++var2) {
         if (this.e[var2] == var0) {
            return var2;
         }

         if (this.e[var2] == c) {
            return -1;
         }
      }

      for(int var2 = 0; var2 < var1; ++var2) {
         if (this.e[var2] == var0) {
            return var2;
         }

         if (this.e[var2] == c) {
            return -1;
         }
      }

      return -1;
   }

   private int g(int var0) {
      for(int var1 = var0; var1 < this.e.length; ++var1) {
         if (this.e[var1] == c) {
            return var1;
         }
      }

      for(int var1 = 0; var1 < var0; ++var1) {
         if (this.e[var1] == c) {
            return var1;
         }
      }

      throw new RuntimeException("Overflowed :(");
   }

   @Override
   public Iterator<K> iterator() {
      return Iterators.filter(Iterators.forArray(this.g), Predicates.notNull());
   }

   public void a() {
      Arrays.fill(this.e, null);
      Arrays.fill(this.g, null);
      this.h = 0;
      this.i = 0;
   }

   @Override
   public int b() {
      return this.i;
   }

   public RegistryID<K> c() {
      return new RegistryID<>((K[])((Object[])this.e.clone()), (int[])this.f.clone(), (K[])((Object[])this.g.clone()), this.h, this.i);
   }
}
