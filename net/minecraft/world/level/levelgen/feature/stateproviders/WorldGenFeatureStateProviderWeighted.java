package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.state.IBlockData;

public class WorldGenFeatureStateProviderWeighted extends WorldGenFeatureStateProvider {
   public static final Codec<WorldGenFeatureStateProviderWeighted> b = SimpleWeightedRandomList.b(IBlockData.b)
      .comapFlatMap(WorldGenFeatureStateProviderWeighted::a, var0 -> var0.c)
      .fieldOf("entries")
      .codec();
   private final SimpleWeightedRandomList<IBlockData> c;

   private static DataResult<WorldGenFeatureStateProviderWeighted> a(SimpleWeightedRandomList<IBlockData> var0) {
      return var0.d() ? DataResult.error(() -> "WeightedStateProvider with no states") : DataResult.success(new WorldGenFeatureStateProviderWeighted(var0));
   }

   public WorldGenFeatureStateProviderWeighted(SimpleWeightedRandomList<IBlockData> var0) {
      this.c = var0;
   }

   public WorldGenFeatureStateProviderWeighted(SimpleWeightedRandomList.a<IBlockData> var0) {
      this(var0.a());
   }

   @Override
   protected WorldGenFeatureStateProviders<?> a() {
      return WorldGenFeatureStateProviders.b;
   }

   @Override
   public IBlockData a(RandomSource var0, BlockPosition var1) {
      return this.c.a(var0).orElseThrow(IllegalStateException::new);
   }
}
