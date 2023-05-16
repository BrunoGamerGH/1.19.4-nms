package net.minecraft.world.level.levelgen.feature.featuresize;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public class FeatureSizeType<P extends FeatureSize> {
   public static final FeatureSizeType<FeatureSizeTwoLayers> a = a("two_layers_feature_size", FeatureSizeTwoLayers.d);
   public static final FeatureSizeType<FeatureSizeThreeLayers> b = a("three_layers_feature_size", FeatureSizeThreeLayers.d);
   private final Codec<P> c;

   private static <P extends FeatureSize> FeatureSizeType<P> a(String var0, Codec<P> var1) {
      return IRegistry.a(BuiltInRegistries.aa, var0, new FeatureSizeType<>(var1));
   }

   private FeatureSizeType(Codec<P> var0) {
      this.c = var0;
   }

   public Codec<P> a() {
      return this.c;
   }
}
