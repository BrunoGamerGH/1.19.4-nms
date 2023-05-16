package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockCactus extends Block {
   public static final BlockStateInteger a = BlockProperties.aw;
   public static final int b = 15;
   protected static final int c = 1;
   protected static final VoxelShape d = Block.a(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);
   protected static final VoxelShape e = Block.a(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   protected BlockCactus(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(a, Integer.valueOf(0)));
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (!iblockdata.a(worldserver, blockposition)) {
         worldserver.b(blockposition, true);
      }
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      BlockPosition blockposition1 = blockposition.c();
      if (worldserver.w(blockposition1)) {
         int i = 1;

         while(worldserver.a_(blockposition.c(i)).a(this)) {
            ++i;
         }

         if (i < 3) {
            int j = iblockdata.c(a);
            int modifier = worldserver.spigotConfig.cactusModifier;
            if (j < 15 && (modifier == 100 || !(randomsource.i() < (float)modifier / 1600.0F))) {
               if (modifier == 100 || randomsource.i() < (float)modifier / 1600.0F) {
                  worldserver.a(blockposition, iblockdata.a(a, Integer.valueOf(j + 1)), 4);
               }
            } else {
               CraftEventFactory.handleBlockGrowEvent(worldserver, blockposition1, this.o());
               IBlockData iblockdata1 = iblockdata.a(a, Integer.valueOf(0));
               worldserver.a(blockposition, iblockdata1, 4);
               worldserver.a(iblockdata1, blockposition1, this, blockposition, false);
            }
         }
      }
   }

   @Override
   public VoxelShape c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return d;
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return e;
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
      for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.a) {
         IBlockData iblockdata2 = iworldreader.a_(blockposition.a(enumdirection));
         Material material = iblockdata2.d();
         if (material.b() || iworldreader.b_(blockposition.a(enumdirection)).a(TagsFluid.b)) {
            return false;
         }
      }

      IBlockData iblockdata1 = iworldreader.a_(blockposition.d());
      return (iblockdata1.a(Blocks.dP) || iblockdata1.a(Blocks.I) || iblockdata1.a(Blocks.K)) && !iworldreader.a_(blockposition.c()).d().a();
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
      CraftEventFactory.blockDamage = world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w());
      entity.a(world.af().j(), 1.0F);
      CraftEventFactory.blockDamage = null;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a);
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return false;
   }
}
