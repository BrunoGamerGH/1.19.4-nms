package net.minecraft.nbt;

public interface StreamTagVisitor {
   StreamTagVisitor.b a();

   StreamTagVisitor.b a(String var1);

   StreamTagVisitor.b a(byte var1);

   StreamTagVisitor.b a(short var1);

   StreamTagVisitor.b a(int var1);

   StreamTagVisitor.b a(long var1);

   StreamTagVisitor.b a(float var1);

   StreamTagVisitor.b a(double var1);

   StreamTagVisitor.b a(byte[] var1);

   StreamTagVisitor.b a(int[] var1);

   StreamTagVisitor.b a(long[] var1);

   StreamTagVisitor.b a(NBTTagType<?> var1, int var2);

   StreamTagVisitor.a a(NBTTagType<?> var1);

   StreamTagVisitor.a a(NBTTagType<?> var1, String var2);

   StreamTagVisitor.a b(NBTTagType<?> var1, int var2);

   StreamTagVisitor.b b();

   StreamTagVisitor.b b(NBTTagType<?> var1);

   public static enum a {
      a,
      b,
      c,
      d;
   }

   public static enum b {
      a,
      b,
      c;
   }
}
