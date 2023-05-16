package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.particles.Particles;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityEndGateway;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.FluidType;

public class BlockEndGateway extends BlockTileEntity {
   protected BlockEndGateway(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new TileEntityEndGateway(var0, var1);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World var0, IBlockData var1, TileEntityTypes<T> var2) {
      return a(var2, TileEntityTypes.v, var0.B ? TileEntityEndGateway::a : TileEntityEndGateway::b);
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, RandomSource var3) {
      TileEntity var4 = var1.c_(var2);
      if (var4 instanceof TileEntityEndGateway) {
         int var5 = ((TileEntityEndGateway)var4).g();

         for(int var6 = 0; var6 < var5; ++var6) {
            double var7 = (double)var2.u() + var3.j();
            double var9 = (double)var2.v() + var3.j();
            double var11 = (double)var2.w() + var3.j();
            double var13 = (var3.j() - 0.5) * 0.5;
            double var15 = (var3.j() - 0.5) * 0.5;
            double var17 = (var3.j() - 0.5) * 0.5;
            int var19 = var3.a(2) * 2 - 1;
            if (var3.h()) {
               var11 = (double)var2.w() + 0.5 + 0.25 * (double)var19;
               var17 = (double)(var3.i() * 2.0F * (float)var19);
            } else {
               var7 = (double)var2.u() + 0.5 + 0.25 * (double)var19;
               var13 = (double)(var3.i() * 2.0F * (float)var19);
            }

            var1.a(Particles.Z, var7, var9, var11, var13, var15, var17);
         }
      }
   }

   @Override
   public ItemStack a(IBlockAccess var0, BlockPosition var1, IBlockData var2) {
      return ItemStack.b;
   }

   @Override
   public boolean a(IBlockData var0, FluidType var1) {
      return false;
   }
}
