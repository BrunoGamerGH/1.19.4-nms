package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockCryingObsidian extends Block {
   public BlockCryingObsidian(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      if (var3.a(5) == 0) {
         EnumDirection var4 = EnumDirection.b(var3);
         if (var4 != EnumDirection.b) {
            BlockPosition var5 = var2.a(var4);
            IBlockData var6 = var1.a_(var5);
            if (!var0.m() || !var6.d(var1, var5, var4.g())) {
               double var7 = var4.j() == 0 ? var3.j() : 0.5 + (double)var4.j() * 0.6;
               double var9 = var4.k() == 0 ? var3.j() : 0.5 + (double)var4.k() * 0.6;
               double var11 = var4.l() == 0 ? var3.j() : 0.5 + (double)var4.l() * 0.6;
               var1.a(Particles.aA, (double)var2.u() + var7, (double)var2.v() + var9, (double)var2.w() + var11, 0.0, 0.0, 0.0);
            }
         }
      }
   }
}
