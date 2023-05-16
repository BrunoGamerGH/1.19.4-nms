package net.minecraft.world.level.levelgen;

import com.google.common.annotations.VisibleForTesting;
import java.util.concurrent.atomic.AtomicLong;

public final class RandomSupport {
   public static final long a = -7046029254386353131L;
   public static final long b = 7640891576956012809L;
   private static final AtomicLong c = new AtomicLong(8682522807148012L);

   @VisibleForTesting
   public static long a(long var0) {
      var0 = (var0 ^ var0 >>> 30) * -4658895280553007687L;
      var0 = (var0 ^ var0 >>> 27) * -7723592293110705685L;
      return var0 ^ var0 >>> 31;
   }

   public static RandomSupport.a b(long var0) {
      long var2 = var0 ^ 7640891576956012809L;
      long var4 = var2 + -7046029254386353131L;
      return new RandomSupport.a(a(var2), a(var4));
   }

   public static long a() {
      return c.updateAndGet(var0 -> var0 * 1181783497276652981L) ^ System.nanoTime();
   }

   public static record a(long seedLo, long seedHi) {
      private final long a;
      private final long b;

      public a(long var0, long var2) {
         this.a = var0;
         this.b = var2;
      }
   }
}
