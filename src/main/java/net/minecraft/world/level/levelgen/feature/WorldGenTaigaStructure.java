package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureLakeConfiguration;

public class WorldGenTaigaStructure extends WorldGenerator<WorldGenFeatureLakeConfiguration> {
   public WorldGenTaigaStructure(Codec<WorldGenFeatureLakeConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureLakeConfiguration> var0) {
      BlockPosition var1 = var0.e();
      GeneratorAccessSeed var2 = var0.b();
      RandomSource var3 = var0.d();

      WorldGenFeatureLakeConfiguration var4;
      for(var4 = var0.f(); var1.v() > var2.v_() + 3; var1 = var1.d()) {
         if (!var2.w(var1.d())) {
            IBlockData var5 = var2.a_(var1.d());
            if (b(var5) || a(var5)) {
               break;
            }
         }
      }

      if (var1.v() <= var2.v_() + 3) {
         return false;
      } else {
         for(int var5 = 0; var5 < 3; ++var5) {
            int var6 = var3.a(2);
            int var7 = var3.a(2);
            int var8 = var3.a(2);
            float var9 = (float)(var6 + var7 + var8) * 0.333F + 0.5F;

            for(BlockPosition var11 : BlockPosition.a(var1.b(-var6, -var7, -var8), var1.b(var6, var7, var8))) {
               if (var11.j(var1) <= (double)(var9 * var9)) {
                  var2.a(var11, var4.b, 3);
               }
            }

            var1 = var1.b(-1 + var3.a(2), -var3.a(2), -1 + var3.a(2));
         }

         return true;
      }
   }
}
