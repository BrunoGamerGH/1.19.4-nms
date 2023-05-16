package net.minecraft.world.level.levelgen;

import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.util.RandomSource;

@Deprecated
public class ThreadSafeLegacyRandomSource implements BitRandomSource {
   private static final int d = 48;
   private static final long e = 281474976710655L;
   private static final long f = 25214903917L;
   private static final long g = 11L;
   private final AtomicLong h = new AtomicLong();
   private final MarsagliaPolarGaussian i = new MarsagliaPolarGaussian(this);

   public ThreadSafeLegacyRandomSource(long var0) {
      this.b(var0);
   }

   @Override
   public RandomSource d() {
      return new ThreadSafeLegacyRandomSource(this.g());
   }

   @Override
   public PositionalRandomFactory e() {
      return new LegacyRandomSource.a(this.g());
   }

   @Override
   public void b(long var0) {
      this.h.set((var0 ^ 25214903917L) & 281474976710655L);
   }

   @Override
   public int c(int var0) {
      long var1;
      long var3;
      do {
         var1 = this.h.get();
         var3 = var1 * 25214903917L + 11L & 281474976710655L;
      } while(!this.h.compareAndSet(var1, var3));

      return (int)(var3 >>> 48 - var0);
   }

   @Override
   public double k() {
      return this.i.b();
   }
}
