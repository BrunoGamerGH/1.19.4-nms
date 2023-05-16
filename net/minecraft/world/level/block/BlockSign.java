package net.minecraft.world.level.block;

import net.minecraft.advancements.CriterionTriggers;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.stats.StatisticList;
import net.minecraft.world.EnumHand;
import net.minecraft.world.EnumInteractionResult;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDye;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntitySign;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockPropertyWood;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.MovingObjectPositionBlock;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public abstract class BlockSign extends BlockTileEntity implements IBlockWaterlogged {
   public static final BlockStateBoolean e = BlockProperties.C;
   protected static final float f = 4.0F;
   protected static final VoxelShape g = Block.a(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
   private final BlockPropertyWood a;

   protected BlockSign(BlockBase.Info var0, BlockPropertyWood var1) {
      super(var0);
      this.a = var1;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(e)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return g;
   }

   @Override
   public boolean ao_() {
      return true;
   }

   @Override
   public TileEntity a(BlockPosition var0, IBlockData var1) {
      return new TileEntitySign(var0, var1);
   }

   @Override
   public EnumInteractionResult a(IBlockData var0, World var1, BlockPosition var2, EntityHuman var3, EnumHand var4, MovingObjectPositionBlock var5) {
      ItemStack var6 = var3.b(var4);
      Item var7 = var6.c();
      boolean var8 = var7 instanceof ItemDye;
      boolean var9 = var6.a(Items.qo);
      boolean var10 = var6.a(Items.qn);
      boolean var11 = (var9 || var8 || var10) && var3.fK().e;
      if (var1.B) {
         return var11 ? EnumInteractionResult.a : EnumInteractionResult.b;
      } else {
         TileEntity var13 = var1.c_(var2);
         if (!(var13 instanceof TileEntitySign)) {
            return EnumInteractionResult.d;
         } else {
            TileEntitySign var12 = (TileEntitySign)var13;
            boolean var13 = var12.v();
            if ((!var9 || !var13) && (!var10 || var13)) {
               if (var11) {
                  boolean var14;
                  if (var9) {
                     var1.a(null, var2, SoundEffects.jk, SoundCategory.e, 1.0F, 1.0F);
                     var14 = var12.b(true);
                     if (var3 instanceof EntityPlayer) {
                        CriterionTriggers.M.a((EntityPlayer)var3, var2, var6);
                     }
                  } else if (var10) {
                     var1.a(null, var2, SoundEffects.lA, SoundCategory.e, 1.0F, 1.0F);
                     var14 = var12.b(false);
                  } else {
                     var1.a(null, var2, SoundEffects.gB, SoundCategory.e, 1.0F, 1.0F);
                     var14 = var12.a(((ItemDye)var7).d());
                  }

                  if (var14) {
                     if (!var3.f()) {
                        var6.h(1);
                     }

                     var3.b(StatisticList.c.b(var7));
                  }
               }

               return var12.a((EntityPlayer)var3) ? EnumInteractionResult.a : EnumInteractionResult.d;
            } else {
               return EnumInteractionResult.d;
            }
         }
      }
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(e) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   public BlockPropertyWood d() {
      return this.a;
   }

   public static BlockPropertyWood a(Block var0) {
      BlockPropertyWood var1;
      if (var0 instanceof BlockSign) {
         var1 = ((BlockSign)var0).d();
      } else {
         var1 = BlockPropertyWood.a;
      }

      return var1;
   }
}
