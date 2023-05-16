package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IMaterial;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockCarrots extends BlockCrops {
   private static final VoxelShape[] a = new VoxelShape[]{
      Block.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 3.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 5.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 7.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 9.0, 16.0)
   };

   public BlockCarrots(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   protected IMaterial d() {
      return Items.th;
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return a[var0.c(this.b())];
   }
}
