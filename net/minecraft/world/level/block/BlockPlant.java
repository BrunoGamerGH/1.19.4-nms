package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsBlock;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathMode;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockPlant extends Block {
   protected BlockPlant(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   protected boolean d(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return iblockdata.a(TagsBlock.ae) || iblockdata.a(Blocks.cB);
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
      return !iblockdata.a(generatoraccess, blockposition) && !CraftEventFactory.callBlockPhysicsEvent(generatoraccess, blockposition).isCancelled()
         ? Blocks.a.o()
         : super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      BlockPosition blockposition1 = blockposition.d();
      return this.d(iworldreader.a_(blockposition1), iworldreader, blockposition1);
   }

   @Override
   public boolean c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return iblockdata.r().c();
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return pathmode == PathMode.c && !this.aG ? true : super.a(iblockdata, iblockaccess, blockposition, pathmode);
   }
}
