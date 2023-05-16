package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;

public class WorldGenCanyon extends WorldGenCarverAbstract<CanyonCarverConfiguration> {
   public WorldGenCanyon(Codec<CanyonCarverConfiguration> var0) {
      super(var0);
   }

   public boolean a(CanyonCarverConfiguration var0, RandomSource var1) {
      return var1.i() <= var0.l;
   }

   public boolean a(
      CarvingContext var0,
      CanyonCarverConfiguration var1,
      IChunkAccess var2,
      Function<BlockPosition, Holder<BiomeBase>> var3,
      RandomSource var4,
      Aquifer var5,
      ChunkCoordIntPair var6,
      CarvingMask var7
   ) {
      int var8 = (this.d() * 2 - 1) * 16;
      double var9 = (double)var6.a(var4.a(16));
      int var11 = var1.e.a(var4, var0);
      double var12 = (double)var6.b(var4.a(16));
      float var14 = var4.i() * (float) (Math.PI * 2);
      float var15 = var1.b.a(var4);
      double var16 = (double)var1.f.a(var4);
      float var18 = var1.c.c.a(var4);
      int var19 = (int)((float)var8 * var1.c.b.a(var4));
      int var20 = 0;
      this.a(var0, var1, var2, var3, var4.g(), var5, var9, (double)var11, var12, var18, var14, var15, 0, var19, var16, var7);
      return true;
   }

   private void a(
      CarvingContext var0,
      CanyonCarverConfiguration var1,
      IChunkAccess var2,
      Function<BlockPosition, Holder<BiomeBase>> var3,
      long var4,
      Aquifer var6,
      double var7,
      double var9,
      double var11,
      float var13,
      float var14,
      float var15,
      int var16,
      int var17,
      double var18,
      CarvingMask var20
   ) {
      RandomSource var21 = RandomSource.a(var4);
      float[] var22 = this.a(var0, var1, var21);
      float var23 = 0.0F;
      float var24 = 0.0F;

      for(int var25 = var16; var25 < var17; ++var25) {
         double var26 = 1.5 + (double)(MathHelper.a((float)var25 * (float) Math.PI / (float)var17) * var13);
         double var28 = var26 * var18;
         var26 *= (double)var1.c.e.a(var21);
         var28 = this.a(var1, var21, var28, (float)var17, (float)var25);
         float var30 = MathHelper.b(var15);
         float var31 = MathHelper.a(var15);
         var7 += (double)(MathHelper.b(var14) * var30);
         var9 += (double)var31;
         var11 += (double)(MathHelper.a(var14) * var30);
         var15 *= 0.7F;
         var15 += var24 * 0.05F;
         var14 += var23 * 0.05F;
         var24 *= 0.8F;
         var23 *= 0.5F;
         var24 += (var21.i() - var21.i()) * var21.i() * 2.0F;
         var23 += (var21.i() - var21.i()) * var21.i() * 4.0F;
         if (var21.a(4) != 0) {
            if (!a(var2.f(), var7, var11, var25, var17, var13)) {
               return;
            }

            this.a(
               var0,
               var1,
               var2,
               var3,
               var6,
               var7,
               var9,
               var11,
               var26,
               var28,
               var20,
               (var1x, var2x, var4x, var6x, var8x) -> this.a(var1x, var22, var2x, var4x, var6x, var8x)
            );
         }
      }
   }

   private float[] a(CarvingContext var0, CanyonCarverConfiguration var1, RandomSource var2) {
      int var3 = var0.b();
      float[] var4 = new float[var3];
      float var5 = 1.0F;

      for(int var6 = 0; var6 < var3; ++var6) {
         if (var6 == 0 || var2.a(var1.c.d) == 0) {
            var5 = 1.0F + var2.i() * var2.i();
         }

         var4[var6] = var5 * var5;
      }

      return var4;
   }

   private double a(CanyonCarverConfiguration var0, RandomSource var1, double var2, float var4, float var5) {
      float var6 = 1.0F - MathHelper.e(0.5F - var5 / var4) * 2.0F;
      float var7 = var0.c.f + var0.c.g * var6;
      return (double)var7 * var2 * (double)MathHelper.b(var1, 0.75F, 1.0F);
   }

   private boolean a(CarvingContext var0, float[] var1, double var2, double var4, double var6, int var8) {
      int var9 = var8 - var0.a();
      return (var2 * var2 + var6 * var6) * (double)var1[var9 - 1] + var4 * var4 / 6.0 >= 1.0;
   }
}
