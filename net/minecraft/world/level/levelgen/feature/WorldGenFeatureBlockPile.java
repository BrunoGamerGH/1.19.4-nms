package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureBlockPileConfiguration;

public class WorldGenFeatureBlockPile extends WorldGenerator<WorldGenFeatureBlockPileConfiguration> {
   public WorldGenFeatureBlockPile(Codec<WorldGenFeatureBlockPileConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureBlockPileConfiguration> var0) {
      BlockPosition var1 = var0.e();
      GeneratorAccessSeed var2 = var0.b();
      RandomSource var3 = var0.d();
      WorldGenFeatureBlockPileConfiguration var4 = var0.f();
      if (var1.v() < var2.v_() + 5) {
         return false;
      } else {
         int var5 = 2 + var3.a(2);
         int var6 = 2 + var3.a(2);

         for(BlockPosition var8 : BlockPosition.a(var1.b(-var5, 0, -var6), var1.b(var5, 1, var6))) {
            int var9 = var1.u() - var8.u();
            int var10 = var1.w() - var8.w();
            if ((float)(var9 * var9 + var10 * var10) <= var3.i() * 10.0F - var3.i() * 6.0F) {
               this.a(var2, var8, var3, var4);
            } else if ((double)var3.i() < 0.031) {
               this.a(var2, var8, var3, var4);
            }
         }

         return true;
      }
   }

   private boolean a(GeneratorAccess var0, BlockPosition var1, RandomSource var2) {
      BlockPosition var3 = var1.d();
      IBlockData var4 = var0.a_(var3);
      return var4.a(Blocks.kB) ? var2.h() : var4.d(var0, var3, EnumDirection.b);
   }

   private void a(GeneratorAccess var0, BlockPosition var1, RandomSource var2, WorldGenFeatureBlockPileConfiguration var3) {
      if (var0.w(var1) && this.a(var0, var1, var2)) {
         var0.a(var1, var3.b.a(var2, var1), 4);
      }
   }
}
