package net.minecraft.util;

import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;

@FunctionalInterface
public interface TimeSource {
   long get(TimeUnit var1);

   public interface a extends TimeSource, LongSupplier {
      @Override
      default long get(TimeUnit var0) {
         return var0.convert(this.getAsLong(), TimeUnit.NANOSECONDS);
      }
   }
}
