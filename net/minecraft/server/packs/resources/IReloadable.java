package net.minecraft.server.packs.resources;

import java.util.concurrent.CompletableFuture;

public interface IReloadable {
   CompletableFuture<?> a();

   float b();

   default boolean c() {
      return this.a().isDone();
   }

   default void d() {
      CompletableFuture<?> var0 = this.a();
      if (var0.isCompletedExceptionally()) {
         var0.join();
      }
   }
}
