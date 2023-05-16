package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.BlockBamboo;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyBambooSize;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfigurationChance;

public class WorldGenFeatureBamboo extends WorldGenerator<WorldGenFeatureConfigurationChance> {
   private static final IBlockData a = Blocks.mV
      .o()
      .a(BlockBamboo.g, Integer.valueOf(1))
      .a(BlockBamboo.h, BlockPropertyBambooSize.a)
      .a(BlockBamboo.i, Integer.valueOf(0));
   private static final IBlockData b = a.a(BlockBamboo.h, BlockPropertyBambooSize.c).a(BlockBamboo.i, Integer.valueOf(1));
   private static final IBlockData c = a.a(BlockBamboo.h, BlockPropertyBambooSize.c);
   private static final IBlockData d = a.a(BlockBamboo.h, BlockPropertyBambooSize.b);

   public WorldGenFeatureBamboo(Codec<WorldGenFeatureConfigurationChance> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureConfigurationChance> var0) {
      int var1 = 0;
      BlockPosition var2 = var0.e();
      GeneratorAccessSeed var3 = var0.b();
      RandomSource var4 = var0.d();
      WorldGenFeatureConfigurationChance var5 = var0.f();
      BlockPosition.MutableBlockPosition var6 = var2.j();
      BlockPosition.MutableBlockPosition var7 = var2.j();
      if (var3.w(var6)) {
         if (Blocks.mV.o().a(var3, var6)) {
            int var8 = var4.a(12) + 5;
            if (var4.i() < var5.l) {
               int var9 = var4.a(4) + 1;

               for(int var10 = var2.u() - var9; var10 <= var2.u() + var9; ++var10) {
                  for(int var11 = var2.w() - var9; var11 <= var2.w() + var9; ++var11) {
                     int var12 = var10 - var2.u();
                     int var13 = var11 - var2.w();
                     if (var12 * var12 + var13 * var13 <= var9 * var9) {
                        var7.d(var10, var3.a(HeightMap.Type.b, var10, var11) - 1, var11);
                        if (b(var3.a_(var7))) {
                           var3.a(var7, Blocks.l.o(), 2);
                        }
                     }
                  }
               }
            }

            for(int var9 = 0; var9 < var8 && var3.w(var6); ++var9) {
               var3.a(var6, a, 2);
               var6.c(EnumDirection.b, 1);
            }

            if (var6.v() - var2.v() >= 3) {
               var3.a(var6, b, 2);
               var3.a(var6.c(EnumDirection.a, 1), c, 2);
               var3.a(var6.c(EnumDirection.a, 1), d, 2);
            }
         }

         ++var1;
      }

      return var1 > 0;
   }
}
