package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.stats.Statistic;
import net.minecraft.stats.StatisticList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityChest;
import net.minecraft.world.level.block.entity.TileEntityChestTrapped;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockChestTrapped extends BlockChest {
   public BlockChestTrapped(BlockBase.Info var0) {
      super(var0, () -> TileEntityTypes.c);
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new TileEntityChestTrapped(var0, var1);
   }

   @Override
   protected Statistic<MinecraftKey> c() {
      return StatisticList.i.b(StatisticList.ai);
   }

   @Override
   public boolean f_(IBlockData var0) {
      return true;
   }

   @Override
   public int a(IBlockData var0, IBlockAccess var1, BlockPosition var2, EnumDirection var3) {
      return MathHelper.a(TileEntityChest.a(var1, var2), 0, 15);
   }

   @Override
   public int b(IBlockData var0, IBlockAccess var1, BlockPosition var2, EnumDirection var3) {
      return var3 == EnumDirection.b ? var0.b(var1, var2, var3) : 0;
   }
}
