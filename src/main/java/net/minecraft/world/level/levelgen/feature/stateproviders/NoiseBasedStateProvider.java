package net.minecraft.world.level.levelgen.feature.stateproviders;

import com.mojang.datafixers.Products.P3;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;

public abstract class NoiseBasedStateProvider extends WorldGenFeatureStateProvider {
   protected final long c;
   protected final NoiseGeneratorNormal.a d;
   protected final float e;
   protected final NoiseGeneratorNormal f;

   protected static <P extends NoiseBasedStateProvider> P3<Mu<P>, Long, NoiseGeneratorNormal.a, Float> a(Instance<P> var0) {
      return var0.group(
         Codec.LONG.fieldOf("seed").forGetter(var0x -> var0x.c),
         NoiseGeneratorNormal.a.a.fieldOf("noise").forGetter(var0x -> var0x.d),
         ExtraCodecs.j.fieldOf("scale").forGetter(var0x -> var0x.e)
      );
   }

   protected NoiseBasedStateProvider(long var0, NoiseGeneratorNormal.a var2, float var3) {
      this.c = var0;
      this.d = var2;
      this.e = var3;
      this.f = NoiseGeneratorNormal.b(new SeededRandom(new LegacyRandomSource(var0)), var2);
   }

   protected double a(BlockPosition var0, double var1) {
      return this.f.a((double)var0.u() * var1, (double)var0.v() * var1, (double)var0.w() * var1);
   }
}
