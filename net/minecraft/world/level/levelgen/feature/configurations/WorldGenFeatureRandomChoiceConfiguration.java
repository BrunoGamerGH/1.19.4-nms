package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class WorldGenFeatureRandomChoiceConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureRandomChoiceConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.apply2(
            WorldGenFeatureRandomChoiceConfiguration::new,
            WeightedPlacedFeature.a.listOf().fieldOf("features").forGetter(var0x -> var0x.b),
            PlacedFeature.b.fieldOf("default").forGetter(var0x -> var0x.c)
         )
   );
   public final List<WeightedPlacedFeature> b;
   public final Holder<PlacedFeature> c;

   public WorldGenFeatureRandomChoiceConfiguration(List<WeightedPlacedFeature> var0, Holder<PlacedFeature> var1) {
      this.b = var0;
      this.c = var1;
   }

   @Override
   public Stream<WorldGenFeatureConfigured<?, ?>> e() {
      return Stream.concat(this.b.stream().flatMap(var0 -> var0.b.a().a()), this.c.a().a());
   }
}
