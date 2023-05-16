package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockStructureVoid extends Block {
   private static final double a = 5.0;
   private static final VoxelShape b = Block.a(5.0, 5.0, 5.0, 11.0, 11.0, 11.0);

   protected BlockStructureVoid(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.a;
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return b;
   }

   @Override
   public float b(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return 1.0F;
   }

   @Override
   public EnumPistonReaction d(IBlockData var0) {
      return EnumPistonReaction.b;
   }
}
