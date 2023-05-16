package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.InventoryUtils;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityFurnace;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.block.state.properties.BlockStateDirection;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.Vec3D;

public abstract class BlockFurnace extends BlockTileEntity {
   public static final BlockStateDirection a = BlockFacingHorizontal.aD;
   public static final BlockStateBoolean b = BlockProperties.r;

   protected BlockFurnace(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, EnumDirection.c).a(b, Boolean.valueOf(false)));
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      if (var1.B) {
         return EnumInteractionResult.a;
      } else {
         this.a(var1, var2, var3);
         return EnumInteractionResult.b;
      }
   }

   protected abstract void a(World var1, BlockPosition var2, EntityHuman var3);

   @Override
   public IBlockData a(BlockActionContext var0) {
      return this.o().a(a, var0.g().g());
   }

   @Override
   public void a(World var0, BlockPosition var1, IBlockData var2, EntityLiving var3, ItemStack var4) {
      if (var4.z()) {
         TileEntity var5 = var0.c_(var1);
         if (var5 instanceof TileEntityFurnace) {
            ((TileEntityFurnace)var5).a(var4.x());
         }
      }
   }

   @Override
   public void a(IBlockData var0, World var1, BlockPosition var2, IBlockData var3, boolean var4) {
      if (!var0.a(var3.b())) {
         TileEntity var5 = var1.c_(var2);
         if (var5 instanceof TileEntityFurnace) {
            if (var1 instanceof WorldServer) {
               InventoryUtils.a(var1, var2, (TileEntityFurnace)var5);
               ((TileEntityFurnace)var5).a((WorldServer)var1, Vec3D.b(var2));
            }

            var1.c(var2, this);
         }

         super.a(var0, var1, var2, var3, var4);
      }
   }

   @Override
   public boolean d_(IBlockData var0) {
      return true;
   }

   @Override
   public int a(IBlockData var0, World var1, BlockPosition var2) {
      return Container.a(var1.c_(var2));
   }

   @Override
   public EnumRenderType b_(IBlockData var0) {
      return EnumRenderType.c;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockRotation var1) {
      return var0.a(a, var1.a(var0.c(a)));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumBlockMirror var1) {
      return var0.a(var1.a(var0.c(a)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a, b);
   }

   @Nullable
   protected static <T extends TileEntity> BlockEntityTicker<T> a(World var0, TileEntityTypes<T> var1, TileEntityTypes<? extends TileEntityFurnace> var2) {
      return var0.B ? null : a(var1, var2, TileEntityFurnace::a);
   }
}
