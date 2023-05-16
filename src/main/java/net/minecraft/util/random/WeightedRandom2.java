package net.minecraft.util.random;

import java.util.List;
import java.util.Optional;
import net.minecraft.SystemUtils;
import net.minecraft.util.RandomSource;

public class WeightedRandom2 {
   private WeightedRandom2() {
   }

   public static int a(List<? extends WeightedEntry> var0) {
      long var1 = 0L;

      for(WeightedEntry var4 : var0) {
         var1 += (long)var4.a().a();
      }

      if (var1 > 2147483647L) {
         throw new IllegalArgumentException("Sum of weights must be <= 2147483647");
      } else {
         return (int)var1;
      }
   }

   public static <T extends WeightedEntry> Optional<T> a(RandomSource var0, List<T> var1, int var2) {
      if (var2 < 0) {
         throw (IllegalArgumentException)SystemUtils.b(new IllegalArgumentException("Negative total weight in getRandomItem"));
      } else if (var2 == 0) {
         return Optional.empty();
      } else {
         int var3 = var0.a(var2);
         return a(var1, var3);
      }
   }

   public static <T extends WeightedEntry> Optional<T> a(List<T> var0, int var1) {
      for(T var3 : var0) {
         var1 -= var3.a().a();
         if (var1 < 0) {
            return Optional.of(var3);
         }
      }

      return Optional.empty();
   }

   public static <T extends WeightedEntry> Optional<T> a(RandomSource var0, List<T> var1) {
      return a(var0, var1, a(var1));
   }
}
