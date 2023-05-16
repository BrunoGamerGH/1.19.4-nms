package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagEnd implements NBTBase {
   private static final int c = 8;
   public static final NBTTagType<NBTTagEnd> a = new NBTTagType<NBTTagEnd>() {
      public NBTTagEnd a(DataInput var0, int var1, NBTReadLimiter var2) {
         var2.a(8L);
         return NBTTagEnd.b;
      }

      @Override
      public StreamTagVisitor.b a(DataInput var0, StreamTagVisitor var1) {
         return var1.a();
      }

      @Override
      public void a(DataInput var0, int var1) {
      }

      @Override
      public void a(DataInput var0) {
      }

      @Override
      public String a() {
         return "END";
      }

      @Override
      public String b() {
         return "TAG_End";
      }

      @Override
      public boolean d() {
         return true;
      }
   };
   public static final NBTTagEnd b = new NBTTagEnd();

   private NBTTagEnd() {
   }

   @Override
   public void a(DataOutput var0) throws IOException {
   }

   @Override
   public int a() {
      return 8;
   }

   @Override
   public byte b() {
      return 0;
   }

   @Override
   public NBTTagType<NBTTagEnd> c() {
      return a;
   }

   @Override
   public String toString() {
      return this.f_();
   }

   public NBTTagEnd e() {
      return this;
   }

   @Override
   public void a(TagVisitor var0) {
      var0.a(this);
   }

   @Override
   public StreamTagVisitor.b a(StreamTagVisitor var0) {
      return var0.a();
   }
}
