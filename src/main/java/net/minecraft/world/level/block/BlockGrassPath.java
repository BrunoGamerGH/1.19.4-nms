package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockGrassPath extends Block {
   protected static final VoxelShape a = BlockSoil.b;

   protected BlockGrassPath(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   @Override
   public boolean g_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      return !this.o().a((IWorldReader)blockactioncontext.q(), blockactioncontext.a())
         ? Block.a(this.o(), Blocks.j.o(), blockactioncontext.q(), blockactioncontext.a())
         : super.a(blockactioncontext);
   }

   @Override
   public IBlockData a(
      IBlockData iblockdata,
      EnumDirection enumdirection,
      IBlockData iblockdata1,
      GeneratorAccess generatoraccess,
      BlockPosition blockposition,
      BlockPosition blockposition1
   ) {
      if (enumdirection == EnumDirection.b && !iblockdata.a(generatoraccess, blockposition)) {
         generatoraccess.a(blockposition, this, 1);
      }

      return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (!iblockdata.a(worldserver, blockposition)) {
         BlockSoil.a(null, iblockdata, worldserver, blockposition);
      }
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      IBlockData iblockdata1 = iworldreader.a_(blockposition.c());
      return !iblockdata1.d().b() || iblockdata1.b() instanceof BlockFenceGate;
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return a;
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return false;
   }
}
