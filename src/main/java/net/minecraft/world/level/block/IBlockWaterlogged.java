package net.minecraft.world.level.block;

import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidType;
import net.minecraft.world.level.material.FluidTypes;

public interface IBlockWaterlogged extends IFluidSource, IFluidContainer {
   @Override
   default boolean a(IBlockAccess var0, BlockPosition var1, IBlockData var2, FluidType var3) {
      return var3 == FluidTypes.c;
   }

   @Override
   default boolean a(GeneratorAccess var0, BlockPosition var1, IBlockData var2, Fluid var3) {
      if (!var2.c(BlockProperties.C) && var3.a() == FluidTypes.c) {
         if (!var0.k_()) {
            var0.a(var1, var2.a(BlockProperties.C, Boolean.valueOf(true)), 3);
            var0.a(var1, var3.a(), var3.a().a(var0));
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   default ItemStack c(GeneratorAccess var0, BlockPosition var1, IBlockData var2) {
      if (var2.c(BlockProperties.C)) {
         var0.a(var1, var2.a(BlockProperties.C, Boolean.valueOf(false)), 3);
         if (!var2.a(var0, var1)) {
            var0.b(var1, true);
         }

         return new ItemStack(Items.pH);
      } else {
         return ItemStack.b;
      }
   }

   @Override
   default Optional<SoundEffect> an_() {
      return FluidTypes.c.j();
   }
}
