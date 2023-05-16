package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockSlowSand extends Block {
   protected static final VoxelShape a = Block.a(0.0, 0.0, 0.0, 16.0, 14.0, 16.0);
   private static final int b = 20;

   public BlockSlowSand(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public VoxelShape c(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return a;
   }

   @Override
   public VoxelShape b_(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return VoxelShapes.b();
   }

   @Override
   public VoxelShape b(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return VoxelShapes.b();
   }

   @Override
   public void a(IBlockData var0, WorldServer var1, BlockPosition var2, RandomSource var3) {
      BlockBubbleColumn.b(var1, var2.c(), var0);
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var1 == EnumDirection.b && var2.a(Blocks.G)) {
         var3.a(var4, this, 20);
      }

      return super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public void b(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      var1.a(var2, this, 20);
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }

   @Override
   public float b(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return 0.2F;
   }
}
