package net.minecraft.util.profiling.jfr.serialize;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.LongSerializationPolicy;
import com.mojang.datafixers.util.Pair;
import java.time.Duration;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.DoubleStream;
import net.minecraft.SystemUtils;
import net.minecraft.util.profiling.jfr.Percentiles;
import net.minecraft.util.profiling.jfr.parse.JfrStatsResult;
import net.minecraft.util.profiling.jfr.stats.ChunkGenStat;
import net.minecraft.util.profiling.jfr.stats.CpuLoadStat;
import net.minecraft.util.profiling.jfr.stats.FileIOStat;
import net.minecraft.util.profiling.jfr.stats.GcHeapStat;
import net.minecraft.util.profiling.jfr.stats.NetworkPacketSummary;
import net.minecraft.util.profiling.jfr.stats.ThreadAllocationStat;
import net.minecraft.util.profiling.jfr.stats.TickTimeStat;
import net.minecraft.util.profiling.jfr.stats.TimedStatSummary;
import net.minecraft.world.level.chunk.ChunkStatus;

public class JfrResultJsonSerializer {
   private static final String b = "bytesPerSecond";
   private static final String c = "count";
   private static final String d = "durationNanosTotal";
   private static final String e = "totalBytes";
   private static final String f = "countPerSecond";
   final Gson a = new GsonBuilder().setPrettyPrinting().setLongSerializationPolicy(LongSerializationPolicy.DEFAULT).create();

   public String a(JfrStatsResult var0) {
      JsonObject var1 = new JsonObject();
      var1.addProperty("startedEpoch", var0.c().toEpochMilli());
      var1.addProperty("endedEpoch", var0.d().toEpochMilli());
      var1.addProperty("durationMs", var0.e().toMillis());
      Duration var2 = var0.f();
      if (var2 != null) {
         var1.addProperty("worldGenDurationMs", var2.toMillis());
      }

      var1.add("heap", this.a(var0.i()));
      var1.add("cpuPercent", this.c(var0.h()));
      var1.add("network", this.c(var0));
      var1.add("fileIO", this.b(var0));
      var1.add("serverTick", this.b(var0.g()));
      var1.add("threadAllocation", this.a(var0.j()));
      var1.add("chunkGen", this.a(var0.a()));
      return this.a.toJson(var1);
   }

   private JsonElement a(GcHeapStat.a var0) {
      JsonObject var1 = new JsonObject();
      var1.addProperty("allocationRateBytesPerSecond", var0.e());
      var1.addProperty("gcCount", var0.d());
      var1.addProperty("gcOverHeadPercent", var0.a());
      var1.addProperty("gcTotalDurationMs", var0.c().toMillis());
      return var1;
   }

   private JsonElement a(List<Pair<ChunkStatus, TimedStatSummary<ChunkGenStat>>> var0) {
      JsonObject var1 = new JsonObject();
      var1.addProperty("durationNanosTotal", var0.stream().mapToDouble(var0x -> (double)((TimedStatSummary)var0x.getSecond()).f().toNanos()).sum());
      JsonArray var2 = SystemUtils.a(new JsonArray(), var1x -> var1.add("status", var1x));

      for(Pair<ChunkStatus, TimedStatSummary<ChunkGenStat>> var4 : var0) {
         TimedStatSummary<ChunkGenStat> var5 = (TimedStatSummary)var4.getSecond();
         JsonObject var6 = SystemUtils.a(new JsonObject(), var2::add);
         var6.addProperty("state", ((ChunkStatus)var4.getFirst()).d());
         var6.addProperty("count", var5.d());
         var6.addProperty("durationNanosTotal", var5.f().toNanos());
         var6.addProperty("durationNanosAvg", var5.f().toNanos() / (long)var5.d());
         JsonObject var7 = SystemUtils.a(new JsonObject(), var1x -> var6.add("durationNanosPercentiles", var1x));
         var5.e().forEach((var1x, var2x) -> var7.addProperty("p" + var1x, var2x));
         Function<ChunkGenStat, JsonElement> var8 = var0x -> {
            JsonObject var1x = new JsonObject();
            var1x.addProperty("durationNanos", var0x.a().toNanos());
            var1x.addProperty("level", var0x.e());
            var1x.addProperty("chunkPosX", var0x.b().e);
            var1x.addProperty("chunkPosZ", var0x.b().f);
            var1x.addProperty("worldPosX", var0x.c().c());
            var1x.addProperty("worldPosZ", var0x.c().d());
            return var1x;
         };
         var6.add("fastest", (JsonElement)var8.apply(var5.a()));
         var6.add("slowest", (JsonElement)var8.apply(var5.b()));
         var6.add("secondSlowest", (JsonElement)(var5.c() != null ? (JsonElement)var8.apply(var5.c()) : JsonNull.INSTANCE));
      }

      return var1;
   }

   private JsonElement a(ThreadAllocationStat.a var0) {
      JsonArray var1 = new JsonArray();
      var0.a().forEach((var1x, var2x) -> var1.add(SystemUtils.a(new JsonObject(), var2xx -> {
            var2xx.addProperty("thread", var1x);
            var2xx.addProperty("bytesPerSecond", var2x);
         })));
      return var1;
   }

   private JsonElement b(List<TickTimeStat> var0) {
      if (var0.isEmpty()) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject var1 = new JsonObject();
         double[] var2 = var0.stream().mapToDouble(var0x -> (double)var0x.b().toNanos() / 1000000.0).toArray();
         DoubleSummaryStatistics var3 = DoubleStream.of(var2).summaryStatistics();
         var1.addProperty("minMs", var3.getMin());
         var1.addProperty("averageMs", var3.getAverage());
         var1.addProperty("maxMs", var3.getMax());
         Map<Integer, Double> var4 = Percentiles.a(var2);
         var4.forEach((var1x, var2x) -> var1.addProperty("p" + var1x, var2x));
         return var1;
      }
   }

   private JsonElement b(JfrStatsResult var0) {
      JsonObject var1 = new JsonObject();
      var1.add("write", this.a(var0.m()));
      var1.add("read", this.a(var0.n()));
      return var1;
   }

   private JsonElement a(FileIOStat.a var0) {
      JsonObject var1 = new JsonObject();
      var1.addProperty("totalBytes", var0.a());
      var1.addProperty("count", var0.c());
      var1.addProperty("bytesPerSecond", var0.b());
      var1.addProperty("countPerSecond", var0.d());
      JsonArray var2 = new JsonArray();
      var1.add("topContributors", var2);
      var0.f().forEach(var1x -> {
         JsonObject var2x = new JsonObject();
         var2.add(var2x);
         var2x.addProperty("path", (String)var1x.getFirst());
         var2x.addProperty("totalBytes", (Number)var1x.getSecond());
      });
      return var1;
   }

   private JsonElement c(JfrStatsResult var0) {
      JsonObject var1 = new JsonObject();
      var1.add("sent", this.a(var0.l()));
      var1.add("received", this.a(var0.k()));
      return var1;
   }

   private JsonElement a(NetworkPacketSummary var0) {
      JsonObject var1 = new JsonObject();
      var1.addProperty("totalBytes", var0.d());
      var1.addProperty("count", var0.c());
      var1.addProperty("bytesPerSecond", var0.b());
      var1.addProperty("countPerSecond", var0.a());
      JsonArray var2 = new JsonArray();
      var1.add("topContributors", var2);
      var0.e().forEach(var1x -> {
         JsonObject var2x = new JsonObject();
         var2.add(var2x);
         NetworkPacketSummary.b var3x = (NetworkPacketSummary.b)var1x.getFirst();
         NetworkPacketSummary.a var4 = (NetworkPacketSummary.a)var1x.getSecond();
         var2x.addProperty("protocolId", var3x.c());
         var2x.addProperty("packetId", var3x.d());
         var2x.addProperty("packetName", var3x.a());
         var2x.addProperty("totalBytes", var4.b());
         var2x.addProperty("count", var4.a());
      });
      return var1;
   }

   private JsonElement c(List<CpuLoadStat> var0) {
      JsonObject var1 = new JsonObject();
      BiFunction<List<CpuLoadStat>, ToDoubleFunction<CpuLoadStat>, JsonObject> var2 = (var0x, var1x) -> {
         JsonObject var2x = new JsonObject();
         DoubleSummaryStatistics var3x = var0x.stream().mapToDouble(var1x).summaryStatistics();
         var2x.addProperty("min", var3x.getMin());
         var2x.addProperty("average", var3x.getAverage());
         var2x.addProperty("max", var3x.getMax());
         return var2x;
      };
      var1.add("jvm", (JsonElement)var2.apply(var0, CpuLoadStat::a));
      var1.add("userJvm", (JsonElement)var2.apply(var0, CpuLoadStat::b));
      var1.add("system", (JsonElement)var2.apply(var0, CpuLoadStat::c));
      return var1;
   }
}
