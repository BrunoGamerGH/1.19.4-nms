package net.minecraft.world.level.levelgen.structure;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.resources.ResourceKey;

public interface BuiltinStructureSets {
   ResourceKey<StructureSet> a = a("villages");
   ResourceKey<StructureSet> b = a("desert_pyramids");
   ResourceKey<StructureSet> c = a("igloos");
   ResourceKey<StructureSet> d = a("jungle_temples");
   ResourceKey<StructureSet> e = a("swamp_huts");
   ResourceKey<StructureSet> f = a("pillager_outposts");
   ResourceKey<StructureSet> g = a("ocean_monuments");
   ResourceKey<StructureSet> h = a("woodland_mansions");
   ResourceKey<StructureSet> i = a("buried_treasures");
   ResourceKey<StructureSet> j = a("mineshafts");
   ResourceKey<StructureSet> k = a("ruined_portals");
   ResourceKey<StructureSet> l = a("shipwrecks");
   ResourceKey<StructureSet> m = a("ocean_ruins");
   ResourceKey<StructureSet> n = a("nether_complexes");
   ResourceKey<StructureSet> o = a("nether_fossils");
   ResourceKey<StructureSet> p = a("end_cities");
   ResourceKey<StructureSet> q = a("ancient_cities");
   ResourceKey<StructureSet> r = a("strongholds");

   private static ResourceKey<StructureSet> a(String var0) {
      return ResourceKey.a(Registries.az, new MinecraftKey(var0));
   }
}
