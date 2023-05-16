package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.BlockGrowingTop;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;

public class WorldGenFeatureWeepingVines extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
   private static final EnumDirection[] a = EnumDirection.values();

   public WorldGenFeatureWeepingVines(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureEmptyConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      BlockPosition var2 = var0.e();
      RandomSource var3 = var0.d();
      if (!var1.w(var2)) {
         return false;
      } else {
         IBlockData var4 = var1.a_(var2.c());
         if (!var4.a(Blocks.dV) && !var4.a(Blocks.kH)) {
            return false;
         } else {
            this.a(var1, var3, var2);
            this.b(var1, var3, var2);
            return true;
         }
      }
   }

   private void a(GeneratorAccess var0, RandomSource var1, BlockPosition var2) {
      var0.a(var2, Blocks.kH.o(), 2);
      BlockPosition.MutableBlockPosition var3 = new BlockPosition.MutableBlockPosition();
      BlockPosition.MutableBlockPosition var4 = new BlockPosition.MutableBlockPosition();

      for(int var5 = 0; var5 < 200; ++var5) {
         var3.a(var2, var1.a(6) - var1.a(6), var1.a(2) - var1.a(5), var1.a(6) - var1.a(6));
         if (var0.w(var3)) {
            int var6 = 0;

            for(EnumDirection var10 : a) {
               IBlockData var11 = var0.a_(var4.a(var3, var10));
               if (var11.a(Blocks.dV) || var11.a(Blocks.kH)) {
                  ++var6;
               }

               if (var6 > 1) {
                  break;
               }
            }

            if (var6 == 1) {
               var0.a(var3, Blocks.kH.o(), 2);
            }
         }
      }
   }

   private void b(GeneratorAccess var0, RandomSource var1, BlockPosition var2) {
      BlockPosition.MutableBlockPosition var3 = new BlockPosition.MutableBlockPosition();

      for(int var4 = 0; var4 < 100; ++var4) {
         var3.a(var2, var1.a(8) - var1.a(8), var1.a(2) - var1.a(7), var1.a(8) - var1.a(8));
         if (var0.w(var3)) {
            IBlockData var5 = var0.a_(var3.c());
            if (var5.a(Blocks.dV) || var5.a(Blocks.kH)) {
               int var6 = MathHelper.a(var1, 1, 8);
               if (var1.a(6) == 0) {
                  var6 *= 2;
               }

               if (var1.a(5) == 0) {
                  var6 = 1;
               }

               int var7 = 17;
               int var8 = 25;
               a(var0, var1, var3, var6, 17, 25);
            }
         }
      }
   }

   public static void a(GeneratorAccess var0, RandomSource var1, BlockPosition.MutableBlockPosition var2, int var3, int var4, int var5) {
      for(int var6 = 0; var6 <= var3; ++var6) {
         if (var0.w(var2)) {
            if (var6 == var3 || !var0.w(var2.d())) {
               var0.a(var2, Blocks.ov.o().a(BlockGrowingTop.d, Integer.valueOf(MathHelper.a(var1, var4, var5))), 2);
               break;
            }

            var0.a(var2, Blocks.ow.o(), 2);
         }

         var2.c(EnumDirection.a);
      }
   }
}
