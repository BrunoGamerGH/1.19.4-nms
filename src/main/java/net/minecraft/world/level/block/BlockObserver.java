package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockObserver extends BlockDirectional {
   public static final BlockStateBoolean b = BlockProperties.w;

   public BlockObserver(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(a, EnumDirection.d).a(b, Boolean.valueOf(false)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a, b);
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
      return iblockdata.a(a, enumblockrotation.a(iblockdata.c(a)));
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
      return iblockdata.a(enumblockmirror.a(iblockdata.c(a)));
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (iblockdata.c(b)) {
         if (CraftEventFactory.callRedstoneChange(worldserver, blockposition, 15, 0).getNewCurrent() != 0) {
            return;
         }

         worldserver.a(blockposition, iblockdata.a(b, Boolean.valueOf(false)), 2);
      } else {
         if (CraftEventFactory.callRedstoneChange(worldserver, blockposition, 0, 15).getNewCurrent() != 15) {
            return;
         }

         worldserver.a(blockposition, iblockdata.a(b, Boolean.valueOf(true)), 2);
         worldserver.a(blockposition, this, 2);
      }

      this.a(worldserver, blockposition, iblockdata);
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
      if (iblockdata.c(a) == enumdirection && !iblockdata.c(b)) {
         this.a(generatoraccess, blockposition);
      }

      return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   private void a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
      if (!generatoraccess.k_() && !generatoraccess.K().a(blockposition, this)) {
         generatoraccess.a(blockposition, this, 2);
      }
   }

   protected void a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      EnumDirection enumdirection = iblockdata.c(a);
      BlockPosition blockposition1 = blockposition.a(enumdirection.g());
      world.a(blockposition1, this, blockposition);
      world.a(blockposition1, this, enumdirection);
   }

   @Override
   public boolean f_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return iblockdata.b(iblockaccess, blockposition, enumdirection);
   }

   @Override
   public int a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return iblockdata.c(b) && iblockdata.c(a) == enumdirection ? 15 : 0;
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!iblockdata.a(iblockdata1.b()) && !world.k_() && iblockdata.c(b) && !world.K().a(blockposition, this)) {
         IBlockData iblockdata2 = iblockdata.a(b, Boolean.valueOf(false));
         world.a(blockposition, iblockdata2, 18);
         this.a(world, blockposition, iblockdata2);
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!iblockdata.a(iblockdata1.b()) && !world.B && iblockdata.c(b) && world.K().a(blockposition, this)) {
         this.a(world, blockposition, iblockdata.a(b, Boolean.valueOf(false)));
      }
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      return this.o().a(a, blockactioncontext.d().g().g());
   }
}
