package net.minecraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.biome.BiomeBase;

public class BiomeTags {
   public static final TagKey<BiomeBase> a = a("is_deep_ocean");
   public static final TagKey<BiomeBase> b = a("is_ocean");
   public static final TagKey<BiomeBase> c = a("is_beach");
   public static final TagKey<BiomeBase> d = a("is_river");
   public static final TagKey<BiomeBase> e = a("is_mountain");
   public static final TagKey<BiomeBase> f = a("is_badlands");
   public static final TagKey<BiomeBase> g = a("is_hill");
   public static final TagKey<BiomeBase> h = a("is_taiga");
   public static final TagKey<BiomeBase> i = a("is_jungle");
   public static final TagKey<BiomeBase> j = a("is_forest");
   public static final TagKey<BiomeBase> k = a("is_savanna");
   public static final TagKey<BiomeBase> l = a("is_overworld");
   public static final TagKey<BiomeBase> m = a("is_nether");
   public static final TagKey<BiomeBase> n = a("is_end");
   public static final TagKey<BiomeBase> o = a("stronghold_biased_to");
   public static final TagKey<BiomeBase> p = a("has_structure/buried_treasure");
   public static final TagKey<BiomeBase> q = a("has_structure/desert_pyramid");
   public static final TagKey<BiomeBase> r = a("has_structure/igloo");
   public static final TagKey<BiomeBase> s = a("has_structure/jungle_temple");
   public static final TagKey<BiomeBase> t = a("has_structure/mineshaft");
   public static final TagKey<BiomeBase> u = a("has_structure/mineshaft_mesa");
   public static final TagKey<BiomeBase> v = a("has_structure/ocean_monument");
   public static final TagKey<BiomeBase> w = a("has_structure/ocean_ruin_cold");
   public static final TagKey<BiomeBase> x = a("has_structure/ocean_ruin_warm");
   public static final TagKey<BiomeBase> y = a("has_structure/pillager_outpost");
   public static final TagKey<BiomeBase> z = a("has_structure/ruined_portal_desert");
   public static final TagKey<BiomeBase> A = a("has_structure/ruined_portal_jungle");
   public static final TagKey<BiomeBase> B = a("has_structure/ruined_portal_ocean");
   public static final TagKey<BiomeBase> C = a("has_structure/ruined_portal_swamp");
   public static final TagKey<BiomeBase> D = a("has_structure/ruined_portal_mountain");
   public static final TagKey<BiomeBase> E = a("has_structure/ruined_portal_standard");
   public static final TagKey<BiomeBase> F = a("has_structure/shipwreck_beached");
   public static final TagKey<BiomeBase> G = a("has_structure/shipwreck");
   public static final TagKey<BiomeBase> H = a("has_structure/stronghold");
   public static final TagKey<BiomeBase> I = a("has_structure/swamp_hut");
   public static final TagKey<BiomeBase> J = a("has_structure/village_desert");
   public static final TagKey<BiomeBase> K = a("has_structure/village_plains");
   public static final TagKey<BiomeBase> L = a("has_structure/village_savanna");
   public static final TagKey<BiomeBase> M = a("has_structure/village_snowy");
   public static final TagKey<BiomeBase> N = a("has_structure/village_taiga");
   public static final TagKey<BiomeBase> O = a("has_structure/woodland_mansion");
   public static final TagKey<BiomeBase> P = a("has_structure/nether_fortress");
   public static final TagKey<BiomeBase> Q = a("has_structure/nether_fossil");
   public static final TagKey<BiomeBase> R = a("has_structure/bastion_remnant");
   public static final TagKey<BiomeBase> S = a("has_structure/ancient_city");
   public static final TagKey<BiomeBase> T = a("has_structure/ruined_portal_nether");
   public static final TagKey<BiomeBase> U = a("has_structure/end_city");
   public static final TagKey<BiomeBase> V = a("required_ocean_monument_surrounding");
   public static final TagKey<BiomeBase> W = a("mineshaft_blocking");
   public static final TagKey<BiomeBase> X = a("plays_underwater_music");
   public static final TagKey<BiomeBase> Y = a("has_closer_water_fog");
   public static final TagKey<BiomeBase> Z = a("water_on_map_outlines");
   public static final TagKey<BiomeBase> aa = a("produces_corals_from_bonemeal");
   public static final TagKey<BiomeBase> ab = a("increased_fire_burnout");
   public static final TagKey<BiomeBase> ac = a("snow_golem_melts");
   public static final TagKey<BiomeBase> ad = a("without_zombie_sieges");
   public static final TagKey<BiomeBase> ae = a("without_patrol_spawns");
   public static final TagKey<BiomeBase> af = a("without_wandering_trader_spawns");
   public static final TagKey<BiomeBase> ag = a("spawns_cold_variant_frogs");
   public static final TagKey<BiomeBase> ah = a("spawns_warm_variant_frogs");
   public static final TagKey<BiomeBase> ai = a("spawns_gold_rabbits");
   public static final TagKey<BiomeBase> aj = a("spawns_white_rabbits");
   public static final TagKey<BiomeBase> ak = a("reduce_water_ambient_spawns");
   public static final TagKey<BiomeBase> al = a("allows_tropical_fish_spawns_at_any_height");
   public static final TagKey<BiomeBase> am = a("polar_bears_spawn_on_alternate_blocks");
   public static final TagKey<BiomeBase> an = a("more_frequent_drowned_spawns");
   public static final TagKey<BiomeBase> ao = a("allows_surface_slime_spawns");
   public static final TagKey<BiomeBase> ap = a("spawns_snow_foxes");

   private BiomeTags() {
   }

   private static TagKey<BiomeBase> a(String var0) {
      return TagKey.a(Registries.an, new MinecraftKey(var0));
   }
}
