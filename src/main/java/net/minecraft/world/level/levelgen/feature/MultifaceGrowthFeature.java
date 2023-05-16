package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;

public class MultifaceGrowthFeature extends WorldGenerator<MultifaceGrowthConfiguration> {
   public MultifaceGrowthFeature(Codec<MultifaceGrowthConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<MultifaceGrowthConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      BlockPosition var2 = var0.e();
      RandomSource var3 = var0.d();
      MultifaceGrowthConfiguration var4 = var0.f();
      if (!c(var1.a_(var2))) {
         return false;
      } else {
         List<EnumDirection> var5 = var4.a(var3);
         if (a(var1, var2, var1.a_(var2), var4, var3, var5)) {
            return true;
         } else {
            BlockPosition.MutableBlockPosition var6 = var2.j();

            for(EnumDirection var8 : var5) {
               var6.g(var2);
               List<EnumDirection> var9 = var4.a(var3, var8.g());

               for(int var10 = 0; var10 < var4.c; ++var10) {
                  var6.a(var2, var8);
                  IBlockData var11 = var1.a_(var6);
                  if (!c(var11) && !var11.a(var4.b)) {
                     break;
                  }

                  if (a(var1, var6, var11, var4, var3, var9)) {
                     return true;
                  }
               }
            }

            return false;
         }
      }
   }

   public static boolean a(
      GeneratorAccessSeed var0, BlockPosition var1, IBlockData var2, MultifaceGrowthConfiguration var3, RandomSource var4, List<EnumDirection> var5
   ) {
      BlockPosition.MutableBlockPosition var6 = var1.j();

      for(EnumDirection var8 : var5) {
         IBlockData var9 = var0.a_(var6.a(var1, var8));
         if (var9.a(var3.h)) {
            IBlockData var10 = var3.b.c(var2, var0, var1, var8);
            if (var10 == null) {
               return false;
            }

            var0.a(var1, var10, 3);
            var0.A(var1).e(var1);
            if (var4.i() < var3.g) {
               var3.b.c().a(var10, var0, var1, var8, var4, true);
            }

            return true;
         }
      }

      return false;
   }

   private static boolean c(IBlockData var0) {
      return var0.h() || var0.a(Blocks.G);
   }
}
