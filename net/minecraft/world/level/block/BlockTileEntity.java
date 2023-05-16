package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public abstract class BlockTileEntity extends Block implements ITileEntity {
   protected BlockTileEntity(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.a;
   }

   @Override
   public boolean a(IBlockData var0, World var1, BlockPosition var2, int var3, int var4) {
      super.a(var0, var1, var2, var3, var4);
      TileEntity var5 = var1.c_(var2);
      return var5 == null ? false : var5.a_(var3, var4);
   }

   @Nullable
   @Override
   public ITileInventory b(IBlockData var0, World var1, BlockPosition var2) {
      TileEntity var3 = var1.c_(var2);
      return var3 instanceof ITileInventory ? (ITileInventory)var3 : null;
   }

   @Nullable
   protected static <E extends TileEntity, A extends TileEntity> BlockEntityTicker<A> a(
      TileEntityTypes<A> var0, TileEntityTypes<E> var1, BlockEntityTicker<? super E> var2
   ) {
      return var1 == var0 ? var2 : null;
   }
}
