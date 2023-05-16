package net.minecraft.util.thread;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Queues;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;
import net.minecraft.util.profiling.metrics.MetricsRegistry;
import net.minecraft.util.profiling.metrics.ProfilerMeasured;
import org.slf4j.Logger;

public abstract class IAsyncTaskHandler<R extends Runnable> implements ProfilerMeasured, Mailbox<R>, Executor {
   private final String b;
   private static final Logger c = LogUtils.getLogger();
   private final Queue<R> d = Queues.newConcurrentLinkedQueue();
   private int e;

   protected IAsyncTaskHandler(String var0) {
      this.b = var0;
      MetricsRegistry.a.a(this);
   }

   protected abstract R f(Runnable var1);

   protected abstract boolean e(R var1);

   public boolean bn() {
      return Thread.currentThread() == this.au();
   }

   protected abstract Thread au();

   protected boolean at() {
      return !this.bn();
   }

   public int bo() {
      return this.d.size();
   }

   @Override
   public String bp() {
      return this.b;
   }

   public <V> CompletableFuture<V> a(Supplier<V> var0) {
      return this.at() ? CompletableFuture.supplyAsync(var0, this) : CompletableFuture.completedFuture(var0.get());
   }

   private CompletableFuture<Void> a(Runnable var0) {
      return CompletableFuture.supplyAsync(() -> {
         var0.run();
         return null;
      }, this);
   }

   public CompletableFuture<Void> g(Runnable var0) {
      if (this.at()) {
         return this.a(var0);
      } else {
         var0.run();
         return CompletableFuture.completedFuture(null);
      }
   }

   public void h(Runnable var0) {
      if (!this.bn()) {
         this.a(var0).join();
      } else {
         var0.run();
      }
   }

   public void i(R var0) {
      this.d.add(var0);
      LockSupport.unpark(this.au());
   }

   @Override
   public void execute(Runnable var0) {
      if (this.at()) {
         this.i(this.f(var0));
      } else {
         var0.run();
      }
   }

   public void c(Runnable var0) {
      this.execute(var0);
   }

   protected void bq() {
      this.d.clear();
   }

   protected void br() {
      while(this.x()) {
      }
   }

   public boolean x() {
      R var0 = this.d.peek();
      if (var0 == null) {
         return false;
      } else if (this.e == 0 && !this.e(var0)) {
         return false;
      } else {
         this.d(this.d.remove());
         return true;
      }
   }

   public void c(BooleanSupplier var0) {
      ++this.e;

      try {
         while(!var0.getAsBoolean()) {
            if (!this.x()) {
               this.bs();
            }
         }
      } finally {
         --this.e;
      }
   }

   protected void bs() {
      Thread.yield();
      LockSupport.parkNanos("waiting for tasks", 100000L);
   }

   protected void d(R var0) {
      try {
         var0.run();
      } catch (Exception var3) {
         c.error(LogUtils.FATAL_MARKER, "Error executing task on {}", this.bp(), var3);
      }
   }

   @Override
   public List<MetricSampler> bm() {
      return ImmutableList.of(MetricSampler.a(this.b + "-pending-tasks", MetricCategory.b, this::bo));
   }
}
