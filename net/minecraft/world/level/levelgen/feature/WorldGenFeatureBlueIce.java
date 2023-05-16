package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;
import net.minecraft.world.level.material.Material;

public class WorldGenFeatureBlueIce extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
   public WorldGenFeatureBlueIce(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureEmptyConfiguration> var0) {
      BlockPosition var1 = var0.e();
      GeneratorAccessSeed var2 = var0.b();
      RandomSource var3 = var0.d();
      if (var1.v() > var2.m_() - 1) {
         return false;
      } else if (!var2.a_(var1).a(Blocks.G) && !var2.a_(var1.d()).a(Blocks.G)) {
         return false;
      } else {
         boolean var4 = false;

         for(EnumDirection var8 : EnumDirection.values()) {
            if (var8 != EnumDirection.a && var2.a_(var1.a(var8)).a(Blocks.iB)) {
               var4 = true;
               break;
            }
         }

         if (!var4) {
            return false;
         } else {
            var2.a(var1, Blocks.mS.o(), 2);

            for(int var5 = 0; var5 < 200; ++var5) {
               int var6 = var3.a(5) - var3.a(6);
               int var7 = 3;
               if (var6 < 2) {
                  var7 += var6 / 2;
               }

               if (var7 >= 1) {
                  BlockPosition var8 = var1.b(var3.a(var7) - var3.a(var7), var6, var3.a(var7) - var3.a(var7));
                  IBlockData var9 = var2.a_(var8);
                  if (var9.d() == Material.a || var9.a(Blocks.G) || var9.a(Blocks.iB) || var9.a(Blocks.dN)) {
                     for(EnumDirection var13 : EnumDirection.values()) {
                        IBlockData var14 = var2.a_(var8.a(var13));
                        if (var14.a(Blocks.mS)) {
                           var2.a(var8, Blocks.mS.o(), 2);
                           break;
                        }
                     }
                  }
               }
            }

            return true;
         }
      }
   }
}
