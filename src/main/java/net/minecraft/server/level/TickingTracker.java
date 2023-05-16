package net.minecraft.server.level;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.ArraySetSorted;
import net.minecraft.world.level.ChunkCoordIntPair;

public class TickingTracker extends ChunkMap {
   private static final int b = 4;
   protected final Long2ByteMap a = new Long2ByteOpenHashMap();
   private final Long2ObjectOpenHashMap<ArraySetSorted<Ticket<?>>> c = new Long2ObjectOpenHashMap();

   public TickingTracker() {
      super(34, 16, 256);
      this.a.defaultReturnValue((byte)33);
   }

   private ArraySetSorted<Ticket<?>> g(long var0) {
      return (ArraySetSorted<Ticket<?>>)this.c.computeIfAbsent(var0, var0x -> ArraySetSorted.a(4));
   }

   private int a(ArraySetSorted<Ticket<?>> var0) {
      return var0.isEmpty() ? 34 : var0.b().b();
   }

   public void a(long var0, Ticket<?> var2) {
      ArraySetSorted<Ticket<?>> var3 = this.g(var0);
      int var4 = this.a(var3);
      var3.add(var2);
      if (var2.b() < var4) {
         this.b(var0, var2.b(), true);
      }
   }

   public void b(long var0, Ticket<?> var2) {
      ArraySetSorted<Ticket<?>> var3 = this.g(var0);
      var3.remove(var2);
      if (var3.isEmpty()) {
         this.c.remove(var0);
      }

      this.b(var0, this.a(var3), false);
   }

   public <T> void a(TicketType<T> var0, ChunkCoordIntPair var1, int var2, T var3) {
      this.a(var1.a(), new Ticket<>(var0, var2, var3));
   }

   public <T> void b(TicketType<T> var0, ChunkCoordIntPair var1, int var2, T var3) {
      Ticket<T> var4 = new Ticket<>(var0, var2, var3);
      this.b(var1.a(), var4);
   }

   @Override
   public void a(int var0) {
      List<Pair<Ticket<ChunkCoordIntPair>, Long>> var1 = new ArrayList();
      ObjectIterator var3 = this.c.long2ObjectEntrySet().iterator();

      while(var3.hasNext()) {
         Entry<ArraySetSorted<Ticket<?>>> var3x = (Entry)var3.next();

         for(Ticket<?> var5 : (ArraySetSorted)var3x.getValue()) {
            if (var5.a() == TicketType.c) {
               var1.add(Pair.of(var5, var3x.getLongKey()));
            }
         }
      }

      for(Pair<Ticket<ChunkCoordIntPair>, Long> var3 : var1) {
         Long var4 = (Long)var3.getSecond();
         Ticket<ChunkCoordIntPair> var5 = (Ticket)var3.getFirst();
         this.b(var4, var5);
         ChunkCoordIntPair var6 = new ChunkCoordIntPair(var4);
         TicketType<ChunkCoordIntPair> var7 = var5.a();
         this.a(var7, var6, var0, var6);
      }
   }

   @Override
   protected int b(long var0) {
      ArraySetSorted<Ticket<?>> var2 = (ArraySetSorted)this.c.get(var0);
      return var2 != null && !var2.isEmpty() ? var2.b().b() : Integer.MAX_VALUE;
   }

   public int a(ChunkCoordIntPair var0) {
      return this.c(var0.a());
   }

   @Override
   protected int c(long var0) {
      return this.a.get(var0);
   }

   @Override
   protected void a(long var0, int var2) {
      if (var2 > 33) {
         this.a.remove(var0);
      } else {
         this.a.put(var0, (byte)var2);
      }
   }

   public void a() {
      this.b(Integer.MAX_VALUE);
   }

   public String d(long var0) {
      ArraySetSorted<Ticket<?>> var2 = (ArraySetSorted)this.c.get(var0);
      return var2 != null && !var2.isEmpty() ? var2.b().toString() : "no_ticket";
   }
}
