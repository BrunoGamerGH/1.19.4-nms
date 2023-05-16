package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureOreConfiguration;

public class ScatteredOreFeature extends WorldGenerator<WorldGenFeatureOreConfiguration> {
   private static final int a = 7;

   ScatteredOreFeature(Codec<WorldGenFeatureOreConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureOreConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      RandomSource var2 = var0.d();
      WorldGenFeatureOreConfiguration var3 = var0.f();
      BlockPosition var4 = var0.e();
      int var5 = var2.a(var3.c + 1);
      BlockPosition.MutableBlockPosition var6 = new BlockPosition.MutableBlockPosition();

      for(int var7 = 0; var7 < var5; ++var7) {
         this.a(var6, var2, var4, Math.min(var7, 7));
         IBlockData var8 = var1.a_(var6);

         for(WorldGenFeatureOreConfiguration.a var10 : var3.b) {
            if (WorldGenMinable.a(var8, var1::a_, var2, var3, var10, var6)) {
               var1.a(var6, var10.c, 2);
               break;
            }
         }
      }

      return true;
   }

   private void a(BlockPosition.MutableBlockPosition var0, RandomSource var1, BlockPosition var2, int var3) {
      int var4 = this.a(var1, var3);
      int var5 = this.a(var1, var3);
      int var6 = this.a(var1, var3);
      var0.a(var2, var4, var5, var6);
   }

   private int a(RandomSource var0, int var1) {
      return Math.round((var0.i() - var0.i()) * (float)var1);
   }
}
