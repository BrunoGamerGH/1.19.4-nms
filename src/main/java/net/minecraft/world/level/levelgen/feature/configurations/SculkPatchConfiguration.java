package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;

public record SculkPatchConfiguration(
   int chargeCount, int amountPerCharge, int spreadAttempts, int growthRounds, int spreadRounds, IntProvider extraRareGrowths, float catalystChance
) implements WorldGenFeatureConfiguration {
   private final int b;
   private final int c;
   private final int d;
   private final int e;
   private final int f;
   private final IntProvider g;
   private final float h;
   public static final Codec<SculkPatchConfiguration> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.intRange(1, 32).fieldOf("charge_count").forGetter(SculkPatchConfiguration::a),
               Codec.intRange(1, 500).fieldOf("amount_per_charge").forGetter(SculkPatchConfiguration::b),
               Codec.intRange(1, 64).fieldOf("spread_attempts").forGetter(SculkPatchConfiguration::c),
               Codec.intRange(0, 8).fieldOf("growth_rounds").forGetter(SculkPatchConfiguration::d),
               Codec.intRange(0, 8).fieldOf("spread_rounds").forGetter(SculkPatchConfiguration::f),
               IntProvider.c.fieldOf("extra_rare_growths").forGetter(SculkPatchConfiguration::g),
               Codec.floatRange(0.0F, 1.0F).fieldOf("catalyst_chance").forGetter(SculkPatchConfiguration::h)
            )
            .apply(var0, SculkPatchConfiguration::new)
   );

   public SculkPatchConfiguration(int var0, int var1, int var2, int var3, int var4, IntProvider var5, float var6) {
      this.b = var0;
      this.c = var1;
      this.d = var2;
      this.e = var3;
      this.f = var4;
      this.g = var5;
      this.h = var6;
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

   public int d() {
      return this.e;
   }
}
