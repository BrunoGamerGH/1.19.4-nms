package net.minecraft.util.profiling.metrics.storage;

import java.time.Instant;
import net.minecraft.util.profiling.MethodProfilerResults;

public final class RecordedDeviation {
   public final Instant a;
   public final int b;
   public final MethodProfilerResults c;

   public RecordedDeviation(Instant var0, int var1, MethodProfilerResults var2) {
      this.a = var0;
      this.b = var1;
      this.c = var2;
   }
}
