package net.minecraft.world.level.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleParam;
import net.minecraft.core.particles.Particles;
import net.minecraft.util.RandomSource;

public class BiomeParticles {
   public static final Codec<BiomeParticles> a = RecordCodecBuilder.create(
      var0 -> var0.group(Particles.aS.fieldOf("options").forGetter(var0x -> var0x.b), Codec.FLOAT.fieldOf("probability").forGetter(var0x -> var0x.c))
            .apply(var0, BiomeParticles::new)
   );
   private final ParticleParam b;
   private final float c;

   public BiomeParticles(ParticleParam var0, float var1) {
      this.b = var0;
      this.c = var1;
   }

   public ParticleParam a() {
      return this.b;
   }

   public boolean a(RandomSource var0) {
      return var0.i() <= this.c;
   }
}
