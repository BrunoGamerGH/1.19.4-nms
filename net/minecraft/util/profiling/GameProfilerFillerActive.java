package net.minecraft.util.profiling;

import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.util.profiling.metrics.MetricCategory;
import org.apache.commons.lang3.tuple.Pair;

public interface GameProfilerFillerActive extends GameProfilerFiller {
   MethodProfilerResults d();

   @Nullable
   MethodProfiler.a c(String var1);

   Set<Pair<String, MetricCategory>> e();
}
