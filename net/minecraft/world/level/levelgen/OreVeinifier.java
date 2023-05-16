package net.minecraft.world.level.levelgen;

import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;

public final class OreVeinifier {
   private static final float a = 0.4F;
   private static final int b = 20;
   private static final double c = 0.2;
   private static final float d = 0.7F;
   private static final float e = 0.1F;
   private static final float f = 0.3F;
   private static final float g = 0.6F;
   private static final float h = 0.02F;
   private static final float i = -0.3F;

   private OreVeinifier() {
   }

   protected static NoiseChunk.c a(DensityFunction var0, DensityFunction var1, DensityFunction var2, PositionalRandomFactory var3) {
      IBlockData var4 = null;
      return var5 -> {
         double var6 = var0.a(var5);
         int var8 = var5.b();
         OreVeinifier.a var9 = var6 > 0.0 ? OreVeinifier.a.a : OreVeinifier.a.b;
         double var10 = Math.abs(var6);
         int var12 = var9.d - var8;
         int var13 = var8 - var9.c;
         if (var13 >= 0 && var12 >= 0) {
            int var14 = Math.min(var12, var13);
            double var15 = MathHelper.a((double)var14, 0.0, 20.0, -0.2, 0.0);
            if (var10 + var15 < 0.4F) {
               return var4;
            } else {
               RandomSource var17 = var3.a(var5.a(), var8, var5.c());
               if (var17.i() > 0.7F) {
                  return var4;
               } else if (var1.a(var5) >= 0.0) {
                  return var4;
               } else {
                  double var18 = MathHelper.a(var10, 0.4F, 0.6F, 0.1F, 0.3F);
                  if ((double)var17.i() < var18 && var2.a(var5) > -0.3F) {
                     return var17.i() < 0.02F ? var9.f : var9.e;
                  } else {
                     return var9.g;
                  }
               }
            }
         } else {
            return var4;
         }
      };
   }

   protected static enum a {
      a(Blocks.qI.o(), Blocks.sa.o(), Blocks.c.o(), 0, 50),
      b(Blocks.P.o(), Blocks.rZ.o(), Blocks.qv.o(), -60, -8);

      final IBlockData e;
      final IBlockData f;
      final IBlockData g;
      protected final int c;
      protected final int d;

      private a(IBlockData var2, IBlockData var3, IBlockData var4, int var5, int var6) {
         this.e = var2;
         this.f = var3;
         this.g = var4;
         this.c = var5;
         this.d = var6;
      }
   }
}
