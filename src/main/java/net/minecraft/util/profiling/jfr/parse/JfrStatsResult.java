package net.minecraft.util.profiling.jfr.parse;

import com.mojang.datafixers.util.Pair;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.profiling.jfr.serialize.JfrResultJsonSerializer;
import net.minecraft.util.profiling.jfr.stats.ChunkGenStat;
import net.minecraft.util.profiling.jfr.stats.CpuLoadStat;
import net.minecraft.util.profiling.jfr.stats.FileIOStat;
import net.minecraft.util.profiling.jfr.stats.GcHeapStat;
import net.minecraft.util.profiling.jfr.stats.NetworkPacketSummary;
import net.minecraft.util.profiling.jfr.stats.ThreadAllocationStat;
import net.minecraft.util.profiling.jfr.stats.TickTimeStat;
import net.minecraft.util.profiling.jfr.stats.TimedStatSummary;
import net.minecraft.world.level.chunk.ChunkStatus;

public record JfrStatsResult(
   Instant recordingStarted,
   Instant recordingEnded,
   Duration recordingDuration,
   @Nullable Duration worldCreationDuration,
   List<TickTimeStat> tickTimes,
   List<CpuLoadStat> cpuLoadStats,
   GcHeapStat.a heapSummary,
   ThreadAllocationStat.a threadAllocationSummary,
   NetworkPacketSummary receivedPacketsSummary,
   NetworkPacketSummary sentPacketsSummary,
   FileIOStat.a fileWrites,
   FileIOStat.a fileReads,
   List<ChunkGenStat> chunkGenStats
) {
   private final Instant a;
   private final Instant b;
   private final Duration c;
   @Nullable
   private final Duration d;
   private final List<TickTimeStat> e;
   private final List<CpuLoadStat> f;
   private final GcHeapStat.a g;
   private final ThreadAllocationStat.a h;
   private final NetworkPacketSummary i;
   private final NetworkPacketSummary j;
   private final FileIOStat.a k;
   private final FileIOStat.a l;
   private final List<ChunkGenStat> m;

   public JfrStatsResult(
      Instant var0,
      Instant var1,
      Duration var2,
      @Nullable Duration var3,
      List<TickTimeStat> var4,
      List<CpuLoadStat> var5,
      GcHeapStat.a var6,
      ThreadAllocationStat.a var7,
      NetworkPacketSummary var8,
      NetworkPacketSummary var9,
      FileIOStat.a var10,
      FileIOStat.a var11,
      List<ChunkGenStat> var12
   ) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
      this.g = var6;
      this.h = var7;
      this.i = var8;
      this.j = var9;
      this.k = var10;
      this.l = var11;
      this.m = var12;
   }

   public List<Pair<ChunkStatus, TimedStatSummary<ChunkGenStat>>> a() {
      Map<ChunkStatus, List<ChunkGenStat>> var0 = this.m.stream().collect(Collectors.groupingBy(ChunkGenStat::d));
      return var0.entrySet()
         .stream()
         .map(var0x -> Pair.of((ChunkStatus)var0x.getKey(), TimedStatSummary.a((List)var0x.getValue())))
         .sorted(Comparator.comparing(var0x -> ((TimedStatSummary)var0x.getSecond()).f()).reversed())
         .toList();
   }

   public String b() {
      return new JfrResultJsonSerializer().a(this);
   }

   public Instant c() {
      return this.a;
   }

   public Instant d() {
      return this.b;
   }

   public Duration e() {
      return this.c;
   }

   @Nullable
   public Duration f() {
      return this.d;
   }

   public List<TickTimeStat> g() {
      return this.e;
   }

   public List<CpuLoadStat> h() {
      return this.f;
   }

   public GcHeapStat.a i() {
      return this.g;
   }

   public ThreadAllocationStat.a j() {
      return this.h;
   }

   public NetworkPacketSummary k() {
      return this.i;
   }

   public NetworkPacketSummary l() {
      return this.j;
   }

   public FileIOStat.a m() {
      return this.k;
   }

   public FileIOStat.a n() {
      return this.l;
   }

   public List<ChunkGenStat> o() {
      return this.m;
   }
}
