package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.biome.BiomeBase;

public class NoiseBasedCountPlacement extends RepeatingPlacement {
   public static final Codec<NoiseBasedCountPlacement> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               Codec.INT.fieldOf("noise_to_count_ratio").forGetter(var0x -> var0x.c),
               Codec.DOUBLE.fieldOf("noise_factor").forGetter(var0x -> var0x.d),
               Codec.DOUBLE.fieldOf("noise_offset").orElse(0.0).forGetter(var0x -> var0x.e)
            )
            .apply(var0, NoiseBasedCountPlacement::new)
   );
   private final int c;
   private final double d;
   private final double e;

   private NoiseBasedCountPlacement(int var0, double var1, double var3) {
      this.c = var0;
      this.d = var1;
      this.e = var3;
   }

   public static NoiseBasedCountPlacement a(int var0, double var1, double var3) {
      return new NoiseBasedCountPlacement(var0, var1, var3);
   }

   @Override
   protected int a(RandomSource var0, BlockPosition var1) {
      double var2 = BiomeBase.e.a((double)var1.u() / this.d, (double)var1.w() / this.d, false);
      return (int)Math.ceil((var2 + this.e) * (double)this.c);
   }

   @Override
   public PlacementModifierType<?> b() {
      return PlacementModifierType.g;
   }
}
