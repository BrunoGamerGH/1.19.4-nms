package net.minecraft.world.level.levelgen;

import com.google.common.annotations.VisibleForTesting;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.ThreadingDetector;

public class LegacyRandomSource implements BitRandomSource {
   private static final int d = 48;
   private static final long e = 281474976710655L;
   private static final long f = 25214903917L;
   private static final long g = 11L;
   private final AtomicLong h = new AtomicLong();
   private final MarsagliaPolarGaussian i = new MarsagliaPolarGaussian(this);

   public LegacyRandomSource(long var0) {
      this.b(var0);
   }

   @Override
   public RandomSource d() {
      return new LegacyRandomSource(this.g());
   }

   @Override
   public PositionalRandomFactory e() {
      return new LegacyRandomSource.a(this.g());
   }

   @Override
   public void b(long var0) {
      if (!this.h.compareAndSet(this.h.get(), (var0 ^ 25214903917L) & 281474976710655L)) {
         throw ThreadingDetector.a("LegacyRandomSource", null);
      } else {
         this.i.a();
      }
   }

   @Override
   public int c(int var0) {
      long var1 = this.h.get();
      long var3 = var1 * 25214903917L + 11L & 281474976710655L;
      if (!this.h.compareAndSet(var1, var3)) {
         throw ThreadingDetector.a("LegacyRandomSource", null);
      } else {
         return (int)(var3 >> 48 - var0);
      }
   }

   @Override
   public double k() {
      return this.i.b();
   }

   public static class a implements PositionalRandomFactory {
      private final long a;

      public a(long var0) {
         this.a = var0;
      }

      @Override
      public RandomSource a(int var0, int var1, int var2) {
         long var3 = MathHelper.b(var0, var1, var2);
         long var5 = var3 ^ this.a;
         return new LegacyRandomSource(var5);
      }

      @Override
      public RandomSource a(String var0) {
         int var1 = var0.hashCode();
         return new LegacyRandomSource((long)var1 ^ this.a);
      }

      @VisibleForTesting
      @Override
      public void a(StringBuilder var0) {
         var0.append("LegacyPositionalRandomFactory{").append(this.a).append("}");
      }
   }
}
