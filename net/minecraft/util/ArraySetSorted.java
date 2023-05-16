package net.minecraft.util;

import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

public class ArraySetSorted<T> extends AbstractSet<T> {
   private static final int a = 10;
   private final Comparator<T> b;
   T[] c;
   int d;

   private ArraySetSorted(int var0, Comparator<T> var1) {
      this.b = var1;
      if (var0 < 0) {
         throw new IllegalArgumentException("Initial capacity (" + var0 + ") is negative");
      } else {
         this.c = (T[])a(new Object[var0]);
      }
   }

   public static <T extends Comparable<T>> ArraySetSorted<T> a() {
      return a(10);
   }

   public static <T extends Comparable<T>> ArraySetSorted<T> a(int var0) {
      return new ArraySetSorted<>(var0, Comparator.naturalOrder());
   }

   public static <T> ArraySetSorted<T> a(Comparator<T> var0) {
      return a(var0, 10);
   }

   public static <T> ArraySetSorted<T> a(Comparator<T> var0, int var1) {
      return new ArraySetSorted<>(var1, var0);
   }

   private static <T> T[] a(Object[] var0) {
      return (T[])var0;
   }

   private int c(T var0) {
      return Arrays.binarySearch(this.c, 0, this.d, var0, this.b);
   }

   private static int b(int var0) {
      return -var0 - 1;
   }

   @Override
   public boolean add(T var0) {
      int var1 = this.c(var0);
      if (var1 >= 0) {
         return false;
      } else {
         int var2 = b(var1);
         this.a(var0, var2);
         return true;
      }
   }

   private void c(int var0) {
      if (var0 > this.c.length) {
         if (this.c != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
            var0 = (int)Math.max(Math.min((long)this.c.length + (long)(this.c.length >> 1), 2147483639L), (long)var0);
         } else if (var0 < 10) {
            var0 = 10;
         }

         Object[] var1 = new Object[var0];
         System.arraycopy(this.c, 0, var1, 0, this.d);
         this.c = (T[])a(var1);
      }
   }

   private void a(T var0, int var1) {
      this.c(this.d + 1);
      if (var1 != this.d) {
         System.arraycopy(this.c, var1, this.c, var1 + 1, this.d - var1);
      }

      this.c[var1] = var0;
      ++this.d;
   }

   void d(int var0) {
      --this.d;
      if (var0 != this.d) {
         System.arraycopy(this.c, var0 + 1, this.c, var0, this.d - var0);
      }

      this.c[this.d] = null;
   }

   private T e(int var0) {
      return this.c[var0];
   }

   public T a(T var0) {
      int var1 = this.c(var0);
      if (var1 >= 0) {
         return this.e(var1);
      } else {
         this.a(var0, b(var1));
         return var0;
      }
   }

   @Override
   public boolean remove(Object var0) {
      int var1 = this.c((T)var0);
      if (var1 >= 0) {
         this.d(var1);
         return true;
      } else {
         return false;
      }
   }

   @Nullable
   public T b(T var0) {
      int var1 = this.c(var0);
      return var1 >= 0 ? this.e(var1) : null;
   }

   public T b() {
      return this.e(0);
   }

   public T c() {
      return this.e(this.d - 1);
   }

   @Override
   public boolean contains(Object var0) {
      int var1 = this.c((T)var0);
      return var1 >= 0;
   }

   @Override
   public Iterator<T> iterator() {
      return new ArraySetSorted.a();
   }

   @Override
   public int size() {
      return this.d;
   }

   @Override
   public Object[] toArray() {
      return Arrays.copyOf(this.c, this.d, Object[].class);
   }

   @Override
   public <U> U[] toArray(U[] var0) {
      if (var0.length < this.d) {
         return (U[])Arrays.copyOf(this.c, this.d, var0.getClass());
      } else {
         System.arraycopy(this.c, 0, var0, 0, this.d);
         if (var0.length > this.d) {
            var0[this.d] = null;
         }

         return var0;
      }
   }

   @Override
   public void clear() {
      Arrays.fill(this.c, 0, this.d, null);
      this.d = 0;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         if (var0 instanceof ArraySetSorted var1 && this.b.equals(var1.b)) {
            return this.d == var1.d && Arrays.equals(this.c, var1.c);
         }

         return super.equals(var0);
      }
   }

   class a implements Iterator<T> {
      private int b;
      private int c = -1;

      @Override
      public boolean hasNext() {
         return this.b < ArraySetSorted.this.d;
      }

      @Override
      public T next() {
         if (this.b >= ArraySetSorted.this.d) {
            throw new NoSuchElementException();
         } else {
            this.c = this.b++;
            return ArraySetSorted.this.c[this.c];
         }
      }

      @Override
      public void remove() {
         if (this.c == -1) {
            throw new IllegalStateException();
         } else {
            ArraySetSorted.this.d(this.c);
            --this.b;
            this.c = -1;
         }
      }
   }
}
