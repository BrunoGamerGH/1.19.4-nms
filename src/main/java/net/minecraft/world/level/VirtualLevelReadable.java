package net.minecraft.world.level;

import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.material.Fluid;

public interface VirtualLevelReadable {
   boolean a(BlockPosition var1, Predicate<IBlockData> var2);

   boolean b(BlockPosition var1, Predicate<Fluid> var2);

   <T extends TileEntity> Optional<T> a(BlockPosition var1, TileEntityTypes<T> var2);

   BlockPosition a(HeightMap.Type var1, BlockPosition var2);
}
