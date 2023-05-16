package net.minecraft.world.level.block;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.INamableTileEntity;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.TileInventory;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.ContainerAccess;
import net.minecraft.world.inventory.ContainerEnchantTable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityEnchantTable;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.pathfinder.PathMode;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class BlockEnchantmentTable extends BlockTileEntity {
   protected static final VoxelShape a = Block.a(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
   public static final List<BlockPosition> b = BlockPosition.a(-2, 0, -2, 2, 1, 2)
      .filter(var0 -> Math.abs(var0.u()) == 2 || Math.abs(var0.w()) == 2)
      .map(BlockPosition::i)
      .toList();

   protected BlockEnchantmentTable(BlockBase.Info var0) {
      super(var0);
   }

   public static boolean a(World var0, BlockPosition var1, BlockPosition var2) {
      return var0.a_(var1.a(var2)).a(Blocks.ck) && var0.w(var1.b(var2.u() / 2, var2.v(), var2.w() / 2));
   }

   @Override
   public boolean g_(IBlockData var0) {
      return true;
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return a;
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      super.a(var0, var1, var2, var3);

      for(BlockPosition var5 : b) {
         if (var3.a(16) == 0 && a(var1, var2, var5)) {
            var1.a(
               Particles.t,
               (double)var2.u() + 0.5,
               (double)var2.v() + 2.0,
               (double)var2.w() + 0.5,
               (double)((float)var5.u() + var3.i()) - 0.5,
               (double)((float)var5.v() - var3.i() - 1.0F),
               (double)((float)var5.w() + var3.i()) - 0.5
            );
         }
      }
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.c;
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new TileEntityEnchantTable(var0, var1);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World var0, IBlockData var1, TileEntityTypes<T> var2) {
      return var0.B ? a(var2, TileEntityTypes.m, TileEntityEnchantTable::a) : null;
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      if (var1.B) {
         return EnumInteractionResult.a;
      } else {
         var3.a(var0.b(var1, var2));
         return EnumInteractionResult.b;
      }
   }

   @Nullable
   @Override
   public ITileInventory b(IBlockData var0, World var1, BlockPosition var2) {
      TileEntity var3 = var1.c_(var2);
      if (var3 instanceof TileEntityEnchantTable) {
         IChatBaseComponent var4 = ((INamableTileEntity)var3).G_();
         return new TileInventory((var2x, var3x, var4x) -> new ContainerEnchantTable(var2x, var3x, ContainerAccess.a(var1, var2)), var4);
      } else {
         return null;
      }
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, EntityLiving var3, ItemStack var4) {
      if (var4.z()) {
         TileEntity var5 = var0.c_(var1);
         if (var5 instanceof TileEntityEnchantTable) {
            ((TileEntityEnchantTable)var5).a(var4.x());
         }
      }
   }

   @Override
   public boolean a(IBlockData var0, IBlockAccess var1, BlockPosition var2, PathMode var3) {
      return false;
   }
}
