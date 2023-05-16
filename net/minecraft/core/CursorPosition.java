package net.minecraft.core;

public class CursorPosition {
   public static final int a = 0;
   public static final int b = 1;
   public static final int c = 2;
   public static final int d = 3;
   private final int e;
   private final int f;
   private final int g;
   private final int h;
   private final int i;
   private final int j;
   private final int k;
   private int l;
   private int m;
   private int n;
   private int o;

   public CursorPosition(int var0, int var1, int var2, int var3, int var4, int var5) {
      this.e = var0;
      this.f = var1;
      this.g = var2;
      this.h = var3 - var0 + 1;
      this.i = var4 - var1 + 1;
      this.j = var5 - var2 + 1;
      this.k = this.h * this.i * this.j;
   }

   public boolean a() {
      if (this.l == this.k) {
         return false;
      } else {
         this.m = this.l % this.h;
         int var0 = this.l / this.h;
         this.n = var0 % this.i;
         this.o = var0 / this.i;
         ++this.l;
         return true;
      }
   }

   public int b() {
      return this.e + this.m;
   }

   public int c() {
      return this.f + this.n;
   }

   public int d() {
      return this.g + this.o;
   }

   public int e() {
      int var0 = 0;
      if (this.m == 0 || this.m == this.h - 1) {
         ++var0;
      }

      if (this.n == 0 || this.n == this.i - 1) {
         ++var0;
      }

      if (this.o == 0 || this.o == this.j - 1) {
         ++var0;
      }

      return var0;
   }
}
