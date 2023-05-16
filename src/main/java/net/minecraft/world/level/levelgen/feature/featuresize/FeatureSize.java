package net.minecraft.world.level.levelgen.feature.featuresize;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.core.registries.BuiltInRegistries;

public abstract class FeatureSize {
   public static final Codec<FeatureSize> a = BuiltInRegistries.aa.q().dispatch(FeatureSize::b, FeatureSizeType::a);
   protected static final int b = 16;
   protected final OptionalInt c;

   protected static <S extends FeatureSize> RecordCodecBuilder<S, OptionalInt> a() {
      return Codec.intRange(0, 80)
         .optionalFieldOf("min_clipped_height")
         .xmap(var0 -> var0.map(OptionalInt::of).orElse(OptionalInt.empty()), var0 -> var0.isPresent() ? Optional.of(var0.getAsInt()) : Optional.empty())
         .forGetter(var0 -> var0.c);
   }

   public FeatureSize(OptionalInt var0) {
      this.c = var0;
   }

   protected abstract FeatureSizeType<?> b();

   public abstract int a(int var1, int var2);

   public OptionalInt c() {
      return this.c;
   }
}
