package net.minecraft.util.thread;

import com.mojang.datafixers.util.Either;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public interface Mailbox<Msg> extends AutoCloseable {
   String bp();

   void a(Msg var1);

   @Override
   default void close() {
   }

   default <Source> CompletableFuture<Source> b(Function<? super Mailbox<Source>, ? extends Msg> var0) {
      CompletableFuture<Source> var1 = new CompletableFuture<>();
      Msg var2 = var0.apply(a("ask future procesor handle", var1::complete));
      this.a(var2);
      return var1;
   }

   default <Source> CompletableFuture<Source> c(Function<? super Mailbox<Either<Source, Exception>>, ? extends Msg> var0) {
      CompletableFuture<Source> var1 = new CompletableFuture<>();
      Msg var2 = var0.apply(a("ask future procesor handle", var1x -> {
         var1x.ifLeft(var1::complete);
         var1x.ifRight(var1::completeExceptionally);
      }));
      this.a(var2);
      return var1;
   }

   static <Msg> Mailbox<Msg> a(final String var0, final Consumer<Msg> var1) {
      return new Mailbox<Msg>() {
         @Override
         public String bp() {
            return var0;
         }

         @Override
         public void a(Msg var0x) {
            var1.accept(var0);
         }

         @Override
         public String toString() {
            return var0;
         }
      };
   }
}
