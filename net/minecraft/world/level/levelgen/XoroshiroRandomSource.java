package net.minecraft.world.level.levelgen;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Longs;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;

public class XoroshiroRandomSource implements RandomSource {
   private static final float b = 5.9604645E-8F;
   private static final double c = 1.110223E-16F;
   private Xoroshiro128PlusPlus d;
   private final MarsagliaPolarGaussian e = new MarsagliaPolarGaussian(this);

   public XoroshiroRandomSource(long var0) {
      this.d = new Xoroshiro128PlusPlus(RandomSupport.b(var0));
   }

   public XoroshiroRandomSource(long var0, long var2) {
      this.d = new Xoroshiro128PlusPlus(var0, var2);
   }

   @Override
   public RandomSource d() {
      return new XoroshiroRandomSource(this.d.a(), this.d.a());
   }

   @Override
   public PositionalRandomFactory e() {
      return new XoroshiroRandomSource.a(this.d.a(), this.d.a());
   }

   @Override
   public void b(long var0) {
      this.d = new Xoroshiro128PlusPlus(RandomSupport.b(var0));
      this.e.a();
   }

   @Override
   public int f() {
      return (int)this.d.a();
   }

   @Override
   public int a(int var0) {
      if (var0 <= 0) {
         throw new IllegalArgumentException("Bound must be positive");
      } else {
         long var1 = Integer.toUnsignedLong(this.f());
         long var3 = var1 * (long)var0;
         long var5 = var3 & 4294967295L;
         if (var5 < (long)var0) {
            for(int var7 = Integer.remainderUnsigned(~var0 + 1, var0); var5 < (long)var7; var5 = var3 & 4294967295L) {
               var1 = Integer.toUnsignedLong(this.f());
               var3 = var1 * (long)var0;
            }
         }

         long var7 = var3 >> 32;
         return (int)var7;
      }
   }

   @Override
   public long g() {
      return this.d.a();
   }

   @Override
   public boolean h() {
      return (this.d.a() & 1L) != 0L;
   }

   @Override
   public float i() {
      return (float)this.c(24) * 5.9604645E-8F;
   }

   @Override
   public double j() {
      return (double)this.c(53) * 1.110223E-16F;
   }

   @Override
   public double k() {
      return this.e.b();
   }

   @Override
   public void b(int var0) {
      for(int var1 = 0; var1 < var0; ++var1) {
         this.d.a();
      }
   }

   private long c(int var0) {
      return this.d.a() >>> 64 - var0;
   }

   public static class a implements PositionalRandomFactory {
      private static final HashFunction a = Hashing.md5();
      private final long b;
      private final long c;

      public a(long var0, long var2) {
         this.b = var0;
         this.c = var2;
      }

      @Override
      public RandomSource a(int var0, int var1, int var2) {
         long var3 = MathHelper.b(var0, var1, var2);
         long var5 = var3 ^ this.b;
         return new XoroshiroRandomSource(var5, this.c);
      }

      @Override
      public RandomSource a(String var0) {
         byte[] var1 = a.hashString(var0, Charsets.UTF_8).asBytes();
         long var2 = Longs.fromBytes(var1[0], var1[1], var1[2], var1[3], var1[4], var1[5], var1[6], var1[7]);
         long var4 = Longs.fromBytes(var1[8], var1[9], var1[10], var1[11], var1[12], var1[13], var1[14], var1[15]);
         return new XoroshiroRandomSource(var2 ^ this.b, var4 ^ this.c);
      }

      @VisibleForTesting
      @Override
      public void a(StringBuilder var0) {
         var0.append("seedLo: ").append(this.b).append(", seedHi: ").append(this.c);
      }
   }
}
