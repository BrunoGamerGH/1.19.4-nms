package net.minecraft.server.dedicated;

import com.google.common.collect.Streams;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.SystemUtils;
import net.minecraft.server.DispenserRegistry;
import net.minecraft.world.level.GameRules;
import org.slf4j.Logger;

public class ThreadWatchdog implements Runnable {
   private static final Logger a = LogUtils.getLogger();
   private static final long b = 10000L;
   private static final int c = 1;
   private final DedicatedServer d;
   private final long e;

   public ThreadWatchdog(DedicatedServer var0) {
      this.d = var0;
      this.e = var0.bl();
   }

   @Override
   public void run() {
      while(this.d.v()) {
         long var0 = this.d.ax();
         long var2 = SystemUtils.b();
         long var4 = var2 - var0;
         if (var4 > this.e) {
            a.error(
               LogUtils.FATAL_MARKER,
               "A single server tick took {} seconds (should be max {})",
               String.format(Locale.ROOT, "%.2f", (float)var4 / 1000.0F),
               String.format(Locale.ROOT, "%.2f", 0.05F)
            );
            a.error(LogUtils.FATAL_MARKER, "Considering it to be crashed, server will forcibly shutdown.");
            ThreadMXBean var6 = ManagementFactory.getThreadMXBean();
            ThreadInfo[] var7 = var6.dumpAllThreads(true, true);
            StringBuilder var8 = new StringBuilder();
            Error var9 = new Error("Watchdog");

            for(ThreadInfo var13 : var7) {
               if (var13.getThreadId() == this.d.au().getId()) {
                  var9.setStackTrace(var13.getStackTrace());
               }

               var8.append(var13);
               var8.append("\n");
            }

            CrashReport var10 = new CrashReport("Watching Server", var9);
            this.d.b(var10.g());
            CrashReportSystemDetails var11 = var10.a("Thread Dump");
            var11.a("Threads", var8);
            CrashReportSystemDetails var12 = var10.a("Performance stats");
            var12.a("Random tick rate", () -> this.d.aW().q().a(GameRules.n).toString());
            var12.a("Level stats", () -> Streams.stream(this.d.F()).map(var0x -> var0x.ab() + ": " + var0x.D()).collect(Collectors.joining(",\n")));
            DispenserRegistry.a("Crash report:\n" + var10.e());
            File var13 = new File(new File(this.d.z(), "crash-reports"), "crash-" + SystemUtils.e() + "-server.txt");
            if (var10.a(var13)) {
               a.error("This crash report has been saved to: {}", var13.getAbsolutePath());
            } else {
               a.error("We were unable to save this crash report to disk.");
            }

            this.a();
         }

         try {
            Thread.sleep(var0 + this.e - var2);
         } catch (InterruptedException var15) {
         }
      }
   }

   private void a() {
      try {
         Timer var0 = new Timer();
         var0.schedule(new TimerTask() {
            @Override
            public void run() {
               Runtime.getRuntime().halt(1);
            }
         }, 10000L);
         System.exit(1);
      } catch (Throwable var2) {
         Runtime.getRuntime().halt(1);
      }
   }
}
