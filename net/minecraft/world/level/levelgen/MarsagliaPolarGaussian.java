package net.minecraft.world.level.levelgen;

import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;

public class MarsagliaPolarGaussian {
   public final RandomSource a;
   private double b;
   private boolean c;

   public MarsagliaPolarGaussian(RandomSource var0) {
      this.a = var0;
   }

   public void a() {
      this.c = false;
   }

   public double b() {
      if (this.c) {
         this.c = false;
         return this.b;
      } else {
         double var0;
         double var2;
         double var4;
         do {
            var0 = 2.0 * this.a.j() - 1.0;
            var2 = 2.0 * this.a.j() - 1.0;
            var4 = MathHelper.k(var0) + MathHelper.k(var2);
         } while(var4 >= 1.0 || var4 == 0.0);

         double var6 = Math.sqrt(-2.0 * Math.log(var4) / var4);
         this.b = var2 * var6;
         this.c = true;
         return var0 * var6;
      }
   }
}
