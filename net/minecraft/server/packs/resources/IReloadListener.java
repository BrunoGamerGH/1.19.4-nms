package net.minecraft.server.packs.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.util.profiling.GameProfilerFiller;

public interface IReloadListener {
   CompletableFuture<Void> a(IReloadListener.a var1, IResourceManager var2, GameProfilerFiller var3, GameProfilerFiller var4, Executor var5, Executor var6);

   default String c() {
      return this.getClass().getSimpleName();
   }

   public interface a {
      <T> CompletableFuture<T> a(T var1);
   }
}
