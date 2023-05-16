package net.minecraft.world.level.block;

import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.TreeType;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockMushroom extends BlockPlant implements IBlockFragilePlantElement {
   protected static final float a = 3.0F;
   protected static final VoxelShape b = Block.a(5.0, 0.0, 5.0, 11.0, 6.0, 11.0);
   private final ResourceKey<WorldGenFeatureConfigured<?, ?>> c;

   public BlockMushroom(BlockBase.Info blockbase_info, ResourceKey<WorldGenFeatureConfigured<?, ?>> resourcekey) {
      super(blockbase_info);
      this.c = resourcekey;
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return b;
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (randomsource.i() < (float)worldserver.spigotConfig.mushroomModifier / 2500.0F) {
         int i = 5;
         boolean flag = true;

         for(BlockPosition blockposition1 : BlockPosition.a(blockposition.b(-4, -1, -4), blockposition.b(4, 1, 4))) {
            if (worldserver.a_(blockposition1).a(this)) {
               if (--i <= 0) {
                  return;
               }
            }
         }

         BlockPosition blockposition2 = blockposition.b(randomsource.a(3) - 1, randomsource.a(2) - randomsource.a(2), randomsource.a(3) - 1);

         for(int j = 0; j < 4; ++j) {
            if (worldserver.w(blockposition2) && iblockdata.a(worldserver, blockposition2)) {
               blockposition = blockposition2;
            }

            blockposition2 = blockposition.b(randomsource.a(3) - 1, randomsource.a(2) - randomsource.a(2), randomsource.a(3) - 1);
         }

         if (worldserver.w(blockposition2) && iblockdata.a(worldserver, blockposition2)) {
            CraftEventFactory.handleBlockSpreadEvent(worldserver, blockposition, blockposition2, iblockdata, 2);
         }
      }
   }

   @Override
   protected boolean d(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return iblockdata.i(iblockaccess, blockposition);
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      BlockPosition blockposition1 = blockposition.d();
      IBlockData iblockdata1 = iworldreader.a_(blockposition1);
      return iblockdata1.a(TagsBlock.aX) ? true : iworldreader.b(blockposition, 0) < 13 && this.d(iblockdata1, iworldreader, blockposition1);
   }

   public boolean a(WorldServer worldserver, BlockPosition blockposition, IBlockData iblockdata, RandomSource randomsource) {
      Optional<? extends Holder<WorldGenFeatureConfigured<?, ?>>> optional = worldserver.u_().d(Registries.aq).b(this.c);
      if (optional.isEmpty()) {
         return false;
      } else {
         worldserver.a(blockposition, false);
         BlockSapling.treeType = this == Blocks.ce ? TreeType.BROWN_MUSHROOM : TreeType.BROWN_MUSHROOM;
         if (optional.get().a().a(worldserver, worldserver.k().g(), randomsource, blockposition)) {
            return true;
         } else {
            worldserver.a(blockposition, iblockdata, 3);
            return false;
         }
      }
   }

   @Override
   public boolean a(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
      return true;
   }

   @Override
   public boolean a(World world, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      return (double)randomsource.i() < 0.4;
   }

   @Override
   public void a(WorldServer worldserver, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      this.a(worldserver, blockposition, iblockdata, randomsource);
   }
}
