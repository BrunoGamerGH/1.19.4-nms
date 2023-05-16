package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.material.Material;

public class WorldGenFeatureHugeFungi extends WorldGenerator<WorldGenFeatureHugeFungiConfiguration> {
   private static final float a = 0.06F;

   public WorldGenFeatureHugeFungi(Codec<WorldGenFeatureHugeFungiConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureHugeFungiConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      BlockPosition var2 = var0.e();
      RandomSource var3 = var0.d();
      ChunkGenerator var4 = var0.c();
      WorldGenFeatureHugeFungiConfiguration var5 = var0.f();
      Block var6 = var5.b.b();
      BlockPosition var7 = null;
      IBlockData var8 = var1.a_(var2.d());
      if (var8.a(var6)) {
         var7 = var2;
      }

      if (var7 == null) {
         return false;
      } else {
         int var9 = MathHelper.a(var3, 4, 13);
         if (var3.a(12) == 0) {
            var9 *= 2;
         }

         if (!var5.f) {
            int var10 = var4.d();
            if (var7.v() + var9 + 1 >= var10) {
               return false;
            }
         }

         boolean var10 = !var5.f && var3.i() < 0.06F;
         var1.a(var2, Blocks.a.o(), 4);
         this.a(var1, var3, var5, var7, var9, var10);
         this.b(var1, var3, var5, var7, var9, var10);
         return true;
      }
   }

   private static boolean a(GeneratorAccess var0, BlockPosition var1, boolean var2) {
      return var0.a(var1, var1x -> {
         Material var2x = var1x.d();
         return var1x.o() || var2 && var2x == Material.e;
      });
   }

   private void a(GeneratorAccess var0, RandomSource var1, WorldGenFeatureHugeFungiConfiguration var2, BlockPosition var3, int var4, boolean var5) {
      BlockPosition.MutableBlockPosition var6 = new BlockPosition.MutableBlockPosition();
      IBlockData var7 = var2.c;
      int var8 = var5 ? 1 : 0;

      for(int var9 = -var8; var9 <= var8; ++var9) {
         for(int var10 = -var8; var10 <= var8; ++var10) {
            boolean var11 = var5 && MathHelper.a(var9) == var8 && MathHelper.a(var10) == var8;

            for(int var12 = 0; var12 < var4; ++var12) {
               var6.a(var3, var9, var12, var10);
               if (a(var0, var6, true)) {
                  if (var2.f) {
                     if (!var0.a_(var6.d()).h()) {
                        var0.b(var6, true);
                     }

                     var0.a(var6, var7, 3);
                  } else if (var11) {
                     if (var1.i() < 0.1F) {
                        this.a(var0, var6, var7);
                     }
                  } else {
                     this.a(var0, var6, var7);
                  }
               }
            }
         }
      }
   }

   private void b(GeneratorAccess var0, RandomSource var1, WorldGenFeatureHugeFungiConfiguration var2, BlockPosition var3, int var4, boolean var5) {
      BlockPosition.MutableBlockPosition var6 = new BlockPosition.MutableBlockPosition();
      boolean var7 = var2.d.a(Blocks.kH);
      int var8 = Math.min(var1.a(1 + var4 / 3) + 5, var4);
      int var9 = var4 - var8;

      for(int var10 = var9; var10 <= var4; ++var10) {
         int var11 = var10 < var4 - var1.a(3) ? 2 : 1;
         if (var8 > 8 && var10 < var9 + 4) {
            var11 = 3;
         }

         if (var5) {
            ++var11;
         }

         for(int var12 = -var11; var12 <= var11; ++var12) {
            for(int var13 = -var11; var13 <= var11; ++var13) {
               boolean var14 = var12 == -var11 || var12 == var11;
               boolean var15 = var13 == -var11 || var13 == var11;
               boolean var16 = !var14 && !var15 && var10 != var4;
               boolean var17 = var14 && var15;
               boolean var18 = var10 < var9 + 3;
               var6.a(var3, var12, var10, var13);
               if (a(var0, var6, false)) {
                  if (var2.f && !var0.a_(var6.d()).h()) {
                     var0.b(var6, true);
                  }

                  if (var18) {
                     if (!var16) {
                        this.a(var0, var1, var6, var2.d, var7);
                     }
                  } else if (var16) {
                     this.a(var0, var1, var2, var6, 0.1F, 0.2F, var7 ? 0.1F : 0.0F);
                  } else if (var17) {
                     this.a(var0, var1, var2, var6, 0.01F, 0.7F, var7 ? 0.083F : 0.0F);
                  } else {
                     this.a(var0, var1, var2, var6, 5.0E-4F, 0.98F, var7 ? 0.07F : 0.0F);
                  }
               }
            }
         }
      }
   }

   private void a(
      GeneratorAccess var0,
      RandomSource var1,
      WorldGenFeatureHugeFungiConfiguration var2,
      BlockPosition.MutableBlockPosition var3,
      float var4,
      float var5,
      float var6
   ) {
      if (var1.i() < var4) {
         this.a(var0, var3, var2.e);
      } else if (var1.i() < var5) {
         this.a(var0, var3, var2.d);
         if (var1.i() < var6) {
            a(var3, var0, var1);
         }
      }
   }

   private void a(GeneratorAccess var0, RandomSource var1, BlockPosition var2, IBlockData var3, boolean var4) {
      if (var0.a_(var2.d()).a(var3.b())) {
         this.a(var0, var2, var3);
      } else if ((double)var1.i() < 0.15) {
         this.a(var0, var2, var3);
         if (var4 && var1.a(11) == 0) {
            a(var2, var0, var1);
         }
      }
   }

   private static void a(BlockPosition var0, GeneratorAccess var1, RandomSource var2) {
      BlockPosition.MutableBlockPosition var3 = var0.j().c(EnumDirection.a);
      if (var1.w(var3)) {
         int var4 = MathHelper.a(var2, 1, 5);
         if (var2.a(7) == 0) {
            var4 *= 2;
         }

         int var5 = 23;
         int var6 = 25;
         WorldGenFeatureWeepingVines.a(var1, var2, var3, var4, 23, 25);
      }
   }
}
