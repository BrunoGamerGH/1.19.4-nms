package net.minecraft.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.levelgen.structure.Structure;

public interface StructureTags {
   TagKey<Structure> a = a("eye_of_ender_located");
   TagKey<Structure> b = a("dolphin_located");
   TagKey<Structure> c = a("on_woodland_explorer_maps");
   TagKey<Structure> d = a("on_ocean_explorer_maps");
   TagKey<Structure> e = a("on_treasure_maps");
   TagKey<Structure> f = a("cats_spawn_in");
   TagKey<Structure> g = a("cats_spawn_as_black");
   TagKey<Structure> h = a("village");
   TagKey<Structure> i = a("mineshaft");
   TagKey<Structure> j = a("shipwreck");
   TagKey<Structure> k = a("ruined_portal");
   TagKey<Structure> l = a("ocean_ruin");

   private static TagKey<Structure> a(String var0) {
      return TagKey.a(Registries.ax, new MinecraftKey(var0));
   }
}
