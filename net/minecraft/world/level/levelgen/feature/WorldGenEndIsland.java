package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;

public class WorldGenEndIsland extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
   public WorldGenEndIsland(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureEmptyConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      RandomSource var2 = var0.d();
      BlockPosition var3 = var0.e();
      float var4 = (float)var2.a(3) + 4.0F;

      for(int var5 = 0; var4 > 0.5F; --var5) {
         for(int var6 = MathHelper.d(-var4); var6 <= MathHelper.f(var4); ++var6) {
            for(int var7 = MathHelper.d(-var4); var7 <= MathHelper.f(var4); ++var7) {
               if ((float)(var6 * var6 + var7 * var7) <= (var4 + 1.0F) * (var4 + 1.0F)) {
                  this.a(var1, var3.b(var6, var5, var7), Blocks.fy.o());
               }
            }
         }

         var4 -= (float)var2.a(2) + 0.5F;
      }

      return true;
   }
}
