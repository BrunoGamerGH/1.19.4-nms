package net.minecraft.world.level.block;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class PiglinWallSkullBlock extends BlockSkullWall {
   private static final Map<EnumDirection, VoxelShape> b = Maps.immutableEnumMap(
      Map.of(
         EnumDirection.c,
         Block.a(3.0, 4.0, 8.0, 13.0, 12.0, 16.0),
         EnumDirection.d,
         Block.a(3.0, 4.0, 0.0, 13.0, 12.0, 8.0),
         EnumDirection.f,
         Block.a(0.0, 4.0, 3.0, 8.0, 12.0, 13.0),
         EnumDirection.e,
         Block.a(8.0, 4.0, 3.0, 16.0, 12.0, 13.0)
      )
   );

   public PiglinWallSkullBlock(BlockBase.Info var0) {
      super(BlockSkull.Type.f, var0);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return b.get(var0.c(a));
   }
}
