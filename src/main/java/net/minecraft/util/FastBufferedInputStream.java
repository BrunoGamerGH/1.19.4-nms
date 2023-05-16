package net.minecraft.util;

import java.io.IOException;
import java.io.InputStream;

public class FastBufferedInputStream extends InputStream {
   private static final int a = 8192;
   private final InputStream b;
   private final byte[] c;
   private int d;
   private int e;

   public FastBufferedInputStream(InputStream var0) {
      this(var0, 8192);
   }

   public FastBufferedInputStream(InputStream var0, int var1) {
      this.b = var0;
      this.c = new byte[var1];
   }

   @Override
   public int read() throws IOException {
      if (this.e >= this.d) {
         this.b();
         if (this.e >= this.d) {
            return -1;
         }
      }

      return Byte.toUnsignedInt(this.c[this.e++]);
   }

   @Override
   public int read(byte[] var0, int var1, int var2) throws IOException {
      int var3 = this.a();
      if (var3 <= 0) {
         if (var2 >= this.c.length) {
            return this.b.read(var0, var1, var2);
         }

         this.b();
         var3 = this.a();
         if (var3 <= 0) {
            return -1;
         }
      }

      if (var2 > var3) {
         var2 = var3;
      }

      System.arraycopy(this.c, this.e, var0, var1, var2);
      this.e += var2;
      return var2;
   }

   @Override
   public long skip(long var0) throws IOException {
      if (var0 <= 0L) {
         return 0L;
      } else {
         long var2 = (long)this.a();
         if (var2 <= 0L) {
            return this.b.skip(var0);
         } else {
            if (var0 > var2) {
               var0 = var2;
            }

            this.e = (int)((long)this.e + var0);
            return var0;
         }
      }
   }

   @Override
   public int available() throws IOException {
      return this.a() + this.b.available();
   }

   @Override
   public void close() throws IOException {
      this.b.close();
   }

   private int a() {
      return this.d - this.e;
   }

   private void b() throws IOException {
      this.d = 0;
      this.e = 0;
      int var0 = this.b.read(this.c, 0, this.c.length);
      if (var0 > 0) {
         this.d = var0;
      }
   }
}
