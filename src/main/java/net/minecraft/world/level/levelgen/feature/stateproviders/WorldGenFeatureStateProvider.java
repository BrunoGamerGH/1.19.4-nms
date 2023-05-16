package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.IBlockData;

public abstract class WorldGenFeatureStateProvider {
   public static final Codec<WorldGenFeatureStateProvider> a = BuiltInRegistries.V
      .q()
      .dispatch(WorldGenFeatureStateProvider::a, WorldGenFeatureStateProviders::a);

   public static WorldGenFeatureStateProviderSimpl a(IBlockData var0) {
      return new WorldGenFeatureStateProviderSimpl(var0);
   }

   public static WorldGenFeatureStateProviderSimpl a(Block var0) {
      return new WorldGenFeatureStateProviderSimpl(var0.o());
   }

   protected abstract WorldGenFeatureStateProviders<?> a();

   public abstract IBlockData a(RandomSource var1, BlockPosition var2);
}
