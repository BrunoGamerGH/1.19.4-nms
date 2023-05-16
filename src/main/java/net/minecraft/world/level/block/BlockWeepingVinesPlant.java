package net.minecraft.world.level.block;

import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockWeepingVinesPlant extends BlockGrowingStem {
   public static final VoxelShape d = Block.a(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   public BlockWeepingVinesPlant(BlockBase.Info var0) {
      super(var0, EnumDirection.a, d, false);
   }

   @Override
   protected BlockGrowingTop c() {
      return (BlockGrowingTop)Blocks.ov;
   }
}
