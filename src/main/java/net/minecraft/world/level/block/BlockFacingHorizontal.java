package net.minecraft.world.level.block;

import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;

public abstract class BlockFacingHorizontal extends Block {
   public static final BlockStateDirection aD = BlockProperties.R;

   protected BlockFacingHorizontal(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(aD, var1.a(var0.c(aD)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(var1.a(var0.c(aD)));
   }
}
