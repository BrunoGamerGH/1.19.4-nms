package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityBanner;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public abstract class BlockBannerAbstract extends BlockTileEntity {
   private final EnumColor a;

   protected BlockBannerAbstract(EnumColor var0, BlockBase.Info var1) {
      super(var1);
      this.a = var0;
   }

   @Override
   public boolean ao_() {
      return true;
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new TileEntityBanner(var0, var1, this.a);
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, @Nullable EntityLiving var3, ItemStack var4) {
      if (var0.B) {
         var0.a(var1, TileEntityTypes.t).ifPresent(var1x -> var1x.b(var4));
      } else if (var4.z()) {
         var0.a(var1, TileEntityTypes.t).ifPresent(var1x -> var1x.a(var4.x()));
      }
   }

   @Override
   public ItemStack a(IBlockAccess var0, BlockPosition var1, IBlockData var2) {
      TileEntity var3 = var0.c_(var1);
      return var3 instanceof TileEntityBanner ? ((TileEntityBanner)var3).f() : super.a(var0, var1, var2);
   }

   public EnumColor b() {
      return this.a;
   }
}
