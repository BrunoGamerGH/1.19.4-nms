package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.EnumColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityBeacon;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class BlockBeacon extends BlockTileEntity implements IBeaconBeam {
   public BlockBeacon(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public EnumColor a() {
      return EnumColor.a;
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new TileEntityBeacon(var0, var1);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World var0, IBlockData var1, TileEntityTypes<T> var2) {
      return a(var2, TileEntityTypes.o, TileEntityBeacon::a);
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      if (var1.B) {
         return EnumInteractionResult.a;
      } else {
         TileEntity var6 = var1.c_(var2);
         if (var6 instanceof TileEntityBeacon) {
            var3.a((TileEntityBeacon)var6);
            var3.a(StatisticList.ab);
         }

         return EnumInteractionResult.b;
      }
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.c;
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, EntityLiving var3, ItemStack var4) {
      if (var4.z()) {
         TileEntity var5 = var0.c_(var1);
         if (var5 instanceof TileEntityBeacon) {
            ((TileEntityBeacon)var5).a(var4.x());
         }
      }
   }
}
