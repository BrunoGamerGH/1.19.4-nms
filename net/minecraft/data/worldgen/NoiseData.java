package net.minecraft.data.worldgen;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;

public class NoiseData {
   @Deprecated
   public static final NoiseGeneratorNormal.a a = new NoiseGeneratorNormal.a(-3, 1.0, 1.0, 1.0, 0.0);

   public static void a(BootstapContext<NoiseGeneratorNormal.a> var0) {
      a(var0, 0, Noises.a, Noises.b, Noises.c, Noises.d);
      a(var0, -2, Noises.e, Noises.f, Noises.g, Noises.h);
      a(var0, Noises.i, -7, 1.0, 2.0, 1.0, 0.0, 0.0, 0.0);
      var0.a(Noises.j, a);
      a(var0, Noises.k, -3, 1.0);
      a(var0, Noises.l, -7, 1.0);
      a(var0, Noises.m, -1, 1.0);
      a(var0, Noises.n, -5, 1.0);
      a(var0, Noises.o, -7, 1.0, 1.0);
      a(var0, Noises.p, -8, 1.0);
      a(var0, Noises.q, -8, 1.0);
      a(var0, Noises.r, -7, 1.0);
      a(var0, Noises.s, -8, 1.0);
      a(var0, Noises.t, -11, 1.0);
      a(var0, Noises.u, -11, 1.0);
      a(var0, Noises.v, -7, 1.0);
      a(var0, Noises.w, -7, 1.0);
      a(var0, Noises.x, -11, 1.0);
      a(var0, Noises.y, -8, 1.0);
      a(var0, Noises.z, -5, 1.0);
      a(var0, Noises.A, -8, 1.0);
      a(var0, Noises.B, -7, 0.4, 0.5, 1.0);
      a(var0, Noises.C, -8, 1.0);
      a(var0, Noises.D, -8, 0.5, 1.0, 2.0, 1.0, 2.0, 1.0, 0.0, 2.0, 0.0);
      a(var0, Noises.E, -8, 1.0);
      a(var0, Noises.F, -7, 1.0);
      a(var0, Noises.G, -7, 1.0);
      a(var0, Noises.H, -5, 1.0);
      a(var0, Noises.I, -8, 1.0);
      a(var0, Noises.J, -8, 1.0);
      a(var0, Noises.K, -7, 1.0);
      a(var0, Noises.L, -7, 1.0);
      a(var0, Noises.M, -16, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0);
      a(var0, Noises.N, -6, 1.0, 1.0, 1.0);
      a(var0, Noises.O, -6, 1.0, 1.0, 0.0, 1.0);
      a(var0, Noises.P, -8, 1.0);
      a(var0, Noises.Q, -2, 1.0, 1.0, 1.0, 1.0);
      a(var0, Noises.R, -8, 1.0);
      a(var0, Noises.S, -6, 1.0, 1.0, 1.0);
      a(var0, Noises.T, -6, 1.0, 1.0, 1.0, 1.0);
      a(var0, Noises.U, -3, 1.0);
      a(var0, Noises.V, -6, 1.0, 1.0, 1.0);
      a(var0, Noises.W, -2, 1.0);
      a(var0, Noises.X, -9, 1.0, 1.0, 1.0, 1.0);
      a(var0, Noises.Y, -8, 1.0, 1.0, 1.0, 1.0);
      a(var0, Noises.Z, -6, 1.0, 1.0, 1.0, 1.0);
      a(var0, Noises.aa, -7, 1.0, 1.0, 1.0, 1.0);
      a(var0, Noises.ab, -4, 1.0, 1.0, 1.0, 1.0);
      a(var0, Noises.ac, -8, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.013333333333333334);
      a(var0, Noises.ad, -8, 1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.013333333333333334);
      a(var0, Noises.ae, -5, 1.0, 0.0, 0.0, 0.0, 0.0, 0.013333333333333334);
      a(var0, Noises.af, -3, 1.0, 0.0, 0.0, 0.35);
      a(var0, Noises.ag, -3, 1.0, 0.0, 0.0, 0.9);
      a(var0, Noises.ah, -4, 1.0);
   }

   private static void a(
      BootstapContext<NoiseGeneratorNormal.a> var0,
      int var1,
      ResourceKey<NoiseGeneratorNormal.a> var2,
      ResourceKey<NoiseGeneratorNormal.a> var3,
      ResourceKey<NoiseGeneratorNormal.a> var4,
      ResourceKey<NoiseGeneratorNormal.a> var5
   ) {
      a(var0, var2, -10 + var1, 1.5, 0.0, 1.0, 0.0, 0.0, 0.0);
      a(var0, var3, -8 + var1, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0);
      a(var0, var4, -9 + var1, 1.0, 1.0, 2.0, 2.0, 2.0, 1.0, 1.0, 1.0, 1.0);
      a(var0, var5, -9 + var1, 1.0, 1.0, 0.0, 1.0, 1.0);
   }

   private static void a(BootstapContext<NoiseGeneratorNormal.a> var0, ResourceKey<NoiseGeneratorNormal.a> var1, int var2, double var3, double... var5) {
      var0.a(var1, new NoiseGeneratorNormal.a(var2, var3, var5));
   }
}
