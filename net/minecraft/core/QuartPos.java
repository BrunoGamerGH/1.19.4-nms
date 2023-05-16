package net.minecraft.core;

public final class QuartPos {
   public static final int a = 2;
   public static final int b = 4;
   public static final int c = 3;
   private static final int d = 2;

   private QuartPos() {
   }

   public static int a(int var0) {
      return var0 >> 2;
   }

   public static int b(int var0) {
      return var0 & 3;
   }

   public static int c(int var0) {
      return var0 << 2;
   }

   public static int d(int var0) {
      return var0 << 2;
   }

   public static int e(int var0) {
      return var0 >> 2;
   }
}
