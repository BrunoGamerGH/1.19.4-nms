package net.minecraft.nbt;

import com.google.common.base.Preconditions;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class NBTTagByteArray extends NBTList<NBTTagByte> {
   private static final int b = 24;
   public static final NBTTagType<NBTTagByteArray> a = new NBTTagType.b<NBTTagByteArray>() {
      public NBTTagByteArray a(DataInput datainput, int i, NBTReadLimiter nbtreadlimiter) throws IOException {
         nbtreadlimiter.a(24L);
         int j = datainput.readInt();
         Preconditions.checkArgument(j < 16777216);
         nbtreadlimiter.a(1L * (long)j);
         byte[] abyte = new byte[j];
         datainput.readFully(abyte);
         return new NBTTagByteArray(abyte);
      }

      @Override
      public StreamTagVisitor.b a(DataInput datainput, StreamTagVisitor streamtagvisitor) throws IOException {
         int i = datainput.readInt();
         byte[] abyte = new byte[i];
         datainput.readFully(abyte);
         return streamtagvisitor.a(abyte);
      }

      @Override
      public void a(DataInput datainput) throws IOException {
         datainput.skipBytes(datainput.readInt() * 1);
      }

      @Override
      public String a() {
         return "BYTE[]";
      }

      @Override
      public String b() {
         return "TAG_Byte_Array";
      }
   };
   private byte[] c;

   public NBTTagByteArray(byte[] abyte) {
      this.c = abyte;
   }

   public NBTTagByteArray(List<Byte> list) {
      this(a(list));
   }

   private static byte[] a(List<Byte> list) {
      byte[] abyte = new byte[list.size()];

      for(int i = 0; i < list.size(); ++i) {
         Byte obyte = list.get(i);
         abyte[i] = obyte == null ? 0 : obyte;
      }

      return abyte;
   }

   @Override
   public void a(DataOutput dataoutput) throws IOException {
      dataoutput.writeInt(this.c.length);
      dataoutput.write(this.c);
   }

   @Override
   public int a() {
      return 24 + 1 * this.c.length;
   }

   @Override
   public byte b() {
      return 7;
   }

   @Override
   public NBTTagType<NBTTagByteArray> c() {
      return a;
   }

   @Override
   public String toString() {
      return this.f_();
   }

   @Override
   public NBTBase d() {
      byte[] abyte = new byte[this.c.length];
      System.arraycopy(this.c, 0, abyte, 0, this.c.length);
      return new NBTTagByteArray(abyte);
   }

   @Override
   public boolean equals(Object object) {
      return this == object ? true : object instanceof NBTTagByteArray && Arrays.equals(this.c, ((NBTTagByteArray)object).c);
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(this.c);
   }

   @Override
   public void a(TagVisitor tagvisitor) {
      tagvisitor.a(this);
   }

   public byte[] e() {
      return this.c;
   }

   @Override
   public int size() {
      return this.c.length;
   }

   public NBTTagByte a(int i) {
      return NBTTagByte.a(this.c[i]);
   }

   public NBTTagByte a(int i, NBTTagByte nbttagbyte) {
      byte b0 = this.c[i];
      this.c[i] = nbttagbyte.i();
      return NBTTagByte.a(b0);
   }

   public void b(int i, NBTTagByte nbttagbyte) {
      this.c = ArrayUtils.add(this.c, i, nbttagbyte.i());
   }

   @Override
   public boolean a(int i, NBTBase nbtbase) {
      if (nbtbase instanceof NBTNumber) {
         this.c[i] = ((NBTNumber)nbtbase).i();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean b(int i, NBTBase nbtbase) {
      if (nbtbase instanceof NBTNumber) {
         this.c = ArrayUtils.add(this.c, i, ((NBTNumber)nbtbase).i());
         return true;
      } else {
         return false;
      }
   }

   public NBTTagByte b(int i) {
      byte b0 = this.c[i];
      this.c = ArrayUtils.remove(this.c, i);
      return NBTTagByte.a(b0);
   }

   @Override
   public byte f() {
      return 1;
   }

   @Override
   public void clear() {
      this.c = new byte[0];
   }

   @Override
   public StreamTagVisitor.b a(StreamTagVisitor streamtagvisitor) {
      return streamtagvisitor.a(this.c);
   }
}
