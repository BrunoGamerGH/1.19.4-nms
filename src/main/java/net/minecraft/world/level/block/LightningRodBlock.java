package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLightning;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.projectile.EntityThrownTrident;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.weather.LightningStrikeEvent.Cause;

public class LightningRodBlock extends RodBlock implements IBlockWaterlogged {
   public static final BlockStateBoolean b = BlockProperties.C;
   public static final BlockStateBoolean c = BlockProperties.w;
   private static final int j = 8;
   public static final int d = 128;
   private static final int k = 200;

   public LightningRodBlock(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(a, EnumDirection.b).a(b, Boolean.valueOf(false)).a(c, Boolean.valueOf(false)));
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      Fluid fluid = blockactioncontext.q().b_(blockactioncontext.a());
      boolean flag = fluid.a() == FluidTypes.c;
      return this.o().a(a, blockactioncontext.k()).a(b, Boolean.valueOf(flag));
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
      if (iblockdata.c(b)) {
         generatoraccess.a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
      }

      return super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   @Override
   public Fluid c_(IBlockData iblockdata) {
      return iblockdata.c(b) ? FluidTypes.c.a(false) : super.c_(iblockdata);
   }

   @Override
   public int a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return iblockdata.c(c) ? 15 : 0;
   }

   @Override
   public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return iblockdata.c(c) && iblockdata.c(a) == enumdirection ? 15 : 0;
   }

   public void d(IBlockData iblockdata, World world, BlockPosition blockposition) {
      boolean powered = iblockdata.c(c);
      int old = powered ? 15 : 0;
      int current = !powered ? 15 : 0;
      BlockRedstoneEvent eventRedstone = new BlockRedstoneEvent(CraftBlock.at(world, blockposition), old, current);
      world.getCraftServer().getPluginManager().callEvent(eventRedstone);
      if (eventRedstone.getNewCurrent() > 0) {
         world.a(blockposition, iblockdata.a(c, Boolean.valueOf(true)), 3);
         this.e(iblockdata, world, blockposition);
         world.a(blockposition, this, 8);
         world.c(3002, blockposition, iblockdata.c(a).o().ordinal());
      }
   }

   private void e(IBlockData iblockdata, World world, BlockPosition blockposition) {
      world.a(blockposition.a(iblockdata.c(a).g()), this);
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      worldserver.a(blockposition, iblockdata.a(c, Boolean.valueOf(false)), 3);
      this.e(iblockdata, worldserver, blockposition);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, RandomSource randomsource) {
      if (world.X() && (long)world.z.a(200) <= world.U() % 200L && blockposition.v() == world.a(HeightMap.Type.b, blockposition.u(), blockposition.w()) - 1) {
         ParticleUtils.a(iblockdata.c(a).o(), world, blockposition, 0.125, Particles.aP, UniformInt.a(1, 2));
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!iblockdata.a(iblockdata1.b())) {
         if (iblockdata.c(c)) {
            this.e(iblockdata, world, blockposition);
         }

         super.a(iblockdata, world, blockposition, iblockdata1, flag);
      }
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!iblockdata.a(iblockdata1.b()) && iblockdata.c(c) && !world.K().a(blockposition, this)) {
         world.a(blockposition, iblockdata.a(c, Boolean.valueOf(false)), 18);
      }
   }

   @Override
   public void a(World world, IBlockData iblockdata, MovingObjectPositionBlock movingobjectpositionblock, IProjectile iprojectile) {
      if (world.X() && iprojectile instanceof EntityThrownTrident && ((EntityThrownTrident)iprojectile).C()) {
         BlockPosition blockposition = movingobjectpositionblock.a();
         if (world.g(blockposition)) {
            EntityLightning entitylightning = EntityTypes.ai.a(world);
            if (entitylightning != null) {
               entitylightning.d(Vec3D.c(blockposition.c()));
               Entity entity = iprojectile.v();
               entitylightning.b(entity instanceof EntityPlayer ? (EntityPlayer)entity : null);
               ((WorldServer)world).strikeLightning(entitylightning, Cause.TRIDENT);
            }

            world.a(null, blockposition, SoundEffects.xv, SoundCategory.d, 5.0F, 1.0F);
         }
      }
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a, c, b);
   }

   @Override
   public boolean f_(IBlockData iblockdata) {
      return true;
   }
}
