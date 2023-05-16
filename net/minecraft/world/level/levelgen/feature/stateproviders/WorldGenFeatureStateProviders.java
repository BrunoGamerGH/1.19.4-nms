package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public class WorldGenFeatureStateProviders<P extends WorldGenFeatureStateProvider> {
   public static final WorldGenFeatureStateProviders<WorldGenFeatureStateProviderSimpl> a = a("simple_state_provider", WorldGenFeatureStateProviderSimpl.b);
   public static final WorldGenFeatureStateProviders<WorldGenFeatureStateProviderWeighted> b = a(
      "weighted_state_provider", WorldGenFeatureStateProviderWeighted.b
   );
   public static final WorldGenFeatureStateProviders<NoiseThresholdProvider> c = a("noise_threshold_provider", NoiseThresholdProvider.b);
   public static final WorldGenFeatureStateProviders<NoiseProvider> d = a("noise_provider", NoiseProvider.g);
   public static final WorldGenFeatureStateProviders<DualNoiseProvider> e = a("dual_noise_provider", DualNoiseProvider.b);
   public static final WorldGenFeatureStateProviders<WorldGenFeatureStateProviderRotatedBlock> f = a(
      "rotated_block_provider", WorldGenFeatureStateProviderRotatedBlock.b
   );
   public static final WorldGenFeatureStateProviders<RandomizedIntStateProvider> g = a("randomized_int_state_provider", RandomizedIntStateProvider.b);
   private final Codec<P> h;

   private static <P extends WorldGenFeatureStateProvider> WorldGenFeatureStateProviders<P> a(String var0, Codec<P> var1) {
      return IRegistry.a(BuiltInRegistries.V, var0, new WorldGenFeatureStateProviders<>(var1));
   }

   private WorldGenFeatureStateProviders(Codec<P> var0) {
      this.h = var0;
   }

   public Codec<P> a() {
      return this.h;
   }
}
