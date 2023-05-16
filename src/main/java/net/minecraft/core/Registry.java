package net.minecraft.core;

import javax.annotation.Nullable;

public interface Registry<T> extends Iterable<T> {
   int a = -1;

   int a(T var1);

   @Nullable
   T a(int var1);

   default T b(int var0) {
      T var1 = this.a(var0);
      if (var1 == null) {
         throw new IllegalArgumentException("No value with id " + var0);
      } else {
         return var1;
      }
   }

   int b();
}
