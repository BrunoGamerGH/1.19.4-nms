package net.minecraft.util;

import javax.annotation.Nullable;

public class MemoryReserve {
   @Nullable
   private static byte[] a = null;

   public static void a() {
      a = new byte[10485760];
   }

   public static void b() {
      a = new byte[0];
   }
}
