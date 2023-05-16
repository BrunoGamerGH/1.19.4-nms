package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

public class WaterloggedVegetationPatchFeature extends VegetationPatchFeature {
   public WaterloggedVegetationPatchFeature(Codec<VegetationPatchConfiguration> var0) {
      super(var0);
   }

   @Override
   protected Set<BlockPosition> a(
      GeneratorAccessSeed var0, VegetationPatchConfiguration var1, RandomSource var2, BlockPosition var3, Predicate<IBlockData> var4, int var5, int var6
   ) {
      Set<BlockPosition> var7 = super.a(var0, var1, var2, var3, var4, var5, var6);
      Set<BlockPosition> var8 = new HashSet<>();
      BlockPosition.MutableBlockPosition var9 = new BlockPosition.MutableBlockPosition();

      for(BlockPosition var11 : var7) {
         if (!a(var0, var7, var11, var9)) {
            var8.add(var11);
         }
      }

      for(BlockPosition var11 : var8) {
         var0.a(var11, Blocks.G.o(), 2);
      }

      return var8;
   }

   private static boolean a(GeneratorAccessSeed var0, Set<BlockPosition> var1, BlockPosition var2, BlockPosition.MutableBlockPosition var3) {
      return a(var0, var2, var3, EnumDirection.c)
         || a(var0, var2, var3, EnumDirection.f)
         || a(var0, var2, var3, EnumDirection.d)
         || a(var0, var2, var3, EnumDirection.e)
         || a(var0, var2, var3, EnumDirection.a);
   }

   private static boolean a(GeneratorAccessSeed var0, BlockPosition var1, BlockPosition.MutableBlockPosition var2, EnumDirection var3) {
      var2.a(var1, var3);
      return !var0.a_(var2).d(var0, var2, var3.g());
   }

   @Override
   protected boolean a(GeneratorAccessSeed var0, VegetationPatchConfiguration var1, ChunkGenerator var2, RandomSource var3, BlockPosition var4) {
      if (super.a(var0, var1, var2, var3, var4.d())) {
         IBlockData var5 = var0.a_(var4);
         if (var5.b(BlockProperties.C) && !var5.c(BlockProperties.C)) {
            var0.a(var4, var5.a(BlockProperties.C, Boolean.valueOf(true)), 2);
         }

         return true;
      } else {
         return false;
      }
   }
}
