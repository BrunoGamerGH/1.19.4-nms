package net.minecraft.nbt;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportSystemDetails;
import net.minecraft.ReportedException;

public class NBTTagCompound implements NBTBase {
   public static final Codec<NBTTagCompound> a = Codec.PASSTHROUGH.comapFlatMap(var0 -> {
      NBTBase var1 = (NBTBase)var0.convert(DynamicOpsNBT.a).getValue();
      return var1 instanceof NBTTagCompound ? DataResult.success((NBTTagCompound)var1) : DataResult.error(() -> "Not a compound tag: " + var1);
   }, var0 -> new Dynamic(DynamicOpsNBT.a, var0));
   private static final int c = 48;
   private static final int w = 32;
   public static final NBTTagType<NBTTagCompound> b = new NBTTagType.b<NBTTagCompound>() {
      public NBTTagCompound a(DataInput var0, int var1, NBTReadLimiter var2) throws IOException {
         var2.a(48L);
         if (var1 > 512) {
            throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
         } else {
            Map<String, NBTBase> var3 = Maps.newHashMap();

            byte var4;
            while((var4 = NBTTagCompound.a(var0, var2)) != 0) {
               String var5 = NBTTagCompound.b(var0, var2);
               var2.a((long)(28 + 2 * var5.length()));
               NBTBase var6 = NBTTagCompound.a(NBTTagTypes.a(var4), var5, var0, var1 + 1, var2);
               if (var3.put(var5, var6) == null) {
                  var2.a(36L);
               }
            }

            return new NBTTagCompound(var3);
         }
      }

      @Override
      public StreamTagVisitor.b a(DataInput var0, StreamTagVisitor var1) throws IOException {
         byte var2;
         label33:
         while((var2 = var0.readByte()) != 0) {
            NBTTagType<?> var3 = NBTTagTypes.a(var2);
            switch(var1.a(var3)) {
               case d:
                  return StreamTagVisitor.b.c;
               case c:
                  NBTTagString.a(var0);
                  var3.a(var0);
                  break label33;
               case b:
                  NBTTagString.a(var0);
                  var3.a(var0);
                  break;
               default:
                  String var4 = var0.readUTF();
                  switch(var1.a(var3, var4)) {
                     case d:
                        return StreamTagVisitor.b.c;
                     case c:
                        var3.a(var0);
                        break label33;
                     case b:
                        var3.a(var0);
                        break;
                     default:
                        switch(var3.a(var0, var1)) {
                           case c:
                              return StreamTagVisitor.b.c;
                           case b:
                        }
                  }
            }
         }

         if (var2 != 0) {
            while((var2 = var0.readByte()) != false) {
               NBTTagString.a(var0);
               NBTTagTypes.a(var2).a(var0);
            }
         }

         return var1.b();
      }

      @Override
      public void a(DataInput var0) throws IOException {
         byte var1;
         while((var1 = var0.readByte()) != 0) {
            NBTTagString.a(var0);
            NBTTagTypes.a(var1).a(var0);
         }
      }

      @Override
      public String a() {
         return "COMPOUND";
      }

      @Override
      public String b() {
         return "TAG_Compound";
      }
   };
   private final Map<String, NBTBase> x;

   protected NBTTagCompound(Map<String, NBTBase> var0) {
      this.x = var0;
   }

   public NBTTagCompound() {
      this(Maps.newHashMap());
   }

   @Override
   public void a(DataOutput var0) throws IOException {
      for(String var2 : this.x.keySet()) {
         NBTBase var3 = this.x.get(var2);
         a(var2, var3, var0);
      }

      var0.writeByte(0);
   }

   @Override
   public int a() {
      int var0 = 48;

      for(Entry<String, NBTBase> var2 : this.x.entrySet()) {
         var0 += 28 + 2 * var2.getKey().length();
         var0 += 36;
         var0 += var2.getValue().a();
      }

      return var0;
   }

   public Set<String> e() {
      return this.x.keySet();
   }

   @Override
   public byte b() {
      return 10;
   }

   @Override
   public NBTTagType<NBTTagCompound> c() {
      return b;
   }

   public int f() {
      return this.x.size();
   }

   @Nullable
   public NBTBase a(String var0, NBTBase var1) {
      return this.x.put(var0, var1);
   }

   public void a(String var0, byte var1) {
      this.x.put(var0, NBTTagByte.a(var1));
   }

   public void a(String var0, short var1) {
      this.x.put(var0, NBTTagShort.a(var1));
   }

   public void a(String var0, int var1) {
      this.x.put(var0, NBTTagInt.a(var1));
   }

   public void a(String var0, long var1) {
      this.x.put(var0, NBTTagLong.a(var1));
   }

   public void a(String var0, UUID var1) {
      this.x.put(var0, GameProfileSerializer.a(var1));
   }

   public UUID a(String var0) {
      return GameProfileSerializer.a(this.c(var0));
   }

   public boolean b(String var0) {
      NBTBase var1 = this.c(var0);
      return var1 != null && var1.c() == NBTTagIntArray.a && ((NBTTagIntArray)var1).g().length == 4;
   }

   public void a(String var0, float var1) {
      this.x.put(var0, NBTTagFloat.a(var1));
   }

   public void a(String var0, double var1) {
      this.x.put(var0, NBTTagDouble.a(var1));
   }

   public void a(String var0, String var1) {
      this.x.put(var0, NBTTagString.a(var1));
   }

   public void a(String var0, byte[] var1) {
      this.x.put(var0, new NBTTagByteArray(var1));
   }

   public void a(String var0, List<Byte> var1) {
      this.x.put(var0, new NBTTagByteArray(var1));
   }

   public void a(String var0, int[] var1) {
      this.x.put(var0, new NBTTagIntArray(var1));
   }

   public void b(String var0, List<Integer> var1) {
      this.x.put(var0, new NBTTagIntArray(var1));
   }

   public void a(String var0, long[] var1) {
      this.x.put(var0, new NBTTagLongArray(var1));
   }

   public void c(String var0, List<Long> var1) {
      this.x.put(var0, new NBTTagLongArray(var1));
   }

   public void a(String var0, boolean var1) {
      this.x.put(var0, NBTTagByte.a(var1));
   }

   @Nullable
   public NBTBase c(String var0) {
      return this.x.get(var0);
   }

   public byte d(String var0) {
      NBTBase var1 = this.x.get(var0);
      return var1 == null ? 0 : var1.b();
   }

   public boolean e(String var0) {
      return this.x.containsKey(var0);
   }

   public boolean b(String var0, int var1) {
      int var2 = this.d(var0);
      if (var2 == var1) {
         return true;
      } else if (var1 != 99) {
         return false;
      } else {
         return var2 == 1 || var2 == 2 || var2 == 3 || var2 == 4 || var2 == 5 || var2 == 6;
      }
   }

   public byte f(String var0) {
      try {
         if (this.b(var0, 99)) {
            return ((NBTNumber)this.x.get(var0)).i();
         }
      } catch (ClassCastException var3) {
      }

      return 0;
   }

   public short g(String var0) {
      try {
         if (this.b(var0, 99)) {
            return ((NBTNumber)this.x.get(var0)).h();
         }
      } catch (ClassCastException var3) {
      }

      return 0;
   }

   public int h(String var0) {
      try {
         if (this.b(var0, 99)) {
            return ((NBTNumber)this.x.get(var0)).g();
         }
      } catch (ClassCastException var3) {
      }

      return 0;
   }

   public long i(String var0) {
      try {
         if (this.b(var0, 99)) {
            return ((NBTNumber)this.x.get(var0)).f();
         }
      } catch (ClassCastException var3) {
      }

      return 0L;
   }

   public float j(String var0) {
      try {
         if (this.b(var0, 99)) {
            return ((NBTNumber)this.x.get(var0)).k();
         }
      } catch (ClassCastException var3) {
      }

      return 0.0F;
   }

   public double k(String var0) {
      try {
         if (this.b(var0, 99)) {
            return ((NBTNumber)this.x.get(var0)).j();
         }
      } catch (ClassCastException var3) {
      }

      return 0.0;
   }

   public String l(String var0) {
      try {
         if (this.b(var0, 8)) {
            return this.x.get(var0).f_();
         }
      } catch (ClassCastException var3) {
      }

      return "";
   }

   public byte[] m(String var0) {
      try {
         if (this.b(var0, 7)) {
            return ((NBTTagByteArray)this.x.get(var0)).e();
         }
      } catch (ClassCastException var3) {
         throw new ReportedException(this.a(var0, NBTTagByteArray.a, var3));
      }

      return new byte[0];
   }

   public int[] n(String var0) {
      try {
         if (this.b(var0, 11)) {
            return ((NBTTagIntArray)this.x.get(var0)).g();
         }
      } catch (ClassCastException var3) {
         throw new ReportedException(this.a(var0, NBTTagIntArray.a, var3));
      }

      return new int[0];
   }

   public long[] o(String var0) {
      try {
         if (this.b(var0, 12)) {
            return ((NBTTagLongArray)this.x.get(var0)).g();
         }
      } catch (ClassCastException var3) {
         throw new ReportedException(this.a(var0, NBTTagLongArray.a, var3));
      }

      return new long[0];
   }

   public NBTTagCompound p(String var0) {
      try {
         if (this.b(var0, 10)) {
            return (NBTTagCompound)this.x.get(var0);
         }
      } catch (ClassCastException var3) {
         throw new ReportedException(this.a(var0, b, var3));
      }

      return new NBTTagCompound();
   }

   public NBTTagList c(String var0, int var1) {
      try {
         if (this.d(var0) == 9) {
            NBTTagList var2 = (NBTTagList)this.x.get(var0);
            if (!var2.isEmpty() && var2.f() != var1) {
               return new NBTTagList();
            }

            return var2;
         }
      } catch (ClassCastException var4) {
         throw new ReportedException(this.a(var0, NBTTagList.a, var4));
      }

      return new NBTTagList();
   }

   public boolean q(String var0) {
      return this.f(var0) != 0;
   }

   public void r(String var0) {
      this.x.remove(var0);
   }

   @Override
   public String toString() {
      return this.f_();
   }

   public boolean g() {
      return this.x.isEmpty();
   }

   private CrashReport a(String var0, NBTTagType<?> var1, ClassCastException var2) {
      CrashReport var3 = CrashReport.a(var2, "Reading NBT data");
      CrashReportSystemDetails var4 = var3.a("Corrupt NBT tag", 1);
      var4.a("Tag type found", () -> this.x.get(var0).c().a());
      var4.a("Tag type expected", var1::a);
      var4.a("Tag name", var0);
      return var3;
   }

   public NBTTagCompound h() {
      Map<String, NBTBase> var0 = Maps.newHashMap(Maps.transformValues(this.x, NBTBase::d));
      return new NBTTagCompound(var0);
   }

   @Override
   public boolean equals(Object var0) {
      if (this == var0) {
         return true;
      } else {
         return var0 instanceof NBTTagCompound && Objects.equals(this.x, ((NBTTagCompound)var0).x);
      }
   }

   @Override
   public int hashCode() {
      return this.x.hashCode();
   }

   private static void a(String var0, NBTBase var1, DataOutput var2) throws IOException {
      var2.writeByte(var1.b());
      if (var1.b() != 0) {
         var2.writeUTF(var0);
         var1.a(var2);
      }
   }

   static byte a(DataInput var0, NBTReadLimiter var1) throws IOException {
      return var0.readByte();
   }

   static String b(DataInput var0, NBTReadLimiter var1) throws IOException {
      return var0.readUTF();
   }

   static NBTBase a(NBTTagType<?> var0, String var1, DataInput var2, int var3, NBTReadLimiter var4) {
      try {
         return var0.b(var2, var3, var4);
      } catch (IOException var8) {
         CrashReport var6 = CrashReport.a(var8, "Loading NBT data");
         CrashReportSystemDetails var7 = var6.a("NBT Tag");
         var7.a("Tag name", var1);
         var7.a("Tag type", var0.a());
         throw new ReportedException(var6);
      }
   }

   public NBTTagCompound a(NBTTagCompound var0) {
      for(String var2 : var0.x.keySet()) {
         NBTBase var3 = var0.x.get(var2);
         if (var3.b() == 10) {
            if (this.b(var2, 10)) {
               NBTTagCompound var4 = this.p(var2);
               var4.a((NBTTagCompound)var3);
            } else {
               this.a(var2, var3.d());
            }
         } else {
            this.a(var2, var3.d());
         }
      }

      return this;
   }

   @Override
   public void a(TagVisitor var0) {
      var0.a(this);
   }

   protected Map<String, NBTBase> i() {
      return Collections.unmodifiableMap(this.x);
   }

   @Override
   public StreamTagVisitor.b a(StreamTagVisitor var0) {
      for(Entry<String, NBTBase> var2 : this.x.entrySet()) {
         NBTBase var3 = var2.getValue();
         NBTTagType<?> var4 = var3.c();
         StreamTagVisitor.a var5 = var0.a(var4);
         switch(var5) {
            case d:
               return StreamTagVisitor.b.c;
            case c:
               return var0.b();
            case b:
               break;
            default:
               var5 = var0.a(var4, var2.getKey());
               switch(var5) {
                  case d:
                     return StreamTagVisitor.b.c;
                  case c:
                     return var0.b();
                  case b:
                     break;
                  default:
                     StreamTagVisitor.b var6 = var3.a(var0);
                     switch(var6) {
                        case c:
                           return StreamTagVisitor.b.c;
                        case b:
                           return var0.b();
                     }
               }
         }
      }

      return var0.b();
   }
}
