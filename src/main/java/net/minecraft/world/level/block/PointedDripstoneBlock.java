package net.minecraft.world.level.block;

import com.google.common.annotations.VisibleForTesting;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.Particles;
import net.minecraft.server.level.WorldServer;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.IEntitySelector;
import net.minecraft.world.entity.item.EntityFallingBlock;
import net.minecraft.world.entity.projectile.EntityThrownTrident;
import net.minecraft.world.entity.projectile.IProjectile;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.bukkit.craftbukkit.v1_19_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_19_R3.event.CraftEventFactory;

public class PointedDripstoneBlock extends Block implements Fallable, IBlockWaterlogged {
   public static final BlockStateDirection a = BlockProperties.bm;
   public static final BlockStateEnum<DripstoneThickness> b = BlockProperties.bn;
   public static final BlockStateBoolean c = BlockProperties.C;
   private static final int d = 11;
   private static final int e = 2;
   private static final float f = 0.02F;
   private static final float g = 0.12F;
   private static final int h = 11;
   private static final float i = 0.17578125F;
   private static final float j = 0.05859375F;
   private static final double k = 0.6;
   private static final float l = 1.0F;
   private static final int m = 40;
   private static final int n = 6;
   private static final float E = 2.0F;
   private static final int F = 2;
   private static final float G = 5.0F;
   private static final float H = 0.011377778F;
   private static final int I = 7;
   private static final int J = 10;
   private static final float K = 0.6875F;
   private static final VoxelShape L = Block.a(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
   private static final VoxelShape M = Block.a(5.0, 0.0, 5.0, 11.0, 11.0, 11.0);
   private static final VoxelShape N = Block.a(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);
   private static final VoxelShape O = Block.a(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
   private static final VoxelShape P = Block.a(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
   private static final VoxelShape Q = Block.a(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
   private static final float R = 0.125F;
   private static final VoxelShape S = Block.a(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);

   public PointedDripstoneBlock(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(a, EnumDirection.b).a(b, DripstoneThickness.b).a(c, Boolean.valueOf(false)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a, b, c);
   }

   @Override
   public boolean a(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      return c(iworldreader, blockposition, iblockdata.c(a));
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
      if (iblockdata.c(c)) {
         generatoraccess.a(blockposition, FluidTypes.c, FluidTypes.c.a(generatoraccess));
      }

      if (enumdirection != EnumDirection.b && enumdirection != EnumDirection.a) {
         return iblockdata;
      } else {
         EnumDirection enumdirection1 = iblockdata.c(a);
         if (enumdirection1 == EnumDirection.a && generatoraccess.K().a(blockposition, this)) {
            return iblockdata;
         } else if (enumdirection == enumdirection1.g() && !this.a(iblockdata, (IWorldReader)generatoraccess, blockposition)) {
            if (enumdirection1 == EnumDirection.a) {
               generatoraccess.a(blockposition, this, 2);
            } else {
               generatoraccess.a(blockposition, this, 1);
            }

            return iblockdata;
         } else {
            boolean flag = iblockdata.c(b) == DripstoneThickness.a;
            DripstoneThickness dripstonethickness = a(generatoraccess, blockposition, enumdirection1, flag);
            return iblockdata.a(b, dripstonethickness);
         }
      }
   }

   @Override
   public void a(World world, IBlockData iblockdata, MovingObjectPositionBlock movingobjectpositionblock, IProjectile iprojectile) {
      BlockPosition blockposition = movingobjectpositionblock.a();
      if (!world.B && iprojectile.a(world, blockposition) && iprojectile instanceof EntityThrownTrident && iprojectile.dj().f() > 0.6) {
         if (CraftEventFactory.callEntityChangeBlockEvent(iprojectile, blockposition, Blocks.a.o()).isCancelled()) {
            return;
         }

         world.b(blockposition, true);
      }
   }

   @Override
   public void a(World world, IBlockData iblockdata, BlockPosition blockposition, Entity entity, float f) {
      if (iblockdata.c(a) == EnumDirection.b && iblockdata.c(b) == DripstoneThickness.b) {
         CraftEventFactory.blockDamage = CraftBlock.at(world, blockposition);
         entity.a(f + 2.0F, 2.0F, world.af().u());
         CraftEventFactory.blockDamage = null;
      } else {
         super.a(world, iblockdata, blockposition, entity, f);
      }
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, RandomSource randomsource) {
      if (h(iblockdata)) {
         float f = randomsource.i();
         if (f <= 0.12F) {
            b(world, blockposition, iblockdata)
               .filter(pointeddripstoneblock_a -> f < 0.02F || a(pointeddripstoneblock_a.b))
               .ifPresent(pointeddripstoneblock_a -> a(world, blockposition, iblockdata, pointeddripstoneblock_a.b));
         }
      }
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      if (o(iblockdata) && !this.a(iblockdata, (IWorldReader)worldserver, blockposition)) {
         worldserver.b(blockposition, true);
      } else {
         a(iblockdata, worldserver, blockposition);
      }
   }

   @Override
   public void b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      a(iblockdata, worldserver, blockposition, randomsource.i());
      if (randomsource.i() < 0.011377778F && b(iblockdata, (IWorldReader)worldserver, blockposition)) {
         c(iblockdata, worldserver, blockposition, randomsource);
      }
   }

   @VisibleForTesting
   public static void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, float f) {
      if ((f <= 0.17578125F || f <= 0.05859375F) && b(iblockdata, (IWorldReader)worldserver, blockposition)) {
         Optional<PointedDripstoneBlock.a> optional = b((World)worldserver, blockposition, iblockdata);
         if (!optional.isEmpty()) {
            FluidType fluidtype = optional.get().b;
            float f1;
            if (fluidtype == FluidTypes.c) {
               f1 = 0.17578125F;
            } else {
               if (fluidtype != FluidTypes.e) {
                  return;
               }

               f1 = 0.05859375F;
            }

            if (f < f1) {
               BlockPosition blockposition1 = a(iblockdata, worldserver, blockposition, 11, false);
               if (blockposition1 != null) {
                  if (optional.get().c.a(Blocks.rC) && fluidtype == FluidTypes.c) {
                     IBlockData iblockdata1 = Blocks.dQ.o();
                     worldserver.b(optional.get().a, iblockdata1);
                     Block.a(optional.get().c, iblockdata1, worldserver, optional.get().a);
                     worldserver.a(GameEvent.c, optional.get().a, GameEvent.a.a(iblockdata1));
                     worldserver.c(1504, blockposition1, 0);
                  } else {
                     BlockPosition blockposition2 = a(worldserver, blockposition1, fluidtype);
                     if (blockposition2 != null) {
                        worldserver.c(1504, blockposition1, 0);
                        int i = blockposition1.v() - blockposition2.v();
                        int j = 50 + i;
                        IBlockData iblockdata2 = worldserver.a_(blockposition2);
                        worldserver.a(blockposition2, iblockdata2.b(), j);
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public EnumPistonReaction d(IBlockData iblockdata) {
      return EnumPistonReaction.b;
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      World world = blockactioncontext.q();
      BlockPosition blockposition = blockactioncontext.a();
      EnumDirection enumdirection = blockactioncontext.e().g();
      EnumDirection enumdirection1 = b(world, blockposition, enumdirection);
      if (enumdirection1 == null) {
         return null;
      } else {
         boolean flag = !blockactioncontext.h();
         DripstoneThickness dripstonethickness = a(world, blockposition, enumdirection1, flag);
         return dripstonethickness == null
            ? null
            : this.o().a(a, enumdirection1).a(b, dripstonethickness).a(c, Boolean.valueOf(world.b_(blockposition).a() == FluidTypes.c));
      }
   }

   @Override
   public Fluid c_(IBlockData iblockdata) {
      return iblockdata.c(c) ? FluidTypes.c.a(false) : super.c_(iblockdata);
   }

   @Override
   public VoxelShape f(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return VoxelShapes.a();
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      DripstoneThickness dripstonethickness = iblockdata.c(b);
      VoxelShape voxelshape;
      if (dripstonethickness == DripstoneThickness.a) {
         voxelshape = L;
      } else if (dripstonethickness == DripstoneThickness.b) {
         if (iblockdata.c(a) == EnumDirection.a) {
            voxelshape = N;
         } else {
            voxelshape = M;
         }
      } else if (dripstonethickness == DripstoneThickness.c) {
         voxelshape = O;
      } else if (dripstonethickness == DripstoneThickness.d) {
         voxelshape = P;
      } else {
         voxelshape = Q;
      }

      Vec3D vec3d = iblockdata.n(iblockaccess, blockposition);
      return voxelshape.a(vec3d.c, 0.0, vec3d.e);
   }

   @Override
   public boolean a_(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return false;
   }

   @Override
   public float am_() {
      return 0.125F;
   }

   @Override
   public void a(World world, BlockPosition blockposition, EntityFallingBlock entityfallingblock) {
      if (!entityfallingblock.aO()) {
         world.c(1045, blockposition, 0);
      }
   }

   @Override
   public DamageSource a(Entity entity) {
      return entity.dG().c(entity);
   }

   @Override
   public Predicate<Entity> al_() {
      return IEntitySelector.e.and(IEntitySelector.b);
   }

   private static void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition) {
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = blockposition.j();

      for(IBlockData iblockdata1 = iblockdata; n(iblockdata1); iblockdata1 = worldserver.a_(blockposition_mutableblockposition)) {
         EntityFallingBlock entityfallingblock = EntityFallingBlock.a(worldserver, blockposition_mutableblockposition, iblockdata1);
         if (a(iblockdata1, true)) {
            int i = Math.max(1 + blockposition.v() - blockposition_mutableblockposition.v(), 6);
            float f = 1.0F * (float)i;
            entityfallingblock.b(f, 40);
            break;
         }

         blockposition_mutableblockposition.c(EnumDirection.a);
      }
   }

   @VisibleForTesting
   public static void c(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      IBlockData iblockdata1 = worldserver.a_(blockposition.b(1));
      IBlockData iblockdata2 = worldserver.a_(blockposition.b(2));
      if (a(iblockdata1, iblockdata2)) {
         BlockPosition blockposition1 = a(iblockdata, worldserver, blockposition, 7, false);
         if (blockposition1 != null) {
            IBlockData iblockdata3 = worldserver.a_(blockposition1);
            if (h(iblockdata3) && b(iblockdata3, worldserver, blockposition1)) {
               if (randomsource.h()) {
                  a(worldserver, blockposition1, EnumDirection.a);
               } else {
                  b(worldserver, blockposition1);
               }
            }
         }
      }
   }

   private static void b(WorldServer worldserver, BlockPosition blockposition) {
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = blockposition.j();

      for(int i = 0; i < 10; ++i) {
         blockposition_mutableblockposition.c(EnumDirection.a);
         IBlockData iblockdata = worldserver.a_(blockposition_mutableblockposition);
         if (!iblockdata.r().c()) {
            return;
         }

         if (a(iblockdata, EnumDirection.b) && b(iblockdata, worldserver, blockposition_mutableblockposition)) {
            a(worldserver, blockposition_mutableblockposition, EnumDirection.b);
            return;
         }

         if (c(worldserver, blockposition_mutableblockposition, EnumDirection.b) && !worldserver.B(blockposition_mutableblockposition.d())) {
            a(worldserver, blockposition_mutableblockposition.d(), EnumDirection.b);
            return;
         }

         if (!b((IBlockAccess)worldserver, blockposition_mutableblockposition, iblockdata)) {
            return;
         }
      }
   }

   private static void a(WorldServer worldserver, BlockPosition blockposition, EnumDirection enumdirection) {
      BlockPosition blockposition1 = blockposition.a(enumdirection);
      IBlockData iblockdata = worldserver.a_(blockposition1);
      if (a(iblockdata, enumdirection.g())) {
         a(iblockdata, (GeneratorAccess)worldserver, blockposition1);
      } else if (iblockdata.h() || iblockdata.a(Blocks.G)) {
         createDripstone(worldserver, blockposition1, enumdirection, DripstoneThickness.b, blockposition);
      }
   }

   private static void createDripstone(
      GeneratorAccess generatoraccess, BlockPosition blockposition, EnumDirection enumdirection, DripstoneThickness dripstonethickness, BlockPosition source
   ) {
      IBlockData iblockdata = Blocks.rn
         .o()
         .a(a, enumdirection)
         .a(b, dripstonethickness)
         .a(c, Boolean.valueOf(generatoraccess.b_(blockposition).a() == FluidTypes.c));
      CraftEventFactory.handleBlockSpreadEvent(generatoraccess, source, blockposition, iblockdata, 3);
   }

   private static void a(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition) {
      BlockPosition blockposition1;
      BlockPosition blockposition2;
      if (iblockdata.c(a) == EnumDirection.b) {
         blockposition1 = blockposition;
         blockposition2 = blockposition.c();
      } else {
         blockposition2 = blockposition;
         blockposition1 = blockposition.d();
      }

      createDripstone(generatoraccess, blockposition2, EnumDirection.a, DripstoneThickness.a, blockposition);
      createDripstone(generatoraccess, blockposition1, EnumDirection.b, DripstoneThickness.a, blockposition);
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      b(world, blockposition, iblockdata).ifPresent(pointeddripstoneblock_a -> a(world, blockposition, iblockdata, pointeddripstoneblock_a.b));
   }

   private static void a(World world, BlockPosition blockposition, IBlockData iblockdata, FluidType fluidtype) {
      Vec3D vec3d = iblockdata.n(world, blockposition);
      double d0 = 0.0625;
      double d1 = (double)blockposition.u() + 0.5 + vec3d.c;
      double d2 = (double)((float)(blockposition.v() + 1) - 0.6875F) - 0.0625;
      double d3 = (double)blockposition.w() + 0.5 + vec3d.e;
      FluidType fluidtype1 = a(world, fluidtype);
      ParticleType particletype = fluidtype1.a(TagsFluid.b) ? Particles.aH : Particles.aJ;
      world.a(particletype, d1, d2, d3, 0.0, 0.0, 0.0);
   }

   @Nullable
   private static BlockPosition a(IBlockData iblockdata, GeneratorAccess generatoraccess, BlockPosition blockposition, int i, boolean flag) {
      if (a(iblockdata, flag)) {
         return blockposition;
      } else {
         EnumDirection enumdirection = iblockdata.c(a);
         BiPredicate<BlockPosition, IBlockData> bipredicate = (blockposition1, iblockdata1) -> iblockdata1.a(Blocks.rn) && iblockdata1.c(a) == enumdirection;
         return a(generatoraccess, blockposition, enumdirection.f(), bipredicate, iblockdata1 -> a(iblockdata1, flag), i).orElse(null);
      }
   }

   @Nullable
   private static EnumDirection b(IWorldReader iworldreader, BlockPosition blockposition, EnumDirection enumdirection) {
      EnumDirection enumdirection1;
      if (c(iworldreader, blockposition, enumdirection)) {
         enumdirection1 = enumdirection;
      } else {
         if (!c(iworldreader, blockposition, enumdirection.g())) {
            return null;
         }

         enumdirection1 = enumdirection.g();
      }

      return enumdirection1;
   }

   private static DripstoneThickness a(IWorldReader iworldreader, BlockPosition blockposition, EnumDirection enumdirection, boolean flag) {
      EnumDirection enumdirection1 = enumdirection.g();
      IBlockData iblockdata = iworldreader.a_(blockposition.a(enumdirection));
      if (!b(iblockdata, enumdirection1)) {
         if (!b(iblockdata, enumdirection)) {
            return DripstoneThickness.b;
         } else {
            DripstoneThickness dripstonethickness = iblockdata.c(b);
            if (dripstonethickness != DripstoneThickness.b && dripstonethickness != DripstoneThickness.a) {
               IBlockData iblockdata1 = iworldreader.a_(blockposition.a(enumdirection1));
               return !b(iblockdata1, enumdirection) ? DripstoneThickness.e : DripstoneThickness.d;
            } else {
               return DripstoneThickness.c;
            }
         }
      } else {
         return !flag && iblockdata.c(b) != DripstoneThickness.a ? DripstoneThickness.b : DripstoneThickness.a;
      }
   }

   public static boolean h(IBlockData iblockdata) {
      return n(iblockdata) && iblockdata.c(b) == DripstoneThickness.b && !iblockdata.c(c);
   }

   private static boolean b(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition) {
      EnumDirection enumdirection = iblockdata.c(a);
      BlockPosition blockposition1 = blockposition.a(enumdirection);
      IBlockData iblockdata1 = worldserver.a_(blockposition1);
      return !iblockdata1.r().c() ? false : (iblockdata1.h() ? true : a(iblockdata1, enumdirection.g()));
   }

   private static Optional<BlockPosition> a(World world, BlockPosition blockposition, IBlockData iblockdata, int i) {
      EnumDirection enumdirection = iblockdata.c(a);
      BiPredicate<BlockPosition, IBlockData> bipredicate = (blockposition1, iblockdata1) -> iblockdata1.a(Blocks.rn) && iblockdata1.c(a) == enumdirection;
      return a(world, blockposition, enumdirection.g().f(), bipredicate, iblockdata1 -> !iblockdata1.a(Blocks.rn), i);
   }

   private static boolean c(IWorldReader iworldreader, BlockPosition blockposition, EnumDirection enumdirection) {
      BlockPosition blockposition1 = blockposition.a(enumdirection.g());
      IBlockData iblockdata = iworldreader.a_(blockposition1);
      return iblockdata.d(iworldreader, blockposition1, enumdirection) || b(iblockdata, enumdirection);
   }

   private static boolean a(IBlockData iblockdata, boolean flag) {
      if (!iblockdata.a(Blocks.rn)) {
         return false;
      } else {
         DripstoneThickness dripstonethickness = iblockdata.c(b);
         return dripstonethickness == DripstoneThickness.b || flag && dripstonethickness == DripstoneThickness.a;
      }
   }

   private static boolean a(IBlockData iblockdata, EnumDirection enumdirection) {
      return a(iblockdata, false) && iblockdata.c(a) == enumdirection;
   }

   private static boolean n(IBlockData iblockdata) {
      return b(iblockdata, EnumDirection.a);
   }

   private static boolean o(IBlockData iblockdata) {
      return b(iblockdata, EnumDirection.b);
   }

   private static boolean b(IBlockData iblockdata, IWorldReader iworldreader, BlockPosition blockposition) {
      return n(iblockdata) && !iworldreader.a_(blockposition.c()).a(Blocks.rn);
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return false;
   }

   private static boolean b(IBlockData iblockdata, EnumDirection enumdirection) {
      return iblockdata.a(Blocks.rn) && iblockdata.c(a) == enumdirection;
   }

   @Nullable
   private static BlockPosition a(World world, BlockPosition blockposition, FluidType fluidtype) {
      Predicate<IBlockData> predicate = iblockdata -> iblockdata.b() instanceof AbstractCauldronBlock && ((AbstractCauldronBlock)iblockdata.b()).a(fluidtype);
      BiPredicate<BlockPosition, IBlockData> bipredicate = (blockposition1, iblockdata) -> b((IBlockAccess)world, blockposition1, iblockdata);
      return a(world, blockposition, EnumDirection.a.f(), bipredicate, predicate, 11).orElse(null);
   }

   @Nullable
   public static BlockPosition a(World world, BlockPosition blockposition) {
      BiPredicate<BlockPosition, IBlockData> bipredicate = (blockposition1, iblockdata) -> b((IBlockAccess)world, blockposition1, iblockdata);
      return a(world, blockposition, EnumDirection.b.f(), bipredicate, PointedDripstoneBlock::h, 11).orElse(null);
   }

   public static FluidType a(WorldServer worldserver, BlockPosition blockposition) {
      return b((World)worldserver, blockposition, worldserver.a_(blockposition))
         .map(pointeddripstoneblock_a -> pointeddripstoneblock_a.b)
         .filter(PointedDripstoneBlock::a)
         .orElse(FluidTypes.a);
   }

   private static Optional<PointedDripstoneBlock.a> b(World world, BlockPosition blockposition, IBlockData iblockdata) {
      return !n(iblockdata) ? Optional.empty() : a(world, blockposition, iblockdata, 11).map(blockposition1 -> {
         BlockPosition blockposition2 = blockposition1.c();
         IBlockData iblockdata1 = world.a_(blockposition2);
         Object object;
         if (iblockdata1.a(Blocks.rC) && !world.q_().i()) {
            object = FluidTypes.c;
         } else {
            object = world.b_(blockposition2).a();
         }

         return new PointedDripstoneBlock.a(blockposition2, (FluidType)object, iblockdata1);
      });
   }

   private static boolean a(FluidType fluidtype) {
      return fluidtype == FluidTypes.e || fluidtype == FluidTypes.c;
   }

   private static boolean a(IBlockData iblockdata, IBlockData iblockdata1) {
      return iblockdata.a(Blocks.ro) && iblockdata1.a(Blocks.G) && iblockdata1.r().b();
   }

   private static FluidType a(World world, FluidType fluidtype) {
      return (FluidType)(fluidtype.a(FluidTypes.a) ? (world.q_().i() ? FluidTypes.e : FluidTypes.c) : fluidtype);
   }

   private static Optional<BlockPosition> a(
      GeneratorAccess generatoraccess,
      BlockPosition blockposition,
      EnumDirection.EnumAxisDirection enumdirection_enumaxisdirection,
      BiPredicate<BlockPosition, IBlockData> bipredicate,
      Predicate<IBlockData> predicate,
      int i
   ) {
      EnumDirection enumdirection = EnumDirection.a(enumdirection_enumaxisdirection, EnumDirection.EnumAxis.b);
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = blockposition.j();

      for(int j = 1; j < i; ++j) {
         blockposition_mutableblockposition.c(enumdirection);
         IBlockData iblockdata = generatoraccess.a_(blockposition_mutableblockposition);
         if (predicate.test(iblockdata)) {
            return Optional.of(blockposition_mutableblockposition.i());
         }

         if (generatoraccess.d(blockposition_mutableblockposition.v()) || !bipredicate.test(blockposition_mutableblockposition, iblockdata)) {
            return Optional.empty();
         }
      }

      return Optional.empty();
   }

   private static boolean b(IBlockAccess iblockaccess, BlockPosition blockposition, IBlockData iblockdata) {
      if (iblockdata.h()) {
         return true;
      } else if (iblockdata.i(iblockaccess, blockposition)) {
         return false;
      } else if (!iblockdata.r().c()) {
         return false;
      } else {
         VoxelShape voxelshape = iblockdata.k(iblockaccess, blockposition);
         return !VoxelShapes.c(S, voxelshape, OperatorBoolean.i);
      }
   }

   static record a(BlockPosition pos, FluidType fluid, IBlockData sourceState) {
      private final BlockPosition a;
      private final FluidType b;
      private final IBlockData c;

      a(BlockPosition pos, FluidType fluid, IBlockData sourceState) {
         this.a = pos;
         this.b = fluid;
         this.c = sourceState;
      }
   }
}
