package net.minecraft.world.level.levelgen;

import java.util.function.LongFunction;
import net.minecraft.util.RandomSource;

public class SeededRandom extends LegacyRandomSource {
   private final RandomSource d;
   private int e;

   public SeededRandom(RandomSource var0) {
      super(0L);
      this.d = var0;
   }

   public int l() {
      return this.e;
   }

   @Override
   public RandomSource d() {
      return this.d.d();
   }

   @Override
   public PositionalRandomFactory e() {
      return this.d.e();
   }

   @Override
   public int c(int var0) {
      ++this.e;
      RandomSource var3 = this.d;
      return var3 instanceof LegacyRandomSource var1 ? var1.c(var0) : (int)(this.d.g() >>> 64 - var0);
   }

   @Override
   public synchronized void b(long var0) {
      if (this.d != null) {
         this.d.b(var0);
      }
   }

   public long a(long var0, int var2, int var3) {
      this.b(var0);
      long var4 = this.g() | 1L;
      long var6 = this.g() | 1L;
      long var8 = (long)var2 * var4 + (long)var3 * var6 ^ var0;
      this.b(var8);
      return var8;
   }

   public void b(long var0, int var2, int var3) {
      long var4 = var0 + (long)var2 + (long)(10000 * var3);
      this.b(var4);
   }

   public void c(long var0, int var2, int var3) {
      this.b(var0);
      long var4 = this.g();
      long var6 = this.g();
      long var8 = (long)var2 * var4 ^ (long)var3 * var6 ^ var0;
      this.b(var8);
   }

   public void a(long var0, int var2, int var3, int var4) {
      long var5 = (long)var2 * 341873128712L + (long)var3 * 132897987541L + var0 + (long)var4;
      this.b(var5);
   }

   public static RandomSource a(int var0, int var1, long var2, long var4) {
      return RandomSource.a(var2 + (long)(var0 * var0 * 4987142) + (long)(var0 * 5947611) + (long)(var1 * var1) * 4392871L + (long)(var1 * 389711) ^ var4);
   }

   public static enum a {
      a(LegacyRandomSource::new),
      b(XoroshiroRandomSource::new);

      private final LongFunction<RandomSource> c;

      private a(LongFunction var2) {
         this.c = var2;
      }

      public RandomSource a(long var0) {
         return this.c.apply(var0);
      }
   }
}
