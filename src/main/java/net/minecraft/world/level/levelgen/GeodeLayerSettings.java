package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class GeodeLayerSettings {
   private static final Codec<Double> f = Codec.doubleRange(0.01, 50.0);
   public static final Codec<GeodeLayerSettings> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               f.fieldOf("filling").orElse(1.7).forGetter(var0x -> var0x.b),
               f.fieldOf("inner_layer").orElse(2.2).forGetter(var0x -> var0x.c),
               f.fieldOf("middle_layer").orElse(3.2).forGetter(var0x -> var0x.d),
               f.fieldOf("outer_layer").orElse(4.2).forGetter(var0x -> var0x.e)
            )
            .apply(var0, GeodeLayerSettings::new)
   );
   public final double b;
   public final double c;
   public final double d;
   public final double e;

   public GeodeLayerSettings(double var0, double var2, double var4, double var6) {
      this.b = var0;
      this.c = var2;
      this.d = var4;
      this.e = var6;
   }
}
