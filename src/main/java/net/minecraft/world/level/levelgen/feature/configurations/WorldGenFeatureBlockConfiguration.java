package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;

public record WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider toPlace) implements WorldGenFeatureConfiguration {
   private final WorldGenFeatureStateProvider b;
   public static final Codec<WorldGenFeatureBlockConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(WorldGenFeatureStateProvider.a.fieldOf("to_place").forGetter(var0x -> var0x.b)).apply(var0, WorldGenFeatureBlockConfiguration::new)
   );

   public WorldGenFeatureBlockConfiguration(WorldGenFeatureStateProvider var0) {
      this.b = var0;
   }

   public WorldGenFeatureStateProvider a() {
      return this.b;
   }
}
