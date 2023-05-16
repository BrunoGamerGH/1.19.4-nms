package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.BlockSeaPickle;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenDecoratorFrequencyConfiguration;

public class WorldGenFeatureSeaPickel extends WorldGenerator<WorldGenDecoratorFrequencyConfiguration> {
   public WorldGenFeatureSeaPickel(Codec<WorldGenDecoratorFrequencyConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenDecoratorFrequencyConfiguration> var0) {
      int var1 = 0;
      RandomSource var2 = var0.d();
      GeneratorAccessSeed var3 = var0.b();
      BlockPosition var4 = var0.e();
      int var5 = var0.f().a().a(var2);

      for(int var6 = 0; var6 < var5; ++var6) {
         int var7 = var2.a(8) - var2.a(8);
         int var8 = var2.a(8) - var2.a(8);
         int var9 = var3.a(HeightMap.Type.d, var4.u() + var7, var4.w() + var8);
         BlockPosition var10 = new BlockPosition(var4.u() + var7, var9, var4.w() + var8);
         IBlockData var11 = Blocks.mR.o().a(BlockSeaPickle.b, Integer.valueOf(var2.a(4) + 1));
         if (var3.a_(var10).a(Blocks.G) && var11.a(var3, var10)) {
            var3.a(var10, var11, 2);
            ++var1;
         }
      }

      return var1 > 0;
   }
}
