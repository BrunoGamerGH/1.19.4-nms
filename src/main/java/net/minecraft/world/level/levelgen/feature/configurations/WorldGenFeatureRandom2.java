package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import java.util.stream.Stream;
import net.minecraft.core.HolderSet;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class WorldGenFeatureRandom2 implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureRandom2> a = ExtraCodecs.b(PlacedFeature.c)
      .fieldOf("features")
      .xmap(WorldGenFeatureRandom2::new, var0 -> var0.b)
      .codec();
   public final HolderSet<PlacedFeature> b;

   public WorldGenFeatureRandom2(HolderSet<PlacedFeature> var0) {
      this.b = var0;
   }

   @Override
   public Stream<WorldGenFeatureConfigured<?, ?>> e() {
      return this.b.a().flatMap(var0 -> var0.a().a());
   }
}
