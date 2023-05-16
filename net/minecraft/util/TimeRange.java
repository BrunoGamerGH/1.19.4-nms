package net.minecraft.util;

import java.util.concurrent.TimeUnit;
import net.minecraft.util.valueproviders.UniformInt;

public class TimeRange {
   public static final long a = TimeUnit.SECONDS.toNanos(1L);
   public static final long b = TimeUnit.MILLISECONDS.toNanos(1L);

   public static UniformInt a(int var0, int var1) {
      return UniformInt.a(var0 * 20, var1 * 20);
   }
}
