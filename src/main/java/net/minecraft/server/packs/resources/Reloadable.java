package net.minecraft.server.packs.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.SystemUtils;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.GameProfilerDisabled;

public class Reloadable<S> implements IReloadable {
   private static final int c = 2;
   private static final int d = 2;
   private static final int e = 1;
   protected final CompletableFuture<Unit> a = new CompletableFuture<>();
   protected CompletableFuture<List<S>> b;
   final Set<IReloadListener> f;
   private final int g;
   private int h;
   private int i;
   private final AtomicInteger j = new AtomicInteger();
   private final AtomicInteger k = new AtomicInteger();

   public static Reloadable<Void> a(IResourceManager var0, List<IReloadListener> var1, Executor var2, Executor var3, CompletableFuture<Unit> var4) {
      return new Reloadable<>(
         var2, var3, var0, var1, (var1x, var2x, var3x, var4x, var5) -> var3x.a(var1x, var2x, GameProfilerDisabled.a, GameProfilerDisabled.a, var2, var5), var4
      );
   }

   protected Reloadable(
      Executor var0, final Executor var1, IResourceManager var2, List<IReloadListener> var3, Reloadable.a<S> var4, CompletableFuture<Unit> var5
   ) {
      this.g = var3.size();
      this.j.incrementAndGet();
      var5.thenRun(this.k::incrementAndGet);
      List<CompletableFuture<S>> var6 = Lists.newArrayList();
      CompletableFuture<?> var7 = var5;
      this.f = Sets.newHashSet(var3);

      for(final IReloadListener var9 : var3) {
         final CompletableFuture<?> var10 = var7;
         CompletableFuture<S> var11 = var4.create(new IReloadListener.a() {
            @Override
            public <T> CompletableFuture<T> a(T var0) {
               var1.execute(() -> {
                  Reloadable.this.f.remove(var9);
                  if (Reloadable.this.f.isEmpty()) {
                     Reloadable.this.a.complete(Unit.a);
                  }
               });
               return Reloadable.this.a.thenCombine(var10, (var1xx, var2) -> var0);
            }
         }, var2, var9, var1x -> {
            this.j.incrementAndGet();
            var0.execute(() -> {
               var1x.run();
               this.k.incrementAndGet();
            });
         }, var1x -> {
            ++this.h;
            var1.execute(() -> {
               var1x.run();
               ++this.i;
            });
         });
         var6.add(var11);
         var7 = var11;
      }

      this.b = SystemUtils.c(var6);
   }

   @Override
   public CompletableFuture<?> a() {
      return this.b;
   }

   @Override
   public float b() {
      int var0 = this.g - this.f.size();
      float var1 = (float)(this.k.get() * 2 + this.i * 2 + var0 * 1);
      float var2 = (float)(this.j.get() * 2 + this.h * 2 + this.g * 1);
      return var1 / var2;
   }

   public static IReloadable a(IResourceManager var0, List<IReloadListener> var1, Executor var2, Executor var3, CompletableFuture<Unit> var4, boolean var5) {
      return (IReloadable)(var5 ? new ReloadableProfiled(var0, var1, var2, var3, var4) : a(var0, var1, var2, var3, var4));
   }

   protected interface a<S> {
      CompletableFuture<S> create(IReloadListener.a var1, IResourceManager var2, IReloadListener var3, Executor var4, Executor var5);
   }
}
