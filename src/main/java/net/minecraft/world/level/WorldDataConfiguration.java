package net.minecraft.world.level;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;

public record WorldDataConfiguration(DataPackConfiguration dataPacks, FeatureFlagSet enabledFeatures) {
   private final DataPackConfiguration d;
   private final FeatureFlagSet e;
   public static final String a = "enabled_features";
   public static final Codec<WorldDataConfiguration> b = RecordCodecBuilder.create(
      var0 -> var0.group(
               DataPackConfiguration.b.optionalFieldOf("DataPacks", DataPackConfiguration.a).forGetter(WorldDataConfiguration::a),
               FeatureFlags.e.optionalFieldOf("enabled_features", FeatureFlags.g).forGetter(WorldDataConfiguration::b)
            )
            .apply(var0, WorldDataConfiguration::new)
   );
   public static final WorldDataConfiguration c = new WorldDataConfiguration(DataPackConfiguration.a, FeatureFlags.g);

   public WorldDataConfiguration(DataPackConfiguration var0, FeatureFlagSet var1) {
      this.d = var0;
      this.e = var1;
   }

   public WorldDataConfiguration a(FeatureFlagSet var0) {
      return new WorldDataConfiguration(this.d, this.e.b(var0));
   }

   public DataPackConfiguration a() {
      return this.d;
   }

   public FeatureFlagSet b() {
      return this.e;
   }
}
