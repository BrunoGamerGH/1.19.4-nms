package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.BlockVine;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;

public class WorldGenVines extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
   public WorldGenVines(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureEmptyConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      BlockPosition var2 = var0.e();
      var0.f();
      if (!var1.w(var2)) {
         return false;
      } else {
         for(EnumDirection var6 : EnumDirection.values()) {
            if (var6 != EnumDirection.a && BlockVine.a(var1, var2.a(var6), var6)) {
               var1.a(var2, Blocks.fe.o().a(BlockVine.a(var6), Boolean.valueOf(true)), 2);
               return true;
            }
         }

         return false;
      }
   }
}
