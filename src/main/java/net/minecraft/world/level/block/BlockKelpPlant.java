package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.phys.shapes.VoxelShapes;

public class BlockKelpPlant extends BlockGrowingStem implements IFluidContainer {
   protected BlockKelpPlant(BlockBase.Info var0) {
      super(var0, EnumDirection.b, VoxelShapes.b(), true);
   }

   @Override
   protected BlockGrowingTop c() {
      return (BlockGrowingTop)Blocks.lZ;
   }

   @Override
   public Fluid c_(IBlockData var0) {
      return FluidTypes.c.a(false);
   }

   @Override
   protected boolean h(IBlockData var0) {
      return this.c().h(var0);
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
