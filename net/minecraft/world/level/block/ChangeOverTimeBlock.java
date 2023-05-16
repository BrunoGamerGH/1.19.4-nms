package net.minecraft.world.level.block;

import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.IBlockData;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public interface ChangeOverTimeBlock<T extends Enum<T>> {
   int u_ = 4;

   Optional<IBlockData> a(IBlockData var1);

   float a();

   default void a_(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      float f = 0.05688889F;
      if (randomsource.i() < 0.05688889F) {
         this.c(iblockdata, worldserver, blockposition, randomsource);
      }
   }

   T b();

   default void c(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      int i = this.b().ordinal();
      int j = 0;
      int k = 0;

      for(BlockPosition blockposition1 : BlockPosition.a(blockposition, 4, 4, 4)) {
         int l = blockposition1.k(blockposition);
         if (l > 4) {
            break;
         }

         if (!blockposition1.equals(blockposition)) {
            IBlockData iblockdata1 = worldserver.a_(blockposition1);
            Block block = iblockdata1.b();
            if (block instanceof ChangeOverTimeBlock) {
               Enum<?> oenum = ((ChangeOverTimeBlock)block).b();
               if (this.b().getClass() == oenum.getClass()) {
                  int i1 = oenum.ordinal();
                  if (i1 < i) {
                     return;
                  }

                  if (i1 > i) {
                     ++k;
                  } else {
                     ++j;
                  }
               }
            }
         }
      }

      float f = (float)(k + 1) / (float)(k + j + 1);
      float f1 = f * f * this.a();
      if (randomsource.i() < f1) {
         this.a(iblockdata).ifPresent(iblockdata2 -> CraftEventFactory.handleBlockFormEvent(worldserver, blockposition, iblockdata2));
      }
   }
}
