package net.minecraft.util.profiling.metrics.profiling;

import net.minecraft.util.profiling.GameProfilerDisabled;
import net.minecraft.util.profiling.GameProfilerFiller;

public class InactiveMetricsRecorder implements MetricsRecorder {
   public static final MetricsRecorder a = new InactiveMetricsRecorder();

   @Override
   public void a() {
   }

   @Override
   public void b() {
   }

   @Override
   public void c() {
   }

   @Override
   public boolean e() {
      return false;
   }

   @Override
   public GameProfilerFiller f() {
      return GameProfilerDisabled.a;
   }

   @Override
   public void d() {
   }
}
