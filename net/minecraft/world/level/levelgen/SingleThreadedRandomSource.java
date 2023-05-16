package net.minecraft.world.level.levelgen;

import net.minecraft.util.RandomSource;

public class SingleThreadedRandomSource implements BitRandomSource {
   private static final int d = 48;
   private static final long e = 281474976710655L;
   private static final long f = 25214903917L;
   private static final long g = 11L;
   private long h;
   private final MarsagliaPolarGaussian i = new MarsagliaPolarGaussian(this);

   public SingleThreadedRandomSource(long var0) {
      this.b(var0);
   }

   @Override
   public RandomSource d() {
      return new SingleThreadedRandomSource(this.g());
   }

   @Override
   public PositionalRandomFactory e() {
      return new LegacyRandomSource.a(this.g());
   }

   @Override
   public void b(long var0) {
      this.h = (var0 ^ 25214903917L) & 281474976710655L;
      this.i.a();
   }

   @Override
   public int c(int var0) {
      long var1 = this.h * 25214903917L + 11L & 281474976710655L;
      this.h = var1;
      return (int)(var1 >> 48 - var0);
   }

   @Override
   public double k() {
      return this.i.b();
   }
}
