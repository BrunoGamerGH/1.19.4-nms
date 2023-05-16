package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;

public class WorldGenFeatureCoralTree extends WorldGenFeatureCoral {
   public WorldGenFeatureCoralTree(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   @Override
   protected boolean a(GeneratorAccess var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      BlockPosition.MutableBlockPosition var4 = var2.j();
      int var5 = var1.a(3) + 1;

      for(int var6 = 0; var6 < var5; ++var6) {
         if (!this.b(var0, var1, var4, var3)) {
            return true;
         }

         var4.c(EnumDirection.b);
      }

      BlockPosition var6 = var4.i();
      int var7 = var1.a(3) + 2;
      List<EnumDirection> var8 = EnumDirection.EnumDirectionLimit.a.c(var1);

      for(EnumDirection var11 : var8.subList(0, var7)) {
         var4.g(var6);
         var4.c(var11);
         int var12 = var1.a(5) + 2;
         int var13 = 0;

         for(int var14 = 0; var14 < var12 && this.b(var0, var1, var4, var3); ++var14) {
            ++var13;
            var4.c(EnumDirection.b);
            if (var14 == 0 || var13 >= 2 && var1.i() < 0.25F) {
               var4.c(var11);
               var13 = 0;
            }
         }
      }

      return true;
   }
}
