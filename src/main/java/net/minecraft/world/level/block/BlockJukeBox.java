package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemBlock;
import net.minecraft.world.item.ItemRecord;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityJukeBox;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.phys.MovingObjectPositionBlock;

public class BlockJukeBox extends BlockTileEntity {
   public static final BlockStateBoolean a = BlockProperties.n;

   protected BlockJukeBox(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, Boolean.valueOf(false)));
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, @Nullable EntityLiving var3, ItemStack var4) {
      super.a(var0, var1, var2, var3, var4);
      NBTTagCompound var5 = ItemBlock.a(var4);
      if (var5 != null && var5.e("RecordItem")) {
         var0.a(var1, var2.a(a, Boolean.valueOf(true)), 2);
      }
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      if (var0.c(a)) {
         TileEntity var8 = var1.c_(var2);
         if (var8 instanceof TileEntityJukeBox var6) {
            var6.i();
            return EnumInteractionResult.a(var1.B);
         }
      }

      return EnumInteractionResult.d;
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      if (!var0.a(var3.b())) {
         TileEntity var7 = var1.c_(var2);
         if (var7 instanceof TileEntityJukeBox var5) {
            var5.i();
         }

         super.a(var0, var1, var2, var3, var4);
      }
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new TileEntityJukeBox(var0, var1);
   }

   @Override
   public boolean f_(IBlockData var0) {
      return true;
   }

   @Override
   public int a(IBlockData var0, IBlockAccess var1, BlockPosition var2, EnumDirection var3) {
      TileEntity var6 = var1.c_(var2);
      if (var6 instanceof TileEntityJukeBox var4 && var4.f()) {
         return 15;
      }

      return 0;
   }

   @Override
   public boolean d_(IBlockData var0) {
      return true;
   }

   @Override
   public int a(IBlockData var0, World var1, BlockPosition var2) {
      TileEntity var6 = var1.c_(var2);
      if (var6 instanceof TileEntityJukeBox var3) {
         Item var7 = var3.at_().c();
         if (var7 instanceof ItemRecord var4) {
            return var4.h();
         }
      }

      return 0;
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.c;
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a);
   }

   @Nullable
   @Override
   public <T extends TileEntity> BlockEntityTicker<T> a(World var0, IBlockData var1, TileEntityTypes<T> var2) {
      return var1.c(a) ? a(var2, TileEntityTypes.e, TileEntityJukeBox::a) : null;
   }
}
