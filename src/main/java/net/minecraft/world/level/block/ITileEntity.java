package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.gameevent.GameEventListener;

public interface ITileEntity {
   @Nullable
   TileEntity a(BlockPosition var1, IBlockData var2);

   @Nullable
   default <T extends TileEntity> BlockEntityTicker<T> a(World var0, IBlockData var1, TileEntityTypes<T> var2) {
      return null;
   }

   @Nullable
   default <T extends TileEntity> GameEventListener a(WorldServer var0, T var1) {
      return null;
   }
}
