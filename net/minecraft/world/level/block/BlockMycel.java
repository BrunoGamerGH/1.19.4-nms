package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockMycel extends BlockDirtSnowSpreadable {
   public BlockMycel(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      super.a(var0, var1, var2, var3);
      if (var3.a(10) == 0) {
         var1.a(Particles.W, (double)var2.u() + var3.j(), (double)var2.v() + 1.1, (double)var2.w() + var3.j(), 0.0, 0.0, 0.0);
      }
   }
}
