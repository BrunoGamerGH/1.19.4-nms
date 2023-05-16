package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public abstract class BlockGlassAbstract extends BlockHalfTransparent {
   protected BlockGlassAbstract(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public VoxelShape b(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return VoxelShapes.a();
   }

   @Override
   public float b(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return 1.0F;
   }

   @Override
   public boolean c(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return true;
   }
}
