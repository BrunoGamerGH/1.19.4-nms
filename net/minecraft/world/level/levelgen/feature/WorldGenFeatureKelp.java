package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.BlockKelp;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;

public class WorldGenFeatureKelp extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
   public WorldGenFeatureKelp(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureEmptyConfiguration> var0) {
      int var1 = 0;
      GeneratorAccessSeed var2 = var0.b();
      BlockPosition var3 = var0.e();
      RandomSource var4 = var0.d();
      int var5 = var2.a(HeightMap.Type.d, var3.u(), var3.w());
      BlockPosition var6 = new BlockPosition(var3.u(), var5, var3.w());
      if (var2.a_(var6).a(Blocks.G)) {
         IBlockData var7 = Blocks.lZ.o();
         IBlockData var8 = Blocks.ma.o();
         int var9 = 1 + var4.a(10);

         for(int var10 = 0; var10 <= var9; ++var10) {
            if (var2.a_(var6).a(Blocks.G) && var2.a_(var6.c()).a(Blocks.G) && var8.a(var2, var6)) {
               if (var10 == var9) {
                  var2.a(var6, var7.a(BlockKelp.d, Integer.valueOf(var4.a(4) + 20)), 2);
                  ++var1;
               } else {
                  var2.a(var6, var8, 2);
               }
            } else if (var10 > 0) {
               BlockPosition var11 = var6.d();
               if (var7.a(var2, var11) && !var2.a_(var11.d()).a(Blocks.lZ)) {
                  var2.a(var11, var7.a(BlockKelp.d, Integer.valueOf(var4.a(4) + 20)), 2);
                  ++var1;
               }
               break;
            }

            var6 = var6.c();
         }
      }

      return var1 > 0;
   }
}
