package net.minecraft.world.level.block;

import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockHalfTransparent extends Block {
   protected BlockHalfTransparent(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public boolean a(IBlockData var0, IBlockData var1, EnumDirection var2) {
      return var1.a(this) ? true : super.a(var0, var1, var2);
   }
}
