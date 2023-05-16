package net.minecraft.world.level.block;

import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockTwistingVines extends BlockGrowingTop {
   public static final VoxelShape f = Block.a(4.0, 0.0, 4.0, 12.0, 15.0, 12.0);

   public BlockTwistingVines(BlockBase.Info var0) {
      super(var0, EnumDirection.b, f, false, 0.1);
   }

   @Override
   protected int a(RandomSource var0) {
      return BlockNetherVinesUtil.a(var0);
   }

   @Override
   protected Block b() {
      return Blocks.oy;
   }

   @Override
   protected boolean g(IBlockData var0) {
      return BlockNetherVinesUtil.a(var0);
   }
}
