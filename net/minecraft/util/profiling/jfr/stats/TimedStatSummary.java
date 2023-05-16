package net.minecraft.util.profiling.jfr.stats;

import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.util.profiling.jfr.Percentiles;

public record TimedStatSummary<T extends TimedStat>(
   T fastest, T slowest, @Nullable T secondSlowest, int count, Map<Integer, Double> percentilesNanos, Duration totalDuration
) {
   private final T a;
   private final T b;
   @Nullable
   private final T c;
   private final int d;
   private final Map<Integer, Double> e;
   private final Duration f;

   public TimedStatSummary(T var0, T var1, @Nullable T var2, int var3, Map<Integer, Double> var4, Duration var5) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
   }

   public static <T extends TimedStat> TimedStatSummary<T> a(List<T> var0) {
      if (var0.isEmpty()) {
         throw new IllegalArgumentException("No values");
      } else {
         List<T> var1 = var0.stream().sorted(Comparator.comparing(TimedStat::a)).toList();
         Duration var2 = var1.stream().map(TimedStat::a).reduce(Duration::plus).orElse(Duration.ZERO);
         T var3 = var1.get(0);
         T var4 = var1.get(var1.size() - 1);
         T var5 = var1.size() > 1 ? var1.get(var1.size() - 2) : null;
         int var6 = var1.size();
         Map<Integer, Double> var7 = Percentiles.a(var1.stream().mapToLong(var0x -> var0x.a().toNanos()).toArray());
         return new TimedStatSummary<>(var3, var4, var5, var6, var7, var2);
      }
   }
}
