package net.minecraft.util.profiling;

import java.util.function.Supplier;
import net.minecraft.util.profiling.metrics.MetricCategory;

public interface GameProfilerFiller {
   String b = "root";

   void a();

   void b();

   void a(String var1);

   void a(Supplier<String> var1);

   void c();

   void b(String var1);

   void b(Supplier<String> var1);

   void a(MetricCategory var1);

   default void d(String var0) {
      this.a(var0, 1);
   }

   void a(String var1, int var2);

   default void c(Supplier<String> var0) {
      this.a(var0, 1);
   }

   void a(Supplier<String> var1, int var2);

   static GameProfilerFiller a(final GameProfilerFiller var0, final GameProfilerFiller var1) {
      if (var0 == GameProfilerDisabled.a) {
         return var1;
      } else {
         return var1 == GameProfilerDisabled.a ? var0 : new GameProfilerFiller() {
            @Override
            public void a() {
               var0.a();
               var1.a();
            }

            @Override
            public void b() {
               var0.b();
               var1.b();
            }

            @Override
            public void a(String var0x) {
               var0.a(var0);
               var1.a(var0);
            }

            @Override
            public void a(Supplier<String> var0x) {
               var0.a(var0);
               var1.a(var0);
            }

            @Override
            public void a(MetricCategory var0x) {
               var0.a(var0);
               var1.a(var0);
            }

            @Override
            public void c() {
               var0.c();
               var1.c();
            }

            @Override
            public void b(String var0x) {
               var0.b(var0);
               var1.b(var0);
            }

            @Override
            public void b(Supplier<String> var0x) {
               var0.b(var0);
               var1.b(var0);
            }

            @Override
            public void a(String var0x, int var1x) {
               var0.a(var0, var1);
               var1.a(var0, var1);
            }

            @Override
            public void a(Supplier<String> var0x, int var1x) {
               var0.a(var0, var1);
               var1.a(var0, var1);
            }
         };
      }
   }
}
