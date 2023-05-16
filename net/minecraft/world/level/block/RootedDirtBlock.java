package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class RootedDirtBlock extends Block implements IBlockFragilePlantElement {
   public RootedDirtBlock(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   @Override
   public boolean a(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
      return iworldreader.a_(blockposition.d()).h();
   }

   @Override
   public boolean a(World world, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      return true;
   }

   @Override
   public void a(WorldServer worldserver, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      CraftEventFactory.handleBlockSpreadEvent(worldserver, blockposition, blockposition.d(), Blocks.rA.o());
   }
}
