package net.minecraft.util;

public class CircularTimer {
   public static final int a = 240;
   private final long[] b = new long[240];
   private int c;
   private int d;
   private int e;

   public void a(long var0) {
      this.b[this.e] = var0;
      ++this.e;
      if (this.e == 240) {
         this.e = 0;
      }

      if (this.d < 240) {
         this.c = 0;
         ++this.d;
      } else {
         this.c = this.b(this.e + 1);
      }
   }

   public long a(int var0) {
      int var1 = (this.c + var0) % 240;
      int var2 = this.c;

      long var3;
      for(var3 = 0L; var2 != var1; ++var2) {
         var3 += this.b[var2];
      }

      return var3 / (long)var0;
   }

   public int a(int var0, int var1) {
      return this.a(this.a(var0), var1, 60);
   }

   public int a(long var0, int var2, int var3) {
      double var4 = (double)var0 / (double)(1000000000L / (long)var3);
      return (int)(var4 * (double)var2);
   }

   public int a() {
      return this.c;
   }

   public int b() {
      return this.e;
   }

   public int b(int var0) {
      return var0 % 240;
   }

   public long[] c() {
      return this.b;
   }
}
