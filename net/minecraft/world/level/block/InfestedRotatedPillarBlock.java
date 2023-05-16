package net.minecraft.world.level.block;

import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;

public class InfestedRotatedPillarBlock extends BlockMonsterEggs {
   public InfestedRotatedPillarBlock(Block var0, BlockBase.Info var1) {
      super(var0, var1);
      this.k(this.o().a(BlockRotatable.g, EnumDirection.EnumAxis.b));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return BlockRotatable.b(var0, var1);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(BlockRotatable.g);
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      return this.o().a(BlockRotatable.g, var0.k().o());
   }
}
