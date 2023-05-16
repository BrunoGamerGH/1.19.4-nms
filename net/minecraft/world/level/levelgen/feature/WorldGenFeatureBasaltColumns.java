package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureBasaltColumnsConfiguration;

public class WorldGenFeatureBasaltColumns extends WorldGenerator<WorldGenFeatureBasaltColumnsConfiguration> {
   private static final ImmutableList<Block> a = ImmutableList.of(
      Blocks.H, Blocks.F, Blocks.kG, Blocks.dW, Blocks.fm, Blocks.fn, Blocks.fo, Blocks.fp, Blocks.cu, Blocks.cs
   );
   private static final int b = 5;
   private static final int c = 50;
   private static final int d = 8;
   private static final int e = 15;

   public WorldGenFeatureBasaltColumns(Codec<WorldGenFeatureBasaltColumnsConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureBasaltColumnsConfiguration> var0) {
      int var1 = var0.c().e();
      BlockPosition var2 = var0.e();
      GeneratorAccessSeed var3 = var0.b();
      RandomSource var4 = var0.d();
      WorldGenFeatureBasaltColumnsConfiguration var5 = var0.f();
      if (!a(var3, var1, var2.j())) {
         return false;
      } else {
         int var6 = var5.b().a(var4);
         boolean var7 = var4.i() < 0.9F;
         int var8 = Math.min(var6, var7 ? 5 : 8);
         int var9 = var7 ? 50 : 15;
         boolean var10 = false;

         for(BlockPosition var12 : BlockPosition.a(var4, var9, var2.u() - var8, var2.v(), var2.w() - var8, var2.u() + var8, var2.v(), var2.w() + var8)) {
            int var13 = var6 - var12.k(var2);
            if (var13 >= 0) {
               var10 |= this.a(var3, var1, var12, var13, var5.a().a(var4));
            }
         }

         return var10;
      }
   }

   private boolean a(GeneratorAccess var0, int var1, BlockPosition var2, int var3, int var4) {
      boolean var5 = false;

      for(BlockPosition var7 : BlockPosition.b(var2.u() - var4, var2.v(), var2.w() - var4, var2.u() + var4, var2.v(), var2.w() + var4)) {
         int var8 = var7.k(var2);
         BlockPosition var9 = a(var0, var1, var7) ? a(var0, var1, var7.j(), var8) : a(var0, var7.j(), var8);
         if (var9 != null) {
            int var10 = var3 - var8 / 2;

            for(BlockPosition.MutableBlockPosition var11 = var9.j(); var10 >= 0; --var10) {
               if (a(var0, var1, (BlockPosition)var11)) {
                  this.a(var0, var11, Blocks.dY.o());
                  var11.c(EnumDirection.b);
                  var5 = true;
               } else {
                  if (!var0.a_(var11).a(Blocks.dY)) {
                     break;
                  }

                  var11.c(EnumDirection.b);
               }
            }
         }
      }

      return var5;
   }

   @Nullable
   private static BlockPosition a(GeneratorAccess var0, int var1, BlockPosition.MutableBlockPosition var2, int var3) {
      while(var2.v() > var0.v_() + 1 && var3 > 0) {
         --var3;
         if (a(var0, var1, var2)) {
            return var2;
         }

         var2.c(EnumDirection.a);
      }

      return null;
   }

   private static boolean a(GeneratorAccess var0, int var1, BlockPosition.MutableBlockPosition var2) {
      if (!a(var0, var1, (BlockPosition)var2)) {
         return false;
      } else {
         IBlockData var3 = var0.a_(var2.c(EnumDirection.a));
         var2.c(EnumDirection.b);
         return !var3.h() && !a.contains(var3.b());
      }
   }

   @Nullable
   private static BlockPosition a(GeneratorAccess var0, BlockPosition.MutableBlockPosition var1, int var2) {
      while(var1.v() < var0.ai() && var2 > 0) {
         --var2;
         IBlockData var3 = var0.a_(var1);
         if (a.contains(var3.b())) {
            return null;
         }

         if (var3.h()) {
            return var1;
         }

         var1.c(EnumDirection.b);
      }

      return null;
   }

   private static boolean a(GeneratorAccess var0, int var1, BlockPosition var2) {
      IBlockData var3 = var0.a_(var2);
      return var3.h() || var3.a(Blocks.H) && var2.v() <= var1;
   }
}
