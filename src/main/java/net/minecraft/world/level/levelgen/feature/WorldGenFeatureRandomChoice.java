package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandomChoiceConfiguration;

public class WorldGenFeatureRandomChoice extends WorldGenerator<WorldGenFeatureRandomChoiceConfiguration> {
   public WorldGenFeatureRandomChoice(Codec<WorldGenFeatureRandomChoiceConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureRandomChoiceConfiguration> var0) {
      WorldGenFeatureRandomChoiceConfiguration var1 = var0.f();
      RandomSource var2 = var0.d();
      GeneratorAccessSeed var3 = var0.b();
      ChunkGenerator var4 = var0.c();
      BlockPosition var5 = var0.e();

      for(WeightedPlacedFeature var7 : var1.b) {
         if (var2.i() < var7.c) {
            return var7.a(var3, var4, var2, var5);
         }
      }

      return var1.c.a().a(var3, var4, var2, var5);
   }
}
