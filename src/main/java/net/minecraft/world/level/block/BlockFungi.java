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

public class BlockFungi extends BlockPlant implements IBlockFragilePlantElement {
   protected static final VoxelShape a = Block.a(4.0, 0.0, 4.0, 12.0, 9.0, 12.0);
   private static final double b = 0.4;
   private final Block c;
   private final ResourceKey<WorldGenFeatureConfigured<?, ?>> d;

   protected BlockFungi(BlockBase.Info blockbase_info, ResourceKey<WorldGenFeatureConfigured<?, ?>> resourcekey, Block block) {
      super(blockbase_info);
      this.d = resourcekey;
      this.c = block;
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return a;
   }

   @Override
   protected boolean d(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return iblockdata.a(TagsBlock.aI) || iblockdata.a(Blocks.fk) || iblockdata.a(Blocks.dX) || super.d(iblockdata, iblockaccess, blockposition);
   }

   private Optional<? extends Holder<WorldGenFeatureConfigured<?, ?>>> a(IWorldReader iworldreader) {
      return iworldreader.u_().d(Registries.aq).b(this.d);
   }

   @Override
   public boolean a(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
      IBlockData iblockdata1 = iworldreader.a_(blockposition.d());
      return iblockdata1.a(this.c);
   }

   @Override
   public boolean a(World world, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      return (double)randomsource.i() < 0.4;
   }

   @Override
   public void a(WorldServer worldserver, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      this.a(worldserver).ifPresent(holder -> {
         if (this == Blocks.ok) {
            BlockSapling.treeType = TreeType.WARPED_FUNGUS;
         } else if (this == Blocks.ot) {
            BlockSapling.treeType = TreeType.CRIMSON_FUNGUS;
         }

         holder.a().a(worldserver, worldserver.k().g(), randomsource, blockposition);
      });
   }
}
