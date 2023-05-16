package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureFillConfiguration;

public class WorldGenFeatureFill extends WorldGenerator<WorldGenFeatureFillConfiguration> {
   public WorldGenFeatureFill(Codec<WorldGenFeatureFillConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureFillConfiguration> var0) {
      BlockPosition var1 = var0.e();
      WorldGenFeatureFillConfiguration var2 = var0.f();
      GeneratorAccessSeed var3 = var0.b();
      BlockPosition.MutableBlockPosition var4 = new BlockPosition.MutableBlockPosition();

      for(int var5 = 0; var5 < 16; ++var5) {
         for(int var6 = 0; var6 < 16; ++var6) {
            int var7 = var1.u() + var5;
            int var8 = var1.w() + var6;
            int var9 = var3.v_() + var2.b;
            var4.d(var7, var9, var8);
            if (var3.a_(var4).h()) {
               var3.a(var4, var2.c, 2);
            }
         }
      }

      return true;
   }
}
