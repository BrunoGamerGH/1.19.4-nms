package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.NetherForestVegetationConfig;

public class WorldGenFeatureNetherForestVegetation extends WorldGenerator<NetherForestVegetationConfig> {
   public WorldGenFeatureNetherForestVegetation(Codec<NetherForestVegetationConfig> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<NetherForestVegetationConfig> var0) {
      GeneratorAccessSeed var1 = var0.b();
      BlockPosition var2 = var0.e();
      IBlockData var3 = var1.a_(var2.d());
      NetherForestVegetationConfig var4 = var0.f();
      RandomSource var5 = var0.d();
      if (!var3.a(TagsBlock.aI)) {
         return false;
      } else {
         int var6 = var2.v();
         if (var6 >= var1.v_() + 1 && var6 + 1 < var1.ai()) {
            int var7 = 0;

            for(int var8 = 0; var8 < var4.d * var4.d; ++var8) {
               BlockPosition var9 = var2.b(var5.a(var4.d) - var5.a(var4.d), var5.a(var4.e) - var5.a(var4.e), var5.a(var4.d) - var5.a(var4.d));
               IBlockData var10 = var4.b.a(var5, var9);
               if (var1.w(var9) && var9.v() > var1.v_() && var10.a(var1, var9)) {
                  var1.a(var9, var10, 2);
                  ++var7;
               }
            }

            return var7 > 0;
         } else {
            return false;
         }
      }
   }
}
