package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockPowered extends Block {
   public BlockPowered(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public boolean f_(IBlockData var0) {
      return true;
   }

   @Override
   public int a(IBlockData var0, IBlockAccess var1, BlockPosition var2, EnumDirection var3) {
      return 15;
   }
}
