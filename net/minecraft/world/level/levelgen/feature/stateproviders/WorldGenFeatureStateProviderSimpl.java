package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.IBlockData;

public class WorldGenFeatureStateProviderSimpl extends WorldGenFeatureStateProvider {
   public static final Codec<WorldGenFeatureStateProviderSimpl> b = IBlockData.b
      .fieldOf("state")
      .xmap(WorldGenFeatureStateProviderSimpl::new, var0 -> var0.c)
      .codec();
   private final IBlockData c;

   protected WorldGenFeatureStateProviderSimpl(IBlockData var0) {
      this.c = var0;
   }

   @Override
   protected WorldGenFeatureStateProviders<?> a() {
      return WorldGenFeatureStateProviders.a;
   }

   @Override
   public IBlockData a(RandomSource var0, BlockPosition var1) {
      return this.c;
   }
}
