package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.function.Function;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.chunk.ChunkSection;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureOreConfiguration;

public class WorldGenMinable extends WorldGenerator<WorldGenFeatureOreConfiguration> {
   public WorldGenMinable(Codec<WorldGenFeatureOreConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureOreConfiguration> var0) {
      RandomSource var1 = var0.d();
      BlockPosition var2 = var0.e();
      GeneratorAccessSeed var3 = var0.b();
      WorldGenFeatureOreConfiguration var4 = var0.f();
      float var5 = var1.i() * (float) Math.PI;
      float var6 = (float)var4.c / 8.0F;
      int var7 = MathHelper.f(((float)var4.c / 16.0F * 2.0F + 1.0F) / 2.0F);
      double var8 = (double)var2.u() + Math.sin((double)var5) * (double)var6;
      double var10 = (double)var2.u() - Math.sin((double)var5) * (double)var6;
      double var12 = (double)var2.w() + Math.cos((double)var5) * (double)var6;
      double var14 = (double)var2.w() - Math.cos((double)var5) * (double)var6;
      int var16 = 2;
      double var17 = (double)(var2.v() + var1.a(3) - 2);
      double var19 = (double)(var2.v() + var1.a(3) - 2);
      int var21 = var2.u() - MathHelper.f(var6) - var7;
      int var22 = var2.v() - 2 - var7;
      int var23 = var2.w() - MathHelper.f(var6) - var7;
      int var24 = 2 * (MathHelper.f(var6) + var7);
      int var25 = 2 * (2 + var7);

      for(int var26 = var21; var26 <= var21 + var24; ++var26) {
         for(int var27 = var23; var27 <= var23 + var24; ++var27) {
            if (var22 <= var3.a(HeightMap.Type.c, var26, var27)) {
               return this.a(var3, var1, var4, var8, var10, var12, var14, var17, var19, var21, var22, var23, var24, var25);
            }
         }
      }

      return false;
   }

   protected boolean a(
      GeneratorAccessSeed var0,
      RandomSource var1,
      WorldGenFeatureOreConfiguration var2,
      double var3,
      double var5,
      double var7,
      double var9,
      double var11,
      double var13,
      int var15,
      int var16,
      int var17,
      int var18,
      int var19
   ) {
      int var20 = 0;
      BitSet var21 = new BitSet(var18 * var19 * var18);
      BlockPosition.MutableBlockPosition var22 = new BlockPosition.MutableBlockPosition();
      int var23 = var2.c;
      double[] var24 = new double[var23 * 4];

      for(int var25 = 0; var25 < var23; ++var25) {
         float var26 = (float)var25 / (float)var23;
         double var27 = MathHelper.d((double)var26, var3, var5);
         double var29 = MathHelper.d((double)var26, var11, var13);
         double var31 = MathHelper.d((double)var26, var7, var9);
         double var33 = var1.j() * (double)var23 / 16.0;
         double var35 = ((double)(MathHelper.a((float) Math.PI * var26) + 1.0F) * var33 + 1.0) / 2.0;
         var24[var25 * 4 + 0] = var27;
         var24[var25 * 4 + 1] = var29;
         var24[var25 * 4 + 2] = var31;
         var24[var25 * 4 + 3] = var35;
      }

      for(int var25 = 0; var25 < var23 - 1; ++var25) {
         if (!(var24[var25 * 4 + 3] <= 0.0)) {
            for(int var26 = var25 + 1; var26 < var23; ++var26) {
               if (!(var24[var26 * 4 + 3] <= 0.0)) {
                  double var27 = var24[var25 * 4 + 0] - var24[var26 * 4 + 0];
                  double var29 = var24[var25 * 4 + 1] - var24[var26 * 4 + 1];
                  double var31 = var24[var25 * 4 + 2] - var24[var26 * 4 + 2];
                  double var33 = var24[var25 * 4 + 3] - var24[var26 * 4 + 3];
                  if (var33 * var33 > var27 * var27 + var29 * var29 + var31 * var31) {
                     if (var33 > 0.0) {
                        var24[var26 * 4 + 3] = -1.0;
                     } else {
                        var24[var25 * 4 + 3] = -1.0;
                     }
                  }
               }
            }
         }
      }

      try (BulkSectionAccess var25 = new BulkSectionAccess(var0)) {
         for(int var26 = 0; var26 < var23; ++var26) {
            double var27 = var24[var26 * 4 + 3];
            if (!(var27 < 0.0)) {
               double var29 = var24[var26 * 4 + 0];
               double var31 = var24[var26 * 4 + 1];
               double var33 = var24[var26 * 4 + 2];
               int var35 = Math.max(MathHelper.a(var29 - var27), var15);
               int var36 = Math.max(MathHelper.a(var31 - var27), var16);
               int var37 = Math.max(MathHelper.a(var33 - var27), var17);
               int var38 = Math.max(MathHelper.a(var29 + var27), var35);
               int var39 = Math.max(MathHelper.a(var31 + var27), var36);
               int var40 = Math.max(MathHelper.a(var33 + var27), var37);

               for(int var41 = var35; var41 <= var38; ++var41) {
                  double var42 = ((double)var41 + 0.5 - var29) / var27;
                  if (var42 * var42 < 1.0) {
                     for(int var44 = var36; var44 <= var39; ++var44) {
                        double var45 = ((double)var44 + 0.5 - var31) / var27;
                        if (var42 * var42 + var45 * var45 < 1.0) {
                           for(int var47 = var37; var47 <= var40; ++var47) {
                              double var48 = ((double)var47 + 0.5 - var33) / var27;
                              if (var42 * var42 + var45 * var45 + var48 * var48 < 1.0 && !var0.d(var44)) {
                                 int var50 = var41 - var15 + (var44 - var16) * var18 + (var47 - var17) * var18 * var19;
                                 if (!var21.get(var50)) {
                                    var21.set(var50);
                                    var22.d(var41, var44, var47);
                                    if (var0.e_(var22)) {
                                       ChunkSection var51 = var25.a(var22);
                                       if (var51 != null) {
                                          int var52 = SectionPosition.b(var41);
                                          int var53 = SectionPosition.b(var44);
                                          int var54 = SectionPosition.b(var47);
                                          IBlockData var55 = var51.a(var52, var53, var54);

                                          for(WorldGenFeatureOreConfiguration.a var57 : var2.b) {
                                             if (a(var55, var25::b, var1, var2, var57, var22)) {
                                                var51.a(var52, var53, var54, var57.c, false);
                                                ++var20;
                                                break;
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return var20 > 0;
   }

   public static boolean a(
      IBlockData var0,
      Function<BlockPosition, IBlockData> var1,
      RandomSource var2,
      WorldGenFeatureOreConfiguration var3,
      WorldGenFeatureOreConfiguration.a var4,
      BlockPosition.MutableBlockPosition var5
   ) {
      if (!var4.b.a(var0, var2)) {
         return false;
      } else if (a(var2, var3.d)) {
         return true;
      } else {
         return !a(var1, var5);
      }
   }

   protected static boolean a(RandomSource var0, float var1) {
      if (var1 <= 0.0F) {
         return true;
      } else if (var1 >= 1.0F) {
         return false;
      } else {
         return var0.i() >= var1;
      }
   }
}
