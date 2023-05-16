package net.minecraft.core;

import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.state.IBlockData;

public interface ISourceBlock extends IPosition {
   @Override
   double a();

   @Override
   double b();

   @Override
   double c();

   BlockPosition d();

   IBlockData e();

   <T extends TileEntity> T f();

   WorldServer g();
}
