package net.minecraft.world.level.block.entity;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.block.state.IBlockData;

public class HangingSignBlockEntity extends TileEntitySign {
   private static final int b = 50;
   private static final int c = 9;

   public HangingSignBlockEntity(BlockPosition var0, IBlockData var1) {
      super(TileEntityTypes.i, var0, var1);
   }

   @Override
   public int c() {
      return 9;
   }

   @Override
   public int d() {
      return 50;
   }
}
