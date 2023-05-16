package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureHellFlowingLavaConfiguration;

public class WorldGenLiquids extends WorldGenerator<WorldGenFeatureHellFlowingLavaConfiguration> {
   public WorldGenLiquids(Codec<WorldGenFeatureHellFlowingLavaConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureHellFlowingLavaConfiguration> var0) {
      WorldGenFeatureHellFlowingLavaConfiguration var1 = var0.f();
      GeneratorAccessSeed var2 = var0.b();
      BlockPosition var3 = var0.e();
      if (!var2.a_(var3.c()).a(var1.f)) {
         return false;
      } else if (var1.c && !var2.a_(var3.d()).a(var1.f)) {
         return false;
      } else {
         IBlockData var4 = var2.a_(var3);
         if (!var4.h() && !var4.a(var1.f)) {
            return false;
         } else {
            int var5 = 0;
            int var6 = 0;
            if (var2.a_(var3.g()).a(var1.f)) {
               ++var6;
            }

            if (var2.a_(var3.h()).a(var1.f)) {
               ++var6;
            }

            if (var2.a_(var3.e()).a(var1.f)) {
               ++var6;
            }

            if (var2.a_(var3.f()).a(var1.f)) {
               ++var6;
            }

            if (var2.a_(var3.d()).a(var1.f)) {
               ++var6;
            }

            int var7 = 0;
            if (var2.w(var3.g())) {
               ++var7;
            }

            if (var2.w(var3.h())) {
               ++var7;
            }

            if (var2.w(var3.e())) {
               ++var7;
            }

            if (var2.w(var3.f())) {
               ++var7;
            }

            if (var2.w(var3.d())) {
               ++var7;
            }

            if (var6 == var1.d && var7 == var1.e) {
               var2.a(var3, var1.b.g(), 2);
               var2.a(var3, var1.b.a(), 0);
               ++var5;
            }

            return var5 > 0;
         }
      }
   }
}
