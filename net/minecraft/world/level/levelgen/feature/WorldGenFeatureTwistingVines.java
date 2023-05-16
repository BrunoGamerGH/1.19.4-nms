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
import net.minecraft.world.level.levelgen.feature.configurations.TwistingVinesConfig;

public class WorldGenFeatureTwistingVines extends WorldGenerator<TwistingVinesConfig> {
   public WorldGenFeatureTwistingVines(Codec<TwistingVinesConfig> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<TwistingVinesConfig> var0) {
      GeneratorAccessSeed var1 = var0.b();
      BlockPosition var2 = var0.e();
      if (a(var1, var2)) {
         return false;
      } else {
         RandomSource var3 = var0.d();
         TwistingVinesConfig var4 = var0.f();
         int var5 = var4.a();
         int var6 = var4.b();
         int var7 = var4.c();
         BlockPosition.MutableBlockPosition var8 = new BlockPosition.MutableBlockPosition();

         for(int var9 = 0; var9 < var5 * var5; ++var9) {
            var8.g(var2).e(MathHelper.a(var3, -var5, var5), MathHelper.a(var3, -var6, var6), MathHelper.a(var3, -var5, var5));
            if (a(var1, var8) && !a(var1, (BlockPosition)var8)) {
               int var10 = MathHelper.a(var3, 1, var7);
               if (var3.a(6) == 0) {
                  var10 *= 2;
               }

               if (var3.a(5) == 0) {
                  var10 = 1;
               }

               int var11 = 17;
               int var12 = 25;
               a(var1, var3, var8, var10, 17, 25);
            }
         }

         return true;
      }
   }

   private static boolean a(GeneratorAccess var0, BlockPosition.MutableBlockPosition var1) {
      do {
         var1.e(0, -1, 0);
         if (var0.u(var1)) {
            return false;
         }
      } while(var0.a_(var1).h());

      var1.e(0, 1, 0);
      return true;
   }

   public static void a(GeneratorAccess var0, RandomSource var1, BlockPosition.MutableBlockPosition var2, int var3, int var4, int var5) {
      for(int var6 = 1; var6 <= var3; ++var6) {
         if (var0.w(var2)) {
            if (var6 == var3 || !var0.w(var2.c())) {
               var0.a(var2, Blocks.ox.o().a(BlockGrowingTop.d, Integer.valueOf(MathHelper.a(var1, var4, var5))), 2);
               break;
            }

            var0.a(var2, Blocks.oy.o(), 2);
         }

         var2.c(EnumDirection.b);
      }
   }

   private static boolean a(GeneratorAccess var0, BlockPosition var1) {
      if (!var0.w(var1)) {
         return true;
      } else {
         IBlockData var2 = var0.a_(var1.d());
         return !var2.a(Blocks.dV) && !var2.a(Blocks.oj) && !var2.a(Blocks.ol);
      }
   }
}
