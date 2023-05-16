package net.minecraft.nbt;

import com.google.common.base.Preconditions;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class NBTTagIntArray extends NBTList<NBTTagInt> {
   private static final int b = 24;
   public static final NBTTagType<NBTTagIntArray> a = new NBTTagType.b<NBTTagIntArray>() {
      public NBTTagIntArray a(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
         nbtreadlimiter.a(24L);
         int j = datainput.readInt();
         Preconditions.checkArgument(j < 16777216);
         nbtreadlimiter.a(4L * (long)j);
         int[] aint = new int[j];

         for(int k = 0; k < j; ++k) {
            aint[k] = datainput.readInt();
         }

         return new NBTTagIntArray(aint);
      }

      @Override
      public StreamTagVisitor.b a(DataInput datainput, StreamTagVisitor streamtagvisitor) throws IOException {
         int i = datainput.readInt();
         int[] aint = new int[i];

         for(int j = 0; j < i; ++j) {
            aint[j] = datainput.readInt();
         }

         return streamtagvisitor.a(aint);
      }

      @Override
      public void a(DataInput datainput) throws IOException {
         datainput.skipBytes(datainput.readInt() * 4);
      }

      @Override
      public String a() {
         return "INT[]";
      }

      @Override
      public String b() {
         return "TAG_Int_Array";
      }
   };
   private int[] c;

   public NBTTagIntArray(int[] aint) {
      this.c = aint;
   }

   public NBTTagIntArray(List<Integer> list) {
      this(a(list));
   }

   private static int[] a(List<Integer> list) {
      int[] aint = new int[list.size()];

      for(int i = 0; i < list.size(); ++i) {
         Integer integer = list.get(i);
         aint[i] = integer == null ? 0 : integer;
      }

      return aint;
   }

   @Override
   public void a(DataOutput dataoutput) throws IOException {
      dataoutput.writeInt(this.c.length);

      for(int k : this.c) {
         dataoutput.writeInt(k);
      }
   }

   @Override
   public int a() {
      return 24 + 4 * this.c.length;
   }

   @Override
   public byte b() {
      return 11;
   }

   @Override
   public NBTTagType<NBTTagIntArray> c() {
      return a;
   }

   @Override
   public String toString() {
      return this.f_();
   }

   public NBTTagIntArray e() {
      int[] aint = new int[this.c.length];
      System.arraycopy(this.c, 0, aint, 0, this.c.length);
      return new NBTTagIntArray(aint);
   }

   @Override
   public boolean equals(Object object) {
      return this == object ? true : object instanceof NBTTagIntArray && Arrays.equals(this.c, ((NBTTagIntArray)object).c);
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(this.c);
   }

   public int[] g() {
      return this.c;
   }

   @Override
   public void a(TagVisitor tagvisitor) {
      tagvisitor.a(this);
   }

   @Override
   public int size() {
      return this.c.length;
   }

   public NBTTagInt a(int i) {
      return NBTTagInt.a(this.c[i]);
   }

   public NBTTagInt a(int i, NBTTagInt nbttagint) {
      int j = this.c[i];
      this.c[i] = nbttagint.g();
      return NBTTagInt.a(j);
   }

   public void b(int i, NBTTagInt nbttagint) {
      this.c = ArrayUtils.add(this.c, i, nbttagint.g());
   }

   @Override
   public boolean a(int i, NBTBase nbtbase) {
      if (nbtbase instanceof NBTNumber) {
         this.c[i] = ((NBTNumber)nbtbase).g();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean b(int i, NBTBase nbtbase) {
      if (nbtbase instanceof NBTNumber) {
         this.c = ArrayUtils.add(this.c, i, ((NBTNumber)nbtbase).g());
         return true;
      } else {
         return false;
      }
   }

   public NBTTagInt b(int i) {
      int j = this.c[i];
      this.c = ArrayUtils.remove(this.c, i);
      return NBTTagInt.a(j);
   }

   @Override
   public byte f() {
      return 3;
   }

   @Override
   public void clear() {
      this.c = new int[0];
   }

   @Override
   public StreamTagVisitor.b a(StreamTagVisitor streamtagvisitor) {
      return streamtagvisitor.a(this.c);
   }
}
