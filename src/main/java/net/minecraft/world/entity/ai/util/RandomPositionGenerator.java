package net.minecraft.world.entity.ai.util;

import com.google.common.annotations.VisibleForTesting;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.phys.Vec3D;

public class RandomPositionGenerator {
   private static final int a = 10;

   public static BlockPosition a(RandomSource var0, int var1, int var2) {
      int var3 = var0.a(2 * var1 + 1) - var1;
      int var4 = var0.a(2 * var2 + 1) - var2;
      int var5 = var0.a(2 * var1 + 1) - var1;
      return new BlockPosition(var3, var4, var5);
   }

   @Nullable
   public static BlockPosition a(RandomSource var0, int var1, int var2, int var3, double var4, double var6, double var8) {
      double var10 = MathHelper.d(var6, var4) - (float) (Math.PI / 2);
      double var12 = var10 + (double)(2.0F * var0.i() - 1.0F) * var8;
      double var14 = Math.sqrt(var0.j()) * (double)MathHelper.g * (double)var1;
      double var16 = -var14 * Math.sin(var12);
      double var18 = var14 * Math.cos(var12);
      if (!(Math.abs(var16) > (double)var1) && !(Math.abs(var18) > (double)var1)) {
         int var20 = var0.a(2 * var2 + 1) - var2 + var3;
         return BlockPosition.a(var16, (double)var20, var18);
      } else {
         return null;
      }
   }

   @VisibleForTesting
   public static BlockPosition a(BlockPosition var0, int var1, Predicate<BlockPosition> var2) {
      if (!var2.test(var0)) {
         return var0;
      } else {
         BlockPosition var3 = var0.c();

         while(var3.v() < var1 && var2.test(var3)) {
            var3 = var3.c();
         }

         return var3;
      }
   }

   @VisibleForTesting
   public static BlockPosition a(BlockPosition var0, int var1, int var2, Predicate<BlockPosition> var3) {
      if (var1 < 0) {
         throw new IllegalArgumentException("aboveSolidAmount was " + var1 + ", expected >= 0");
      } else if (!var3.test(var0)) {
         return var0;
      } else {
         BlockPosition var4 = var0.c();

         while(var4.v() < var2 && var3.test(var4)) {
            var4 = var4.c();
         }

         BlockPosition var5;
         BlockPosition var6;
         for(var5 = var4; var5.v() < var2 && var5.v() - var4.v() < var1; var5 = var6) {
            var6 = var5.c();
            if (var3.test(var6)) {
               break;
            }
         }

         return var5;
      }
   }

   @Nullable
   public static Vec3D a(EntityCreature var0, Supplier<BlockPosition> var1) {
      return a(var1, var0::f);
   }

   @Nullable
   public static Vec3D a(Supplier<BlockPosition> var0, ToDoubleFunction<BlockPosition> var1) {
      double var2 = Double.NEGATIVE_INFINITY;
      BlockPosition var4 = null;

      for(int var5 = 0; var5 < 10; ++var5) {
         BlockPosition var6 = var0.get();
         if (var6 != null) {
            double var7 = var1.applyAsDouble(var6);
            if (var7 > var2) {
               var2 = var7;
               var4 = var6;
            }
         }
      }

      return var4 != null ? Vec3D.c(var4) : null;
   }

   public static BlockPosition a(EntityCreature var0, int var1, RandomSource var2, BlockPosition var3) {
      int var4 = var3.u();
      int var5 = var3.w();
      if (var0.fG() && var1 > 1) {
         BlockPosition var6 = var0.fD();
         if (var0.dl() > (double)var6.u()) {
            var4 -= var2.a(var1 / 2);
         } else {
            var4 += var2.a(var1 / 2);
         }

         if (var0.dr() > (double)var6.w()) {
            var5 -= var2.a(var1 / 2);
         } else {
            var5 += var2.a(var1 / 2);
         }
      }

      return BlockPosition.a((double)var4 + var0.dl(), (double)var3.v() + var0.dn(), (double)var5 + var0.dr());
   }
}
