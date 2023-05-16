package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;

public class WorldGenFeatureMushroomConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureMushroomConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               WorldGenFeatureStateProvider.a.fieldOf("cap_provider").forGetter(var0x -> var0x.b),
               WorldGenFeatureStateProvider.a.fieldOf("stem_provider").forGetter(var0x -> var0x.c),
               Codec.INT.fieldOf("foliage_radius").orElse(2).forGetter(var0x -> var0x.d)
            )
            .apply(var0, WorldGenFeatureMushroomConfiguration::new)
   );
   public final WorldGenFeatureStateProvider b;
   public final WorldGenFeatureStateProvider c;
   public final int d;

   public WorldGenFeatureMushroomConfiguration(WorldGenFeatureStateProvider var0, WorldGenFeatureStateProvider var1, int var2) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
   }
}
