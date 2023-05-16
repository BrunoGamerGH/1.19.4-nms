package net.minecraft.world.level.levelgen;

import net.minecraft.util.RandomSource;

public interface BitRandomSource extends RandomSource {
   float b = 5.9604645E-8F;
   double c = 1.110223E-16F;

   int c(int var1);

   @Override
   default int f() {
      return this.c(32);
   }

   @Override
   default int a(int var0) {
      if (var0 <= 0) {
         throw new IllegalArgumentException("Bound must be positive");
      } else if ((var0 & var0 - 1) == 0) {
         return (int)((long)var0 * (long)this.c(31) >> 31);
      } else {
         int var1;
         int var2;
         do {
            var1 = this.c(31);
            var2 = var1 % var0;
         } while(var1 - var2 + (var0 - 1) < 0);

         return var2;
      }
   }

   @Override
   default long g() {
      int var0 = this.c(32);
      int var1 = this.c(32);
      long var2 = (long)var0 << 32;
      return var2 + (long)var1;
   }

   @Override
   default boolean h() {
      return this.c(1) != 0;
   }

   @Override
   default float i() {
      return (float)this.c(24) * 5.9604645E-8F;
   }

   @Override
   default double j() {
      int var0 = this.c(26);
      int var1 = this.c(27);
      long var2 = ((long)var0 << 27) + (long)var1;
      return (double)var2 * 1.110223E-16F;
   }
}
