package net.minecraft.util;

public class LinearCongruentialGenerator {
   private static final long a = 6364136223846793005L;
   private static final long b = 1442695040888963407L;

   public static long a(long var0, long var2) {
      var0 *= var0 * 6364136223846793005L + 1442695040888963407L;
      return var0 + var2;
   }
}
