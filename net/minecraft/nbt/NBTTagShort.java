package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTNumber {
   private static final int b = 10;
   public static final NBTTagType<NBTTagShort> a = new NBTTagType.a<NBTTagShort>() {
      public NBTTagShort a(DataInput var0, int var1, NBTReadLimiter var2) throws IOException {
         var2.a(10L);
         return NBTTagShort.a(var0.readShort());
      }

      @Override
      public StreamTagVisitor.b a(DataInput var0, StreamTagVisitor var1) throws IOException {
         return var1.a(var0.readShort());
      }

      @Override
      public int c() {
         return 2;
      }

      @Override
      public String a() {
         return "SHORT";
      }

      @Override
      public String b() {
         return "TAG_Short";
      }

      @Override
      public boolean d() {
         return true;
      }
   };
   private final short c;

   NBTTagShort(short var0) {
      this.c = var0;
   }

   public static NBTTagShort a(short var0) {
      return var0 >= -128 && var0 <= 1024 ? NBTTagShort.a.a[var0 - -128] : new NBTTagShort(var0);
   }

   @Override
   public void a(DataOutput var0) throws IOException {
      var0.writeShort(this.c);
   }

   @Override
   public int a() {
      return 10;
   }

   @Override
   public byte b() {
      return 2;
   }

   @Override
   public NBTTagType<NBTTagShort> c() {
      return a;
   }

   public NBTTagShort e() {
      return this;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         return var0 instanceof NBTTagShort && this.c == ((NBTTagShort)var0).c;
      }
   }

   @Override
   public int hashCode() {
      return this.c;
   }

   @Override
   public void a(TagVisitor var0) {
      var0.a(this);
   }

   @Override
   public long f() {
      return (long)this.c;
   }

   @Override
   public int g() {
      return this.c;
   }

   @Override
   public short h() {
      return this.c;
   }

   @Override
   public byte i() {
      return (byte)(this.c & 255);
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
      static final NBTTagShort[] a = new NBTTagShort[1153];

      private a() {
      }

      static {
         for(int var0 = 0; var0 < a.length; ++var0) {
            a[var0] = new NBTTagShort((short)(-128 + var0));
         }
      }
   }
}
