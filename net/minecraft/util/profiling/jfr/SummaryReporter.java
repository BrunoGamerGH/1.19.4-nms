package net.minecraft.util.profiling.jfr;

import com.mojang.logging.LogUtils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.server.DispenserRegistry;
import net.minecraft.util.profiling.jfr.parse.JfrStatsParser;
import net.minecraft.util.profiling.jfr.parse.JfrStatsResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

public class SummaryReporter {
   private static final Logger a = LogUtils.getLogger();
   private final Runnable b;

   protected SummaryReporter(Runnable var0) {
      this.b = var0;
   }

   public void a(@Nullable Path var0) {
      if (var0 != null) {
         this.b.run();
         a(() -> "Dumped flight recorder profiling to " + var0);

         JfrStatsResult var1;
         try {
            var1 = JfrStatsParser.a(var0);
         } catch (Throwable var5) {
            a(() -> "Failed to parse JFR recording", var5);
            return;
         }

         try {
            a(var1::b);
            Path var2 = var0.resolveSibling("jfr-report-" + StringUtils.substringBefore(var0.getFileName().toString(), ".jfr") + ".json");
            Files.writeString(var2, var1.b(), StandardOpenOption.CREATE);
            a(() -> "Dumped recording summary to " + var2);
         } catch (Throwable var4) {
            a(() -> "Failed to output JFR report", var4);
         }
      }
   }

   private static void a(Supplier<String> var0) {
      if (LogUtils.isLoggerActive()) {
         a.info(var0.get());
      } else {
         DispenserRegistry.a(var0.get());
      }
   }

   private static void a(Supplier<String> var0, Throwable var1) {
      if (LogUtils.isLoggerActive()) {
         a.warn(var0.get(), var1);
      } else {
         DispenserRegistry.a(var0.get());
         var1.printStackTrace(DispenserRegistry.a);
      }
   }
}
