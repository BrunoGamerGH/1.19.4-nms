package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.BiomeBase;

public class NoiseThresholdCountPlacement extends RepeatingPlacement {
   public static final Codec<NoiseThresholdCountPlacement> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.DOUBLE.fieldOf("noise_level").forGetter(var0x -> var0x.c),
               Codec.INT.fieldOf("below_noise").forGetter(var0x -> var0x.d),
               Codec.INT.fieldOf("above_noise").forGetter(var0x -> var0x.e)
            )
            .apply(var0, NoiseThresholdCountPlacement::new)
   );
   private final double c;
   private final int d;
   private final int e;

   private NoiseThresholdCountPlacement(double var0, int var2, int var3) {
      this.c = var0;
      this.d = var2;
      this.e = var3;
   }

   public static NoiseThresholdCountPlacement a(double var0, int var2, int var3) {
      return new NoiseThresholdCountPlacement(var0, var2, var3);
   }

   @Override
   protected int a(RandomSource var0, BlockPosition var1) {
      double var2 = BiomeBase.e.a((double)var1.u() / 200.0, (double)var1.w() / 200.0, false);
      return var2 < this.c ? this.d : this.e;
   }

   @Override
   public PlacementModifierType<?> b() {
      return PlacementModifierType.h;
   }
}
