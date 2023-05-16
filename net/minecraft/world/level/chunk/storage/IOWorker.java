package net.minecraft.world.level.chunk.storage;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import java.io.IOException;
import java.nio.file.Path;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.visitors.CollectFields;
import net.minecraft.nbt.visitors.FieldSelector;
import net.minecraft.util.Unit;
import net.minecraft.util.thread.PairedQueue;
import net.minecraft.util.thread.ThreadedMailbox;
import net.minecraft.world.level.ChunkCoordIntPair;
import org.slf4j.Logger;

public class IOWorker implements ChunkScanAccess, AutoCloseable {
   private static final Logger a = LogUtils.getLogger();
   private final AtomicBoolean b = new AtomicBoolean();
   private final ThreadedMailbox<PairedQueue.b> c;
   private final RegionFileCache d;
   private final Map<ChunkCoordIntPair, IOWorker.a> e = Maps.newLinkedHashMap();
   private final Long2ObjectLinkedOpenHashMap<CompletableFuture<BitSet>> f = new Long2ObjectLinkedOpenHashMap();
   private static final int g = 1024;

   protected IOWorker(Path var0, boolean var1, String var2) {
      this.d = new RegionFileCache(var0, var1);
      this.c = new ThreadedMailbox<>(new PairedQueue.a(IOWorker.Priority.values().length), SystemUtils.g(), "IOWorker-" + var2);
   }

   public boolean a(ChunkCoordIntPair var0, int var1) {
      ChunkCoordIntPair var2 = new ChunkCoordIntPair(var0.e - var1, var0.f - var1);
      ChunkCoordIntPair var3 = new ChunkCoordIntPair(var0.e + var1, var0.f + var1);

      for(int var4 = var2.h(); var4 <= var3.h(); ++var4) {
         for(int var5 = var2.i(); var5 <= var3.i(); ++var5) {
            BitSet var6 = this.a(var4, var5).join();
            if (!var6.isEmpty()) {
               ChunkCoordIntPair var7 = ChunkCoordIntPair.a(var4, var5);
               int var8 = Math.max(var2.e - var7.e, 0);
               int var9 = Math.max(var2.f - var7.f, 0);
               int var10 = Math.min(var3.e - var7.e, 31);
               int var11 = Math.min(var3.f - var7.f, 31);

               for(int var12 = var8; var12 <= var10; ++var12) {
                  for(int var13 = var9; var13 <= var11; ++var13) {
                     int var14 = var13 * 32 + var12;
                     if (var6.get(var14)) {
                        return true;
                     }
                  }
               }
            }
         }
      }

      return false;
   }

   private CompletableFuture<BitSet> a(int var0, int var1) {
      long var2 = ChunkCoordIntPair.c(var0, var1);
      synchronized(this.f) {
         CompletableFuture<BitSet> var5 = (CompletableFuture)this.f.getAndMoveToFirst(var2);
         if (var5 == null) {
            var5 = this.b(var0, var1);
            this.f.putAndMoveToFirst(var2, var5);
            if (this.f.size() > 1024) {
               this.f.removeLast();
            }
         }

         return var5;
      }
   }

   private CompletableFuture<BitSet> b(int var0, int var1) {
      return CompletableFuture.supplyAsync(() -> {
         ChunkCoordIntPair var2x = ChunkCoordIntPair.a(var0, var1);
         ChunkCoordIntPair var3 = ChunkCoordIntPair.b(var0, var1);
         BitSet var4 = new BitSet();
         ChunkCoordIntPair.a(var2x, var3).forEach(var1xx -> {
            CollectFields var2xx = new CollectFields(new FieldSelector(NBTTagInt.a, "DataVersion"), new FieldSelector(NBTTagCompound.b, "blending_data"));

            try {
               this.a(var1xx, var2xx).join();
            } catch (Exception var7) {
               a.warn("Failed to scan chunk {}", var1xx, var7);
               return;
            }

            NBTBase var3x = var2xx.d();
            if (var3x instanceof NBTTagCompound var4x && this.a(var4x)) {
               int var5x = var1xx.k() * 32 + var1xx.j();
               var4.set(var5x);
            }
         });
         return var4;
      }, SystemUtils.f());
   }

   private boolean a(NBTTagCompound var0) {
      return var0.b("DataVersion", 99) && var0.h("DataVersion") >= 3088 ? var0.b("blending_data", 10) : true;
   }

   public CompletableFuture<Void> a(ChunkCoordIntPair var0, @Nullable NBTTagCompound var1) {
      return this.a(() -> {
         IOWorker.a var2x = this.e.computeIfAbsent(var0, var1xx -> new IOWorker.a(var1));
         var2x.a = var1;
         return Either.left(var2x.b);
      }).thenCompose(Function.identity());
   }

   public CompletableFuture<Optional<NBTTagCompound>> a(ChunkCoordIntPair var0) {
      return this.a(() -> {
         IOWorker.a var1x = this.e.get(var0);
         if (var1x != null) {
            return Either.left(Optional.ofNullable(var1x.a));
         } else {
            try {
               NBTTagCompound var2 = this.d.a(var0);
               return Either.left(Optional.ofNullable(var2));
            } catch (Exception var4) {
               a.warn("Failed to read chunk {}", var0, var4);
               return Either.right(var4);
            }
         }
      });
   }

   public CompletableFuture<Void> a(boolean var0) {
      CompletableFuture<Void> var1 = this.a(
            () -> Either.left(CompletableFuture.allOf(this.e.values().stream().map(var0x -> var0x.b).toArray(var0x -> new CompletableFuture[var0x])))
         )
         .thenCompose(Function.identity());
      return var0 ? var1.thenCompose(var0x -> this.a(() -> {
            try {
               this.d.a();
               return Either.left(null);
            } catch (Exception var2x) {
               a.warn("Failed to synchronize chunks", var2x);
               return Either.right(var2x);
            }
         })) : var1.thenCompose(var0x -> this.a(() -> Either.left(null)));
   }

   @Override
   public CompletableFuture<Void> a(ChunkCoordIntPair var0, StreamTagVisitor var1) {
      return this.a(() -> {
         try {
            IOWorker.a var2x = this.e.get(var0);
            if (var2x != null) {
               if (var2x.a != null) {
                  var2x.a.b(var1);
               }
            } else {
               this.d.a(var0, var1);
            }

            return Either.left(null);
         } catch (Exception var4) {
            a.warn("Failed to bulk scan chunk {}", var0, var4);
            return Either.right(var4);
         }
      });
   }

   private <T> CompletableFuture<T> a(Supplier<Either<T, Exception>> var0) {
      return this.c.c(var1x -> new PairedQueue.b(IOWorker.Priority.a.ordinal(), () -> {
            if (!this.b.get()) {
               var1x.a((Either)var0.get());
            }

            this.b();
         }));
   }

   private void a() {
      if (!this.e.isEmpty()) {
         Iterator<Entry<ChunkCoordIntPair, IOWorker.a>> var0 = this.e.entrySet().iterator();
         Entry<ChunkCoordIntPair, IOWorker.a> var1 = var0.next();
         var0.remove();
         this.a(var1.getKey(), var1.getValue());
         this.b();
      }
   }

   private void b() {
      this.c.a(new PairedQueue.b(IOWorker.Priority.b.ordinal(), this::a));
   }

   private void a(ChunkCoordIntPair var0, IOWorker.a var1) {
      try {
         this.d.a(var0, var1.a);
         var1.b.complete(null);
      } catch (Exception var4) {
         a.error("Failed to store chunk {}", var0, var4);
         var1.b.completeExceptionally(var4);
      }
   }

   @Override
   public void close() throws IOException {
      if (this.b.compareAndSet(false, true)) {
         this.c.b(var0 -> new PairedQueue.b(IOWorker.Priority.c.ordinal(), () -> var0.a(Unit.a))).join();
         this.c.close();

         try {
            this.d.close();
         } catch (Exception var2) {
            a.error("Failed to close storage", var2);
         }
      }
   }

   static enum Priority {
      a,
      b,
      c;
   }

   static class a {
      @Nullable
      NBTTagCompound a;
      final CompletableFuture<Void> b = new CompletableFuture<>();

      public a(@Nullable NBTTagCompound var0) {
         this.a = var0;
      }
   }
}
