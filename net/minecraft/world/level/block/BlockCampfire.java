package net.minecraft.world.level.block;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.Particles;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InventoryUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.item.crafting.RecipeCampfire;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityCampfire;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class BlockCampfire extends BlockTileEntity implements IBlockWaterlogged {
   protected static final VoxelShape a = Block.a(0.0, 0.0, 0.0, 16.0, 7.0, 16.0);
   public static final BlockStateBoolean b = BlockProperties.r;
   public static final BlockStateBoolean c = BlockProperties.y;
   public static final BlockStateBoolean d = BlockProperties.C;
   public static final BlockStateDirection e = BlockProperties.R;
   private static final VoxelShape f = Block.a(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
   private static final int g = 5;
   private final boolean h;
   private final int i;

   public BlockCampfire(boolean flag, int i, BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.h = flag;
      this.i = i;
      this.k(this.D.b().a(b, Boolean.valueOf(true)).a(c, Boolean.valueOf(false)).a(d, Boolean.valueOf(false)).a(e, EnumDirection.c));
   }

   @Override
   public EnumInteractionResult a(
      IBlockData iblockdata,
      World world,
      BlockPosition blockposition,
      EntityHuman entityhuman,
      EnumHand enumhand,
      MovingObjectPositionBlock movingobjectpositionblock
   ) {
      TileEntity tileentity = world.c_(blockposition);
      if (tileentity instanceof TileEntityCampfire tileentitycampfire) {
         ItemStack itemstack = entityhuman.b(enumhand);
         Optional<RecipeCampfire> optional = tileentitycampfire.a(itemstack);
         if (optional.isPresent()) {
            if (!world.B && tileentitycampfire.a(entityhuman, entityhuman.fK().d ? itemstack.o() : itemstack, optional.get().d())) {
               entityhuman.a(StatisticList.av);
               return EnumInteractionResult.a;
            }

            return EnumInteractionResult.b;
         }
      }

      return EnumInteractionResult.d;
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Entity entity) {
      if (iblockdata.c(b) && entity instanceof EntityLiving && !EnchantmentManager.j((EntityLiving)entity)) {
         CraftEventFactory.blockDamage = CraftBlock.at(world, blockposition);
         entity.a(world.af().a(), (float)this.i);
         CraftEventFactory.blockDamage = null;
      }

      super.a(iblockdata, world, blockposition, entity);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!iblockdata.a(iblockdata1.b())) {
         TileEntity tileentity = world.c_(blockposition);
         if (tileentity instanceof TileEntityCampfire) {
            InventoryUtils.a(world, blockposition, ((TileEntityCampfire)tileentity).c());
         }

         super.a(iblockdata, world, blockposition, iblockdata1, flag);
      }
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      World world = blockactioncontext.q();
      BlockPosition blockposition = blockactioncontext.a();
      boolean flag = world.b_(blockposition).a() == FluidTypes.c;
      return this.o()
         .a(d, Boolean.valueOf(flag))
         .a(c, Boolean.valueOf(this.n(world.a_(blockposition.d()))))
         .a(b, Boolean.valueOf(!flag))
         .a(e, blockactioncontext.g());
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

      return enumdirection == EnumDirection.a
         ? iblockdata.a(c, Boolean.valueOf(this.n(iblockdata1)))
         : super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   private boolean n(IBlockData iblockdata) {
      return iblockdata.a(Blocks.ii);
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return a;
   }

   @Override
   public EnumRenderType b_(IBlockData iblockdata) {
      return EnumRenderType.c;
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, RandomSource randomsource) {
      if (iblockdata.c(b)) {
         if (randomsource.a(10) == 0) {
            world.a(
               (double)blockposition.u() + 0.5,
               (double)blockposition.v() + 0.5,
               (double)blockposition.w() + 0.5,
               SoundEffects.cX,
               SoundCategory.e,
               0.5F + randomsource.i(),
               randomsource.i() * 0.7F + 0.6F,
               false
            );
         }

         if (this.h && randomsource.a(5) == 0) {
            for(int i = 0; i < randomsource.a(1) + 1; ++i) {
               world.a(
                  Particles.V,
                  (double)blockposition.u() + 0.5,
                  (double)blockposition.v() + 0.5,
                  (double)blockposition.w() + 0.5,
                  (double)(randomsource.i() / 2.0F),
                  5.0E-5,
                  (double)(randomsource.i() / 2.0F)
               );
            }
         }
      }
   }

   public static void a(@Nullable Entity entity, GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata) {
      if (generatoraccess.k_()) {
         for(int i = 0; i < 20; ++i) {
            a((World)generatoraccess, blockposition, iblockdata.c(c), true);
         }
      }

      TileEntity tileentity = generatoraccess.c_(blockposition);
      if (tileentity instanceof TileEntityCampfire) {
         ((TileEntityCampfire)tileentity).f();
      }

      generatoraccess.a(entity, GameEvent.c, blockposition);
   }

   @Override
   public boolean a(GeneratorAccess generatoraccess, BlockPosition blockposition, IBlockData iblockdata, Fluid fluid) {
      if (!iblockdata.c(BlockProperties.C) && fluid.a() == FluidTypes.c) {
         boolean flag = iblockdata.c(b);
         if (flag) {
            if (!generatoraccess.k_()) {
               generatoraccess.a(null, blockposition, SoundEffects.iP, SoundCategory.e, 1.0F, 1.0F);
            }

            a(null, generatoraccess, blockposition, iblockdata);
         }

         generatoraccess.a(blockposition, iblockdata.a(d, Boolean.valueOf(true)).a(b, Boolean.valueOf(false)), 3);
         generatoraccess.a(blockposition, fluid.a(), fluid.a().a(generatoraccess));
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void a(World world, IBlockData iblockdata, MovingObjectPositionBlock movingobjectpositionblock, IProjectile iprojectile) {
      BlockPosition blockposition = movingobjectpositionblock.a();
      if (!world.B && iprojectile.bK() && iprojectile.a(world, blockposition) && !iblockdata.c(b) && !iblockdata.c(d)) {
         if (CraftEventFactory.callBlockIgniteEvent(world, blockposition, iprojectile).isCancelled()) {
            return;
         }

         world.a(blockposition, iblockdata.a(BlockProperties.r, Boolean.valueOf(true)), 11);
      }
   }

   public static void a(World world, BlockPosition blockposition, boolean flag, boolean flag1) {
      RandomSource randomsource = world.r_();
      ParticleType particletype = flag ? Particles.aq : Particles.ap;
      world.b(
         particletype,
         true,
         (double)blockposition.u() + 0.5 + randomsource.j() / 3.0 * (double)(randomsource.h() ? 1 : -1),
         (double)blockposition.v() + randomsource.j() + randomsource.j(),
         (double)blockposition.w() + 0.5 + randomsource.j() / 3.0 * (double)(randomsource.h() ? 1 : -1),
         0.0,
         0.07,
         0.0
      );
      if (flag1) {
         world.a(
            Particles.ab,
            (double)blockposition.u() + 0.5 + randomsource.j() / 4.0 * (double)(randomsource.h() ? 1 : -1),
            (double)blockposition.v() + 0.4,
            (double)blockposition.w() + 0.5 + randomsource.j() / 4.0 * (double)(randomsource.h() ? 1 : -1),
            0.0,
            0.005,
            0.0
         );
      }
   }

   public static boolean a(World world, BlockPosition blockposition) {
      for(int i = 1; i <= 5; ++i) {
         BlockPosition blockposition1 = blockposition.c(i);
         IBlockData iblockdata = world.a_(blockposition1);
         if (g(iblockdata)) {
            return true;
         }

         boolean flag = VoxelShapes.c(f, iblockdata.b(world, blockposition, VoxelShapeCollision.a()), OperatorBoolean.i);
         if (flag) {
            IBlockData iblockdata1 = world.a_(blockposition1.d());
            return g(iblockdata1);
         }
      }

      return false;
   }

   public static boolean g(IBlockData iblockdata) {
      return iblockdata.b(b) && iblockdata.a(TagsBlock.aS) && iblockdata.c(b);
   }

   @Override
   public Fluid c_(IBlockData iblockdata) {
      return iblockdata.c(d) ? FluidTypes.c.a(false) : super.c_(iblockdata);
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
      return iblockdata.a(e, enumblockrotation.a(iblockdata.c(e)));
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
      return iblockdata.a(enumblockmirror.a(iblockdata.c(e)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(b, c, d, e);
   }

   @Override
   public TileEntity a(BlockPosition blockposition, IBlockData iblockdata) {
      return new TileEntityCampfire(blockposition, iblockdata);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World world, IBlockData iblockdata, TileEntityTypes<T> tileentitytypes) {
      return world.B
         ? (iblockdata.c(b) ? a(tileentitytypes, TileEntityTypes.G, TileEntityCampfire::c) : null)
         : (iblockdata.c(b) ? a(tileentitytypes, TileEntityTypes.G, TileEntityCampfire::a) : a(tileentitytypes, TileEntityTypes.G, TileEntityCampfire::b));
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return false;
   }

   public static boolean h(IBlockData iblockdata) {
      return iblockdata.a(TagsBlock.aS, blockbase_blockdata -> blockbase_blockdata.b(d) && blockbase_blockdata.b(b)) && !iblockdata.c(d) && !iblockdata.c(b);
   }
}
