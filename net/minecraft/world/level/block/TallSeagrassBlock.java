package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsFluid;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyDoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.BlockStateEnum;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class TallSeagrassBlock extends BlockTallPlant implements IFluidContainer {
   public static final BlockStateEnum<BlockPropertyDoubleBlockHalf> b = BlockTallPlant.a;
   protected static final float c = 6.0F;
   protected static final VoxelShape d = Block.a(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

   public TallSeagrassBlock(BlockBase.Info var0) {
      super(var0);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return d;
   }

   @Override
   protected boolean d(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      return var0.d(var1, var2, EnumDirection.b) && !var0.a(Blocks.kG);
   }

   @Override
   public ItemStack a(IBlockAccess var0, BlockPosition var1, IBlockData var2) {
      return new ItemStack(Blocks.bv);
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      IBlockData var1 = super.a(var0);
      if (var1 != null) {
         Fluid var2 = var0.q().b_(var0.a().c());
         if (var2.a(TagsFluid.a) && var2.e() == 8) {
            return var1;
         }
      }

      return null;
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      if (var0.c(b) == BlockPropertyDoubleBlockHalf.a) {
         IBlockData var3 = var1.a_(var2.d());
         return var3.a(this) && var3.c(b) == BlockPropertyDoubleBlockHalf.b;
      } else {
         Fluid var3 = var1.b_(var2);
         return super.a(var0, var1, var2) && var3.a(TagsFluid.a) && var3.e() == 8;
      }
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return FluidTypes.c.a(false);
   }

   @Override
   public boolean a(IBlockAccess var0, BlockPosition var1, IBlockData var2, FluidType var3) {
      return false;
   }

   @Override
   public boolean a(GeneratorAccess var0, BlockPosition var1, IBlockData var2, Fluid var3) {
      return false;
   }
}
