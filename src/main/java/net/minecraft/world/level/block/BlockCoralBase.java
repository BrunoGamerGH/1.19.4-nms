package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsFluid;
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

public class BlockCoralBase extends Block implements IBlockWaterlogged {
   public static final BlockStateBoolean c = BlockProperties.C;
   private static final VoxelShape a = Block.a(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);

   protected BlockCoralBase(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(c, Boolean.valueOf(true)));
   }

   protected void a(IBlockData var0, GeneratorAccess var1, BlockPosition var2) {
      if (!e(var0, var1, var2)) {
         var1.a(var2, this, 60 + var1.r_().a(40));
      }
   }

   protected static boolean e(IBlockData var0, IBlockAccess var1, BlockPosition var2) {
      if (var0.c(c)) {
         return true;
      } else {
         for(EnumDirection var6 : EnumDirection.values()) {
            if (var1.b_(var2.a(var6)).a(TagsFluid.a)) {
               return true;
            }
         }

         return false;
      }
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      Fluid var1 = var0.q().b_(var0.a());
      return this.o().a(c, Boolean.valueOf(var1.a(TagsFluid.a) && var1.e() == 8));
   }

   @Override
   public VoxelShape a(IBlockData var0, IBlockAccess var1, BlockPosition var2, VoxelShapeCollision var3) {
      return a;
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(c)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return var1 == EnumDirection.a && !this.a(var0, (IWorldReader)var3, var4) ? Blocks.a.o() : super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public boolean a(IBlockData var0, IWorldReader var1, BlockPosition var2) {
      BlockPosition var3 = var2.d();
      return var1.a_(var3).d(var1, var3, EnumDirection.b);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(c);
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(c) ? FluidTypes.c.a(false) : super.c_(var0);
   }
}
