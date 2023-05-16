package net.minecraft.world.level.block;

import com.google.common.cache.CacheLoader;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;

class Block$1 extends CacheLoader<VoxelShape, Boolean> {
   public Boolean a(VoxelShape var0) {
      return !VoxelShapes.c(VoxelShapes.b(), var0, OperatorBoolean.g);
   }
}
