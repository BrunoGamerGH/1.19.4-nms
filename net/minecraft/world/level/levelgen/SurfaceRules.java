package net.minecraft.world.level.levelgen;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.Holder;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.IChunkAccess;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;

public class SurfaceRules {
   public static final SurfaceRules.f a = a(0, false, CaveSurface.b);
   public static final SurfaceRules.f b = a(0, true, CaveSurface.b);
   public static final SurfaceRules.f c = a(0, true, 6, CaveSurface.b);
   public static final SurfaceRules.f d = a(0, true, 30, CaveSurface.b);
   public static final SurfaceRules.f e = a(0, false, CaveSurface.a);
   public static final SurfaceRules.f f = a(0, true, CaveSurface.a);

   public static SurfaceRules.f a(int var0, boolean var1, CaveSurface var2) {
      return new SurfaceRules.t(var0, var1, 0, var2);
   }

   public static SurfaceRules.f a(int var0, boolean var1, int var2, CaveSurface var3) {
      return new SurfaceRules.t(var0, var1, var2, var3);
   }

   public static SurfaceRules.f a(SurfaceRules.f var0) {
      return new SurfaceRules.n(var0);
   }

   public static SurfaceRules.f a(VerticalAnchor var0, int var1) {
      return new SurfaceRules.aa(var0, var1, false);
   }

   public static SurfaceRules.f b(VerticalAnchor var0, int var1) {
      return new SurfaceRules.aa(var0, var1, true);
   }

   public static SurfaceRules.f a(int var0, int var1) {
      return new SurfaceRules.z(var0, var1, false);
   }

   public static SurfaceRules.f b(int var0, int var1) {
      return new SurfaceRules.z(var0, var1, true);
   }

   @SafeVarargs
   public static SurfaceRules.f a(ResourceKey<BiomeBase>... var0) {
      return a(List.of(var0));
   }

   private static SurfaceRules.c a(List<ResourceKey<BiomeBase>> var0) {
      return new SurfaceRules.c(var0);
   }

   public static SurfaceRules.f a(ResourceKey<NoiseGeneratorNormal.a> var0, double var1) {
      return a(var0, var1, Double.MAX_VALUE);
   }

   public static SurfaceRules.f a(ResourceKey<NoiseGeneratorNormal.a> var0, double var1, double var3) {
      return new SurfaceRules.l(var0, var1, var3);
   }

   public static SurfaceRules.f a(String var0, VerticalAnchor var1, VerticalAnchor var2) {
      return new SurfaceRules.y(new MinecraftKey(var0), var1, var2);
   }

   public static SurfaceRules.f a() {
      return SurfaceRules.s.a;
   }

   public static SurfaceRules.f b() {
      return SurfaceRules.h.a;
   }

   public static SurfaceRules.f c() {
      return SurfaceRules.a.a;
   }

   public static SurfaceRules.f d() {
      return SurfaceRules.v.a;
   }

   public static SurfaceRules.o a(SurfaceRules.f var0, SurfaceRules.o var1) {
      return new SurfaceRules.x(var0, var1);
   }

   public static SurfaceRules.o a(SurfaceRules.o... var0) {
      if (var0.length == 0) {
         throw new IllegalArgumentException("Need at least 1 rule for a sequence");
      } else {
         return new SurfaceRules.q(Arrays.asList(var0));
      }
   }

   public static SurfaceRules.o a(IBlockData var0) {
      return new SurfaceRules.d(var0);
   }

   public static SurfaceRules.o e() {
      return SurfaceRules.b.a;
   }

   static <A> Codec<? extends A> a(IRegistry<Codec<? extends A>> var0, String var1, KeyDispatchDataCodec<? extends A> var2) {
      return IRegistry.a(var0, var1, var2.a());
   }

   static enum a implements SurfaceRules.f {
      a;

      static final KeyDispatchDataCodec<SurfaceRules.a> c = KeyDispatchDataCodec.a(MapCodec.unit(a));

      @Override
      public KeyDispatchDataCodec<? extends SurfaceRules.f> a() {
         return c;
      }

      public SurfaceRules.e a(SurfaceRules.g var0) {
         return var0.i;
      }
   }

   static record aa(VerticalAnchor anchor, int surfaceDepthMultiplier, boolean addStoneDepth) implements SurfaceRules.f {
      final VerticalAnchor a;
      final int c;
      final boolean d;
      static final KeyDispatchDataCodec<SurfaceRules.aa> e = KeyDispatchDataCodec.a(
         RecordCodecBuilder.mapCodec(
            var0 -> var0.group(
                     VerticalAnchor.a.fieldOf("anchor").forGetter(SurfaceRules.aa::b),
                     Codec.intRange(-20, 20).fieldOf("surface_depth_multiplier").forGetter(SurfaceRules.aa::c),
                     Codec.BOOL.fieldOf("add_stone_depth").forGetter(SurfaceRules.aa::d)
                  )
                  .apply(var0, SurfaceRules.aa::new)
         )
      );

      aa(VerticalAnchor var0, int var1, boolean var2) {
         this.a = var0;
         this.c = var1;
         this.d = var2;
      }

      @Override
      public KeyDispatchDataCodec<? extends SurfaceRules.f> a() {
         return e;
      }

      public SurfaceRules.e a(final SurfaceRules.g var0) {
         class a extends SurfaceRules.k {
            a() {
               super(var0);
            }

            @Override
            protected boolean a() {
               return this.c.B + (aa.this.d ? this.c.E : 0) >= aa.this.a.a(this.c.n) + this.c.t * aa.this.c;
            }
         }

         return new a();
      }

      public VerticalAnchor b() {
         return this.a;
      }
   }

   static enum b implements SurfaceRules.o {
      a;

      static final KeyDispatchDataCodec<SurfaceRules.b> c = KeyDispatchDataCodec.a(MapCodec.unit(a));

      @Override
      public KeyDispatchDataCodec<? extends SurfaceRules.o> a() {
         return c;
      }

      public SurfaceRules.u a(SurfaceRules.g var0) {
         return var0.e::a;
      }
   }

   static final class c implements SurfaceRules.f {
      static final KeyDispatchDataCodec<SurfaceRules.c> a = KeyDispatchDataCodec.a(
         ResourceKey.a(Registries.an).listOf().fieldOf("biome_is").xmap(SurfaceRules::a, var0 -> var0.c)
      );
      private final List<ResourceKey<BiomeBase>> c;
      final Predicate<ResourceKey<BiomeBase>> d;

      c(List<ResourceKey<BiomeBase>> var0) {
         this.c = var0;
         this.d = Set.copyOf(var0)::contains;
      }

      @Override
      public KeyDispatchDataCodec<? extends SurfaceRules.f> a() {
         return a;
      }

      public SurfaceRules.e a(final SurfaceRules.g var0) {
         class a extends SurfaceRules.k {
            a() {
               super(var0);
            }

            @Override
            protected boolean a() {
               return this.c.A.get().a(c.this.d);
            }
         }

         return new a();
      }

      @Override
      public boolean equals(Object var0) {
         if (this == var0) {
            return true;
         } else {
            return var0 instanceof SurfaceRules.c var1 ? this.c.equals(var1.c) : false;
         }
      }

      @Override
      public int hashCode() {
         return this.c.hashCode();
      }

      @Override
      public String toString() {
         return "BiomeConditionSource[biomes=" + this.c + "]";
      }
   }

   static record d(IBlockData resultState, SurfaceRules.r rule) implements SurfaceRules.o {
      private final IBlockData a;
      private final SurfaceRules.r c;
      static final KeyDispatchDataCodec<SurfaceRules.d> d = KeyDispatchDataCodec.a(
         IBlockData.b.xmap(SurfaceRules.d::new, SurfaceRules.d::b).fieldOf("result_state")
      );

      d(IBlockData var0) {
         this(var0, new SurfaceRules.r(var0));
      }

      private d(IBlockData var0, SurfaceRules.r var1) {
         this.a = var0;
         this.c = var1;
      }

      @Override
      public KeyDispatchDataCodec<? extends SurfaceRules.o> a() {
         return d;
      }

      public SurfaceRules.u a(SurfaceRules.g var0) {
         return this.c;
      }

      public IBlockData b() {
         return this.a;
      }
   }

   interface e {
      boolean b();
   }

   public interface f extends Function<SurfaceRules.g, SurfaceRules.e> {
      Codec<SurfaceRules.f> b = BuiltInRegistries.ad.q().dispatch(var0 -> var0.a().a(), Function.identity());

      static Codec<? extends SurfaceRules.f> a(IRegistry<Codec<? extends SurfaceRules.f>> var0) {
         SurfaceRules.a(var0, "biome", SurfaceRules.c.a);
         SurfaceRules.a(var0, "noise_threshold", SurfaceRules.l.e);
         SurfaceRules.a(var0, "vertical_gradient", SurfaceRules.y.e);
         SurfaceRules.a(var0, "y_above", SurfaceRules.aa.e);
         SurfaceRules.a(var0, "water", SurfaceRules.z.e);
         SurfaceRules.a(var0, "temperature", SurfaceRules.v.c);
         SurfaceRules.a(var0, "steep", SurfaceRules.s.c);
         SurfaceRules.a(var0, "not", SurfaceRules.n.c);
         SurfaceRules.a(var0, "hole", SurfaceRules.h.c);
         SurfaceRules.a(var0, "above_preliminary_surface", SurfaceRules.a.c);
         return SurfaceRules.a(var0, "stone_depth", SurfaceRules.t.f);
      }

      KeyDispatchDataCodec<? extends SurfaceRules.f> a();
   }

   protected static final class g {
      private static final int a = 8;
      private static final int b = 4;
      private static final int c = 16;
      private static final int d = 15;
      final SurfaceSystem e;
      final SurfaceRules.e f = new SurfaceRules.g.d(this);
      final SurfaceRules.e g = new SurfaceRules.g.c(this);
      final SurfaceRules.e h = new SurfaceRules.g.b(this);
      final SurfaceRules.e i = new SurfaceRules.g.a();
      final RandomState j;
      final IChunkAccess k;
      private final NoiseChunk l;
      private final Function<BlockPosition, Holder<BiomeBase>> m;
      final WorldGenerationContext n;
      private long o = Long.MAX_VALUE;
      private final int[] p = new int[4];
      long q = -9223372036854775807L;
      int r;
      int s;
      int t;
      private long u = this.q - 1L;
      private double v;
      private long w = this.q - 1L;
      private int x;
      long y = -9223372036854775807L;
      final BlockPosition.MutableBlockPosition z = new BlockPosition.MutableBlockPosition();
      Supplier<Holder<BiomeBase>> A;
      int B;
      int C;
      int D;
      int E;

      protected g(
         SurfaceSystem var0,
         RandomState var1,
         IChunkAccess var2,
         NoiseChunk var3,
         Function<BlockPosition, Holder<BiomeBase>> var4,
         IRegistry<BiomeBase> var5,
         WorldGenerationContext var6
      ) {
         this.e = var0;
         this.j = var1;
         this.k = var2;
         this.l = var3;
         this.m = var4;
         this.n = var6;
      }

      protected void a(int var0, int var1) {
         ++this.q;
         ++this.y;
         this.r = var0;
         this.s = var1;
         this.t = this.e.a(var0, var1);
      }

      protected void a(int var0, int var1, int var2, int var3, int var4, int var5) {
         ++this.y;
         this.A = Suppliers.memoize(() -> this.m.apply(this.z.d(var3, var4, var5)));
         this.B = var4;
         this.C = var2;
         this.D = var1;
         this.E = var0;
      }

      protected double a() {
         if (this.u != this.q) {
            this.u = this.q;
            this.v = this.e.b(this.r, this.s);
         }

         return this.v;
      }

      private static int a(int var0) {
         return var0 >> 4;
      }

      private static int b(int var0) {
         return var0 << 4;
      }

      protected int b() {
         if (this.w != this.q) {
            this.w = this.q;
            int var0 = a(this.r);
            int var1 = a(this.s);
            long var2 = ChunkCoordIntPair.c(var0, var1);
            if (this.o != var2) {
               this.o = var2;
               this.p[0] = this.l.a(b(var0), b(var1));
               this.p[1] = this.l.a(b(var0 + 1), b(var1));
               this.p[2] = this.l.a(b(var0), b(var1 + 1));
               this.p[3] = this.l.a(b(var0 + 1), b(var1 + 1));
            }

            int var4 = MathHelper.a(
               MathHelper.a(
                  (double)((float)(this.r & 15) / 16.0F),
                  (double)((float)(this.s & 15) / 16.0F),
                  (double)this.p[0],
                  (double)this.p[1],
                  (double)this.p[2],
                  (double)this.p[3]
               )
            );
            this.x = var4 + this.t - 8;
         }

         return this.x;
      }

      final class a implements SurfaceRules.e {
         @Override
         public boolean b() {
            return g.this.B >= g.this.b();
         }
      }

      static final class b extends SurfaceRules.j {
         b(SurfaceRules.g var0) {
            super(var0);
         }

         @Override
         protected boolean a() {
            return this.c.t <= 0;
         }
      }

      static class c extends SurfaceRules.j {
         c(SurfaceRules.g var0) {
            super(var0);
         }

         @Override
         protected boolean a() {
            int var0 = this.c.r & 15;
            int var1 = this.c.s & 15;
            int var2 = Math.max(var1 - 1, 0);
            int var3 = Math.min(var1 + 1, 15);
            IChunkAccess var4 = this.c.k;
            int var5 = var4.a(HeightMap.Type.a, var0, var2);
            int var6 = var4.a(HeightMap.Type.a, var0, var3);
            if (var6 >= var5 + 4) {
               return true;
            } else {
               int var7 = Math.max(var0 - 1, 0);
               int var8 = Math.min(var0 + 1, 15);
               int var9 = var4.a(HeightMap.Type.a, var7, var1);
               int var10 = var4.a(HeightMap.Type.a, var8, var1);
               return var9 >= var10 + 4;
            }
         }
      }

      static class d extends SurfaceRules.k {
         d(SurfaceRules.g var0) {
            super(var0);
         }

         @Override
         protected boolean a() {
            return this.c.A.get().a().b(this.c.z.d(this.c.r, this.c.B, this.c.s));
         }
      }
   }

   static enum h implements SurfaceRules.f {
      a;

      static final KeyDispatchDataCodec<SurfaceRules.h> c = KeyDispatchDataCodec.a(MapCodec.unit(a));

      @Override
      public KeyDispatchDataCodec<? extends SurfaceRules.f> a() {
         return c;
      }

      public SurfaceRules.e a(SurfaceRules.g var0) {
         return var0.h;
      }
   }

   abstract static class i implements SurfaceRules.e {
      protected final SurfaceRules.g c;
      private long a;
      @Nullable
      Boolean d;

      protected i(SurfaceRules.g var0) {
         this.c = var0;
         this.a = this.c() - 1L;
      }

      @Override
      public boolean b() {
         long var0 = this.c();
         if (var0 == this.a) {
            if (this.d == null) {
               throw new IllegalStateException("Update triggered but the result is null");
            } else {
               return this.d;
            }
         } else {
            this.a = var0;
            this.d = this.a();
            return this.d;
         }
      }

      protected abstract long c();

      protected abstract boolean a();
   }

   abstract static class j extends SurfaceRules.i {
      protected j(SurfaceRules.g var0) {
         super(var0);
      }

      @Override
      protected long c() {
         return this.c.q;
      }
   }

   abstract static class k extends SurfaceRules.i {
      protected k(SurfaceRules.g var0) {
         super(var0);
      }

      @Override
      protected long c() {
         return this.c.y;
      }
   }

   static record l(ResourceKey<NoiseGeneratorNormal.a> noise, double minThreshold, double maxThreshold) implements SurfaceRules.f {
      private final ResourceKey<NoiseGeneratorNormal.a> a;
      final double c;
      final double d;
      static final KeyDispatchDataCodec<SurfaceRules.l> e = KeyDispatchDataCodec.a(
         RecordCodecBuilder.mapCodec(
            var0 -> var0.group(
                     ResourceKey.a(Registries.av).fieldOf("noise").forGetter(SurfaceRules.l::b),
                     Codec.DOUBLE.fieldOf("min_threshold").forGetter(SurfaceRules.l::c),
                     Codec.DOUBLE.fieldOf("max_threshold").forGetter(SurfaceRules.l::d)
                  )
                  .apply(var0, SurfaceRules.l::new)
         )
      );

      l(ResourceKey<NoiseGeneratorNormal.a> var0, double var1, double var3) {
         this.a = var0;
         this.c = var1;
         this.d = var3;
      }

      @Override
      public KeyDispatchDataCodec<? extends SurfaceRules.f> a() {
         return e;
      }

      public SurfaceRules.e a(final SurfaceRules.g var0) {
         final NoiseGeneratorNormal var1 = var0.j.a(this.a);

         class a extends SurfaceRules.j {
            a() {
               super(var0);
            }

            @Override
            protected boolean a() {
               double var0 = var1.a((double)this.c.r, 0.0, (double)this.c.s);
               return var0 >= l.this.c && var0 <= l.this.d;
            }
         }

         return new a();
      }

      public ResourceKey<NoiseGeneratorNormal.a> b() {
         return this.a;
      }
   }

   static record m(SurfaceRules.e target) implements SurfaceRules.e {
      private final SurfaceRules.e a;

      m(SurfaceRules.e var0) {
         this.a = var0;
      }

      @Override
      public boolean b() {
         return !this.a.b();
      }
   }

   static record n(SurfaceRules.f target) implements SurfaceRules.f {
      private final SurfaceRules.f a;
      static final KeyDispatchDataCodec<SurfaceRules.n> c = KeyDispatchDataCodec.a(
         SurfaceRules.f.b.xmap(SurfaceRules.n::new, SurfaceRules.n::b).fieldOf("invert")
      );

      n(SurfaceRules.f var0) {
         this.a = var0;
      }

      @Override
      public KeyDispatchDataCodec<? extends SurfaceRules.f> a() {
         return c;
      }

      public SurfaceRules.e a(SurfaceRules.g var0) {
         return new SurfaceRules.m(this.a.apply(var0));
      }

      public SurfaceRules.f b() {
         return this.a;
      }
   }

   public interface o extends Function<SurfaceRules.g, SurfaceRules.u> {
      Codec<SurfaceRules.o> b = BuiltInRegistries.ae.q().dispatch(var0 -> var0.a().a(), Function.identity());

      static Codec<? extends SurfaceRules.o> a(IRegistry<Codec<? extends SurfaceRules.o>> var0) {
         SurfaceRules.a(var0, "bandlands", SurfaceRules.b.c);
         SurfaceRules.a(var0, "block", SurfaceRules.d.d);
         SurfaceRules.a(var0, "sequence", SurfaceRules.q.c);
         return SurfaceRules.a(var0, "condition", SurfaceRules.x.d);
      }

      KeyDispatchDataCodec<? extends SurfaceRules.o> a();
   }

   static record p(List<SurfaceRules.u> rules) implements SurfaceRules.u {
      private final List<SurfaceRules.u> a;

      p(List<SurfaceRules.u> var0) {
         this.a = var0;
      }

      @Nullable
      @Override
      public IBlockData tryApply(int var0, int var1, int var2) {
         for(SurfaceRules.u var4 : this.a) {
            IBlockData var5 = var4.tryApply(var0, var1, var2);
            if (var5 != null) {
               return var5;
            }
         }

         return null;
      }
   }

   static record q(List<SurfaceRules.o> sequence) implements SurfaceRules.o {
      private final List<SurfaceRules.o> a;
      static final KeyDispatchDataCodec<SurfaceRules.q> c = KeyDispatchDataCodec.a(
         SurfaceRules.o.b.listOf().xmap(SurfaceRules.q::new, SurfaceRules.q::b).fieldOf("sequence")
      );

      q(List<SurfaceRules.o> var0) {
         this.a = var0;
      }

      @Override
      public KeyDispatchDataCodec<? extends SurfaceRules.o> a() {
         return c;
      }

      public SurfaceRules.u a(SurfaceRules.g var0) {
         if (this.a.size() == 1) {
            return this.a.get(0).apply(var0);
         } else {
            Builder<SurfaceRules.u> var1 = ImmutableList.builder();

            for(SurfaceRules.o var3 : this.a) {
               var1.add(var3.apply(var0));
            }

            return new SurfaceRules.p(var1.build());
         }
      }

      public List<SurfaceRules.o> b() {
         return this.a;
      }
   }

   static record r(IBlockData state) implements SurfaceRules.u {
      private final IBlockData a;

      r(IBlockData var0) {
         this.a = var0;
      }

      @Override
      public IBlockData tryApply(int var0, int var1, int var2) {
         return this.a;
      }
   }

   static enum s implements SurfaceRules.f {
      a;

      static final KeyDispatchDataCodec<SurfaceRules.s> c = KeyDispatchDataCodec.a(MapCodec.unit(a));

      @Override
      public KeyDispatchDataCodec<? extends SurfaceRules.f> a() {
         return c;
      }

      public SurfaceRules.e a(SurfaceRules.g var0) {
         return var0.g;
      }
   }

   static record t(int offset, boolean addSurfaceDepth, int secondaryDepthRange, CaveSurface surfaceType) implements SurfaceRules.f {
      final int a;
      final boolean c;
      final int d;
      private final CaveSurface e;
      static final KeyDispatchDataCodec<SurfaceRules.t> f = KeyDispatchDataCodec.a(
         RecordCodecBuilder.mapCodec(
            var0 -> var0.group(
                     Codec.INT.fieldOf("offset").forGetter(SurfaceRules.t::b),
                     Codec.BOOL.fieldOf("add_surface_depth").forGetter(SurfaceRules.t::c),
                     Codec.INT.fieldOf("secondary_depth_range").forGetter(SurfaceRules.t::d),
                     CaveSurface.c.fieldOf("surface_type").forGetter(SurfaceRules.t::e)
                  )
                  .apply(var0, SurfaceRules.t::new)
         )
      );

      t(int var0, boolean var1, int var2, CaveSurface var3) {
         this.a = var0;
         this.c = var1;
         this.d = var2;
         this.e = var3;
      }

      @Override
      public KeyDispatchDataCodec<? extends SurfaceRules.f> a() {
         return f;
      }

      public SurfaceRules.e a(final SurfaceRules.g var0) {
         final boolean var1 = this.e == CaveSurface.a;

         class a extends SurfaceRules.k {
            a() {
               super(var0);
            }

            @Override
            protected boolean a() {
               int var0 = var1 ? this.c.D : this.c.E;
               int var1 = t.this.c ? this.c.t : 0;
               int var2 = t.this.d == 0 ? 0 : (int)MathHelper.b(this.c.a(), -1.0, 1.0, 0.0, (double)t.this.d);
               return var0 <= 1 + t.this.a + var1 + var2;
            }
         }

         return new a();
      }

      public int b() {
         return this.a;
      }
   }

   protected interface u {
      @Nullable
      IBlockData tryApply(int var1, int var2, int var3);
   }

   static enum v implements SurfaceRules.f {
      a;

      static final KeyDispatchDataCodec<SurfaceRules.v> c = KeyDispatchDataCodec.a(MapCodec.unit(a));

      @Override
      public KeyDispatchDataCodec<? extends SurfaceRules.f> a() {
         return c;
      }

      public SurfaceRules.e a(SurfaceRules.g var0) {
         return var0.f;
      }
   }

   static record w(SurfaceRules.e condition, SurfaceRules.u followup) implements SurfaceRules.u {
      private final SurfaceRules.e a;
      private final SurfaceRules.u b;

      w(SurfaceRules.e var0, SurfaceRules.u var1) {
         this.a = var0;
         this.b = var1;
      }

      @Nullable
      @Override
      public IBlockData tryApply(int var0, int var1, int var2) {
         return !this.a.b() ? null : this.b.tryApply(var0, var1, var2);
      }
   }

   static record x(SurfaceRules.f ifTrue, SurfaceRules.o thenRun) implements SurfaceRules.o {
      private final SurfaceRules.f a;
      private final SurfaceRules.o c;
      static final KeyDispatchDataCodec<SurfaceRules.x> d = KeyDispatchDataCodec.a(
         RecordCodecBuilder.mapCodec(
            var0 -> var0.group(
                     SurfaceRules.f.b.fieldOf("if_true").forGetter(SurfaceRules.x::b), SurfaceRules.o.b.fieldOf("then_run").forGetter(SurfaceRules.x::c)
                  )
                  .apply(var0, SurfaceRules.x::new)
         )
      );

      x(SurfaceRules.f var0, SurfaceRules.o var1) {
         this.a = var0;
         this.c = var1;
      }

      @Override
      public KeyDispatchDataCodec<? extends SurfaceRules.o> a() {
         return d;
      }

      public SurfaceRules.u a(SurfaceRules.g var0) {
         return new SurfaceRules.w(this.a.apply(var0), this.c.apply(var0));
      }

      public SurfaceRules.f b() {
         return this.a;
      }
   }

   static record y(MinecraftKey randomName, VerticalAnchor trueAtAndBelow, VerticalAnchor falseAtAndAbove) implements SurfaceRules.f {
      private final MinecraftKey a;
      private final VerticalAnchor c;
      private final VerticalAnchor d;
      static final KeyDispatchDataCodec<SurfaceRules.y> e = KeyDispatchDataCodec.a(
         RecordCodecBuilder.mapCodec(
            var0 -> var0.group(
                     MinecraftKey.a.fieldOf("random_name").forGetter(SurfaceRules.y::b),
                     VerticalAnchor.a.fieldOf("true_at_and_below").forGetter(SurfaceRules.y::c),
                     VerticalAnchor.a.fieldOf("false_at_and_above").forGetter(SurfaceRules.y::d)
                  )
                  .apply(var0, SurfaceRules.y::new)
         )
      );

      y(MinecraftKey var0, VerticalAnchor var1, VerticalAnchor var2) {
         this.a = var0;
         this.c = var1;
         this.d = var2;
      }

      @Override
      public KeyDispatchDataCodec<? extends SurfaceRules.f> a() {
         return e;
      }

      public SurfaceRules.e a(final SurfaceRules.g var0) {
         final int var1 = this.c().a(var0.n);
         final int var2 = this.d().a(var0.n);
         final PositionalRandomFactory var3 = var0.j.a(this.b());

         class a extends SurfaceRules.k {
            a() {
               super(var0);
            }

            @Override
            protected boolean a() {
               int var0 = this.c.B;
               if (var0 <= var1) {
                  return true;
               } else if (var0 >= var2) {
                  return false;
               } else {
                  double var1 = MathHelper.b((double)var0, (double)var1, (double)var2, 1.0, 0.0);
                  RandomSource var3 = var3.a(this.c.r, var0, this.c.s);
                  return (double)var3.i() < var1;
               }
            }
         }

         return new a();
      }

      public MinecraftKey b() {
         return this.a;
      }
   }

   static record z(int offset, int surfaceDepthMultiplier, boolean addStoneDepth) implements SurfaceRules.f {
      final int a;
      final int c;
      final boolean d;
      static final KeyDispatchDataCodec<SurfaceRules.z> e = KeyDispatchDataCodec.a(
         RecordCodecBuilder.mapCodec(
            var0 -> var0.group(
                     Codec.INT.fieldOf("offset").forGetter(SurfaceRules.z::b),
                     Codec.intRange(-20, 20).fieldOf("surface_depth_multiplier").forGetter(SurfaceRules.z::c),
                     Codec.BOOL.fieldOf("add_stone_depth").forGetter(SurfaceRules.z::d)
                  )
                  .apply(var0, SurfaceRules.z::new)
         )
      );

      z(int var0, int var1, boolean var2) {
         this.a = var0;
         this.c = var1;
         this.d = var2;
      }

      @Override
      public KeyDispatchDataCodec<? extends SurfaceRules.f> a() {
         return e;
      }

      public SurfaceRules.e a(final SurfaceRules.g var0) {
         class a extends SurfaceRules.k {
            a() {
               super(var0);
            }

            @Override
            protected boolean a() {
               return this.c.C == Integer.MIN_VALUE || this.c.B + (z.this.d ? this.c.E : 0) >= this.c.C + z.this.a + this.c.t * z.this.c;
            }
         }

         return new a();
      }

      public int b() {
         return this.a;
      }
   }
}
