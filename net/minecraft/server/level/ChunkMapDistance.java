package net.minecraft.server.level;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntMaps;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.core.SectionPosition;
import net.minecraft.util.ArraySetSorted;
import net.minecraft.util.thread.Mailbox;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.chunk.Chunk;
import net.minecraft.world.level.chunk.ChunkStatus;
import org.slf4j.Logger;

public abstract class ChunkMapDistance {
   static final Logger a = LogUtils.getLogger();
   private static final int b = 2;
   static final int c = 33 + ChunkStatus.a(ChunkStatus.o) - 2;
   private static final int d = 4;
   private static final int e = 32;
   private static final int f = 33;
   final Long2ObjectMap<ObjectSet<EntityPlayer>> g = new Long2ObjectOpenHashMap();
   public final Long2ObjectOpenHashMap<ArraySetSorted<Ticket<?>>> h = new Long2ObjectOpenHashMap();
   private final ChunkMapDistance.a i = new ChunkMapDistance.a();
   private final ChunkMapDistance.b j = new ChunkMapDistance.b(8);
   private final TickingTracker k = new TickingTracker();
   private final ChunkMapDistance.c l = new ChunkMapDistance.c(33);
   final Set<PlayerChunk> m = Sets.newHashSet();
   final ChunkTaskQueueSorter n;
   final Mailbox<ChunkTaskQueueSorter.a<Runnable>> o;
   final Mailbox<ChunkTaskQueueSorter.b> p;
   final LongSet q = new LongOpenHashSet();
   final Executor r;
   private long s;
   private int t = 10;

   protected ChunkMapDistance(Executor executor, Executor executor1) {
      Mailbox<Runnable> mailbox = Mailbox.a("player ticket throttler", executor1::execute);
      ChunkTaskQueueSorter chunktaskqueuesorter = new ChunkTaskQueueSorter(ImmutableList.of(mailbox), executor, 4);
      this.n = chunktaskqueuesorter;
      this.o = chunktaskqueuesorter.a(mailbox, true);
      this.p = chunktaskqueuesorter.a(mailbox);
      this.r = executor1;
   }

   protected void a() {
      ++this.s;
      ObjectIterator objectiterator = this.h.long2ObjectEntrySet().fastIterator();

      while(objectiterator.hasNext()) {
         Entry<ArraySetSorted<Ticket<?>>> entry = (Entry)objectiterator.next();
         Iterator<Ticket<?>> iterator = ((ArraySetSorted)entry.getValue()).iterator();
         boolean flag = false;

         while(iterator.hasNext()) {
            Ticket<?> ticket = iterator.next();
            if (ticket.b(this.s)) {
               iterator.remove();
               flag = true;
               this.k.b(entry.getLongKey(), ticket);
            }
         }

         if (flag) {
            this.i.b(entry.getLongKey(), a((ArraySetSorted<Ticket<?>>)entry.getValue()), false);
         }

         if (((ArraySetSorted)entry.getValue()).isEmpty()) {
            objectiterator.remove();
         }
      }
   }

   private static int a(ArraySetSorted<Ticket<?>> arraysetsorted) {
      return !arraysetsorted.isEmpty() ? arraysetsorted.b().b() : PlayerChunkMap.b + 1;
   }

   protected abstract boolean a(long var1);

   @Nullable
   protected abstract PlayerChunk b(long var1);

   @Nullable
   protected abstract PlayerChunk a(long var1, int var3, @Nullable PlayerChunk var4, int var5);

   public boolean a(PlayerChunkMap playerchunkmap) {
      this.j.a();
      this.k.a();
      this.l.a();
      int i = Integer.MAX_VALUE - this.i.a(Integer.MAX_VALUE);
      boolean flag = i != 0;
      if (flag) {
      }

      if (!this.m.isEmpty()) {
         Iterator<PlayerChunk> iter = this.m.iterator();
         int expectedSize = this.m.size();

         do {
            PlayerChunk playerchunk = iter.next();
            iter.remove();
            --expectedSize;
            playerchunk.a(playerchunkmap, this.r);
            if (this.m.size() != expectedSize) {
               expectedSize = this.m.size();
               iter = this.m.iterator();
            }
         } while(iter.hasNext());

         return true;
      } else {
         if (!this.q.isEmpty()) {
            LongIterator longiterator = this.q.iterator();

            while(longiterator.hasNext()) {
               long j = longiterator.nextLong();
               if (this.g(j).stream().anyMatch(ticket -> ticket.a() == TicketType.c)) {
                  PlayerChunk playerchunk = playerchunkmap.a(j);
                  if (playerchunk == null) {
                     throw new IllegalStateException();
                  }

                  CompletableFuture<Either<Chunk, PlayerChunk.Failure>> completablefuture = playerchunk.b();
                  completablefuture.thenAccept(either -> this.r.execute(() -> this.p.a(ChunkTaskQueueSorter.a(() -> {
                        }, j, false))));
               }
            }

            this.q.clear();
         }

         return flag;
      }
   }

   boolean addTicket(long i, Ticket<?> ticket) {
      ArraySetSorted<Ticket<?>> arraysetsorted = this.g(i);
      int j = a(arraysetsorted);
      Ticket<?> ticket1 = arraysetsorted.a(ticket);
      ticket1.a(this.s);
      if (ticket.b() < j) {
         this.i.b(i, ticket.b(), true);
      }

      return ticket == ticket1;
   }

   boolean removeTicket(long i, Ticket<?> ticket) {
      ArraySetSorted<Ticket<?>> arraysetsorted = this.g(i);
      boolean removed = false;
      if (arraysetsorted.remove(ticket)) {
         removed = true;
      }

      if (arraysetsorted.isEmpty()) {
         this.h.remove(i);
      }

      this.i.b(i, a(arraysetsorted), false);
      return removed;
   }

   public <T> void a(TicketType<T> tickettype, ChunkCoordIntPair chunkcoordintpair, int i, T t0) {
      this.addTicket(chunkcoordintpair.a(), new Ticket<>(tickettype, i, t0));
   }

   public <T> void b(TicketType<T> tickettype, ChunkCoordIntPair chunkcoordintpair, int i, T t0) {
      Ticket<T> ticket = new Ticket<>(tickettype, i, t0);
      this.removeTicket(chunkcoordintpair.a(), ticket);
   }

   public <T> void c(TicketType<T> tickettype, ChunkCoordIntPair chunkcoordintpair, int i, T t0) {
      this.addRegionTicketAtDistance(tickettype, chunkcoordintpair, i, t0);
   }

   public <T> boolean addRegionTicketAtDistance(TicketType<T> tickettype, ChunkCoordIntPair chunkcoordintpair, int i, T t0) {
      Ticket<T> ticket = new Ticket<>(tickettype, 33 - i, t0);
      long j = chunkcoordintpair.a();
      boolean added = this.addTicket(j, ticket);
      this.k.a(j, ticket);
      return added;
   }

   public <T> void d(TicketType<T> tickettype, ChunkCoordIntPair chunkcoordintpair, int i, T t0) {
      this.removeRegionTicketAtDistance(tickettype, chunkcoordintpair, i, t0);
   }

   public <T> boolean removeRegionTicketAtDistance(TicketType<T> tickettype, ChunkCoordIntPair chunkcoordintpair, int i, T t0) {
      Ticket<T> ticket = new Ticket<>(tickettype, 33 - i, t0);
      long j = chunkcoordintpair.a();
      boolean removed = this.removeTicket(j, ticket);
      this.k.b(j, ticket);
      return removed;
   }

   private ArraySetSorted<Ticket<?>> g(long i) {
      return (ArraySetSorted<Ticket<?>>)this.h.computeIfAbsent(i, j -> ArraySetSorted.a(4));
   }

   protected void a(ChunkCoordIntPair chunkcoordintpair, boolean flag) {
      Ticket<ChunkCoordIntPair> ticket = new Ticket<>(TicketType.d, 31, chunkcoordintpair);
      long i = chunkcoordintpair.a();
      if (flag) {
         this.addTicket(i, ticket);
         this.k.a(i, ticket);
      } else {
         this.removeTicket(i, ticket);
         this.k.b(i, ticket);
      }
   }

   public void a(SectionPosition sectionposition, EntityPlayer entityplayer) {
      ChunkCoordIntPair chunkcoordintpair = sectionposition.r();
      long i = chunkcoordintpair.a();
      ((ObjectSet)this.g.computeIfAbsent(i, j -> new ObjectOpenHashSet())).add(entityplayer);
      this.j.b(i, 0, true);
      this.l.b(i, 0, true);
      this.k.a(TicketType.c, chunkcoordintpair, this.g(), chunkcoordintpair);
   }

   public void b(SectionPosition sectionposition, EntityPlayer entityplayer) {
      ChunkCoordIntPair chunkcoordintpair = sectionposition.r();
      long i = chunkcoordintpair.a();
      ObjectSet<EntityPlayer> objectset = (ObjectSet)this.g.get(i);
      if (objectset != null) {
         objectset.remove(entityplayer);
         if (objectset.isEmpty()) {
            this.g.remove(i);
            this.j.b(i, Integer.MAX_VALUE, false);
            this.l.b(i, Integer.MAX_VALUE, false);
            this.k.b(TicketType.c, chunkcoordintpair, this.g(), chunkcoordintpair);
         }
      }
   }

   private int g() {
      return Math.max(0, 31 - this.t);
   }

   public boolean c(long i) {
      return this.k.c(i) < 32;
   }

   public boolean d(long i) {
      return this.k.c(i) < 33;
   }

   protected String e(long i) {
      ArraySetSorted<Ticket<?>> arraysetsorted = (ArraySetSorted)this.h.get(i);
      return arraysetsorted != null && !arraysetsorted.isEmpty() ? arraysetsorted.b().toString() : "no_ticket";
   }

   protected void a(int i) {
      this.l.a(i);
   }

   public void b(int i) {
      if (i != this.t) {
         this.t = i;
         this.k.a(this.g());
      }
   }

   public int b() {
      this.j.a();
      return this.j.a.size();
   }

   public boolean f(long i) {
      this.j.a();
      return this.j.a.containsKey(i);
   }

   public String c() {
      return this.n.b();
   }

   private void a(String s) {
      try (FileOutputStream fileoutputstream = new FileOutputStream(new File(s))) {
         ObjectIterator objectiterator = this.h.long2ObjectEntrySet().iterator();

         while(objectiterator.hasNext()) {
            Entry<ArraySetSorted<Ticket<?>>> entry = (Entry)objectiterator.next();
            ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(entry.getLongKey());

            for(Ticket<?> ticket : (ArraySetSorted)entry.getValue()) {
               fileoutputstream.write(
                  (chunkcoordintpair.e + "\t" + chunkcoordintpair.f + "\t" + ticket.a() + "\t" + ticket.b() + "\t\n").getBytes(StandardCharsets.UTF_8)
               );
            }
         }
      } catch (IOException var10) {
         a.error("Failed to dump tickets to {}", s, var10);
      }
   }

   @VisibleForTesting
   TickingTracker d() {
      return this.k;
   }

   public void e() {
      ImmutableSet<TicketType<?>> immutableset = ImmutableSet.of(TicketType.h, TicketType.g, TicketType.e);
      ObjectIterator objectiterator = this.h.long2ObjectEntrySet().fastIterator();

      while(objectiterator.hasNext()) {
         Entry<ArraySetSorted<Ticket<?>>> entry = (Entry)objectiterator.next();
         Iterator<Ticket<?>> iterator = ((ArraySetSorted)entry.getValue()).iterator();
         boolean flag = false;

         while(iterator.hasNext()) {
            Ticket<?> ticket = iterator.next();
            if (!immutableset.contains(ticket.a())) {
               iterator.remove();
               flag = true;
               this.k.b(entry.getLongKey(), ticket);
            }
         }

         if (flag) {
            this.i.b(entry.getLongKey(), a((ArraySetSorted<Ticket<?>>)entry.getValue()), false);
         }

         if (((ArraySetSorted)entry.getValue()).isEmpty()) {
            objectiterator.remove();
         }
      }
   }

   public boolean f() {
      return !this.h.isEmpty();
   }

   public <T> void removeAllTicketsFor(TicketType<T> ticketType, int ticketLevel, T ticketIdentifier) {
      Ticket<T> target = new Ticket<>(ticketType, ticketLevel, ticketIdentifier);
      Iterator<Entry<ArraySetSorted<Ticket<?>>>> iterator = this.h.long2ObjectEntrySet().fastIterator();

      while(iterator.hasNext()) {
         Entry<ArraySetSorted<Ticket<?>>> entry = (Entry)iterator.next();
         ArraySetSorted<Ticket<?>> tickets = (ArraySetSorted)entry.getValue();
         if (tickets.remove(target)) {
            this.i.b(entry.getLongKey(), a(tickets), false);
            if (tickets.isEmpty()) {
               iterator.remove();
            }
         }
      }
   }

   private class a extends ChunkMap {
      public a() {
         super(PlayerChunkMap.b + 2, 16, 256);
      }

      @Override
      protected int b(long i) {
         ArraySetSorted<Ticket<?>> arraysetsorted = (ArraySetSorted)ChunkMapDistance.this.h.get(i);
         return arraysetsorted == null ? Integer.MAX_VALUE : (arraysetsorted.isEmpty() ? Integer.MAX_VALUE : arraysetsorted.b().b());
      }

      @Override
      protected int c(long i) {
         if (!ChunkMapDistance.this.a(i)) {
            PlayerChunk playerchunk = ChunkMapDistance.this.b(i);
            if (playerchunk != null) {
               return playerchunk.k();
            }
         }

         return PlayerChunkMap.b + 1;
      }

      @Override
      protected void a(long i, int j) {
         PlayerChunk playerchunk = ChunkMapDistance.this.b(i);
         int k = playerchunk == null ? PlayerChunkMap.b + 1 : playerchunk.k();
         if (k != j) {
            playerchunk = ChunkMapDistance.this.a(i, j, playerchunk, k);
            if (playerchunk != null) {
               ChunkMapDistance.this.m.add(playerchunk);
            }
         }
      }

      public int a(int i) {
         return this.b(i);
      }
   }

   private class b extends ChunkMap {
      protected final Long2ByteMap a = new Long2ByteOpenHashMap();
      protected final int b;

      protected b(int i) {
         super(i + 2, 16, 256);
         this.b = i;
         this.a.defaultReturnValue((byte)(i + 2));
      }

      @Override
      protected int c(long i) {
         return this.a.get(i);
      }

      @Override
      protected void a(long i, int j) {
         byte b0;
         if (j > this.b) {
            b0 = this.a.remove(i);
         } else {
            b0 = this.a.put(i, (byte)j);
         }

         this.a(i, b0, j);
      }

      @Override
      protected void a(long i, int j, int k) {
      }

      @Override
      protected int b(long i) {
         return this.d(i) ? 0 : Integer.MAX_VALUE;
      }

      private boolean d(long i) {
         ObjectSet<EntityPlayer> objectset = (ObjectSet)ChunkMapDistance.this.g.get(i);
         return objectset != null && !objectset.isEmpty();
      }

      public void a() {
         this.b(Integer.MAX_VALUE);
      }

      private void a(String s) {
         try (FileOutputStream fileoutputstream = new FileOutputStream(new File(s))) {
            ObjectIterator objectiterator = this.a.long2ByteEntrySet().iterator();

            while(objectiterator.hasNext()) {
               it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry it_unimi_dsi_fastutil_longs_long2bytemap_entry = (it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry)objectiterator.next(
                  
               );
               ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(it_unimi_dsi_fastutil_longs_long2bytemap_entry.getLongKey());
               String s1 = Byte.toString(it_unimi_dsi_fastutil_longs_long2bytemap_entry.getByteValue());
               fileoutputstream.write((chunkcoordintpair.e + "\t" + chunkcoordintpair.f + "\t" + s1 + "\n").getBytes(StandardCharsets.UTF_8));
            }
         } catch (IOException var9) {
            ChunkMapDistance.a.error("Failed to dump chunks to {}", s, var9);
         }
      }
   }

   private class c extends ChunkMapDistance.b {
      private int e = 0;
      private final Long2IntMap f = Long2IntMaps.synchronize(new Long2IntOpenHashMap());
      private final LongSet g = new LongOpenHashSet();

      protected c(int i) {
         super(i);
         this.f.defaultReturnValue(i + 2);
      }

      @Override
      protected void a(long i, int j, int k) {
         this.g.add(i);
      }

      @Override
      public void a(int i) {
         ObjectIterator objectiterator = this.a.long2ByteEntrySet().iterator();

         while(objectiterator.hasNext()) {
            it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry it_unimi_dsi_fastutil_longs_long2bytemap_entry = (it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry)objectiterator.next(
               
            );
            byte b0 = it_unimi_dsi_fastutil_longs_long2bytemap_entry.getByteValue();
            long j = it_unimi_dsi_fastutil_longs_long2bytemap_entry.getLongKey();
            this.a(j, b0, this.c(b0), b0 <= i - 2);
         }

         this.e = i;
      }

      private void a(long i, int j, boolean flag, boolean flag1) {
         if (flag != flag1) {
            Ticket<?> ticket = new Ticket<>(TicketType.c, ChunkMapDistance.c, new ChunkCoordIntPair(i));
            if (flag1) {
               ChunkMapDistance.this.o.a(ChunkTaskQueueSorter.a(() -> ChunkMapDistance.this.r.execute(() -> {
                     if (this.c(this.c(i))) {
                        ChunkMapDistance.this.addTicket(i, ticket);
                        ChunkMapDistance.this.q.add(i);
                     } else {
                        ChunkMapDistance.this.p.a(ChunkTaskQueueSorter.a(() -> {
                        }, i, false));
                     }
                  }), i, () -> j));
            } else {
               ChunkMapDistance.this.p
                  .a(ChunkTaskQueueSorter.a(() -> ChunkMapDistance.this.r.execute(() -> ChunkMapDistance.this.removeTicket(i, ticket)), i, true));
            }
         }
      }

      @Override
      public void a() {
         super.a();
         if (!this.g.isEmpty()) {
            LongIterator longiterator = this.g.iterator();

            while(longiterator.hasNext()) {
               long i = longiterator.nextLong();
               int j = this.f.get(i);
               int k = this.c(i);
               if (j != k) {
                  ChunkMapDistance.this.n.onLevelChange(new ChunkCoordIntPair(i), () -> this.f.get(i), k, l -> {
                     if (l >= this.f.defaultReturnValue()) {
                        this.f.remove(i);
                     } else {
                        this.f.put(i, l);
                     }
                  });
                  this.a(i, k, this.c(j), this.c(k));
               }
            }

            this.g.clear();
         }
      }

      private boolean c(int i) {
         return i <= this.e - 2;
      }
   }
}
