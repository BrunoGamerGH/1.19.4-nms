package net.minecraft.world.level;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface ICombinedAccess extends IEntityAccess, IWorldReader, VirtualLevelWritable {
   @Override
   default <T extends TileEntity> Optional<T> a(BlockPosition var0, TileEntityTypes<T> var1) {
      return IWorldReader.super.a(var0, var1);
   }

   @Override
   default List<VoxelShape> b(@Nullable Entity var0, AxisAlignedBB var1) {
      return IEntityAccess.super.b(var0, var1);
   }

   @Override
   default boolean a(@Nullable Entity var0, VoxelShape var1) {
      return IEntityAccess.super.a(var0, var1);
   }

   @Override
   default BlockPosition a(HeightMap.Type var0, BlockPosition var1) {
      return IWorldReader.super.a(var0, var1);
   }
}
