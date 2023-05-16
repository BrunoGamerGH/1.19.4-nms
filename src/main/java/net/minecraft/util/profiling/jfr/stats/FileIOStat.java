package net.minecraft.util.profiling.jfr.stats;

import com.mojang.datafixers.util.Pair;
import java.time.Duration;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public record FileIOStat(Duration duration, @Nullable String path, long bytes) {
   private final Duration a;
   @Nullable
   private final String b;
   private final long c;

   public FileIOStat(Duration var0, @Nullable String var1, long var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }

   public static FileIOStat.a a(Duration var0, List<FileIOStat> var1) {
      long var2 = var1.stream().mapToLong(var0x -> var0x.c).sum();
      return new FileIOStat.a(
         var2,
         (double)var2 / (double)var0.getSeconds(),
         (long)var1.size(),
         (double)var1.size() / (double)var0.getSeconds(),
         var1.stream().map(FileIOStat::a).reduce(Duration.ZERO, Duration::plus),
         var1.stream()
            .filter(var0x -> var0x.b != null)
            .collect(Collectors.groupingBy(var0x -> var0x.b, Collectors.summingLong(var0x -> var0x.c)))
            .entrySet()
            .stream()
            .sorted(Entry.<String, Long>comparingByValue().reversed())
            .map(var0x -> Pair.of((String)var0x.getKey(), (Long)var0x.getValue()))
            .limit(10L)
            .toList()
      );
   }

   public static record a(
      long totalBytes,
      double bytesPerSecond,
      long counts,
      double countsPerSecond,
      Duration timeSpentInIO,
      List<Pair<String, Long>> topTenContributorsByTotalBytes
   ) {
      private final long a;
      private final double b;
      private final long c;
      private final double d;
      private final Duration e;
      private final List<Pair<String, Long>> f;

      public a(long var0, double var2, long var4, double var6, Duration var8, List<Pair<String, Long>> var9) {
         this.a = var0;
         this.b = var2;
         this.c = var4;
         this.d = var6;
         this.e = var8;
         this.f = var9;
      }
   }
}
