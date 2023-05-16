package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;

public class BlockColumnFeature extends WorldGenerator<BlockColumnConfiguration> {
   public BlockColumnFeature(Codec<BlockColumnConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<BlockColumnConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      BlockColumnConfiguration var2 = var0.f();
      RandomSource var3 = var0.d();
      int var4 = var2.a().size();
      int[] var5 = new int[var4];
      int var6 = 0;

      for(int var7 = 0; var7 < var4; ++var7) {
         var5[var7] = var2.a().get(var7).a().a(var3);
         var6 += var5[var7];
      }

      if (var6 == 0) {
         return false;
      } else {
         BlockPosition.MutableBlockPosition var7 = var0.e().j();
         BlockPosition.MutableBlockPosition var8 = var7.j().c(var2.b());

         for(int var9 = 0; var9 < var6; ++var9) {
            if (!var2.c().test(var1, var8)) {
               a(var5, var6, var9, var2.d());
               break;
            }

            var8.c(var2.b());
         }

         for(int var9 = 0; var9 < var4; ++var9) {
            int var10 = var5[var9];
            if (var10 != 0) {
               BlockColumnConfiguration.a var11 = var2.a().get(var9);

               for(int var12 = 0; var12 < var10; ++var12) {
                  var1.a(var7, var11.b().a(var3, var7), 2);
                  var7.c(var2.b());
               }
            }
         }

         return true;
      }
   }

   private static void a(int[] var0, int var1, int var2, boolean var3) {
      int var4 = var1 - var2;
      int var5 = var3 ? 1 : -1;
      int var6 = var3 ? 0 : var0.length - 1;
      int var7 = var3 ? var0.length : -1;

      for(int var8 = var6; var8 != var7 && var4 > 0; var8 += var5) {
         int var9 = var0[var8];
         int var10 = Math.min(var9, var4);
         var4 -= var10;
         var0[var8] -= var10;
      }
   }
}
