package net.minecraft.util.datafix;

import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.Validate;

public class DataBitsPacked {
   private static final int a = 6;
   private final long[] b;
   private final int c;
   private final long d;
   private final int e;

   public DataBitsPacked(int var0, int var1) {
      this(var0, var1, new long[MathHelper.d(var1 * var0, 64) / 64]);
   }

   public DataBitsPacked(int var0, int var1, long[] var2) {
      Validate.inclusiveBetween(1L, 32L, (long)var0);
      this.e = var1;
      this.c = var0;
      this.b = var2;
      this.d = (1L << var0) - 1L;
      int var3 = MathHelper.d(var1 * var0, 64) / 64;
      if (var2.length != var3) {
         throw new IllegalArgumentException("Invalid length given for storage, got: " + var2.length + " but expected: " + var3);
      }
   }

   public void a(int var0, int var1) {
      Validate.inclusiveBetween(0L, (long)(this.e - 1), (long)var0);
      Validate.inclusiveBetween(0L, this.d, (long)var1);
      int var2 = var0 * this.c;
      int var3 = var2 >> 6;
      int var4 = (var0 + 1) * this.c - 1 >> 6;
      int var5 = var2 ^ var3 << 6;
      this.b[var3] = this.b[var3] & ~(this.d << var5) | ((long)var1 & this.d) << var5;
      if (var3 != var4) {
         int var6 = 64 - var5;
         int var7 = this.c - var6;
         this.b[var4] = this.b[var4] >>> var7 << var7 | ((long)var1 & this.d) >> var6;
      }
   }

   public int a(int var0) {
      Validate.inclusiveBetween(0L, (long)(this.e - 1), (long)var0);
      int var1 = var0 * this.c;
      int var2 = var1 >> 6;
      int var3 = (var0 + 1) * this.c - 1 >> 6;
      int var4 = var1 ^ var2 << 6;
      if (var2 == var3) {
         return (int)(this.b[var2] >>> var4 & this.d);
      } else {
         int var5 = 64 - var4;
         return (int)((this.b[var2] >>> var4 | this.b[var3] << var5) & this.d);
      }
   }

   public long[] a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }
}
