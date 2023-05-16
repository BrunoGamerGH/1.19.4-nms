package net.minecraft.world.level.block.grower;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.BlockSapling;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import org.bukkit.TreeType;

public abstract class WorldGenTreeProvider {
   @Nullable
   protected abstract ResourceKey<WorldGenFeatureConfigured<?, ?>> a(RandomSource var1, boolean var2);

   public boolean a(WorldServer worldserver, ChunkGenerator chunkgenerator, BlockPosition blockposition, IBlockData iblockdata, RandomSource randomsource) {
      ResourceKey<WorldGenFeatureConfigured<?, ?>> resourcekey = this.a(randomsource, this.a(worldserver, blockposition));
      if (resourcekey == null) {
         return false;
      } else {
         Holder<WorldGenFeatureConfigured<?, ?>> holder = worldserver.u_().d(Registries.aq).b(resourcekey).orElse(null);
         if (holder == null) {
            return false;
         } else {
            this.setTreeType(holder);
            WorldGenFeatureConfigured<?, ?> worldgenfeatureconfigured = holder.a();
            IBlockData iblockdata1 = worldserver.b_(blockposition).g();
            worldserver.a(blockposition, iblockdata1, 4);
            if (worldgenfeatureconfigured.a(worldserver, chunkgenerator, randomsource, blockposition)) {
               if (worldserver.a_(blockposition) == iblockdata1) {
                  worldserver.a(blockposition, iblockdata, iblockdata1, 2);
               }

               return true;
            } else {
               worldserver.a(blockposition, iblockdata, 4);
               return false;
            }
         }
      }
   }

   private boolean a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
      for(BlockPosition blockposition1 : BlockPosition.MutableBlockPosition.a(blockposition.d().d(2).f(2), blockposition.c().e(2).g(2))) {
         if (generatoraccess.a_(blockposition1).a(TagsBlock.T)) {
            return true;
         }
      }

      return false;
   }

   protected void setTreeType(Holder<WorldGenFeatureConfigured<?, ?>> holder) {
      ResourceKey<WorldGenFeatureConfigured<?, ?>> worldgentreeabstract = holder.e().get();
      if (worldgentreeabstract == TreeFeatures.g || worldgentreeabstract == TreeFeatures.C) {
         BlockSapling.treeType = TreeType.TREE;
      } else if (worldgentreeabstract == TreeFeatures.f) {
         BlockSapling.treeType = TreeType.RED_MUSHROOM;
      } else if (worldgentreeabstract == TreeFeatures.e) {
         BlockSapling.treeType = TreeType.BROWN_MUSHROOM;
      } else if (worldgentreeabstract == TreeFeatures.m) {
         BlockSapling.treeType = TreeType.COCOA_TREE;
      } else if (worldgentreeabstract == TreeFeatures.o) {
         BlockSapling.treeType = TreeType.SMALL_JUNGLE;
      } else if (worldgentreeabstract == TreeFeatures.l) {
         BlockSapling.treeType = TreeType.TALL_REDWOOD;
      } else if (worldgentreeabstract == TreeFeatures.k) {
         BlockSapling.treeType = TreeType.REDWOOD;
      } else if (worldgentreeabstract == TreeFeatures.j) {
         BlockSapling.treeType = TreeType.ACACIA;
      } else if (worldgentreeabstract == TreeFeatures.i || worldgentreeabstract == TreeFeatures.F) {
         BlockSapling.treeType = TreeType.BIRCH;
      } else if (worldgentreeabstract == TreeFeatures.s) {
         BlockSapling.treeType = TreeType.TALL_BIRCH;
      } else if (worldgentreeabstract == TreeFeatures.u) {
         BlockSapling.treeType = TreeType.SWAMP;
      } else if (worldgentreeabstract == TreeFeatures.n || worldgentreeabstract == TreeFeatures.I) {
         BlockSapling.treeType = TreeType.BIG_TREE;
      } else if (worldgentreeabstract == TreeFeatures.v) {
         BlockSapling.treeType = TreeType.JUNGLE_BUSH;
      } else if (worldgentreeabstract == TreeFeatures.h) {
         BlockSapling.treeType = TreeType.DARK_OAK;
      } else if (worldgentreeabstract == TreeFeatures.q) {
         BlockSapling.treeType = TreeType.MEGA_REDWOOD;
      } else if (worldgentreeabstract == TreeFeatures.r) {
         BlockSapling.treeType = TreeType.MEGA_REDWOOD;
      } else if (worldgentreeabstract == TreeFeatures.p) {
         BlockSapling.treeType = TreeType.JUNGLE;
      } else if (worldgentreeabstract == TreeFeatures.w) {
         BlockSapling.treeType = TreeType.AZALEA;
      } else if (worldgentreeabstract == TreeFeatures.x) {
         BlockSapling.treeType = TreeType.MANGROVE;
      } else if (worldgentreeabstract == TreeFeatures.y) {
         BlockSapling.treeType = TreeType.TALL_MANGROVE;
      } else {
         if (worldgentreeabstract != TreeFeatures.z && worldgentreeabstract != TreeFeatures.K) {
            throw new IllegalArgumentException("Unknown tree generator " + worldgentreeabstract);
         }

         BlockSapling.treeType = TreeType.CHERRY;
      }
   }
}
