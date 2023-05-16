package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureLakeConfiguration;
import net.minecraft.world.level.material.Material;

public class WorldGenFeatureIceburg extends WorldGenerator<WorldGenFeatureLakeConfiguration> {
   public WorldGenFeatureIceburg(Codec<WorldGenFeatureLakeConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureLakeConfiguration> var0) {
      BlockPosition var1 = var0.e();
      GeneratorAccessSeed var2 = var0.b();
      var1 = new BlockPosition(var1.u(), var0.c().e(), var1.w());
      RandomSource var3 = var0.d();
      boolean var4 = var3.j() > 0.7;
      IBlockData var5 = var0.f().b;
      double var6 = var3.j() * 2.0 * Math.PI;
      int var8 = 11 - var3.a(5);
      int var9 = 3 + var3.a(3);
      boolean var10 = var3.j() > 0.7;
      int var11 = 11;
      int var12 = var10 ? var3.a(6) + 6 : var3.a(15) + 3;
      if (!var10 && var3.j() > 0.9) {
         var12 += var3.a(19) + 7;
      }

      int var13 = Math.min(var12 + var3.a(11), 18);
      int var14 = Math.min(var12 + var3.a(7) - var3.a(5), 11);
      int var15 = var10 ? var8 : 11;

      for(int var16 = -var15; var16 < var15; ++var16) {
         for(int var17 = -var15; var17 < var15; ++var17) {
            for(int var18 = 0; var18 < var12; ++var18) {
               int var19 = var10 ? this.b(var18, var12, var14) : this.a(var3, var18, var12, var14);
               if (var10 || var16 < var19) {
                  this.a(var2, var3, var1, var12, var16, var18, var17, var19, var15, var10, var9, var6, var4, var5);
               }
            }
         }
      }

      this.a(var2, var1, var14, var12, var10, var8);

      for(int var16 = -var15; var16 < var15; ++var16) {
         for(int var17 = -var15; var17 < var15; ++var17) {
            for(int var18 = -1; var18 > -var13; --var18) {
               int var19 = var10 ? MathHelper.f((float)var15 * (1.0F - (float)Math.pow((double)var18, 2.0) / ((float)var13 * 8.0F))) : var15;
               int var20 = this.b(var3, -var18, var13, var14);
               if (var16 < var20) {
                  this.a(var2, var3, var1, var13, var16, var18, var17, var20, var19, var10, var9, var6, var4, var5);
               }
            }
         }
      }

      boolean var16 = var10 ? var3.j() > 0.1 : var3.j() > 0.7;
      if (var16) {
         this.a(var3, var2, var14, var12, var1, var10, var8, var6, var9);
      }

      return true;
   }

   private void a(RandomSource var0, GeneratorAccess var1, int var2, int var3, BlockPosition var4, boolean var5, int var6, double var7, int var9) {
      int var10 = var0.h() ? -1 : 1;
      int var11 = var0.h() ? -1 : 1;
      int var12 = var0.a(Math.max(var2 / 2 - 2, 1));
      if (var0.h()) {
         var12 = var2 / 2 + 1 - var0.a(Math.max(var2 - var2 / 2 - 1, 1));
      }

      int var13 = var0.a(Math.max(var2 / 2 - 2, 1));
      if (var0.h()) {
         var13 = var2 / 2 + 1 - var0.a(Math.max(var2 - var2 / 2 - 1, 1));
      }

      if (var5) {
         var12 = var13 = var0.a(Math.max(var6 - 5, 1));
      }

      BlockPosition var14 = new BlockPosition(var10 * var12, 0, var11 * var13);
      double var15 = var5 ? var7 + (Math.PI / 2) : var0.j() * 2.0 * Math.PI;

      for(int var17 = 0; var17 < var3 - 3; ++var17) {
         int var18 = this.a(var0, var17, var3, var2);
         this.a(var18, var17, var4, var1, false, var15, var14, var6, var9);
      }

      for(int var17 = -1; var17 > -var3 + var0.a(5); --var17) {
         int var18 = this.b(var0, -var17, var3, var2);
         this.a(var18, var17, var4, var1, true, var15, var14, var6, var9);
      }
   }

   private void a(int var0, int var1, BlockPosition var2, GeneratorAccess var3, boolean var4, double var5, BlockPosition var7, int var8, int var9) {
      int var10 = var0 + 1 + var8 / 3;
      int var11 = Math.min(var0 - 3, 3) + var9 / 2 - 1;

      for(int var12 = -var10; var12 < var10; ++var12) {
         for(int var13 = -var10; var13 < var10; ++var13) {
            double var14 = this.a(var12, var13, var7, var10, var11, var5);
            if (var14 < 0.0) {
               BlockPosition var16 = var2.b(var12, var1, var13);
               IBlockData var17 = var3.a_(var16);
               if (c(var17) || var17.a(Blocks.dO)) {
                  if (var4) {
                     this.a(var3, var16, Blocks.G.o());
                  } else {
                     this.a(var3, var16, Blocks.a.o());
                     this.a(var3, var16);
                  }
               }
            }
         }
      }
   }

   private void a(GeneratorAccess var0, BlockPosition var1) {
      if (var0.a_(var1.c()).a(Blocks.dM)) {
         this.a(var0, var1.c(), Blocks.a.o());
      }
   }

   private void a(
      GeneratorAccess var0,
      RandomSource var1,
      BlockPosition var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      boolean var9,
      int var10,
      double var11,
      boolean var13,
      IBlockData var14
   ) {
      double var15 = var9 ? this.a(var4, var6, BlockPosition.b, var8, this.a(var5, var3, var10), var11) : this.a(var4, var6, BlockPosition.b, var7, var1);
      if (var15 < 0.0) {
         BlockPosition var17 = var2.b(var4, var5, var6);
         double var18 = var9 ? -0.5 : (double)(-6 - var1.a(3));
         if (var15 > var18 && var1.j() > 0.9) {
            return;
         }

         this.a(var17, var0, var1, var3 - var5, var3, var9, var13, var14);
      }
   }

   private void a(BlockPosition var0, GeneratorAccess var1, RandomSource var2, int var3, int var4, boolean var5, boolean var6, IBlockData var7) {
      IBlockData var8 = var1.a_(var0);
      if (var8.d() == Material.a || var8.a(Blocks.dO) || var8.a(Blocks.dN) || var8.a(Blocks.G)) {
         boolean var9 = !var5 || var2.j() > 0.05;
         int var10 = var5 ? 3 : 2;
         if (var6 && !var8.a(Blocks.G) && (double)var3 <= (double)var2.a(Math.max(1, var4 / var10)) + (double)var4 * 0.6 && var9) {
            this.a(var1, var0, Blocks.dO.o());
         } else {
            this.a(var1, var0, var7);
         }
      }
   }

   private int a(int var0, int var1, int var2) {
      int var3 = var2;
      if (var0 > 0 && var1 - var0 <= 3) {
         var3 = var2 - (4 - (var1 - var0));
      }

      return var3;
   }

   private double a(int var0, int var1, BlockPosition var2, int var3, RandomSource var4) {
      float var5 = 10.0F * MathHelper.a(var4.i(), 0.2F, 0.8F) / (float)var3;
      return (double)var5 + Math.pow((double)(var0 - var2.u()), 2.0) + Math.pow((double)(var1 - var2.w()), 2.0) - Math.pow((double)var3, 2.0);
   }

   private double a(int var0, int var1, BlockPosition var2, int var3, int var4, double var5) {
      return Math.pow(((double)(var0 - var2.u()) * Math.cos(var5) - (double)(var1 - var2.w()) * Math.sin(var5)) / (double)var3, 2.0)
         + Math.pow(((double)(var0 - var2.u()) * Math.sin(var5) + (double)(var1 - var2.w()) * Math.cos(var5)) / (double)var4, 2.0)
         - 1.0;
   }

   private int a(RandomSource var0, int var1, int var2, int var3) {
      float var4 = 3.5F - var0.i();
      float var5 = (1.0F - (float)Math.pow((double)var1, 2.0) / ((float)var2 * var4)) * (float)var3;
      if (var2 > 15 + var0.a(5)) {
         int var6 = var1 < 3 + var0.a(6) ? var1 / 2 : var1;
         var5 = (1.0F - (float)var6 / ((float)var2 * var4 * 0.4F)) * (float)var3;
      }

      return MathHelper.f(var5 / 2.0F);
   }

   private int b(int var0, int var1, int var2) {
      float var3 = 1.0F;
      float var4 = (1.0F - (float)Math.pow((double)var0, 2.0) / ((float)var1 * 1.0F)) * (float)var2;
      return MathHelper.f(var4 / 2.0F);
   }

   private int b(RandomSource var0, int var1, int var2, int var3) {
      float var4 = 1.0F + var0.i() / 2.0F;
      float var5 = (1.0F - (float)var1 / ((float)var2 * var4)) * (float)var3;
      return MathHelper.f(var5 / 2.0F);
   }

   private static boolean c(IBlockData var0) {
      return var0.a(Blocks.iB) || var0.a(Blocks.dO) || var0.a(Blocks.mS);
   }

   private boolean a(IBlockAccess var0, BlockPosition var1) {
      return var0.a_(var1.d()).d() == Material.a;
   }

   private void a(GeneratorAccess var0, BlockPosition var1, int var2, int var3, boolean var4, int var5) {
      int var6 = var4 ? var5 : var2 / 2;

      for(int var7 = -var6; var7 <= var6; ++var7) {
         for(int var8 = -var6; var8 <= var6; ++var8) {
            for(int var9 = 0; var9 <= var3; ++var9) {
               BlockPosition var10 = var1.b(var7, var9, var8);
               IBlockData var11 = var0.a_(var10);
               if (c(var11) || var11.a(Blocks.dM)) {
                  if (this.a((IBlockAccess)var0, var10)) {
                     this.a(var0, var10, Blocks.a.o());
                     this.a(var0, var10.c(), Blocks.a.o());
                  } else if (c(var11)) {
                     IBlockData[] var12 = new IBlockData[]{var0.a_(var10.g()), var0.a_(var10.h()), var0.a_(var10.e()), var0.a_(var10.f())};
                     int var13 = 0;

                     for(IBlockData var17 : var12) {
                        if (!c(var17)) {
                           ++var13;
                        }
                     }

                     if (var13 >= 3) {
                        this.a(var0, var10, Blocks.a.o());
                     }
                  }
               }
            }
         }
      }
   }
}
