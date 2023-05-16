package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockWetSponge extends Block {
   protected BlockWetSponge(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public void b(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      if (var1.q_().i()) {
         var1.a(var2, Blocks.aN.o(), 3);
         var1.c(2009, var2, 0);
         var1.a(null, var2, SoundEffects.hJ, SoundCategory.e, 1.0F, (1.0F + var1.r_().i() * 0.2F) * 0.7F);
      }
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      EnumDirection var4 = EnumDirection.b(var3);
      if (var4 != EnumDirection.b) {
         BlockPosition var5 = var2.a(var4);
         IBlockData var6 = var1.a_(var5);
         if (!var0.m() || !var6.d(var1, var5, var4.g())) {
            double var7 = (double)var2.u();
            double var9 = (double)var2.v();
            double var11 = (double)var2.w();
            if (var4 == EnumDirection.a) {
               var9 -= 0.05;
               var7 += var3.j();
               var11 += var3.j();
            } else {
               var9 += var3.j() * 0.8;
               if (var4.o() == EnumDirection.EnumAxis.a) {
                  var11 += var3.j();
                  if (var4 == EnumDirection.f) {
                     ++var7;
                  } else {
                     var7 += 0.05;
                  }
               } else {
                  var7 += var3.j();
                  if (var4 == EnumDirection.d) {
                     ++var11;
                  } else {
                     var11 += 0.05;
                  }
               }
            }

            var1.a(Particles.m, var7, var9, var11, 0.0, 0.0, 0.0);
         }
      }
   }
}
