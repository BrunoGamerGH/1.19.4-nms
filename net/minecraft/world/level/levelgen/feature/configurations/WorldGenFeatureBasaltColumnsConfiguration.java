package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;

public class WorldGenFeatureBasaltColumnsConfiguration implements WorldGenFeatureConfiguration {
   public static final Codec<WorldGenFeatureBasaltColumnsConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(IntProvider.b(0, 3).fieldOf("reach").forGetter(var0x -> var0x.b), IntProvider.b(1, 10).fieldOf("height").forGetter(var0x -> var0x.c))
            .apply(var0, WorldGenFeatureBasaltColumnsConfiguration::new)
   );
   private final IntProvider b;
   private final IntProvider c;

   public WorldGenFeatureBasaltColumnsConfiguration(IntProvider var0, IntProvider var1) {
      this.b = var0;
      this.c = var1;
   }

   public IntProvider a() {
      return this.b;
   }

   public IntProvider b() {
      return this.c;
   }
}
