package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InventoryUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.IHopper;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityHopper;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.OperatorBoolean;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockHopper extends BlockTileEntity {
   public static final BlockStateDirection a = BlockProperties.Q;
   public static final BlockStateBoolean b = BlockProperties.f;
   private static final VoxelShape c = Block.a(0.0, 10.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape d = Block.a(4.0, 4.0, 4.0, 12.0, 10.0, 12.0);
   private static final VoxelShape e = VoxelShapes.a(d, c);
   private static final VoxelShape f = VoxelShapes.a(e, IHopper.c, OperatorBoolean.e);
   private static final VoxelShape g = VoxelShapes.a(f, Block.a(6.0, 0.0, 6.0, 10.0, 4.0, 10.0));
   private static final VoxelShape h = VoxelShapes.a(f, Block.a(12.0, 4.0, 6.0, 16.0, 8.0, 10.0));
   private static final VoxelShape i = VoxelShapes.a(f, Block.a(6.0, 4.0, 0.0, 10.0, 8.0, 4.0));
   private static final VoxelShape j = VoxelShapes.a(f, Block.a(6.0, 4.0, 12.0, 10.0, 8.0, 16.0));
   private static final VoxelShape k = VoxelShapes.a(f, Block.a(0.0, 4.0, 6.0, 4.0, 8.0, 10.0));
   private static final VoxelShape l = IHopper.c;
   private static final VoxelShape m = VoxelShapes.a(IHopper.c, Block.a(12.0, 8.0, 6.0, 16.0, 10.0, 10.0));
   private static final VoxelShape n = VoxelShapes.a(IHopper.c, Block.a(6.0, 8.0, 0.0, 10.0, 10.0, 4.0));
   private static final VoxelShape E = VoxelShapes.a(IHopper.c, Block.a(6.0, 8.0, 12.0, 10.0, 10.0, 16.0));
   private static final VoxelShape F = VoxelShapes.a(IHopper.c, Block.a(0.0, 8.0, 6.0, 4.0, 10.0, 10.0));

   public BlockHopper(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, EnumDirection.a).a(b, Boolean.valueOf(true)));
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      switch((EnumDirection)var0.c(a)) {
         case a:
            return g;
         case c:
            return i;
         case d:
            return j;
         case e:
            return k;
         case f:
            return h;
         default:
            return f;
      }
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      switch((EnumDirection)var0.c(a)) {
         case a:
            return l;
         case c:
            return n;
         case d:
            return E;
         case e:
            return F;
         case f:
            return m;
         default:
            return IHopper.c;
      }
   }

   @Override
   public IBlockData a(BlockActionContext var0) {
      EnumDirection var1 = var0.k().g();
      return this.o().a(a, var1.o() == EnumDirection.EnumAxis.b ? EnumDirection.a : var1).a(b, Boolean.valueOf(true));
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new TileEntityHopper(var0, var1);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World var0, IBlockData var1, TileEntityTypes<T> var2) {
      return var0.B ? null : a(var2, TileEntityTypes.r, TileEntityHopper::a);
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, EntityLiving var3, ItemStack var4) {
      if (var4.z()) {
         TileEntity var5 = var0.c_(var1);
         if (var5 instanceof TileEntityHopper) {
            ((TileEntityHopper)var5).a(var4.x());
         }
      }
   }

   @Override
   public void b(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      if (!var3.a(var0.b())) {
         this.a(var1, var2, var0, 2);
      }
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      if (var1.B) {
         return EnumInteractionResult.a;
      } else {
         TileEntity var6 = var1.c_(var2);
         if (var6 instanceof TileEntityHopper) {
            var3.a((TileEntityHopper)var6);
            var3.a(StatisticList.ad);
         }

         return EnumInteractionResult.b;
      }
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, Block var3, BlockPosition var4, boolean var5) {
      this.a(var1, var2, var0, 4);
   }

   private void a(World var0, BlockPosition var1, IBlockData var2, int var3) {
      boolean var4 = !var0.r(var1);
      if (var4 != var2.c(b)) {
         var0.a(var1, var2.a(b, Boolean.valueOf(var4)), var3);
      }
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      if (!var0.a(var3.b())) {
         TileEntity var5 = var1.c_(var2);
         if (var5 instanceof TileEntityHopper) {
            InventoryUtils.a(var1, var2, (TileEntityHopper)var5);
            var1.c(var2, this);
         }

         super.a(var0, var1, var2, var3, var4);
      }
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.c;
   }

   @Override
   public boolean d_(IBlockData var0) {
      return true;
   }

   @Override
   public int a(IBlockData var0, World var1, BlockPosition var2) {
      return Container.a(var1.c_(var2));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(a, var1.a(var0.c(a)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(var1.a(var0.c(a)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, b);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, Entity var3) {
      TileEntity var4 = var1.c_(var2);
      if (var4 instanceof TileEntityHopper) {
         TileEntityHopper.a(var1, var2, var0, var3, (TileEntityHopper)var4);
      }
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }
}
