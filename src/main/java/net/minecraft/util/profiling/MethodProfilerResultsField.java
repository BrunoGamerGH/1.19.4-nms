package net.minecraft.util.profiling;

public final class MethodProfilerResultsField implements Comparable<MethodProfilerResultsField> {
   public final double a;
   public final double b;
   public final long c;
   public final String d;

   public MethodProfilerResultsField(String var0, double var1, double var3, long var5) {
      this.d = var0;
      this.a = var1;
      this.b = var3;
      this.c = var5;
   }

   public int a(MethodProfilerResultsField var0) {
      if (var0.a < this.a) {
         return -1;
      } else {
         return var0.a > this.a ? 1 : var0.d.compareTo(this.d);
      }
   }

   public int a() {
      return (this.d.hashCode() & 11184810) + 4473924;
   }
}
