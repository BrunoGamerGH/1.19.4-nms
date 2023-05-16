package net.minecraft.world.level;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;

public enum BlockAccessAir implements IBlockAccess {
   a;

   @Nullable
   @Override
   public TileEntity c_(BlockPosition var0) {
      return null;
   }

   @Override
   public IBlockData a_(BlockPosition var0) {
      return Blocks.a.o();
   }

   @Override
   public Fluid b_(BlockPosition var0) {
      return FluidTypes.a.g();
   }

   @Override
   public int v_() {
      return 0;
   }

   @Override
   public int w_() {
      return 0;
   }
}
