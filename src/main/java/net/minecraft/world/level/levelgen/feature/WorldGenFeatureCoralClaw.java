package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.WorldGenFeatureEmptyConfiguration;

public class WorldGenFeatureCoralClaw extends WorldGenFeatureCoral {
   public WorldGenFeatureCoralClaw(Codec<WorldGenFeatureEmptyConfiguration> var0) {
      super(var0);
   }

   @Override
   protected boolean a(GeneratorAccess var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      if (!this.b(var0, var1, var2, var3)) {
         return false;
      } else {
         EnumDirection var4 = EnumDirection.EnumDirectionLimit.a.a(var1);
         int var5 = var1.a(2) + 2;
         List<EnumDirection> var6 = SystemUtils.a(Stream.of(var4, var4.h(), var4.i()), var1);

         for(EnumDirection var9 : var6.subList(0, var5)) {
            BlockPosition.MutableBlockPosition var10 = var2.j();
            int var11 = var1.a(2) + 1;
            var10.c(var9);
            int var12;
            EnumDirection var13;
            if (var9 == var4) {
               var13 = var4;
               var12 = var1.a(3) + 2;
            } else {
               var10.c(EnumDirection.b);
               EnumDirection[] var14 = new EnumDirection[]{var9, EnumDirection.b};
               var13 = SystemUtils.a(var14, var1);
               var12 = var1.a(3) + 3;
            }

            for(int var14 = 0; var14 < var11 && this.b(var0, var1, var10, var3); ++var14) {
               var10.c(var13);
            }

            var10.c(var13.g());
            var10.c(EnumDirection.b);

            for(int var14 = 0; var14 < var12; ++var14) {
               var10.c(var4);
               if (!this.b(var0, var1, var10, var3)) {
                  break;
               }

               if (var1.i() < 0.25F) {
                  var10.c(EnumDirection.b);
               }
            }
         }

         return true;
      }
   }
}
