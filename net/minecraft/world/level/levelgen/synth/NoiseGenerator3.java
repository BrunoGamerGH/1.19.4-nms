package net.minecraft.world.level.levelgen.synth;

import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.List;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.SeededRandom;

public class NoiseGenerator3 {
   private final NoiseGenerator3Handler[] a;
   private final double b;
   private final double c;

   public NoiseGenerator3(RandomSource var0, List<Integer> var1) {
      this(var0, new IntRBTreeSet(var1));
   }

   private NoiseGenerator3(RandomSource var0, IntSortedSet var1) {
      if (var1.isEmpty()) {
         throw new IllegalArgumentException("Need some octaves!");
      } else {
         int var2 = -var1.firstInt();
         int var3 = var1.lastInt();
         int var4 = var2 + var3 + 1;
         if (var4 < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
         } else {
            NoiseGenerator3Handler var5 = new NoiseGenerator3Handler(var0);
            int var6 = var3;
            this.a = new NoiseGenerator3Handler[var4];
            if (var3 >= 0 && var3 < var4 && var1.contains(0)) {
               this.a[var3] = var5;
            }

            for(int var7 = var3 + 1; var7 < var4; ++var7) {
               if (var7 >= 0 && var1.contains(var6 - var7)) {
                  this.a[var7] = new NoiseGenerator3Handler(var0);
               } else {
                  var0.b(262);
               }
            }

            if (var3 > 0) {
               long var7 = (long)(var5.a(var5.b, var5.c, var5.d) * 9.223372E18F);
               RandomSource var9 = new SeededRandom(new LegacyRandomSource(var7));

               for(int var10 = var6 - 1; var10 >= 0; --var10) {
                  if (var10 < var4 && var1.contains(var6 - var10)) {
                     this.a[var10] = new NoiseGenerator3Handler(var9);
                  } else {
                     var9.b(262);
                  }
               }
            }

            this.c = Math.pow(2.0, (double)var3);
            this.b = 1.0 / (Math.pow(2.0, (double)var4) - 1.0);
         }
      }
   }

   public double a(double var0, double var2, boolean var4) {
      double var5 = 0.0;
      double var7 = this.c;
      double var9 = this.b;

      for(NoiseGenerator3Handler var14 : this.a) {
         if (var14 != null) {
            var5 += var14.a(var0 * var7 + (var4 ? var14.b : 0.0), var2 * var7 + (var4 ? var14.c : 0.0)) * var9;
         }

         var7 /= 2.0;
         var9 *= 2.0;
      }

      return var5;
   }
}
