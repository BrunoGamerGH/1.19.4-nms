package net.minecraft.util.profiling.jfr.stats;

import com.google.common.base.MoreObjects;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordedThread;

public record ThreadAllocationStat(Instant timestamp, String threadName, long totalBytes) {
   private final Instant a;
   private final String b;
   private final long c;
   private static final String d = "unknown";

   public ThreadAllocationStat(Instant var0, String var1, long var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public static ThreadAllocationStat a(RecordedEvent var0) {
      RecordedThread var1 = var0.getThread("thread");
      String var2 = var1 == null ? "unknown" : (String)MoreObjects.firstNonNull(var1.getJavaName(), "unknown");
      return new ThreadAllocationStat(var0.getStartTime(), var2, var0.getLong("allocated"));
   }

   public static ThreadAllocationStat.a a(List<ThreadAllocationStat> var0) {
      Map<String, Double> var1 = new TreeMap<>();
      Map<String, List<ThreadAllocationStat>> var2 = var0.stream().collect(Collectors.groupingBy(var0x -> var0x.b));
      var2.forEach((var1x, var2x) -> {
         if (var2x.size() >= 2) {
            ThreadAllocationStat var3 = var2x.get(0);
            ThreadAllocationStat var4 = var2x.get(var2x.size() - 1);
            long var5 = Duration.between(var3.a, var4.a).getSeconds();
            long var7 = var4.c - var3.c;
            var1.put(var1x, (double)var7 / (double)var5);
         }
      });
      return new ThreadAllocationStat.a(var1);
   }

   public static record a(Map<String, Double> allocationsPerSecondByThread) {
      private final Map<String, Double> a;

      public a(Map<String, Double> var0) {
         this.a = var0;
      }
   }
}
