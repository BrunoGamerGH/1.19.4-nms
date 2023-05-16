package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockHay extends BlockRotatable {
   public BlockHay(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(g, EnumDirection.EnumAxis.b));
   }

   @Override
   public void a(World var0, IBlockData var1, BlockPosition var2, Entity var3, float var4) {
      var3.a(var4, 0.2F, var0.af().k());
   }
}
