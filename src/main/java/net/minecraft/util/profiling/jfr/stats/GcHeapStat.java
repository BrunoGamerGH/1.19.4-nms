package net.minecraft.util.profiling.jfr.stats;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jdk.jfr.consumer.RecordedEvent;

public record GcHeapStat(Instant timestamp, long heapUsed, GcHeapStat.b timing) {
   private final Instant a;
   private final long b;
   private final GcHeapStat.b c;

   public GcHeapStat(Instant var0, long var1, GcHeapStat.b var3) {
      this.a = var0;
      this.b = var1;
      this.c = var3;
   }

   public static GcHeapStat a(RecordedEvent var0) {
      return new GcHeapStat(
         var0.getStartTime(), var0.getLong("heapUsed"), var0.getString("when").equalsIgnoreCase("before gc") ? GcHeapStat.b.a : GcHeapStat.b.b
      );
   }

   public static GcHeapStat.a a(Duration var0, List<GcHeapStat> var1, Duration var2, int var3) {
      return new GcHeapStat.a(var0, var2, var3, a(var1));
   }

   private static double a(List<GcHeapStat> var0) {
      long var1 = 0L;
      Map<GcHeapStat.b, List<GcHeapStat>> var3 = var0.stream().collect(Collectors.groupingBy(var0x -> var0x.c));
      List<GcHeapStat> var4 = var3.get(GcHeapStat.b.a);
      List<GcHeapStat> var5 = var3.get(GcHeapStat.b.b);

      for(int var6 = 1; var6 < var4.size(); ++var6) {
         GcHeapStat var7 = var4.get(var6);
         GcHeapStat var8 = var5.get(var6 - 1);
         var1 += var7.b - var8.b;
      }

      Duration var6 = Duration.between(var0.get(1).a, var0.get(var0.size() - 1).a);
      return (double)var1 / (double)var6.getSeconds();
   }

   public static record a(Duration duration, Duration gcTotalDuration, int totalGCs, double allocationRateBytesPerSecond) {
      private final Duration a;
      private final Duration b;
      private final int c;
      private final double d;

      public a(Duration var0, Duration var1, int var2, double var3) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
      }

      public float a() {
         return (float)this.b.toMillis() / (float)this.a.toMillis();
      }

      public Duration b() {
         return this.a;
      }

      public Duration c() {
         return this.b;
      }

      public int d() {
         return this.c;
      }

      public double e() {
         return this.d;
      }
   }

   static enum b {
      a,
      b;
   }
}
