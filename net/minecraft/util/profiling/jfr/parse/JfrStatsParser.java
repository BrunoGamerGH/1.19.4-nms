package net.minecraft.util.profiling.jfr.parse;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;
import net.minecraft.util.profiling.jfr.stats.ChunkGenStat;
import net.minecraft.util.profiling.jfr.stats.CpuLoadStat;
import net.minecraft.util.profiling.jfr.stats.FileIOStat;
import net.minecraft.util.profiling.jfr.stats.GcHeapStat;
import net.minecraft.util.profiling.jfr.stats.NetworkPacketSummary;
import net.minecraft.util.profiling.jfr.stats.ThreadAllocationStat;
import net.minecraft.util.profiling.jfr.stats.TickTimeStat;

public class JfrStatsParser {
   private Instant a = Instant.EPOCH;
   private Instant b = Instant.EPOCH;
   private final List<ChunkGenStat> c = Lists.newArrayList();
   private final List<CpuLoadStat> d = Lists.newArrayList();
   private final Map<NetworkPacketSummary.b, JfrStatsParser.a> e = Maps.newHashMap();
   private final Map<NetworkPacketSummary.b, JfrStatsParser.a> f = Maps.newHashMap();
   private final List<FileIOStat> g = Lists.newArrayList();
   private final List<FileIOStat> h = Lists.newArrayList();
   private int i;
   private Duration j = Duration.ZERO;
   private final List<GcHeapStat> k = Lists.newArrayList();
   private final List<ThreadAllocationStat> l = Lists.newArrayList();
   private final List<TickTimeStat> m = Lists.newArrayList();
   @Nullable
   private Duration n = null;

   private JfrStatsParser(Stream<RecordedEvent> var0) {
      this.a(var0);
   }

   public static JfrStatsResult a(Path var0) {
      try {
         JfrStatsResult var4;
         try (final RecordingFile var1 = new RecordingFile(var0)) {
            Iterator<RecordedEvent> var2 = new Iterator<RecordedEvent>() {
               @Override
               public boolean hasNext() {
                  return var1.hasMoreEvents();
               }

               public RecordedEvent a() {
                  if (!this.hasNext()) {
                     throw new NoSuchElementException();
                  } else {
                     try {
                        return var1.readEvent();
                     } catch (IOException var2) {
                        throw new UncheckedIOException(var2);
                     }
                  }
               }
            };
            Stream<RecordedEvent> var3 = StreamSupport.stream(Spliterators.spliteratorUnknownSize(var2, 1297), false);
            var4 = new JfrStatsParser(var3).a();
         }

         return var4;
      } catch (IOException var7) {
         throw new UncheckedIOException(var7);
      }
   }

   private JfrStatsResult a() {
      Duration var0 = Duration.between(this.a, this.b);
      return new JfrStatsResult(
         this.a,
         this.b,
         var0,
         this.n,
         this.m,
         this.d,
         GcHeapStat.a(var0, this.k, this.j, this.i),
         ThreadAllocationStat.a(this.l),
         a(var0, this.e),
         a(var0, this.f),
         FileIOStat.a(var0, this.g),
         FileIOStat.a(var0, this.h),
         this.c
      );
   }

   private void a(Stream<RecordedEvent> var0) {
      var0.forEach(var0x -> {
         if (var0x.getEndTime().isAfter(this.b) || this.b.equals(Instant.EPOCH)) {
            this.b = var0x.getEndTime();
         }

         if (var0x.getStartTime().isBefore(this.a) || this.a.equals(Instant.EPOCH)) {
            this.a = var0x.getStartTime();
         }

         String var2 = var0x.getEventType().getName();
         switch(var2) {
            case "minecraft.ChunkGeneration":
               this.c.add(ChunkGenStat.a(var0x));
               break;
            case "minecraft.LoadWorld":
               this.n = var0x.getDuration();
               break;
            case "minecraft.ServerTickTime":
               this.m.add(TickTimeStat.a(var0x));
               break;
            case "minecraft.PacketReceived":
               this.a(var0x, var0x.getInt("bytes"), this.e);
               break;
            case "minecraft.PacketSent":
               this.a(var0x, var0x.getInt("bytes"), this.f);
               break;
            case "jdk.ThreadAllocationStatistics":
               this.l.add(ThreadAllocationStat.a(var0x));
               break;
            case "jdk.GCHeapSummary":
               this.k.add(GcHeapStat.a(var0x));
               break;
            case "jdk.CPULoad":
               this.d.add(CpuLoadStat.a(var0x));
               break;
            case "jdk.FileWrite":
               this.a(var0x, this.g, "bytesWritten");
               break;
            case "jdk.FileRead":
               this.a(var0x, this.h, "bytesRead");
               break;
            case "jdk.GarbageCollection":
               ++this.i;
               this.j = this.j.plus(var0x.getDuration());
         }
      });
   }

   private void a(RecordedEvent var0, int var1, Map<NetworkPacketSummary.b, JfrStatsParser.a> var2) {
      var2.computeIfAbsent(NetworkPacketSummary.b.a(var0), var0x -> new JfrStatsParser.a()).a(var1);
   }

   private void a(RecordedEvent var0, List<FileIOStat> var1, String var2) {
      var1.add(new FileIOStat(var0.getDuration(), var0.getString("path"), var0.getLong(var2)));
   }

   private static NetworkPacketSummary a(Duration var0, Map<NetworkPacketSummary.b, JfrStatsParser.a> var1) {
      List<Pair<NetworkPacketSummary.b, NetworkPacketSummary.a>> var2 = var1.entrySet()
         .stream()
         .map(var0x -> Pair.of((NetworkPacketSummary.b)var0x.getKey(), ((JfrStatsParser.a)var0x.getValue()).a()))
         .toList();
      return new NetworkPacketSummary(var0, var2);
   }

   public static final class a {
      private long a;
      private long b;

      public void a(int var0) {
         this.b += (long)var0;
         ++this.a;
      }

      public NetworkPacketSummary.a a() {
         return new NetworkPacketSummary.a(this.a, this.b);
      }
   }
}
