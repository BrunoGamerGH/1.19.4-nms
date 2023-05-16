package net.minecraft.world.level.block;

import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockTwistingVinesPlant extends BlockGrowingStem {
   public static final VoxelShape d = Block.a(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

   public BlockTwistingVinesPlant(BlockBase.Info var0) {
      super(var0, EnumDirection.b, d, false);
   }

   @Override
   protected BlockGrowingTop c() {
      return (BlockGrowingTop)Blocks.ox;
   }
}
