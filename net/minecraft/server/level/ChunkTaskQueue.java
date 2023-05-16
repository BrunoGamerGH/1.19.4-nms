package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.world.level.ChunkCoordIntPair;

public class ChunkTaskQueue<T> {
   public static final int a = PlayerChunkMap.b + 2;
   private final List<Long2ObjectLinkedOpenHashMap<List<Optional<T>>>> b = IntStream.range(0, a)
      .mapToObj(var0x -> new Long2ObjectLinkedOpenHashMap())
      .collect(Collectors.toList());
   private volatile int c = a;
   private final String d;
   private final LongSet e = new LongOpenHashSet();
   private final int f;

   public ChunkTaskQueue(String var0, int var1) {
      this.d = var0;
      this.f = var1;
   }

   protected void a(int var0, ChunkCoordIntPair var1, int var2) {
      if (var0 < a) {
         Long2ObjectLinkedOpenHashMap<List<Optional<T>>> var3 = (Long2ObjectLinkedOpenHashMap)this.b.get(var0);
         List<Optional<T>> var4 = (List)var3.remove(var1.a());
         if (var0 == this.c) {
            while(this.b() && ((Long2ObjectLinkedOpenHashMap)this.b.get(this.c)).isEmpty()) {
               ++this.c;
            }
         }

         if (var4 != null && !var4.isEmpty()) {
            ((List)((Long2ObjectLinkedOpenHashMap)this.b.get(var2)).computeIfAbsent(var1.a(), var0x -> Lists.newArrayList())).addAll(var4);
            this.c = Math.min(this.c, var2);
         }
      }
   }

   protected void a(Optional<T> var0, long var1, int var3) {
      ((List)((Long2ObjectLinkedOpenHashMap)this.b.get(var3)).computeIfAbsent(var1, var0x -> Lists.newArrayList())).add(var0);
      this.c = Math.min(this.c, var3);
   }

   protected void a(long var0, boolean var2) {
      for(Long2ObjectLinkedOpenHashMap<List<Optional<T>>> var4 : this.b) {
         List<Optional<T>> var5 = (List)var4.get(var0);
         if (var5 != null) {
            if (var2) {
               var5.clear();
            } else {
               var5.removeIf(var0x -> !var0x.isPresent());
            }

            if (var5.isEmpty()) {
               var4.remove(var0);
            }
         }
      }

      while(this.b() && ((Long2ObjectLinkedOpenHashMap)this.b.get(this.c)).isEmpty()) {
         ++this.c;
      }

      this.e.remove(var0);
   }

   private Runnable a(long var0) {
      return () -> this.e.add(var0);
   }

   @Nullable
   public Stream<Either<T, Runnable>> a() {
      if (this.e.size() >= this.f) {
         return null;
      } else if (!this.b()) {
         return null;
      } else {
         int var0 = this.c;
         Long2ObjectLinkedOpenHashMap<List<Optional<T>>> var1 = (Long2ObjectLinkedOpenHashMap)this.b.get(var0);
         long var2 = var1.firstLongKey();
         List<Optional<T>> var4 = (List)var1.removeFirst();

         while(this.b() && ((Long2ObjectLinkedOpenHashMap)this.b.get(this.c)).isEmpty()) {
            ++this.c;
         }

         return var4.stream().map(var2x -> (Either)var2x.map(Either::left).orElseGet(() -> (T)Either.right(this.a(var2))));
      }
   }

   public boolean b() {
      return this.c < a;
   }

   @Override
   public String toString() {
      return this.d + " " + this.c + "...";
   }

   @VisibleForTesting
   LongSet c() {
      return new LongOpenHashSet(this.e);
   }
}
