package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;

public class WorldGenPackedIce2 extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
   public WorldGenPackedIce2(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureEmptyConfiguration> var0) {
      BlockPosition var1 = var0.e();
      RandomSource var2 = var0.d();
      GeneratorAccessSeed var3 = var0.b();

      while(var3.w(var1) && var1.v() > var3.v_() + 2) {
         var1 = var1.d();
      }

      if (!var3.a_(var1).a(Blocks.dO)) {
         return false;
      } else {
         var1 = var1.b(var2.a(4));
         int var4 = var2.a(4) + 7;
         int var5 = var4 / 4 + var2.a(2);
         if (var5 > 1 && var2.a(60) == 0) {
            var1 = var1.b(10 + var2.a(30));
         }

         for(int var6 = 0; var6 < var4; ++var6) {
            float var7 = (1.0F - (float)var6 / (float)var4) * (float)var5;
            int var8 = MathHelper.f(var7);

            for(int var9 = -var8; var9 <= var8; ++var9) {
               float var10 = (float)MathHelper.a(var9) - 0.25F;

               for(int var11 = -var8; var11 <= var8; ++var11) {
                  float var12 = (float)MathHelper.a(var11) - 0.25F;
                  if ((var9 == 0 && var11 == 0 || !(var10 * var10 + var12 * var12 > var7 * var7))
                     && (var9 != -var8 && var9 != var8 && var11 != -var8 && var11 != var8 || !(var2.i() > 0.75F))) {
                     IBlockData var13 = var3.a_(var1.b(var9, var6, var11));
                     if (var13.h() || b(var13) || var13.a(Blocks.dO) || var13.a(Blocks.dN)) {
                        this.a(var3, var1.b(var9, var6, var11), Blocks.iB.o());
                     }

                     if (var6 != 0 && var8 > 1) {
                        var13 = var3.a_(var1.b(var9, -var6, var11));
                        if (var13.h() || b(var13) || var13.a(Blocks.dO) || var13.a(Blocks.dN)) {
                           this.a(var3, var1.b(var9, -var6, var11), Blocks.iB.o());
                        }
                     }
                  }
               }
            }
         }

         int var6 = var5 - 1;
         if (var6 < 0) {
            var6 = 0;
         } else if (var6 > 1) {
            var6 = 1;
         }

         for(int var7 = -var6; var7 <= var6; ++var7) {
            for(int var8 = -var6; var8 <= var6; ++var8) {
               BlockPosition var9 = var1.b(var7, -1, var8);
               int var10 = 50;
               if (Math.abs(var7) == 1 && Math.abs(var8) == 1) {
                  var10 = var2.a(5);
               }

               while(var9.v() > 50) {
                  IBlockData var11 = var3.a_(var9);
                  if (!var11.h() && !b(var11) && !var11.a(Blocks.dO) && !var11.a(Blocks.dN) && !var11.a(Blocks.iB)) {
                     break;
                  }

                  this.a(var3, var9, Blocks.iB.o());
                  var9 = var9.d();
                  if (--var10 <= 0) {
                     var9 = var9.c(var2.a(5) + 1);
                     var10 = var2.a(5);
                  }
               }
            }
         }

         return true;
      }
   }
}
