package net.minecraft.util.profiling.jfr.stats;

import java.time.Duration;
import java.time.Instant;
import jdk.jfr.consumer.RecordedEvent;

public record TickTimeStat(Instant timestamp, Duration currentAverage) {
   private final Instant a;
   private final Duration b;

   public TickTimeStat(Instant var0, Duration var1) {
      this.a = var0;
      this.b = var1;
   }

   public static TickTimeStat a(RecordedEvent var0) {
      return new TickTimeStat(var0.getStartTime(), var0.getDuration("averageTickDuration"));
   }
}
