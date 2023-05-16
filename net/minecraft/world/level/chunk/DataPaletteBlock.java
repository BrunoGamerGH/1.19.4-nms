package net.minecraft.world.level.chunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.LongStream;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.network.PacketDataSerializer;
import net.minecraft.util.DataBits;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.MathHelper;
import net.minecraft.util.SimpleBitStorage;
import net.minecraft.util.ThreadingDetector;
import net.minecraft.util.ZeroBitStorage;

public class DataPaletteBlock<T> implements DataPaletteExpandable<T>, PalettedContainerRO<T> {
   private static final int a = 0;
   private final DataPaletteExpandable<T> b = (var0x, var1x) -> 0;
   public final Registry<T> c;
   private volatile DataPaletteBlock.c<T> d;
   private final DataPaletteBlock.d e;
   private final ThreadingDetector f = new ThreadingDetector("PalettedContainer");

   public void a() {
      this.f.a();
   }

   public void b() {
      this.f.b();
   }

   public static <T> Codec<DataPaletteBlock<T>> a(Registry<T> var0, Codec<T> var1, DataPaletteBlock.d var2, T var3) {
      PalettedContainerRO.b<T, DataPaletteBlock<T>> var4 = DataPaletteBlock::a;
      return a(var0, var1, var2, var3, var4);
   }

   public static <T> Codec<PalettedContainerRO<T>> b(Registry<T> var0, Codec<T> var1, DataPaletteBlock.d var2, T var3) {
      PalettedContainerRO.b<T, PalettedContainerRO<T>> var4 = (var0x, var1x, var2x) -> a(var0x, var1x, var2x).map(var0xx -> var0xx);
      return a(var0, var1, var2, var3, var4);
   }

   private static <T, C extends PalettedContainerRO<T>> Codec<C> a(
      Registry<T> var0, Codec<T> var1, DataPaletteBlock.d var2, T var3, PalettedContainerRO.b<T, C> var4
   ) {
      return RecordCodecBuilder.create(
            var2x -> var2x.group(
                     var1.mapResult(ExtraCodecs.a(var3)).listOf().fieldOf("palette").forGetter(PalettedContainerRO.a::a),
                     Codec.LONG_STREAM.optionalFieldOf("data").forGetter(PalettedContainerRO.a::b)
                  )
                  .apply(var2x, PalettedContainerRO.a::new)
         )
         .comapFlatMap(var3x -> var4.read(var0, var2, var3x), var2x -> var2x.a(var0, var2));
   }

   public DataPaletteBlock(Registry<T> var0, DataPaletteBlock.d var1, DataPaletteBlock.a<T> var2, DataBits var3, List<T> var4) {
      this.c = var0;
      this.e = var1;
      this.d = new DataPaletteBlock.c<>(var2, var3, var2.a().create(var2.b(), var0, this, var4));
   }

   private DataPaletteBlock(Registry<T> var0, DataPaletteBlock.d var1, DataPaletteBlock.c<T> var2) {
      this.c = var0;
      this.e = var1;
      this.d = var2;
   }

   public DataPaletteBlock(Registry<T> var0, T var1, DataPaletteBlock.d var2) {
      this.e = var2;
      this.c = var0;
      this.d = this.a(null, 0);
      this.d.c.a(var1);
   }

   private DataPaletteBlock.c<T> a(@Nullable DataPaletteBlock.c<T> var0, int var1) {
      DataPaletteBlock.a<T> var2 = this.e.a(this.c, var1);
      return var0 != null && var2.equals(var0.c()) ? var0 : var2.a(this.c, this, this.e.a());
   }

   @Override
   public int onResize(int var0, T var1) {
      DataPaletteBlock.c<T> var2 = this.d;
      DataPaletteBlock.c<T> var3 = this.a(var2, var0);
      var3.a(var2.c, var2.b);
      this.d = var3;
      return var3.c.a(var1);
   }

   public T a(int var0, int var1, int var2, T var3) {
      this.a();

      Object var5;
      try {
         var5 = this.a(this.e.a(var0, var1, var2), var3);
      } finally {
         this.b();
      }

      return (T)var5;
   }

   public T b(int var0, int var1, int var2, T var3) {
      return this.a(this.e.a(var0, var1, var2), var3);
   }

   private T a(int var0, T var1) {
      int var2 = this.d.c.a(var1);
      int var3 = this.d.b.a(var0, var2);
      return this.d.c.a(var3);
   }

   public void c(int var0, int var1, int var2, T var3) {
      this.a();

      try {
         this.b(this.e.a(var0, var1, var2), var3);
      } finally {
         this.b();
      }
   }

   private void b(int var0, T var1) {
      int var2 = this.d.c.a(var1);
      this.d.b.b(var0, var2);
   }

   @Override
   public T a(int var0, int var1, int var2) {
      return this.a(this.e.a(var0, var1, var2));
   }

   protected T a(int var0) {
      DataPaletteBlock.c<T> var1 = this.d;
      return var1.c.a(var1.b.a(var0));
   }

   @Override
   public void a(Consumer<T> var0) {
      DataPalette<T> var1 = this.d.e();
      IntSet var2 = new IntArraySet();
      this.d.b.a(var2::add);
      var2.forEach(var2x -> var0.accept(var1.a(var2x)));
   }

   public void a(PacketDataSerializer var0) {
      this.a();

      try {
         int var1 = var0.readByte();
         DataPaletteBlock.c<T> var2 = this.a(this.d, var1);
         var2.c.a(var0);
         var0.b(var2.b.a());
         this.d = var2;
      } finally {
         this.b();
      }
   }

   @Override
   public void b(PacketDataSerializer var0) {
      this.a();

      try {
         this.d.a(var0);
      } finally {
         this.b();
      }
   }

   private static <T> DataResult<DataPaletteBlock<T>> a(Registry<T> var0, DataPaletteBlock.d var1, PalettedContainerRO.a<T> var2) {
      List<T> var3 = var2.a();
      int var4 = var1.a();
      int var5 = var1.b(var0, var3.size());
      DataPaletteBlock.a<T> var6 = var1.a(var0, var5);
      DataBits var7;
      if (var5 == 0) {
         var7 = new ZeroBitStorage(var4);
      } else {
         Optional<LongStream> var8 = var2.b();
         if (var8.isEmpty()) {
            return DataResult.error(() -> "Missing values for non-zero storage");
         }

         long[] var9 = var8.get().toArray();

         try {
            if (var6.a() == DataPaletteBlock.d.f) {
               DataPalette<T> var10 = new DataPaletteHash<>(var0, var5, (var0x, var1x) -> 0, var3);
               SimpleBitStorage var11 = new SimpleBitStorage(var5, var4, var9);
               int[] var12 = new int[var4];
               var11.a(var12);
               a(var12, var2x -> var0.a(var10.a(var2x)));
               var7 = new SimpleBitStorage(var6.b(), var4, var12);
            } else {
               var7 = new SimpleBitStorage(var6.b(), var4, var9);
            }
         } catch (SimpleBitStorage.a var13) {
            return DataResult.error(() -> "Failed to read PalettedContainer: " + var13.getMessage());
         }
      }

      return DataResult.success(new DataPaletteBlock<>(var0, var1, var6, var7, var3));
   }

   @Override
   public PalettedContainerRO.a<T> a(Registry<T> var0, DataPaletteBlock.d var1) {
      this.a();

      PalettedContainerRO.a var12;
      try {
         DataPaletteHash<T> var2 = new DataPaletteHash<>(var0, this.d.b.c(), this.b);
         int var3 = var1.a();
         int[] var4 = new int[var3];
         this.d.b.a(var4);
         a(var4, var1x -> var2.a(this.d.c.a(var1x)));
         int var5 = var1.b(var0, var2.b());
         Optional<LongStream> var6;
         if (var5 != 0) {
            SimpleBitStorage var7 = new SimpleBitStorage(var5, var3, var4);
            var6 = Optional.of(Arrays.stream(var7.a()));
         } else {
            var6 = Optional.empty();
         }

         var12 = new PalettedContainerRO.a<>(var2.d(), var6);
      } finally {
         this.b();
      }

      return var12;
   }

   private static <T> void a(int[] var0, IntUnaryOperator var1) {
      int var2 = -1;
      int var3 = -1;

      for(int var4 = 0; var4 < var0.length; ++var4) {
         int var5 = var0[var4];
         if (var5 != var2) {
            var2 = var5;
            var3 = var1.applyAsInt(var5);
         }

         var0[var4] = var3;
      }
   }

   @Override
   public int c() {
      return this.d.a();
   }

   @Override
   public boolean a(Predicate<T> var0) {
      return this.d.c.a(var0);
   }

   public DataPaletteBlock<T> d() {
      return new DataPaletteBlock<>(this.c, this.e, this.d.b());
   }

   @Override
   public DataPaletteBlock<T> e() {
      return new DataPaletteBlock<>(this.c, this.d.c.a(0), this.e);
   }

   @Override
   public void a(DataPaletteBlock.b<T> var0) {
      if (this.d.c.b() == 1) {
         var0.accept(this.d.c.a(0), this.d.b.b());
      } else {
         Int2IntOpenHashMap var1 = new Int2IntOpenHashMap();
         this.d.b.a(var1x -> var1.addTo(var1x, 1));
         var1.int2IntEntrySet().forEach(var1x -> var0.accept(this.d.c.a(var1x.getIntKey()), var1x.getIntValue()));
      }
   }

   static record a<T>(DataPalette.a factory, int bits) {
      private final DataPalette.a a;
      private final int b;

      a(DataPalette.a var0, int var1) {
         this.a = var0;
         this.b = var1;
      }

      public DataPaletteBlock.c<T> a(Registry<T> var0, DataPaletteExpandable<T> var1, int var2) {
         DataBits var3 = (DataBits)(this.b == 0 ? new ZeroBitStorage(var2) : new SimpleBitStorage(this.b, var2));
         DataPalette<T> var4 = this.a.create(this.b, var0, var1, List.of());
         return new DataPaletteBlock.c<>(this, var3, var4);
      }
   }

   @FunctionalInterface
   public interface b<T> {
      void accept(T var1, int var2);
   }

   static record c<T>(DataPaletteBlock.a<T> configuration, DataBits storage, DataPalette<T> palette) {
      private final DataPaletteBlock.a<T> a;
      final DataBits b;
      final DataPalette<T> c;

      c(DataPaletteBlock.a<T> var0, DataBits var1, DataPalette<T> var2) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
      }

      public void a(DataPalette<T> var0, DataBits var1) {
         for(int var2 = 0; var2 < var1.b(); ++var2) {
            T var3 = var0.a(var1.a(var2));
            this.b.b(var2, this.c.a(var3));
         }
      }

      public int a() {
         return 1 + this.c.a() + PacketDataSerializer.a(this.b.b()) + this.b.a().length * 8;
      }

      public void a(PacketDataSerializer var0) {
         var0.writeByte(this.b.c());
         this.c.b(var0);
         var0.a(this.b.a());
      }

      public DataPaletteBlock.c<T> b() {
         return new DataPaletteBlock.c<>(this.a, this.b.d(), this.c.c());
      }

      public DataPaletteBlock.a<T> c() {
         return this.a;
      }

      public DataBits d() {
         return this.b;
      }

      public DataPalette<T> e() {
         return this.c;
      }
   }

   public abstract static class d {
      public static final DataPalette.a a = SingleValuePalette::a;
      public static final DataPalette.a b = DataPaletteLinear::a;
      public static final DataPalette.a c = DataPaletteHash::a;
      static final DataPalette.a f = DataPaletteGlobal::a;
      public static final DataPaletteBlock.d d = new DataPaletteBlock.d(4) {
         @Override
         public <A> DataPaletteBlock.a<A> a(Registry<A> var0, int var1) {
            return switch(var1) {
               case 0 -> new DataPaletteBlock.a(a, var1);
               case 1, 2, 3, 4 -> new DataPaletteBlock.a(b, 4);
               case 5, 6, 7, 8 -> new DataPaletteBlock.a(c, var1);
               default -> new DataPaletteBlock.a(DataPaletteBlock.d.f, MathHelper.e(var0.b()));
            };
         }
      };
      public static final DataPaletteBlock.d e = new DataPaletteBlock.d(2) {
         @Override
         public <A> DataPaletteBlock.a<A> a(Registry<A> var0, int var1) {
            return switch(var1) {
               case 0 -> new DataPaletteBlock.a(a, var1);
               case 1, 2, 3 -> new DataPaletteBlock.a(b, var1);
               default -> new DataPaletteBlock.a(DataPaletteBlock.d.f, MathHelper.e(var0.b()));
            };
         }
      };
      private final int g;

      d(int var0) {
         this.g = var0;
      }

      public int a() {
         return 1 << this.g * 3;
      }

      public int a(int var0, int var1, int var2) {
         return (var1 << this.g | var2) << this.g | var0;
      }

      public abstract <A> DataPaletteBlock.a<A> a(Registry<A> var1, int var2);

      <A> int b(Registry<A> var0, int var1) {
         int var2 = MathHelper.e(var1);
         DataPaletteBlock.a<A> var3 = this.a(var0, var2);
         return var3.a() == f ? var2 : var3.b();
      }
   }
}
