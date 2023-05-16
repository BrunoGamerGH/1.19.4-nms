package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockReed extends Block {
   public static final BlockStateInteger a = BlockProperties.aw;
   protected static final float b = 6.0F;
   protected static final VoxelShape c = Block.a(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

   protected BlockReed(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return c;
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (!iblockdata.a(worldserver, blockposition)) {
         worldserver.b(blockposition, true);
      }
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (worldserver.w(blockposition.c())) {
         int i = 1;

         while(worldserver.a_(blockposition.c(i)).a(this)) {
            ++i;
         }

         if (i < 3) {
            int j = iblockdata.c(a);
            int modifier = worldserver.spigotConfig.caneModifier;
            if (j < 15 && (modifier == 100 || !(randomsource.i() < (float)modifier / 1600.0F))) {
               if (modifier == 100 || randomsource.i() < (float)modifier / 1600.0F) {
                  worldserver.a(blockposition, iblockdata.a(a, Integer.valueOf(j + 1)), 4);
               }
            } else {
               CraftEventFactory.handleBlockGrowEvent(worldserver, blockposition.c(), this.o());
               worldserver.a(blockposition, iblockdata.a(a, Integer.valueOf(0)), 4);
            }
         }
      }
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
      if (!iblockdata.a(generatoraccess, blockposition)) {
         generatoraccess.a(blockposition, this, 1);
      }

      return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      IBlockData iblockdata1 = iworldreader.a_(blockposition.d());
      if (iblockdata1.a(this)) {
         return true;
      } else {
         if (iblockdata1.a(TagsBlock.ae) || iblockdata1.a(Blocks.I) || iblockdata1.a(Blocks.K)) {
            BlockPosition blockposition1 = blockposition.d();

            for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
               IBlockData iblockdata2 = iworldreader.a_(blockposition1.a(enumdirection));
               Fluid fluid = iworldreader.b_(blockposition1.a(enumdirection));
               if (fluid.a(TagsFluid.a) || iblockdata2.a(Blocks.kF)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a);
   }
}
