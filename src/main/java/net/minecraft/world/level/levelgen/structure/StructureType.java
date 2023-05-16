package net.minecraft.world.level.levelgen.structure;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.structures.BuriedTreasureStructure;
import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidStructure;
import net.minecraft.world.level.levelgen.structure.structures.EndCityStructure;
import net.minecraft.world.level.levelgen.structure.structures.IglooStructure;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.structures.JungleTempleStructure;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftStructure;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressStructure;
import net.minecraft.world.level.levelgen.structure.structures.NetherFossilStructure;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentStructure;
import net.minecraft.world.level.levelgen.structure.structures.OceanRuinStructure;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalStructure;
import net.minecraft.world.level.levelgen.structure.structures.ShipwreckStructure;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdStructure;
import net.minecraft.world.level.levelgen.structure.structures.SwampHutStructure;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionStructure;

public interface StructureType<S extends Structure> {
   StructureType<BuriedTreasureStructure> a = a("buried_treasure", BuriedTreasureStructure.d);
   StructureType<DesertPyramidStructure> b = a("desert_pyramid", DesertPyramidStructure.d);
   StructureType<EndCityStructure> c = a("end_city", EndCityStructure.d);
   StructureType<NetherFortressStructure> d = a("fortress", NetherFortressStructure.e);
   StructureType<IglooStructure> e = a("igloo", IglooStructure.d);
   StructureType<JigsawStructure> f = a("jigsaw", JigsawStructure.e);
   StructureType<JungleTempleStructure> g = a("jungle_temple", JungleTempleStructure.d);
   StructureType<MineshaftStructure> h = a("mineshaft", MineshaftStructure.d);
   StructureType<NetherFossilStructure> i = a("nether_fossil", NetherFossilStructure.d);
   StructureType<OceanMonumentStructure> j = a("ocean_monument", OceanMonumentStructure.d);
   StructureType<OceanRuinStructure> k = a("ocean_ruin", OceanRuinStructure.d);
   StructureType<RuinedPortalStructure> l = a("ruined_portal", RuinedPortalStructure.d);
   StructureType<ShipwreckStructure> m = a("shipwreck", ShipwreckStructure.d);
   StructureType<StrongholdStructure> n = a("stronghold", StrongholdStructure.d);
   StructureType<SwampHutStructure> o = a("swamp_hut", SwampHutStructure.d);
   StructureType<WoodlandMansionStructure> p = a("woodland_mansion", WoodlandMansionStructure.d);

   Codec<S> codec();

   private static <S extends Structure> StructureType<S> a(String var0, Codec<S> var1) {
      return IRegistry.a(BuiltInRegistries.T, var0, () -> var1);
   }
}
