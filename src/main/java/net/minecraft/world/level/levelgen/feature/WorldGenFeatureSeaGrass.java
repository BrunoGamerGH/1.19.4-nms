package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyDoubleBlockHalf;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfigurationChance;

public class WorldGenFeatureSeaGrass extends WorldGenerator<WorldGenFeatureConfigurationChance> {
   public WorldGenFeatureSeaGrass(Codec<WorldGenFeatureConfigurationChance> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureConfigurationChance> var0) {
      boolean var1 = false;
      RandomSource var2 = var0.d();
      GeneratorAccessSeed var3 = var0.b();
      BlockPosition var4 = var0.e();
      WorldGenFeatureConfigurationChance var5 = var0.f();
      int var6 = var2.a(8) - var2.a(8);
      int var7 = var2.a(8) - var2.a(8);
      int var8 = var3.a(HeightMap.Type.d, var4.u() + var6, var4.w() + var7);
      BlockPosition var9 = new BlockPosition(var4.u() + var6, var8, var4.w() + var7);
      if (var3.a_(var9).a(Blocks.G)) {
         boolean var10 = var2.j() < (double)var5.l;
         IBlockData var11 = var10 ? Blocks.bw.o() : Blocks.bv.o();
         if (var11.a(var3, var9)) {
            if (var10) {
               IBlockData var12 = var11.a(TallSeagrassBlock.b, BlockPropertyDoubleBlockHalf.a);
               BlockPosition var13 = var9.c();
               if (var3.a_(var13).a(Blocks.G)) {
                  var3.a(var9, var11, 2);
                  var3.a(var13, var12, 2);
               }
            } else {
               var3.a(var9, var11, 2);
            }

            var1 = true;
         }
      }

      return var1;
   }
}
