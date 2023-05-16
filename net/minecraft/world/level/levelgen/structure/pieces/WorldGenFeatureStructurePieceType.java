package net.minecraft.world.level.levelgen.structure.pieces;

import java.util.Locale;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.WorldGenFeaturePillagerOutpostPoolPiece;
import net.minecraft.world.level.levelgen.structure.structures.BuriedTreasurePieces;
import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidPiece;
import net.minecraft.world.level.levelgen.structure.structures.EndCityPieces;
import net.minecraft.world.level.levelgen.structure.structures.IglooPieces;
import net.minecraft.world.level.levelgen.structure.structures.JungleTemplePiece;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftPieces;
import net.minecraft.world.level.levelgen.structure.structures.NetherFortressPieces;
import net.minecraft.world.level.levelgen.structure.structures.NetherFossilPieces;
import net.minecraft.world.level.levelgen.structure.structures.OceanMonumentPieces;
import net.minecraft.world.level.levelgen.structure.structures.OceanRuinPieces;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece;
import net.minecraft.world.level.levelgen.structure.structures.ShipwreckPieces;
import net.minecraft.world.level.levelgen.structure.structures.StrongholdPieces;
import net.minecraft.world.level.levelgen.structure.structures.SwampHutPiece;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionPieces;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public interface WorldGenFeatureStructurePieceType {
   WorldGenFeatureStructurePieceType a = a(MineshaftPieces.a::new, "MSCorridor");
   WorldGenFeatureStructurePieceType b = a(MineshaftPieces.b::new, "MSCrossing");
   WorldGenFeatureStructurePieceType c = a(MineshaftPieces.d::new, "MSRoom");
   WorldGenFeatureStructurePieceType d = a(MineshaftPieces.e::new, "MSStairs");
   WorldGenFeatureStructurePieceType e = a(NetherFortressPieces.a::new, "NeBCr");
   WorldGenFeatureStructurePieceType f = a(NetherFortressPieces.b::new, "NeBEF");
   WorldGenFeatureStructurePieceType g = a(NetherFortressPieces.c::new, "NeBS");
   WorldGenFeatureStructurePieceType h = a(NetherFortressPieces.d::new, "NeCCS");
   WorldGenFeatureStructurePieceType i = a(NetherFortressPieces.e::new, "NeCTB");
   WorldGenFeatureStructurePieceType j = a(NetherFortressPieces.f::new, "NeCE");
   WorldGenFeatureStructurePieceType k = a(NetherFortressPieces.g::new, "NeSCSC");
   WorldGenFeatureStructurePieceType l = a(NetherFortressPieces.h::new, "NeSCLT");
   WorldGenFeatureStructurePieceType m = a(NetherFortressPieces.i::new, "NeSC");
   WorldGenFeatureStructurePieceType n = a(NetherFortressPieces.j::new, "NeSCRT");
   WorldGenFeatureStructurePieceType o = a(NetherFortressPieces.k::new, "NeCSR");
   WorldGenFeatureStructurePieceType p = a(NetherFortressPieces.l::new, "NeMT");
   WorldGenFeatureStructurePieceType q = a(NetherFortressPieces.o::new, "NeRC");
   WorldGenFeatureStructurePieceType r = a(NetherFortressPieces.p::new, "NeSR");
   WorldGenFeatureStructurePieceType s = a(NetherFortressPieces.q::new, "NeStart");
   WorldGenFeatureStructurePieceType t = a(StrongholdPieces.a::new, "SHCC");
   WorldGenFeatureStructurePieceType u = a(StrongholdPieces.b::new, "SHFC");
   WorldGenFeatureStructurePieceType v = a(StrongholdPieces.c::new, "SH5C");
   WorldGenFeatureStructurePieceType w = a(StrongholdPieces.d::new, "SHLT");
   WorldGenFeatureStructurePieceType x = a(StrongholdPieces.e::new, "SHLi");
   WorldGenFeatureStructurePieceType y = a(StrongholdPieces.g::new, "SHPR");
   WorldGenFeatureStructurePieceType z = a(StrongholdPieces.h::new, "SHPH");
   WorldGenFeatureStructurePieceType A = a(StrongholdPieces.i::new, "SHRT");
   WorldGenFeatureStructurePieceType B = a(StrongholdPieces.j::new, "SHRC");
   WorldGenFeatureStructurePieceType C = a(StrongholdPieces.l::new, "SHSD");
   WorldGenFeatureStructurePieceType D = a(StrongholdPieces.m::new, "SHStart");
   WorldGenFeatureStructurePieceType E = a(StrongholdPieces.n::new, "SHS");
   WorldGenFeatureStructurePieceType F = a(StrongholdPieces.o::new, "SHSSD");
   WorldGenFeatureStructurePieceType G = a(JungleTemplePiece::new, "TeJP");
   WorldGenFeatureStructurePieceType H = a(OceanRuinPieces.a::new, "ORP");
   WorldGenFeatureStructurePieceType I = a(IglooPieces.a::new, "Iglu");
   WorldGenFeatureStructurePieceType J = a(RuinedPortalPiece::new, "RUPO");
   WorldGenFeatureStructurePieceType K = a(SwampHutPiece::new, "TeSH");
   WorldGenFeatureStructurePieceType L = a(DesertPyramidPiece::new, "TeDP");
   WorldGenFeatureStructurePieceType M = a(OceanMonumentPieces.h::new, "OMB");
   WorldGenFeatureStructurePieceType N = a(OceanMonumentPieces.j::new, "OMCR");
   WorldGenFeatureStructurePieceType O = a(OceanMonumentPieces.k::new, "OMDXR");
   WorldGenFeatureStructurePieceType P = a(OceanMonumentPieces.l::new, "OMDXYR");
   WorldGenFeatureStructurePieceType Q = a(OceanMonumentPieces.m::new, "OMDYR");
   WorldGenFeatureStructurePieceType R = a(OceanMonumentPieces.n::new, "OMDYZR");
   WorldGenFeatureStructurePieceType S = a(OceanMonumentPieces.o::new, "OMDZR");
   WorldGenFeatureStructurePieceType T = a(OceanMonumentPieces.p::new, "OMEntry");
   WorldGenFeatureStructurePieceType U = a(OceanMonumentPieces.q::new, "OMPenthouse");
   WorldGenFeatureStructurePieceType V = a(OceanMonumentPieces.s::new, "OMSimple");
   WorldGenFeatureStructurePieceType W = a(OceanMonumentPieces.t::new, "OMSimpleT");
   WorldGenFeatureStructurePieceType X = a(OceanMonumentPieces.u::new, "OMWR");
   WorldGenFeatureStructurePieceType Y = a(EndCityPieces.a::new, "ECP");
   WorldGenFeatureStructurePieceType Z = a(WoodlandMansionPieces.i::new, "WMP");
   WorldGenFeatureStructurePieceType aa = a(BuriedTreasurePieces.a::new, "BTP");
   WorldGenFeatureStructurePieceType ab = a(ShipwreckPieces.a::new, "Shipwreck");
   WorldGenFeatureStructurePieceType ac = a(NetherFossilPieces.a::new, "NeFos");
   WorldGenFeatureStructurePieceType ad = a(WorldGenFeaturePillagerOutpostPoolPiece::new, "jigsaw");

   StructurePiece load(StructurePieceSerializationContext var1, NBTTagCompound var2);

   private static WorldGenFeatureStructurePieceType a(WorldGenFeatureStructurePieceType var0, String var1) {
      return IRegistry.a(BuiltInRegistries.S, var1.toLowerCase(Locale.ROOT), var0);
   }

   private static WorldGenFeatureStructurePieceType a(WorldGenFeatureStructurePieceType.a var0, String var1) {
      return a((WorldGenFeatureStructurePieceType)var0, var1);
   }

   private static WorldGenFeatureStructurePieceType a(WorldGenFeatureStructurePieceType.b var0, String var1) {
      return a((WorldGenFeatureStructurePieceType)var0, var1);
   }

   public interface a extends WorldGenFeatureStructurePieceType {
      StructurePiece load(NBTTagCompound var1);

      @Override
      default StructurePiece load(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         return this.load(var1);
      }
   }

   public interface b extends WorldGenFeatureStructurePieceType {
      StructurePiece load(StructureTemplateManager var1, NBTTagCompound var2);

      @Override
      default StructurePiece load(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         return this.load(var0.c(), var1);
      }
   }
}
