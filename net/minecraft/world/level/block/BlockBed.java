package net.minecraft.world.level.block;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.npc.EntityVillager;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.vehicle.DismountUtil;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.ICollisionAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityBed;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyBedPart;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.material.EnumPistonReaction;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.AxisAlignedBB;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;
import org.apache.commons.lang3.ArrayUtils;

public class BlockBed extends BlockFacingHorizontal implements ITileEntity {
   public static final BlockStateEnum<BlockPropertyBedPart> a = BlockProperties.bb;
   public static final BlockStateBoolean b = BlockProperties.t;
   protected static final int c = 9;
   protected static final VoxelShape d = Block.a(0.0, 3.0, 0.0, 16.0, 9.0, 16.0);
   private static final int m = 3;
   protected static final VoxelShape e = Block.a(0.0, 0.0, 0.0, 3.0, 3.0, 3.0);
   protected static final VoxelShape f = Block.a(0.0, 0.0, 13.0, 3.0, 3.0, 16.0);
   protected static final VoxelShape g = Block.a(13.0, 0.0, 0.0, 16.0, 3.0, 3.0);
   protected static final VoxelShape h = Block.a(13.0, 0.0, 13.0, 16.0, 3.0, 16.0);
   protected static final VoxelShape i = VoxelShapes.a(d, e, g);
   protected static final VoxelShape j = VoxelShapes.a(d, f, h);
   protected static final VoxelShape k = VoxelShapes.a(d, e, f);
   protected static final VoxelShape l = VoxelShapes.a(d, g, h);
   private final EnumColor n;

   public BlockBed(EnumColor enumcolor, BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.n = enumcolor;
      this.k(this.D.b().a(a, BlockPropertyBedPart.b).a(b, Boolean.valueOf(false)));
   }

   @Nullable
   public static EnumDirection a(IBlockAccess iblockaccess, BlockPosition blockposition) {
      IBlockData iblockdata = iblockaccess.a_(blockposition);
      return iblockdata.b() instanceof BlockBed ? iblockdata.c(aD) : null;
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
      if (world.B) {
         return EnumInteractionResult.b;
      } else {
         if (iblockdata.c(a) != BlockPropertyBedPart.a) {
            blockposition = blockposition.a(iblockdata.c(aD));
            iblockdata = world.a_(blockposition);
            if (!iblockdata.a(this)) {
               return EnumInteractionResult.b;
            }
         }

         if (iblockdata.c(b)) {
            if (!this.a(world, blockposition)) {
               entityhuman.a(IChatBaseComponent.c("block.minecraft.bed.occupied"), true);
            }

            return EnumInteractionResult.a;
         } else {
            IBlockData finaliblockdata = iblockdata;
            BlockPosition finalblockposition = blockposition;
            entityhuman.a(blockposition).ifLeft(entityhuman_enumbedresult -> {
               if (!world.q_().l()) {
                  this.explodeBed(finaliblockdata, world, finalblockposition);
               } else if (entityhuman_enumbedresult.a() != null) {
                  entityhuman.a(entityhuman_enumbedresult.a(), true);
               }
            });
            return EnumInteractionResult.a;
         }
      }
   }

   private EnumInteractionResult explodeBed(IBlockData iblockdata, World world, BlockPosition blockposition) {
      world.a(blockposition, false);
      BlockPosition blockposition1 = blockposition.a(iblockdata.c(aD).g());
      if (world.a_(blockposition1).b() == this) {
         world.a(blockposition1, false);
      }

      Vec3D vec3d = blockposition.b();
      world.a(null, world.af().a(vec3d), null, vec3d, 5.0F, true, World.a.b);
      return EnumInteractionResult.a;
   }

   public static boolean a(World world) {
      return true;
   }

   private boolean a(World world, BlockPosition blockposition) {
      List<EntityVillager> list = world.a(EntityVillager.class, new AxisAlignedBB(blockposition), EntityLiving::fu);
      if (list.isEmpty()) {
         return false;
      } else {
         list.get(0).fv();
         return true;
      }
   }

   @Override
   public void a(World world, IBlockData iblockdata, BlockPosition blockposition, Entity entity, float f) {
      super.a(world, iblockdata, blockposition, entity, f * 0.5F);
   }

   @Override
   public void a(IBlockAccess iblockaccess, Entity entity) {
      if (entity.bQ()) {
         super.a(iblockaccess, entity);
      } else {
         this.a(entity);
      }
   }

   private void a(Entity entity) {
      Vec3D vec3d = entity.dj();
      if (vec3d.d < 0.0) {
         double d0 = entity instanceof EntityLiving ? 1.0 : 0.8;
         entity.o(vec3d.c, -vec3d.d * 0.66F * d0, vec3d.e);
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
      return enumdirection == a(iblockdata.c(a), iblockdata.c(aD))
         ? (iblockdata1.a(this) && iblockdata1.c(a) != iblockdata.c(a) ? iblockdata.a(b, iblockdata1.c(b)) : Blocks.a.o())
         : super.a(iblockdata, enumdirection, iblockdata1, generatoraccess, blockposition, blockposition1);
   }

   private static EnumDirection a(BlockPropertyBedPart blockpropertybedpart, EnumDirection enumdirection) {
      return blockpropertybedpart == BlockPropertyBedPart.b ? enumdirection : enumdirection.g();
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, EntityHuman entityhuman) {
      if (!world.B && entityhuman.f()) {
         BlockPropertyBedPart blockpropertybedpart = iblockdata.c(a);
         if (blockpropertybedpart == BlockPropertyBedPart.b) {
            BlockPosition blockposition1 = blockposition.a(a(blockpropertybedpart, iblockdata.c(aD)));
            IBlockData iblockdata1 = world.a_(blockposition1);
            if (iblockdata1.a(this) && iblockdata1.c(a) == BlockPropertyBedPart.a) {
               world.a(blockposition1, Blocks.a.o(), 35);
               world.a(entityhuman, 2001, blockposition1, Block.i(iblockdata1));
            }
         }
      }

      super.a(world, blockposition, iblockdata, entityhuman);
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      EnumDirection enumdirection = blockactioncontext.g();
      BlockPosition blockposition = blockactioncontext.a();
      BlockPosition blockposition1 = blockposition.a(enumdirection);
      World world = blockactioncontext.q();
      return world.a_(blockposition1).a(blockactioncontext) && world.p_().a(blockposition1) ? this.o().a(aD, enumdirection) : null;
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      EnumDirection enumdirection = g(iblockdata).g();
      switch(enumdirection) {
         case c:
            return i;
         case d:
            return j;
         case e:
            return k;
         default:
            return l;
      }
   }

   public static EnumDirection g(IBlockData iblockdata) {
      EnumDirection enumdirection = iblockdata.c(aD);
      return iblockdata.c(a) == BlockPropertyBedPart.a ? enumdirection.g() : enumdirection;
   }

   public static DoubleBlockFinder.BlockType h(IBlockData iblockdata) {
      BlockPropertyBedPart blockpropertybedpart = iblockdata.c(a);
      return blockpropertybedpart == BlockPropertyBedPart.a ? DoubleBlockFinder.BlockType.b : DoubleBlockFinder.BlockType.c;
   }

   private static boolean b(IBlockAccess iblockaccess, BlockPosition blockposition) {
      return iblockaccess.a_(blockposition.d()).b() instanceof BlockBed;
   }

   public static Optional<Vec3D> a(
      EntityTypes<?> entitytypes, ICollisionAccess icollisionaccess, BlockPosition blockposition, EnumDirection enumdirection, float f
   ) {
      EnumDirection enumdirection1 = enumdirection.h();
      EnumDirection enumdirection2 = enumdirection1.a(f) ? enumdirection1.g() : enumdirection1;
      if (b(icollisionaccess, blockposition)) {
         return a(entitytypes, icollisionaccess, blockposition, enumdirection, enumdirection2);
      } else {
         int[][] aint = a(enumdirection, enumdirection2);
         Optional<Vec3D> optional = a(entitytypes, icollisionaccess, blockposition, aint, true);
         return optional.isPresent() ? optional : a(entitytypes, icollisionaccess, blockposition, aint, false);
      }
   }

   private static Optional<Vec3D> a(
      EntityTypes<?> entitytypes, ICollisionAccess icollisionaccess, BlockPosition blockposition, EnumDirection enumdirection, EnumDirection enumdirection1
   ) {
      int[][] aint = b(enumdirection, enumdirection1);
      Optional<Vec3D> optional = a(entitytypes, icollisionaccess, blockposition, aint, true);
      if (optional.isPresent()) {
         return optional;
      } else {
         BlockPosition blockposition1 = blockposition.d();
         Optional<Vec3D> optional1 = a(entitytypes, icollisionaccess, blockposition1, aint, true);
         if (optional1.isPresent()) {
            return optional1;
         } else {
            int[][] aint1 = a(enumdirection);
            Optional<Vec3D> optional2 = a(entitytypes, icollisionaccess, blockposition, aint1, true);
            if (optional2.isPresent()) {
               return optional2;
            } else {
               Optional<Vec3D> optional3 = a(entitytypes, icollisionaccess, blockposition, aint, false);
               if (optional3.isPresent()) {
                  return optional3;
               } else {
                  Optional<Vec3D> optional4 = a(entitytypes, icollisionaccess, blockposition1, aint, false);
                  return optional4.isPresent() ? optional4 : a(entitytypes, icollisionaccess, blockposition, aint1, false);
               }
            }
         }
      }
   }

   private static Optional<Vec3D> a(EntityTypes<?> entitytypes, ICollisionAccess icollisionaccess, BlockPosition blockposition, int[][] aint, boolean flag) {
      BlockPosition.MutableBlockPosition blockposition_mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(int[] aint2 : aint) {
         blockposition_mutableblockposition.d(blockposition.u() + aint2[0], blockposition.v(), blockposition.w() + aint2[1]);
         Vec3D vec3d = DismountUtil.a(entitytypes, icollisionaccess, blockposition_mutableblockposition, flag);
         if (vec3d != null) {
            return Optional.of(vec3d);
         }
      }

      return Optional.empty();
   }

   @Override
   public EnumPistonReaction d(IBlockData iblockdata) {
      return EnumPistonReaction.b;
   }

   @Override
   public EnumRenderType b_(IBlockData iblockdata) {
      return EnumRenderType.b;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(aD, a, b);
   }

   @Override
   public TileEntity a(BlockPosition blockposition, IBlockData iblockdata) {
      return new TileEntityBed(blockposition, iblockdata, this.n);
   }

   @Override
   public void a(World world, BlockPosition blockposition, IBlockData iblockdata, @Nullable EntityLiving entityliving, ItemStack itemstack) {
      super.a(world, blockposition, iblockdata, entityliving, itemstack);
      if (!world.B) {
         BlockPosition blockposition1 = blockposition.a(iblockdata.c(aD));
         world.a(blockposition1, iblockdata.a(a, BlockPropertyBedPart.a), 3);
         world.b(blockposition, Blocks.a);
         iblockdata.a(world, blockposition, 3);
      }
   }

   public EnumColor b() {
      return this.n;
   }

   @Override
   public long a(IBlockData iblockdata, BlockPosition blockposition) {
      BlockPosition blockposition1 = blockposition.a(iblockdata.c(aD), iblockdata.c(a) == BlockPropertyBedPart.a ? 0 : 1);
      return MathHelper.b(blockposition1.u(), blockposition.v(), blockposition1.w());
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return false;
   }

   private static int[][] a(EnumDirection enumdirection, EnumDirection enumdirection1) {
      return (int[][])ArrayUtils.addAll(b(enumdirection, enumdirection1), a(enumdirection));
   }

   private static int[][] b(EnumDirection enumdirection, EnumDirection enumdirection1) {
      return new int[][]{
         {enumdirection1.j(), enumdirection1.l()},
         {enumdirection1.j() - enumdirection.j(), enumdirection1.l() - enumdirection.l()},
         {enumdirection1.j() - enumdirection.j() * 2, enumdirection1.l() - enumdirection.l() * 2},
         {-enumdirection.j() * 2, -enumdirection.l() * 2},
         {-enumdirection1.j() - enumdirection.j() * 2, -enumdirection1.l() - enumdirection.l() * 2},
         {-enumdirection1.j() - enumdirection.j(), -enumdirection1.l() - enumdirection.l()},
         {-enumdirection1.j(), -enumdirection1.l()},
         {-enumdirection1.j() + enumdirection.j(), -enumdirection1.l() + enumdirection.l()},
         {enumdirection.j(), enumdirection.l()},
         {enumdirection1.j() + enumdirection.j(), enumdirection1.l() + enumdirection.l()}
      };
   }

   private static int[][] a(EnumDirection enumdirection) {
      return new int[][]{new int[2], {-enumdirection.j(), -enumdirection.l()}};
   }
}
