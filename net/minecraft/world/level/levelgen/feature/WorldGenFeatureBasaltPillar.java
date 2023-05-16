package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;

public class WorldGenFeatureBasaltPillar extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
   public WorldGenFeatureBasaltPillar(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureEmptyConfiguration> var0) {
      BlockPosition var1 = var0.e();
      GeneratorAccessSeed var2 = var0.b();
      RandomSource var3 = var0.d();
      if (var2.w(var1) && !var2.w(var1.c())) {
         BlockPosition.MutableBlockPosition var4 = var1.j();
         BlockPosition.MutableBlockPosition var5 = var1.j();
         boolean var6 = true;
         boolean var7 = true;
         boolean var8 = true;
         boolean var9 = true;

         while(var2.w(var4)) {
            if (var2.u(var4)) {
               return true;
            }

            var2.a(var4, Blocks.dY.o(), 2);
            var6 = var6 && this.b(var2, var3, var5.a(var4, EnumDirection.c));
            var7 = var7 && this.b(var2, var3, var5.a(var4, EnumDirection.d));
            var8 = var8 && this.b(var2, var3, var5.a(var4, EnumDirection.e));
            var9 = var9 && this.b(var2, var3, var5.a(var4, EnumDirection.f));
            var4.c(EnumDirection.a);
         }

         var4.c(EnumDirection.b);
         this.a(var2, var3, var5.a(var4, EnumDirection.c));
         this.a(var2, var3, var5.a(var4, EnumDirection.d));
         this.a(var2, var3, var5.a(var4, EnumDirection.e));
         this.a(var2, var3, var5.a(var4, EnumDirection.f));
         var4.c(EnumDirection.a);
         BlockPosition.MutableBlockPosition var10 = new BlockPosition.MutableBlockPosition();

         for(int var11 = -3; var11 < 4; ++var11) {
            for(int var12 = -3; var12 < 4; ++var12) {
               int var13 = MathHelper.a(var11) * MathHelper.a(var12);
               if (var3.a(10) < 10 - var13) {
                  var10.g(var4.b(var11, 0, var12));
                  int var14 = 3;

                  while(var2.w(var5.a(var10, EnumDirection.a))) {
                     var10.c(EnumDirection.a);
                     if (--var14 <= 0) {
                        break;
                     }
                  }

                  if (!var2.w(var5.a(var10, EnumDirection.a))) {
                     var2.a(var10, Blocks.dY.o(), 2);
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void a(GeneratorAccess var0, RandomSource var1, BlockPosition var2) {
      if (var1.h()) {
         var0.a(var2, Blocks.dY.o(), 2);
      }
   }

   private boolean b(GeneratorAccess var0, RandomSource var1, BlockPosition var2) {
      if (var1.a(10) != 0) {
         var0.a(var2, Blocks.dY.o(), 2);
         return true;
      } else {
         return false;
      }
   }
}
