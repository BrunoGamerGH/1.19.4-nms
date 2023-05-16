package net.minecraft.nbt;

import java.io.DataOutput;
import java.io.IOException;

public interface NBTBase {
   int d = 8;
   int e = 12;
   int f = 4;
   int g = 28;
   byte h = 0;
   byte i = 1;
   byte j = 2;
   byte k = 3;
   byte l = 4;
   byte m = 5;
   byte n = 6;
   byte o = 7;
   byte p = 8;
   byte q = 9;
   byte r = 10;
   byte s = 11;
   byte t = 12;
   byte u = 99;
   int v = 512;

   void a(DataOutput var1) throws IOException;

   @Override
   String toString();

   byte b();

   NBTTagType<?> c();

   NBTBase d();

   int a();

   default String f_() {
      return new StringTagVisitor().a(this);
   }

   void a(TagVisitor var1);

   StreamTagVisitor.b a(StreamTagVisitor var1);

   default void b(StreamTagVisitor var0) {
      StreamTagVisitor.b var1 = var0.b(this.c());
      if (var1 == StreamTagVisitor.b.a) {
         this.a(var0);
      }
   }
}
