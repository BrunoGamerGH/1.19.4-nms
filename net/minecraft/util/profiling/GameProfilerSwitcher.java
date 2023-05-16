package net.minecraft.util.profiling;

import java.util.function.IntSupplier;
import java.util.function.LongSupplier;

public class GameProfilerSwitcher {
   private final LongSupplier a;
   private final IntSupplier b;
   private GameProfilerFillerActive c = GameProfilerDisabled.a;

   public GameProfilerSwitcher(LongSupplier var0, IntSupplier var1) {
      this.a = var0;
      this.b = var1;
   }

   public boolean a() {
      return this.c != GameProfilerDisabled.a;
   }

   public void b() {
      this.c = GameProfilerDisabled.a;
   }

   public void c() {
      this.c = new MethodProfiler(this.a, this.b, true);
   }

   public GameProfilerFiller d() {
      return this.c;
   }

   public MethodProfilerResults e() {
      return this.c.d();
   }
}
