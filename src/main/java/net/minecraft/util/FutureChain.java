package net.minecraft.util;

import com.mojang.logging.LogUtils;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import org.slf4j.Logger;

public class FutureChain implements TaskChainer, AutoCloseable {
   private static final Logger b = LogUtils.getLogger();
   private CompletableFuture<?> c = CompletableFuture.completedFuture(null);
   private final Executor d;
   private volatile boolean e;

   public FutureChain(Executor var0) {
      this.d = var1x -> {
         if (!this.e) {
            var0.execute(var1x);
         }
      };
   }

   @Override
   public void append(TaskChainer.a var0) {
      this.c = this.c.thenComposeAsync(var1x -> var0.submit(this.d), this.d).exceptionally(var0x -> {
         if (var0x instanceof CompletionException var1x) {
            var0x = var1x.getCause();
         }

         if (var0x instanceof CancellationException var1) {
            throw var1;
         } else {
            b.error("Chain link failed, continuing to next one", var0x);
            return null;
         }
      });
   }

   @Override
   public void close() {
      this.e = true;
   }
}
