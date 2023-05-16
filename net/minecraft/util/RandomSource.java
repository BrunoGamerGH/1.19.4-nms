package net.minecraft.util;

import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
import net.minecraft.world.level.levelgen.ThreadSafeLegacyRandomSource;

public interface RandomSource {
   @Deprecated
   double a = 2.297;

   static RandomSource a() {
      return a(RandomSupport.a());
   }

   @Deprecated
   static RandomSource b() {
      return new ThreadSafeLegacyRandomSource(RandomSupport.a());
   }

   static RandomSource a(long var0) {
      return new LegacyRandomSource(var0);
   }

   static RandomSource c() {
      return new SingleThreadedRandomSource(ThreadLocalRandom.current().nextLong());
   }

   RandomSource d();

   PositionalRandomFactory e();

   void b(long var1);

   int f();

   int a(int var1);

   default int a(int var0, int var1) {
      return this.a(var1 - var0 + 1) + var0;
   }

   long g();

   boolean h();

   float i();

   double j();

   double k();

   default double a(double var0, double var2) {
      return var0 + var2 * (this.j() - this.j());
   }

   default void b(int var0) {
      for(int var1 = 0; var1 < var0; ++var1) {
         this.f();
      }
   }

   default int b(int var0, int var1) {
      if (var0 >= var1) {
         throw new IllegalArgumentException("bound - origin is non positive");
      } else {
         return var0 + this.a(var1 - var0);
      }
   }
}
