package net.minecraft.world.level.block;

import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.World;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class PowderSnowCauldronBlock extends LayeredCauldronBlock {
   public PowderSnowCauldronBlock(BlockBase.Info var0, Predicate<BiomeBase.Precipitation> var1, Map<Item, CauldronInteraction> var2) {
      super(var0, var1, var2);
   }

   @Override
   protected void d(IBlockData var0, World var1, BlockPosition var2) {
      e(Blocks.ft.o().a(e, var0.c(e)), var1, var2);
   }
}
