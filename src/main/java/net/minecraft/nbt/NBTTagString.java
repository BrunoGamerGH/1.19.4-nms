package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.UTFDataFormatException;
import java.util.Objects;
import net.minecraft.SystemUtils;

public class NBTTagString implements NBTBase {
   private static final int b = 36;
   public static final NBTTagType<NBTTagString> a = new NBTTagType.b<NBTTagString>() {
      public NBTTagString a(DataInput var0, int var1, NBTReadLimiter var2) throws IOException {
         var2.a(36L);
         String var3 = var0.readUTF();
         var2.a((long)(2 * var3.length()));
         return NBTTagString.a(var3);
      }

      @Override
      public StreamTagVisitor.b a(DataInput var0, StreamTagVisitor var1) throws IOException {
         return var1.a(var0.readUTF());
      }

      @Override
      public void a(DataInput var0) throws IOException {
         NBTTagString.a(var0);
      }

      @Override
      public String a() {
         return "STRING";
      }

      @Override
      public String b() {
         return "TAG_String";
      }

      @Override
      public boolean d() {
         return true;
      }
   };
   private static final NBTTagString c = new NBTTagString("");
   private static final char w = '"';
   private static final char x = '\'';
   private static final char y = '\\';
   private static final char z = '\u0000';
   private final String A;

   public static void a(DataInput var0) throws IOException {
      var0.skipBytes(var0.readUnsignedShort());
   }

   private NBTTagString(String var0) {
      Objects.requireNonNull(var0, "Null string not allowed");
      this.A = var0;
   }

   public static NBTTagString a(String var0) {
      return var0.isEmpty() ? c : new NBTTagString(var0);
   }

   @Override
   public void a(DataOutput var0) throws IOException {
      try {
         var0.writeUTF(this.A);
      } catch (UTFDataFormatException var3) {
         SystemUtils.a("Failed to write NBT String", var3);
         var0.writeUTF("");
      }
   }

   @Override
   public int a() {
      return 36 + 2 * this.A.length();
   }

   @Override
   public byte b() {
      return 8;
   }

   @Override
   public NBTTagType<NBTTagString> c() {
      return a;
   }

   @Override
   public String toString() {
      return NBTBase.super.f_();
   }

   public NBTTagString e() {
      return this;
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         return var0 instanceof NBTTagString && Objects.equals(this.A, ((NBTTagString)var0).A);
      }
   }

   @Override
   public int hashCode() {
      return this.A.hashCode();
   }

   @Override
   public String f_() {
      return this.A;
   }

   @Override
   public void a(TagVisitor var0) {
      var0.a(this);
   }

   public static String b(String var0) {
      StringBuilder var1 = new StringBuilder(" ");
      char var2 = 0;

      for(int var3 = 0; var3 < var0.length(); ++var3) {
         char var4 = var0.charAt(var3);
         if (var4 == '\\') {
            var1.append('\\');
         } else if (var4 == '"' || var4 == '\'') {
            if (var2 == 0) {
               var2 = (char)(var4 == '"' ? 39 : 34);
            }

            if (var2 == var4) {
               var1.append('\\');
            }
         }

         var1.append(var4);
      }

      if (var2 == 0) {
         var2 = '"';
      }

      var1.setCharAt(0, var2);
      var1.append(var2);
      return var1.toString();
   }

   @Override
   public StreamTagVisitor.b a(StreamTagVisitor var0) {
      return var0.a(this.A);
   }
}
