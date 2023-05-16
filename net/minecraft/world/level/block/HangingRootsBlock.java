package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapeCollision;

public class HangingRootsBlock extends Block implements IBlockWaterlogged {
   private static final BlockStateBoolean b = BlockProperties.C;
   protected static final VoxelShape a = Block.a(2.0, 10.0, 2.0, 14.0, 16.0, 14.0);

   protected HangingRootsBlock(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(b, Boolean.valueOf(false)));
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(b);
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(b) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      IBlockData var1 = super.a(var0);
      if (var1 != null) {
         Fluid var2 = var0.q().b_(var0.a());
         return var1.a(b, Boolean.valueOf(var2.a() == FluidTypes.c));
      } else {
         return null;
      }
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      BlockPosition var3 = var2.c();
      IBlockData var4 = var1.a_(var3);
      return var4.d(var1, var3, EnumDirection.a);
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return a;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var1 == EnumDirection.b && !this.a(var0, var3, var4)) {
         return Blocks.a.o();
      } else {
         if (var0.c(b)) {
            var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
         }

         return super.a(var0, var1, var2, var3, var4, var5);
      }
   }
}
