package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.BlockStateList;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidTypes;

public class MangroveRootsBlock extends Block implements IBlockWaterlogged {
   public static final BlockStateBoolean a = BlockProperties.C;

   protected MangroveRootsBlock(BlockBase.Info var0) {
      super(var0);
      this.k(this.D.b().a(a, Boolean.valueOf(false)));
   }

   @Override
   public boolean a(IBlockData var0, IBlockData var1, EnumDirection var2) {
      return var1.a(Blocks.ab) && var2.o() == EnumDirection.EnumAxis.b;
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      Fluid var1 = var0.q().b_(var0.a());
      boolean var2 = var1.a() == FluidTypes.c;
      return super.a(var0).a(a, Boolean.valueOf(var2));
   }

   @Override
   public IBlockData a(IBlockData var0, EnumDirection var1, IBlockData var2, GeneratorAccess var3, BlockPosition var4, BlockPosition var5) {
      if (var0.c(a)) {
         var3.a(var4, FluidTypes.c, FluidTypes.c.a(var3));
      }

      return super.a(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return var0.c(a) ? FluidTypes.c.a(false) : super.c_(var0);
   }

   @Override
   protected void a(BlockStateList.a<Block, IBlockData> var0) {
      var0.a(a);
   }
}
