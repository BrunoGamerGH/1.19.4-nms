package net.minecraft.core.particles;

import com.mojang.serialization.Codec;

public abstract class Particle<T extends ParticleParam> {
   private final boolean a;
   private final ParticleParam.a<T> b;

   protected Particle(boolean var0, ParticleParam.a<T> var1) {
      this.a = var0;
      this.b = var1;
   }

   public boolean c() {
      return this.a;
   }

   public ParticleParam.a<T> d() {
      return this.b;
   }

   public abstract Codec<T> e();
}
