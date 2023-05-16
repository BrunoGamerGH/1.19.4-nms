package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.BlockDirtSnow;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;

public class WorldGenFeatureIceSnow extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
   public WorldGenFeatureIceSnow(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureEmptyConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      BlockPosition var2 = var0.e();
      BlockPosition.MutableBlockPosition var3 = new BlockPosition.MutableBlockPosition();
      BlockPosition.MutableBlockPosition var4 = new BlockPosition.MutableBlockPosition();

      for(int var5 = 0; var5 < 16; ++var5) {
         for(int var6 = 0; var6 < 16; ++var6) {
            int var7 = var2.u() + var5;
            int var8 = var2.w() + var6;
            int var9 = var1.a(HeightMap.Type.e, var7, var8);
            var3.d(var7, var9, var8);
            var4.g(var3).c(EnumDirection.a, 1);
            BiomeBase var10 = var1.v(var3).a();
            if (var10.a(var1, var4, false)) {
               var1.a(var4, Blocks.dN.o(), 2);
            }

            if (var10.b(var1, var3)) {
               var1.a(var3, Blocks.dM.o(), 2);
               IBlockData var11 = var1.a_(var4);
               if (var11.b(BlockDirtSnow.a)) {
                  var1.a(var4, var11.a(BlockDirtSnow.a, Boolean.valueOf(true)), 2);
               }
            }
         }
      }

      return true;
   }
}
