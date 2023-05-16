package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockSand extends BlockFalling {
   private final int a;

   public BlockSand(int var0, BlockBase.Info var1) {
      super(var1);
      this.a = var0;
   }

   @Override
   public int d(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return this.a;
   }
}
