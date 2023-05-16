package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureMushroomConfiguration;

public abstract class WorldGenMushrooms extends WorldGenerator<WorldGenFeatureMushroomConfiguration> {
   public WorldGenMushrooms(Codec<WorldGenFeatureMushroomConfiguration> var0) {
      super(var0);
   }

   protected void a(
      GeneratorAccess var0,
      RandomSource var1,
      BlockPosition var2,
      WorldGenFeatureMushroomConfiguration var3,
      int var4,
      BlockPosition.MutableBlockPosition var5
   ) {
      for(int var6 = 0; var6 < var4; ++var6) {
         var5.g(var2).c(EnumDirection.b, var6);
         if (!var0.a_(var5).i(var0, var5)) {
            this.a(var0, var5, var3.c.a(var1, var2));
         }
      }
   }

   protected int a(RandomSource var0) {
      int var1 = var0.a(3) + 4;
      if (var0.a(12) == 0) {
         var1 *= 2;
      }

      return var1;
   }

   protected boolean a(GeneratorAccess var0, BlockPosition var1, int var2, BlockPosition.MutableBlockPosition var3, WorldGenFeatureMushroomConfiguration var4) {
      int var5 = var1.v();
      if (var5 >= var0.v_() + 1 && var5 + var2 + 1 < var0.ai()) {
         IBlockData var6 = var0.a_(var1.d());
         if (!b(var6) && !var6.a(TagsBlock.aX)) {
            return false;
         } else {
            for(int var7 = 0; var7 <= var2; ++var7) {
               int var8 = this.a(-1, -1, var4.d, var7);

               for(int var9 = -var8; var9 <= var8; ++var9) {
                  for(int var10 = -var8; var10 <= var8; ++var10) {
                     IBlockData var11 = var0.a_(var3.a(var1, var9, var7, var10));
                     if (!var11.h() && !var11.a(TagsBlock.N)) {
                        return false;
                     }
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureMushroomConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      BlockPosition var2 = var0.e();
      RandomSource var3 = var0.d();
      WorldGenFeatureMushroomConfiguration var4 = var0.f();
      int var5 = this.a(var3);
      BlockPosition.MutableBlockPosition var6 = new BlockPosition.MutableBlockPosition();
      if (!this.a(var1, var2, var5, var6, var4)) {
         return false;
      } else {
         this.a(var1, var3, var2, var5, var6, var4);
         this.a(var1, var3, var2, var4, var5, var6);
         return true;
      }
   }

   protected abstract int a(int var1, int var2, int var3, int var4);

   protected abstract void a(
      GeneratorAccess var1,
      RandomSource var2,
      BlockPosition var3,
      int var4,
      BlockPosition.MutableBlockPosition var5,
      WorldGenFeatureMushroomConfiguration var6
   );
}
