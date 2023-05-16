package net.minecraft.world.level.levelgen.feature.rootplacers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.stateproviders.WorldGenFeatureStateProvider;

public record AboveRootPlacement(WorldGenFeatureStateProvider aboveRootProvider, float aboveRootPlacementChance) {
   private final WorldGenFeatureStateProvider b;
   private final float c;
   public static final Codec<AboveRootPlacement> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               WorldGenFeatureStateProvider.a.fieldOf("above_root_provider").forGetter(var0x -> var0x.b),
               Codec.floatRange(0.0F, 1.0F).fieldOf("above_root_placement_chance").forGetter(var0x -> var0x.c)
            )
            .apply(var0, AboveRootPlacement::new)
   );

   public AboveRootPlacement(WorldGenFeatureStateProvider var0, float var1) {
      this.b = var0;
      this.c = var1;
   }

   public WorldGenFeatureStateProvider a() {
      return this.b;
   }

   public float b() {
      return this.c;
   }
}
