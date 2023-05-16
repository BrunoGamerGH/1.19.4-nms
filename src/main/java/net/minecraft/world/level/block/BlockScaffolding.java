package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.EntityFallingBlock;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockScaffolding extends Block implements IBlockWaterlogged {
   private static final int e = 1;
   private static final VoxelShape f;
   private static final VoxelShape g;
   private static final VoxelShape h = Block.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
   private static final VoxelShape i = VoxelShapes.b().a(0.0, -1.0, 0.0);
   public static final int a = 7;
   public static final BlockStateInteger b = BlockProperties.aW;
   public static final BlockStateBoolean c = BlockProperties.C;
   public static final BlockStateBoolean d = BlockProperties.b;

   static {
      VoxelShape voxelshape = Block.a(0.0, 14.0, 0.0, 16.0, 16.0, 16.0);
      VoxelShape voxelshape1 = Block.a(0.0, 0.0, 0.0, 2.0, 16.0, 2.0);
      VoxelShape voxelshape2 = Block.a(14.0, 0.0, 0.0, 16.0, 16.0, 2.0);
      VoxelShape voxelshape3 = Block.a(0.0, 0.0, 14.0, 2.0, 16.0, 16.0);
      VoxelShape voxelshape4 = Block.a(14.0, 0.0, 14.0, 16.0, 16.0, 16.0);
      f = VoxelShapes.a(voxelshape, voxelshape1, voxelshape2, voxelshape3, voxelshape4);
      VoxelShape voxelshape5 = Block.a(0.0, 0.0, 0.0, 2.0, 2.0, 16.0);
      VoxelShape voxelshape6 = Block.a(14.0, 0.0, 0.0, 16.0, 2.0, 16.0);
      VoxelShape voxelshape7 = Block.a(0.0, 0.0, 14.0, 16.0, 2.0, 16.0);
      VoxelShape voxelshape8 = Block.a(0.0, 0.0, 0.0, 16.0, 2.0, 2.0);
      g = VoxelShapes.a(BlockScaffolding.h, f, voxelshape6, voxelshape5, voxelshape8, voxelshape7);
   }

   protected BlockScaffolding(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(b, Integer.valueOf(7)).a(c, Boolean.valueOf(false)).a(d, Boolean.valueOf(false)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(b, c, d);
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return !voxelshapecollision.a(iblockdata.b().k()) ? (iblockdata.c(d) ? g : f) : VoxelShapes.b();
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return VoxelShapes.b();
   }

   @Override
   public boolean a(IBlockData iblockdata, BlockActionContext blockactioncontext) {
      return blockactioncontext.n().a(this.k());
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      BlockPosition blockposition = blockactioncontext.a();
      World world = blockactioncontext.q();
      int i = a(world, blockposition);
      return this.o()
         .a(c, Boolean.valueOf(world.b_(blockposition).a() == FluidTypes.c))
         .a(b, Integer.valueOf(i))
         .a(d, Boolean.valueOf(this.a(world, blockposition, i)));
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!world.B) {
         world.a(blockposition, this, 1);
      }
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
      if (iblockdata.c(c)) {
         generatoraccess.a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
      }

      if (!generatoraccess.k_()) {
         generatoraccess.a(blockposition, this, 1);
      }

      return iblockdata;
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      int i = a(worldserver, blockposition);
      IBlockData iblockdata1 = iblockdata.a(b, Integer.valueOf(i)).a(d, Boolean.valueOf(this.a(worldserver, blockposition, i)));
      if (iblockdata1.c(b) == 7 && !CraftEventFactory.callBlockFadeEvent(worldserver, blockposition, Blocks.a.o()).isCancelled()) {
         if (iblockdata.c(b) == 7) {
            EntityFallingBlock.a(worldserver, blockposition, iblockdata1);
         } else {
            worldserver.b(blockposition, true);
         }
      } else if (iblockdata != iblockdata1) {
         worldserver.a(blockposition, iblockdata1, 3);
      }
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      return a(iworldreader, blockposition) < 7;
   }

   @Override
   public VoxelShape c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return voxelshapecollision.a(VoxelShapes.b(), blockposition, true) && !voxelshapecollision.b()
         ? f
         : (iblockdata.c(b) != 0 && iblockdata.c(d) && voxelshapecollision.a(i, blockposition, true) ? h : VoxelShapes.a());
   }

   @Override
   public Fluid c_(IBlockData iblockdata) {
      return iblockdata.c(c) ? FluidTypes.c.a(false) : super.c_(iblockdata);
   }

   private boolean a(IBlockAccess iblockaccess, BlockPosition blockposition, int i) {
      return i > 0 && !iblockaccess.a_(blockposition.d()).a(this);
   }

   public static int a(IBlockAccess iblockaccess, BlockPosition blockposition) {
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = blockposition.j().c(EnumDirection.a);
      IBlockData iblockdata = iblockaccess.a_(blockposition_mutableblockposition);
      int i = 7;
      if (iblockdata.a(Blocks.nO)) {
         i = iblockdata.c(b);
      } else if (iblockdata.d(iblockaccess, blockposition_mutableblockposition, EnumDirection.b)) {
         return 0;
      }

      for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
         IBlockData iblockdata1 = iblockaccess.a_(blockposition_mutableblockposition.a(blockposition, enumdirection));
         if (iblockdata1.a(Blocks.nO)) {
            i = Math.min(i, iblockdata1.c(b) + 1);
            if (i == 1) {
               break;
            }
         }
      }

      return i;
   }
}
