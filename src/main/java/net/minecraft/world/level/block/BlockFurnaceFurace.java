package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.particles.Particles;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ITileInventory;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityFurnaceFurnace;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockFurnaceFurace extends BlockFurnace {
   protected BlockFurnaceFurace(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new TileEntityFurnaceFurnace(var0, var1);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World var0, IBlockData var1, TileEntityTypes<T> var2) {
      return a(var0, var2, TileEntityTypes.a);
   }

   @Override
   protected void a(World var0, BlockPosition var1, EntityHuman var2) {
      TileEntity var3 = var0.c_(var1);
      if (var3 instanceof TileEntityFurnaceFurnace) {
         var2.a((ITileInventory)var3);
         var2.a(StatisticList.am);
      }
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      if (var0.c(b)) {
         double var4 = (double)var2.u() + 0.5;
         double var6 = (double)var2.v();
         double var8 = (double)var2.w() + 0.5;
         if (var3.j() < 0.1) {
            var1.a(var4, var6, var8, SoundEffects.iI, SoundCategory.e, 1.0F, 1.0F, false);
         }

         EnumDirection var10 = var0.c(a);
         EnumDirection.EnumAxis var11 = var10.o();
         double var12 = 0.52;
         double var14 = var3.j() * 0.6 - 0.3;
         double var16 = var11 == EnumDirection.EnumAxis.a ? (double)var10.j() * 0.52 : var14;
         double var18 = var3.j() * 6.0 / 16.0;
         double var20 = var11 == EnumDirection.EnumAxis.c ? (double)var10.l() * 0.52 : var14;
         var1.a(Particles.ab, var4 + var16, var6 + var18, var8 + var20, 0.0, 0.0, 0.0);
         var1.a(Particles.C, var4 + var16, var6 + var18, var8 + var20, 0.0, 0.0, 0.0);
      }
   }
}
