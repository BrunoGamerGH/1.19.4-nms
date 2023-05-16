package net.minecraft.util.thread;

import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.SystemUtils;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;
import net.minecraft.util.profiling.metrics.MetricsRegistry;
import net.minecraft.util.profiling.metrics.ProfilerMeasured;
import org.slf4j.Logger;

public class ThreadedMailbox<T> implements ProfilerMeasured, Mailbox<T>, AutoCloseable, Runnable {
   private static final Logger a = LogUtils.getLogger();
   private static final int b = 1;
   private static final int c = 2;
   private final AtomicInteger d = new AtomicInteger(0);
   private final PairedQueue<? super T, ? extends Runnable> e;
   private final Executor f;
   private final String g;

   public static ThreadedMailbox<Runnable> a(Executor var0, String var1) {
      return new ThreadedMailbox<>(new PairedQueue.c<>(new ConcurrentLinkedQueue<>()), var0, var1);
   }

   public ThreadedMailbox(PairedQueue<? super T, ? extends Runnable> var0, Executor var1, String var2) {
      this.f = var1;
      this.e = var0;
      this.g = var2;
      MetricsRegistry.a.a(this);
   }

   private boolean d() {
      int var0;
      do {
         var0 = this.d.get();
         if ((var0 & 3) != 0) {
            return false;
         }
      } while(!this.d.compareAndSet(var0, var0 | 2));

      return true;
   }

   private void e() {
      int var0;
      do {
         var0 = this.d.get();
      } while(!this.d.compareAndSet(var0, var0 & -3));
   }

   private boolean f() {
      if ((this.d.get() & 1) != 0) {
         return false;
      } else {
         return !this.e.b();
      }
   }

   @Override
   public void close() {
      int var0;
      do {
         var0 = this.d.get();
      } while(!this.d.compareAndSet(var0, var0 | 1));
   }

   private boolean g() {
      return (this.d.get() & 2) != 0;
   }

   private boolean h() {
      if (!this.g()) {
         return false;
      } else {
         Runnable var0 = this.e.a();
         if (var0 == null) {
            return false;
         } else {
            SystemUtils.a(this.g, var0).run();
            return true;
         }
      }
   }

   @Override
   public void run() {
      try {
         this.a(var0 -> var0 == 0);
      } finally {
         this.e();
         this.i();
      }
   }

   public void a() {
      try {
         this.a(var0 -> true);
      } finally {
         this.e();
         this.i();
      }
   }

   @Override
   public void a(T var0) {
      this.e.a(var0);
      this.i();
   }

   private void i() {
      if (this.f() && this.d()) {
         try {
            this.f.execute(this);
         } catch (RejectedExecutionException var4) {
            try {
               this.f.execute(this);
            } catch (RejectedExecutionException var3) {
               a.error("Cound not schedule mailbox", var3);
            }
         }
      }
   }

   private int a(Int2BooleanFunction var0) {
      int var1 = 0;

      while(var0.get(var1) && this.h()) {
         ++var1;
      }

      return var1;
   }

   public int b() {
      return this.e.c();
   }

   public boolean c() {
      return this.g() && !this.e.b();
   }

   @Override
   public String toString() {
      return this.g + " " + this.d.get() + " " + this.e.b();
   }

   @Override
   public String bp() {
      return this.g;
   }

   @Override
   public List<MetricSampler> bm() {
      return ImmutableList.of(MetricSampler.a(this.g + "-queue-size", MetricCategory.c, this::b));
   }
}
