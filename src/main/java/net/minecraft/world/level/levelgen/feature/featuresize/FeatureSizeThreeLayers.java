package net.minecraft.world.level.levelgen.feature.featuresize;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.OptionalInt;

public class FeatureSizeThreeLayers extends FeatureSize {
   public static final Codec<FeatureSizeThreeLayers> d = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.intRange(0, 80).fieldOf("limit").orElse(1).forGetter(var0x -> var0x.e),
               Codec.intRange(0, 80).fieldOf("upper_limit").orElse(1).forGetter(var0x -> var0x.f),
               Codec.intRange(0, 16).fieldOf("lower_size").orElse(0).forGetter(var0x -> var0x.g),
               Codec.intRange(0, 16).fieldOf("middle_size").orElse(1).forGetter(var0x -> var0x.h),
               Codec.intRange(0, 16).fieldOf("upper_size").orElse(1).forGetter(var0x -> var0x.i),
               a()
            )
            .apply(var0, FeatureSizeThreeLayers::new)
   );
   private final int e;
   private final int f;
   private final int g;
   private final int h;
   private final int i;

   public FeatureSizeThreeLayers(int var0, int var1, int var2, int var3, int var4, OptionalInt var5) {
      super(var5);
      this.e = var0;
      this.f = var1;
      this.g = var2;
      this.h = var3;
      this.i = var4;
   }

   @Override
   protected FeatureSizeType<?> b() {
      return FeatureSizeType.b;
   }

   @Override
   public int a(int var0, int var1) {
      if (var1 < this.e) {
         return this.g;
      } else {
         return var1 >= var0 - this.f ? this.i : this.h;
      }
   }
}
