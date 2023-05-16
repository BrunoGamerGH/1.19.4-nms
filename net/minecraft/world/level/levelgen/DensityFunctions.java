package net.minecraft.world.level.levelgen;

import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.doubles.Double2DoubleFunction;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.CubicSpline;
import net.minecraft.util.INamable;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.ToFloatFunction;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NoiseGenerator3Handler;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;
import org.slf4j.Logger;

public final class DensityFunctions {
   private static final Codec<DensityFunction> c = BuiltInRegistries.af.q().dispatch(var0 -> var0.c().a(), Function.identity());
   protected static final double a = 1000000.0;
   static final Codec<Double> d = Codec.doubleRange(-1000000.0, 1000000.0);
   public static final Codec<DensityFunction> b = Codec.either(d, c)
      .xmap(
         var0 -> (DensityFunction)var0.map(DensityFunctions::a, Function.identity()),
         var0 -> var0 instanceof DensityFunctions.h var1 ? Either.left(var1.j()) : Either.right(var0)
      );

   public static Codec<? extends DensityFunction> a(IRegistry<Codec<? extends DensityFunction>> var0) {
      a(var0, "blend_alpha", DensityFunctions.d.e);
      a(var0, "blend_offset", DensityFunctions.f.e);
      a(var0, "beardifier", DensityFunctions.b.e);
      a(var0, "old_blended_noise", BlendedNoise.a);

      for(DensityFunctions.l.a var4 : DensityFunctions.l.a.values()) {
         a(var0, var4.c(), var4.g);
      }

      a(var0, "noise", DensityFunctions.o.e);
      a(var0, "end_islands", DensityFunctions.i.a);
      a(var0, "weird_scaled_sampler", DensityFunctions.z.a);
      a(var0, "shifted_noise", DensityFunctions.v.a);
      a(var0, "range_choice", DensityFunctions.q.e);
      a(var0, "shift_a", DensityFunctions.s.e);
      a(var0, "shift_b", DensityFunctions.t.e);
      a(var0, "shift", DensityFunctions.r.e);
      a(var0, "blend_density", DensityFunctions.e.e);
      a(var0, "clamp", DensityFunctions.g.a);

      for(DensityFunctions.k.a var4 : DensityFunctions.k.a.values()) {
         a(var0, var4.c(), var4.h);
      }

      for(DensityFunctions.y.a var4 : DensityFunctions.y.a.values()) {
         a(var0, var4.c(), var4.e);
      }

      a(var0, "spline", DensityFunctions.w.a);
      a(var0, "constant", DensityFunctions.h.e);
      return a(var0, "y_clamped_gradient", DensityFunctions.aa.a);
   }

   private static Codec<? extends DensityFunction> a(
      IRegistry<Codec<? extends DensityFunction>> var0, String var1, KeyDispatchDataCodec<? extends DensityFunction> var2
   ) {
      return IRegistry.a(var0, var1, var2.a());
   }

   static <A, O> KeyDispatchDataCodec<O> a(Codec<A> var0, Function<A, O> var1, Function<O, A> var2) {
      return KeyDispatchDataCodec.a(var0.fieldOf("argument").xmap(var1, var2));
   }

   static <O> KeyDispatchDataCodec<O> a(Function<DensityFunction, O> var0, Function<O, DensityFunction> var1) {
      return a(DensityFunction.d, var0, var1);
   }

   static <O> KeyDispatchDataCodec<O> a(
      BiFunction<DensityFunction, DensityFunction, O> var0, Function<O, DensityFunction> var1, Function<O, DensityFunction> var2
   ) {
      return KeyDispatchDataCodec.a(
         RecordCodecBuilder.mapCodec(
            var3 -> var3.group(DensityFunction.d.fieldOf("argument1").forGetter(var1), DensityFunction.d.fieldOf("argument2").forGetter(var2))
                  .apply(var3, var0)
         )
      );
   }

   static <O> KeyDispatchDataCodec<O> a(MapCodec<O> var0) {
      return KeyDispatchDataCodec.a(var0);
   }

   private DensityFunctions() {
   }

   public static DensityFunction a(DensityFunction var0) {
      return new DensityFunctions.l(DensityFunctions.l.a.a, var0);
   }

   public static DensityFunction b(DensityFunction var0) {
      return new DensityFunctions.l(DensityFunctions.l.a.b, var0);
   }

   public static DensityFunction c(DensityFunction var0) {
      return new DensityFunctions.l(DensityFunctions.l.a.c, var0);
   }

   public static DensityFunction d(DensityFunction var0) {
      return new DensityFunctions.l(DensityFunctions.l.a.d, var0);
   }

   public static DensityFunction e(DensityFunction var0) {
      return new DensityFunctions.l(DensityFunctions.l.a.e, var0);
   }

   public static DensityFunction a(Holder<NoiseGeneratorNormal.a> var0, @Deprecated double var1, double var3, double var5, double var7) {
      return a(new DensityFunctions.o(new DensityFunction.c(var0), var1, var3), var5, var7);
   }

   public static DensityFunction a(Holder<NoiseGeneratorNormal.a> var0, double var1, double var3, double var5) {
      return a(var0, 1.0, var1, var3, var5);
   }

   public static DensityFunction a(Holder<NoiseGeneratorNormal.a> var0, double var1, double var3) {
      return a(var0, 1.0, 1.0, var1, var3);
   }

   public static DensityFunction a(DensityFunction var0, DensityFunction var1, double var2, Holder<NoiseGeneratorNormal.a> var4) {
      return new DensityFunctions.v(var0, a(), var1, var2, 0.0, new DensityFunction.c(var4));
   }

   public static DensityFunction a(Holder<NoiseGeneratorNormal.a> var0) {
      return b(var0, 1.0, 1.0);
   }

   public static DensityFunction b(Holder<NoiseGeneratorNormal.a> var0, double var1, double var3) {
      return new DensityFunctions.o(new DensityFunction.c(var0), var1, var3);
   }

   public static DensityFunction a(Holder<NoiseGeneratorNormal.a> var0, double var1) {
      return b(var0, 1.0, var1);
   }

   public static DensityFunction a(DensityFunction var0, double var1, double var3, DensityFunction var5, DensityFunction var6) {
      return new DensityFunctions.q(var0, var1, var3, var5, var6);
   }

   public static DensityFunction b(Holder<NoiseGeneratorNormal.a> var0) {
      return new DensityFunctions.s(new DensityFunction.c(var0));
   }

   public static DensityFunction c(Holder<NoiseGeneratorNormal.a> var0) {
      return new DensityFunctions.t(new DensityFunction.c(var0));
   }

   public static DensityFunction d(Holder<NoiseGeneratorNormal.a> var0) {
      return new DensityFunctions.r(new DensityFunction.c(var0));
   }

   public static DensityFunction f(DensityFunction var0) {
      return new DensityFunctions.e(var0);
   }

   public static DensityFunction a(long var0) {
      return new DensityFunctions.i(var0);
   }

   public static DensityFunction a(DensityFunction var0, Holder<NoiseGeneratorNormal.a> var1, DensityFunctions.z.a var2) {
      return new DensityFunctions.z(var0, new DensityFunction.c(var1), var2);
   }

   public static DensityFunction a(DensityFunction var0, DensityFunction var1) {
      return DensityFunctions.y.a(DensityFunctions.y.a.a, var0, var1);
   }

   public static DensityFunction b(DensityFunction var0, DensityFunction var1) {
      return DensityFunctions.y.a(DensityFunctions.y.a.b, var0, var1);
   }

   public static DensityFunction c(DensityFunction var0, DensityFunction var1) {
      return DensityFunctions.y.a(DensityFunctions.y.a.c, var0, var1);
   }

   public static DensityFunction d(DensityFunction var0, DensityFunction var1) {
      return DensityFunctions.y.a(DensityFunctions.y.a.d, var0, var1);
   }

   public static DensityFunction a(CubicSpline<DensityFunctions.w.b, DensityFunctions.w.a> var0) {
      return new DensityFunctions.w(var0);
   }

   public static DensityFunction a() {
      return DensityFunctions.h.f;
   }

   public static DensityFunction a(double var0) {
      return new DensityFunctions.h(var0);
   }

   public static DensityFunction a(int var0, int var1, double var2, double var4) {
      return new DensityFunctions.aa(var0, var1, var2, var4);
   }

   public static DensityFunction a(DensityFunction var0, DensityFunctions.k.a var1) {
      return DensityFunctions.k.a(var1, var0);
   }

   private static DensityFunction a(DensityFunction var0, double var1, double var3) {
      double var5 = (var1 + var3) * 0.5;
      double var7 = (var3 - var1) * 0.5;
      return a(a(var5), b(a(var7), var0));
   }

   public static DensityFunction b() {
      return DensityFunctions.d.a;
   }

   public static DensityFunction c() {
      return DensityFunctions.f.a;
   }

   public static DensityFunction a(DensityFunction var0, DensityFunction var1, DensityFunction var2) {
      if (var1 instanceof DensityFunctions.h var3) {
         return a(var0, var3.a, var2);
      } else {
         DensityFunction var3 = d(var0);
         DensityFunction var4 = a(b(var3, a(-1.0)), a(1.0));
         return a(b(var1, var4), b(var2, var3));
      }
   }

   public static DensityFunction a(DensityFunction var0, double var1, DensityFunction var3) {
      return a(b(var0, a(var3, a(-var1))), a(var1));
   }

   static record a(DensityFunctions.y.a type, DensityFunction argument1, DensityFunction argument2, double minValue, double maxValue)
      implements DensityFunctions.y {
      private final DensityFunctions.y.a e;
      private final DensityFunction f;
      private final DensityFunction g;
      private final double h;
      private final double i;

      a(DensityFunctions.y.a var0, DensityFunction var1, DensityFunction var2, double var3, double var5) {
         this.e = var0;
         this.f = var1;
         this.g = var2;
         this.h = var3;
         this.i = var5;
      }

      @Override
      public double a(DensityFunction.b var0) {
         double var1 = this.f.a(var0);

         return switch(this.e) {
            case a -> var1 + this.g.a(var0);
            case d -> var1 > this.g.b() ? var1 : Math.max(var1, this.g.a(var0));
            case c -> var1 < this.g.a() ? var1 : Math.min(var1, this.g.a(var0));
            case b -> var1 == 0.0 ? 0.0 : var1 * this.g.a(var0);
         };
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         this.f.a(var0, var1);
         switch(this.e) {
            case a:
               double[] var2 = new double[var0.length];
               this.g.a(var2, var1);

               for(int var3 = 0; var3 < var0.length; ++var3) {
                  var0[var3] += var2[var3];
               }
               break;
            case d:
               double var2 = this.g.b();

               for(int var4 = 0; var4 < var0.length; ++var4) {
                  double var5 = var0[var4];
                  var0[var4] = var5 > var2 ? var5 : Math.max(var5, this.g.a(var1.a(var4)));
               }
               break;
            case c:
               double var2 = this.g.a();

               for(int var4 = 0; var4 < var0.length; ++var4) {
                  double var5 = var0[var4];
                  var0[var4] = var5 < var2 ? var5 : Math.min(var5, this.g.a(var1.a(var4)));
               }
               break;
            case b:
               for(int var2 = 0; var2 < var0.length; ++var2) {
                  double var3 = var0[var2];
                  var0[var2] = var3 == 0.0 ? 0.0 : var3 * this.g.a(var1.a(var2));
               }
         }
      }

      @Override
      public DensityFunction a(DensityFunction.f var0) {
         return var0.apply(DensityFunctions.y.a(this.e, this.f.a(var0), this.g.a(var0)));
      }

      @Override
      public double a() {
         return this.h;
      }

      @Override
      public double b() {
         return this.i;
      }

      @Override
      public DensityFunctions.y.a j() {
         return this.e;
      }

      @Override
      public DensityFunction k() {
         return this.f;
      }

      @Override
      public DensityFunction l() {
         return this.g;
      }
   }

   static record aa(int fromY, int toY, double fromValue, double toValue) implements DensityFunction.d {
      private final int e;
      private final int f;
      private final double g;
      private final double h;
      private static final MapCodec<DensityFunctions.aa> i = RecordCodecBuilder.mapCodec(
         var0 -> var0.group(
                  Codec.intRange(DimensionManager.e * 2, DimensionManager.d * 2).fieldOf("from_y").forGetter(DensityFunctions.aa::j),
                  Codec.intRange(DimensionManager.e * 2, DimensionManager.d * 2).fieldOf("to_y").forGetter(DensityFunctions.aa::k),
                  DensityFunctions.d.fieldOf("from_value").forGetter(DensityFunctions.aa::l),
                  DensityFunctions.d.fieldOf("to_value").forGetter(DensityFunctions.aa::m)
               )
               .apply(var0, DensityFunctions.aa::new)
      );
      public static final KeyDispatchDataCodec<DensityFunctions.aa> a = DensityFunctions.a(i);

      aa(int var0, int var1, double var2, double var4) {
         this.e = var0;
         this.f = var1;
         this.g = var2;
         this.h = var4;
      }

      @Override
      public double a(DensityFunction.b var0) {
         return MathHelper.a((double)var0.b(), (double)this.e, (double)this.f, this.g, this.h);
      }

      @Override
      public double a() {
         return Math.min(this.g, this.h);
      }

      @Override
      public double b() {
         return Math.max(this.g, this.h);
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         return a;
      }

      public int j() {
         return this.e;
      }

      public int k() {
         return this.f;
      }

      public double l() {
         return this.g;
      }

      public double m() {
         return this.h;
      }
   }

   protected static enum b implements DensityFunctions.c {
      a;

      @Override
      public double a(DensityFunction.b var0) {
         return 0.0;
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         Arrays.fill(var0, 0.0);
      }

      @Override
      public double a() {
         return 0.0;
      }

      @Override
      public double b() {
         return 0.0;
      }
   }

   public interface c extends DensityFunction.d {
      KeyDispatchDataCodec<DensityFunction> e = KeyDispatchDataCodec.a(MapCodec.unit(DensityFunctions.b.a));

      @Override
      default KeyDispatchDataCodec<? extends DensityFunction> c() {
         return e;
      }
   }

   protected static enum d implements DensityFunction.d {
      a;

      public static final KeyDispatchDataCodec<DensityFunction> e = KeyDispatchDataCodec.a(MapCodec.unit(a));

      @Override
      public double a(DensityFunction.b var0) {
         return 1.0;
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         Arrays.fill(var0, 1.0);
      }

      @Override
      public double a() {
         return 1.0;
      }

      @Override
      public double b() {
         return 1.0;
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         return e;
      }
   }

   static record e(DensityFunction input) implements DensityFunctions.x {
      private final DensityFunction a;
      static final KeyDispatchDataCodec<DensityFunctions.e> e = DensityFunctions.a(DensityFunctions.e::new, DensityFunctions.e::j);

      e(DensityFunction var0) {
         this.a = var0;
      }

      @Override
      public double a(DensityFunction.b var0, double var1) {
         return var0.d().a(var0, var1);
      }

      @Override
      public DensityFunction a(DensityFunction.f var0) {
         return var0.apply(new DensityFunctions.e(this.a.a(var0)));
      }

      @Override
      public double a() {
         return Double.NEGATIVE_INFINITY;
      }

      @Override
      public double b() {
         return Double.POSITIVE_INFINITY;
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         return e;
      }

      @Override
      public DensityFunction j() {
         return this.a;
      }
   }

   protected static enum f implements DensityFunction.d {
      a;

      public static final KeyDispatchDataCodec<DensityFunction> e = KeyDispatchDataCodec.a(MapCodec.unit(a));

      @Override
      public double a(DensityFunction.b var0) {
         return 0.0;
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         Arrays.fill(var0, 0.0);
      }

      @Override
      public double a() {
         return 0.0;
      }

      @Override
      public double b() {
         return 0.0;
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         return e;
      }
   }

   protected static record g(DensityFunction input, double minValue, double maxValue) implements DensityFunctions.p {
      private final DensityFunction e;
      private final double f;
      private final double g;
      private static final MapCodec<DensityFunctions.g> h = RecordCodecBuilder.mapCodec(
         var0 -> var0.group(
                  DensityFunction.b.fieldOf("input").forGetter(DensityFunctions.g::au_),
                  DensityFunctions.d.fieldOf("min").forGetter(DensityFunctions.g::a),
                  DensityFunctions.d.fieldOf("max").forGetter(DensityFunctions.g::b)
               )
               .apply(var0, DensityFunctions.g::new)
      );
      public static final KeyDispatchDataCodec<DensityFunctions.g> a = DensityFunctions.a(h);

      protected g(DensityFunction var0, double var1, double var3) {
         this.e = var0;
         this.f = var1;
         this.g = var3;
      }

      @Override
      public double a(double var0) {
         return MathHelper.a(var0, this.f, this.g);
      }

      @Override
      public DensityFunction a(DensityFunction.f var0) {
         return new DensityFunctions.g(this.e.a(var0), this.f, this.g);
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         return a;
      }

      @Override
      public DensityFunction au_() {
         return this.e;
      }

      @Override
      public double a() {
         return this.f;
      }

      @Override
      public double b() {
         return this.g;
      }
   }

   static record h(double value) implements DensityFunction.d {
      final double a;
      static final KeyDispatchDataCodec<DensityFunctions.h> e = DensityFunctions.a(DensityFunctions.d, DensityFunctions.h::new, DensityFunctions.h::j);
      static final DensityFunctions.h f = new DensityFunctions.h(0.0);

      h(double var0) {
         this.a = var0;
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         Arrays.fill(var0, this.a);
      }

      @Override
      public double b() {
         return this.a;
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         return e;
      }

      public double j() {
         return this.a;
      }
   }

   protected static final class i implements DensityFunction.d {
      public static final KeyDispatchDataCodec<DensityFunctions.i> a = KeyDispatchDataCodec.a(MapCodec.unit(new DensityFunctions.i(0L)));
      private static final float e = -0.9F;
      private final NoiseGenerator3Handler f;

      public i(long var0) {
         RandomSource var2 = new LegacyRandomSource(var0);
         var2.b(17292);
         this.f = new NoiseGenerator3Handler(var2);
      }

      private static float a(NoiseGenerator3Handler var0, int var1, int var2) {
         int var3 = var1 / 2;
         int var4 = var2 / 2;
         int var5 = var1 % 2;
         int var6 = var2 % 2;
         float var7 = 100.0F - MathHelper.c((float)(var1 * var1 + var2 * var2)) * 8.0F;
         var7 = MathHelper.a(var7, -100.0F, 80.0F);

         for(int var8 = -12; var8 <= 12; ++var8) {
            for(int var9 = -12; var9 <= 12; ++var9) {
               long var10 = (long)(var3 + var8);
               long var12 = (long)(var4 + var9);
               if (var10 * var10 + var12 * var12 > 4096L && var0.a((double)var10, (double)var12) < -0.9F) {
                  float var14 = (MathHelper.e((float)var10) * 3439.0F + MathHelper.e((float)var12) * 147.0F) % 13.0F + 9.0F;
                  float var15 = (float)(var5 - var8 * 2);
                  float var16 = (float)(var6 - var9 * 2);
                  float var17 = 100.0F - MathHelper.c(var15 * var15 + var16 * var16) * var14;
                  var17 = MathHelper.a(var17, -100.0F, 80.0F);
                  var7 = Math.max(var7, var17);
               }
            }
         }

         return var7;
      }

      @Override
      public double a(DensityFunction.b var0) {
         return ((double)a(this.f, var0.a() / 8, var0.c() / 8) - 8.0) / 128.0;
      }

      @Override
      public double a() {
         return -0.84375;
      }

      @Override
      public double b() {
         return 0.5625;
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         return a;
      }
   }

   @VisibleForDebug
   public static record j(Holder<DensityFunction> function) implements DensityFunction {
      private final Holder<DensityFunction> a;

      public j(Holder<DensityFunction> var0) {
         this.a = var0;
      }

      @Override
      public double a(DensityFunction.b var0) {
         return this.a.a().a(var0);
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         this.a.a().a(var0, var1);
      }

      @Override
      public DensityFunction a(DensityFunction.f var0) {
         return var0.apply(new DensityFunctions.j(new Holder.a<>(this.a.a().a(var0))));
      }

      @Override
      public double a() {
         return this.a.b() ? this.a.a().a() : Double.NEGATIVE_INFINITY;
      }

      @Override
      public double b() {
         return this.a.b() ? this.a.a().b() : Double.POSITIVE_INFINITY;
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         throw new UnsupportedOperationException("Calling .codec() on HolderHolder");
      }

      public Holder<DensityFunction> j() {
         return this.a;
      }
   }

   protected static record k(DensityFunctions.k.a type, DensityFunction input, double minValue, double maxValue) implements DensityFunctions.p {
      private final DensityFunctions.k.a a;
      private final DensityFunction e;
      private final double f;
      private final double g;

      protected k(DensityFunctions.k.a var0, DensityFunction var1, double var2, double var4) {
         this.a = var0;
         this.e = var1;
         this.f = var2;
         this.g = var4;
      }

      public static DensityFunctions.k a(DensityFunctions.k.a var0, DensityFunction var1) {
         double var2 = var1.a();
         double var4 = a(var0, var2);
         double var6 = a(var0, var1.b());
         return var0 != DensityFunctions.k.a.a && var0 != DensityFunctions.k.a.b
            ? new DensityFunctions.k(var0, var1, var4, var6)
            : new DensityFunctions.k(var0, var1, Math.max(0.0, var2), Math.max(var4, var6));
      }

      private static double a(DensityFunctions.k.a var0, double var1) {
         return switch(var0) {
            case a -> Math.abs(var1);
            case b -> var1 * var1;
            case c -> var1 * var1 * var1;
            case d -> var1 > 0.0 ? var1 : var1 * 0.5;
            case e -> var1 > 0.0 ? var1 : var1 * 0.25;
            case f -> {
               double var3 = MathHelper.a(var1, -1.0, 1.0);
               yield var3 / 2.0 - var3 * var3 * var3 / 24.0;
            }
         };
      }

      @Override
      public double a(double var0) {
         return a(this.a, var0);
      }

      public DensityFunctions.k b(DensityFunction.f var0) {
         return a(this.a, this.e.a(var0));
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         return this.a.h;
      }

      public DensityFunctions.k.a k() {
         return this.a;
      }

      @Override
      public DensityFunction au_() {
         return this.e;
      }

      @Override
      public double a() {
         return this.f;
      }

      @Override
      public double b() {
         return this.g;
      }

      static enum a implements INamable {
         a("abs"),
         b("square"),
         c("cube"),
         d("half_negative"),
         e("quarter_negative"),
         f("squeeze");

         private final String g;
         final KeyDispatchDataCodec<DensityFunctions.k> h = DensityFunctions.a(var0 -> DensityFunctions.k.a(this, var0), DensityFunctions.k::au_);

         private a(String var2) {
            this.g = var2;
         }

         @Override
         public String c() {
            return this.g;
         }
      }
   }

   protected static record l(DensityFunctions.l.a type, DensityFunction wrapped) implements DensityFunctions.m {
      private final DensityFunctions.l.a a;
      private final DensityFunction e;

      protected l(DensityFunctions.l.a var0, DensityFunction var1) {
         this.a = var0;
         this.e = var1;
      }

      @Override
      public double a(DensityFunction.b var0) {
         return this.e.a(var0);
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         this.e.a(var0, var1);
      }

      @Override
      public double a() {
         return this.e.a();
      }

      @Override
      public double b() {
         return this.e.b();
      }

      @Override
      public DensityFunctions.l.a j() {
         return this.a;
      }

      @Override
      public DensityFunction k() {
         return this.e;
      }

      static enum a implements INamable {
         a("interpolated"),
         b("flat_cache"),
         c("cache_2d"),
         d("cache_once"),
         e("cache_all_in_cell");

         private final String f;
         final KeyDispatchDataCodec<DensityFunctions.m> g = DensityFunctions.a(var0 -> new DensityFunctions.l(this, var0), DensityFunctions.m::k);

         private a(String var2) {
            this.f = var2;
         }

         @Override
         public String c() {
            return this.f;
         }
      }
   }

   public interface m extends DensityFunction {
      DensityFunctions.l.a j();

      DensityFunction k();

      @Override
      default KeyDispatchDataCodec<? extends DensityFunction> c() {
         return this.j().g;
      }

      @Override
      default DensityFunction a(DensityFunction.f var0) {
         return var0.apply(new DensityFunctions.l(this.j(), this.k().a(var0)));
      }
   }

   static record n(DensityFunctions.n.a specificType, DensityFunction input, double minValue, double maxValue, double argument)
      implements DensityFunctions.p,
      DensityFunctions.y {
      private final DensityFunctions.n.a e;
      private final DensityFunction f;
      private final double g;
      private final double h;
      private final double i;

      n(DensityFunctions.n.a var0, DensityFunction var1, double var2, double var4, double var6) {
         this.e = var0;
         this.f = var1;
         this.g = var2;
         this.h = var4;
         this.i = var6;
      }

      @Override
      public DensityFunctions.y.a j() {
         return this.e == DensityFunctions.n.a.a ? DensityFunctions.y.a.b : DensityFunctions.y.a.a;
      }

      @Override
      public DensityFunction k() {
         return DensityFunctions.a(this.i);
      }

      @Override
      public DensityFunction l() {
         return this.f;
      }

      @Override
      public double a(double var0) {
         return switch(this.e) {
            case a -> var0 * this.i;
            case b -> var0 + this.i;
         };
      }

      @Override
      public DensityFunction a(DensityFunction.f var0) {
         DensityFunction var1 = this.f.a(var0);
         double var2 = var1.a();
         double var4 = var1.b();
         double var6;
         double var8;
         if (this.e == DensityFunctions.n.a.b) {
            var6 = var2 + this.i;
            var8 = var4 + this.i;
         } else if (this.i >= 0.0) {
            var6 = var2 * this.i;
            var8 = var4 * this.i;
         } else {
            var6 = var4 * this.i;
            var8 = var2 * this.i;
         }

         return new DensityFunctions.n(this.e, var1, var6, var8, this.i);
      }

      public DensityFunctions.n.a m() {
         return this.e;
      }

      @Override
      public DensityFunction au_() {
         return this.f;
      }

      @Override
      public double a() {
         return this.g;
      }

      @Override
      public double b() {
         return this.h;
      }

      public double n() {
         return this.i;
      }

      static enum a {
         a,
         b;
      }
   }

   protected static record o(DensityFunction.c noise, @Deprecated double xzScale, double yScale) implements DensityFunction {
      private final DensityFunction.c f;
      @Deprecated
      private final double g;
      private final double h;
      public static final MapCodec<DensityFunctions.o> a = RecordCodecBuilder.mapCodec(
         var0 -> var0.group(
                  DensityFunction.c.a.fieldOf("noise").forGetter(DensityFunctions.o::j),
                  Codec.DOUBLE.fieldOf("xz_scale").forGetter(DensityFunctions.o::k),
                  Codec.DOUBLE.fieldOf("y_scale").forGetter(DensityFunctions.o::l)
               )
               .apply(var0, DensityFunctions.o::new)
      );
      public static final KeyDispatchDataCodec<DensityFunctions.o> e = DensityFunctions.a(a);

      protected o(DensityFunction.c var0, @Deprecated double var1, double var3) {
         this.f = var0;
         this.g = var1;
         this.h = var3;
      }

      @Override
      public double a(DensityFunction.b var0) {
         return this.f.a((double)var0.a() * this.g, (double)var0.b() * this.h, (double)var0.c() * this.g);
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         var1.a(var0, this);
      }

      @Override
      public DensityFunction a(DensityFunction.f var0) {
         return var0.apply(new DensityFunctions.o(var0.a(this.f), this.g, this.h));
      }

      @Override
      public double a() {
         return -this.b();
      }

      @Override
      public double b() {
         return this.f.a();
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         return e;
      }

      public DensityFunction.c j() {
         return this.f;
      }

      @Deprecated
      public double k() {
         return this.g;
      }

      public double l() {
         return this.h;
      }
   }

   interface p extends DensityFunction {
      DensityFunction au_();

      @Override
      default double a(DensityFunction.b var0) {
         return this.a(this.au_().a(var0));
      }

      @Override
      default void a(double[] var0, DensityFunction.a var1) {
         this.au_().a(var0, var1);

         for(int var2 = 0; var2 < var0.length; ++var2) {
            var0[var2] = this.a(var0[var2]);
         }
      }

      double a(double var1);
   }

   static record q(DensityFunction input, double minInclusive, double maxExclusive, DensityFunction whenInRange, DensityFunction whenOutOfRange)
      implements DensityFunction {
      private final DensityFunction f;
      private final double g;
      private final double h;
      private final DensityFunction i;
      private final DensityFunction j;
      public static final MapCodec<DensityFunctions.q> a = RecordCodecBuilder.mapCodec(
         var0 -> var0.group(
                  DensityFunction.d.fieldOf("input").forGetter(DensityFunctions.q::j),
                  DensityFunctions.d.fieldOf("min_inclusive").forGetter(DensityFunctions.q::k),
                  DensityFunctions.d.fieldOf("max_exclusive").forGetter(DensityFunctions.q::l),
                  DensityFunction.d.fieldOf("when_in_range").forGetter(DensityFunctions.q::m),
                  DensityFunction.d.fieldOf("when_out_of_range").forGetter(DensityFunctions.q::n)
               )
               .apply(var0, DensityFunctions.q::new)
      );
      public static final KeyDispatchDataCodec<DensityFunctions.q> e = DensityFunctions.a(a);

      q(DensityFunction var0, double var1, double var3, DensityFunction var5, DensityFunction var6) {
         this.f = var0;
         this.g = var1;
         this.h = var3;
         this.i = var5;
         this.j = var6;
      }

      @Override
      public double a(DensityFunction.b var0) {
         double var1 = this.f.a(var0);
         return var1 >= this.g && var1 < this.h ? this.i.a(var0) : this.j.a(var0);
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         this.f.a(var0, var1);

         for(int var2 = 0; var2 < var0.length; ++var2) {
            double var3 = var0[var2];
            if (var3 >= this.g && var3 < this.h) {
               var0[var2] = this.i.a(var1.a(var2));
            } else {
               var0[var2] = this.j.a(var1.a(var2));
            }
         }
      }

      @Override
      public DensityFunction a(DensityFunction.f var0) {
         return var0.apply(new DensityFunctions.q(this.f.a(var0), this.g, this.h, this.i.a(var0), this.j.a(var0)));
      }

      @Override
      public double a() {
         return Math.min(this.i.a(), this.j.a());
      }

      @Override
      public double b() {
         return Math.max(this.i.b(), this.j.b());
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         return e;
      }

      public DensityFunction j() {
         return this.f;
      }

      public double k() {
         return this.g;
      }

      public double l() {
         return this.h;
      }

      public DensityFunction m() {
         return this.i;
      }

      public DensityFunction n() {
         return this.j;
      }
   }

   protected static record r(DensityFunction.c offsetNoise) implements DensityFunctions.u {
      private final DensityFunction.c a;
      static final KeyDispatchDataCodec<DensityFunctions.r> e = DensityFunctions.a(DensityFunction.c.a, DensityFunctions.r::new, DensityFunctions.r::j);

      protected r(DensityFunction.c var0) {
         this.a = var0;
      }

      @Override
      public double a(DensityFunction.b var0) {
         return this.a((double)var0.a(), (double)var0.b(), (double)var0.c());
      }

      @Override
      public DensityFunction a(DensityFunction.f var0) {
         return var0.apply(new DensityFunctions.r(var0.a(this.a)));
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         return e;
      }

      @Override
      public DensityFunction.c j() {
         return this.a;
      }
   }

   protected static record s(DensityFunction.c offsetNoise) implements DensityFunctions.u {
      private final DensityFunction.c a;
      static final KeyDispatchDataCodec<DensityFunctions.s> e = DensityFunctions.a(DensityFunction.c.a, DensityFunctions.s::new, DensityFunctions.s::j);

      protected s(DensityFunction.c var0) {
         this.a = var0;
      }

      @Override
      public double a(DensityFunction.b var0) {
         return this.a((double)var0.a(), 0.0, (double)var0.c());
      }

      @Override
      public DensityFunction a(DensityFunction.f var0) {
         return var0.apply(new DensityFunctions.s(var0.a(this.a)));
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         return e;
      }

      @Override
      public DensityFunction.c j() {
         return this.a;
      }
   }

   protected static record t(DensityFunction.c offsetNoise) implements DensityFunctions.u {
      private final DensityFunction.c a;
      static final KeyDispatchDataCodec<DensityFunctions.t> e = DensityFunctions.a(DensityFunction.c.a, DensityFunctions.t::new, DensityFunctions.t::j);

      protected t(DensityFunction.c var0) {
         this.a = var0;
      }

      @Override
      public double a(DensityFunction.b var0) {
         return this.a((double)var0.c(), (double)var0.a(), 0.0);
      }

      @Override
      public DensityFunction a(DensityFunction.f var0) {
         return var0.apply(new DensityFunctions.t(var0.a(this.a)));
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         return e;
      }

      @Override
      public DensityFunction.c j() {
         return this.a;
      }
   }

   interface u extends DensityFunction {
      DensityFunction.c j();

      @Override
      default double a() {
         return -this.b();
      }

      @Override
      default double b() {
         return this.j().a() * 4.0;
      }

      default double a(double var0, double var2, double var4) {
         return this.j().a(var0 * 0.25, var2 * 0.25, var4 * 0.25) * 4.0;
      }

      @Override
      default void a(double[] var0, DensityFunction.a var1) {
         var1.a(var0, this);
      }
   }

   protected static record v(DensityFunction shiftX, DensityFunction shiftY, DensityFunction shiftZ, double xzScale, double yScale, DensityFunction.c noise)
      implements DensityFunction {
      private final DensityFunction e;
      private final DensityFunction f;
      private final DensityFunction g;
      private final double h;
      private final double i;
      private final DensityFunction.c j;
      private static final MapCodec<DensityFunctions.v> k = RecordCodecBuilder.mapCodec(
         var0 -> var0.group(
                  DensityFunction.d.fieldOf("shift_x").forGetter(DensityFunctions.v::j),
                  DensityFunction.d.fieldOf("shift_y").forGetter(DensityFunctions.v::k),
                  DensityFunction.d.fieldOf("shift_z").forGetter(DensityFunctions.v::l),
                  Codec.DOUBLE.fieldOf("xz_scale").forGetter(DensityFunctions.v::m),
                  Codec.DOUBLE.fieldOf("y_scale").forGetter(DensityFunctions.v::n),
                  DensityFunction.c.a.fieldOf("noise").forGetter(DensityFunctions.v::o)
               )
               .apply(var0, DensityFunctions.v::new)
      );
      public static final KeyDispatchDataCodec<DensityFunctions.v> a = DensityFunctions.a(k);

      protected v(DensityFunction var0, DensityFunction var1, DensityFunction var2, double var3, double var5, DensityFunction.c var7) {
         this.e = var0;
         this.f = var1;
         this.g = var2;
         this.h = var3;
         this.i = var5;
         this.j = var7;
      }

      @Override
      public double a(DensityFunction.b var0) {
         double var1 = (double)var0.a() * this.h + this.e.a(var0);
         double var3 = (double)var0.b() * this.i + this.f.a(var0);
         double var5 = (double)var0.c() * this.h + this.g.a(var0);
         return this.j.a(var1, var3, var5);
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         var1.a(var0, this);
      }

      @Override
      public DensityFunction a(DensityFunction.f var0) {
         return var0.apply(new DensityFunctions.v(this.e.a(var0), this.f.a(var0), this.g.a(var0), this.h, this.i, var0.a(this.j)));
      }

      @Override
      public double a() {
         return -this.b();
      }

      @Override
      public double b() {
         return this.j.a();
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         return a;
      }

      public DensityFunction j() {
         return this.e;
      }

      public DensityFunction k() {
         return this.f;
      }

      public DensityFunction l() {
         return this.g;
      }

      public double m() {
         return this.h;
      }

      public double n() {
         return this.i;
      }

      public DensityFunction.c o() {
         return this.j;
      }
   }

   public static record w(CubicSpline<DensityFunctions.w.b, DensityFunctions.w.a> spline) implements DensityFunction {
      private final CubicSpline<DensityFunctions.w.b, DensityFunctions.w.a> e;
      private static final Codec<CubicSpline<DensityFunctions.w.b, DensityFunctions.w.a>> f = CubicSpline.a(DensityFunctions.w.a.b);
      private static final MapCodec<DensityFunctions.w> g = f.fieldOf("spline").xmap(DensityFunctions.w::new, DensityFunctions.w::j);
      public static final KeyDispatchDataCodec<DensityFunctions.w> a = DensityFunctions.a(g);

      public w(CubicSpline<DensityFunctions.w.b, DensityFunctions.w.a> var0) {
         this.e = var0;
      }

      @Override
      public double a(DensityFunction.b var0) {
         return (double)this.e.a(new DensityFunctions.w.b(var0));
      }

      @Override
      public double a() {
         return (double)this.e.b();
      }

      @Override
      public double b() {
         return (double)this.e.c();
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         var1.a(var0, this);
      }

      @Override
      public DensityFunction a(DensityFunction.f var0) {
         return var0.apply(new DensityFunctions.w(this.e.a((CubicSpline.d<DensityFunctions.w.a>)(var1x -> var1x.a(var0)))));
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         return a;
      }

      public CubicSpline<DensityFunctions.w.b, DensityFunctions.w.a> j() {
         return this.e;
      }

      public static record a(Holder<DensityFunction> function) implements ToFloatFunction<DensityFunctions.w.b> {
         private final Holder<DensityFunction> c;
         public static final Codec<DensityFunctions.w.a> b = DensityFunction.c.xmap(DensityFunctions.w.a::new, DensityFunctions.w.a::a);

         public a(Holder<DensityFunction> var0) {
            this.c = var0;
         }

         @Override
         public String toString() {
            Optional<ResourceKey<DensityFunction>> var0 = this.c.e();
            if (var0.isPresent()) {
               ResourceKey<DensityFunction> var1 = var0.get();
               if (var1 == NoiseRouterData.d) {
                  return "continents";
               }

               if (var1 == NoiseRouterData.e) {
                  return "erosion";
               }

               if (var1 == NoiseRouterData.f) {
                  return "weirdness";
               }

               if (var1 == NoiseRouterData.g) {
                  return "ridges";
               }
            }

            return "Coordinate[" + this.c + "]";
         }

         public float a(DensityFunctions.w.b var0) {
            return (float)this.c.a().a(var0.a());
         }

         @Override
         public float b() {
            return this.c.b() ? (float)this.c.a().a() : Float.NEGATIVE_INFINITY;
         }

         @Override
         public float c() {
            return this.c.b() ? (float)this.c.a().b() : Float.POSITIVE_INFINITY;
         }

         public DensityFunctions.w.a a(DensityFunction.f var0) {
            return new DensityFunctions.w.a(new Holder.a<>(this.c.a().a(var0)));
         }

         public Holder<DensityFunction> a() {
            return this.c;
         }
      }

      public static record b(DensityFunction.b context) {
         private final DensityFunction.b a;

         public b(DensityFunction.b var0) {
            this.a = var0;
         }
      }
   }

   interface x extends DensityFunction {
      DensityFunction j();

      @Override
      default double a(DensityFunction.b var0) {
         return this.a(var0, this.j().a(var0));
      }

      @Override
      default void a(double[] var0, DensityFunction.a var1) {
         this.j().a(var0, var1);

         for(int var2 = 0; var2 < var0.length; ++var2) {
            var0[var2] = this.a(var1.a(var2), var0[var2]);
         }
      }

      double a(DensityFunction.b var1, double var2);
   }

   interface y extends DensityFunction {
      Logger a = LogUtils.getLogger();

      static DensityFunctions.y a(DensityFunctions.y.a var0, DensityFunction var1, DensityFunction var2) {
         double var3 = var1.a();
         double var5 = var2.a();
         double var7 = var1.b();
         double var9 = var2.b();
         if (var0 == DensityFunctions.y.a.c || var0 == DensityFunctions.y.a.d) {
            boolean var11 = var3 >= var9;
            boolean var12 = var5 >= var7;
            if (var11 || var12) {
               a.warn("Creating a " + var0 + " function between two non-overlapping inputs: " + var1 + " and " + var2);
            }
         }
         double var11 = switch(var0) {
            case a -> var3 + var5;
            case d -> Math.max(var3, var5);
            case c -> Math.min(var3, var5);
            case b -> var3 > 0.0 && var5 > 0.0 ? var3 * var5 : (var7 < 0.0 && var9 < 0.0 ? var7 * var9 : Math.min(var3 * var9, var7 * var5));
         };

         double var13 = switch(var0) {
            case a -> var7 + var9;
            case d -> Math.max(var7, var9);
            case c -> Math.min(var7, var9);
            case b -> var3 > 0.0 && var5 > 0.0 ? var7 * var9 : (var7 < 0.0 && var9 < 0.0 ? var3 * var5 : Math.max(var3 * var5, var7 * var9));
         };
         if (var0 == DensityFunctions.y.a.b || var0 == DensityFunctions.y.a.a) {
            if (var1 instanceof DensityFunctions.h var15) {
               return new DensityFunctions.n(var0 == DensityFunctions.y.a.a ? DensityFunctions.n.a.b : DensityFunctions.n.a.a, var2, var11, var13, var15.a);
            }

            if (var2 instanceof DensityFunctions.h var15) {
               return new DensityFunctions.n(var0 == DensityFunctions.y.a.a ? DensityFunctions.n.a.b : DensityFunctions.n.a.a, var1, var11, var13, var15.a);
            }
         }

         return new DensityFunctions.a(var0, var1, var2, var11, var13);
      }

      DensityFunctions.y.a j();

      DensityFunction k();

      DensityFunction l();

      @Override
      default KeyDispatchDataCodec<? extends DensityFunction> c() {
         return this.j().e;
      }

      public static enum a implements INamable {
         a("add"),
         b("mul"),
         c("min"),
         d("max");

         final KeyDispatchDataCodec<DensityFunctions.y> e = DensityFunctions.a(
            (BiFunction<DensityFunction, DensityFunction, DensityFunctions.y>)((var0, var1x) -> DensityFunctions.y.a(this, var0, var1x)),
            DensityFunctions.y::k,
            DensityFunctions.y::l
         );
         private final String f;

         private a(String var2) {
            this.f = var2;
         }

         @Override
         public String c() {
            return this.f;
         }
      }
   }

   protected static record z(DensityFunction input, DensityFunction.c noise, DensityFunctions.z.a rarityValueMapper) implements DensityFunctions.x {
      private final DensityFunction e;
      private final DensityFunction.c f;
      private final DensityFunctions.z.a g;
      private static final MapCodec<DensityFunctions.z> h = RecordCodecBuilder.mapCodec(
         var0 -> var0.group(
                  DensityFunction.d.fieldOf("input").forGetter(DensityFunctions.z::j),
                  DensityFunction.c.a.fieldOf("noise").forGetter(DensityFunctions.z::k),
                  DensityFunctions.z.a.c.fieldOf("rarity_value_mapper").forGetter(DensityFunctions.z::l)
               )
               .apply(var0, DensityFunctions.z::new)
      );
      public static final KeyDispatchDataCodec<DensityFunctions.z> a = DensityFunctions.a(h);

      protected z(DensityFunction var0, DensityFunction.c var1, DensityFunctions.z.a var2) {
         this.e = var0;
         this.f = var1;
         this.g = var2;
      }

      @Override
      public double a(DensityFunction.b var0, double var1) {
         double var3 = this.g.e.get(var1);
         return var3 * Math.abs(this.f.a((double)var0.a() / var3, (double)var0.b() / var3, (double)var0.c() / var3));
      }

      @Override
      public DensityFunction a(DensityFunction.f var0) {
         return var0.apply(new DensityFunctions.z(this.e.a(var0), var0.a(this.f), this.g));
      }

      @Override
      public double a() {
         return 0.0;
      }

      @Override
      public double b() {
         return this.g.f * this.f.a();
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         return a;
      }

      @Override
      public DensityFunction j() {
         return this.e;
      }

      public DensityFunction.c k() {
         return this.f;
      }

      public DensityFunctions.z.a l() {
         return this.g;
      }

      public static enum a implements INamable {
         a("type_1", NoiseRouterData.a::b, 2.0),
         b("type_2", NoiseRouterData.a::a, 3.0);

         public static final Codec<DensityFunctions.z.a> c = INamable.a(DensityFunctions.z.a::values);
         private final String d;
         final Double2DoubleFunction e;
         final double f;

         private a(String var2, Double2DoubleFunction var3, double var4) {
            this.d = var2;
            this.e = var3;
            this.f = var4;
         }

         @Override
         public String c() {
            return this.d;
         }
      }
   }
}
