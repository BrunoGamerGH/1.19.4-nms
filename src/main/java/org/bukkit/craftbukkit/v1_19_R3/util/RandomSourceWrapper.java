package org.bukkit.craftbukkit.v1_19_R3.util;

import java.util.Random;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;

public final class RandomSourceWrapper implements RandomSource {
   private final Random random;

   public RandomSourceWrapper(Random random) {
      this.random = random;
   }

   @Override
   public RandomSource d() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public PositionalRandomFactory e() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public synchronized void b(long seed) {
      this.random.setSeed(seed);
   }

   @Override
   public int f() {
      return this.random.nextInt();
   }

   @Override
   public int a(int bound) {
      return this.random.nextInt(bound);
   }

   @Override
   public long g() {
      return this.random.nextLong();
   }

   @Override
   public boolean h() {
      return this.random.nextBoolean();
   }

   @Override
   public float i() {
      return this.random.nextFloat();
   }

   @Override
   public double j() {
      return this.random.nextDouble();
   }

   @Override
   public synchronized double k() {
      return this.random.nextGaussian();
   }

   public static final class RandomWrapper extends Random {
      private final RandomSource random;

      public RandomWrapper(RandomSource random) {
         this.random = random;
      }

      @Override
      public void setSeed(long l) {
         if (this.random != null) {
            this.random.b(l);
         }
      }

      @Override
      public int nextInt() {
         return this.random.f();
      }

      @Override
      public int nextInt(int i) {
         return this.random.a(i);
      }

      @Override
      public long nextLong() {
         return this.random.g();
      }

      @Override
      public boolean nextBoolean() {
         return this.random.h();
      }

      @Override
      public float nextFloat() {
         return this.random.i();
      }

      @Override
      public double nextDouble() {
         return this.random.j();
      }

      @Override
      public double nextGaussian() {
         return this.random.k();
      }

      @Override
      public int nextInt(int var0, int var1) {
         return this.random.b(var0, var1);
      }
   }
}
