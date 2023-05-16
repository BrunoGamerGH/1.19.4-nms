package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;

public class NBTReadLimiter {
   public static final NBTReadLimiter a = new NBTReadLimiter(0L) {
      @Override
      public void a(long var0) {
      }
   };
   private final long b;
   private long c;

   public NBTReadLimiter(long var0) {
      this.b = var0;
   }

   public void a(long var0) {
      this.c += var0;
      if (this.c > this.b) {
         throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.c + "bytes where max allowed: " + this.b);
      }
   }

   @VisibleForTesting
   public long a() {
      return this.c;
   }
}
