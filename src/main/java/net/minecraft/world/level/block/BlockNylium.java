package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.lighting.LightEngineLayer;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockNylium extends Block implements IBlockFragilePlantElement {
   protected BlockNylium(BlockBase.Info blockbase_info) {
      super(blockbase_info);
   }

   private static boolean b(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      BlockPosition blockposition1 = blockposition.c();
      IBlockData iblockdata1 = iworldreader.a_(blockposition1);
      int i = LightEngineLayer.a(
         iworldreader, iblockdata, blockposition, iblockdata1, blockposition1, EnumDirection.b, iblockdata1.b(iworldreader, blockposition1)
      );
      return i < iworldreader.L();
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (!b(iblockdata, worldserver, blockposition)) {
         if (CraftEventFactory.callBlockFadeEvent(worldserver, blockposition, Blocks.dV.o()).isCancelled()) {
            return;
         }

         worldserver.b(blockposition, Blocks.dV.o());
      }
   }

   @Override
   public boolean a(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
      return iworldreader.a_(blockposition.c()).h();
   }

   @Override
   public boolean a(World world, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      return true;
   }

   @Override
   public void a(WorldServer worldserver, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      IBlockData iblockdata1 = worldserver.a_(blockposition);
      BlockPosition blockposition1 = blockposition.c();
      ChunkGenerator chunkgenerator = worldserver.k().g();
      IRegistry<WorldGenFeatureConfigured<?, ?>> iregistry = worldserver.u_().d(Registries.aq);
      if (iblockdata1.a(Blocks.os)) {
         this.a(iregistry, NetherFeatures.h, worldserver, chunkgenerator, randomsource, blockposition1);
      } else if (iblockdata1.a(Blocks.oj)) {
         this.a(iregistry, NetherFeatures.j, worldserver, chunkgenerator, randomsource, blockposition1);
         this.a(iregistry, NetherFeatures.l, worldserver, chunkgenerator, randomsource, blockposition1);
         if (randomsource.a(8) == 0) {
            this.a(iregistry, NetherFeatures.n, worldserver, chunkgenerator, randomsource, blockposition1);
         }
      }
   }

   private void a(
      IRegistry<WorldGenFeatureConfigured<?, ?>> iregistry,
      ResourceKey<WorldGenFeatureConfigured<?, ?>> resourcekey,
      WorldServer worldserver,
      ChunkGenerator chunkgenerator,
      RandomSource randomsource,
      BlockPosition blockposition
   ) {
      iregistry.b(resourcekey).ifPresent(holder_c -> holder_c.a().a(worldserver, chunkgenerator, randomsource, blockposition));
   }
}
