package net.minecraft.world.level.levelgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NoiseGeneratorNormal;

public class Noises {
   public static final ResourceKey<NoiseGeneratorNormal.a> a = a("temperature");
   public static final ResourceKey<NoiseGeneratorNormal.a> b = a("vegetation");
   public static final ResourceKey<NoiseGeneratorNormal.a> c = a("continentalness");
   public static final ResourceKey<NoiseGeneratorNormal.a> d = a("erosion");
   public static final ResourceKey<NoiseGeneratorNormal.a> e = a("temperature_large");
   public static final ResourceKey<NoiseGeneratorNormal.a> f = a("vegetation_large");
   public static final ResourceKey<NoiseGeneratorNormal.a> g = a("continentalness_large");
   public static final ResourceKey<NoiseGeneratorNormal.a> h = a("erosion_large");
   public static final ResourceKey<NoiseGeneratorNormal.a> i = a("ridge");
   public static final ResourceKey<NoiseGeneratorNormal.a> j = a("offset");
   public static final ResourceKey<NoiseGeneratorNormal.a> k = a("aquifer_barrier");
   public static final ResourceKey<NoiseGeneratorNormal.a> l = a("aquifer_fluid_level_floodedness");
   public static final ResourceKey<NoiseGeneratorNormal.a> m = a("aquifer_lava");
   public static final ResourceKey<NoiseGeneratorNormal.a> n = a("aquifer_fluid_level_spread");
   public static final ResourceKey<NoiseGeneratorNormal.a> o = a("pillar");
   public static final ResourceKey<NoiseGeneratorNormal.a> p = a("pillar_rareness");
   public static final ResourceKey<NoiseGeneratorNormal.a> q = a("pillar_thickness");
   public static final ResourceKey<NoiseGeneratorNormal.a> r = a("spaghetti_2d");
   public static final ResourceKey<NoiseGeneratorNormal.a> s = a("spaghetti_2d_elevation");
   public static final ResourceKey<NoiseGeneratorNormal.a> t = a("spaghetti_2d_modulator");
   public static final ResourceKey<NoiseGeneratorNormal.a> u = a("spaghetti_2d_thickness");
   public static final ResourceKey<NoiseGeneratorNormal.a> v = a("spaghetti_3d_1");
   public static final ResourceKey<NoiseGeneratorNormal.a> w = a("spaghetti_3d_2");
   public static final ResourceKey<NoiseGeneratorNormal.a> x = a("spaghetti_3d_rarity");
   public static final ResourceKey<NoiseGeneratorNormal.a> y = a("spaghetti_3d_thickness");
   public static final ResourceKey<NoiseGeneratorNormal.a> z = a("spaghetti_roughness");
   public static final ResourceKey<NoiseGeneratorNormal.a> A = a("spaghetti_roughness_modulator");
   public static final ResourceKey<NoiseGeneratorNormal.a> B = a("cave_entrance");
   public static final ResourceKey<NoiseGeneratorNormal.a> C = a("cave_layer");
   public static final ResourceKey<NoiseGeneratorNormal.a> D = a("cave_cheese");
   public static final ResourceKey<NoiseGeneratorNormal.a> E = a("ore_veininess");
   public static final ResourceKey<NoiseGeneratorNormal.a> F = a("ore_vein_a");
   public static final ResourceKey<NoiseGeneratorNormal.a> G = a("ore_vein_b");
   public static final ResourceKey<NoiseGeneratorNormal.a> H = a("ore_gap");
   public static final ResourceKey<NoiseGeneratorNormal.a> I = a("noodle");
   public static final ResourceKey<NoiseGeneratorNormal.a> J = a("noodle_thickness");
   public static final ResourceKey<NoiseGeneratorNormal.a> K = a("noodle_ridge_a");
   public static final ResourceKey<NoiseGeneratorNormal.a> L = a("noodle_ridge_b");
   public static final ResourceKey<NoiseGeneratorNormal.a> M = a("jagged");
   public static final ResourceKey<NoiseGeneratorNormal.a> N = a("surface");
   public static final ResourceKey<NoiseGeneratorNormal.a> O = a("surface_secondary");
   public static final ResourceKey<NoiseGeneratorNormal.a> P = a("clay_bands_offset");
   public static final ResourceKey<NoiseGeneratorNormal.a> Q = a("badlands_pillar");
   public static final ResourceKey<NoiseGeneratorNormal.a> R = a("badlands_pillar_roof");
   public static final ResourceKey<NoiseGeneratorNormal.a> S = a("badlands_surface");
   public static final ResourceKey<NoiseGeneratorNormal.a> T = a("iceberg_pillar");
   public static final ResourceKey<NoiseGeneratorNormal.a> U = a("iceberg_pillar_roof");
   public static final ResourceKey<NoiseGeneratorNormal.a> V = a("iceberg_surface");
   public static final ResourceKey<NoiseGeneratorNormal.a> W = a("surface_swamp");
   public static final ResourceKey<NoiseGeneratorNormal.a> X = a("calcite");
   public static final ResourceKey<NoiseGeneratorNormal.a> Y = a("gravel");
   public static final ResourceKey<NoiseGeneratorNormal.a> Z = a("powder_snow");
   public static final ResourceKey<NoiseGeneratorNormal.a> aa = a("packed_ice");
   public static final ResourceKey<NoiseGeneratorNormal.a> ab = a("ice");
   public static final ResourceKey<NoiseGeneratorNormal.a> ac = a("soul_sand_layer");
   public static final ResourceKey<NoiseGeneratorNormal.a> ad = a("gravel_layer");
   public static final ResourceKey<NoiseGeneratorNormal.a> ae = a("patch");
   public static final ResourceKey<NoiseGeneratorNormal.a> af = a("netherrack");
   public static final ResourceKey<NoiseGeneratorNormal.a> ag = a("nether_wart");
   public static final ResourceKey<NoiseGeneratorNormal.a> ah = a("nether_state_selector");

   private static ResourceKey<NoiseGeneratorNormal.a> a(String var0) {
      return ResourceKey.a(Registries.av, new MinecraftKey(var0));
   }

   public static NoiseGeneratorNormal a(HolderGetter<NoiseGeneratorNormal.a> var0, PositionalRandomFactory var1, ResourceKey<NoiseGeneratorNormal.a> var2) {
      Holder<NoiseGeneratorNormal.a> var3 = var0.b(var2);
      return NoiseGeneratorNormal.b(var1.a(var3.e().orElseThrow().a()), var3.a());
   }
}
