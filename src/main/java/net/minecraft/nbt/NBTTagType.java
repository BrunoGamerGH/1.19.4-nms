package net.minecraft.nbt;

import java.io.DataInput;
import java.io.IOException;

public interface NBTTagType<T extends NBTBase> {
   T b(DataInput var1, int var2, NBTReadLimiter var3) throws IOException;

   StreamTagVisitor.b a(DataInput var1, StreamTagVisitor var2) throws IOException;

   default void b(DataInput var0, StreamTagVisitor var1) throws IOException {
      switch(var1.b(this)) {
         case a:
            this.a(var0, var1);
         case c:
         default:
            break;
         case b:
            this.a(var0);
      }
   }

   void a(DataInput var1, int var2) throws IOException;

   void a(DataInput var1) throws IOException;

   default boolean d() {
      return false;
   }

   String a();

   String b();

   static NBTTagType<NBTTagEnd> a(final int var0) {
      return new NBTTagType<NBTTagEnd>() {
         private IOException c() {
            return new IOException("Invalid tag id: " + var0);
         }

         public NBTTagEnd a(DataInput var0x, int var1, NBTReadLimiter var2) throws IOException {
            throw this.c();
         }

         @Override
         public StreamTagVisitor.b a(DataInput var0x, StreamTagVisitor var1) throws IOException {
            throw this.c();
         }

         @Override
         public void a(DataInput var0x, int var1) throws IOException {
            throw this.c();
         }

         @Override
         public void a(DataInput var0x) throws IOException {
            throw this.c();
         }

         @Override
         public String a() {
            return "INVALID[" + var0 + "]";
         }

         @Override
         public String b() {
            return "UNKNOWN_" + var0;
         }
      };
   }

   public interface a<T extends NBTBase> extends NBTTagType<T> {
      @Override
      default void a(DataInput var0) throws IOException {
         var0.skipBytes(this.c());
      }

      @Override
      default void a(DataInput var0, int var1) throws IOException {
         var0.skipBytes(this.c() * var1);
      }

      int c();
   }

   public interface b<T extends NBTBase> extends NBTTagType<T> {
      @Override
      default void a(DataInput var0, int var1) throws IOException {
         for(int var2 = 0; var2 < var1; ++var2) {
            this.a(var0);
         }
      }
   }
}
