package net.minecraft.world.level.levelgen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableList.Builder;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.QuartPos;
import net.minecraft.core.SectionPosition;
import net.minecraft.server.level.BlockPosition2D;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.MathHelper;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.material.MaterialRuleList;

public class NoiseChunk implements DensityFunction.a, DensityFunction.b {
   private final NoiseSettings a;
   final int b;
   final int c;
   final int d;
   private final int e;
   private final int f;
   final int g;
   final int h;
   final List<NoiseChunk.i> i;
   final List<NoiseChunk.e> j;
   private final Map<DensityFunction, DensityFunction> k = new HashMap<>();
   private final Long2IntMap l = new Long2IntOpenHashMap();
   private final Aquifer m;
   private final DensityFunction n;
   private final NoiseChunk.c o;
   private final Blender p;
   private final NoiseChunk.g q;
   private final NoiseChunk.g r;
   private final DensityFunctions.c s;
   private long t = ChunkCoordIntPair.a;
   private Blender.a u = new Blender.a(1.0, 0.0);
   final int v;
   final int w;
   final int x;
   boolean y;
   boolean z;
   private int A;
   int B;
   private int C;
   int D;
   int E;
   int F;
   long G;
   long H;
   int I;
   private final DensityFunction.a J = new DensityFunction.a() {
      @Override
      public DensityFunction.b a(int var0) {
         NoiseChunk.this.B = (var0 + NoiseChunk.this.d) * NoiseChunk.this.x;
         ++NoiseChunk.this.G;
         NoiseChunk.this.E = 0;
         NoiseChunk.this.I = var0;
         return NoiseChunk.this;
      }

      @Override
      public void a(double[] var0, DensityFunction var1) {
         for(int var2 = 0; var2 < NoiseChunk.this.c + 1; ++var2) {
            NoiseChunk.this.B = (var2 + NoiseChunk.this.d) * NoiseChunk.this.x;
            ++NoiseChunk.this.G;
            NoiseChunk.this.E = 0;
            NoiseChunk.this.I = var2;
            var0[var2] = var1.a(NoiseChunk.this);
         }
      }
   };

   public static NoiseChunk a(IChunkAccess var0, RandomState var1, DensityFunctions.c var2, GeneratorSettingBase var3, Aquifer.a var4, Blender var5) {
      NoiseSettings var6 = var3.f().a(var0);
      ChunkCoordIntPair var7 = var0.f();
      int var8 = 16 / var6.b();
      return new NoiseChunk(var8, var1, var7.d(), var7.e(), var6, var2, var3, var4, var5);
   }

   public NoiseChunk(
      int var0, RandomState var1, int var2, int var3, NoiseSettings var4, DensityFunctions.c var5, GeneratorSettingBase var6, Aquifer.a var7, Blender var8
   ) {
      this.a = var4;
      this.w = var4.b();
      this.x = var4.a();
      this.b = var0;
      this.c = MathHelper.a(var4.d(), this.x);
      this.d = MathHelper.a(var4.c(), this.x);
      this.e = Math.floorDiv(var2, this.w);
      this.f = Math.floorDiv(var3, this.w);
      this.i = Lists.newArrayList();
      this.j = Lists.newArrayList();
      this.g = QuartPos.a(var2);
      this.h = QuartPos.a(var3);
      this.v = QuartPos.a(var0 * this.w);
      this.p = var8;
      this.s = var5;
      this.q = new NoiseChunk.g(new NoiseChunk.a(), false);
      this.r = new NoiseChunk.g(new NoiseChunk.b(), false);

      for(int var9 = 0; var9 <= this.v; ++var9) {
         int var10 = this.g + var9;
         int var11 = QuartPos.c(var10);

         for(int var12 = 0; var12 <= this.v; ++var12) {
            int var13 = this.h + var12;
            int var14 = QuartPos.c(var13);
            Blender.a var15 = var8.a(var11, var14);
            this.q.f[var9][var12] = var15.a();
            this.r.f[var9][var12] = var15.b();
         }
      }

      NoiseRouter var9 = var1.a();
      NoiseRouter var10 = var9.a(this::a);
      if (!var6.b()) {
         this.m = Aquifer.a(var7);
      } else {
         int var11 = SectionPosition.a(var2);
         int var12 = SectionPosition.a(var3);
         this.m = Aquifer.a(this, new ChunkCoordIntPair(var11, var12), var10, var1.d(), var4.c(), var4.d(), var7);
      }

      Builder<NoiseChunk.c> var11 = ImmutableList.builder();
      DensityFunction var12 = DensityFunctions.e(DensityFunctions.a(var10.l(), DensityFunctions.b.a)).a(this::a);
      var11.add((NoiseChunk.c)var1x -> this.m.a(var1x, var12.a(var1x)));
      if (var6.c()) {
         var11.add(OreVeinifier.a(var10.m(), var10.n(), var10.o(), var1.e()));
      }

      this.o = new MaterialRuleList(var11.build());
      this.n = var10.k();
   }

   protected Climate.Sampler a(NoiseRouter var0, List<Climate.d> var1) {
      return new Climate.Sampler(
         var0.e().a(this::a), var0.f().a(this::a), var0.g().a(this::a), var0.h().a(this::a), var0.i().a(this::a), var0.j().a(this::a), var1
      );
   }

   @Nullable
   protected IBlockData e() {
      return this.o.calculate(this);
   }

   @Override
   public int a() {
      return this.A + this.D;
   }

   @Override
   public int b() {
      return this.B + this.E;
   }

   @Override
   public int c() {
      return this.C + this.F;
   }

   public int a(int var0, int var1) {
      int var2 = QuartPos.c(QuartPos.a(var0));
      int var3 = QuartPos.c(QuartPos.a(var1));
      return this.l.computeIfAbsent(BlockPosition2D.a(var2, var3), this::a);
   }

   private int a(long var0) {
      int var2 = BlockPosition2D.a(var0);
      int var3 = BlockPosition2D.b(var0);
      int var4 = this.a.c();

      for(int var5 = var4 + this.a.d(); var5 >= var4; var5 -= this.x) {
         if (this.n.a(new DensityFunction.e(var2, var5, var3)) > 0.390625) {
            return var5;
         }
      }

      return Integer.MAX_VALUE;
   }

   @Override
   public Blender d() {
      return this.p;
   }

   private void a(boolean var0, int var1) {
      this.A = var1 * this.w;
      this.D = 0;

      for(int var2 = 0; var2 < this.b + 1; ++var2) {
         int var3 = this.f + var2;
         this.C = var3 * this.w;
         this.F = 0;
         ++this.H;

         for(NoiseChunk.i var5 : this.i) {
            double[] var6 = (var0 ? var5.e : var5.f)[var2];
            var5.a(var6, this.J);
         }
      }

      ++this.H;
   }

   public void f() {
      if (this.y) {
         throw new IllegalStateException("Staring interpolation twice");
      } else {
         this.y = true;
         this.G = 0L;
         this.a(true, this.e);
      }
   }

   public void b(int var0) {
      this.a(false, this.e + var0 + 1);
      this.A = (this.e + var0) * this.w;
   }

   public NoiseChunk c(int var0) {
      int var1 = Math.floorMod(var0, this.w);
      int var2 = Math.floorDiv(var0, this.w);
      int var3 = Math.floorMod(var2, this.w);
      int var4 = this.x - 1 - Math.floorDiv(var2, this.w);
      this.D = var3;
      this.E = var4;
      this.F = var1;
      this.I = var0;
      return this;
   }

   @Override
   public void a(double[] var0, DensityFunction var1) {
      this.I = 0;

      for(int var2 = this.x - 1; var2 >= 0; --var2) {
         this.E = var2;

         for(int var3 = 0; var3 < this.w; ++var3) {
            this.D = var3;

            for(int var4 = 0; var4 < this.w; ++var4) {
               this.F = var4;
               var0[this.I++] = var1.a(this);
            }
         }
      }
   }

   public void b(int var0, int var1) {
      this.i.forEach(var2x -> var2x.b(var0, var1));
      this.z = true;
      this.B = (var0 + this.d) * this.x;
      this.C = (this.f + var1) * this.w;
      ++this.H;

      for(NoiseChunk.e var3 : this.j) {
         var3.e.a(var3.f, this);
      }

      ++this.H;
      this.z = false;
   }

   public void a(int var0, double var1) {
      this.E = var0 - this.B;
      this.i.forEach(var2x -> var2x.a(var1));
   }

   public void b(int var0, double var1) {
      this.D = var0 - this.A;
      this.i.forEach(var2x -> var2x.b(var1));
   }

   public void c(int var0, double var1) {
      this.F = var0 - this.C;
      ++this.G;
      this.i.forEach(var2x -> var2x.c(var1));
   }

   public void g() {
      if (!this.y) {
         throw new IllegalStateException("Staring interpolation twice");
      } else {
         this.y = false;
      }
   }

   public void h() {
      this.i.forEach(NoiseChunk.i::l);
   }

   public Aquifer i() {
      return this.m;
   }

   protected int j() {
      return this.w;
   }

   protected int k() {
      return this.x;
   }

   Blender.a c(int var0, int var1) {
      long var2 = ChunkCoordIntPair.c(var0, var1);
      if (this.t == var2) {
         return this.u;
      } else {
         this.t = var2;
         Blender.a var4 = this.p.a(var0, var1);
         this.u = var4;
         return var4;
      }
   }

   protected DensityFunction a(DensityFunction var0) {
      return this.k.computeIfAbsent(var0, this::b);
   }

   private DensityFunction b(DensityFunction var0) {
      if (var0 instanceof DensityFunctions.l var1) {
         return (DensityFunction)(switch(var1.j()) {
            case a -> new NoiseChunk.i(var1.k());
            case b -> new NoiseChunk.g(var1.k(), true);
            case c -> new NoiseChunk.d(var1.k());
            case d -> new NoiseChunk.f(var1.k());
            case e -> new NoiseChunk.e(var1.k());
         });
      } else {
         if (this.p != Blender.a()) {
            if (var0 == DensityFunctions.d.a) {
               return this.q;
            }

            if (var0 == DensityFunctions.f.a) {
               return this.r;
            }
         }

         if (var0 == DensityFunctions.b.a) {
            return this.s;
         } else {
            return var0 instanceof DensityFunctions.j var1 ? var1.j().a() : var0;
         }
      }
   }

   class a implements NoiseChunk.h {
      @Override
      public DensityFunction k() {
         return DensityFunctions.d.a;
      }

      @Override
      public DensityFunction a(DensityFunction.f var0) {
         return this.k().a(var0);
      }

      @Override
      public double a(DensityFunction.b var0) {
         return NoiseChunk.this.c(var0.a(), var0.c()).a();
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         var1.a(var0, this);
      }

      @Override
      public double a() {
         return 0.0;
      }

      @Override
      public double b() {
         return 1.0;
      }

      @Override
      public KeyDispatchDataCodec<? extends DensityFunction> c() {
         return DensityFunctions.d.e;
      }
   }

   class b implements NoiseChunk.h {
      @Override
      public DensityFunction k() {
         return DensityFunctions.f.a;
      }

      @Override
      public DensityFunction a(DensityFunction.f var0) {
         return this.k().a(var0);
      }

      @Override
      public double a(DensityFunction.b var0) {
         return NoiseChunk.this.c(var0.a(), var0.c()).b();
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         var1.a(var0, this);
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
         return DensityFunctions.f.e;
      }
   }

   @FunctionalInterface
   public interface c {
      @Nullable
      IBlockData calculate(DensityFunction.b var1);
   }

   static class d implements DensityFunctions.m, NoiseChunk.h {
      private final DensityFunction a;
      private long e = ChunkCoordIntPair.a;
      private double f;

      d(DensityFunction var0) {
         this.a = var0;
      }

      @Override
      public double a(DensityFunction.b var0) {
         int var1 = var0.a();
         int var2 = var0.c();
         long var3 = ChunkCoordIntPair.c(var1, var2);
         if (this.e == var3) {
            return this.f;
         } else {
            this.e = var3;
            double var5 = this.a.a(var0);
            this.f = var5;
            return var5;
         }
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         this.a.a(var0, var1);
      }

      @Override
      public DensityFunction k() {
         return this.a;
      }

      @Override
      public DensityFunctions.l.a j() {
         return DensityFunctions.l.a.c;
      }
   }

   class e implements DensityFunctions.m, NoiseChunk.h {
      final DensityFunction e;
      final double[] f;

      e(DensityFunction var1) {
         this.e = var1;
         this.f = new double[NoiseChunk.this.w * NoiseChunk.this.w * NoiseChunk.this.x];
         NoiseChunk.this.j.add(this);
      }

      @Override
      public double a(DensityFunction.b var0) {
         if (var0 != NoiseChunk.this) {
            return this.e.a(var0);
         } else if (!NoiseChunk.this.y) {
            throw new IllegalStateException("Trying to sample interpolator outside the interpolation loop");
         } else {
            int var1 = NoiseChunk.this.D;
            int var2 = NoiseChunk.this.E;
            int var3 = NoiseChunk.this.F;
            return var1 >= 0 && var2 >= 0 && var3 >= 0 && var1 < NoiseChunk.this.w && var2 < NoiseChunk.this.x && var3 < NoiseChunk.this.w
               ? this.f[((NoiseChunk.this.x - 1 - var2) * NoiseChunk.this.w + var1) * NoiseChunk.this.w + var3]
               : this.e.a(var0);
         }
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         var1.a(var0, this);
      }

      @Override
      public DensityFunction k() {
         return this.e;
      }

      @Override
      public DensityFunctions.l.a j() {
         return DensityFunctions.l.a.e;
      }
   }

   class f implements DensityFunctions.m, NoiseChunk.h {
      private final DensityFunction e;
      private long f;
      private long g;
      private double h;
      @Nullable
      private double[] i;

      f(DensityFunction var1) {
         this.e = var1;
      }

      @Override
      public double a(DensityFunction.b var0) {
         if (var0 != NoiseChunk.this) {
            return this.e.a(var0);
         } else if (this.i != null && this.g == NoiseChunk.this.H) {
            return this.i[NoiseChunk.this.I];
         } else if (this.f == NoiseChunk.this.G) {
            return this.h;
         } else {
            this.f = NoiseChunk.this.G;
            double var1 = this.e.a(var0);
            this.h = var1;
            return var1;
         }
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         if (this.i != null && this.g == NoiseChunk.this.H) {
            System.arraycopy(this.i, 0, var0, 0, var0.length);
         } else {
            this.k().a(var0, var1);
            if (this.i != null && this.i.length == var0.length) {
               System.arraycopy(var0, 0, this.i, 0, var0.length);
            } else {
               this.i = (double[])var0.clone();
            }

            this.g = NoiseChunk.this.H;
         }
      }

      @Override
      public DensityFunction k() {
         return this.e;
      }

      @Override
      public DensityFunctions.l.a j() {
         return DensityFunctions.l.a.d;
      }
   }

   class g implements DensityFunctions.m, NoiseChunk.h {
      private final DensityFunction e;
      final double[][] f;

      g(DensityFunction var1, boolean var2) {
         this.e = var1;
         this.f = new double[NoiseChunk.this.v + 1][NoiseChunk.this.v + 1];
         if (var2) {
            for(int var3 = 0; var3 <= NoiseChunk.this.v; ++var3) {
               int var4 = NoiseChunk.this.g + var3;
               int var5 = QuartPos.c(var4);

               for(int var6 = 0; var6 <= NoiseChunk.this.v; ++var6) {
                  int var7 = NoiseChunk.this.h + var6;
                  int var8 = QuartPos.c(var7);
                  this.f[var3][var6] = var1.a(new DensityFunction.e(var5, 0, var8));
               }
            }
         }
      }

      @Override
      public double a(DensityFunction.b var0) {
         int var1 = QuartPos.a(var0.a());
         int var2 = QuartPos.a(var0.c());
         int var3 = var1 - NoiseChunk.this.g;
         int var4 = var2 - NoiseChunk.this.h;
         int var5 = this.f.length;
         return var3 >= 0 && var4 >= 0 && var3 < var5 && var4 < var5 ? this.f[var3][var4] : this.e.a(var0);
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         var1.a(var0, this);
      }

      @Override
      public DensityFunction k() {
         return this.e;
      }

      @Override
      public DensityFunctions.l.a j() {
         return DensityFunctions.l.a.b;
      }
   }

   interface h extends DensityFunction {
      DensityFunction k();

      @Override
      default double a() {
         return this.k().a();
      }

      @Override
      default double b() {
         return this.k().b();
      }
   }

   public class i implements DensityFunctions.m, NoiseChunk.h {
      double[][] e;
      double[][] f;
      private final DensityFunction g;
      private double h;
      private double i;
      private double j;
      private double k;
      private double l;
      private double m;
      private double n;
      private double o;
      private double p;
      private double q;
      private double r;
      private double s;
      private double t;
      private double u;
      private double v;

      i(DensityFunction var1) {
         this.g = var1;
         this.e = this.a(NoiseChunk.this.c, NoiseChunk.this.b);
         this.f = this.a(NoiseChunk.this.c, NoiseChunk.this.b);
         NoiseChunk.this.i.add(this);
      }

      private double[][] a(int var0, int var1) {
         int var2 = var1 + 1;
         int var3 = var0 + 1;
         double[][] var4 = new double[var2][var3];

         for(int var5 = 0; var5 < var2; ++var5) {
            var4[var5] = new double[var3];
         }

         return var4;
      }

      void b(int var0, int var1) {
         this.h = this.e[var1][var0];
         this.i = this.e[var1 + 1][var0];
         this.j = this.f[var1][var0];
         this.k = this.f[var1 + 1][var0];
         this.l = this.e[var1][var0 + 1];
         this.m = this.e[var1 + 1][var0 + 1];
         this.n = this.f[var1][var0 + 1];
         this.o = this.f[var1 + 1][var0 + 1];
      }

      void a(double var0) {
         this.p = MathHelper.d(var0, this.h, this.l);
         this.q = MathHelper.d(var0, this.j, this.n);
         this.r = MathHelper.d(var0, this.i, this.m);
         this.s = MathHelper.d(var0, this.k, this.o);
      }

      void b(double var0) {
         this.t = MathHelper.d(var0, this.p, this.q);
         this.u = MathHelper.d(var0, this.r, this.s);
      }

      void c(double var0) {
         this.v = MathHelper.d(var0, this.t, this.u);
      }

      @Override
      public double a(DensityFunction.b var0) {
         if (var0 != NoiseChunk.this) {
            return this.g.a(var0);
         } else if (!NoiseChunk.this.y) {
            throw new IllegalStateException("Trying to sample interpolator outside the interpolation loop");
         } else {
            return NoiseChunk.this.z
               ? MathHelper.a(
                  (double)NoiseChunk.this.D / (double)NoiseChunk.this.w,
                  (double)NoiseChunk.this.E / (double)NoiseChunk.this.x,
                  (double)NoiseChunk.this.F / (double)NoiseChunk.this.w,
                  this.h,
                  this.j,
                  this.l,
                  this.n,
                  this.i,
                  this.k,
                  this.m,
                  this.o
               )
               : this.v;
         }
      }

      @Override
      public void a(double[] var0, DensityFunction.a var1) {
         if (NoiseChunk.this.z) {
            var1.a(var0, this);
         } else {
            this.k().a(var0, var1);
         }
      }

      @Override
      public DensityFunction k() {
         return this.g;
      }

      private void l() {
         double[][] var0 = this.e;
         this.e = this.f;
         this.f = var0;
      }

      @Override
      public DensityFunctions.l.a j() {
         return DensityFunctions.l.a.a;
      }
   }
}
