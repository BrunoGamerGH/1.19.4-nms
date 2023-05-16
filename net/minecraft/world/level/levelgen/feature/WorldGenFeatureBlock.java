package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.BlockTallPlant;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureBlockConfiguration;

public class WorldGenFeatureBlock extends WorldGenerator<WorldGenFeatureBlockConfiguration> {
   public WorldGenFeatureBlock(Codec<WorldGenFeatureBlockConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureBlockConfiguration> var0) {
      WorldGenFeatureBlockConfiguration var1 = var0.f();
      GeneratorAccessSeed var2 = var0.b();
      BlockPosition var3 = var0.e();
      IBlockData var4 = var1.a().a(var0.d(), var3);
      if (var4.a(var2, var3)) {
         if (var4.b() instanceof BlockTallPlant) {
            if (!var2.w(var3.c())) {
               return false;
            }

            BlockTallPlant.a(var2, var4, var3, 2);
         } else {
            var2.a(var3, var4, 2);
         }

         return true;
      } else {
         return false;
      }
   }
}
