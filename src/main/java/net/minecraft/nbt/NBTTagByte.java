package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTNumber {
   private static final int w = 9;
   public static final NBTTagType<NBTTagByte> a = new NBTTagType.a<NBTTagByte>() {
      public NBTTagByte a(DataInput var0, int var1, NBTReadLimiter var2) throws IOException {
         var2.a(9L);
         return NBTTagByte.a(var0.readByte());
      }

      @Override
      public StreamTagVisitor.b a(DataInput var0, StreamTagVisitor var1) throws IOException {
         return var1.a(var0.readByte());
      }

      @Override
      public int c() {
         return 1;
      }

      @Override
      public String a() {
         return "BYTE";
      }

      @Override
      public String b() {
         return "TAG_Byte";
      }

      @Override
      public boolean d() {
         return true;
      }
   };
   public static final NBTTagByte b = a((byte)0);
   public static final NBTTagByte c = a((byte)1);
   private final byte x;

   NBTTagByte(byte var0) {
      this.x = var0;
   }

   public static NBTTagByte a(byte var0) {
      return NBTTagByte.a.a[128 + var0];
   }

   public static NBTTagByte a(boolean var0) {
      return var0 ? c : b;
   }

   @Override
   public void a(DataOutput var0) throws IOException {
      var0.writeByte(this.x);
   }

   @Override
   public int a() {
      return 9;
   }

   @Override
   public byte b() {
      return 1;
   }

   @Override
   public NBTTagType<NBTTagByte> c() {
      return a;
   }

   public NBTTagByte e() {
      return this;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         return var0 instanceof NBTTagByte && this.x == ((NBTTagByte)var0).x;
      }
   }

   @Override
   public int hashCode() {
      return this.x;
   }

   @Override
   public void a(TagVisitor var0) {
      var0.a(this);
   }

   @Override
   public long f() {
      return (long)this.x;
   }

   @Override
   public int g() {
      return this.x;
   }

   @Override
   public short h() {
      return (short)this.x;
   }

   @Override
   public byte i() {
      return this.x;
   }

   @Override
   public double j() {
      return (double)this.x;
   }

   @Override
   public float k() {
      return (float)this.x;
   }

   @Override
   public Number l() {
      return this.x;
   }

   @Override
   public StreamTagVisitor.b a(StreamTagVisitor var0) {
      return var0.a(this.x);
   }

   static class a {
      static final NBTTagByte[] a = new NBTTagByte[256];

      private a() {
      }

      static {
         for(int var0 = 0; var0 < a.length; ++var0) {
            a[var0] = new NBTTagByte((byte)(var0 - 128));
         }
      }
   }
}
