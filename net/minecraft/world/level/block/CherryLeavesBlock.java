package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class CherryLeavesBlock extends BlockLeaves {
   public CherryLeavesBlock(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      super.a(var0, var1, var2, var3);
      if (var3.a(15) == 0) {
         BlockPosition var4 = var2.d();
         IBlockData var5 = var1.a_(var4);
         if (!var5.m() || !var5.d(var1, var4, EnumDirection.b)) {
            ParticleUtils.a(var1, var2, var3, Particles.D);
         }
      }
   }
}
