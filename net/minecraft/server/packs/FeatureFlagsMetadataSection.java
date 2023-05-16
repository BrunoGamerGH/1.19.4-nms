package net.minecraft.server.packs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;

public record FeatureFlagsMetadataSection(FeatureFlagSet flags) {
   private final FeatureFlagSet b;
   private static final Codec<FeatureFlagsMetadataSection> c = RecordCodecBuilder.create(
      var0 -> var0.group(FeatureFlags.e.fieldOf("enabled").forGetter(FeatureFlagsMetadataSection::a)).apply(var0, FeatureFlagsMetadataSection::new)
   );
   public static final MetadataSectionType<FeatureFlagsMetadataSection> a = MetadataSectionType.a("features", c);

   public FeatureFlagsMetadataSection(FeatureFlagSet var0) {
      this.b = var0;
   }

   public FeatureFlagSet a() {
      return this.b;
   }
}
