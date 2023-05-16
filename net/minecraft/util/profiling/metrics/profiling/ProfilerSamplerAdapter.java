package net.minecraft.util.profiling.metrics.profiling;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import net.minecraft.util.TimeRange;
import net.minecraft.util.profiling.GameProfilerFillerActive;
import net.minecraft.util.profiling.MethodProfiler;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;

public class ProfilerSamplerAdapter {
   private final Set<String> a = new ObjectOpenHashSet();

   public Set<MetricSampler> a(Supplier<GameProfilerFillerActive> var0) {
      Set<MetricSampler> var1 = var0.get()
         .e()
         .stream()
         .filter(var0x -> !this.a.contains(var0x.getLeft()))
         .map(var1x -> a(var0, (String)var1x.getLeft(), (MetricCategory)var1x.getRight()))
         .collect(Collectors.toSet());

      for(MetricSampler var3 : var1) {
         this.a.add(var3.d());
      }

      return var1;
   }

   private static MetricSampler a(Supplier<GameProfilerFillerActive> var0, String var1, MetricCategory var2) {
      return MetricSampler.a(var1, var2, () -> {
         MethodProfiler.a var2x = var0.get().c(var1);
         return var2x == null ? 0.0 : (double)var2x.b() / (double)TimeRange.b;
      });
   }
}
