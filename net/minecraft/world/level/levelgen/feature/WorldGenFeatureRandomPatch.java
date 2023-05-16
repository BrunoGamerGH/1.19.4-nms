package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureRandomPatchConfiguration;

public class WorldGenFeatureRandomPatch extends WorldGenerator<WorldGenFeatureRandomPatchConfiguration> {
   public WorldGenFeatureRandomPatch(Codec<WorldGenFeatureRandomPatchConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureRandomPatchConfiguration> var0) {
      WorldGenFeatureRandomPatchConfiguration var1 = var0.f();
      RandomSource var2 = var0.d();
      BlockPosition var3 = var0.e();
      GeneratorAccessSeed var4 = var0.b();
      int var5 = 0;
      BlockPosition.MutableBlockPosition var6 = new BlockPosition.MutableBlockPosition();
      int var7 = var1.b() + 1;
      int var8 = var1.c() + 1;

      for(int var9 = 0; var9 < var1.a(); ++var9) {
         var6.a(var3, var2.a(var7) - var2.a(var7), var2.a(var8) - var2.a(var8), var2.a(var7) - var2.a(var7));
         if (var1.d().a().a(var4, var0.c(), var2, var6)) {
            ++var5;
         }
      }

      return var5 > 0;
   }
}
