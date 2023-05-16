package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockLongGrass extends BlockPlant implements IBlockFragilePlantElement {
   protected static final float a = 6.0F;
   protected static final VoxelShape b = Block.a(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

   protected BlockLongGrass(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return b;
   }

   @Override
   public boolean a(IWorldReader var0, BlockPosition var1, IBlockData var2, boolean var3) {
      return true;
   }

   @Override
   public boolean a(World var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      return true;
   }

   @Override
   public void a(WorldServer var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      BlockTallPlant var4 = (BlockTallPlant)(var3.a(Blocks.bt) ? Blocks.iH : Blocks.iG);
      if (var4.o().a(var0, var2) && var0.w(var2.c())) {
         BlockTallPlant.a(var0, var4.o(), var2, 2);
      }
   }
}
