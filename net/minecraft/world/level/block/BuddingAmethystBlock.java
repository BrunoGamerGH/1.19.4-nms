package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.level.material.FluidTypes;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BuddingAmethystBlock extends AmethystBlock {
   public static final int a = 5;
   private static final EnumDirection[] b = EnumDirection.values();

   public BuddingAmethystBlock(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   @Override
   public EnumPistonReaction d(IBlockData iblockdata) {
      return EnumPistonReaction.b;
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (randomsource.a(5) == 0) {
         EnumDirection enumdirection = b[randomsource.a(b.length)];
         BlockPosition blockposition1 = blockposition.a(enumdirection);
         IBlockData iblockdata1 = worldserver.a_(blockposition1);
         Block block = null;
         if (g(iblockdata1)) {
            block = Blocks.qu;
         } else if (iblockdata1.a(Blocks.qu) && iblockdata1.c(AmethystClusterBlock.b) == enumdirection) {
            block = Blocks.qt;
         } else if (iblockdata1.a(Blocks.qt) && iblockdata1.c(AmethystClusterBlock.b) == enumdirection) {
            block = Blocks.qs;
         } else if (iblockdata1.a(Blocks.qs) && iblockdata1.c(AmethystClusterBlock.b) == enumdirection) {
            block = Blocks.qr;
         }

         if (block != null) {
            IBlockData iblockdata2 = block.o()
               .a(AmethystClusterBlock.b, enumdirection)
               .a(AmethystClusterBlock.a, Boolean.valueOf(iblockdata1.r().a() == FluidTypes.c));
            CraftEventFactory.handleBlockSpreadEvent(worldserver, blockposition, blockposition1, iblockdata2);
         }
      }
   }

   public static boolean g(IBlockData iblockdata) {
      return iblockdata.h() || iblockdata.a(Blocks.G) && iblockdata.r().e() == 8;
   }
}
