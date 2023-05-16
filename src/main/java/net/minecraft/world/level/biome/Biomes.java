package net.minecraft.world.level.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;

public abstract class Biomes {
   public static final ResourceKey<BiomeBase> a = a("the_void");
   public static final ResourceKey<BiomeBase> b = a("plains");
   public static final ResourceKey<BiomeBase> c = a("sunflower_plains");
   public static final ResourceKey<BiomeBase> d = a("snowy_plains");
   public static final ResourceKey<BiomeBase> e = a("ice_spikes");
   public static final ResourceKey<BiomeBase> f = a("desert");
   public static final ResourceKey<BiomeBase> g = a("swamp");
   public static final ResourceKey<BiomeBase> h = a("mangrove_swamp");
   public static final ResourceKey<BiomeBase> i = a("forest");
   public static final ResourceKey<BiomeBase> j = a("flower_forest");
   public static final ResourceKey<BiomeBase> k = a("birch_forest");
   public static final ResourceKey<BiomeBase> l = a("dark_forest");
   public static final ResourceKey<BiomeBase> m = a("old_growth_birch_forest");
   public static final ResourceKey<BiomeBase> n = a("old_growth_pine_taiga");
   public static final ResourceKey<BiomeBase> o = a("old_growth_spruce_taiga");
   public static final ResourceKey<BiomeBase> p = a("taiga");
   public static final ResourceKey<BiomeBase> q = a("snowy_taiga");
   public static final ResourceKey<BiomeBase> r = a("savanna");
   public static final ResourceKey<BiomeBase> s = a("savanna_plateau");
   public static final ResourceKey<BiomeBase> t = a("windswept_hills");
   public static final ResourceKey<BiomeBase> u = a("windswept_gravelly_hills");
   public static final ResourceKey<BiomeBase> v = a("windswept_forest");
   public static final ResourceKey<BiomeBase> w = a("windswept_savanna");
   public static final ResourceKey<BiomeBase> x = a("jungle");
   public static final ResourceKey<BiomeBase> y = a("sparse_jungle");
   public static final ResourceKey<BiomeBase> z = a("bamboo_jungle");
   public static final ResourceKey<BiomeBase> A = a("badlands");
   public static final ResourceKey<BiomeBase> B = a("eroded_badlands");
   public static final ResourceKey<BiomeBase> C = a("wooded_badlands");
   public static final ResourceKey<BiomeBase> D = a("meadow");
   public static final ResourceKey<BiomeBase> E = a("cherry_grove");
   public static final ResourceKey<BiomeBase> F = a("grove");
   public static final ResourceKey<BiomeBase> G = a("snowy_slopes");
   public static final ResourceKey<BiomeBase> H = a("frozen_peaks");
   public static final ResourceKey<BiomeBase> I = a("jagged_peaks");
   public static final ResourceKey<BiomeBase> J = a("stony_peaks");
   public static final ResourceKey<BiomeBase> K = a("river");
   public static final ResourceKey<BiomeBase> L = a("frozen_river");
   public static final ResourceKey<BiomeBase> M = a("beach");
   public static final ResourceKey<BiomeBase> N = a("snowy_beach");
   public static final ResourceKey<BiomeBase> O = a("stony_shore");
   public static final ResourceKey<BiomeBase> P = a("warm_ocean");
   public static final ResourceKey<BiomeBase> Q = a("lukewarm_ocean");
   public static final ResourceKey<BiomeBase> R = a("deep_lukewarm_ocean");
   public static final ResourceKey<BiomeBase> S = a("ocean");
   public static final ResourceKey<BiomeBase> T = a("deep_ocean");
   public static final ResourceKey<BiomeBase> U = a("cold_ocean");
   public static final ResourceKey<BiomeBase> V = a("deep_cold_ocean");
   public static final ResourceKey<BiomeBase> W = a("frozen_ocean");
   public static final ResourceKey<BiomeBase> X = a("deep_frozen_ocean");
   public static final ResourceKey<BiomeBase> Y = a("mushroom_fields");
   public static final ResourceKey<BiomeBase> Z = a("dripstone_caves");
   public static final ResourceKey<BiomeBase> aa = a("lush_caves");
   public static final ResourceKey<BiomeBase> ab = a("deep_dark");
   public static final ResourceKey<BiomeBase> ac = a("nether_wastes");
   public static final ResourceKey<BiomeBase> ad = a("warped_forest");
   public static final ResourceKey<BiomeBase> ae = a("crimson_forest");
   public static final ResourceKey<BiomeBase> af = a("soul_sand_valley");
   public static final ResourceKey<BiomeBase> ag = a("basalt_deltas");
   public static final ResourceKey<BiomeBase> ah = a("the_end");
   public static final ResourceKey<BiomeBase> ai = a("end_highlands");
   public static final ResourceKey<BiomeBase> aj = a("end_midlands");
   public static final ResourceKey<BiomeBase> ak = a("small_end_islands");
   public static final ResourceKey<BiomeBase> al = a("end_barrens");

   private static ResourceKey<BiomeBase> a(String var0) {
      return ResourceKey.a(Registries.an, new MinecraftKey(var0));
   }
}
