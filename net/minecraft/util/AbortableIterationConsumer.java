package net.minecraft.util;

import java.util.function.Consumer;

@FunctionalInterface
public interface AbortableIterationConsumer<T> {
   AbortableIterationConsumer.a accept(T var1);

   static <T> AbortableIterationConsumer<T> forConsumer(Consumer<T> var0) {
      return var1 -> {
         var0.accept(var1);
         return AbortableIterationConsumer.a.a;
      };
   }

   public static enum a {
      a,
      b;

      public boolean a() {
         return this == b;
      }
   }
}
