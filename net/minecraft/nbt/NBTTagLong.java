package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTNumber {
   private static final int b = 16;
   public static final NBTTagType<NBTTagLong> a = new NBTTagType.a<NBTTagLong>() {
      public NBTTagLong a(DataInput var0, int var1, NBTReadLimiter var2) throws IOException {
         var2.a(16L);
         return NBTTagLong.a(var0.readLong());
      }

      @Override
      public StreamTagVisitor.b a(DataInput var0, StreamTagVisitor var1) throws IOException {
         return var1.a(var0.readLong());
      }

      @Override
      public int c() {
         return 8;
      }

      @Override
      public String a() {
         return "LONG";
      }

      @Override
      public String b() {
         return "TAG_Long";
      }

      @Override
      public boolean d() {
         return true;
      }
   };
   private final long c;

   NBTTagLong(long var0) {
      this.c = var0;
   }

   public static NBTTagLong a(long var0) {
      return var0 >= -128L && var0 <= 1024L ? NBTTagLong.a.a[(int)var0 - -128] : new NBTTagLong(var0);
   }

   @Override
   public void a(DataOutput var0) throws IOException {
      var0.writeLong(this.c);
   }

   @Override
   public int a() {
      return 16;
   }

   @Override
   public byte b() {
      return 4;
   }

   @Override
   public NBTTagType<NBTTagLong> c() {
      return a;
   }

   public NBTTagLong e() {
      return this;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         return var0 instanceof NBTTagLong && this.c == ((NBTTagLong)var0).c;
      }
   }

   @Override
   public int hashCode() {
      return (int)(this.c ^ this.c >>> 32);
   }

   @Override
   public void a(TagVisitor var0) {
      var0.a(this);
   }

   @Override
   public long f() {
      return this.c;
   }

   @Override
   public int g() {
      return (int)(this.c & -1L);
   }

   @Override
   public short h() {
      return (short)((int)(this.c & 65535L));
   }

   @Override
   public byte i() {
      return (byte)((int)(this.c & 255L));
   }

   @Override
   public double j() {
      return (double)this.c;
   }

   @Override
   public float k() {
      return (float)this.c;
   }

   @Override
   public Number l() {
      return this.c;
   }

   @Override
   public StreamTagVisitor.b a(StreamTagVisitor var0) {
      return var0.a(this.c);
   }

   static class a {
      private static final int b = 1024;
      private static final int c = -128;
      static final NBTTagLong[] a = new NBTTagLong[1153];

      private a() {
      }

      static {
         for(int var0 = 0; var0 < a.length; ++var0) {
            a[var0] = new NBTTagLong((long)(-128 + var0));
         }
      }
   }
}
