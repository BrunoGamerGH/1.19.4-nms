package net.minecraft.util.profiling.metrics.storage;

import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.SystemUtils;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.CSVWriter;
import net.minecraft.util.profiling.MethodProfilerResults;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class MetricsPersister {
   public static final Path a = Paths.get("debug/profiling");
   public static final String b = "metrics";
   public static final String c = "deviations";
   public static final String d = "profiling.txt";
   private static final Logger e = LogUtils.getLogger();
   private final String f;

   public MetricsPersister(String var0) {
      this.f = var0;
   }

   public Path a(Set<MetricSampler> var0, Map<MetricSampler, List<RecordedDeviation>> var1, MethodProfilerResults var2) {
      try {
         Files.createDirectories(a);
      } catch (IOException var8) {
         throw new UncheckedIOException(var8);
      }

      try {
         Path var3 = Files.createTempDirectory("minecraft-profiling");
         var3.toFile().deleteOnExit();
         Files.createDirectories(a);
         Path var4 = var3.resolve(this.f);
         Path var5 = var4.resolve("metrics");
         this.a(var0, var5);
         if (!var1.isEmpty()) {
            this.a(var1, var4.resolve("deviations"));
         }

         this.a(var2, var4);
         return var3;
      } catch (IOException var7) {
         throw new UncheckedIOException(var7);
      }
   }

   private void a(Set<MetricSampler> var0, Path var1) {
      if (var0.isEmpty()) {
         throw new IllegalArgumentException("Expected at least one sampler to persist");
      } else {
         Map<MetricCategory, List<MetricSampler>> var2 = var0.stream().collect(Collectors.groupingBy(MetricSampler::e));
         var2.forEach((var1x, var2x) -> this.a(var1x, var2x, var1));
      }
   }

   private void a(MetricCategory var0, List<MetricSampler> var1, Path var2) {
      Path var3 = var2.resolve(SystemUtils.a(var0.a(), MinecraftKey::b) + ".csv");
      Writer var4 = null;

      try {
         Files.createDirectories(var3.getParent());
         var4 = Files.newBufferedWriter(var3, StandardCharsets.UTF_8);
         CSVWriter.a var5 = CSVWriter.a();
         var5.a("@tick");

         for(MetricSampler var7 : var1) {
            var5.a(var7.d());
         }

         CSVWriter var6 = var5.a(var4);
         List<MetricSampler.b> var7 = var1.stream().map(MetricSampler::f).collect(Collectors.toList());
         int var8 = var7.stream().mapToInt(MetricSampler.b::a).summaryStatistics().getMin();
         int var9 = var7.stream().mapToInt(MetricSampler.b::b).summaryStatistics().getMax();

         for(int var10 = var8; var10 <= var9; ++var10) {
            int var11 = var10;
            Stream<String> var12 = var7.stream().map(var1x -> String.valueOf(var1x.a(var11)));
            Object[] var13 = Stream.concat(Stream.of(String.valueOf(var10)), var12).toArray(var0x -> new String[var0x]);
            var6.a(var13);
         }

         e.info("Flushed metrics to {}", var3);
      } catch (Exception var18) {
         e.error("Could not save profiler results to {}", var3, var18);
      } finally {
         IOUtils.closeQuietly(var4);
      }
   }

   private void a(Map<MetricSampler, List<RecordedDeviation>> var0, Path var1) {
      DateTimeFormatter var2 = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss.SSS", Locale.UK).withZone(ZoneId.systemDefault());
      var0.forEach((var2x, var3x) -> var3x.forEach(var3xx -> {
            String var4 = var2.format(var3xx.a);
            Path var5 = var1.resolve(SystemUtils.a(var2x.d(), MinecraftKey::b)).resolve(String.format(Locale.ROOT, "%d@%s.txt", var3xx.b, var4));
            var3xx.c.a(var5);
         }));
   }

   private void a(MethodProfilerResults var0, Path var1) {
      var0.a(var1.resolve("profiling.txt"));
   }
}
