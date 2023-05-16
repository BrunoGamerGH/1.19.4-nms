package net.minecraft.server.packs.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.util.profiling.GameProfilerFiller;

public abstract class ResourceDataAbstract<T> implements IReloadListener {
   @Override
   public final CompletableFuture<Void> a(
      IReloadListener.a var0, IResourceManager var1, GameProfilerFiller var2, GameProfilerFiller var3, Executor var4, Executor var5
   ) {
      return CompletableFuture.<T>supplyAsync(() -> this.b(var1, var2), var4).thenCompose(var0::a).thenAcceptAsync(var2x -> this.a(var2x, var1, var3), var5);
   }

   protected abstract T b(IResourceManager var1, GameProfilerFiller var2);

   protected abstract void a(T var1, IResourceManager var2, GameProfilerFiller var3);
}
