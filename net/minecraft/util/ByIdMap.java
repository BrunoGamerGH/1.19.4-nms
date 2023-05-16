package net.minecraft.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

public class ByIdMap {
   private static <T> IntFunction<T> a(ToIntFunction<T> var0, T[] var1) {
      if (var1.length == 0) {
         throw new IllegalArgumentException("Empty value list");
      } else {
         Int2ObjectMap<T> var2 = new Int2ObjectOpenHashMap();

         for(T var6 : var1) {
            int var7 = var0.applyAsInt(var6);
            T var8 = (T)var2.put(var7, var6);
            if (var8 != null) {
               throw new IllegalArgumentException("Duplicate entry on id " + var7 + ": current=" + var6 + ", previous=" + var8);
            }
         }

         return var2;
      }
   }

   public static <T> IntFunction<T> a(ToIntFunction<T> var0, T[] var1, T var2) {
      IntFunction<T> var3 = a(var0, var1);
      return var2x -> Objects.requireNonNullElse(var3.apply(var2x), var2);
   }

   private static <T> T[] b(ToIntFunction<T> var0, T[] var1) {
      int var2 = var1.length;
      if (var2 == 0) {
         throw new IllegalArgumentException("Empty value list");
      } else {
         T[] var3 = (T[])var1.clone();
         Arrays.fill(var3, null);

         for(T var7 : var1) {
            int var8 = var0.applyAsInt(var7);
            if (var8 < 0 || var8 >= var2) {
               throw new IllegalArgumentException("Values are not continous, found index " + var8 + " for value " + var7);
            }

            T var9 = var3[var8];
            if (var9 != null) {
               throw new IllegalArgumentException("Duplicate entry on id " + var8 + ": current=" + var7 + ", previous=" + var9);
            }

            var3[var8] = var7;
         }

         for(int var4 = 0; var4 < var2; ++var4) {
            if (var3[var4] == null) {
               throw new IllegalArgumentException("Missing value at index: " + var4);
            }
         }

         return var3;
      }
   }

   public static <T> IntFunction<T> a(ToIntFunction<T> var0, T[] var1, ByIdMap.a var2) {
      T[] var3 = b(var0, var1);
      int var4 = var3.length;

      return switch(var2) {
         case a -> {
            T var5 = var3[0];
            yield var3x -> var3x >= 0 && var3x < var4 ? var3[var3x] : var5;
         }
         case b -> var2x -> var3[MathHelper.b(var2x, var4)];
         case c -> var2x -> var3[MathHelper.a(var2x, 0, var4 - 1)];
      };
   }

   public static enum a {
      a,
      b,
      c;
   }
}
