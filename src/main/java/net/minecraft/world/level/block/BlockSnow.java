package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.EnumSkyBlock;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockSnow extends Block {
   public static final int a = 8;
   public static final BlockStateInteger b = BlockProperties.aF;
   protected static final VoxelShape[] c = new VoxelShape[]{
      VoxelShapes.a(),
      Block.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
      Block.a(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
   };
   public static final int d = 5;

   protected BlockSnow(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(b, Integer.valueOf(1)));
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      switch(pathmode) {
         case a:
            if (iblockdata.c(b) < 5) {
               return true;
            }

            return false;
         case b:
            return false;
         case c:
            return false;
         default:
            return false;
      }
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return c[iblockdata.c(b)];
   }

   @Override
   public VoxelShape c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return c[iblockdata.c(b) - 1];
   }

   @Override
   public VoxelShape b_(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return c[iblockdata.c(b)];
   }

   @Override
   public VoxelShape b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return c[iblockdata.c(b)];
   }

   @Override
   public boolean g_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public float b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return iblockdata.c(b) == 8 ? 0.2F : 1.0F;
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      IBlockData iblockdata1 = iworldreader.a_(blockposition.d());
      return iblockdata1.a(TagsBlock.ca)
         ? false
         : (
            iblockdata1.a(TagsBlock.cb)
               ? true
               : Block.a(iblockdata1.k(iworldreader, blockposition.d()), EnumDirection.b) || iblockdata1.a(this) && iblockdata1.c(b) == 8
         );
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
      return !iblockdata.a(generatoraccess, blockposition)
         ? Blocks.a.o()
         : super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (worldserver.a(EnumSkyBlock.b, blockposition) > 11) {
         if (CraftEventFactory.callBlockFadeEvent(worldserver, blockposition, Blocks.a.o()).isCancelled()) {
            return;
         }

         c(iblockdata, worldserver, blockposition);
         worldserver.a(blockposition, false);
      }
   }

   @Override
   public boolean a(IBlockData iblockdata, BlockActionContext blockactioncontext) {
      int i = iblockdata.c(b);
      return !blockactioncontext.n().a(this.k()) || i >= 8 ? i == 1 : (blockactioncontext.c() ? blockactioncontext.k() == EnumDirection.b : true);
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      IBlockData iblockdata = blockactioncontext.q().a_(blockactioncontext.a());
      if (iblockdata.a(this)) {
         int i = iblockdata.c(b);
         return iblockdata.a(b, Integer.valueOf(Math.min(8, i + 1)));
      } else {
         return super.a(blockactioncontext);
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(b);
   }
}
