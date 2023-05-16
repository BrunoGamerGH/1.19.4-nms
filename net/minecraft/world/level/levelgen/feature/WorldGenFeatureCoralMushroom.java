package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;

public class WorldGenFeatureCoralMushroom extends WorldGenFeatureCoral {
   public WorldGenFeatureCoralMushroom(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   @Override
   protected boolean a(GeneratorAccess var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      int var4 = var1.a(3) + 3;
      int var5 = var1.a(3) + 3;
      int var6 = var1.a(3) + 3;
      int var7 = var1.a(3) + 1;
      BlockPosition.MutableBlockPosition var8 = var2.j();

      for(int var9 = 0; var9 <= var5; ++var9) {
         for(int var10 = 0; var10 <= var4; ++var10) {
            for(int var11 = 0; var11 <= var6; ++var11) {
               var8.d(var9 + var2.u(), var10 + var2.v(), var11 + var2.w());
               var8.c(EnumDirection.a, var7);
               if ((var9 != 0 && var9 != var5 || var10 != 0 && var10 != var4)
                  && (var11 != 0 && var11 != var6 || var10 != 0 && var10 != var4)
                  && (var9 != 0 && var9 != var5 || var11 != 0 && var11 != var6)
                  && (var9 == 0 || var9 == var5 || var10 == 0 || var10 == var4 || var11 == 0 || var11 == var6)
                  && !(var1.i() < 0.1F)
                  && !this.b(var0, var1, var8, var3)) {
               }
            }
         }
      }

      return true;
   }
}
