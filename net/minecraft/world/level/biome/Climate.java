package net.minecraft.world.level.biome;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.QuartPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;

public class Climate {
   private static final boolean b = false;
   private static final float c = 10000.0F;
   @VisibleForTesting
   protected static final int a = 7;

   public static Climate.h a(float var0, float var1, float var2, float var3, float var4, float var5) {
      return new Climate.h(a(var0), a(var1), a(var2), a(var3), a(var4), a(var5));
   }

   public static Climate.d a(float var0, float var1, float var2, float var3, float var4, float var5, float var6) {
      return new Climate.d(Climate.b.a(var0), Climate.b.a(var1), Climate.b.a(var2), Climate.b.a(var3), Climate.b.a(var4), Climate.b.a(var5), a(var6));
   }

   public static Climate.d a(Climate.b var0, Climate.b var1, Climate.b var2, Climate.b var3, Climate.b var4, Climate.b var5, float var6) {
      return new Climate.d(var0, var1, var2, var3, var4, var5, a(var6));
   }

   public static long a(float var0) {
      return (long)(var0 * 10000.0F);
   }

   public static float a(long var0) {
      return (float)var0 / 10000.0F;
   }

   public static Climate.Sampler a() {
      DensityFunction var0 = DensityFunctions.a();
      return new Climate.Sampler(var0, var0, var0, var0, var0, var0, List.of());
   }

   public static BlockPosition a(List<Climate.d> var0, Climate.Sampler var1) {
      return (new Climate.g(var0, var1)).a.a();
   }

   public static record Sampler(
      DensityFunction temperature,
      DensityFunction humidity,
      DensityFunction continentalness,
      DensityFunction erosion,
      DensityFunction depth,
      DensityFunction weirdness,
      List<Climate.d> spawnTarget
   ) {
      private final DensityFunction a;
      private final DensityFunction b;
      private final DensityFunction c;
      private final DensityFunction d;
      private final DensityFunction e;
      private final DensityFunction f;
      private final List<Climate.d> g;

      public Sampler(
         DensityFunction var0,
         DensityFunction var1,
         DensityFunction var2,
         DensityFunction var3,
         DensityFunction var4,
         DensityFunction var5,
         List<Climate.d> var6
      ) {
         this.a = var0;
         this.b = var1;
         this.c = var2;
         this.d = var3;
         this.e = var4;
         this.f = var5;
         this.g = var6;
      }

      public Climate.h a(int var0, int var1, int var2) {
         int var3 = QuartPos.c(var0);
         int var4 = QuartPos.c(var1);
         int var5 = QuartPos.c(var2);
         DensityFunction.e var6 = new DensityFunction.e(var3, var4, var5);
         return Climate.a(
            (float)this.a.a(var6), (float)this.b.a(var6), (float)this.c.a(var6), (float)this.d.a(var6), (float)this.e.a(var6), (float)this.f.a(var6)
         );
      }

      public BlockPosition a() {
         return this.g.isEmpty() ? BlockPosition.b : Climate.a(this.g, this);
      }

      public DensityFunction b() {
         return this.a;
      }

      public DensityFunction c() {
         return this.b;
      }

      public DensityFunction d() {
         return this.c;
      }

      public DensityFunction e() {
         return this.d;
      }

      public DensityFunction f() {
         return this.e;
      }

      public DensityFunction g() {
         return this.f;
      }

      public List<Climate.d> h() {
         return this.g;
      }
   }

   interface a<T> {
      long distance(Climate.e.b<T> var1, long[] var2);
   }

   public static record b(long min, long max) {
      private final long b;
      private final long c;
      public static final Codec<Climate.b> a = ExtraCodecs.a(
         Codec.floatRange(-2.0F, 2.0F),
         "min",
         "max",
         (var0, var1) -> var0.compareTo(var1) > 0
               ? DataResult.error(() -> "Cannon construct interval, min > max (" + var0 + " > " + var1 + ")")
               : DataResult.success(new Climate.b(Climate.a(var0), Climate.a(var1))),
         var0 -> Climate.a(var0.a()),
         var0 -> Climate.a(var0.b())
      );

      public b(long var0, long var2) {
         this.b = var0;
         this.c = var2;
      }

      public static Climate.b a(float var0) {
         return a(var0, var0);
      }

      public static Climate.b a(float var0, float var1) {
         if (var0 > var1) {
            throw new IllegalArgumentException("min > max: " + var0 + " " + var1);
         } else {
            return new Climate.b(Climate.a(var0), Climate.a(var1));
         }
      }

      public static Climate.b a(Climate.b var0, Climate.b var1) {
         if (var0.a() > var1.b()) {
            throw new IllegalArgumentException("min > max: " + var0 + " " + var1);
         } else {
            return new Climate.b(var0.a(), var1.b());
         }
      }

      @Override
      public String toString() {
         return this.b == this.c ? String.format(Locale.ROOT, "%d", this.b) : String.format(Locale.ROOT, "[%d-%d]", this.b, this.c);
      }

      public long a(long var0) {
         long var2 = var0 - this.c;
         long var4 = this.b - var0;
         return var2 > 0L ? var2 : Math.max(var4, 0L);
      }

      public long a(Climate.b var0) {
         long var1 = var0.a() - this.c;
         long var3 = this.b - var0.b();
         return var1 > 0L ? var1 : Math.max(var3, 0L);
      }

      public Climate.b b(@Nullable Climate.b var0) {
         return var0 == null ? this : new Climate.b(Math.min(this.b, var0.a()), Math.max(this.c, var0.b()));
      }

      public long a() {
         return this.b;
      }

      public long b() {
         return this.c;
      }
   }

   public static class c<T> {
      private final List<Pair<Climate.d, T>> a;
      private final Climate.e<T> b;

      public static <T> Codec<Climate.c<T>> a(MapCodec<T> var0) {
         return ExtraCodecs.a(
               RecordCodecBuilder.create(
                     var1 -> var1.group(Climate.d.a.fieldOf("parameters").forGetter(Pair::getFirst), var0.forGetter(Pair::getSecond)).apply(var1, Pair::of)
                  )
                  .listOf()
            )
            .xmap(Climate.c::new, Climate.c::a);
      }

      public c(List<Pair<Climate.d, T>> var0) {
         this.a = var0;
         this.b = Climate.e.a(var0);
      }

      public List<Pair<Climate.d, T>> a() {
         return this.a;
      }

      public T a(Climate.h var0) {
         return this.c(var0);
      }

      @VisibleForTesting
      public T b(Climate.h var0) {
         Iterator<Pair<Climate.d, T>> var1 = this.a().iterator();
         Pair<Climate.d, T> var2 = (Pair)var1.next();
         long var3 = ((Climate.d)var2.getFirst()).a(var0);
         T var5 = (T)var2.getSecond();

         while(var1.hasNext()) {
            Pair<Climate.d, T> var6 = (Pair)var1.next();
            long var7 = ((Climate.d)var6.getFirst()).a(var0);
            if (var7 < var3) {
               var3 = var7;
               var5 = (T)var6.getSecond();
            }
         }

         return var5;
      }

      public T c(Climate.h var0) {
         return this.a(var0, Climate.e.b::a);
      }

      protected T a(Climate.h var0, Climate.a<T> var1) {
         return this.b.a(var0, var1);
      }
   }

   public static record d(
      Climate.b temperature, Climate.b humidity, Climate.b continentalness, Climate.b erosion, Climate.b depth, Climate.b weirdness, long offset
   ) {
      private final Climate.b b;
      private final Climate.b c;
      private final Climate.b d;
      private final Climate.b e;
      private final Climate.b f;
      private final Climate.b g;
      private final long h;
      public static final Codec<Climate.d> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Climate.b.a.fieldOf("temperature").forGetter(var0x -> var0x.b),
                  Climate.b.a.fieldOf("humidity").forGetter(var0x -> var0x.c),
                  Climate.b.a.fieldOf("continentalness").forGetter(var0x -> var0x.d),
                  Climate.b.a.fieldOf("erosion").forGetter(var0x -> var0x.e),
                  Climate.b.a.fieldOf("depth").forGetter(var0x -> var0x.f),
                  Climate.b.a.fieldOf("weirdness").forGetter(var0x -> var0x.g),
                  Codec.floatRange(0.0F, 1.0F).fieldOf("offset").xmap(Climate::a, Climate::a).forGetter(var0x -> var0x.h)
               )
               .apply(var0, Climate.d::new)
      );

      public d(Climate.b var0, Climate.b var1, Climate.b var2, Climate.b var3, Climate.b var4, Climate.b var5, long var6) {
         this.b = var0;
         this.c = var1;
         this.d = var2;
         this.e = var3;
         this.f = var4;
         this.g = var5;
         this.h = var6;
      }

      long a(Climate.h var0) {
         return MathHelper.a(this.b.a(var0.a))
            + MathHelper.a(this.c.a(var0.b))
            + MathHelper.a(this.d.a(var0.c))
            + MathHelper.a(this.e.a(var0.d))
            + MathHelper.a(this.f.a(var0.e))
            + MathHelper.a(this.g.a(var0.f))
            + MathHelper.a(this.h);
      }

      protected List<Climate.b> a() {
         return ImmutableList.of(this.b, this.c, this.d, this.e, this.f, this.g, new Climate.b(this.h, this.h));
      }
   }

   protected static final class e<T> {
      private static final int a = 6;
      private final Climate.e.b<T> b;
      private final ThreadLocal<Climate.e.a<T>> c = new ThreadLocal<>();

      private e(Climate.e.b<T> var0) {
         this.b = var0;
      }

      public static <T> Climate.e<T> a(List<Pair<Climate.d, T>> var0) {
         if (var0.isEmpty()) {
            throw new IllegalArgumentException("Need at least one value to build the search tree.");
         } else {
            int var1 = ((Climate.d)((Pair)var0.get(0)).getFirst()).a().size();
            if (var1 != 7) {
               throw new IllegalStateException("Expecting parameter space to be 7, got " + var1);
            } else {
               List<Climate.e.a<T>> var2 = var0.stream()
                  .map(var0x -> new Climate.e.a<>((Climate.d)var0x.getFirst(), var0x.getSecond()))
                  .collect(Collectors.toCollection(ArrayList::new));
               return new Climate.e<>(a(var1, var2));
            }
         }
      }

      private static <T> Climate.e.b<T> a(int var0, List<? extends Climate.e.b<T>> var1) {
         if (var1.isEmpty()) {
            throw new IllegalStateException("Need at least one child to build a node");
         } else if (var1.size() == 1) {
            return var1.get(0);
         } else if (var1.size() <= 6) {
            var1.sort(Comparator.comparingLong(var1x -> {
               long var2x = 0L;

               for(int var4x = 0; var4x < var0; ++var4x) {
                  Climate.b var5x = var1x.a[var4x];
                  var2x += Math.abs((var5x.a() + var5x.b()) / 2L);
               }

               return var2x;
            }));
            return new Climate.e.c<>(var1);
         } else {
            long var2 = Long.MAX_VALUE;
            int var4 = -1;
            List<Climate.e.c<T>> var5 = null;

            for(int var6 = 0; var6 < var0; ++var6) {
               a(var1, var0, var6, false);
               List<Climate.e.c<T>> var7 = b(var1);
               long var8 = 0L;

               for(Climate.e.c<T> var11 : var7) {
                  var8 += a(var11.a);
               }

               if (var2 > var8) {
                  var2 = var8;
                  var4 = var6;
                  var5 = var7;
               }
            }

            a(var5, var0, var4, true);
            return new Climate.e.c<>(var5.stream().map(var1x -> a(var0, Arrays.asList(var1x.b))).collect(Collectors.toList()));
         }
      }

      private static <T> void a(List<? extends Climate.e.b<T>> var0, int var1, int var2, boolean var3) {
         Comparator<Climate.e.b<T>> var4 = a(var2, var3);

         for(int var5 = 1; var5 < var1; ++var5) {
            var4 = var4.thenComparing(a((var2 + var5) % var1, var3));
         }

         var0.sort(var4);
      }

      private static <T> Comparator<Climate.e.b<T>> a(int var0, boolean var1) {
         return Comparator.comparingLong(var2 -> {
            Climate.b var3 = var2.a[var0];
            long var4 = (var3.a() + var3.b()) / 2L;
            return var1 ? Math.abs(var4) : var4;
         });
      }

      private static <T> List<Climate.e.c<T>> b(List<? extends Climate.e.b<T>> var0) {
         List<Climate.e.c<T>> var1 = Lists.newArrayList();
         List<Climate.e.b<T>> var2 = Lists.newArrayList();
         int var3 = (int)Math.pow(6.0, Math.floor(Math.log((double)var0.size() - 0.01) / Math.log(6.0)));

         for(Climate.e.b<T> var5 : var0) {
            var2.add(var5);
            if (var2.size() >= var3) {
               var1.add(new Climate.e.c<>(var2));
               var2 = Lists.newArrayList();
            }
         }

         if (!var2.isEmpty()) {
            var1.add(new Climate.e.c<>(var2));
         }

         return var1;
      }

      private static long a(Climate.b[] var0) {
         long var1 = 0L;

         for(Climate.b var6 : var0) {
            var1 += Math.abs(var6.b() - var6.a());
         }

         return var1;
      }

      static <T> List<Climate.b> c(List<? extends Climate.e.b<T>> var0) {
         if (var0.isEmpty()) {
            throw new IllegalArgumentException("SubTree needs at least one child");
         } else {
            int var1 = 7;
            List<Climate.b> var2 = Lists.newArrayList();

            for(int var3 = 0; var3 < 7; ++var3) {
               var2.add(null);
            }

            for(Climate.e.b<T> var4 : var0) {
               for(int var5 = 0; var5 < 7; ++var5) {
                  var2.set(var5, var4.a[var5].b(var2.get(var5)));
               }
            }

            return var2;
         }
      }

      public T a(Climate.h var0, Climate.a<T> var1) {
         long[] var2 = var0.a();
         Climate.e.a<T> var3 = this.b.a(var2, this.c.get(), var1);
         this.c.set(var3);
         return var3.b;
      }

      static final class a<T> extends Climate.e.b<T> {
         final T b;

         a(Climate.d var0, T var1) {
            super(var0.a());
            this.b = var1;
         }

         @Override
         protected Climate.e.a<T> a(long[] var0, @Nullable Climate.e.a<T> var1, Climate.a<T> var2) {
            return this;
         }
      }

      abstract static class b<T> {
         protected final Climate.b[] a;

         protected b(List<Climate.b> var0) {
            this.a = var0.toArray(new Climate.b[0]);
         }

         protected abstract Climate.e.a<T> a(long[] var1, @Nullable Climate.e.a<T> var2, Climate.a<T> var3);

         protected long a(long[] var0) {
            long var1 = 0L;

            for(int var3 = 0; var3 < 7; ++var3) {
               var1 += MathHelper.a(this.a[var3].a(var0[var3]));
            }

            return var1;
         }

         @Override
         public String toString() {
            return Arrays.toString((Object[])this.a);
         }
      }

      static final class c<T> extends Climate.e.b<T> {
         final Climate.e.b<T>[] b;

         protected c(List<? extends Climate.e.b<T>> var0) {
            this(Climate.e.c(var0), var0);
         }

         protected c(List<Climate.b> var0, List<? extends Climate.e.b<T>> var1) {
            super(var0);
            this.b = var1.toArray(new Climate.e.b[0]);
         }

         @Override
         protected Climate.e.a<T> a(long[] var0, @Nullable Climate.e.a<T> var1, Climate.a<T> var2) {
            long var3 = var1 == null ? Long.MAX_VALUE : var2.distance(var1, var0);
            Climate.e.a<T> var5 = var1;

            for(Climate.e.b<T> var9 : this.b) {
               long var10 = var2.distance(var9, var0);
               if (var3 > var10) {
                  Climate.e.a<T> var12 = var9.a(var0, var5, var2);
                  long var13 = var9 == var12 ? var10 : var2.distance(var12, var0);
                  if (var3 > var13) {
                     var3 = var13;
                     var5 = var12;
                  }
               }
            }

            return var5;
         }
      }
   }

   static class g {
      Climate.g.a a;

      g(List<Climate.d> var0, Climate.Sampler var1) {
         this.a = a(var0, var1, 0, 0);
         this.a(var0, var1, 2048.0F, 512.0F);
         this.a(var0, var1, 512.0F, 32.0F);
      }

      private void a(List<Climate.d> var0, Climate.Sampler var1, float var2, float var3) {
         float var4 = 0.0F;
         float var5 = var3;
         BlockPosition var6 = this.a.a();

         while(var5 <= var2) {
            int var7 = var6.u() + (int)(Math.sin((double)var4) * (double)var5);
            int var8 = var6.w() + (int)(Math.cos((double)var4) * (double)var5);
            Climate.g.a var9 = a(var0, var1, var7, var8);
            if (var9.b() < this.a.b()) {
               this.a = var9;
            }

            var4 += var3 / var5;
            if ((double)var4 > Math.PI * 2) {
               var4 = 0.0F;
               var5 += var3;
            }
         }
      }

      private static Climate.g.a a(List<Climate.d> var0, Climate.Sampler var1, int var2, int var3) {
         double var4 = MathHelper.k(2500.0);
         int var6 = 2;
         long var7 = (long)((double)MathHelper.k(10000.0F) * Math.pow((double)(MathHelper.a((long)var2) + MathHelper.a((long)var3)) / var4, 2.0));
         Climate.h var9 = var1.a(QuartPos.a(var2), 0, QuartPos.a(var3));
         Climate.h var10 = new Climate.h(var9.b(), var9.c(), var9.d(), var9.e(), 0L, var9.g());
         long var11 = Long.MAX_VALUE;

         for(Climate.d var14 : var0) {
            var11 = Math.min(var11, var14.a(var10));
         }

         return new Climate.g.a(new BlockPosition(var2, 0, var3), var7 + var11);
      }

      static record a(BlockPosition location, long fitness) {
         private final BlockPosition a;
         private final long b;

         a(BlockPosition var0, long var1) {
            this.a = var0;
            this.b = var1;
         }
      }
   }

   public static record h(long temperature, long humidity, long continentalness, long erosion, long depth, long weirdness) {
      final long a;
      final long b;
      final long c;
      final long d;
      final long e;
      final long f;

      public h(long var0, long var2, long var4, long var6, long var8, long var10) {
         this.a = var0;
         this.b = var2;
         this.c = var4;
         this.d = var6;
         this.e = var8;
         this.f = var10;
      }

      @VisibleForTesting
      protected long[] a() {
         return new long[]{this.a, this.b, this.c, this.d, this.e, this.f, 0L};
      }

      public long b() {
         return this.a;
      }

      public long c() {
         return this.b;
      }

      public long d() {
         return this.c;
      }

      public long e() {
         return this.d;
      }

      public long f() {
         return this.e;
      }

      public long g() {
         return this.f;
      }
   }
}
