package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.Vec3D;

public class BlockWeb extends Block {
   public BlockWeb(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, Entity var3) {
      var3.a(var0, new Vec3D(0.25, 0.05F, 0.25));
   }
}
