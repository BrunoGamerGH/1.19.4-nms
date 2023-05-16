package net.minecraft.util.profiling.metrics;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;

public class MetricSampler {
   private final String b;
   private final MetricCategory c;
   private final DoubleSupplier d;
   private final ByteBuf e;
   private final ByteBuf f;
   private volatile boolean g;
   @Nullable
   private final Runnable h;
   @Nullable
   final MetricSampler.c a;
   private double i;

   protected MetricSampler(String var0, MetricCategory var1, DoubleSupplier var2, @Nullable Runnable var3, @Nullable MetricSampler.c var4) {
      this.b = var0;
      this.c = var1;
      this.h = var3;
      this.d = var2;
      this.a = var4;
      this.f = ByteBufAllocator.DEFAULT.buffer();
      this.e = ByteBufAllocator.DEFAULT.buffer();
      this.g = true;
   }

   public static MetricSampler a(String var0, MetricCategory var1, DoubleSupplier var2) {
      return new MetricSampler(var0, var1, var2, null, null);
   }

   public static <T> MetricSampler a(String var0, MetricCategory var1, T var2, ToDoubleFunction<T> var3) {
      return a(var0, var1, var3, var2).a();
   }

   public static <T> MetricSampler.a<T> a(String var0, MetricCategory var1, ToDoubleFunction<T> var2, T var3) {
      return new MetricSampler.a<>(var0, var1, var2, var3);
   }

   public void a() {
      if (!this.g) {
         throw new IllegalStateException("Not running");
      } else {
         if (this.h != null) {
            this.h.run();
         }
      }
   }

   public void a(int var0) {
      this.h();
      this.i = this.d.getAsDouble();
      this.f.writeDouble(this.i);
      this.e.writeInt(var0);
   }

   public void b() {
      this.h();
      this.f.release();
      this.e.release();
      this.g = false;
   }

   private void h() {
      if (!this.g) {
         throw new IllegalStateException(String.format(Locale.ROOT, "Sampler for metric %s not started!", this.b));
      }
   }

   DoubleSupplier c() {
      return this.d;
   }

   public String d() {
      return this.b;
   }

   public MetricCategory e() {
      return this.c;
   }

   public MetricSampler.b f() {
      Int2DoubleMap var0 = new Int2DoubleOpenHashMap();
      int var1 = Integer.MIN_VALUE;

      int var2;
      int var3;
      for(var2 = Integer.MIN_VALUE; this.f.isReadable(8); var2 = var3) {
         var3 = this.e.readInt();
         if (var1 == Integer.MIN_VALUE) {
            var1 = var3;
         }

         var0.put(var3, this.f.readDouble());
      }

      return new MetricSampler.b(var1, var2, var0);
   }

   public boolean g() {
      return this.a != null && this.a.test(this.i);
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else if (var0 != null && this.getClass() == var0.getClass()) {
         MetricSampler var1 = (MetricSampler)var0;
         return this.b.equals(var1.b) && this.c.equals(var1.c);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.b.hashCode();
   }

   public static class a<T> {
      private final String a;
      private final MetricCategory b;
      private final DoubleSupplier c;
      private final T d;
      @Nullable
      private Runnable e;
      @Nullable
      private MetricSampler.c f;

      public a(String var0, MetricCategory var1, ToDoubleFunction<T> var2, T var3) {
         this.a = var0;
         this.b = var1;
         this.c = () -> var2.applyAsDouble(var3);
         this.d = var3;
      }

      public MetricSampler.a<T> a(Consumer<T> var0) {
         this.e = () -> var0.accept(this.d);
         return this;
      }

      public MetricSampler.a<T> a(MetricSampler.c var0) {
         this.f = var0;
         return this;
      }

      public MetricSampler a() {
         return new MetricSampler(this.a, this.b, this.c, this.e, this.f);
      }
   }

   public static class b {
      private final Int2DoubleMap a;
      private final int b;
      private final int c;

      public b(int var0, int var1, Int2DoubleMap var2) {
         this.b = var0;
         this.c = var1;
         this.a = var2;
      }

      public double a(int var0) {
         return this.a.get(var0);
      }

      public int a() {
         return this.b;
      }

      public int b() {
         return this.c;
      }
   }

   public interface c {
      boolean test(double var1);
   }

   public static class d implements MetricSampler.c {
      private final float a;
      private double b = Double.MIN_VALUE;

      public d(float var0) {
         this.a = var0;
      }

      @Override
      public boolean test(double var0) {
         boolean var2;
         if (this.b != Double.MIN_VALUE && !(var0 <= this.b)) {
            var2 = (var0 - this.b) / this.b >= (double)this.a;
         } else {
            var2 = false;
         }

         this.b = var0;
         return var2;
      }
   }
}
