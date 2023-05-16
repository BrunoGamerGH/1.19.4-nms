package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.BlockHugeMushroom;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureMushroomConfiguration;

public class WorldGenHugeMushroomBrown extends WorldGenMushrooms {
   public WorldGenHugeMushroomBrown(Codec<WorldGenFeatureMushroomConfiguration> var0) {
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
      int var6 = var5.d;

      for(int var7 = -var6; var7 <= var6; ++var7) {
         for(int var8 = -var6; var8 <= var6; ++var8) {
            boolean var9 = var7 == -var6;
            boolean var10 = var7 == var6;
            boolean var11 = var8 == -var6;
            boolean var12 = var8 == var6;
            boolean var13 = var9 || var10;
            boolean var14 = var11 || var12;
            if (!var13 || !var14) {
               var4.a(var2, var7, var3, var8);
               if (!var0.a_(var4).i(var0, var4)) {
                  boolean var15 = var9 || var14 && var7 == 1 - var6;
                  boolean var16 = var10 || var14 && var7 == var6 - 1;
                  boolean var17 = var11 || var13 && var8 == 1 - var6;
                  boolean var18 = var12 || var13 && var8 == var6 - 1;
                  IBlockData var19 = var5.b.a(var1, var2);
                  if (var19.b(BlockHugeMushroom.d) && var19.b(BlockHugeMushroom.b) && var19.b(BlockHugeMushroom.a) && var19.b(BlockHugeMushroom.c)) {
                     var19 = var19.a(BlockHugeMushroom.d, Boolean.valueOf(var15))
                        .a(BlockHugeMushroom.b, Boolean.valueOf(var16))
                        .a(BlockHugeMushroom.a, Boolean.valueOf(var17))
                        .a(BlockHugeMushroom.c, Boolean.valueOf(var18));
                  }

                  this.a(var0, var4, var19);
               }
            }
         }
      }
   }

   @Override
   protected int a(int var0, int var1, int var2, int var3) {
      return var3 <= 3 ? 0 : var2;
   }
}
