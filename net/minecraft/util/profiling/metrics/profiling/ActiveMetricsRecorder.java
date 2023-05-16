package net.minecraft.util.profiling.metrics.profiling;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import javax.annotation.Nullable;
import net.minecraft.util.profiling.GameProfilerDisabled;
import net.minecraft.util.profiling.GameProfilerFiller;
import net.minecraft.util.profiling.GameProfilerFillerActive;
import net.minecraft.util.profiling.GameProfilerSwitcher;
import net.minecraft.util.profiling.MethodProfiler;
import net.minecraft.util.profiling.MethodProfilerResults;
import net.minecraft.util.profiling.MethodProfilerResultsEmpty;
import net.minecraft.util.profiling.metrics.MetricSampler;
import net.minecraft.util.profiling.metrics.MetricsSamplerProvider;
import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
import net.minecraft.util.profiling.metrics.storage.RecordedDeviation;

public class ActiveMetricsRecorder implements MetricsRecorder {
   public static final int a = 10;
   @Nullable
   private static Consumer<Path> b = null;
   private final Map<MetricSampler, List<RecordedDeviation>> c = new Object2ObjectOpenHashMap();
   private final GameProfilerSwitcher d;
   private final Executor e;
   private final MetricsPersister f;
   private final Consumer<MethodProfilerResults> g;
   private final Consumer<Path> h;
   private final MetricsSamplerProvider i;
   private final LongSupplier j;
   private final long k;
   private int l;
   private GameProfilerFillerActive m;
   private volatile boolean n;
   private Set<MetricSampler> o = ImmutableSet.of();

   private ActiveMetricsRecorder(
      MetricsSamplerProvider var0, LongSupplier var1, Executor var2, MetricsPersister var3, Consumer<MethodProfilerResults> var4, Consumer<Path> var5
   ) {
      this.i = var0;
      this.j = var1;
      this.d = new GameProfilerSwitcher(var1, () -> this.l);
      this.e = var2;
      this.f = var3;
      this.g = var4;
      this.h = b == null ? var5 : var5.andThen(b);
      this.k = var1.getAsLong() + TimeUnit.NANOSECONDS.convert(10L, TimeUnit.SECONDS);
      this.m = new MethodProfiler(this.j, () -> this.l, false);
      this.d.c();
   }

   public static ActiveMetricsRecorder a(
      MetricsSamplerProvider var0, LongSupplier var1, Executor var2, MetricsPersister var3, Consumer<MethodProfilerResults> var4, Consumer<Path> var5
   ) {
      return new ActiveMetricsRecorder(var0, var1, var2, var3, var4, var5);
   }

   @Override
   public synchronized void a() {
      if (this.e()) {
         this.n = true;
      }
   }

   @Override
   public synchronized void b() {
      if (this.e()) {
         this.m = GameProfilerDisabled.a;
         this.g.accept(MethodProfilerResultsEmpty.a);
         this.a(this.o);
      }
   }

   @Override
   public void c() {
      this.g();
      this.o = this.i.a(() -> this.m);

      for(MetricSampler var1 : this.o) {
         var1.a();
      }

      ++this.l;
   }

   @Override
   public void d() {
      this.g();
      if (this.l != 0) {
         for(MetricSampler var1 : this.o) {
            var1.a(this.l);
            if (var1.g()) {
               RecordedDeviation var2 = new RecordedDeviation(Instant.now(), this.l, this.m.d());
               this.c.computeIfAbsent(var1, var0 -> Lists.newArrayList()).add(var2);
            }
         }

         if (!this.n && this.j.getAsLong() <= this.k) {
            this.m = new MethodProfiler(this.j, () -> this.l, false);
         } else {
            this.n = false;
            MethodProfilerResults var0 = this.d.e();
            this.m = GameProfilerDisabled.a;
            this.g.accept(var0);
            this.a(var0);
         }
      }
   }

   @Override
   public boolean e() {
      return this.d.a();
   }

   @Override
   public GameProfilerFiller f() {
      return GameProfilerFiller.a(this.d.d(), this.m);
   }

   private void g() {
      if (!this.e()) {
         throw new IllegalStateException("Not started!");
      }
   }

   private void a(MethodProfilerResults var0) {
      HashSet<MetricSampler> var1 = new HashSet<>(this.o);
      this.e.execute(() -> {
         Path var2x = this.f.a(var1, this.c, var0);
         this.a(var1);
         this.h.accept(var2x);
      });
   }

   private void a(Collection<MetricSampler> var0) {
      for(MetricSampler var2 : var0) {
         var2.b();
      }

      this.c.clear();
      this.d.b();
   }

   public static void a(Consumer<Path> var0) {
      b = var0;
   }
}
