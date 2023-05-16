package net.minecraft.world.level.block.grower;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;

public abstract class WorldGenMegaTreeProvider extends WorldGenTreeProvider {
   @Override
   public boolean a(WorldServer worldserver, ChunkGenerator chunkgenerator, BlockPosition blockposition, IBlockData iblockdata, RandomSource randomsource) {
      for(int i = 0; i >= -1; --i) {
         for(int j = 0; j >= -1; --j) {
            if (a(iblockdata, worldserver, blockposition, i, j)) {
               return this.a(worldserver, chunkgenerator, blockposition, iblockdata, randomsource, i, j);
            }
         }
      }

      return super.a(worldserver, chunkgenerator, blockposition, iblockdata, randomsource);
   }

   @Nullable
   protected abstract ResourceKey<WorldGenFeatureConfigured<?, ?>> a(RandomSource var1);

   public boolean a(
      WorldServer worldserver, ChunkGenerator chunkgenerator, BlockPosition blockposition, IBlockData iblockdata, RandomSource randomsource, int i, int j
   ) {
      ResourceKey<WorldGenFeatureConfigured<?, ?>> resourcekey = this.a(randomsource);
      if (resourcekey == null) {
         return false;
      } else {
         Holder<WorldGenFeatureConfigured<?, ?>> holder = worldserver.u_().d(Registries.aq).b(resourcekey).orElse(null);
         if (holder == null) {
            return false;
         } else {
            this.setTreeType(holder);
            WorldGenFeatureConfigured<?, ?> worldgenfeatureconfigured = holder.a();
            IBlockData iblockdata1 = Blocks.a.o();
            worldserver.a(blockposition.b(i, 0, j), iblockdata1, 4);
            worldserver.a(blockposition.b(i + 1, 0, j), iblockdata1, 4);
            worldserver.a(blockposition.b(i, 0, j + 1), iblockdata1, 4);
            worldserver.a(blockposition.b(i + 1, 0, j + 1), iblockdata1, 4);
            if (worldgenfeatureconfigured.a(worldserver, chunkgenerator, randomsource, blockposition.b(i, 0, j))) {
               return true;
            } else {
               worldserver.a(blockposition.b(i, 0, j), iblockdata, 4);
               worldserver.a(blockposition.b(i + 1, 0, j), iblockdata, 4);
               worldserver.a(blockposition.b(i, 0, j + 1), iblockdata, 4);
               worldserver.a(blockposition.b(i + 1, 0, j + 1), iblockdata, 4);
               return false;
            }
         }
      }
   }

   public static boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, int i, int j) {
      Block block = iblockdata.b();
      return iblockaccess.a_(blockposition.b(i, 0, j)).a(block)
         && iblockaccess.a_(blockposition.b(i + 1, 0, j)).a(block)
         && iblockaccess.a_(blockposition.b(i, 0, j + 1)).a(block)
         && iblockaccess.a_(blockposition.b(i + 1, 0, j + 1)).a(block);
   }
}
