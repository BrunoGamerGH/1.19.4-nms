package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.lighting.LightEngineLayer;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public abstract class BlockDirtSnowSpreadable extends BlockDirtSnow {
   protected BlockDirtSnowSpreadable(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   private static boolean b(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      BlockPosition blockposition1 = blockposition.c();
      IBlockData iblockdata1 = iworldreader.a_(blockposition1);
      if (iblockdata1.a(Blocks.dM) && iblockdata1.c(BlockSnow.b) == 1) {
         return true;
      } else if (iblockdata1.r().e() == 8) {
         return false;
      } else {
         int i = LightEngineLayer.a(
            iworldreader, iblockdata, blockposition, iblockdata1, blockposition1, EnumDirection.b, iblockdata1.b(iworldreader, blockposition1)
         );
         return i < iworldreader.L();
      }
   }

   private static boolean c(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      BlockPosition blockposition1 = blockposition.c();
      return b(iblockdata, iworldreader, blockposition) && !iworldreader.b_(blockposition1).a(TagsFluid.a);
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (!b(iblockdata, worldserver, blockposition)) {
         if (CraftEventFactory.callBlockFadeEvent(worldserver, blockposition, Blocks.j.o()).isCancelled()) {
            return;
         }

         worldserver.b(blockposition, Blocks.j.o());
      } else if (worldserver.C(blockposition.c()) >= 9) {
         IBlockData iblockdata1 = this.o();

         for(int i = 0; i < 4; ++i) {
            BlockPosition blockposition1 = blockposition.b(randomsource.a(3) - 1, randomsource.a(5) - 3, randomsource.a(3) - 1);
            if (worldserver.a_(blockposition1).a(Blocks.j) && c(iblockdata1, worldserver, blockposition1)) {
               CraftEventFactory.handleBlockSpreadEvent(
                  worldserver, blockposition, blockposition1, iblockdata1.a(a, Boolean.valueOf(worldserver.a_(blockposition1.c()).a(Blocks.dM)))
               );
            }
         }
      }
   }
}
