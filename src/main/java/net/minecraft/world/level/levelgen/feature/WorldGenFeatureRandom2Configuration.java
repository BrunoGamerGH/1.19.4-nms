package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandom2;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class WorldGenFeatureRandom2Configuration extends WorldGenerator<WorldGenFeatureRandom2> {
   public WorldGenFeatureRandom2Configuration(Codec<WorldGenFeatureRandom2> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureRandom2> var0) {
      RandomSource var1 = var0.d();
      WorldGenFeatureRandom2 var2 = var0.f();
      GeneratorAccessSeed var3 = var0.b();
      BlockPosition var4 = var0.e();
      ChunkGenerator var5 = var0.c();
      int var6 = var1.a(var2.b.b());
      PlacedFeature var7 = var2.b.a(var6).a();
      return var7.a(var3, var5, var1, var4);
   }
}
