package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
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
import net.minecraft.world.level.block.entity.TileEntitySmoker;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class BlockSmoker extends BlockFurnace {
   protected BlockSmoker(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new TileEntitySmoker(var0, var1);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World var0, IBlockData var1, TileEntityTypes<T> var2) {
      return a(var0, var2, TileEntityTypes.B);
   }

   @Override
   protected void a(World var0, BlockPosition var1, EntityHuman var2) {
      TileEntity var3 = var0.c_(var1);
      if (var3 instanceof TileEntitySmoker) {
         var2.a((ITileInventory)var3);
         var2.a(StatisticList.at);
      }
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      if (var0.c(b)) {
         double var4 = (double)var2.u() + 0.5;
         double var6 = (double)var2.v();
         double var8 = (double)var2.w() + 0.5;
         if (var3.j() < 0.1) {
            var1.a(var4, var6, var8, SoundEffects.wh, SoundCategory.e, 1.0F, 1.0F, false);
         }

         var1.a(Particles.ab, var4, var6 + 1.1, var8, 0.0, 0.0, 0.0);
      }
   }
}
