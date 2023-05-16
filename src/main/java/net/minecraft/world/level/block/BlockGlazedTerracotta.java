package net.minecraft.world.level.block;

import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.EnumPistonReaction;

public class BlockGlazedTerracotta extends BlockFacingHorizontal {
   public BlockGlazedTerracotta(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(aD);
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      return this.o().a(aD, var0.g().g());
   }

   @Override
   public EnumPistonReaction d(IBlockData var0) {
      return EnumPistonReaction.e;
   }
}
