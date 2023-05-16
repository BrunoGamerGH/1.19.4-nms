package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.BlockHugeMushroom;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureMushroomConfiguration;

public class WorldGenHugeMushroomRed extends WorldGenMushrooms {
   public WorldGenHugeMushroomRed(Codec<WorldGenFeatureMushroomConfiguration> var0) {
      super(var0);
   }

   @Override
   protected void a(
      GeneratorAccess var0,
      RandomSource var1,
      BlockPosition var2,
      int var3,
      BlockPosition.MutableBlockPosition var4,
      WorldGenFeatureMushroomConfiguration var5
   ) {
      for(int var6 = var3 - 3; var6 <= var3; ++var6) {
         int var7 = var6 < var3 ? var5.d : var5.d - 1;
         int var8 = var5.d - 2;

         for(int var9 = -var7; var9 <= var7; ++var9) {
            for(int var10 = -var7; var10 <= var7; ++var10) {
               boolean var11 = var9 == -var7;
               boolean var12 = var9 == var7;
               boolean var13 = var10 == -var7;
               boolean var14 = var10 == var7;
               boolean var15 = var11 || var12;
               boolean var16 = var13 || var14;
               if (var6 >= var3 || var15 != var16) {
                  var4.a(var2, var9, var6, var10);
                  if (!var0.a_(var4).i(var0, var4)) {
                     IBlockData var17 = var5.b.a(var1, var2);
                     if (var17.b(BlockHugeMushroom.d)
                        && var17.b(BlockHugeMushroom.b)
                        && var17.b(BlockHugeMushroom.a)
                        && var17.b(BlockHugeMushroom.c)
                        && var17.b(BlockHugeMushroom.e)) {
                        var17 = var17.a(BlockHugeMushroom.e, Boolean.valueOf(var6 >= var3 - 1))
                           .a(BlockHugeMushroom.d, Boolean.valueOf(var9 < -var8))
                           .a(BlockHugeMushroom.b, Boolean.valueOf(var9 > var8))
                           .a(BlockHugeMushroom.a, Boolean.valueOf(var10 < -var8))
                           .a(BlockHugeMushroom.c, Boolean.valueOf(var10 > var8));
                     }

                     this.a(var0, var4, var17);
                  }
               }
            }
         }
      }
   }

   @Override
   protected int a(int var0, int var1, int var2, int var3) {
      int var4 = 0;
      if (var3 < var1 && var3 >= var1 - 3) {
         var4 = var2;
      } else if (var3 == var1) {
         var4 = var2;
      }

      return var4;
   }
}
