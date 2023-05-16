package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.event.block.LeavesDecayEvent;

public class BlockLeaves extends Block implements IBlockWaterlogged {
   public static final int a = 7;
   public static final BlockStateInteger b = BlockProperties.aC;
   public static final BlockStateBoolean c = BlockProperties.v;
   public static final BlockStateBoolean d = BlockProperties.C;
   private static final int e = 1;

   public BlockLeaves(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(b, Integer.valueOf(7)).a(c, Boolean.valueOf(false)).a(d, Boolean.valueOf(false)));
   }

   @Override
   public VoxelShape b_(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return VoxelShapes.a();
   }

   @Override
   public boolean e_(IBlockData iblockdata) {
      return iblockdata.c(b) == 7 && !iblockdata.c(c);
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (this.h(iblockdata)) {
         LeavesDecayEvent event = new LeavesDecayEvent(worldserver.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w()));
         worldserver.getCraftServer().getPluginManager().callEvent(event);
         if (event.isCancelled() || worldserver.a_(blockposition).b() != this) {
            return;
         }

         c(iblockdata, worldserver, blockposition);
         worldserver.a(blockposition, false);
      }
   }

   protected boolean h(IBlockData iblockdata) {
      return !iblockdata.c(c) && iblockdata.c(b) == 7;
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      worldserver.a(blockposition, a(iblockdata, worldserver, blockposition), 3);
   }

   @Override
   public int g(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return 1;
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
      if (iblockdata.c(d)) {
         generatoraccess.a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
      }

      int i = n(iblockdata1) + 1;
      if (i != 1 || iblockdata.c(b) != i) {
         generatoraccess.a(blockposition, this, 1);
      }

      return iblockdata;
   }

   private static IBlockData a(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition) {
      int i = 7;
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(EnumDirection enumdirection : EnumDirection.values()) {
         blockposition_mutableblockposition.a(blockposition, enumdirection);
         i = Math.min(i, n(generatoraccess.a_(blockposition_mutableblockposition)) + 1);
         if (i == 1) {
            break;
         }
      }

      return iblockdata.a(b, Integer.valueOf(i));
   }

   private static int n(IBlockData iblockdata) {
      return iblockdata.a(TagsBlock.s) ? 0 : (iblockdata.b() instanceof BlockLeaves ? iblockdata.c(b) : 7);
   }

   @Override
   public Fluid c_(IBlockData iblockdata) {
      return iblockdata.c(d) ? FluidTypes.c.a(false) : super.c_(iblockdata);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, RandomSource randomsource) {
      if (world.t(blockposition.c()) && randomsource.a(15) == 1) {
         BlockPosition blockposition1 = blockposition.d();
         IBlockData iblockdata1 = world.a_(blockposition1);
         if (!iblockdata1.m() || !iblockdata1.d(world, blockposition1, EnumDirection.b)) {
            ParticleUtils.a(world, blockposition, randomsource, Particles.m);
         }
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(b, c, d);
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      Fluid fluid = blockactioncontext.q().b_(blockactioncontext.a());
      IBlockData iblockdata = this.o().a(c, Boolean.valueOf(true)).a(d, Boolean.valueOf(fluid.a() == FluidTypes.c));
      return a(iblockdata, blockactioncontext.q(), blockactioncontext.a());
   }
}
