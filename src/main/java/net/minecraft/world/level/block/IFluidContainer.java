package net.minecraft.world.level.block;

import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;

public interface IFluidContainer {
   boolean a(IBlockAccess var1, BlockPosition var2, IBlockData var3, FluidType var4);

   boolean a(GeneratorAccess var1, BlockPosition var2, IBlockData var3, Fluid var4);
}
