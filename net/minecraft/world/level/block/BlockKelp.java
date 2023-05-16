package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockActionContext;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockKelp extends BlockGrowingTop implements IFluidContainer {
   protected static final VoxelShape f = Block.a(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);
   private static final double g = 0.14;

   protected BlockKelp(BlockBase.Info var0) {
      super(var0, EnumDirection.b, f, true, 0.14);
   }

   @Override
   protected boolean g(IBlockData var0) {
      return var0.a(Blocks.G);
   }

   @Override
   protected Block b() {
      return Blocks.ma;
   }

   @Override
   protected boolean h(IBlockData var0) {
      return !var0.a(Blocks.kG);
   }

   @Override
   public boolean a(IBlockAccess var0, BlockPosition var1, IBlockData var2, FluidType var3) {
      return false;
   }

   @Override
   public boolean a(GeneratorAccess var0, BlockPosition var1, IBlockData var2, Fluid var3) {
      return false;
   }

   @Override
   protected int a(RandomSource var0) {
      return 1;
   }

   @Nullable
   @Override
   public IBlockData a(BlockActionContext var0) {
      Fluid var1 = var0.q().b_(var0.a());
      return var1.a(TagsFluid.a) && var1.e() == 8 ? super.a(var0) : null;
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return FluidTypes.c.a(false);
   }
}
