package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTNumber {
   private static final int b = 12;
   public static final NBTTagType<NBTTagInt> a = new NBTTagType.a<NBTTagInt>() {
      public NBTTagInt a(DataInput var0, int var1, NBTReadLimiter var2) throws IOException {
         var2.a(12L);
         return NBTTagInt.a(var0.readInt());
      }

      @Override
      public StreamTagVisitor.b a(DataInput var0, StreamTagVisitor var1) throws IOException {
         return var1.a(var0.readInt());
      }

      @Override
      public int c() {
         return 4;
      }

      @Override
      public String a() {
         return "INT";
      }

      @Override
      public String b() {
         return "TAG_Int";
      }

      @Override
      public boolean d() {
         return true;
      }
   };
   private final int c;

   NBTTagInt(int var0) {
      this.c = var0;
   }

   public static NBTTagInt a(int var0) {
      return var0 >= -128 && var0 <= 1024 ? NBTTagInt.a.a[var0 - -128] : new NBTTagInt(var0);
   }

   @Override
   public void a(DataOutput var0) throws IOException {
      var0.writeInt(this.c);
   }

   @Override
   public int a() {
      return 12;
   }

   @Override
   public byte b() {
      return 3;
   }

   @Override
   public NBTTagType<NBTTagInt> c() {
      return a;
   }

   public NBTTagInt e() {
      return this;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         return var0 instanceof NBTTagInt && this.c == ((NBTTagInt)var0).c;
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
      return (short)(this.c & 65535);
   }

   @Override
   public byte i() {
      return (byte)(this.c & 0xFF);
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
      static final NBTTagInt[] a = new NBTTagInt[1153];

      private a() {
      }

      static {
         for(int var0 = 0; var0 < a.length; ++var0) {
            a[var0] = new NBTTagInt(-128 + var0);
         }
      }
   }
}
