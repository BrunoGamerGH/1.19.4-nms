package net.minecraft.server.packs.resources;

import com.google.common.base.Stopwatch;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.SystemUtils;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.MethodProfiler;
import net.minecraft.util.profiling.MethodProfilerResults;
import org.slf4j.Logger;

public class ReloadableProfiled extends Reloadable<ReloadableProfiled.a> {
   private static final Logger c = LogUtils.getLogger();
   private final Stopwatch d = Stopwatch.createUnstarted();

   public ReloadableProfiled(IResourceManager var0, List<IReloadListener> var1, Executor var2, Executor var3, CompletableFuture<Unit> var4) {
      super(var2, var3, var0, var1, (var1x, var2x, var3x, var4x, var5x) -> {
         AtomicLong var6 = new AtomicLong();
         AtomicLong var7 = new AtomicLong();
         MethodProfiler var8 = new MethodProfiler(SystemUtils.a, () -> 0, false);
         MethodProfiler var9 = new MethodProfiler(SystemUtils.a, () -> 0, false);
         CompletableFuture<Void> var10 = var3x.a(var1x, var2x, var8, var9, var2xx -> var4x.execute(() -> {
               long var2xxx = SystemUtils.c();
               var2xx.run();
               var6.addAndGet(SystemUtils.c() - var2xxx);
            }), var2xx -> var5x.execute(() -> {
               long var2xxx = SystemUtils.c();
               var2xx.run();
               var7.addAndGet(SystemUtils.c() - var2xxx);
            }));
         return var10.thenApplyAsync(var5xx -> {
            c.debug("Finished reloading " + var3x.c());
            return new ReloadableProfiled.a(var3x.c(), var8.d(), var9.d(), var6, var7);
         }, var3);
      }, var4);
      this.d.start();
      this.b = this.b.thenApplyAsync(this::a, var3);
   }

   private List<ReloadableProfiled.a> a(List<ReloadableProfiled.a> var0) {
      this.d.stop();
      long var1 = 0L;
      c.info("Resource reload finished after {} ms", this.d.elapsed(TimeUnit.MILLISECONDS));

      for(ReloadableProfiled.a var4 : var0) {
         MethodProfilerResults var5 = var4.b;
         MethodProfilerResults var6 = var4.c;
         long var7 = TimeUnit.NANOSECONDS.toMillis(var4.d.get());
         long var9 = TimeUnit.NANOSECONDS.toMillis(var4.e.get());
         long var11 = var7 + var9;
         String var13 = var4.a;
         c.info("{} took approximately {} ms ({} ms preparing, {} ms applying)", new Object[]{var13, var11, var7, var9});
         var1 += var9;
      }

      c.info("Total blocking time: {} ms", var1);
      return var0;
   }

   public static class a {
      final String a;
      final MethodProfilerResults b;
      final MethodProfilerResults c;
      final AtomicLong d;
      final AtomicLong e;

      a(String var0, MethodProfilerResults var1, MethodProfilerResults var2, AtomicLong var3, AtomicLong var4) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.e = var4;
      }
   }
}
