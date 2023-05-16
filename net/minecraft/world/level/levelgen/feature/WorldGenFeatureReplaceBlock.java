package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureOreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureReplaceBlockConfiguration;

public class WorldGenFeatureReplaceBlock extends WorldGenerator<WorldGenFeatureReplaceBlockConfiguration> {
   public WorldGenFeatureReplaceBlock(Codec<WorldGenFeatureReplaceBlockConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureReplaceBlockConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      BlockPosition var2 = var0.e();
      WorldGenFeatureReplaceBlockConfiguration var3 = var0.f();

      for(WorldGenFeatureOreConfiguration.a var5 : var3.b) {
         if (var5.b.a(var1.a_(var2), var0.d())) {
            var1.a(var2, var5.c, 2);
            break;
         }
      }

      return true;
   }
}
