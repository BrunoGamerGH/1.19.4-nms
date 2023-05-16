package net.minecraft.world.level.block;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.Tilt;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;

public class BigDripleafBlock extends BlockFacingHorizontal implements IBlockFragilePlantElement, IBlockWaterlogged {
   private static final BlockStateBoolean a = BlockProperties.C;
   private static final BlockStateEnum<Tilt> b = BlockProperties.bl;
   private static final int c = -1;
   private static final Object2IntMap<Tilt> d = SystemUtils.a(new Object2IntArrayMap(), object2intarraymap -> {
      object2intarraymap.defaultReturnValue(-1);
      object2intarraymap.put(Tilt.b, 10);
      object2intarraymap.put(Tilt.c, 10);
      object2intarraymap.put(Tilt.d, 100);
   });
   private static final int e = 5;
   private static final int f = 6;
   private static final int g = 11;
   private static final int h = 13;
   private static final Map<Tilt, VoxelShape> i = ImmutableMap.of(
      Tilt.a,
      Block.a(0.0, 11.0, 0.0, 16.0, 15.0, 16.0),
      Tilt.b,
      Block.a(0.0, 11.0, 0.0, 16.0, 15.0, 16.0),
      Tilt.c,
      Block.a(0.0, 11.0, 0.0, 16.0, 13.0, 16.0),
      Tilt.d,
      VoxelShapes.a()
   );
   private static final VoxelShape j = Block.a(0.0, 13.0, 0.0, 16.0, 16.0, 16.0);
   private static final Map<EnumDirection, VoxelShape> k = ImmutableMap.of(
      EnumDirection.c,
      VoxelShapes.b(BigDripleafStemBlock.a, j, OperatorBoolean.e),
      EnumDirection.d,
      VoxelShapes.b(BigDripleafStemBlock.b, j, OperatorBoolean.e),
      EnumDirection.f,
      VoxelShapes.b(BigDripleafStemBlock.c, j, OperatorBoolean.e),
      EnumDirection.e,
      VoxelShapes.b(BigDripleafStemBlock.d, j, OperatorBoolean.e)
   );
   private final Map<IBlockData, VoxelShape> l;

   protected BigDripleafBlock(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(a, Boolean.valueOf(false)).a(aD, EnumDirection.c).a(b, Tilt.a));
      this.l = this.a(BigDripleafBlock::h);
   }

   private static VoxelShape h(IBlockData iblockdata) {
      return VoxelShapes.a(i.get(iblockdata.c(b)), k.get(iblockdata.c(aD)));
   }

   public static void a(GeneratorAccess generatoraccess, RandomSource randomsource, BlockPosition blockposition, EnumDirection enumdirection) {
      int i = MathHelper.a(randomsource, 2, 5);
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = blockposition.j();
      int j = 0;

      while(j < i && a(generatoraccess, blockposition_mutableblockposition, generatoraccess.a_(blockposition_mutableblockposition))) {
         ++j;
         blockposition_mutableblockposition.c(EnumDirection.b);
      }

      int k = blockposition.v() + j - 1;
      blockposition_mutableblockposition.q(blockposition.v());

      while(blockposition_mutableblockposition.v() < k) {
         BigDripleafStemBlock.a(generatoraccess, blockposition_mutableblockposition, generatoraccess.b_(blockposition_mutableblockposition), enumdirection);
         blockposition_mutableblockposition.c(EnumDirection.b);
      }

      a(generatoraccess, blockposition_mutableblockposition, generatoraccess.b_(blockposition_mutableblockposition), enumdirection);
   }

   private static boolean n(IBlockData iblockdata) {
      return iblockdata.h() || iblockdata.a(Blocks.G) || iblockdata.a(Blocks.rz);
   }

   protected static boolean a(LevelHeightAccessor levelheightaccessor, BlockPosition blockposition, IBlockData iblockdata) {
      return !levelheightaccessor.u(blockposition) && n(iblockdata);
   }

   protected static boolean a(GeneratorAccess generatoraccess, BlockPosition blockposition, Fluid fluid, EnumDirection enumdirection) {
      IBlockData iblockdata = Blocks.rx.o().a(a, Boolean.valueOf(fluid.a(FluidTypes.c))).a(aD, enumdirection);
      return generatoraccess.a(blockposition, iblockdata, 3);
   }

   @Override
   public void a(World world, IBlockData iblockdata, MovingObjectPositionBlock movingobjectpositionblock, IProjectile iprojectile) {
      this.setTiltAndScheduleTick(iblockdata, world, movingobjectpositionblock.a(), Tilt.d, SoundEffects.gq, iprojectile);
   }

   @Override
   public Fluid c_(IBlockData iblockdata) {
      return iblockdata.c(a) ? FluidTypes.c.a(false) : super.c_(iblockdata);
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      BlockPosition blockposition1 = blockposition.d();
      IBlockData iblockdata1 = iworldreader.a_(blockposition1);
      return iblockdata1.a(this) || iblockdata1.a(Blocks.ry) || iblockdata1.a(TagsBlock.bt);
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
      if (enumdirection == EnumDirection.a && !iblockdata.a(generatoraccess, blockposition)) {
         return Blocks.a.o();
      } else {
         if (iblockdata.c(a)) {
            generatoraccess.a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
         }

         return enumdirection == EnumDirection.b && iblockdata1.a(this)
            ? Blocks.ry.l(iblockdata)
            : super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
      }
   }

   @Override
   public boolean a(IWorldReader iworldreader, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
      IBlockData iblockdata1 = iworldreader.a_(blockposition.c());
      return n(iblockdata1);
   }

   @Override
   public boolean a(World world, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      return true;
   }

   @Override
   public void a(WorldServer worldserver, RandomSource randomsource, BlockPosition blockposition, IBlockData iblockdata) {
      BlockPosition blockposition1 = blockposition.c();
      IBlockData iblockdata1 = worldserver.a_(blockposition1);
      if (a(worldserver, blockposition1, iblockdata1)) {
         EnumDirection enumdirection = iblockdata.c(aD);
         BigDripleafStemBlock.a(worldserver, blockposition, iblockdata.r(), enumdirection);
         a(worldserver, blockposition1, iblockdata1.r(), enumdirection);
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
      if (!world.B && iblockdata.c(b) == Tilt.a && a(blockposition, entity) && !world.r(blockposition)) {
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

         this.setTiltAndScheduleTick(iblockdata, world, blockposition, Tilt.b, null, entity);
      }
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (worldserver.r(blockposition)) {
         d(iblockdata, worldserver, blockposition);
      } else {
         Tilt tilt = iblockdata.c(b);
         if (tilt == Tilt.b) {
            this.setTiltAndScheduleTick(iblockdata, worldserver, blockposition, Tilt.c, SoundEffects.gq, null);
         } else if (tilt == Tilt.c) {
            this.setTiltAndScheduleTick(iblockdata, worldserver, blockposition, Tilt.d, SoundEffects.gq, null);
         } else if (tilt == Tilt.d) {
            d(iblockdata, worldserver, blockposition);
         }
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Block block, BlockPosition blockposition1, boolean flag) {
      if (world.r(blockposition)) {
         d(iblockdata, world, blockposition);
      }
   }

   private static void a(World world, BlockPosition blockposition, SoundEffect soundeffect) {
      float f = MathHelper.b(world.z, 0.8F, 1.2F);
      world.a(null, blockposition, soundeffect, SoundCategory.e, 1.0F, f);
   }

   private static boolean a(BlockPosition blockposition, Entity entity) {
      return entity.ax() && entity.de().d > (double)((float)blockposition.v() + 0.6875F);
   }

   private void setTiltAndScheduleTick(
      IBlockData iblockdata, World world, BlockPosition blockposition, Tilt tilt, @Nullable SoundEffect soundeffect, @Nullable Entity entity
   ) {
      if (setTilt(iblockdata, world, blockposition, tilt, entity)) {
         if (soundeffect != null) {
            a(world, blockposition, soundeffect);
         }

         int i = d.getInt(tilt);
         if (i != -1) {
            world.a(blockposition, this, i);
         }
      }
   }

   private static void d(IBlockData iblockdata, World world, BlockPosition blockposition) {
      setTilt(iblockdata, world, blockposition, Tilt.a, null);
      if (iblockdata.c(b) != Tilt.a) {
         a(world, blockposition, SoundEffects.gr);
      }
   }

   private static boolean setTilt(IBlockData iblockdata, World world, BlockPosition blockposition, Tilt tilt, @Nullable Entity entity) {
      if (entity != null && CraftEventFactory.callEntityChangeBlockEvent(entity, blockposition, iblockdata.a(b, tilt)).isCancelled()) {
         return false;
      } else {
         Tilt tilt1 = iblockdata.c(b);
         world.a(blockposition, iblockdata.a(b, tilt), 2);
         if (tilt.a() && tilt != tilt1) {
            world.a(null, GameEvent.c, blockposition);
         }

         return true;
      }
   }

   @Override
   public VoxelShape c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return i.get(iblockdata.c(b));
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return this.l.get(iblockdata);
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      IBlockData iblockdata = blockactioncontext.q().a_(blockactioncontext.a().d());
      Fluid fluid = blockactioncontext.q().b_(blockactioncontext.a());
      boolean flag = iblockdata.a(Blocks.rx) || iblockdata.a(Blocks.ry);
      return this.o().a(a, Boolean.valueOf(fluid.a(FluidTypes.c))).a(aD, flag ? iblockdata.c(aD) : blockactioncontext.g().g());
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a, aD, b);
   }
}
