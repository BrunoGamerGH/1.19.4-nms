package net.minecraft.data.worldgen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.carver.WorldGenCarverWrapper;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public abstract class BiomeData {
   public static void a(BootstapContext<BiomeBase> var0) {
      HolderGetter<PlacedFeature> var1 = var0.a(Registries.aw);
      HolderGetter<WorldGenCarverWrapper<?>> var2 = var0.a(Registries.ap);
      var0.a(Biomes.a, OverworldBiomes.j(var1, var2));
      var0.a(Biomes.b, OverworldBiomes.a(var1, var2, false, false, false));
      var0.a(Biomes.c, OverworldBiomes.a(var1, var2, true, false, false));
      var0.a(Biomes.d, OverworldBiomes.a(var1, var2, false, true, false));
      var0.a(Biomes.e, OverworldBiomes.a(var1, var2, false, true, true));
      var0.a(Biomes.f, OverworldBiomes.d(var1, var2));
      var0.a(Biomes.g, OverworldBiomes.h(var1, var2));
      var0.a(Biomes.h, OverworldBiomes.i(var1, var2));
      var0.a(Biomes.i, OverworldBiomes.b(var1, var2, false, false, false));
      var0.a(Biomes.j, OverworldBiomes.b(var1, var2, false, false, true));
      var0.a(Biomes.k, OverworldBiomes.b(var1, var2, true, false, false));
      var0.a(Biomes.l, OverworldBiomes.g(var1, var2));
      var0.a(Biomes.m, OverworldBiomes.b(var1, var2, true, true, false));
      var0.a(Biomes.n, OverworldBiomes.a(var1, var2, false));
      var0.a(Biomes.o, OverworldBiomes.a(var1, var2, true));
      var0.a(Biomes.p, OverworldBiomes.h(var1, var2, false));
      var0.a(Biomes.q, OverworldBiomes.h(var1, var2, true));
      var0.a(Biomes.r, OverworldBiomes.a(var1, var2, false, false));
      var0.a(Biomes.s, OverworldBiomes.a(var1, var2, false, true));
      var0.a(Biomes.t, OverworldBiomes.b(var1, var2, false));
      var0.a(Biomes.u, OverworldBiomes.b(var1, var2, false));
      var0.a(Biomes.v, OverworldBiomes.b(var1, var2, true));
      var0.a(Biomes.w, OverworldBiomes.a(var1, var2, true, false));
      var0.a(Biomes.x, OverworldBiomes.b(var1, var2));
      var0.a(Biomes.y, OverworldBiomes.a(var1, var2));
      var0.a(Biomes.z, OverworldBiomes.c(var1, var2));
      var0.a(Biomes.A, OverworldBiomes.c(var1, var2, false));
      var0.a(Biomes.B, OverworldBiomes.c(var1, var2, false));
      var0.a(Biomes.C, OverworldBiomes.c(var1, var2, true));
      var0.a(Biomes.D, OverworldBiomes.j(var1, var2, false));
      var0.a(Biomes.F, OverworldBiomes.o(var1, var2));
      var0.a(Biomes.G, OverworldBiomes.n(var1, var2));
      var0.a(Biomes.H, OverworldBiomes.k(var1, var2));
      var0.a(Biomes.I, OverworldBiomes.l(var1, var2));
      var0.a(Biomes.J, OverworldBiomes.m(var1, var2));
      var0.a(Biomes.K, OverworldBiomes.i(var1, var2, false));
      var0.a(Biomes.L, OverworldBiomes.i(var1, var2, true));
      var0.a(Biomes.M, OverworldBiomes.b(var1, var2, false, false));
      var0.a(Biomes.N, OverworldBiomes.b(var1, var2, true, false));
      var0.a(Biomes.O, OverworldBiomes.b(var1, var2, false, true));
      var0.a(Biomes.P, OverworldBiomes.f(var1, var2));
      var0.a(Biomes.Q, OverworldBiomes.f(var1, var2, false));
      var0.a(Biomes.R, OverworldBiomes.f(var1, var2, true));
      var0.a(Biomes.S, OverworldBiomes.e(var1, var2, false));
      var0.a(Biomes.T, OverworldBiomes.e(var1, var2, true));
      var0.a(Biomes.U, OverworldBiomes.d(var1, var2, false));
      var0.a(Biomes.V, OverworldBiomes.d(var1, var2, true));
      var0.a(Biomes.W, OverworldBiomes.g(var1, var2, false));
      var0.a(Biomes.X, OverworldBiomes.g(var1, var2, true));
      var0.a(Biomes.Y, OverworldBiomes.e(var1, var2));
      var0.a(Biomes.Z, OverworldBiomes.q(var1, var2));
      var0.a(Biomes.aa, OverworldBiomes.p(var1, var2));
      var0.a(Biomes.ab, OverworldBiomes.r(var1, var2));
      var0.a(Biomes.ac, NetherBiomes.a(var1, var2));
      var0.a(Biomes.ad, NetherBiomes.e(var1, var2));
      var0.a(Biomes.ae, NetherBiomes.d(var1, var2));
      var0.a(Biomes.af, NetherBiomes.b(var1, var2));
      var0.a(Biomes.ag, NetherBiomes.c(var1, var2));
      var0.a(Biomes.ah, EndBiomes.b(var1, var2));
      var0.a(Biomes.ai, EndBiomes.d(var1, var2));
      var0.a(Biomes.aj, EndBiomes.c(var1, var2));
      var0.a(Biomes.ak, EndBiomes.e(var1, var2));
      var0.a(Biomes.al, EndBiomes.a(var1, var2));
   }

   public static void b(BootstapContext<BiomeBase> var0) {
      HolderGetter<PlacedFeature> var1 = var0.a(Registries.aw);
      HolderGetter<WorldGenCarverWrapper<?>> var2 = var0.a(Registries.ap);
      var0.a(Biomes.E, OverworldBiomes.j(var1, var2, true));
   }
}
