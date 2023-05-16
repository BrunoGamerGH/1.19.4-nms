package net.minecraft.util.profiling;

import com.mojang.logging.LogUtils;
import java.io.File;
import java.util.function.LongSupplier;
import javax.annotation.Nullable;
import net.minecraft.SystemUtils;
import org.slf4j.Logger;

public class GameProfilerTick {
   private static final Logger a = LogUtils.getLogger();
   private final LongSupplier b;
   private final long c;
   private int d;
   private final File e;
   private GameProfilerFillerActive f = GameProfilerDisabled.a;

   public GameProfilerTick(LongSupplier var0, String var1, long var2) {
      this.b = var0;
      this.e = new File("debug", var1);
      this.c = var2;
   }

   public GameProfilerFiller a() {
      this.f = new MethodProfiler(this.b, () -> this.d, false);
      ++this.d;
      return this.f;
   }

   public void b() {
      if (this.f != GameProfilerDisabled.a) {
         MethodProfilerResults var0 = this.f.d();
         this.f = GameProfilerDisabled.a;
         if (var0.g() >= this.c) {
            File var1 = new File(this.e, "tick-results-" + SystemUtils.e() + ".txt");
            var0.a(var1.toPath());
            a.info("Recorded long tick -- wrote info to: {}", var1.getAbsolutePath());
         }
      }
   }

   @Nullable
   public static GameProfilerTick a(String var0) {
      return null;
   }

   public static GameProfilerFiller a(GameProfilerFiller var0, @Nullable GameProfilerTick var1) {
      return var1 != null ? GameProfilerFiller.a(var1.a(), var0) : var0;
   }
}
