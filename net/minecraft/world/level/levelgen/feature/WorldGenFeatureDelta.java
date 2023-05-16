package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureDeltaConfiguration;

public class WorldGenFeatureDelta extends WorldGenerator<WorldGenFeatureDeltaConfiguration> {
   private static final ImmutableList<Block> a = ImmutableList.of(Blocks.F, Blocks.fm, Blocks.fn, Blocks.fo, Blocks.fp, Blocks.cu, Blocks.cs);
   private static final EnumDirection[] b = EnumDirection.values();
   private static final double c = 0.9;

   public WorldGenFeatureDelta(Codec<WorldGenFeatureDeltaConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<WorldGenFeatureDeltaConfiguration> var0) {
      boolean var1 = false;
      RandomSource var2 = var0.d();
      GeneratorAccessSeed var3 = var0.b();
      WorldGenFeatureDeltaConfiguration var4 = var0.f();
      BlockPosition var5 = var0.e();
      boolean var6 = var2.j() < 0.9;
      int var7 = var6 ? var4.d().a(var2) : 0;
      int var8 = var6 ? var4.d().a(var2) : 0;
      boolean var9 = var6 && var7 != 0 && var8 != 0;
      int var10 = var4.c().a(var2);
      int var11 = var4.c().a(var2);
      int var12 = Math.max(var10, var11);

      for(BlockPosition var14 : BlockPosition.a(var5, var10, 0, var11)) {
         if (var14.k(var5) > var12) {
            break;
         }

         if (a(var3, var14, var4)) {
            if (var9) {
               var1 = true;
               this.a(var3, var14, var4.b());
            }

            BlockPosition var15 = var14.b(var7, 0, var8);
            if (a(var3, var15, var4)) {
               var1 = true;
               this.a(var3, var15, var4.a());
            }
         }
      }

      return var1;
   }

   private static boolean a(GeneratorAccess var0, BlockPosition var1, WorldGenFeatureDeltaConfiguration var2) {
      IBlockData var3 = var0.a_(var1);
      if (var3.a(var2.a().b())) {
         return false;
      } else if (a.contains(var3.b())) {
         return false;
      } else {
         for(EnumDirection var7 : b) {
            boolean var8 = var0.a_(var1.a(var7)).h();
            if (var8 && var7 != EnumDirection.b || !var8 && var7 == EnumDirection.b) {
               return false;
            }
         }

         return true;
      }
   }
}
