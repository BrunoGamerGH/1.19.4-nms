package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.WorldGenFeatureConfigured;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class WorldGenFeatureChoiceConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureChoiceConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               PlacedFeature.b.fieldOf("feature_true").forGetter(var0x -> var0x.b), PlacedFeature.b.fieldOf("feature_false").forGetter(var0x -> var0x.c)
            )
            .apply(var0, WorldGenFeatureChoiceConfiguration::new)
   );
   public final Holder<PlacedFeature> b;
   public final Holder<PlacedFeature> c;

   public WorldGenFeatureChoiceConfiguration(Holder<PlacedFeature> var0, Holder<PlacedFeature> var1) {
      this.b = var0;
      this.c = var1;
   }

   @Override
   public Stream<WorldGenFeatureConfigured<?, ?>> e() {
      return Stream.concat(this.b.a().a(), this.c.a().a());
   }
}
