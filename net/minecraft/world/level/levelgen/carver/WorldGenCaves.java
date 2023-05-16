package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.SectionPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;

public class WorldGenCaves extends WorldGenCarverAbstract<CaveCarverConfiguration> {
   public WorldGenCaves(Codec<CaveCarverConfiguration> var0) {
      super(var0);
   }

   public boolean a(CaveCarverConfiguration var0, RandomSource var1) {
      return var1.i() <= var0.l;
   }

   public boolean a(
      CarvingContext var0,
      CaveCarverConfiguration var1,
      IChunkAccess var2,
      Function<BlockPosition, Holder<BiomeBase>> var3,
      RandomSource var4,
      Aquifer var5,
      ChunkCoordIntPair var6,
      CarvingMask var7
   ) {
      int var8 = SectionPosition.c(this.d() * 2 - 1);
      int var9 = var4.a(var4.a(var4.a(this.a()) + 1) + 1);

      for(int var10 = 0; var10 < var9; ++var10) {
         double var11 = (double)var6.a(var4.a(16));
         double var13 = (double)var1.e.a(var4, var0);
         double var15 = (double)var6.b(var4.a(16));
         double var17 = (double)var1.b.a(var4);
         double var19 = (double)var1.c.a(var4);
         double var21 = (double)var1.j.a(var4);
         WorldGenCarverAbstract.a var23 = (var2x, var3x, var5x, var7x, var9x) -> a(var3x, var5x, var7x, var21);
         int var24 = 1;
         if (var4.a(4) == 0) {
            double var25 = (double)var1.f.a(var4);
            float var27 = 1.0F + var4.i() * 6.0F;
            this.a(var0, var1, var2, var3, var5, var11, var13, var15, var27, var25, var7, var23);
            var24 += var4.a(4);
         }

         for(int var25 = 0; var25 < var24; ++var25) {
            float var26 = var4.i() * (float) (Math.PI * 2);
            float var27 = (var4.i() - 0.5F) / 4.0F;
            float var28 = this.a(var4);
            int var29 = var8 - var4.a(var8 / 4);
            int var30 = 0;
            this.a(var0, var1, var2, var3, var4.g(), var5, var11, var13, var15, var17, var19, var28, var26, var27, 0, var29, this.b(), var7, var23);
         }
      }

      return true;
   }

   protected int a() {
      return 15;
   }

   protected float a(RandomSource var0) {
      float var1 = var0.i() * 2.0F + var0.i();
      if (var0.a(10) == 0) {
         var1 *= var0.i() * var0.i() * 3.0F + 1.0F;
      }

      return var1;
   }

   protected double b() {
      return 1.0;
   }

   protected void a(
      CarvingContext var0,
      CaveCarverConfiguration var1,
      IChunkAccess var2,
      Function<BlockPosition, Holder<BiomeBase>> var3,
      Aquifer var4,
      double var5,
      double var7,
      double var9,
      float var11,
      double var12,
      CarvingMask var14,
      WorldGenCarverAbstract.a var15
   ) {
      double var16 = 1.5 + (double)(MathHelper.a((float) (Math.PI / 2)) * var11);
      double var18 = var16 * var12;
      this.a(var0, var1, var2, var3, var4, var5 + 1.0, var7, var9, var16, var18, var14, var15);
   }

   protected void a(
      CarvingContext var0,
      CaveCarverConfiguration var1,
      IChunkAccess var2,
      Function<BlockPosition, Holder<BiomeBase>> var3,
      long var4,
      Aquifer var6,
      double var7,
      double var9,
      double var11,
      double var13,
      double var15,
      float var17,
      float var18,
      float var19,
      int var20,
      int var21,
      double var22,
      CarvingMask var24,
      WorldGenCarverAbstract.a var25
   ) {
      RandomSource var26 = RandomSource.a(var4);
      int var27 = var26.a(var21 / 2) + var21 / 4;
      boolean var28 = var26.a(6) == 0;
      float var29 = 0.0F;
      float var30 = 0.0F;

      for(int var31 = var20; var31 < var21; ++var31) {
         double var32 = 1.5 + (double)(MathHelper.a((float) Math.PI * (float)var31 / (float)var21) * var17);
         double var34 = var32 * var22;
         float var36 = MathHelper.b(var19);
         var7 += (double)(MathHelper.b(var18) * var36);
         var9 += (double)MathHelper.a(var19);
         var11 += (double)(MathHelper.a(var18) * var36);
         var19 *= var28 ? 0.92F : 0.7F;
         var19 += var30 * 0.1F;
         var18 += var29 * 0.1F;
         var30 *= 0.9F;
         var29 *= 0.75F;
         var30 += (var26.i() - var26.i()) * var26.i() * 2.0F;
         var29 += (var26.i() - var26.i()) * var26.i() * 4.0F;
         if (var31 == var27 && var17 > 1.0F) {
            this.a(
               var0,
               var1,
               var2,
               var3,
               var26.g(),
               var6,
               var7,
               var9,
               var11,
               var13,
               var15,
               var26.i() * 0.5F + 0.5F,
               var18 - (float) (Math.PI / 2),
               var19 / 3.0F,
               var31,
               var21,
               1.0,
               var24,
               var25
            );
            this.a(
               var0,
               var1,
               var2,
               var3,
               var26.g(),
               var6,
               var7,
               var9,
               var11,
               var13,
               var15,
               var26.i() * 0.5F + 0.5F,
               var18 + (float) (Math.PI / 2),
               var19 / 3.0F,
               var31,
               var21,
               1.0,
               var24,
               var25
            );
            return;
         }

         if (var26.a(4) != 0) {
            if (!a(var2.f(), var7, var11, var31, var21, var17)) {
               return;
            }

            this.a(var0, var1, var2, var3, var6, var7, var9, var11, var32 * var13, var34 * var15, var24, var25);
         }
      }
   }

   private static boolean a(double var0, double var2, double var4, double var6) {
      if (var2 <= var6) {
         return true;
      } else {
         return var0 * var0 + var2 * var2 + var4 * var4 >= 1.0;
      }
   }
}
