package net.minecraft.util.profiling;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.util.profiling.metrics.MetricCategory;
import org.apache.commons.lang3.tuple.Pair;

public class GameProfilerDisabled implements GameProfilerFillerActive {
   public static final GameProfilerDisabled a = new GameProfilerDisabled();

   private GameProfilerDisabled() {
   }

   @Override
   public void a() {
   }

   @Override
   public void b() {
   }

   @Override
   public void a(String var0) {
   }

   @Override
   public void a(Supplier<String> var0) {
   }

   @Override
   public void a(MetricCategory var0) {
   }

   @Override
   public void c() {
   }

   @Override
   public void b(String var0) {
   }

   @Override
   public void b(Supplier<String> var0) {
   }

   @Override
   public void a(String var0, int var1) {
   }

   @Override
   public void a(Supplier<String> var0, int var1) {
   }

   @Override
   public MethodProfilerResults d() {
      return MethodProfilerResultsEmpty.a;
   }

   @Nullable
   @Override
   public MethodProfiler.a c(String var0) {
      return null;
   }

   @Override
   public Set<Pair<String, MetricCategory>> e() {
      return ImmutableSet.of();
   }
}
