package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.configurations.SculkPatchConfiguration;

public class SculkPatchFeature extends WorldGenerator<SculkPatchConfiguration> {
   public SculkPatchFeature(Codec<SculkPatchConfiguration> var0) {
      super(var0);
   }

   @Override
   public boolean a(FeaturePlaceContext<SculkPatchConfiguration> var0) {
      GeneratorAccessSeed var1 = var0.b();
      BlockPosition var2 = var0.e();
      if (!this.a(var1, var2)) {
         return false;
      } else {
         SculkPatchConfiguration var3 = var0.f();
         RandomSource var4 = var0.d();
         SculkSpreader var5 = SculkSpreader.b();
         int var6 = var3.f() + var3.d();

         for(int var7 = 0; var7 < var6; ++var7) {
            for(int var8 = 0; var8 < var3.a(); ++var8) {
               var5.a(var2, var3.b());
            }

            boolean var8 = var7 < var3.f();

            for(int var9 = 0; var9 < var3.c(); ++var9) {
               var5.a(var1, var2, var4, var8);
            }

            var5.j();
         }

         BlockPosition var7 = var2.d();
         if (var4.i() <= var3.h() && var1.a_(var7).r(var1, var7)) {
            var1.a(var2, Blocks.qC.o(), 3);
         }

         int var8 = var3.g().a(var4);

         for(int var9 = 0; var9 < var8; ++var9) {
            BlockPosition var10 = var2.b(var4.a(5) - 2, 0, var4.a(5) - 2);
            if (var1.a_(var10).h() && var1.a_(var10.d()).d(var1, var10.d(), EnumDirection.b)) {
               var1.a(var10, Blocks.qD.o().a(SculkShriekerBlock.c, Boolean.valueOf(true)), 3);
            }
         }

         return true;
      }
   }

   private boolean a(GeneratorAccess var0, BlockPosition var1) {
      IBlockData var2 = var0.a_(var1);
      if (var2.b() instanceof SculkBehaviour) {
         return true;
      } else {
         return !var2.h() && (!var2.a(Blocks.G) || !var2.r().b()) ? false : EnumDirection.a().map(var1::a).anyMatch(var1x -> var0.a_(var1x).r(var0, var1x));
      }
   }
}
