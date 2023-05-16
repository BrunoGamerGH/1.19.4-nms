package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockCocoa extends BlockFacingHorizontal implements IBlockFragilePlantElement {
   public static final int a = 2;
   public static final BlockStateInteger b = BlockProperties.ar;
   protected static final int c = 4;
   protected static final int d = 5;
   protected static final int e = 2;
   protected static final int f = 6;
   protected static final int g = 7;
   protected static final int h = 3;
   protected static final int i = 8;
   protected static final int j = 9;
   protected static final int k = 4;
   protected static final VoxelShape[] l = new VoxelShape[]{
      Block.a(11.0, 7.0, 6.0, 15.0, 12.0, 10.0), Block.a(9.0, 5.0, 5.0, 15.0, 12.0, 11.0), Block.a(7.0, 3.0, 4.0, 15.0, 12.0, 12.0)
   };
   protected static final VoxelShape[] m = new VoxelShape[]{
      Block.a(1.0, 7.0, 6.0, 5.0, 12.0, 10.0), Block.a(1.0, 5.0, 5.0, 7.0, 12.0, 11.0), Block.a(1.0, 3.0, 4.0, 9.0, 12.0, 12.0)
   };
   protected static final VoxelShape[] n = new VoxelShape[]{
      Block.a(6.0, 7.0, 1.0, 10.0, 12.0, 5.0), Block.a(5.0, 5.0, 1.0, 11.0, 12.0, 7.0), Block.a(4.0, 3.0, 1.0, 12.0, 12.0, 9.0)
   };
   protected static final VoxelShape[] E = new VoxelShape[]{
      Block.a(6.0, 7.0, 11.0, 10.0, 12.0, 15.0), Block.a(5.0, 5.0, 9.0, 11.0, 12.0, 15.0), Block.a(4.0, 3.0, 7.0, 12.0, 12.0, 15.0)
   };

   public BlockCocoa(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(aD, EnumDirection.c).a(b, Integer.valueOf(0)));
   }

   @Override
   public boolean e_(IBlockData iblockdata) {
      return iblockdata.c(b) < 2;
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (worldserver.z.i() < (float)worldserver.spigotConfig.cocoaModifier / 500.0F) {
         int i = iblockdata.c(b);
         if (i < 2) {
            CraftEventFactory.handleBlockGrowEvent(worldserver, blockposition, iblockdata.a(b, Integer.valueOf(i + 1)), 2);
         }
      }
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      IBlockData iblockdata1 = iworldreader.a_(blockposition.a(iblockdata.c(aD)));
      return iblockdata1.a(TagsBlock.y);
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      int i = iblockdata.c(b);
      switch((EnumDirection)iblockdata.c(aD)) {
         case c:
         default:
            return n[i];
         case d:
            return E[i];
         case e:
            return m[i];
         case f:
            return l[i];
      }
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      IBlockData iblockdata = this.o();
      World world = blockactioncontext.q();
      BlockPosition blockposition = blockactioncontext.a();

      for(EnumDirection enumdirection : blockactioncontext.f()) {
         if (enumdirection.o().d()) {
            iblockdata = iblockdata.a(aD, enumdirection);
            if (iblockdata.a((IWorldReader)world, blockposition)) {
               return iblockdata;
            }
         }
      }

      return null;
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
      return enumdirection == iblockdata.c(aD) && !iblockdata.a(generatoraccess, blockposition)
         ? Blocks.a.o()
         : super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public boolean a(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
      return iblockdata.c(b) < 2;
   }

   @Override
   public boolean a(World world, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      return true;
   }

   @Override
   public void a(WorldServer worldserver, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      CraftEventFactory.handleBlockGrowEvent(worldserver, blockposition, iblockdata.a(b, Integer.valueOf(iblockdata.c(b) + 1)), 2);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(aD, b);
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return false;
   }
}
