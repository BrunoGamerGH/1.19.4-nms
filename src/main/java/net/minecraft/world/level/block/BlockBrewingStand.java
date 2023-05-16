package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.stats.StatisticList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InventoryUtils;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityBrewingStand;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockBrewingStand extends BlockTileEntity {
   public static final BlockStateBoolean[] a = new BlockStateBoolean[]{BlockProperties.k, BlockProperties.l, BlockProperties.m};
   protected static final VoxelShape b = VoxelShapes.a(Block.a(1.0, 0.0, 1.0, 15.0, 2.0, 15.0), Block.a(7.0, 0.0, 7.0, 9.0, 14.0, 9.0));

   public BlockBrewingStand(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a[0], Boolean.valueOf(false)).a(a[1], Boolean.valueOf(false)).a(a[2], Boolean.valueOf(false)));
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.c;
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new TileEntityBrewingStand(var0, var1);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World var0, IBlockData var1, TileEntityTypes<T> var2) {
      return var0.B ? null : a(var2, TileEntityTypes.l, TileEntityBrewingStand::a);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return b;
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      if (var1.B) {
         return EnumInteractionResult.a;
      } else {
         TileEntity var6 = var1.c_(var2);
         if (var6 instanceof TileEntityBrewingStand) {
            var3.a((TileEntityBrewingStand)var6);
            var3.a(StatisticList.aa);
         }

         return EnumInteractionResult.b;
      }
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, EntityLiving var3, ItemStack var4) {
      if (var4.z()) {
         TileEntity var5 = var0.c_(var1);
         if (var5 instanceof TileEntityBrewingStand) {
            ((TileEntityBrewingStand)var5).a(var4.x());
         }
      }
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      double var4 = (double)var2.u() + 0.4 + (double)var3.i() * 0.2;
      double var6 = (double)var2.v() + 0.7 + (double)var3.i() * 0.3;
      double var8 = (double)var2.w() + 0.4 + (double)var3.i() * 0.2;
      var1.a(Particles.ab, var4, var6, var8, 0.0, 0.0, 0.0);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      if (!var0.a(var3.b())) {
         TileEntity var5 = var1.c_(var2);
         if (var5 instanceof TileEntityBrewingStand) {
            InventoryUtils.a(var1, var2, (TileEntityBrewingStand)var5);
         }

         super.a(var0, var1, var2, var3, var4);
      }
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
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a[0], a[1], a[2]);
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }
}
