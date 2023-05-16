package net.minecraft.world.level;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.BlockPosition;

public class SpawnerCreatureProbabilities {
   private final List<SpawnerCreatureProbabilities.a> a = Lists.newArrayList();

   public void a(BlockPosition var0, double var1) {
      if (var1 != 0.0) {
         this.a.add(new SpawnerCreatureProbabilities.a(var0, var1));
      }
   }

   public double b(BlockPosition var0, double var1) {
      if (var1 == 0.0) {
         return 0.0;
      } else {
         double var3 = 0.0;

         for(SpawnerCreatureProbabilities.a var6 : this.a) {
            var3 += var6.a(var0);
         }

         return var3 * var1;
      }
   }

   static class a {
      private final BlockPosition a;
      private final double b;

      public a(BlockPosition var0, double var1) {
         this.a = var0;
         this.b = var1;
      }

      public double a(BlockPosition var0) {
         double var1 = this.a.j(var0);
         return var1 == 0.0 ? Double.POSITIVE_INFINITY : this.b / Math.sqrt(var1);
      }
   }
}
