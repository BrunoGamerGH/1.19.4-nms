package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureCircleConfiguration;

public class DiskFeature extends WorldGenerator<WorldGenFeatureCircleConfiguration> {
   public DiskFeature(Codec<WorldGenFeatureCircleConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureCircleConfiguration> var0) {
      WorldGenFeatureCircleConfiguration var1 = var0.f();
      BlockPosition var2 = var0.e();
      GeneratorAccessSeed var3 = var0.b();
      RandomSource var4 = var0.d();
      boolean var5 = false;
      int var6 = var2.v();
      int var7 = var6 + var1.d();
      int var8 = var6 - var1.d() - 1;
      int var9 = var1.c().a(var4);
      BlockPosition.MutableBlockPosition var10 = new BlockPosition.MutableBlockPosition();

      for(BlockPosition var12 : BlockPosition.a(var2.b(-var9, 0, -var9), var2.b(var9, 0, var9))) {
         int var13 = var12.u() - var2.u();
         int var14 = var12.w() - var2.w();
         if (var13 * var13 + var14 * var14 <= var9 * var9) {
            var5 |= this.a(var1, var3, var4, var7, var8, var10.g(var12));
         }
      }

      return var5;
   }

   protected boolean a(
      WorldGenFeatureCircleConfiguration var0, GeneratorAccessSeed var1, RandomSource var2, int var3, int var4, BlockPosition.MutableBlockPosition var5
   ) {
      boolean var6 = false;
      IBlockData var7 = null;

      for(int var8 = var3; var8 > var4; --var8) {
         var5.q(var8);
         if (var0.b().test(var1, var5)) {
            IBlockData var9 = var0.a().a(var1, var2, var5);
            var1.a(var5, var9, 2);
            this.a(var1, var5);
            var6 = true;
         }
      }

      return var6;
   }
}
