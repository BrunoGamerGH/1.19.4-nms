package net.minecraft.util.profiling.jfr.stats;

import jdk.jfr.consumer.RecordedEvent;

public record CpuLoadStat(double jvm, double userJvm, double system) {
   private final double a;
   private final double b;
   private final double c;

   public CpuLoadStat(double var0, double var2, double var4) {
      this.a = var0;
      this.b = var2;
      this.c = var4;
   }

   public static CpuLoadStat a(RecordedEvent var0) {
      return new CpuLoadStat((double)var0.getFloat("jvmSystem"), (double)var0.getFloat("jvmUser"), (double)var0.getFloat("machineTotal"));
   }
}
