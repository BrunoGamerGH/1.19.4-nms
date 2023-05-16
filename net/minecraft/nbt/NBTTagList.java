package net.minecraft.nbt;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class NBTTagList extends NBTList<NBTBase> {
   private static final int b = 37;
   public static final NBTTagType<NBTTagList> a = new NBTTagType.b<NBTTagList>() {
      public NBTTagList a(DataInput var0, int var1, NBTReadLimiter var2) throws IOException {
         var2.a(37L);
         if (var1 > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
         } else {
            byte var3 = var0.readByte();
            int var4 = var0.readInt();
            if (var3 == 0 && var4 > 0) {
               throw new RuntimeException("Missing type on ListTag");
            } else {
               var2.a(4L * (long)var4);
               NBTTagType<?> var5 = NBTTagTypes.a(var3);
               List<NBTBase> var6 = Lists.newArrayListWithCapacity(var4);

               for(int var7 = 0; var7 < var4; ++var7) {
                  var6.add(var5.b(var0, var1 + 1, var2));
               }

               return new NBTTagList(var6, var3);
            }
         }
      }

      @Override
      public StreamTagVisitor.b a(DataInput var0, StreamTagVisitor var1) throws IOException {
         NBTTagType<?> var2 = NBTTagTypes.a(var0.readByte());
         int var3 = var0.readInt();
         switch(var1.a(var2, var3)) {
            case c:
               return StreamTagVisitor.b.c;
            case b:
               var2.a(var0, var3);
               return var1.b();
            default:
               int var4 = 0;

               while(true) {
                  label41: {
                     if (var4 < var3) {
                        switch(var1.b(var2, var4)) {
                           case d:
                              return StreamTagVisitor.b.c;
                           case c:
                              var2.a(var0);
                              break;
                           case b:
                              var2.a(var0);
                              break label41;
                           default:
                              switch(var2.a(var0, var1)) {
                                 case c:
                                    return StreamTagVisitor.b.c;
                                 case b:
                                    break;
                                 default:
                                    break label41;
                              }
                        }
                     }

                     int var5 = var3 - 1 - var4;
                     if (var5 > 0) {
                        var2.a(var0, var5);
                     }

                     return var1.b();
                  }

                  ++var4;
               }
         }
      }

      @Override
      public void a(DataInput var0) throws IOException {
         NBTTagType<?> var1 = NBTTagTypes.a(var0.readByte());
         int var2 = var0.readInt();
         var1.a(var0, var2);
      }

      @Override
      public String a() {
         return "LIST";
      }

      @Override
      public String b() {
         return "TAG_List";
      }
   };
   private final List<NBTBase> c;
   private byte w;

   NBTTagList(List<NBTBase> var0, byte var1) {
      this.c = var0;
      this.w = var1;
   }

   public NBTTagList() {
      this(Lists.newArrayList(), (byte)0);
   }

   @Override
   public void a(DataOutput var0) throws IOException {
      if (this.c.isEmpty()) {
         this.w = 0;
      } else {
         this.w = this.c.get(0).b();
      }

      var0.writeByte(this.w);
      var0.writeInt(this.c.size());

      for(NBTBase var2 : this.c) {
         var2.a(var0);
      }
   }

   @Override
   public int a() {
      int var0 = 37;
      var0 += 4 * this.c.size();

      for(NBTBase var2 : this.c) {
         var0 += var2.a();
      }

      return var0;
   }

   @Override
   public byte b() {
      return 9;
   }

   @Override
   public NBTTagType<NBTTagList> c() {
      return a;
   }

   @Override
   public String toString() {
      return this.f_();
   }

   private void g() {
      if (this.c.isEmpty()) {
         this.w = 0;
      }
   }

   @Override
   public NBTBase c(int var0) {
      NBTBase var1 = this.c.remove(var0);
      this.g();
      return var1;
   }

   @Override
   public boolean isEmpty() {
      return this.c.isEmpty();
   }

   public NBTTagCompound a(int var0) {
      if (var0 >= 0 && var0 < this.c.size()) {
         NBTBase var1 = this.c.get(var0);
         if (var1.b() == 10) {
            return (NBTTagCompound)var1;
         }
      }

      return new NBTTagCompound();
   }

   public NBTTagList b(int var0) {
      if (var0 >= 0 && var0 < this.c.size()) {
         NBTBase var1 = this.c.get(var0);
         if (var1.b() == 9) {
            return (NBTTagList)var1;
         }
      }

      return new NBTTagList();
   }

   public short d(int var0) {
      if (var0 >= 0 && var0 < this.c.size()) {
         NBTBase var1 = this.c.get(var0);
         if (var1.b() == 2) {
            return ((NBTTagShort)var1).h();
         }
      }

      return 0;
   }

   public int e(int var0) {
      if (var0 >= 0 && var0 < this.c.size()) {
         NBTBase var1 = this.c.get(var0);
         if (var1.b() == 3) {
            return ((NBTTagInt)var1).g();
         }
      }

      return 0;
   }

   public int[] f(int var0) {
      if (var0 >= 0 && var0 < this.c.size()) {
         NBTBase var1 = this.c.get(var0);
         if (var1.b() == 11) {
            return ((NBTTagIntArray)var1).g();
         }
      }

      return new int[0];
   }

   public long[] g(int var0) {
      if (var0 >= 0 && var0 < this.c.size()) {
         NBTBase var1 = this.c.get(var0);
         if (var1.b() == 11) {
            return ((NBTTagLongArray)var1).g();
         }
      }

      return new long[0];
   }

   public double h(int var0) {
      if (var0 >= 0 && var0 < this.c.size()) {
         NBTBase var1 = this.c.get(var0);
         if (var1.b() == 6) {
            return ((NBTTagDouble)var1).j();
         }
      }

      return 0.0;
   }

   public float i(int var0) {
      if (var0 >= 0 && var0 < this.c.size()) {
         NBTBase var1 = this.c.get(var0);
         if (var1.b() == 5) {
            return ((NBTTagFloat)var1).k();
         }
      }

      return 0.0F;
   }

   public String j(int var0) {
      if (var0 >= 0 && var0 < this.c.size()) {
         NBTBase var1 = this.c.get(var0);
         return var1.b() == 8 ? var1.f_() : var1.toString();
      } else {
         return "";
      }
   }

   @Override
   public int size() {
      return this.c.size();
   }

   public NBTBase k(int var0) {
      return this.c.get(var0);
   }

   @Override
   public NBTBase d(int var0, NBTBase var1) {
      NBTBase var2 = this.k(var0);
      if (!this.a(var0, var1)) {
         throw new UnsupportedOperationException(String.format(Locale.ROOT, "Trying to add tag of type %d to list of %d", var1.b(), this.w));
      } else {
         return var2;
      }
   }

   @Override
   public void c(int var0, NBTBase var1) {
      if (!this.b(var0, var1)) {
         throw new UnsupportedOperationException(String.format(Locale.ROOT, "Trying to add tag of type %d to list of %d", var1.b(), this.w));
      }
   }

   @Override
   public boolean a(int var0, NBTBase var1) {
      if (this.a(var1)) {
         this.c.set(var0, var1);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean b(int var0, NBTBase var1) {
      if (this.a(var1)) {
         this.c.add(var0, var1);
         return true;
      } else {
         return false;
      }
   }

   private boolean a(NBTBase var0) {
      if (var0.b() == 0) {
         return false;
      } else if (this.w == 0) {
         this.w = var0.b();
         return true;
      } else {
         return this.w == var0.b();
      }
   }

   public NBTTagList e() {
      Iterable<NBTBase> var0 = (Iterable<NBTBase>)(NBTTagTypes.a(this.w).d() ? this.c : Iterables.transform(this.c, NBTBase::d));
      List<NBTBase> var1 = Lists.newArrayList(var0);
      return new NBTTagList(var1, this.w);
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         return var0 instanceof NBTTagList && Objects.equals(this.c, ((NBTTagList)var0).c);
      }
   }

   @Override
   public int hashCode() {
      return this.c.hashCode();
   }

   @Override
   public void a(TagVisitor var0) {
      var0.a(this);
   }

   @Override
   public byte f() {
      return this.w;
   }

   @Override
   public void clear() {
      this.c.clear();
      this.w = 0;
   }

   @Override
   public StreamTagVisitor.b a(StreamTagVisitor var0) {
      switch(var0.a(NBTTagTypes.a(this.w), this.c.size())) {
         case c:
            return StreamTagVisitor.b.c;
         case b:
            return var0.b();
         default:
            int var1 = 0;

            while(var1 < this.c.size()) {
               NBTBase var2 = this.c.get(var1);
               switch(var0.b(var2.c(), var1)) {
                  case d:
                     return StreamTagVisitor.b.c;
                  case c:
                     return var0.b();
                  default:
                     switch(var2.a(var0)) {
                        case c:
                           return StreamTagVisitor.b.c;
                        case b:
                           return var0.b();
                     }
                  case b:
                     ++var1;
               }
            }

            return var0.b();
      }
   }
}
