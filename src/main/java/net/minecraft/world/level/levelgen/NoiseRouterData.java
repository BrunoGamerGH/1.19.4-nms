package net.minecraft.world.level.levelgen;

import java.util.stream.Stream;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.TerrainProvider;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.dimension.DimensionManager;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;

public class NoiseRouterData {
   public static final float a = -0.50375F;
   private static final float n = 0.08F;
   private static final double o = 1.5;
   private static final double p = 1.5;
   private static final double q = 1.5625;
   private static final double r = -0.703125;
   public static final int b = 64;
   public static final long c = 4096L;
   private static final DensityFunction s = DensityFunctions.a(10.0);
   private static final DensityFunction t = DensityFunctions.a();
   private static final ResourceKey<DensityFunction> u = a("zero");
   private static final ResourceKey<DensityFunction> v = a("y");
   private static final ResourceKey<DensityFunction> w = a("shift_x");
   private static final ResourceKey<DensityFunction> x = a("shift_z");
   private static final ResourceKey<DensityFunction> y = a("overworld/base_3d_noise");
   private static final ResourceKey<DensityFunction> z = a("nether/base_3d_noise");
   private static final ResourceKey<DensityFunction> A = a("end/base_3d_noise");
   public static final ResourceKey<DensityFunction> d = a("overworld/continents");
   public static final ResourceKey<DensityFunction> e = a("overworld/erosion");
   public static final ResourceKey<DensityFunction> f = a("overworld/ridges");
   public static final ResourceKey<DensityFunction> g = a("overworld/ridges_folded");
   public static final ResourceKey<DensityFunction> h = a("overworld/offset");
   public static final ResourceKey<DensityFunction> i = a("overworld/factor");
   public static final ResourceKey<DensityFunction> j = a("overworld/jaggedness");
   public static final ResourceKey<DensityFunction> k = a("overworld/depth");
   private static final ResourceKey<DensityFunction> B = a("overworld/sloped_cheese");
   public static final ResourceKey<DensityFunction> l = a("overworld_large_biomes/continents");
   public static final ResourceKey<DensityFunction> m = a("overworld_large_biomes/erosion");
   private static final ResourceKey<DensityFunction> C = a("overworld_large_biomes/offset");
   private static final ResourceKey<DensityFunction> D = a("overworld_large_biomes/factor");
   private static final ResourceKey<DensityFunction> E = a("overworld_large_biomes/jaggedness");
   private static final ResourceKey<DensityFunction> F = a("overworld_large_biomes/depth");
   private static final ResourceKey<DensityFunction> G = a("overworld_large_biomes/sloped_cheese");
   private static final ResourceKey<DensityFunction> H = a("overworld_amplified/offset");
   private static final ResourceKey<DensityFunction> I = a("overworld_amplified/factor");
   private static final ResourceKey<DensityFunction> J = a("overworld_amplified/jaggedness");
   private static final ResourceKey<DensityFunction> K = a("overworld_amplified/depth");
   private static final ResourceKey<DensityFunction> L = a("overworld_amplified/sloped_cheese");
   private static final ResourceKey<DensityFunction> M = a("end/sloped_cheese");
   private static final ResourceKey<DensityFunction> N = a("overworld/caves/spaghetti_roughness_function");
   private static final ResourceKey<DensityFunction> O = a("overworld/caves/entrances");
   private static final ResourceKey<DensityFunction> P = a("overworld/caves/noodle");
   private static final ResourceKey<DensityFunction> Q = a("overworld/caves/pillars");
   private static final ResourceKey<DensityFunction> R = a("overworld/caves/spaghetti_2d_thickness_modulator");
   private static final ResourceKey<DensityFunction> S = a("overworld/caves/spaghetti_2d");

   private static ResourceKey<DensityFunction> a(String var0) {
      return ResourceKey.a(Registries.ar, new MinecraftKey(var0));
   }

   public static Holder<? extends DensityFunction> a(BootstapContext<DensityFunction> var0) {
      HolderGetter<NoiseGeneratorNormal.a> var1 = var0.a(Registries.av);
      HolderGetter<DensityFunction> var2 = var0.a(Registries.ar);
      var0.a(u, DensityFunctions.a());
      int var3 = DimensionManager.e * 2;
      int var4 = DimensionManager.d * 2;
      var0.a(v, DensityFunctions.a(var3, var4, (double)var3, (double)var4));
      DensityFunction var5 = a(var0, w, DensityFunctions.b(DensityFunctions.c(DensityFunctions.b(var1.b(Noises.j)))));
      DensityFunction var6 = a(var0, x, DensityFunctions.b(DensityFunctions.c(DensityFunctions.c(var1.b(Noises.j)))));
      var0.a(y, BlendedNoise.a(0.25, 0.125, 80.0, 160.0, 8.0));
      var0.a(z, BlendedNoise.a(0.25, 0.375, 80.0, 60.0, 8.0));
      var0.a(A, BlendedNoise.a(0.25, 0.25, 80.0, 160.0, 4.0));
      Holder<DensityFunction> var7 = var0.a(d, DensityFunctions.b(DensityFunctions.a(var5, var6, 0.25, var1.b(Noises.c))));
      Holder<DensityFunction> var8 = var0.a(e, DensityFunctions.b(DensityFunctions.a(var5, var6, 0.25, var1.b(Noises.d))));
      DensityFunction var9 = a(var0, f, DensityFunctions.b(DensityFunctions.a(var5, var6, 0.25, var1.b(Noises.i))));
      var0.a(g, a(var9));
      DensityFunction var10 = DensityFunctions.b(var1.b(Noises.M), 1500.0, 0.0);
      a(var0, var2, var10, var7, var8, h, i, j, k, B, false);
      Holder<DensityFunction> var11 = var0.a(l, DensityFunctions.b(DensityFunctions.a(var5, var6, 0.25, var1.b(Noises.g))));
      Holder<DensityFunction> var12 = var0.a(m, DensityFunctions.b(DensityFunctions.a(var5, var6, 0.25, var1.b(Noises.h))));
      a(var0, var2, var10, var11, var12, C, D, E, F, G, false);
      a(var0, var2, var10, var7, var8, H, I, J, K, L, true);
      var0.a(M, DensityFunctions.a(DensityFunctions.a(0L), a(var2, A)));
      var0.a(N, b(var1));
      var0.a(R, DensityFunctions.d(DensityFunctions.a(var1.b(Noises.u), 2.0, 1.0, -0.6, -1.3)));
      var0.a(S, f(var2, var1));
      var0.a(O, d(var2, var1));
      var0.a(P, e(var2, var1));
      return var0.a(Q, c(var1));
   }

   private static void a(
      BootstapContext<DensityFunction> var0,
      HolderGetter<DensityFunction> var1,
      DensityFunction var2,
      Holder<DensityFunction> var3,
      Holder<DensityFunction> var4,
      ResourceKey<DensityFunction> var5,
      ResourceKey<DensityFunction> var6,
      ResourceKey<DensityFunction> var7,
      ResourceKey<DensityFunction> var8,
      ResourceKey<DensityFunction> var9,
      boolean var10
   ) {
      DensityFunctions.w.a var11 = new DensityFunctions.w.a(var3);
      DensityFunctions.w.a var12 = new DensityFunctions.w.a(var4);
      DensityFunctions.w.a var13 = new DensityFunctions.w.a(var1.b(f));
      DensityFunctions.w.a var14 = new DensityFunctions.w.a(var1.b(g));
      DensityFunction var15 = a(
         var0,
         var5,
         a(DensityFunctions.a(DensityFunctions.a(-0.50375F), DensityFunctions.a(TerrainProvider.a(var11, var12, var14, var10))), DensityFunctions.c())
      );
      DensityFunction var16 = a(var0, var6, a(DensityFunctions.a(TerrainProvider.a(var11, var12, var13, var14, var10)), s));
      DensityFunction var17 = a(var0, var8, DensityFunctions.a(DensityFunctions.a(-64, 320, 1.5, -1.5), var15));
      DensityFunction var18 = a(var0, var7, a(DensityFunctions.a(TerrainProvider.b(var11, var12, var13, var14, var10)), t));
      DensityFunction var19 = DensityFunctions.b(var18, var2.g());
      DensityFunction var20 = b(var16, DensityFunctions.a(var17, var19));
      var0.a(var9, DensityFunctions.a(var20, a(var1, y)));
   }

   private static DensityFunction a(BootstapContext<DensityFunction> var0, ResourceKey<DensityFunction> var1, DensityFunction var2) {
      return new DensityFunctions.j(var0.a(var1, var2));
   }

   private static DensityFunction a(HolderGetter<DensityFunction> var0, ResourceKey<DensityFunction> var1) {
      return new DensityFunctions.j(var0.b(var1));
   }

   private static DensityFunction a(DensityFunction var0) {
      return DensityFunctions.b(
         DensityFunctions.a(DensityFunctions.a(var0.d(), DensityFunctions.a(-0.6666666666666666)).d(), DensityFunctions.a(-0.3333333333333333)),
         DensityFunctions.a(-3.0)
      );
   }

   public static float a(float var0) {
      return -(Math.abs(Math.abs(var0) - 0.6666667F) - 0.33333334F) * 3.0F;
   }

   private static DensityFunction b(HolderGetter<NoiseGeneratorNormal.a> var0) {
      DensityFunction var1 = DensityFunctions.a(var0.b(Noises.z));
      DensityFunction var2 = DensityFunctions.a(var0.b(Noises.A), 0.0, -0.1);
      return DensityFunctions.d(DensityFunctions.b(var2, DensityFunctions.a(var1.d(), DensityFunctions.a(-0.4))));
   }

   private static DensityFunction d(HolderGetter<DensityFunction> var0, HolderGetter<NoiseGeneratorNormal.a> var1) {
      DensityFunction var2 = DensityFunctions.d(DensityFunctions.b(var1.b(Noises.x), 2.0, 1.0));
      DensityFunction var3 = DensityFunctions.a(var1.b(Noises.y), -0.065, -0.088);
      DensityFunction var4 = DensityFunctions.a(var2, var1.b(Noises.v), DensityFunctions.z.a.a);
      DensityFunction var5 = DensityFunctions.a(var2, var1.b(Noises.w), DensityFunctions.z.a.a);
      DensityFunction var6 = DensityFunctions.a(DensityFunctions.d(var4, var5), var3).a(-1.0, 1.0);
      DensityFunction var7 = a(var0, N);
      DensityFunction var8 = DensityFunctions.b(var1.b(Noises.B), 0.75, 0.5);
      DensityFunction var9 = DensityFunctions.a(DensityFunctions.a(var8, DensityFunctions.a(0.37)), DensityFunctions.a(-10, 30, 0.3, 0.0));
      return DensityFunctions.d(DensityFunctions.c(var9, DensityFunctions.a(var7, var6)));
   }

   private static DensityFunction e(HolderGetter<DensityFunction> var0, HolderGetter<NoiseGeneratorNormal.a> var1) {
      DensityFunction var2 = a(var0, v);
      int var3 = -64;
      int var4 = -60;
      int var5 = 320;
      DensityFunction var6 = a(var2, DensityFunctions.b(var1.b(Noises.I), 1.0, 1.0), -60, 320, -1);
      DensityFunction var7 = a(var2, DensityFunctions.a(var1.b(Noises.J), 1.0, 1.0, -0.05, -0.1), -60, 320, 0);
      double var8 = 2.6666666666666665;
      DensityFunction var10 = a(var2, DensityFunctions.b(var1.b(Noises.K), 2.6666666666666665, 2.6666666666666665), -60, 320, 0);
      DensityFunction var11 = a(var2, DensityFunctions.b(var1.b(Noises.L), 2.6666666666666665, 2.6666666666666665), -60, 320, 0);
      DensityFunction var12 = DensityFunctions.b(DensityFunctions.a(1.5), DensityFunctions.d(var10.d(), var11.d()));
      return DensityFunctions.a(var6, -1000000.0, 0.0, DensityFunctions.a(64.0), DensityFunctions.a(var7, var12));
   }

   private static DensityFunction c(HolderGetter<NoiseGeneratorNormal.a> var0) {
      double var1 = 25.0;
      double var3 = 0.3;
      DensityFunction var5 = DensityFunctions.b(var0.b(Noises.o), 25.0, 0.3);
      DensityFunction var6 = DensityFunctions.a(var0.b(Noises.p), 0.0, -2.0);
      DensityFunction var7 = DensityFunctions.a(var0.b(Noises.q), 0.0, 1.1);
      DensityFunction var8 = DensityFunctions.a(DensityFunctions.b(var5, DensityFunctions.a(2.0)), var6);
      return DensityFunctions.d(DensityFunctions.b(var8, var7.f()));
   }

   private static DensityFunction f(HolderGetter<DensityFunction> var0, HolderGetter<NoiseGeneratorNormal.a> var1) {
      DensityFunction var2 = DensityFunctions.b(var1.b(Noises.t), 2.0, 1.0);
      DensityFunction var3 = DensityFunctions.a(var2, var1.b(Noises.r), DensityFunctions.z.a.b);
      DensityFunction var4 = DensityFunctions.a(var1.b(Noises.s), 0.0, (double)Math.floorDiv(-64, 8), 8.0);
      DensityFunction var5 = a(var0, R);
      DensityFunction var6 = DensityFunctions.a(var4, DensityFunctions.a(-64, 320, 8.0, -40.0)).d();
      DensityFunction var7 = DensityFunctions.a(var6, var5).f();
      double var8 = 0.083;
      DensityFunction var10 = DensityFunctions.a(var3, DensityFunctions.b(DensityFunctions.a(0.083), var5));
      return DensityFunctions.d(var10, var7).a(-1.0, 1.0);
   }

   private static DensityFunction a(HolderGetter<DensityFunction> var0, HolderGetter<NoiseGeneratorNormal.a> var1, DensityFunction var2) {
      DensityFunction var3 = a(var0, S);
      DensityFunction var4 = a(var0, N);
      DensityFunction var5 = DensityFunctions.a(var1.b(Noises.C), 8.0);
      DensityFunction var6 = DensityFunctions.b(DensityFunctions.a(4.0), var5.e());
      DensityFunction var7 = DensityFunctions.a(var1.b(Noises.D), 0.6666666666666666);
      DensityFunction var8 = DensityFunctions.a(
         DensityFunctions.a(DensityFunctions.a(0.27), var7).a(-1.0, 1.0),
         DensityFunctions.a(DensityFunctions.a(1.5), DensityFunctions.b(DensityFunctions.a(-0.64), var2)).a(0.0, 0.5)
      );
      DensityFunction var9 = DensityFunctions.a(var6, var8);
      DensityFunction var10 = DensityFunctions.c(DensityFunctions.c(var9, a(var0, O)), DensityFunctions.a(var3, var4));
      DensityFunction var11 = a(var0, Q);
      DensityFunction var12 = DensityFunctions.a(var11, -1000000.0, 0.03, DensityFunctions.a(-1000000.0), var11);
      return DensityFunctions.d(var10, var12);
   }

   private static DensityFunction b(DensityFunction var0) {
      DensityFunction var1 = DensityFunctions.f(var0);
      return DensityFunctions.b(DensityFunctions.a(var1), DensityFunctions.a(0.64)).i();
   }

   protected static NoiseRouter a(HolderGetter<DensityFunction> var0, HolderGetter<NoiseGeneratorNormal.a> var1, boolean var2, boolean var3) {
      DensityFunction var4 = DensityFunctions.a(var1.b(Noises.k), 0.5);
      DensityFunction var5 = DensityFunctions.a(var1.b(Noises.l), 0.67);
      DensityFunction var6 = DensityFunctions.a(var1.b(Noises.n), 0.7142857142857143);
      DensityFunction var7 = DensityFunctions.a(var1.b(Noises.m));
      DensityFunction var8 = a(var0, w);
      DensityFunction var9 = a(var0, x);
      DensityFunction var10 = DensityFunctions.a(var8, var9, 0.25, var1.b(var2 ? Noises.e : Noises.a));
      DensityFunction var11 = DensityFunctions.a(var8, var9, 0.25, var1.b(var2 ? Noises.f : Noises.b));
      DensityFunction var12 = a(var0, var2 ? D : (var3 ? I : i));
      DensityFunction var13 = a(var0, var2 ? F : (var3 ? K : k));
      DensityFunction var14 = b(DensityFunctions.c(var12), var13);
      DensityFunction var15 = a(var0, var2 ? G : (var3 ? L : B));
      DensityFunction var16 = DensityFunctions.c(var15, DensityFunctions.b(DensityFunctions.a(5.0), a(var0, O)));
      DensityFunction var17 = DensityFunctions.a(var15, -1000000.0, 1.5625, var16, a(var0, var1, var15));
      DensityFunction var18 = DensityFunctions.c(b(a(var3, var17)), a(var0, P));
      DensityFunction var19 = a(var0, v);
      int var20 = Stream.of(OreVeinifier.a.values()).mapToInt(var0x -> var0x.c).min().orElse(-DimensionManager.e * 2);
      int var21 = Stream.of(OreVeinifier.a.values()).mapToInt(var0x -> var0x.d).max().orElse(-DimensionManager.e * 2);
      DensityFunction var22 = a(var19, DensityFunctions.b(var1.b(Noises.E), 1.5, 1.5), var20, var21, 0);
      float var23 = 4.0F;
      DensityFunction var24 = a(var19, DensityFunctions.b(var1.b(Noises.F), 4.0, 4.0), var20, var21, 0).d();
      DensityFunction var25 = a(var19, DensityFunctions.b(var1.b(Noises.G), 4.0, 4.0), var20, var21, 0).d();
      DensityFunction var26 = DensityFunctions.a(DensityFunctions.a(-0.08F), DensityFunctions.d(var24, var25));
      DensityFunction var27 = DensityFunctions.a(var1.b(Noises.H));
      return new NoiseRouter(
         var4,
         var5,
         var6,
         var7,
         var10,
         var11,
         a(var0, var2 ? l : d),
         a(var0, var2 ? m : e),
         var13,
         a(var0, f),
         a(var3, DensityFunctions.a(var14, DensityFunctions.a(-0.703125)).a(-64.0, 64.0)),
         var18,
         var22,
         var26,
         var27
      );
   }

   private static NoiseRouter b(HolderGetter<DensityFunction> var0, HolderGetter<NoiseGeneratorNormal.a> var1, DensityFunction var2) {
      DensityFunction var3 = a(var0, w);
      DensityFunction var4 = a(var0, x);
      DensityFunction var5 = DensityFunctions.a(var3, var4, 0.25, var1.b(Noises.a));
      DensityFunction var6 = DensityFunctions.a(var3, var4, 0.25, var1.b(Noises.b));
      DensityFunction var7 = b(var2);
      return new NoiseRouter(
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         var5,
         var6,
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         var7,
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a()
      );
   }

   private static DensityFunction a(boolean var0, DensityFunction var1) {
      return a(var1, -64, 384, var0 ? 16 : 80, var0 ? 0 : 64, -0.078125, 0, 24, var0 ? 0.4 : 0.1171875);
   }

   private static DensityFunction a(HolderGetter<DensityFunction> var0, int var1, int var2) {
      return a(a(var0, z), var1, var2, 24, 0, 0.9375, -8, 24, 2.5);
   }

   private static DensityFunction a(DensityFunction var0, int var1, int var2) {
      return a(var0, var1, var2, 72, -184, -23.4375, 4, 32, -0.234375);
   }

   protected static NoiseRouter a(HolderGetter<DensityFunction> var0, HolderGetter<NoiseGeneratorNormal.a> var1) {
      return b(var0, var1, a(var0, 0, 128));
   }

   protected static NoiseRouter b(HolderGetter<DensityFunction> var0, HolderGetter<NoiseGeneratorNormal.a> var1) {
      return b(var0, var1, a(var0, -64, 192));
   }

   protected static NoiseRouter c(HolderGetter<DensityFunction> var0, HolderGetter<NoiseGeneratorNormal.a> var1) {
      return b(var0, var1, a(a(var0, A), 0, 256));
   }

   private static DensityFunction c(DensityFunction var0) {
      return a(var0, 0, 128);
   }

   protected static NoiseRouter a(HolderGetter<DensityFunction> var0) {
      DensityFunction var1 = DensityFunctions.c(DensityFunctions.a(0L));
      DensityFunction var2 = b(c(a(var0, M)));
      return new NoiseRouter(
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         var1,
         DensityFunctions.a(),
         DensityFunctions.a(),
         c(DensityFunctions.a(var1, DensityFunctions.a(-0.703125))),
         var2,
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a()
      );
   }

   protected static NoiseRouter a() {
      return new NoiseRouter(
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a(),
         DensityFunctions.a()
      );
   }

   private static DensityFunction a(DensityFunction var0, DensityFunction var1) {
      DensityFunction var2 = DensityFunctions.a(DensityFunctions.b(), var1, var0);
      return DensityFunctions.b(DensityFunctions.c(var2));
   }

   private static DensityFunction b(DensityFunction var0, DensityFunction var1) {
      DensityFunction var2 = DensityFunctions.b(var1, var0);
      return DensityFunctions.b(DensityFunctions.a(4.0), var2.h());
   }

   private static DensityFunction a(DensityFunction var0, DensityFunction var1, int var2, int var3, int var4) {
      return DensityFunctions.a(DensityFunctions.a(var0, (double)var2, (double)(var3 + 1), var1, DensityFunctions.a((double)var4)));
   }

   private static DensityFunction a(DensityFunction var0, int var1, int var2, int var3, int var4, double var5, int var7, int var8, double var9) {
      DensityFunction var12 = DensityFunctions.a(var1 + var2 - var3, var1 + var2 - var4, 1.0, 0.0);
      DensityFunction var11 = DensityFunctions.a(var12, var5, var0);
      DensityFunction var13 = DensityFunctions.a(var1 + var7, var1 + var8, 0.0, 1.0);
      return DensityFunctions.a(var13, var9, var11);
   }

   protected static final class a {
      protected static double a(double var0) {
         if (var0 < -0.75) {
            return 0.5;
         } else if (var0 < -0.5) {
            return 0.75;
         } else if (var0 < 0.5) {
            return 1.0;
         } else {
            return var0 < 0.75 ? 2.0 : 3.0;
         }
      }

      protected static double b(double var0) {
         if (var0 < -0.5) {
            return 0.75;
         } else if (var0 < 0.0) {
            return 1.0;
         } else {
            return var0 < 0.5 ? 1.5 : 2.0;
         }
      }
   }
}
