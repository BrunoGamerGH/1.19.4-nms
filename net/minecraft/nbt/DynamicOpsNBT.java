package net.minecraft.nbt;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.RecordBuilder.AbstractStringBuilder;
import it.unimi.dsi.fastutil.bytes.ByteArrayList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class DynamicOpsNBT implements DynamicOps<NBTBase> {
   public static final DynamicOpsNBT a = new DynamicOpsNBT();
   private static final String b = "";

   protected DynamicOpsNBT() {
   }

   public NBTBase a() {
      return NBTTagEnd.b;
   }

   public <U> U a(DynamicOps<U> var0, NBTBase var1) {
      switch(var1.b()) {
         case 0:
            return (U)var0.empty();
         case 1:
            return (U)var0.createByte(((NBTNumber)var1).i());
         case 2:
            return (U)var0.createShort(((NBTNumber)var1).h());
         case 3:
            return (U)var0.createInt(((NBTNumber)var1).g());
         case 4:
            return (U)var0.createLong(((NBTNumber)var1).f());
         case 5:
            return (U)var0.createFloat(((NBTNumber)var1).k());
         case 6:
            return (U)var0.createDouble(((NBTNumber)var1).j());
         case 7:
            return (U)var0.createByteList(ByteBuffer.wrap(((NBTTagByteArray)var1).e()));
         case 8:
            return (U)var0.createString(var1.f_());
         case 9:
            return (U)this.convertList(var0, var1);
         case 10:
            return (U)this.convertMap(var0, var1);
         case 11:
            return (U)var0.createIntList(Arrays.stream(((NBTTagIntArray)var1).g()));
         case 12:
            return (U)var0.createLongList(Arrays.stream(((NBTTagLongArray)var1).g()));
         default:
            throw new IllegalStateException("Unknown tag type: " + var1);
      }
   }

   public DataResult<Number> a(NBTBase var0) {
      return var0 instanceof NBTNumber var1 ? DataResult.success(var1.l()) : DataResult.error(() -> "Not a number");
   }

   public NBTBase a(Number var0) {
      return NBTTagDouble.a(var0.doubleValue());
   }

   public NBTBase a(byte var0) {
      return NBTTagByte.a(var0);
   }

   public NBTBase a(short var0) {
      return NBTTagShort.a(var0);
   }

   public NBTBase a(int var0) {
      return NBTTagInt.a(var0);
   }

   public NBTBase a(long var0) {
      return NBTTagLong.a(var0);
   }

   public NBTBase a(float var0) {
      return NBTTagFloat.a(var0);
   }

   public NBTBase a(double var0) {
      return NBTTagDouble.a(var0);
   }

   public NBTBase a(boolean var0) {
      return NBTTagByte.a(var0);
   }

   public DataResult<String> b(NBTBase var0) {
      return var0 instanceof NBTTagString var1 ? DataResult.success(var1.f_()) : DataResult.error(() -> "Not a string");
   }

   public NBTBase a(String var0) {
      return NBTTagString.a(var0);
   }

   public DataResult<NBTBase> a(NBTBase var0, NBTBase var1) {
      return (DataResult<NBTBase>)k(var0)
         .map(var1x -> DataResult.success(var1x.a(var1).a()))
         .orElseGet(() -> DataResult.error(() -> "mergeToList called with not a list: " + var0, var0));
   }

   public DataResult<NBTBase> a(NBTBase var0, List<NBTBase> var1) {
      return (DataResult<NBTBase>)k(var0)
         .map(var1x -> DataResult.success(var1x.a(var1).a()))
         .orElseGet(() -> DataResult.error(() -> "mergeToList called with not a list: " + var0, var0));
   }

   public DataResult<NBTBase> a(NBTBase var0, NBTBase var1, NBTBase var2) {
      if (!(var0 instanceof NBTTagCompound) && !(var0 instanceof NBTTagEnd)) {
         return DataResult.error(() -> "mergeToMap called with not a map: " + var0, var0);
      } else if (!(var1 instanceof NBTTagString)) {
         return DataResult.error(() -> "key is not a string: " + var1, var0);
      } else {
         NBTTagCompound var3 = new NBTTagCompound();
         if (var0 instanceof NBTTagCompound var4) {
            var4.e().forEach(var2x -> var3.a(var2x, var4.c(var2x)));
         }

         var3.a(var1.f_(), var2);
         return DataResult.success(var3);
      }
   }

   public DataResult<NBTBase> a(NBTBase var0, MapLike<NBTBase> var1) {
      if (!(var0 instanceof NBTTagCompound) && !(var0 instanceof NBTTagEnd)) {
         return DataResult.error(() -> "mergeToMap called with not a map: " + var0, var0);
      } else {
         NBTTagCompound var2 = new NBTTagCompound();
         if (var0 instanceof NBTTagCompound var3) {
            var3.e().forEach(var2x -> var2.a(var2x, var3.c(var2x)));
         }

         List<NBTBase> var3 = Lists.newArrayList();
         var1.entries().forEach(var2x -> {
            NBTBase var3x = (NBTBase)var2x.getFirst();
            if (!(var3x instanceof NBTTagString)) {
               var3.add(var3x);
            } else {
               var2.a(var3x.f_(), (NBTBase)var2x.getSecond());
            }
         });
         return !var3.isEmpty() ? DataResult.error(() -> "some keys are not strings: " + var3, var2) : DataResult.success(var2);
      }
   }

   public DataResult<Stream<Pair<NBTBase, NBTBase>>> c(NBTBase var0) {
      return var0 instanceof NBTTagCompound var1
         ? DataResult.success(var1.e().stream().map(var1x -> Pair.of(this.a(var1x), var1.c(var1x))))
         : DataResult.error(() -> "Not a map: " + var0);
   }

   public DataResult<Consumer<BiConsumer<NBTBase, NBTBase>>> d(NBTBase var0) {
      return var0 instanceof NBTTagCompound var1
         ? DataResult.success((Consumer<BiConsumer>)var1x -> var1.e().forEach(var2x -> var1x.accept(this.a(var2x), var1.c(var2x))))
         : DataResult.error(() -> "Not a map: " + var0);
   }

   public DataResult<MapLike<NBTBase>> e(NBTBase var0) {
      return var0 instanceof NBTTagCompound var1 ? DataResult.success(new MapLike<NBTBase>() {
         @Nullable
         public NBTBase a(NBTBase var0) {
            return var1.c(var0.f_());
         }

         @Nullable
         public NBTBase a(String var0) {
            return var1.c(var0);
         }

         public Stream<Pair<NBTBase, NBTBase>> entries() {
            return var1.e().stream().map(var1xx -> Pair.of(DynamicOpsNBT.this.a(var1xx), var1.c(var1xx)));
         }

         @Override
         public String toString() {
            return "MapLike[" + var1 + "]";
         }
      }) : DataResult.error(() -> "Not a map: " + var0);
   }

   public NBTBase a(Stream<Pair<NBTBase, NBTBase>> var0) {
      NBTTagCompound var1 = new NBTTagCompound();
      var0.forEach(var1x -> var1.a(((NBTBase)var1x.getFirst()).f_(), (NBTBase)var1x.getSecond()));
      return var1;
   }

   private static NBTBase a(NBTTagCompound var0) {
      if (var0.f() == 1) {
         NBTBase var1 = var0.c("");
         if (var1 != null) {
            return var1;
         }
      }

      return var0;
   }

   public DataResult<Stream<NBTBase>> f(NBTBase var0) {
      if (var0 instanceof NBTTagList var1) {
         return var1.f() == 10 ? DataResult.success(var1.stream().map(var0x -> a((NBTTagCompound)var0x))) : DataResult.success(var1.stream());
      } else {
         return var0 instanceof NBTList var1 ? DataResult.success(var1.stream().map(var0x -> var0x)) : DataResult.error(() -> "Not a list");
      }
   }

   public DataResult<Consumer<Consumer<NBTBase>>> g(NBTBase var0) {
      if (var0 instanceof NBTTagList var1) {
         return var1.f() == 10
            ? DataResult.success((Consumer<Consumer>)var1x -> var1.forEach(var1xx -> var1x.accept(a((NBTTagCompound)var1xx))))
            : DataResult.success(var1::forEach);
      } else {
         return var0 instanceof NBTList var1 ? DataResult.success(var1::forEach) : DataResult.error(() -> "Not a list: " + var0);
      }
   }

   public DataResult<ByteBuffer> h(NBTBase var0) {
      return var0 instanceof NBTTagByteArray var1 ? DataResult.success(ByteBuffer.wrap(var1.e())) : super.getByteBuffer(var0);
   }

   public NBTBase a(ByteBuffer var0) {
      return new NBTTagByteArray(DataFixUtils.toArray(var0));
   }

   public DataResult<IntStream> i(NBTBase var0) {
      return var0 instanceof NBTTagIntArray var1 ? DataResult.success(Arrays.stream(var1.g())) : super.getIntStream(var0);
   }

   public NBTBase a(IntStream var0) {
      return new NBTTagIntArray(var0.toArray());
   }

   public DataResult<LongStream> j(NBTBase var0) {
      return var0 instanceof NBTTagLongArray var1 ? DataResult.success(Arrays.stream(var1.g())) : super.getLongStream(var0);
   }

   public NBTBase a(LongStream var0) {
      return new NBTTagLongArray(var0.toArray());
   }

   public NBTBase b(Stream<NBTBase> var0) {
      return DynamicOpsNBT.d.a.a(var0).a();
   }

   public NBTBase a(NBTBase var0, String var1) {
      if (var0 instanceof NBTTagCompound var2) {
         NBTTagCompound var3 = new NBTTagCompound();
         var2.e().stream().filter(var1x -> !Objects.equals(var1x, var1)).forEach(var2x -> var3.a(var2x, var2.c(var2x)));
         return var3;
      } else {
         return var0;
      }
   }

   @Override
   public String toString() {
      return "NBT";
   }

   public RecordBuilder<NBTBase> mapBuilder() {
      return new DynamicOpsNBT.h();
   }

   private static Optional<DynamicOpsNBT.f> k(NBTBase var0) {
      if (var0 instanceof NBTTagEnd) {
         return Optional.of(DynamicOpsNBT.d.a);
      } else {
         if (var0 instanceof NBTList var1) {
            if (var1.isEmpty()) {
               return Optional.of(DynamicOpsNBT.d.a);
            }

            if (var1 instanceof NBTTagList var2) {
               return switch(var2.f()) {
                  case 0 -> Optional.of(DynamicOpsNBT.d.a);
                  case 10 -> Optional.of(new DynamicOpsNBT.b(var2));
                  default -> Optional.of(new DynamicOpsNBT.c(var2));
               };
            }

            if (var1 instanceof NBTTagByteArray var2) {
               return Optional.of(new DynamicOpsNBT.a(var2.e()));
            }

            if (var1 instanceof NBTTagIntArray var2) {
               return Optional.of(new DynamicOpsNBT.e(var2.g()));
            }

            if (var1 instanceof NBTTagLongArray var2) {
               return Optional.of(new DynamicOpsNBT.g(var2.g()));
            }
         }

         return Optional.empty();
      }
   }

   static class a implements DynamicOpsNBT.f {
      private final ByteArrayList a = new ByteArrayList();

      public a(byte var0) {
         this.a.add(var0);
      }

      public a(byte[] var0) {
         this.a.addElements(0, var0);
      }

      @Override
      public DynamicOpsNBT.f a(NBTBase var0) {
         if (var0 instanceof NBTTagByte var1) {
            this.a.add(var1.i());
            return this;
         } else {
            return new DynamicOpsNBT.b(this.a).a(var0);
         }
      }

      @Override
      public NBTBase a() {
         return new NBTTagByteArray(this.a.toByteArray());
      }
   }

   static class b implements DynamicOpsNBT.f {
      private final NBTTagList a = new NBTTagList();

      public b() {
      }

      public b(Collection<NBTBase> var0) {
         this.a.addAll(var0);
      }

      public b(IntArrayList var0) {
         var0.forEach(var0x -> this.a.add(c(NBTTagInt.a(var0x))));
      }

      public b(ByteArrayList var0) {
         var0.forEach(var0x -> this.a.add(c(NBTTagByte.a(var0x))));
      }

      public b(LongArrayList var0) {
         var0.forEach(var0x -> this.a.add(c(NBTTagLong.a(var0x))));
      }

      private static boolean a(NBTTagCompound var0) {
         return var0.f() == 1 && var0.e("");
      }

      private static NBTBase b(NBTBase var0) {
         if (var0 instanceof NBTTagCompound var1 && !a(var1)) {
            return var1;
         }

         return c(var0);
      }

      private static NBTTagCompound c(NBTBase var0) {
         NBTTagCompound var1 = new NBTTagCompound();
         var1.a("", var0);
         return var1;
      }

      @Override
      public DynamicOpsNBT.f a(NBTBase var0) {
         this.a.add(b(var0));
         return this;
      }

      @Override
      public NBTBase a() {
         return this.a;
      }
   }

   static class c implements DynamicOpsNBT.f {
      private final NBTTagList a = new NBTTagList();

      c(NBTBase var0) {
         this.a.add(var0);
      }

      c(NBTTagList var0) {
         this.a.addAll(var0);
      }

      @Override
      public DynamicOpsNBT.f a(NBTBase var0) {
         if (var0.b() != this.a.f()) {
            return new DynamicOpsNBT.b().a(this.a).a(var0);
         } else {
            this.a.add(var0);
            return this;
         }
      }

      @Override
      public NBTBase a() {
         return this.a;
      }
   }

   static class d implements DynamicOpsNBT.f {
      public static final DynamicOpsNBT.d a = new DynamicOpsNBT.d();

      private d() {
      }

      @Override
      public DynamicOpsNBT.f a(NBTBase var0) {
         if (var0 instanceof NBTTagCompound var1) {
            return new DynamicOpsNBT.b().a(var1);
         } else if (var0 instanceof NBTTagByte var1) {
            return new DynamicOpsNBT.a(var1.i());
         } else if (var0 instanceof NBTTagInt var1) {
            return new DynamicOpsNBT.e(var1.g());
         } else {
            return (DynamicOpsNBT.f)(var0 instanceof NBTTagLong var1 ? new DynamicOpsNBT.g(var1.f()) : new DynamicOpsNBT.c(var0));
         }
      }

      @Override
      public NBTBase a() {
         return new NBTTagList();
      }
   }

   static class e implements DynamicOpsNBT.f {
      private final IntArrayList a = new IntArrayList();

      public e(int var0) {
         this.a.add(var0);
      }

      public e(int[] var0) {
         this.a.addElements(0, var0);
      }

      @Override
      public DynamicOpsNBT.f a(NBTBase var0) {
         if (var0 instanceof NBTTagInt var1) {
            this.a.add(var1.g());
            return this;
         } else {
            return new DynamicOpsNBT.b(this.a).a(var0);
         }
      }

      @Override
      public NBTBase a() {
         return new NBTTagIntArray(this.a.toIntArray());
      }
   }

   interface f {
      DynamicOpsNBT.f a(NBTBase var1);

      default DynamicOpsNBT.f a(Iterable<NBTBase> var0) {
         DynamicOpsNBT.f var1 = this;

         for(NBTBase var3 : var0) {
            var1 = var1.a(var3);
         }

         return var1;
      }

      default DynamicOpsNBT.f a(Stream<NBTBase> var0) {
         return this.a(var0::iterator);
      }

      NBTBase a();
   }

   static class g implements DynamicOpsNBT.f {
      private final LongArrayList a = new LongArrayList();

      public g(long var0) {
         this.a.add(var0);
      }

      public g(long[] var0) {
         this.a.addElements(0, var0);
      }

      @Override
      public DynamicOpsNBT.f a(NBTBase var0) {
         if (var0 instanceof NBTTagLong var1) {
            this.a.add(var1.f());
            return this;
         } else {
            return new DynamicOpsNBT.b(this.a).a(var0);
         }
      }

      @Override
      public NBTBase a() {
         return new NBTTagLongArray(this.a.toLongArray());
      }
   }

   class h extends AbstractStringBuilder<NBTBase, NBTTagCompound> {
      protected h() {
         super(DynamicOpsNBT.this);
      }

      protected NBTTagCompound a() {
         return new NBTTagCompound();
      }

      protected NBTTagCompound a(String var0, NBTBase var1, NBTTagCompound var2) {
         var2.a(var0, var1);
         return var2;
      }

      protected DataResult<NBTBase> a(NBTTagCompound var0, NBTBase var1) {
         if (var1 == null || var1 == NBTTagEnd.b) {
            return DataResult.success(var0);
         } else if (!(var1 instanceof NBTTagCompound)) {
            return DataResult.error(() -> "mergeToMap called with not a map: " + var1, var1);
         } else {
            NBTTagCompound var2 = (NBTTagCompound)var1;
            NBTTagCompound var3 = new NBTTagCompound(Maps.newHashMap(var2.i()));

            for(Entry<String, NBTBase> var5 : var0.i().entrySet()) {
               var3.a(var5.getKey(), var5.getValue());
            }

            return DataResult.success(var3);
         }
      }
   }
}
