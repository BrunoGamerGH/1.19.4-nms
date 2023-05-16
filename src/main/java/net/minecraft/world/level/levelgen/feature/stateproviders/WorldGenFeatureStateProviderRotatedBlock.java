package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockRotatable;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class WorldGenFeatureStateProviderRotatedBlock extends WorldGenFeatureStateProvider {
   public static final Codec<WorldGenFeatureStateProviderRotatedBlock> b = IBlockData.b
      .fieldOf("state")
      .xmap(BlockBase.BlockData::b, Block::o)
      .xmap(WorldGenFeatureStateProviderRotatedBlock::new, var0 -> var0.c)
      .codec();
   private final Block c;

   public WorldGenFeatureStateProviderRotatedBlock(Block var0) {
      this.c = var0;
   }

   @Override
   protected WorldGenFeatureStateProviders<?> a() {
      return WorldGenFeatureStateProviders.f;
   }

   @Override
   public IBlockData a(RandomSource var0, BlockPosition var1) {
      EnumDirection.EnumAxis var2 = EnumDirection.EnumAxis.a(var0);
      return this.c.o().a(BlockRotatable.g, var2);
   }
}
