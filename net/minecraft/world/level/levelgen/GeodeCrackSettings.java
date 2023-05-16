package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;

public class GeodeCrackSettings {
   public static final Codec<GeodeCrackSettings> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               GeodeConfiguration.a.fieldOf("generate_crack_chance").orElse(1.0).forGetter(var0x -> var0x.b),
               Codec.doubleRange(0.0, 5.0).fieldOf("base_crack_size").orElse(2.0).forGetter(var0x -> var0x.c),
               Codec.intRange(0, 10).fieldOf("crack_point_offset").orElse(2).forGetter(var0x -> var0x.d)
            )
            .apply(var0, GeodeCrackSettings::new)
   );
   public final double b;
   public final double c;
   public final int d;

   public GeodeCrackSettings(double var0, double var2, int var4) {
      this.b = var0;
      this.c = var2;
      this.d = var4;
   }
}
