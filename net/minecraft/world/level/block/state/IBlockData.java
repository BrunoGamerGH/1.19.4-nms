package net.minecraft.world.level.block.state;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IBlockState;

public class IBlockData extends BlockBase.BlockData {
   public static final Codec<IBlockData> b = a(BuiltInRegistries.f.q(), Block::o).stable();

   public IBlockData(Block var0, ImmutableMap<IBlockState<?>, Comparable<?>> var1, MapCodec<IBlockData> var2) {
      super(var0, var1, var2);
   }

   @Override
   protected IBlockData u() {
      return this;
   }
}
