package net.minecraft;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class Optionull {
   @Nullable
   public static <T, R> R a(@Nullable T var0, Function<T, R> var1) {
      return var0 == null ? null : var1.apply(var0);
   }

   public static <T, R> R a(@Nullable T var0, Function<T, R> var1, R var2) {
      return (R)(var0 == null ? var2 : var1.apply(var0));
   }

   public static <T, R> R a(@Nullable T var0, Function<T, R> var1, Supplier<R> var2) {
      return (R)(var0 == null ? var2.get() : var1.apply(var0));
   }

   @Nullable
   public static <T> T a(Collection<T> var0) {
      Iterator<T> var1 = var0.iterator();
      return var1.hasNext() ? var1.next() : null;
   }

   public static <T> T a(Collection<T> var0, T var1) {
      Iterator<T> var2 = var0.iterator();
      return (T)(var2.hasNext() ? var2.next() : var1);
   }

   public static <T> T a(Collection<T> var0, Supplier<T> var1) {
      Iterator<T> var2 = var0.iterator();
      return (T)(var2.hasNext() ? var2.next() : var1.get());
   }

   public static <T> boolean a(@Nullable T[] var0) {
      return var0 == null || var0.length == 0;
   }

   public static boolean a(@Nullable boolean[] var0) {
      return var0 == null || var0.length == 0;
   }

   public static boolean a(@Nullable byte[] var0) {
      return var0 == null || var0.length == 0;
   }

   public static boolean a(@Nullable char[] var0) {
      return var0 == null || var0.length == 0;
   }

   public static boolean a(@Nullable short[] var0) {
      return var0 == null || var0.length == 0;
   }

   public static boolean a(@Nullable int[] var0) {
      return var0 == null || var0.length == 0;
   }

   public static boolean a(@Nullable long[] var0) {
      return var0 == null || var0.length == 0;
   }

   public static boolean a(@Nullable float[] var0) {
      return var0 == null || var0.length == 0;
   }

   public static boolean a(@Nullable double[] var0) {
      return var0 == null || var0.length == 0;
   }
}
