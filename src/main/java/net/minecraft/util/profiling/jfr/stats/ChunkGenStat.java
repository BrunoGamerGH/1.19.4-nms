package net.minecraft.util.profiling.jfr.stats;

import java.time.Duration;
import jdk.jfr.consumer.RecordedEvent;
import net.minecraft.server.level.BlockPosition2D;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.chunk.ChunkStatus;

public record ChunkGenStat(Duration duration, ChunkCoordIntPair chunkPos, BlockPosition2D worldPos, ChunkStatus status, String level) implements TimedStat {
   private final Duration a;
   private final ChunkCoordIntPair b;
   private final BlockPosition2D c;
   private final ChunkStatus d;
   private final String e;

   public ChunkGenStat(Duration var0, ChunkCoordIntPair var1, BlockPosition2D var2, ChunkStatus var3, String var4) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
   }

   public static ChunkGenStat a(RecordedEvent var0) {
      return new ChunkGenStat(
         var0.getDuration(),
         new ChunkCoordIntPair(var0.getInt("chunkPosX"), var0.getInt("chunkPosX")),
         new BlockPosition2D(var0.getInt("worldPosX"), var0.getInt("worldPosZ")),
         ChunkStatus.a(var0.getString("status")),
         var0.getString("level")
      );
   }
}
