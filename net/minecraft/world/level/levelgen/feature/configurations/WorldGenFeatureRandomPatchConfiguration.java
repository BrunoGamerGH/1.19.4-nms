package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public record WorldGenFeatureRandomPatchConfiguration(int tries, int xzSpread, int ySpread, Holder<PlacedFeature> feature)
   implements WorldGenFeatureConfiguration {
   private final int b;
   private final int c;
   private final int d;
   private final Holder<PlacedFeature> e;
   public static final Codec<WorldGenFeatureRandomPatchConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               ExtraCodecs.i.fieldOf("tries").orElse(128).forGetter(WorldGenFeatureRandomPatchConfiguration::a),
               ExtraCodecs.h.fieldOf("xz_spread").orElse(7).forGetter(WorldGenFeatureRandomPatchConfiguration::b),
               ExtraCodecs.h.fieldOf("y_spread").orElse(3).forGetter(WorldGenFeatureRandomPatchConfiguration::c),
               PlacedFeature.b.fieldOf("feature").forGetter(WorldGenFeatureRandomPatchConfiguration::d)
            )
            .apply(var0, WorldGenFeatureRandomPatchConfiguration::new)
   );

   public WorldGenFeatureRandomPatchConfiguration(int var0, int var1, int var2, Holder<PlacedFeature> var3) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
   }

   public int a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }

   public Holder<PlacedFeature> d() {
      return this.e;
   }
}
