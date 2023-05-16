package net.minecraft.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.mutable.MutableObject;

public interface CubicSpline<C, I extends ToFloatFunction<C>> extends ToFloatFunction<C> {
   @VisibleForDebug
   String a();

   CubicSpline<C, I> a(CubicSpline.d<I> var1);

   static <C, I extends ToFloatFunction<C>> Codec<CubicSpline<C, I>> a(Codec<I> var0) {
      MutableObject<Codec<CubicSpline<C, I>>> var1 = new MutableObject();

      record a<C, I extends ToFloatFunction<C>>(float location, CubicSpline<C, I> value, float derivative) {
         private final float a;
         private final CubicSpline<C, I> b;
         private final float c;

         a(float var0, CubicSpline<C, I> var1, float var2) {
            this.a = var0;
            this.b = var1;
            this.c = var2;
         }
      }

      Codec<a<C, I>> var2 = RecordCodecBuilder.create(
         var1x -> var1x.group(
                  Codec.FLOAT.fieldOf("location").forGetter(a::a),
                  ExtraCodecs.a(var1::getValue).fieldOf("value").forGetter(a::b),
                  Codec.FLOAT.fieldOf("derivative").forGetter(a::c)
               )
               .apply(var1x, (var0xx, var1xx, var2x) -> new a(var0xx, var1xx, var2x))
      );
      Codec<CubicSpline.e<C, I>> var3 = RecordCodecBuilder.create(
         var2x -> var2x.group(
                  var0.fieldOf("coordinate").forGetter(CubicSpline.e::d),
                  ExtraCodecs.a(var2.listOf())
                     .fieldOf("points")
                     .forGetter(
                        var0xx -> IntStream.range(0, var0xx.c.length)
                              .mapToObj(var1xx -> new a(var0xx.e()[var1xx], (CubicSpline<C, I>)var0xx.f().get(var1xx), var0xx.g()[var1xx]))
                              .toList()
                     )
               )
               .apply(var2x, (var0xx, var1xx) -> {
                  float[] var2xx = new float[var1xx.size()];
                  Builder<CubicSpline<C, I>> var3x = ImmutableList.builder();
                  float[] var4 = new float[var1xx.size()];
      
                  for(int var5 = 0; var5 < var1xx.size(); ++var5) {
                     a<C, I> var6 = (a)var1xx.get(var5);
                     var2xx[var5] = var6.a();
                     var3x.add(var6.b());
                     var4[var5] = var6.c();
                  }
      
                  return CubicSpline.e.a((I)var0xx, var2xx, var3x.build(), var4);
               })
      );
      var1.setValue(
         Codec.either(Codec.FLOAT, var3)
            .xmap(
               var0x -> (CubicSpline)var0x.map(CubicSpline.c::new, var0xx -> var0xx),
               var0x -> var0x instanceof CubicSpline.c var1x ? Either.left(var1x.d()) : Either.right((CubicSpline.e)var0x)
            )
      );
      return (Codec<CubicSpline<C, I>>)var1.getValue();
   }

   static <C, I extends ToFloatFunction<C>> CubicSpline<C, I> a(float var0) {
      return new CubicSpline.c<>(var0);
   }

   static <C, I extends ToFloatFunction<C>> CubicSpline.b<C, I> a(I var0) {
      return new CubicSpline.b<>(var0);
   }

   static <C, I extends ToFloatFunction<C>> CubicSpline.b<C, I> a(I var0, ToFloatFunction<Float> var1) {
      return new CubicSpline.b<>(var0, var1);
   }

   public static final class b<C, I extends ToFloatFunction<C>> {
      private final I a;
      private final ToFloatFunction<Float> b;
      private final FloatList c = new FloatArrayList();
      private final List<CubicSpline<C, I>> d = Lists.newArrayList();
      private final FloatList e = new FloatArrayList();

      protected b(I var0) {
         this(var0, ToFloatFunction.a);
      }

      protected b(I var0, ToFloatFunction<Float> var1) {
         this.a = var0;
         this.b = var1;
      }

      public CubicSpline.b<C, I> a(float var0, float var1) {
         return this.a(var0, new CubicSpline.c<>(this.b.a(var1)), 0.0F);
      }

      public CubicSpline.b<C, I> a(float var0, float var1, float var2) {
         return this.a(var0, new CubicSpline.c<>(this.b.a(var1)), var2);
      }

      public CubicSpline.b<C, I> a(float var0, CubicSpline<C, I> var1) {
         return this.a(var0, var1, 0.0F);
      }

      private CubicSpline.b<C, I> a(float var0, CubicSpline<C, I> var1, float var2) {
         if (!this.c.isEmpty() && var0 <= this.c.getFloat(this.c.size() - 1)) {
            throw new IllegalArgumentException("Please register points in ascending order");
         } else {
            this.c.add(var0);
            this.d.add(var1);
            this.e.add(var2);
            return this;
         }
      }

      public CubicSpline<C, I> a() {
         if (this.c.isEmpty()) {
            throw new IllegalStateException("No elements added");
         } else {
            return CubicSpline.e.a(this.a, this.c.toFloatArray(), ImmutableList.copyOf(this.d), this.e.toFloatArray());
         }
      }
   }

   @VisibleForDebug
   public static record c<C, I extends ToFloatFunction<C>>(float value) implements CubicSpline<C, I> {
      private final float b;

      public c(float var0) {
         this.b = var0;
      }

      @Override
      public float a(C var0) {
         return this.b;
      }

      @Override
      public String a() {
         return String.format(Locale.ROOT, "k=%.3f", this.b);
      }

      @Override
      public float c() {
         return this.b;
      }

      @Override
      public CubicSpline<C, I> a(CubicSpline.d<I> var0) {
         return this;
      }

      public float d() {
         return this.b;
      }
   }

   public interface d<I> {
      I visit(I var1);
   }

   @VisibleForDebug
   public static record e<C, I extends ToFloatFunction<C>>(
      I coordinate, float[] locations, List<CubicSpline<C, I>> values, float[] derivatives, float minValue, float maxValue
   ) implements CubicSpline<C, I> {
      private final I b;
      final float[] c;
      private final List<CubicSpline<C, I>> d;
      private final float[] e;
      private final float f;
      private final float g;

      public e(I var0, float[] var1, List<CubicSpline<C, I>> var2, float[] var3, float var4, float var5) {
         a(var1, var2, var3);
         this.b = var0;
         this.c = var1;
         this.d = var2;
         this.e = var3;
         this.f = var4;
         this.g = var5;
      }

      static <C, I extends ToFloatFunction<C>> CubicSpline.e<C, I> a(I var0, float[] var1, List<CubicSpline<C, I>> var2, float[] var3) {
         a(var1, var2, var3);
         int var4 = var1.length - 1;
         float var5 = Float.POSITIVE_INFINITY;
         float var6 = Float.NEGATIVE_INFINITY;
         float var7 = var0.b();
         float var8 = var0.c();
         if (var7 < var1[0]) {
            float var9 = a(var7, var1, var2.get(0).b(), var3, 0);
            float var10 = a(var7, var1, var2.get(0).c(), var3, 0);
            var5 = Math.min(var5, Math.min(var9, var10));
            var6 = Math.max(var6, Math.max(var9, var10));
         }

         if (var8 > var1[var4]) {
            float var9 = a(var8, var1, var2.get(var4).b(), var3, var4);
            float var10 = a(var8, var1, var2.get(var4).c(), var3, var4);
            var5 = Math.min(var5, Math.min(var9, var10));
            var6 = Math.max(var6, Math.max(var9, var10));
         }

         for(CubicSpline<C, I> var10 : var2) {
            var5 = Math.min(var5, var10.b());
            var6 = Math.max(var6, var10.c());
         }

         for(int var9 = 0; var9 < var4; ++var9) {
            float var10 = var1[var9];
            float var11 = var1[var9 + 1];
            float var12 = var11 - var10;
            CubicSpline<C, I> var13 = var2.get(var9);
            CubicSpline<C, I> var14 = var2.get(var9 + 1);
            float var15 = var13.b();
            float var16 = var13.c();
            float var17 = var14.b();
            float var18 = var14.c();
            float var19 = var3[var9];
            float var20 = var3[var9 + 1];
            if (var19 != 0.0F || var20 != 0.0F) {
               float var21 = var19 * var12;
               float var22 = var20 * var12;
               float var23 = Math.min(var15, var17);
               float var24 = Math.max(var16, var18);
               float var25 = var21 - var18 + var15;
               float var26 = var21 - var17 + var16;
               float var27 = -var22 + var17 - var16;
               float var28 = -var22 + var18 - var15;
               float var29 = Math.min(var25, var27);
               float var30 = Math.max(var26, var28);
               var5 = Math.min(var5, var23 + 0.25F * var29);
               var6 = Math.max(var6, var24 + 0.25F * var30);
            }
         }

         return new CubicSpline.e<>(var0, var1, var2, var3, var5, var6);
      }

      private static float a(float var0, float[] var1, float var2, float[] var3, int var4) {
         float var5 = var3[var4];
         return var5 == 0.0F ? var2 : var2 + var5 * (var0 - var1[var4]);
      }

      private static <C, I extends ToFloatFunction<C>> void a(float[] var0, List<CubicSpline<C, I>> var1, float[] var2) {
         if (var0.length != var1.size() || var0.length != var2.length) {
            throw new IllegalArgumentException("All lengths must be equal, got: " + var0.length + " " + var1.size() + " " + var2.length);
         } else if (var0.length == 0) {
            throw new IllegalArgumentException("Cannot create a multipoint spline with no points");
         }
      }

      @Override
      public float a(C var0) {
         float var1 = this.b.a(var0);
         int var2 = a(this.c, var1);
         int var3 = this.c.length - 1;
         if (var2 < 0) {
            return a(var1, this.c, this.d.get(0).a(var0), this.e, 0);
         } else if (var2 == var3) {
            return a(var1, this.c, this.d.get(var3).a(var0), this.e, var3);
         } else {
            float var4 = this.c[var2];
            float var5 = this.c[var2 + 1];
            float var6 = (var1 - var4) / (var5 - var4);
            ToFloatFunction<C> var7 = (ToFloatFunction)this.d.get(var2);
            ToFloatFunction<C> var8 = (ToFloatFunction)this.d.get(var2 + 1);
            float var9 = this.e[var2];
            float var10 = this.e[var2 + 1];
            float var11 = var7.a(var0);
            float var12 = var8.a(var0);
            float var13 = var9 * (var5 - var4) - (var12 - var11);
            float var14 = -var10 * (var5 - var4) + (var12 - var11);
            return MathHelper.i(var6, var11, var12) + var6 * (1.0F - var6) * MathHelper.i(var6, var13, var14);
         }
      }

      private static int a(float[] var0, float var1) {
         return MathHelper.a(0, var0.length, var2 -> var1 < var0[var2]) - 1;
      }

      @VisibleForTesting
      @Override
      public String a() {
         return "Spline{coordinate="
            + this.b
            + ", locations="
            + this.a(this.c)
            + ", derivatives="
            + this.a(this.e)
            + ", values="
            + (String)this.d.stream().map(CubicSpline::a).collect(Collectors.joining(", ", "[", "]"))
            + "}";
      }

      private String a(float[] var0) {
         return "["
            + (String)IntStream.range(0, var0.length)
               .mapToDouble(var1x -> (double)var0[var1x])
               .mapToObj(var0x -> String.format(Locale.ROOT, "%.3f", var0x))
               .collect(Collectors.joining(", "))
            + "]";
      }

      @Override
      public CubicSpline<C, I> a(CubicSpline.d<I> var0) {
         return a(var0.visit(this.b), this.c, this.f().stream().map(var1x -> var1x.a(var0)).toList(), this.e);
      }

      public I d() {
         return this.b;
      }

      public float[] e() {
         return this.c;
      }

      public List<CubicSpline<C, I>> f() {
         return this.d;
      }

      public float[] g() {
         return this.e;
      }

      @Override
      public float b() {
         return this.f;
      }

      @Override
      public float c() {
         return this.g;
      }
   }
}
