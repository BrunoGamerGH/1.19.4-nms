package net.minecraft.util.profiling;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.util.profiling.metrics.MetricCategory;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;

public class MethodProfiler implements GameProfilerFillerActive {
   private static final long a = Duration.ofMillis(100L).toNanos();
   private static final Logger c = LogUtils.getLogger();
   private final List<String> d = Lists.newArrayList();
   private final LongList e = new LongArrayList();
   private final Map<String, MethodProfiler.a> f = Maps.newHashMap();
   private final IntSupplier g;
   private final LongSupplier h;
   private final long i;
   private final int j;
   private String k = "";
   private boolean l;
   @Nullable
   private MethodProfiler.a m;
   private final boolean n;
   private final Set<Pair<String, MetricCategory>> o = new ObjectArraySet();

   public MethodProfiler(LongSupplier var0, IntSupplier var1, boolean var2) {
      this.i = var0.getAsLong();
      this.h = var0;
      this.j = var1.getAsInt();
      this.g = var1;
      this.n = var2;
   }

   @Override
   public void a() {
      if (this.l) {
         c.error("Profiler tick already started - missing endTick()?");
      } else {
         this.l = true;
         this.k = "";
         this.d.clear();
         this.a("root");
      }
   }

   @Override
   public void b() {
      if (!this.l) {
         c.error("Profiler tick already ended - missing startTick()?");
      } else {
         this.c();
         this.l = false;
         if (!this.k.isEmpty()) {
            c.error(
               "Profiler tick ended before path was fully popped (remainder: '{}'). Mismatched push/pop?",
               LogUtils.defer(() -> MethodProfilerResults.b(this.k))
            );
         }
      }
   }

   @Override
   public void a(String var0) {
      if (!this.l) {
         c.error("Cannot push '{}' to profiler if profiler tick hasn't started - missing startTick()?", var0);
      } else {
         if (!this.k.isEmpty()) {
            this.k = this.k + "\u001e";
         }

         this.k = this.k + var0;
         this.d.add(this.k);
         this.e.add(SystemUtils.c());
         this.m = null;
      }
   }

   @Override
   public void a(Supplier<String> var0) {
      this.a(var0.get());
   }

   @Override
   public void a(MetricCategory var0) {
      this.o.add(Pair.of(this.k, var0));
   }

   @Override
   public void c() {
      if (!this.l) {
         c.error("Cannot pop from profiler if profiler tick hasn't started - missing startTick()?");
      } else if (this.e.isEmpty()) {
         c.error("Tried to pop one too many times! Mismatched push() and pop()?");
      } else {
         long var0 = SystemUtils.c();
         long var2 = this.e.removeLong(this.e.size() - 1);
         this.d.remove(this.d.size() - 1);
         long var4 = var0 - var2;
         MethodProfiler.a var6 = this.f();
         var6.c += var4;
         ++var6.d;
         var6.a = Math.max(var6.a, var4);
         var6.b = Math.min(var6.b, var4);
         if (this.n && var4 > a) {
            c.warn(
               "Something's taking too long! '{}' took aprox {} ms",
               LogUtils.defer(() -> MethodProfilerResults.b(this.k)),
               LogUtils.defer(() -> (double)var4 / 1000000.0)
            );
         }

         this.k = this.d.isEmpty() ? "" : this.d.get(this.d.size() - 1);
         this.m = null;
      }
   }

   @Override
   public void b(String var0) {
      this.c();
      this.a(var0);
   }

   @Override
   public void b(Supplier<String> var0) {
      this.c();
      this.a(var0);
   }

   private MethodProfiler.a f() {
      if (this.m == null) {
         this.m = this.f.computeIfAbsent(this.k, var0 -> new MethodProfiler.a());
      }

      return this.m;
   }

   @Override
   public void a(String var0, int var1) {
      this.f().e.addTo(var0, (long)var1);
   }

   @Override
   public void a(Supplier<String> var0, int var1) {
      this.f().e.addTo(var0.get(), (long)var1);
   }

   @Override
   public MethodProfilerResults d() {
      return new MethodProfilerResultsFilled(this.f, this.i, this.j, this.h.getAsLong(), this.g.getAsInt());
   }

   @Nullable
   @Override
   public MethodProfiler.a c(String var0) {
      return this.f.get(var0);
   }

   @Override
   public Set<Pair<String, MetricCategory>> e() {
      return this.o;
   }

   public static class a implements MethodProfilerResult {
      long a = Long.MIN_VALUE;
      long b = Long.MAX_VALUE;
      long c;
      long d;
      final Object2LongOpenHashMap<String> e = new Object2LongOpenHashMap();

      @Override
      public long a() {
         return this.c;
      }

      @Override
      public long b() {
         return this.a;
      }

      @Override
      public long c() {
         return this.d;
      }

      @Override
      public Object2LongMap<String> d() {
         return Object2LongMaps.unmodifiable(this.e);
      }
   }
}
