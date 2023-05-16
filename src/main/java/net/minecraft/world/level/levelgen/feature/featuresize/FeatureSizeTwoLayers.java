package net.minecraft.world.level.levelgen.feature.featuresize;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.OptionalInt;

public class FeatureSizeTwoLayers extends FeatureSize {
   public static final Codec<FeatureSizeTwoLayers> d = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.intRange(0, 81).fieldOf("limit").orElse(1).forGetter(var0x -> var0x.e),
               Codec.intRange(0, 16).fieldOf("lower_size").orElse(0).forGetter(var0x -> var0x.f),
               Codec.intRange(0, 16).fieldOf("upper_size").orElse(1).forGetter(var0x -> var0x.g),
               a()
            )
            .apply(var0, FeatureSizeTwoLayers::new)
   );
   private final int e;
   private final int f;
   private final int g;

   public FeatureSizeTwoLayers(int var0, int var1, int var2) {
      this(var0, var1, var2, OptionalInt.empty());
   }

   public FeatureSizeTwoLayers(int var0, int var1, int var2, OptionalInt var3) {
      super(var3);
      this.e = var0;
      this.f = var1;
      this.g = var2;
   }

   @Override
   protected FeatureSizeType<?> b() {
      return FeatureSizeType.a;
   }

   @Override
   public int a(int var0, int var1) {
      return var1 < this.e ? this.f : this.g;
   }
}
