package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockBarrier extends Block {
   protected BlockBarrier(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public boolean c(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return true;
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.a;
   }

   @Override
   public float b(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return 1.0F;
   }
}
