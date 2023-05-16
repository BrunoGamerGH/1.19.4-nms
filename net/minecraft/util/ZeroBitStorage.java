package net.minecraft.util;

import java.util.Arrays;
import java.util.function.IntConsumer;
import org.apache.commons.lang3.Validate;

public class ZeroBitStorage implements DataBits {
   public static final long[] a = new long[0];
   private final int b;

   public ZeroBitStorage(int var0) {
      this.b = var0;
   }

   @Override
   public int a(int var0, int var1) {
      Validate.inclusiveBetween(0L, (long)(this.b - 1), (long)var0);
      Validate.inclusiveBetween(0L, 0L, (long)var1);
      return 0;
   }

   @Override
   public void b(int var0, int var1) {
      Validate.inclusiveBetween(0L, (long)(this.b - 1), (long)var0);
      Validate.inclusiveBetween(0L, 0L, (long)var1);
   }

   @Override
   public int a(int var0) {
      Validate.inclusiveBetween(0L, (long)(this.b - 1), (long)var0);
      return 0;
   }

   @Override
   public long[] a() {
      return a;
   }

   @Override
   public int b() {
      return this.b;
   }

   @Override
   public int c() {
      return 0;
   }

   @Override
   public void a(IntConsumer var0) {
      for(int var1 = 0; var1 < this.b; ++var1) {
         var0.accept(0);
      }
   }

   @Override
   public void a(int[] var0) {
      Arrays.fill(var0, 0, this.b, 0);
   }

   @Override
   public DataBits d() {
      return this;
   }
}
