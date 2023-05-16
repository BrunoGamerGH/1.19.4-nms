package net.minecraft.util;

public class ColorUtil {
   public static class a {
      public static int a(int var0) {
         return var0 >>> 24;
      }

      public static int b(int var0) {
         return var0 & 0xFF;
      }

      public static int c(int var0) {
         return var0 >> 8 & 0xFF;
      }

      public static int d(int var0) {
         return var0 >> 16 & 0xFF;
      }

      public static int e(int var0) {
         return var0 & 16777215;
      }

      public static int f(int var0) {
         return var0 | 0xFF000000;
      }

      public static int a(int var0, int var1, int var2, int var3) {
         return var0 << 24 | var1 << 16 | var2 << 8 | var3;
      }

      public static int a(int var0, int var1) {
         return var0 << 24 | var1 & 16777215;
      }
   }

   public static class b {
      public static int a(int var0) {
         return var0 >>> 24;
      }

      public static int b(int var0) {
         return var0 >> 16 & 0xFF;
      }

      public static int c(int var0) {
         return var0 >> 8 & 0xFF;
      }

      public static int d(int var0) {
         return var0 & 0xFF;
      }

      public static int a(int var0, int var1, int var2, int var3) {
         return var0 << 24 | var1 << 16 | var2 << 8 | var3;
      }

      public static int a(int var0, int var1) {
         return a(a(var0) * a(var1) / 255, b(var0) * b(var1) / 255, c(var0) * c(var1) / 255, d(var0) * d(var1) / 255);
      }

      public static int a(float var0, int var1, int var2) {
         int var3 = MathHelper.a(var0, a(var1), a(var2));
         int var4 = MathHelper.a(var0, b(var1), b(var2));
         int var5 = MathHelper.a(var0, c(var1), c(var2));
         int var6 = MathHelper.a(var0, d(var1), d(var2));
         return a(var3, var4, var5, var6);
      }
   }
}
