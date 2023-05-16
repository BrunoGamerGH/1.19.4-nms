package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemSword;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyBambooSize;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockBamboo extends Block implements IBlockFragilePlantElement {
   protected static final float a = 3.0F;
   protected static final float b = 5.0F;
   protected static final float c = 1.5F;
   protected static final VoxelShape d = Block.a(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
   protected static final VoxelShape e = Block.a(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
   protected static final VoxelShape f = Block.a(6.5, 0.0, 6.5, 9.5, 16.0, 9.5);
   public static final BlockStateInteger g = BlockProperties.aq;
   public static final BlockStateEnum<BlockPropertyBambooSize> h = BlockProperties.bk;
   public static final BlockStateInteger i = BlockProperties.aU;
   public static final int j = 16;
   public static final int k = 0;
   public static final int l = 1;
   public static final int m = 0;
   public static final int n = 1;

   public BlockBamboo(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(g, Integer.valueOf(0)).a(h, BlockPropertyBambooSize.a).a(i, Integer.valueOf(0)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(g, h, i);
   }

   @Override
   public boolean c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return true;
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      VoxelShape voxelshape = iblockdata.c(h) == BlockPropertyBambooSize.c ? e : d;
      Vec3D vec3d = iblockdata.n(iblockaccess, blockposition);
      return voxelshape.a(vec3d.c, vec3d.d, vec3d.e);
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return false;
   }

   @Override
   public VoxelShape c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      Vec3D vec3d = iblockdata.n(iblockaccess, blockposition);
      return f.a(vec3d.c, vec3d.d, vec3d.e);
   }

   @Override
   public boolean a_(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return false;
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      Fluid fluid = blockactioncontext.q().b_(blockactioncontext.a());
      if (!fluid.c()) {
         return null;
      } else {
         IBlockData iblockdata = blockactioncontext.q().a_(blockactioncontext.a().d());
         if (iblockdata.a(TagsBlock.ar)) {
            if (iblockdata.a(Blocks.mU)) {
               return this.o().a(g, Integer.valueOf(0));
            } else if (iblockdata.a(Blocks.mV)) {
               int i = iblockdata.c(g) > 0 ? 1 : 0;
               return this.o().a(g, Integer.valueOf(i));
            } else {
               IBlockData iblockdata1 = blockactioncontext.q().a_(blockactioncontext.a().c());
               return iblockdata1.a(Blocks.mV) ? this.o().a(g, iblockdata1.c(g)) : Blocks.mU.o();
            }
         } else {
            return null;
         }
      }
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (!iblockdata.a(worldserver, blockposition)) {
         worldserver.b(blockposition, true);
      }
   }

   @Override
   public boolean e_(IBlockData iblockdata) {
      return iblockdata.c(i) == 0;
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (iblockdata.c(BlockBamboo.i) == 0
         && randomsource.i() < (float)worldserver.spigotConfig.bambooModifier / 300.0F
         && worldserver.w(blockposition.c())
         && worldserver.b(blockposition.c(), 0) >= 9) {
         int i = this.b(worldserver, blockposition) + 1;
         if (i < 16) {
            this.a(iblockdata, worldserver, blockposition, randomsource, i);
         }
      }
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      return iworldreader.a_(blockposition.d()).a(TagsBlock.ar);
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

      if (enumdirection == EnumDirection.b && iblockdata1.a(Blocks.mV) && iblockdata1.c(g) > iblockdata.c(g)) {
         generatoraccess.a(blockposition, iblockdata.a(g), 2);
      }

      return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public boolean a(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
      int i = this.a(iworldreader, blockposition);
      int j = this.b(iworldreader, blockposition);
      return i + j + 1 < 16 && iworldreader.a_(blockposition.b(i)).c(BlockBamboo.i) != 1;
   }

   @Override
   public boolean a(World world, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      return true;
   }

   @Override
   public void a(WorldServer worldserver, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      int i = this.a(worldserver, blockposition);
      int j = this.b(worldserver, blockposition);
      int k = i + j + 1;
      int l = 1 + randomsource.a(2);

      for(int i1 = 0; i1 < l; ++i1) {
         BlockPosition blockposition1 = blockposition.b(i);
         IBlockData iblockdata1 = worldserver.a_(blockposition1);
         if (k >= 16 || !iblockdata1.a(Blocks.mV) || iblockdata1.c(BlockBamboo.i) == 1 || !worldserver.w(blockposition1.c())) {
            return;
         }

         this.a(iblockdata1, worldserver, blockposition1, randomsource, k);
         ++i;
         ++k;
      }
   }

   @Override
   public float a(IBlockData iblockdata, EntityHuman entityhuman, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return entityhuman.eK().c() instanceof ItemSword ? 1.0F : super.a(iblockdata, entityhuman, iblockaccess, blockposition);
   }

   protected void a(IBlockData iblockdata, World world, BlockPosition blockposition, RandomSource randomsource, int i) {
      IBlockData iblockdata1 = world.a_(blockposition.d());
      BlockPosition blockposition1 = blockposition.c(2);
      IBlockData iblockdata2 = world.a_(blockposition1);
      BlockPropertyBambooSize blockpropertybamboosize = BlockPropertyBambooSize.a;
      boolean shouldUpdateOthers = false;
      if (i >= 1) {
         if (!iblockdata1.a(Blocks.mV) || iblockdata1.c(h) == BlockPropertyBambooSize.a) {
            blockpropertybamboosize = BlockPropertyBambooSize.b;
         } else if (iblockdata1.a(Blocks.mV) && iblockdata1.c(h) != BlockPropertyBambooSize.a) {
            blockpropertybamboosize = BlockPropertyBambooSize.c;
            if (iblockdata2.a(Blocks.mV)) {
               shouldUpdateOthers = true;
            }
         }
      }

      int j = iblockdata.c(g) != 1 && !iblockdata2.a(Blocks.mV) ? 0 : 1;
      int k = (i < 11 || randomsource.i() >= 0.25F) && i != 15 ? 0 : 1;
      if (CraftEventFactory.handleBlockSpreadEvent(
            world, blockposition, blockposition.c(), this.o().a(g, Integer.valueOf(j)).a(h, blockpropertybamboosize).a(BlockBamboo.i, Integer.valueOf(k)), 3
         )
         && shouldUpdateOthers) {
         world.a(blockposition.d(), iblockdata1.a(h, BlockPropertyBambooSize.b), 3);
         world.a(blockposition1, iblockdata2.a(h, BlockPropertyBambooSize.a), 3);
      }
   }

   protected int a(IBlockAccess iblockaccess, BlockPosition blockposition) {
      int i = 0;

      while(i < 16 && iblockaccess.a_(blockposition.b(i + 1)).a(Blocks.mV)) {
         ++i;
      }

      return i;
   }

   protected int b(IBlockAccess iblockaccess, BlockPosition blockposition) {
      int i = 0;

      while(i < 16 && iblockaccess.a_(blockposition.c(i + 1)).a(Blocks.mV)) {
         ++i;
      }

      return i;
   }
}
