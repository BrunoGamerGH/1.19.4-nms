package net.minecraft.world.ticks;

import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongMaps;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.SystemUtils;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.SectionPosition;
import net.minecraft.util.profiling.GameProfilerFiller;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;

public class TickListServer<T> implements LevelTickAccess<T> {
   private static final Comparator<LevelChunkTicks<?>> a = (var0, var1) -> NextTickListEntry.b.compare(var0.b(), var1.b());
   private final LongPredicate b;
   private final Supplier<GameProfilerFiller> c;
   private final Long2ObjectMap<LevelChunkTicks<T>> d = new Long2ObjectOpenHashMap();
   private final Long2LongMap e = SystemUtils.a(new Long2LongOpenHashMap(), var0x -> var0x.defaultReturnValue(Long.MAX_VALUE));
   private final Queue<LevelChunkTicks<T>> f = new PriorityQueue<>(a);
   private final Queue<NextTickListEntry<T>> g = new ArrayDeque<>();
   private final List<NextTickListEntry<T>> h = new ArrayList<>();
   private final Set<NextTickListEntry<?>> i = new ObjectOpenCustomHashSet(NextTickListEntry.c);
   private final BiConsumer<LevelChunkTicks<T>, NextTickListEntry<T>> j = (var0x, var1x) -> {
      if (var1x.equals(var0x.b())) {
         this.b(var1x);
      }
   };

   public TickListServer(LongPredicate var0, Supplier<GameProfilerFiller> var1) {
      this.b = var0;
      this.c = var1;
   }

   public void a(ChunkCoordIntPair var0, LevelChunkTicks<T> var1) {
      long var2 = var0.a();
      this.d.put(var2, var1);
      NextTickListEntry<T> var4 = var1.b();
      if (var4 != null) {
         this.e.put(var2, var4.c());
      }

      var1.a(this.j);
   }

   public void a(ChunkCoordIntPair var0) {
      long var1 = var0.a();
      LevelChunkTicks<T> var3 = (LevelChunkTicks)this.d.remove(var1);
      this.e.remove(var1);
      if (var3 != null) {
         var3.a(null);
      }
   }

   @Override
   public void a(NextTickListEntry<T> var0) {
      long var1 = ChunkCoordIntPair.a(var0.b());
      LevelChunkTicks<T> var3 = (LevelChunkTicks)this.d.get(var1);
      if (var3 == null) {
         SystemUtils.b(new IllegalStateException("Trying to schedule tick in not loaded position " + var0.b()));
      } else {
         var3.a(var0);
      }
   }

   public void a(long var0, int var2, BiConsumer<BlockPosition, T> var3) {
      GameProfilerFiller var4 = this.c.get();
      var4.a("collect");
      this.a(var0, var2, var4);
      var4.b("run");
      var4.a("ticksToRun", this.g.size());
      this.a(var3);
      var4.b("cleanup");
      this.c();
      var4.c();
   }

   private void a(long var0, int var2, GameProfilerFiller var3) {
      this.a(var0);
      var3.a("containersToTick", this.f.size());
      this.a(var0, var2);
      this.b();
   }

   private void a(long var0) {
      ObjectIterator<Entry> var2 = Long2LongMaps.fastIterator(this.e);

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         long var4 = var3.getLongKey();
         long var6 = var3.getLongValue();
         if (var6 <= var0) {
            LevelChunkTicks<T> var8 = (LevelChunkTicks)this.d.get(var4);
            if (var8 == null) {
               var2.remove();
            } else {
               NextTickListEntry<T> var9 = var8.b();
               if (var9 == null) {
                  var2.remove();
               } else if (var9.c() > var0) {
                  var3.setValue(var9.c());
               } else if (this.b.test(var4)) {
                  var2.remove();
                  this.f.add(var8);
               }
            }
         }
      }
   }

   private void a(long var0, int var2) {
      LevelChunkTicks<T> var3;
      while(this.a(var2) && (var3 = this.f.poll()) != null) {
         NextTickListEntry<T> var4 = var3.c();
         this.c(var4);
         this.a(this.f, var3, var0, var2);
         NextTickListEntry<T> var5 = var3.b();
         if (var5 != null) {
            if (var5.c() <= var0 && this.a(var2)) {
               this.f.add(var3);
            } else {
               this.b(var5);
            }
         }
      }
   }

   private void b() {
      for(LevelChunkTicks<T> var1 : this.f) {
         this.b(var1.b());
      }
   }

   private void b(NextTickListEntry<T> var0) {
      this.e.put(ChunkCoordIntPair.a(var0.b()), var0.c());
   }

   private void a(Queue<LevelChunkTicks<T>> var0, LevelChunkTicks<T> var1, long var2, int var4) {
      if (this.a(var4)) {
         LevelChunkTicks<T> var5 = var0.peek();
         NextTickListEntry<T> var6 = var5 != null ? var5.b() : null;

         while(this.a(var4)) {
            NextTickListEntry<T> var7 = var1.b();
            if (var7 == null || var7.c() > var2 || var6 != null && NextTickListEntry.b.compare(var7, var6) > 0) {
               break;
            }

            var1.c();
            this.c(var7);
         }
      }
   }

   private void c(NextTickListEntry<T> var0) {
      this.g.add(var0);
   }

   private boolean a(int var0) {
      return this.g.size() < var0;
   }

   private void a(BiConsumer<BlockPosition, T> var0) {
      while(!this.g.isEmpty()) {
         NextTickListEntry<T> var1 = this.g.poll();
         if (!this.i.isEmpty()) {
            this.i.remove(var1);
         }

         this.h.add(var1);
         var0.accept(var1.b(), var1.a());
      }
   }

   private void c() {
      this.g.clear();
      this.f.clear();
      this.h.clear();
      this.i.clear();
   }

   @Override
   public boolean a(BlockPosition var0, T var1) {
      LevelChunkTicks<T> var2 = (LevelChunkTicks)this.d.get(ChunkCoordIntPair.a(var0));
      return var2 != null && var2.a(var0, var1);
   }

   @Override
   public boolean b(BlockPosition var0, T var1) {
      this.d();
      return this.i.contains(NextTickListEntry.a(var1, var0));
   }

   private void d() {
      if (this.i.isEmpty() && !this.g.isEmpty()) {
         this.i.addAll(this.g);
      }
   }

   private void a(StructureBoundingBox var0, TickListServer.a<T> var1) {
      int var2 = SectionPosition.a((double)var0.g());
      int var3 = SectionPosition.a((double)var0.i());
      int var4 = SectionPosition.a((double)var0.j());
      int var5 = SectionPosition.a((double)var0.l());

      for(int var6 = var2; var6 <= var4; ++var6) {
         for(int var7 = var3; var7 <= var5; ++var7) {
            long var8 = ChunkCoordIntPair.c(var6, var7);
            LevelChunkTicks<T> var10 = (LevelChunkTicks)this.d.get(var8);
            if (var10 != null) {
               var1.accept(var8, var10);
            }
         }
      }
   }

   public void a(StructureBoundingBox var0) {
      Predicate<NextTickListEntry<T>> var1 = var1x -> var0.b(var1x.b());
      this.a(var0, (var1x, var3) -> {
         NextTickListEntry<T> var4 = var3.b();
         var3.a(var1);
         NextTickListEntry<T> var5 = var3.b();
         if (var5 != var4) {
            if (var5 != null) {
               this.b(var5);
            } else {
               this.e.remove(var1x);
            }
         }
      });
      this.h.removeIf(var1);
      this.g.removeIf(var1);
   }

   public void a(StructureBoundingBox var0, BaseBlockPosition var1) {
      this.a(this, var0, var1);
   }

   public void a(TickListServer<T> var0, StructureBoundingBox var1, BaseBlockPosition var2) {
      List<NextTickListEntry<T>> var3 = new ArrayList<>();
      Predicate<NextTickListEntry<T>> var4 = var1x -> var1.b(var1x.b());
      var0.h.stream().filter(var4).forEach(var3::add);
      var0.g.stream().filter(var4).forEach(var3::add);
      var0.a(var1, (var2x, var4x) -> var4x.d().filter(var4).forEach(var3::add));
      LongSummaryStatistics var5 = var3.stream().mapToLong(NextTickListEntry::e).summaryStatistics();
      long var6 = var5.getMin();
      long var8 = var5.getMax();
      var3.forEach(var5x -> this.a(new NextTickListEntry<>(var5x.a(), var5x.b().a(var2), var5x.c(), var5x.d(), var5x.e() - var6 + var8 + 1L)));
   }

   @Override
   public int a() {
      return this.d.values().stream().mapToInt(TickList::a).sum();
   }

   @FunctionalInterface
   interface a<T> {
      void accept(long var1, LevelChunkTicks<T> var3);
   }
}
