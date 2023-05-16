package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.block.state.IBlockData;

public class TileEntityEnderPortal extends TileEntity {
   protected TileEntityEnderPortal(TileEntityTypes<?> var0, BlockPosition var1, IBlockData var2) {
      super(var0, var1, var2);
   }

   public TileEntityEnderPortal(BlockPosition var0, IBlockData var1) {
      this(TileEntityTypes.n, var0, var1);
   }

   public boolean a(EnumDirection var0) {
      return var0.o() == EnumDirection.EnumAxis.b;
   }
}
