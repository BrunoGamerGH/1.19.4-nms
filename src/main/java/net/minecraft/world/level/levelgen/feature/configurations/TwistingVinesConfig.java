package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

public record TwistingVinesConfig(int spreadWidth, int spreadHeight, int maxHeight) implements WorldGenFeatureConfiguration {
   private final int b;
   private final int c;
   private final int d;
   public static final Codec<TwistingVinesConfig> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               ExtraCodecs.i.fieldOf("spread_width").forGetter(TwistingVinesConfig::a),
               ExtraCodecs.i.fieldOf("spread_height").forGetter(TwistingVinesConfig::b),
               ExtraCodecs.i.fieldOf("max_height").forGetter(TwistingVinesConfig::c)
            )
            .apply(var0, TwistingVinesConfig::new)
   );

   public TwistingVinesConfig(int var0, int var1, int var2) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
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
}
