package net.minecraft.world.level.block;

import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.sounds.SoundEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.block.state.IBlockData;

public interface IFluidSource {
   ItemStack c(GeneratorAccess var1, BlockPosition var2, IBlockData var3);

   Optional<SoundEffect> an_();
}
