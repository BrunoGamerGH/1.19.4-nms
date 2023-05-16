package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.BlockStateInteger;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.entity.EntityInteractEvent;

public class SculkSensorBlock extends BlockTileEntity implements IBlockWaterlogged {
   public static final int a = 40;
   public static final int b = 1;
   public static final BlockStateEnum<SculkSensorPhase> c = BlockProperties.bo;
   public static final BlockStateInteger d = BlockProperties.aT;
   public static final BlockStateBoolean e = BlockProperties.C;
   protected static final VoxelShape f = Block.a(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   private final int g;

   public SculkSensorBlock(BlockBase.Info blockbase_info, int i) {
      super(blockbase_info);
      this.k(this.D.b().a(c, SculkSensorPhase.a).a(d, Integer.valueOf(0)).a(e, Boolean.valueOf(false)));
      this.g = i;
   }

   public int d() {
      return this.g;
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      BlockPosition blockposition = blockactioncontext.a();
      Fluid fluid = blockactioncontext.q().b_(blockposition);
      return this.o().a(e, Boolean.valueOf(fluid.a() == FluidTypes.c));
   }

   @Override
   public Fluid c_(IBlockData iblockdata) {
      return iblockdata.c(e) ? FluidTypes.c.a(false) : super.c_(iblockdata);
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (h(iblockdata) != SculkSensorPhase.b) {
         if (h(iblockdata) == SculkSensorPhase.c) {
            worldserver.a(blockposition, iblockdata.a(c, SculkSensorPhase.a), 3);
         }
      } else {
         a(worldserver, blockposition, iblockdata);
      }
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, Entity entity) {
      if (!world.k_() && n(iblockdata) && entity.ae() != EntityTypes.bi) {
         Cancellable cancellable;
         if (entity instanceof EntityHuman) {
            cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, Action.PHYSICAL, blockposition, null, null, null);
         } else {
            cancellable = new EntityInteractEvent(
               entity.getBukkitEntity(), world.getWorld().getBlockAt(blockposition.u(), blockposition.v(), blockposition.w())
            );
            world.getCraftServer().getPluginManager().callEvent((EntityInteractEvent)cancellable);
         }

         if (cancellable.isCancelled()) {
            return;
         }

         TileEntity tileentity = world.c_(blockposition);
         if (tileentity instanceof SculkSensorBlockEntity sculksensorblockentity && world instanceof WorldServer worldserver) {
            sculksensorblockentity.c().b(worldserver, GameEvent.T, GameEvent.a.a(entity), entity.de());
         }
      }

      super.a(world, blockposition, iblockdata, entity);
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!world.k_() && !iblockdata.a(iblockdata1.b())) {
         if (iblockdata.c(d) > 0 && !world.K().a(blockposition, this)) {
            world.a(blockposition, iblockdata.a(d, Integer.valueOf(0)), 18);
         }

         world.a(new BlockPosition(blockposition), iblockdata.b(), 1);
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!iblockdata.a(iblockdata1.b())) {
         if (h(iblockdata) == SculkSensorPhase.b) {
            a(world, blockposition);
         }

         super.a(iblockdata, world, blockposition, iblockdata1, flag);
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
      if (iblockdata.c(e)) {
         generatoraccess.a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
      }

      return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   private static void a(World world, BlockPosition blockposition) {
      world.a(blockposition, Blocks.qz);
      world.a(blockposition.a(EnumDirection.b.g()), Blocks.qz);
   }

   @Nullable
   @Override
   public TileEntity a(BlockPosition blockposition, IBlockData iblockdata) {
      return new SculkSensorBlockEntity(blockposition, iblockdata);
   }

   @Nullable
   @Override
   public <T extends TileEntity> GameEventListener a(WorldServer worldserver, T t0) {
      return t0 instanceof SculkSensorBlockEntity ? ((SculkSensorBlockEntity)t0).c() : null;
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World world, IBlockData iblockdata, TileEntityTypes<T> tileentitytypes) {
      return !world.B
         ? a(tileentitytypes, TileEntityTypes.I, (world1, blockposition, iblockdata1, sculksensorblockentity) -> sculksensorblockentity.c().a(world1))
         : null;
   }

   @Override
   public EnumRenderType b_(IBlockData iblockdata) {
      return EnumRenderType.c;
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return f;
   }

   @Override
   public boolean f_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public int a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return iblockdata.c(d);
   }

   public static SculkSensorPhase h(IBlockData iblockdata) {
      return iblockdata.c(c);
   }

   public static boolean n(IBlockData iblockdata) {
      return h(iblockdata) == SculkSensorPhase.a;
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(CraftBlock.at(world, blockposition), iblockdata.c(d), 0);
      world.getCraftServer().getPluginManager().callEvent(eventRedstone);
      if (eventRedstone.getNewCurrent() > 0) {
         world.a(blockposition, iblockdata.a(d, Integer.valueOf(eventRedstone.getNewCurrent())), 3);
      } else {
         world.a(blockposition, iblockdata.a(c, SculkSensorPhase.c).a(d, Integer.valueOf(0)), 3);
         world.a(blockposition, iblockdata.b(), 1);
         if (!iblockdata.c(e)) {
            world.a(null, blockposition, SoundEffects.ue, SoundCategory.e, 1.0F, world.z.i() * 0.2F + 0.8F);
         }

         a(world, blockposition);
      }
   }

   public static void a(@Nullable Entity entity, World world, BlockPosition blockposition, IBlockData iblockdata, int i) {
      BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(CraftBlock.at(world, blockposition), iblockdata.c(d), i);
      world.getCraftServer().getPluginManager().callEvent(eventRedstone);
      if (eventRedstone.getNewCurrent() > 0) {
         i = eventRedstone.getNewCurrent();
         world.a(blockposition, iblockdata.a(c, SculkSensorPhase.b).a(d, Integer.valueOf(i)), 3);
         world.a(blockposition, iblockdata.b(), 40);
         a(world, blockposition);
         world.a(entity, GameEvent.P, blockposition);
         if (!iblockdata.c(e)) {
            world.a(
               null,
               (double)blockposition.u() + 0.5,
               (double)blockposition.v() + 0.5,
               (double)blockposition.w() + 0.5,
               SoundEffects.ud,
               SoundCategory.e,
               1.0F,
               world.z.i() * 0.2F + 0.8F
            );
         }
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, RandomSource randomsource) {
      if (h(iblockdata) == SculkSensorPhase.b) {
         EnumDirection enumdirection = EnumDirection.b(randomsource);
         if (enumdirection != EnumDirection.b && enumdirection != EnumDirection.a) {
            double d0 = (double)blockposition.u() + 0.5 + (enumdirection.j() == 0 ? 0.5 - randomsource.j() : (double)enumdirection.j() * 0.6);
            double d1 = (double)blockposition.v() + 0.25;
            double d2 = (double)blockposition.w() + 0.5 + (enumdirection.l() == 0 ? 0.5 - randomsource.j() : (double)enumdirection.l() * 0.6);
            double d3 = (double)randomsource.i() * 0.04;
            world.a(DustColorTransitionOptions.b, d0, d1, d2, 0.0, d3, 0.0);
         }
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(c, d, e);
   }

   @Override
   public boolean d_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public int a(IBlockData iblockdata, World world, BlockPosition blockposition) {
      TileEntity tileentity = world.c_(blockposition);
      if (tileentity instanceof SculkSensorBlockEntity sculksensorblockentity) {
         return h(iblockdata) == SculkSensorPhase.b ? sculksensorblockentity.d() : 0;
      } else {
         return 0;
      }
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return false;
   }

   @Override
   public boolean g_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, ItemStack itemstack, boolean flag) {
      super.a(iblockdata, worldserver, blockposition, itemstack, flag);
   }

   @Override
   public int getExpDrop(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, ItemStack itemstack, boolean flag) {
      return flag ? this.tryDropExperience(worldserver, blockposition, itemstack, ConstantInt.a(5)) : 0;
   }
}
