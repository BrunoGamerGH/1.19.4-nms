package net.minecraft.server.rcon;

import java.nio.charset.StandardCharsets;

public class StatusChallengeUtils {
   public static final int a = 1460;
   public static final char[] b = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

   public static String a(byte[] var0, int var1, int var2) {
      int var3 = var2 - 1;
      int var4 = var1 > var3 ? var3 : var1;

      while(0 != var0[var4] && var4 < var3) {
         ++var4;
      }

      return new String(var0, var1, var4 - var1, StandardCharsets.UTF_8);
   }

   public static int a(byte[] var0, int var1) {
      return b(var0, var1, var0.length);
   }

   public static int b(byte[] var0, int var1, int var2) {
      return 0 > var2 - var1 - 4 ? 0 : var0[var1 + 3] << 24 | (var0[var1 + 2] & 0xFF) << 16 | (var0[var1 + 1] & 0xFF) << 8 | var0[var1] & 0xFF;
   }

   public static int c(byte[] var0, int var1, int var2) {
      return 0 > var2 - var1 - 4 ? 0 : var0[var1] << 24 | (var0[var1 + 1] & 0xFF) << 16 | (var0[var1 + 2] & 0xFF) << 8 | var0[var1 + 3] & 0xFF;
   }

   public static String a(byte var0) {
      return "" + b[(var0 & 240) >>> 4] + b[var0 & 15];
   }
}
