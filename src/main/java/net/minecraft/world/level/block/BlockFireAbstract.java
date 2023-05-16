package net.minecraft.world.level.block;

import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.dimension.WorldDimension;
import net.minecraft.world.level.portal.BlockPortalShape;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustEvent;

public abstract class BlockFireAbstract extends Block {
   private static final int c = 8;
   private final float d;
   protected static final float a = 1.0F;
   protected static final VoxelShape b = Block.a(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);

   public BlockFireAbstract(BlockBase.Info blockbase_info, float f) {
      super(blockbase_info);
      this.d = f;
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      return a(blockactioncontext.q(), blockactioncontext.a());
   }

   public static IBlockData a(IBlockAccess iblockaccess, BlockPosition blockposition) {
      BlockPosition blockposition1 = blockposition.d();
      IBlockData iblockdata = iblockaccess.a_(blockposition1);
      return BlockSoulFire.h(iblockdata) ? Blocks.cr.o() : ((BlockFire)Blocks.cq).b(iblockaccess, blockposition);
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return b;
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, RandomSource randomsource) {
      if (randomsource.a(24) == 0) {
         world.a(
            (double)blockposition.u() + 0.5,
            (double)blockposition.v() + 0.5,
            (double)blockposition.w() + 0.5,
            SoundEffects.hI,
            SoundCategory.e,
            1.0F + randomsource.i(),
            randomsource.i() * 0.7F + 0.3F,
            false
         );
      }

      BlockPosition blockposition1 = blockposition.d();
      IBlockData iblockdata1 = world.a_(blockposition1);
      if (!this.f(iblockdata1) && !iblockdata1.d(world, blockposition1, EnumDirection.b)) {
         if (this.f(world.a_(blockposition.g()))) {
            for(int i = 0; i < 2; ++i) {
               double d0 = (double)blockposition.u() + randomsource.j() * 0.1F;
               double d1 = (double)blockposition.v() + randomsource.j();
               double d2 = (double)blockposition.w() + randomsource.j();
               world.a(Particles.U, d0, d1, d2, 0.0, 0.0, 0.0);
            }
         }

         if (this.f(world.a_(blockposition.h()))) {
            for(int i = 0; i < 2; ++i) {
               double d0 = (double)(blockposition.u() + 1) - randomsource.j() * 0.1F;
               double d1 = (double)blockposition.v() + randomsource.j();
               double d2 = (double)blockposition.w() + randomsource.j();
               world.a(Particles.U, d0, d1, d2, 0.0, 0.0, 0.0);
            }
         }

         if (this.f(world.a_(blockposition.e()))) {
            for(int i = 0; i < 2; ++i) {
               double d0 = (double)blockposition.u() + randomsource.j();
               double d1 = (double)blockposition.v() + randomsource.j();
               double d2 = (double)blockposition.w() + randomsource.j() * 0.1F;
               world.a(Particles.U, d0, d1, d2, 0.0, 0.0, 0.0);
            }
         }

         if (this.f(world.a_(blockposition.f()))) {
            for(int i = 0; i < 2; ++i) {
               double d0 = (double)blockposition.u() + randomsource.j();
               double d1 = (double)blockposition.v() + randomsource.j();
               double d2 = (double)(blockposition.w() + 1) - randomsource.j() * 0.1F;
               world.a(Particles.U, d0, d1, d2, 0.0, 0.0, 0.0);
            }
         }

         if (this.f(world.a_(blockposition.c()))) {
            for(int i = 0; i < 2; ++i) {
               double d0 = (double)blockposition.u() + randomsource.j();
               double d1 = (double)(blockposition.v() + 1) - randomsource.j() * 0.1F;
               double d2 = (double)blockposition.w() + randomsource.j();
               world.a(Particles.U, d0, d1, d2, 0.0, 0.0, 0.0);
            }
         }
      } else {
         for(int i = 0; i < 3; ++i) {
            double d0 = (double)blockposition.u() + randomsource.j();
            double d1 = (double)blockposition.v() + randomsource.j() * 0.5 + 0.5;
            double d2 = (double)blockposition.w() + randomsource.j();
            world.a(Particles.U, d0, d1, d2, 0.0, 0.0, 0.0);
         }
      }
   }

   protected abstract boolean f(IBlockData var1);

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
      if (!entity.aS()) {
         entity.g(entity.au() + 1);
         if (entity.au() == 0) {
            EntityCombustEvent event = new EntityCombustByBlockEvent(CraftBlock.at(world, blockposition), entity.getBukkitEntity(), 8);
            world.getCraftServer().getPluginManager().callEvent(event);
            if (!event.isCancelled()) {
               entity.setSecondsOnFire(event.getDuration(), false);
            }
         }
      }

      entity.a(world.af().a(), this.d);
      super.a(iblockdata, world, blockposition, entity);
   }

   @Override
   public void b(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!iblockdata1.a(iblockdata.b())) {
         if (a(world)) {
            Optional<BlockPortalShape> optional = BlockPortalShape.a(world, blockposition, EnumDirection.EnumAxis.a);
            if (optional.isPresent()) {
               optional.get().createPortalBlocks();
               return;
            }
         }

         if (!iblockdata.a((IWorldReader)world, blockposition)) {
            this.fireExtinguished(world, blockposition);
         }
      }
   }

   private static boolean a(World world) {
      return world.getTypeKey() == WorldDimension.b || world.getTypeKey() == WorldDimension.c;
   }

   @Override
   protected void a(World world, EntityHuman entityhuman, BlockPosition blockposition, IBlockData iblockdata) {
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
      if (!world.k_()) {
         world.a(null, 1009, blockposition, 0);
      }

      super.a(world, blockposition, iblockdata, entityhuman);
   }

   public static boolean a(World world, BlockPosition blockposition, EnumDirection enumdirection) {
      IBlockData iblockdata = world.a_(blockposition);
      return !iblockdata.h() ? false : a(world, blockposition).a((IWorldReader)world, blockposition) || b(world, blockposition, enumdirection);
   }

   private static boolean b(World world, BlockPosition blockposition, EnumDirection enumdirection) {
      if (!a(world)) {
         return false;
      } else {
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = blockposition.j();
         boolean flag = false;

         for(EnumDirection enumdirection1 : EnumDirection.values()) {
            if (world.a_(blockposition_mutableblockposition.g(blockposition).c(enumdirection1)).a(Blocks.cn)) {
               flag = true;
               break;
            }
         }

         if (!flag) {
            return false;
         } else {
            EnumDirection.EnumAxis enumdirection_enumaxis = enumdirection.o().d() ? enumdirection.i().o() : EnumDirection.EnumDirectionLimit.a.b(world.z);
            return BlockPortalShape.a(world, blockposition, enumdirection_enumaxis).isPresent();
         }
      }
   }

   protected void fireExtinguished(GeneratorAccess world, BlockPosition position) {
      if (!CraftEventFactory.callBlockFadeEvent(world, position, Blocks.a.o()).isCancelled()) {
         world.a(position, false);
      }
   }
}
