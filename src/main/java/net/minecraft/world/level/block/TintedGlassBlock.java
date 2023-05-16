package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class TintedGlassBlock extends BlockGlassAbstract {
   public TintedGlassBlock(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public boolean c(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return false;
   }

   @Override
   public int g(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return var1.L();
   }
}
