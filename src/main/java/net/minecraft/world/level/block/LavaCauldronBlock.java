package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class LavaCauldronBlock extends AbstractCauldronBlock {
   public LavaCauldronBlock(BlockBase.Info var0) {
      super(var0, CauldronInteraction.c);
   }

   @Override
   protected double a(IBlockData var0) {
      return 0.9375;
   }

   @Override
   public boolean c(IBlockData var0) {
      return true;
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, Entity var3) {
      if (this.a(var0, var2, var3)) {
         var3.at();
      }
   }

   @Override
   public int a(IBlockData var0, World var1, BlockPosition var2) {
      return 3;
   }
}
