package net.minecraft.nbt;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class NBTTagLongArray extends NBTList<NBTTagLong> {
   private static final int b = 24;
   public static final NBTTagType<NBTTagLongArray> a = new NBTTagType.b<NBTTagLongArray>() {
      public NBTTagLongArray a(DataInput var0, int var1, NBTReadLimiter var2) throws IOException {
         var2.a(24L);
         int var3 = var0.readInt();
         var2.a(8L * (long)var3);
         long[] var4 = new long[var3];

         for(int var5 = 0; var5 < var3; ++var5) {
            var4[var5] = var0.readLong();
         }

         return new NBTTagLongArray(var4);
      }

      @Override
      public StreamTagVisitor.b a(DataInput var0, StreamTagVisitor var1) throws IOException {
         int var2 = var0.readInt();
         long[] var3 = new long[var2];

         for(int var4 = 0; var4 < var2; ++var4) {
            var3[var4] = var0.readLong();
         }

         return var1.a(var3);
      }

      @Override
      public void a(DataInput var0) throws IOException {
         var0.skipBytes(var0.readInt() * 8);
      }

      @Override
      public String a() {
         return "LONG[]";
      }

      @Override
      public String b() {
         return "TAG_Long_Array";
      }
   };
   private long[] c;

   public NBTTagLongArray(long[] var0) {
      this.c = var0;
   }

   public NBTTagLongArray(LongSet var0) {
      this.c = var0.toLongArray();
   }

   public NBTTagLongArray(List<Long> var0) {
      this(a(var0));
   }

   private static long[] a(List<Long> var0) {
      long[] var1 = new long[var0.size()];

      for(int var2 = 0; var2 < var0.size(); ++var2) {
         Long var3 = var0.get(var2);
         var1[var2] = var3 == null ? 0L : var3;
      }

      return var1;
   }

   @Override
   public void a(DataOutput var0) throws IOException {
      var0.writeInt(this.c.length);

      for(long var4 : this.c) {
         var0.writeLong(var4);
      }
   }

   @Override
   public int a() {
      return 24 + 8 * this.c.length;
   }

   @Override
   public byte b() {
      return 12;
   }

   @Override
   public NBTTagType<NBTTagLongArray> c() {
      return a;
   }

   @Override
   public String toString() {
      return this.f_();
   }

   public NBTTagLongArray e() {
      long[] var0 = new long[this.c.length];
      System.arraycopy(this.c, 0, var0, 0, this.c.length);
      return new NBTTagLongArray(var0);
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         return var0 instanceof NBTTagLongArray && Arrays.equals(this.c, ((NBTTagLongArray)var0).c);
      }
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(this.c);
   }

   @Override
   public void a(TagVisitor var0) {
      var0.a(this);
   }

   public long[] g() {
      return this.c;
   }

   @Override
   public int size() {
      return this.c.length;
   }

   public NBTTagLong a(int var0) {
      return NBTTagLong.a(this.c[var0]);
   }

   public NBTTagLong a(int var0, NBTTagLong var1) {
      long var2 = this.c[var0];
      this.c[var0] = var1.f();
      return NBTTagLong.a(var2);
   }

   public void b(int var0, NBTTagLong var1) {
      this.c = ArrayUtils.add(this.c, var0, var1.f());
   }

   @Override
   public boolean a(int var0, NBTBase var1) {
      if (var1 instanceof NBTNumber) {
         this.c[var0] = ((NBTNumber)var1).f();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean b(int var0, NBTBase var1) {
      if (var1 instanceof NBTNumber) {
         this.c = ArrayUtils.add(this.c, var0, ((NBTNumber)var1).f());
         return true;
      } else {
         return false;
      }
   }

   public NBTTagLong b(int var0) {
      long var1 = this.c[var0];
      this.c = ArrayUtils.remove(this.c, var0);
      return NBTTagLong.a(var1);
   }

   @Override
   public byte f() {
      return 4;
   }

   @Override
   public void clear() {
      this.c = new long[0];
   }

   @Override
   public StreamTagVisitor.b a(StreamTagVisitor var0) {
      return var0.a(this.c);
   }
}
