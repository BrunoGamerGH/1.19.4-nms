package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockGravel extends BlockFalling {
   public BlockGravel(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public int d(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return -8356741;
   }
}
