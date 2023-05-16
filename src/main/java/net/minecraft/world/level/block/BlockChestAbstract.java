package net.minecraft.world.level.block;

import java.util.function.Supplier;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityChest;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public abstract class BlockChestAbstract<E extends TileEntity> extends BlockTileEntity {
   protected final Supplier<TileEntityTypes<? extends E>> a;

   protected BlockChestAbstract(BlockBase.Info var0, Supplier<TileEntityTypes<? extends E>> var1) {
      super(var0);
      this.a = var1;
   }

   public abstract DoubleBlockFinder.Result<? extends TileEntityChest> a(IBlockData var1, World var2, BlockPosition var3, boolean var4);
}
