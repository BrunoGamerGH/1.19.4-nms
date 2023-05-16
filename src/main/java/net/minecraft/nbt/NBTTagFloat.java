package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.MathHelper;

public class NBTTagFloat extends NBTNumber {
   private static final int c = 12;
   public static final NBTTagFloat a = new NBTTagFloat(0.0F);
   public static final NBTTagType<NBTTagFloat> b = new NBTTagType.a<NBTTagFloat>() {
      public NBTTagFloat a(DataInput var0, int var1, NBTReadLimiter var2) throws IOException {
         var2.a(12L);
         return NBTTagFloat.a(var0.readFloat());
      }

      @Override
      public StreamTagVisitor.b a(DataInput var0, StreamTagVisitor var1) throws IOException {
         return var1.a(var0.readFloat());
      }

      @Override
      public int c() {
         return 4;
      }

      @Override
      public String a() {
         return "FLOAT";
      }

      @Override
      public String b() {
         return "TAG_Float";
      }

      @Override
      public boolean d() {
         return true;
      }
   };
   private final float w;

   private NBTTagFloat(float var0) {
      this.w = var0;
   }

   public static NBTTagFloat a(float var0) {
      return var0 == 0.0F ? a : new NBTTagFloat(var0);
   }

   @Override
   public void a(DataOutput var0) throws IOException {
      var0.writeFloat(this.w);
   }

   @Override
   public int a() {
      return 12;
   }

   @Override
   public byte b() {
      return 5;
   }

   @Override
   public NBTTagType<NBTTagFloat> c() {
      return b;
   }

   public NBTTagFloat e() {
      return this;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         return var0 instanceof NBTTagFloat && this.w == ((NBTTagFloat)var0).w;
      }
   }

   @Override
   public int hashCode() {
      return Float.floatToIntBits(this.w);
   }

   @Override
   public void a(TagVisitor var0) {
      var0.a(this);
   }

   @Override
   public long f() {
      return (long)this.w;
   }

   @Override
   public int g() {
      return MathHelper.d(this.w);
   }

   @Override
   public short h() {
      return (short)(MathHelper.d(this.w) & 65535);
   }

   @Override
   public byte i() {
      return (byte)(MathHelper.d(this.w) & 0xFF);
   }

   @Override
   public double j() {
      return (double)this.w;
   }

   @Override
   public float k() {
      return this.w;
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
