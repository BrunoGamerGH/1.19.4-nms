package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class RodBlock extends BlockDirectional {
   protected static final float e = 6.0F;
   protected static final float f = 10.0F;
   protected static final VoxelShape g = Block.a(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
   protected static final VoxelShape h = Block.a(6.0, 6.0, 0.0, 10.0, 10.0, 16.0);
   protected static final VoxelShape i = Block.a(0.0, 6.0, 6.0, 16.0, 10.0, 10.0);

   protected RodBlock(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      switch(var0.c(a).o()) {
         case a:
         default:
            return i;
         case c:
            return h;
         case b:
            return g;
      }
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(a, var1.a(var0.c(a)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(a, var1.b(var0.c(a)));
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }
}
