package net.minecraft.util.profiling.metrics;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class MetricsRegistry {
   public static final MetricsRegistry a = new MetricsRegistry();
   private final WeakHashMap<ProfilerMeasured, Void> b = new WeakHashMap<>();

   private MetricsRegistry() {
   }

   public void a(ProfilerMeasured var0) {
      this.b.put(var0, null);
   }

   public List<MetricSampler> a() {
      Map<String, List<MetricSampler>> var0 = this.b.keySet().stream().flatMap(var0x -> var0x.bm().stream()).collect(Collectors.groupingBy(MetricSampler::d));
      return a(var0);
   }

   private static List<MetricSampler> a(Map<String, List<MetricSampler>> var0) {
      return var0.entrySet().stream().map(var0x -> {
         String var1 = var0x.getKey();
         List<MetricSampler> var2 = var0x.getValue();
         return (MetricSampler)(var2.size() > 1 ? new MetricsRegistry.a(var1, var2) : var2.get(0));
      }).collect(Collectors.toList());
   }

   static class a extends MetricSampler {
      private final List<MetricSampler> b;

      a(String var0, List<MetricSampler> var1) {
         super(var0, var1.get(0).e(), () -> c(var1), () -> b(var1), a(var1));
         this.b = var1;
      }

      private static MetricSampler.c a(List<MetricSampler> var0) {
         return var1 -> var0.stream().anyMatch(var2 -> var2.a != null ? var2.a.test(var1) : false);
      }

      private static void b(List<MetricSampler> var0) {
         for(MetricSampler var2 : var0) {
            var2.a();
         }
      }

      private static double c(List<MetricSampler> var0) {
         double var1 = 0.0;

         for(MetricSampler var4 : var0) {
            var1 += var4.c().getAsDouble();
         }

         return var1 / (double)var0.size();
      }

      @Override
      public boolean equals(@Nullable Object var0) {
         if (this == var0) {
            return true;
         } else if (var0 == null || this.getClass() != var0.getClass()) {
            return false;
         } else if (!super.equals(var0)) {
            return false;
         } else {
            MetricsRegistry.a var1 = (MetricsRegistry.a)var0;
            return this.b.equals(var1.b);
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(super.hashCode(), this.b);
      }
   }
}
