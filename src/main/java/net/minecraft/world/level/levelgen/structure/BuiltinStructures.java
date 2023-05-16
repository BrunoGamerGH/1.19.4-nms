package net.minecraft.world.level.levelgen.structure;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;

public interface BuiltinStructures {
   ResourceKey<Structure> a = a("pillager_outpost");
   ResourceKey<Structure> b = a("mineshaft");
   ResourceKey<Structure> c = a("mineshaft_mesa");
   ResourceKey<Structure> d = a("mansion");
   ResourceKey<Structure> e = a("jungle_pyramid");
   ResourceKey<Structure> f = a("desert_pyramid");
   ResourceKey<Structure> g = a("igloo");
   ResourceKey<Structure> h = a("shipwreck");
   ResourceKey<Structure> i = a("shipwreck_beached");
   ResourceKey<Structure> j = a("swamp_hut");
   ResourceKey<Structure> k = a("stronghold");
   ResourceKey<Structure> l = a("monument");
   ResourceKey<Structure> m = a("ocean_ruin_cold");
   ResourceKey<Structure> n = a("ocean_ruin_warm");
   ResourceKey<Structure> o = a("fortress");
   ResourceKey<Structure> p = a("nether_fossil");
   ResourceKey<Structure> q = a("end_city");
   ResourceKey<Structure> r = a("buried_treasure");
   ResourceKey<Structure> s = a("bastion_remnant");
   ResourceKey<Structure> t = a("village_plains");
   ResourceKey<Structure> u = a("village_desert");
   ResourceKey<Structure> v = a("village_savanna");
   ResourceKey<Structure> w = a("village_snowy");
   ResourceKey<Structure> x = a("village_taiga");
   ResourceKey<Structure> y = a("ruined_portal");
   ResourceKey<Structure> z = a("ruined_portal_desert");
   ResourceKey<Structure> A = a("ruined_portal_jungle");
   ResourceKey<Structure> B = a("ruined_portal_swamp");
   ResourceKey<Structure> C = a("ruined_portal_mountain");
   ResourceKey<Structure> D = a("ruined_portal_ocean");
   ResourceKey<Structure> E = a("ruined_portal_nether");
   ResourceKey<Structure> F = a("ancient_city");

   private static ResourceKey<Structure> a(String var0) {
      return ResourceKey.a(Registries.ax, new MinecraftKey(var0));
   }
}
