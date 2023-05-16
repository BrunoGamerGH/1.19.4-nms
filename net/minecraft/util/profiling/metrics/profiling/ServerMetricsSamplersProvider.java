package net.minecraft.util.profiling.metrics.profiling;

import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.IntStream;
import net.minecraft.util.profiling.GameProfilerFillerActive;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;
import net.minecraft.util.profiling.metrics.MetricsRegistry;
import net.minecraft.util.profiling.metrics.MetricsSamplerProvider;
import org.slf4j.Logger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

public class ServerMetricsSamplersProvider implements MetricsSamplerProvider {
   private static final Logger a = LogUtils.getLogger();
   private final Set<MetricSampler> b = new ObjectOpenHashSet();
   private final ProfilerSamplerAdapter c = new ProfilerSamplerAdapter();

   public ServerMetricsSamplersProvider(LongSupplier var0, boolean var1) {
      this.b.add(a(var0));
      if (var1) {
         this.b.addAll(a());
      }
   }

   public static Set<MetricSampler> a() {
      Builder<MetricSampler> var0 = ImmutableSet.builder();

      try {
         ServerMetricsSamplersProvider.a var1 = new ServerMetricsSamplersProvider.a();
         IntStream.range(0, var1.a).mapToObj(var1x -> MetricSampler.a("cpu#" + var1x, MetricCategory.h, () -> var1.a(var1x))).forEach(var0::add);
      } catch (Throwable var2) {
         a.warn("Failed to query cpu, no cpu stats will be recorded", var2);
      }

      var0.add(
         MetricSampler.a(
            "heap MiB", MetricCategory.e, () -> (double)((float)(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576.0F)
         )
      );
      var0.addAll(MetricsRegistry.a.a());
      return var0.build();
   }

   @Override
   public Set<MetricSampler> a(Supplier<GameProfilerFillerActive> var0) {
      this.b.addAll(this.c.a(var0));
      return this.b;
   }

   public static MetricSampler a(final LongSupplier var0) {
      Stopwatch var1 = Stopwatch.createUnstarted(new Ticker() {
         public long read() {
            return var0.getAsLong();
         }
      });
      ToDoubleFunction<Stopwatch> var2 = var0x -> {
         if (var0x.isRunning()) {
            var0x.stop();
         }

         long var1x = var0x.elapsed(TimeUnit.NANOSECONDS);
         var0x.reset();
         return (double)var1x;
      };
      MetricSampler.d var3 = new MetricSampler.d(2.0F);
      return MetricSampler.a("ticktime", MetricCategory.d, var2, var1).a(Stopwatch::start).a(var3).a();
   }

   static class a {
      private final SystemInfo b = new SystemInfo();
      private final CentralProcessor c = this.b.getHardware().getProcessor();
      public final int a = this.c.getLogicalProcessorCount();
      private long[][] d = this.c.getProcessorCpuLoadTicks();
      private double[] e = this.c.getProcessorCpuLoadBetweenTicks(this.d);
      private long f;

      public double a(int var0) {
         long var1 = System.currentTimeMillis();
         if (this.f == 0L || this.f + 501L < var1) {
            this.e = this.c.getProcessorCpuLoadBetweenTicks(this.d);
            this.d = this.c.getProcessorCpuLoadTicks();
            this.f = var1;
         }

         return this.e[var0] * 100.0;
      }
   }
}
