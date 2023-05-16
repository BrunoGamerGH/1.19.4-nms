package net.minecraft.world.ticks;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import it.unimi.dsi.fastutil.objects.ObjectOpenCustomHashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.level.ChunkCoordIntPair;

public class LevelChunkTicks<T> implements SerializableTickContainer<T>, TickContainerAccess<T> {
   private final Queue<NextTickListEntry<T>> a = new PriorityQueue<>(NextTickListEntry.a);
   @Nullable
   private List<TickListChunk<T>> b;
   private final Set<NextTickListEntry<?>> c = new ObjectOpenCustomHashSet(NextTickListEntry.c);
   @Nullable
   private BiConsumer<LevelChunkTicks<T>, NextTickListEntry<T>> d;

   public LevelChunkTicks() {
   }

   public LevelChunkTicks(List<TickListChunk<T>> var0) {
      this.b = var0;

      for(TickListChunk<T> var2 : var0) {
         this.c.add(NextTickListEntry.a(var2.a(), var2.b()));
      }
   }

   public void a(@Nullable BiConsumer<LevelChunkTicks<T>, NextTickListEntry<T>> var0) {
      this.d = var0;
   }

   @Nullable
   public NextTickListEntry<T> b() {
      return this.a.peek();
   }

   @Nullable
   public NextTickListEntry<T> c() {
      NextTickListEntry<T> var0 = this.a.poll();
      if (var0 != null) {
         this.c.remove(var0);
      }

      return var0;
   }

   @Override
   public void a(NextTickListEntry<T> var0) {
      if (this.c.add(var0)) {
         this.b(var0);
      }
   }

   private void b(NextTickListEntry<T> var0) {
      this.a.add(var0);
      if (this.d != null) {
         this.d.accept(this, var0);
      }
   }

   @Override
   public boolean a(BlockPosition var0, T var1) {
      return this.c.contains(NextTickListEntry.a(var1, var0));
   }

   public void a(Predicate<NextTickListEntry<T>> var0) {
      Iterator<NextTickListEntry<T>> var1 = this.a.iterator();

      while(var1.hasNext()) {
         NextTickListEntry<T> var2 = var1.next();
         if (var0.test(var2)) {
            var1.remove();
            this.c.remove(var2);
         }
      }
   }

   public Stream<NextTickListEntry<T>> d() {
      return this.a.stream();
   }

   @Override
   public int a() {
      return this.a.size() + (this.b != null ? this.b.size() : 0);
   }

   public NBTTagList a(long var0, Function<T, String> var2) {
      NBTTagList var3 = new NBTTagList();
      if (this.b != null) {
         for(TickListChunk<T> var5 : this.b) {
            var3.add(var5.a(var2));
         }
      }

      for(NextTickListEntry<T> var5 : this.a) {
         var3.add(TickListChunk.a(var5, var2, var0));
      }

      return var3;
   }

   public void a(long var0) {
      if (this.b != null) {
         int var2 = -this.b.size();

         for(TickListChunk<T> var4 : this.b) {
            this.b(var4.a(var0, (long)(var2++)));
         }
      }

      this.b = null;
   }

   public static <T> LevelChunkTicks<T> a(NBTTagList var0, Function<String, Optional<T>> var1, ChunkCoordIntPair var2) {
      Builder<TickListChunk<T>> var3 = ImmutableList.builder();
      TickListChunk.a(var0, var1, var2, var3::add);
      return new LevelChunkTicks<>(var3.build());
   }
}
