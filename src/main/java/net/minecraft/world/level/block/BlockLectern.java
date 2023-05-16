package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.tags.TagsItem;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.EntityItem;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityLectern;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockLectern extends BlockTileEntity {
   public static final BlockStateDirection a = BlockFacingHorizontal.aD;
   public static final BlockStateBoolean b = BlockProperties.w;
   public static final BlockStateBoolean c = BlockProperties.o;
   public static final VoxelShape d = Block.a(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
   public static final VoxelShape e = Block.a(4.0, 2.0, 4.0, 12.0, 14.0, 12.0);
   public static final VoxelShape f = VoxelShapes.a(d, e);
   public static final VoxelShape g = Block.a(0.0, 15.0, 0.0, 16.0, 15.0, 16.0);
   public static final VoxelShape h = VoxelShapes.a(f, g);
   public static final VoxelShape i = VoxelShapes.a(
      Block.a(1.0, 10.0, 0.0, 5.333333, 14.0, 16.0), Block.a(5.333333, 12.0, 0.0, 9.666667, 16.0, 16.0), Block.a(9.666667, 14.0, 0.0, 14.0, 18.0, 16.0), f
   );
   public static final VoxelShape j = VoxelShapes.a(
      Block.a(0.0, 10.0, 1.0, 16.0, 14.0, 5.333333), Block.a(0.0, 12.0, 5.333333, 16.0, 16.0, 9.666667), Block.a(0.0, 14.0, 9.666667, 16.0, 18.0, 14.0), f
   );
   public static final VoxelShape k = VoxelShapes.a(
      Block.a(10.666667, 10.0, 0.0, 15.0, 14.0, 16.0), Block.a(6.333333, 12.0, 0.0, 10.666667, 16.0, 16.0), Block.a(2.0, 14.0, 0.0, 6.333333, 18.0, 16.0), f
   );
   public static final VoxelShape l = VoxelShapes.a(
      Block.a(0.0, 10.0, 10.666667, 16.0, 14.0, 15.0), Block.a(0.0, 12.0, 6.333333, 16.0, 16.0, 10.666667), Block.a(0.0, 14.0, 2.0, 16.0, 18.0, 6.333333), f
   );
   private static final int m = 2;

   protected BlockLectern(BlockBase.Info blockbase_info) {
      super(blockbase_info);
      this.k(this.D.b().a(a, EnumDirection.c).a(b, Boolean.valueOf(false)).a(c, Boolean.valueOf(false)));
   }

   @Override
   public EnumRenderType b_(IBlockData iblockdata) {
      return EnumRenderType.c;
   }

   @Override
   public VoxelShape f(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition) {
      return f;
   }

   @Override
   public boolean g_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public IBlockData a(BlockActionContext blockactioncontext) {
      World world = blockactioncontext.q();
      ItemStack itemstack = blockactioncontext.n();
      EntityHuman entityhuman = blockactioncontext.o();
      boolean flag = false;
      if (!world.B && entityhuman != null && entityhuman.gg()) {
         NBTTagCompound nbttagcompound = ItemBlock.a(itemstack);
         if (nbttagcompound != null && nbttagcompound.e("Book")) {
            flag = true;
         }
      }

      return this.o().a(a, blockactioncontext.g().g()).a(c, Boolean.valueOf(flag));
   }

   @Override
   public VoxelShape c(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      return h;
   }

   @Override
   public VoxelShape a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, VoxelShapeCollision voxelshapecollision) {
      switch((EnumDirection)iblockdata.c(a)) {
         case c:
            return j;
         case d:
            return l;
         case e:
            return i;
         case f:
            return k;
         default:
            return f;
      }
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
      return iblockdata.a(a, enumblockrotation.a(iblockdata.c(a)));
   }

   @Override
   public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
      return iblockdata.a(enumblockmirror.a(iblockdata.c(a)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> blockstatelist_a) {
      blockstatelist_a.a(a, b, c);
   }

   @Override
   public TileEntity a(BlockPosition blockposition, IBlockData iblockdata) {
      return new TileEntityLectern(blockposition, iblockdata);
   }

   public static boolean a(@Nullable Entity entity, World world, BlockPosition blockposition, IBlockData iblockdata, ItemStack itemstack) {
      if (!iblockdata.c(c)) {
         if (!world.B) {
            b(entity, world, blockposition, iblockdata, itemstack);
         }

         return true;
      } else {
         return false;
      }
   }

   private static void b(@Nullable Entity entity, World world, BlockPosition blockposition, IBlockData iblockdata, ItemStack itemstack) {
      TileEntity tileentity = world.c_(blockposition);
      if (tileentity instanceof TileEntityLectern tileentitylectern) {
         tileentitylectern.a(itemstack.a(1));
         a(entity, world, blockposition, iblockdata, true);
         world.a(null, blockposition, SoundEffects.ce, SoundCategory.e, 1.0F, 1.0F);
      }
   }

   public static void a(@Nullable Entity entity, World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
      IBlockData iblockdata1 = iblockdata.a(b, Boolean.valueOf(false)).a(c, Boolean.valueOf(flag));
      world.a(blockposition, iblockdata1, 3);
      world.a(GameEvent.c, blockposition, GameEvent.a.a(entity, iblockdata1));
      b(world, blockposition, iblockdata);
   }

   public static void a(World world, BlockPosition blockposition, IBlockData iblockdata) {
      a(world, blockposition, iblockdata, true);
      world.a(blockposition, iblockdata.b(), 2);
      world.c(1043, blockposition, 0);
   }

   private static void a(World world, BlockPosition blockposition, IBlockData iblockdata, boolean flag) {
      world.a(blockposition, iblockdata.a(b, Boolean.valueOf(flag)), 3);
      b(world, blockposition, iblockdata);
   }

   private static void b(World world, BlockPosition blockposition, IBlockData iblockdata) {
      world.a(blockposition.d(), iblockdata.b());
   }

   @Override
   public void a(IBlockData iblockdata, WorldServer worldserver, BlockPosition blockposition, RandomSource randomsource) {
      a(worldserver, blockposition, iblockdata, false);
   }

   @Override
   public void a(IBlockData iblockdata, World world, BlockPosition blockposition, IBlockData iblockdata1, boolean flag) {
      if (!iblockdata.a(iblockdata1.b())) {
         if (iblockdata.c(c)) {
            this.d(iblockdata, world, blockposition);
         }

         if (iblockdata.c(b)) {
            world.a(blockposition.d(), this);
         }

         super.a(iblockdata, world, blockposition, iblockdata1, flag);
      }
   }

   private void d(IBlockData iblockdata, World world, BlockPosition blockposition) {
      TileEntity tileentity = world.getBlockEntity(blockposition, false);
      if (tileentity instanceof TileEntityLectern tileentitylectern) {
         EnumDirection enumdirection = iblockdata.c(a);
         ItemStack itemstack = tileentitylectern.c().o();
         if (itemstack.b()) {
            return;
         }

         float f = 0.25F * (float)enumdirection.j();
         float f1 = 0.25F * (float)enumdirection.l();
         EntityItem entityitem = new EntityItem(
            world, (double)blockposition.u() + 0.5 + (double)f, (double)(blockposition.v() + 1), (double)blockposition.w() + 0.5 + (double)f1, itemstack
         );
         entityitem.k();
         world.b(entityitem);
         tileentitylectern.a();
      }
   }

   @Override
   public boolean f_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public int a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return iblockdata.c(b) ? 15 : 0;
   }

   @Override
   public int b(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, EnumDirection enumdirection) {
      return enumdirection == EnumDirection.b && iblockdata.c(b) ? 15 : 0;
   }

   @Override
   public boolean d_(IBlockData iblockdata) {
      return true;
   }

   @Override
   public int a(IBlockData iblockdata, World world, BlockPosition blockposition) {
      if (iblockdata.c(c)) {
         TileEntity tileentity = world.c_(blockposition);
         if (tileentity instanceof TileEntityLectern) {
            return ((TileEntityLectern)tileentity).i();
         }
      }

      return 0;
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
      if (iblockdata.c(c)) {
         if (!world.B) {
            this.a(world, blockposition, entityhuman);
         }

         return EnumInteractionResult.a(world.B);
      } else {
         ItemStack itemstack = entityhuman.b(enumhand);
         return !itemstack.b() && !itemstack.a(TagsItem.at) ? EnumInteractionResult.b : EnumInteractionResult.d;
      }
   }

   @Nullable
   @Override
   public ITileInventory b(IBlockData iblockdata, World world, BlockPosition blockposition) {
      return !iblockdata.c(c) ? null : super.b(iblockdata, world, blockposition);
   }

   private void a(World world, BlockPosition blockposition, EntityHuman entityhuman) {
      TileEntity tileentity = world.c_(blockposition);
      if (tileentity instanceof TileEntityLectern) {
         entityhuman.a((TileEntityLectern)tileentity);
         entityhuman.a(StatisticList.au);
      }
   }

   @Override
   public boolean a(IBlockData iblockdata, IBlockAccess iblockaccess, BlockPosition blockposition, PathMode pathmode) {
      return false;
   }
}
