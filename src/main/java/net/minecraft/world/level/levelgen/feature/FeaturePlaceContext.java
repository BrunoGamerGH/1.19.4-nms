package net.minecraft.world.level.levelgen.feature;

import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureConfiguration;

public class FeaturePlaceContext<FC extends WorldGenFeatureConfiguration> {
   private final Optional<WorldGenFeatureConfigured<?, ?>> a;
   private final GeneratorAccessSeed b;
   private final ChunkGenerator c;
   private final RandomSource d;
   private final BlockPosition e;
   private final FC f;

   public FeaturePlaceContext(
      Optional<WorldGenFeatureConfigured<?, ?>> var0, GeneratorAccessSeed var1, ChunkGenerator var2, RandomSource var3, BlockPosition var4, FC var5
   ) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
   }

   public Optional<WorldGenFeatureConfigured<?, ?>> a() {
      return this.a;
   }

   public GeneratorAccessSeed b() {
      return this.b;
   }

   public ChunkGenerator c() {
      return this.c;
   }

   public RandomSource d() {
      return this.d;
   }

   public BlockPosition e() {
      return this.e;
   }

   public FC f() {
      return this.f;
   }
}
