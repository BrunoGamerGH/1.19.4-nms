package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureChoiceConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class WorldGenFeatureChoice extends WorldGenerator<WorldGenFeatureChoiceConfiguration> {
   public WorldGenFeatureChoice(Codec<WorldGenFeatureChoiceConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureChoiceConfiguration> var0) {
      RandomSource var1 = var0.d();
      WorldGenFeatureChoiceConfiguration var2 = var0.f();
      GeneratorAccessSeed var3 = var0.b();
      ChunkGenerator var4 = var0.c();
      BlockPosition var5 = var0.e();
      boolean var6 = var1.h();
      return ((PlacedFeature)(var6 ? var2.b : var2.c).a()).a(var3, var4, var1, var5);
   }
}
