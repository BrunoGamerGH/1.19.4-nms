package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.SystemUtils;
import net.minecraft.util.Unit;
import net.minecraft.util.thread.Mailbox;
import net.minecraft.util.thread.PairedQueue;
import net.minecraft.util.thread.ThreadedMailbox;
import net.minecraft.world.level.ChunkCoordIntPair;
import org.slf4j.Logger;

public class ChunkTaskQueueSorter implements PlayerChunk.d, AutoCloseable {
   private static final Logger a = LogUtils.getLogger();
   private final Map<Mailbox<?>, ChunkTaskQueue<? extends Function<Mailbox<Unit>, ?>>> b;
   private final Set<Mailbox<?>> c;
   private final ThreadedMailbox<PairedQueue.b> d;

   public ChunkTaskQueueSorter(List<Mailbox<?>> var0, Executor var1, int var2) {
      this.b = var0.stream().collect(Collectors.toMap(Function.identity(), var1x -> new ChunkTaskQueue(var1x.bp() + "_queue", var2)));
      this.c = Sets.newHashSet(var0);
      this.d = new ThreadedMailbox<>(new PairedQueue.a(4), var1, "sorter");
   }

   public boolean a() {
      return this.d.c() || this.b.values().stream().anyMatch(ChunkTaskQueue::b);
   }

   public static <T> ChunkTaskQueueSorter.a<T> a(Function<Mailbox<Unit>, T> var0, long var1, IntSupplier var3) {
      return new ChunkTaskQueueSorter.a<>(var0, var1, var3);
   }

   public static ChunkTaskQueueSorter.a<Runnable> a(Runnable var0, long var1, IntSupplier var3) {
      return new ChunkTaskQueueSorter.a<>(var1x -> () -> {
            var0.run();
            var1x.a(Unit.a);
         }, var1, var3);
   }

   public static ChunkTaskQueueSorter.a<Runnable> a(PlayerChunk var0, Runnable var1) {
      return a(var1, var0.j().a(), var0::l);
   }

   public static <T> ChunkTaskQueueSorter.a<T> a(PlayerChunk var0, Function<Mailbox<Unit>, T> var1) {
      return a(var1, var0.j().a(), var0::l);
   }

   public static ChunkTaskQueueSorter.b a(Runnable var0, long var1, boolean var3) {
      return new ChunkTaskQueueSorter.b(var0, var1, var3);
   }

   public <T> Mailbox<ChunkTaskQueueSorter.a<T>> a(Mailbox<T> var0, boolean var1) {
      return this.d.<Mailbox<ChunkTaskQueueSorter.a<T>>>b(var2x -> new PairedQueue.b(0, () -> {
            this.b(var0);
            var2x.a(Mailbox.a("chunk priority sorter around " + var0.bp(), var2xxx -> this.a(var0, var2xxx.a, var2xxx.b, var2xxx.c, var1)));
         })).join();
   }

   public Mailbox<ChunkTaskQueueSorter.b> a(Mailbox<Runnable> var0) {
      return this.d
         .<Mailbox<ChunkTaskQueueSorter.b>>b(
            var1x -> new PairedQueue.b(
                  0, () -> var1x.a(Mailbox.a("chunk priority sorter around " + var0.bp(), var1xxx -> this.a(var0, var1xxx.b, var1xxx.a, var1xxx.c)))
               )
         )
         .join();
   }

   @Override
   public void onLevelChange(ChunkCoordIntPair var0, IntSupplier var1, int var2, IntConsumer var3) {
      this.d.a(new PairedQueue.b(0, () -> {
         int var4x = var1.getAsInt();
         this.b.values().forEach(var3xx -> var3xx.a(var4x, var0, var2));
         var3.accept(var2);
      }));
   }

   private <T> void a(Mailbox<T> var0, long var1, Runnable var3, boolean var4) {
      this.d.a(new PairedQueue.b(1, () -> {
         ChunkTaskQueue<Function<Mailbox<Unit>, T>> var5x = this.b(var0);
         var5x.a(var1, var4);
         if (this.c.remove(var0)) {
            this.a(var5x, var0);
         }

         var3.run();
      }));
   }

   private <T> void a(Mailbox<T> var0, Function<Mailbox<Unit>, T> var1, long var2, IntSupplier var4, boolean var5) {
      this.d.a(new PairedQueue.b(2, () -> {
         ChunkTaskQueue<Function<Mailbox<Unit>, T>> var6x = this.b(var0);
         int var7 = var4.getAsInt();
         var6x.a(Optional.of(var1), var2, var7);
         if (var5) {
            var6x.a(Optional.empty(), var2, var7);
         }

         if (this.c.remove(var0)) {
            this.a(var6x, var0);
         }
      }));
   }

   private <T> void a(ChunkTaskQueue<Function<Mailbox<Unit>, T>> var0, Mailbox<T> var1) {
      this.d.a(new PairedQueue.b(3, () -> {
         Stream<Either<Function<Mailbox<Unit>, T>, Runnable>> var2x = var0.a();
         if (var2x == null) {
            this.c.add(var1);
         } else {
            CompletableFuture.allOf(var2x.map(var1xx -> (CompletableFuture)var1xx.map(var1::b, var0xxx -> {
                  var0xxx.run();
                  return CompletableFuture.completedFuture(Unit.a);
               })).toArray(var0xx -> new CompletableFuture[var0xx])).thenAccept(var2xx -> this.a(var0, var1));
         }
      }));
   }

   private <T> ChunkTaskQueue<Function<Mailbox<Unit>, T>> b(Mailbox<T> var0) {
      ChunkTaskQueue<? extends Function<Mailbox<Unit>, ?>> var1 = this.b.get(var0);
      if (var1 == null) {
         throw (IllegalArgumentException)SystemUtils.b(new IllegalArgumentException("No queue for: " + var0));
      } else {
         return var1;
      }
   }

   @VisibleForTesting
   public String b() {
      return (String)this.b
            .entrySet()
            .stream()
            .map(
               var0 -> var0.getKey().bp()
                     + "=["
                     + (String)var0.getValue().c().stream().map(var0x -> var0x + ":" + new ChunkCoordIntPair(var0x)).collect(Collectors.joining(","))
                     + "]"
            )
            .collect(Collectors.joining(","))
         + ", s="
         + this.c.size();
   }

   @Override
   public void close() {
      this.b.keySet().forEach(Mailbox::close);
   }

   public static final class a<T> {
      final Function<Mailbox<Unit>, T> a;
      final long b;
      final IntSupplier c;

      a(Function<Mailbox<Unit>, T> var0, long var1, IntSupplier var3) {
         this.a = var0;
         this.b = var1;
         this.c = var3;
      }
   }

   public static final class b {
      final Runnable a;
      final long b;
      final boolean c;

      b(Runnable var0, long var1, boolean var3) {
         this.a = var0;
         this.b = var1;
         this.c = var3;
      }
   }
}
