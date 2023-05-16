package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.MathHelper;

public class NBTTagDouble extends NBTNumber {
   private static final int c = 16;
   public static final NBTTagDouble a = new NBTTagDouble(0.0);
   public static final NBTTagType<NBTTagDouble> b = new NBTTagType.a<NBTTagDouble>() {
      public NBTTagDouble a(DataInput var0, int var1, NBTReadLimiter var2) throws IOException {
         var2.a(16L);
         return NBTTagDouble.a(var0.readDouble());
      }

      @Override
      public StreamTagVisitor.b a(DataInput var0, StreamTagVisitor var1) throws IOException {
         return var1.a(var0.readDouble());
      }

      @Override
      public int c() {
         return 8;
      }

      @Override
      public String a() {
         return "DOUBLE";
      }

      @Override
      public String b() {
         return "TAG_Double";
      }

      @Override
      public boolean d() {
         return true;
      }
   };
   private final double w;

   private NBTTagDouble(double var0) {
      this.w = var0;
   }

   public static NBTTagDouble a(double var0) {
      return var0 == 0.0 ? a : new NBTTagDouble(var0);
   }

   @Override
   public void a(DataOutput var0) throws IOException {
      var0.writeDouble(this.w);
   }

   @Override
   public int a() {
      return 16;
   }

   @Override
   public byte b() {
      return 6;
   }

   @Override
   public NBTTagType<NBTTagDouble> c() {
      return b;
   }

   public NBTTagDouble e() {
      return this;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         return var0 instanceof NBTTagDouble && this.w == ((NBTTagDouble)var0).w;
      }
   }

   @Override
   public int hashCode() {
      long var0 = Double.doubleToLongBits(this.w);
      return (int)(var0 ^ var0 >>> 32);
   }

   @Override
   public void a(TagVisitor var0) {
      var0.a(this);
   }

   @Override
   public long f() {
      return (long)Math.floor(this.w);
   }

   @Override
   public int g() {
      return MathHelper.a(this.w);
   }

   @Override
   public short h() {
      return (short)(MathHelper.a(this.w) & 65535);
   }

   @Override
   public byte i() {
      return (byte)(MathHelper.a(this.w) & 0xFF);
   }

   @Override
   public double j() {
      return this.w;
   }

   @Override
   public float k() {
      return (float)this.w;
   }

   @Override
   public Number l() {
      return this.w;
   }

   @Override
   public StreamTagVisitor.b a(StreamTagVisitor var0) {
      return var0.a(this.w);
   }
}
