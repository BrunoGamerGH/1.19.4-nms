package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.grower.AzaleaTreeGrower;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class AzaleaBlock extends BlockPlant implements IBlockFragilePlantElement {
   private static final AzaleaTreeGrower a = new AzaleaTreeGrower();
   private static final VoxelShape b = VoxelShapes.a(Block.a(0.0, 8.0, 0.0, 16.0, 16.0, 16.0), Block.a(6.0, 0.0, 6.0, 10.0, 8.0, 10.0));

   protected AzaleaBlock(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return b;
   }

   @Override
   protected boolean d(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return var0.a(Blocks.dQ) || super.d(var0, var1, var2);
   }

   @Override
   public boolean a(IWorldReader var0, BlockPosition var1, IBlockData var2, boolean var3) {
      return var0.b_(var1.c()).c();
   }

   @Override
   public boolean a(World var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      return (double)var0.z.i() < 0.45;
   }

   @Override
   public void a(WorldServer var0, RandomSource var1, BlockPosition var2, IBlockData var3) {
      a.a(var0, var0.k().g(), var2, var3, var1);
   }
}
