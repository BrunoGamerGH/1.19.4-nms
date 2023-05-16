package net.minecraft.util;

import com.mojang.logging.LogUtils;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.ReportedException;
import org.slf4j.Logger;

public class ThreadingDetector {
   private static final Logger a = LogUtils.getLogger();
   private final String b;
   private final Semaphore c = new Semaphore(1);
   private final Lock d = new ReentrantLock();
   @Nullable
   private volatile Thread e;
   @Nullable
   private volatile ReportedException f;

   public ThreadingDetector(String var0) {
      this.b = var0;
   }

   public void a() {
      boolean var0 = false;

      try {
         this.d.lock();
         if (!this.c.tryAcquire()) {
            this.e = Thread.currentThread();
            var0 = true;
            this.d.unlock();

            try {
               this.c.acquire();
            } catch (InterruptedException var6) {
               Thread.currentThread().interrupt();
            }

            throw this.f;
         }
      } finally {
         if (!var0) {
            this.d.unlock();
         }
      }
   }

   public void b() {
      try {
         this.d.lock();
         Thread var0 = this.e;
         if (var0 != null) {
            ReportedException var1 = a(this.b, var0);
            this.f = var1;
            this.c.release();
            throw var1;
         }

         this.c.release();
      } finally {
         this.d.unlock();
      }
   }

   public static ReportedException a(String var0, @Nullable Thread var1) {
      String var2 = Stream.of(Thread.currentThread(), var1).filter(Objects::nonNull).map(ThreadingDetector::a).collect(Collectors.joining("\n"));
      String var3 = "Accessing " + var0 + " from multiple threads";
      CrashReport var4 = new CrashReport(var3, new IllegalStateException(var3));
      CrashReportSystemDetails var5 = var4.a("Thread dumps");
      var5.a("Thread dumps", var2);
      a.error("Thread dumps: \n" + var2);
      return new ReportedException(var4);
   }

   private static String a(Thread var0) {
      return var0.getName() + ": \n\tat " + (String)Arrays.stream(var0.getStackTrace()).map(Object::toString).collect(Collectors.joining("\n\tat "));
   }
}
