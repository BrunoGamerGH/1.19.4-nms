package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;

public class WorldGenLightStone1 extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
   public WorldGenLightStone1(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureEmptyConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      BlockPosition var2 = var0.e();
      RandomSource var3 = var0.d();
      if (!var1.w(var2)) {
         return false;
      } else {
         IBlockData var4 = var1.a_(var2.c());
         if (!var4.a(Blocks.dV) && !var4.a(Blocks.dY) && !var4.a(Blocks.pn)) {
            return false;
         } else {
            var1.a(var2, Blocks.ec.o(), 2);

            for(int var5 = 0; var5 < 1500; ++var5) {
               BlockPosition var6 = var2.b(var3.a(8) - var3.a(8), -var3.a(12), var3.a(8) - var3.a(8));
               if (var1.a_(var6).h()) {
                  int var7 = 0;

                  for(EnumDirection var11 : EnumDirection.values()) {
                     if (var1.a_(var6.a(var11)).a(Blocks.ec)) {
                        ++var7;
                     }

                     if (var7 > 1) {
                        break;
                     }
                  }

                  if (var7 == 1) {
                     var1.a(var6, Blocks.ec.o(), 2);
                  }
               }
            }

            return true;
         }
      }
   }
}
