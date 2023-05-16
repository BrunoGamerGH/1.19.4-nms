package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRadiusConfiguration;

public class WorldGenFeatureNetherrackReplaceBlobs extends WorldGenerator<WorldGenFeatureRadiusConfiguration> {
   public WorldGenFeatureNetherrackReplaceBlobs(Codec<WorldGenFeatureRadiusConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureRadiusConfiguration> var0) {
      WorldGenFeatureRadiusConfiguration var1 = var0.f();
      GeneratorAccessSeed var2 = var0.b();
      RandomSource var3 = var0.d();
      Block var4 = var1.b.b();
      BlockPosition var5 = a(var2, var0.e().j().a(EnumDirection.EnumAxis.b, var2.v_() + 1, var2.ai() - 1), var4);
      if (var5 == null) {
         return false;
      } else {
         int var6 = var1.a().a(var3);
         int var7 = var1.a().a(var3);
         int var8 = var1.a().a(var3);
         int var9 = Math.max(var6, Math.max(var7, var8));
         boolean var10 = false;

         for(BlockPosition var12 : BlockPosition.a(var5, var6, var7, var8)) {
            if (var12.k(var5) > var9) {
               break;
            }

            IBlockData var13 = var2.a_(var12);
            if (var13.a(var4)) {
               this.a(var2, var12, var1.c);
               var10 = true;
            }
         }

         return var10;
      }
   }

   @Nullable
   private static BlockPosition a(GeneratorAccess var0, BlockPosition.MutableBlockPosition var1, Block var2) {
      while(var1.v() > var0.v_() + 1) {
         IBlockData var3 = var0.a_(var1);
         if (var3.a(var2)) {
            return var1;
         }

         var1.c(EnumDirection.a);
      }

      return null;
   }
}
