package net.minecraft.core;

import com.google.common.collect.Lists;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;

public class NonNullList<E> extends AbstractList<E> {
   private final List<E> a;
   @Nullable
   private final E b;

   public static <E> NonNullList<E> a() {
      return new NonNullList<>(Lists.newArrayList(), (E)null);
   }

   public static <E> NonNullList<E> a(int var0) {
      return new NonNullList<>(Lists.newArrayListWithCapacity(var0), (E)null);
   }

   public static <E> NonNullList<E> a(int var0, E var1) {
      Validate.notNull(var1);
      Object[] var2 = new Object[var0];
      Arrays.fill(var2, var1);
      return new NonNullList<>(Arrays.asList((E[])var2), var1);
   }

   @SafeVarargs
   public static <E> NonNullList<E> a(E var0, E... var1) {
      return new NonNullList<>(Arrays.asList(var1), var0);
   }

   protected NonNullList(List<E> var0, @Nullable E var1) {
      this.a = var0;
      this.b = var1;
   }

   @Nonnull
   @Override
   public E get(int var0) {
      return this.a.get(var0);
   }

   @Override
   public E set(int var0, E var1) {
      Validate.notNull(var1);
      return this.a.set(var0, var1);
   }

   @Override
   public void add(int var0, E var1) {
      Validate.notNull(var1);
      this.a.add(var0, var1);
   }

   @Override
   public E remove(int var0) {
      return this.a.remove(var0);
   }

   @Override
   public int size() {
      return this.a.size();
   }

   @Override
   public void clear() {
      if (this.b == null) {
         super.clear();
      } else {
         for(int var0 = 0; var0 < this.size(); ++var0) {
            this.set(var0, this.b);
         }
      }
   }
}
