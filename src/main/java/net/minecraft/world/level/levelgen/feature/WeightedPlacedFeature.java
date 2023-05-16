package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class WeightedPlacedFeature {
   public static final Codec<WeightedPlacedFeature> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               PlacedFeature.b.fieldOf("feature").forGetter(var0x -> var0x.b), Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter(var0x -> var0x.c)
            )
            .apply(var0, WeightedPlacedFeature::new)
   );
   public final Holder<PlacedFeature> b;
   public final float c;

   public WeightedPlacedFeature(Holder<PlacedFeature> var0, float var1) {
      this.b = var0;
      this.c = var1;
   }

   public boolean a(GeneratorAccessSeed var0, ChunkGenerator var1, RandomSource var2, BlockPosition var3) {
      return this.b.a().a(var0, var1, var2, var3);
   }
}
