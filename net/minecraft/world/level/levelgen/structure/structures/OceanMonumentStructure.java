package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomSupport;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class OceanMonumentStructure extends Structure {
   public static final Codec<OceanMonumentStructure> d = a(OceanMonumentStructure::new);

   public OceanMonumentStructure(Structure.c var0) {
      super(var0);
   }

   @Override
   public Optional<Structure.b> a(Structure.a var0) {
      int var1 = var0.h().a(9);
      int var2 = var0.h().b(9);

      for(Holder<BiomeBase> var5 : var0.c().a(var1, var0.b().e(), var2, 29, var0.d().b())) {
         if (!var5.a(BiomeTags.V)) {
            return Optional.empty();
         }
      }

      return a(var0, HeightMap.Type.c, var1x -> a(var1x, var0));
   }

   private static StructurePiece a(ChunkCoordIntPair var0, SeededRandom var1) {
      int var2 = var0.d() - 29;
      int var3 = var0.e() - 29;
      EnumDirection var4 = EnumDirection.EnumDirectionLimit.a.a(var1);
      return new OceanMonumentPieces.h(var1, var2, var3, var4);
   }

   private static void a(StructurePiecesBuilder var0, Structure.a var1) {
      var0.a(a(var1.h(), var1.f()));
   }

   public static PiecesContainer a(ChunkCoordIntPair var0, long var1, PiecesContainer var3) {
      if (var3.a()) {
         return var3;
      } else {
         SeededRandom var4 = new SeededRandom(new LegacyRandomSource(RandomSupport.a()));
         var4.c(var1, var0.e, var0.f);
         StructurePiece var5 = var3.c().get(0);
         StructureBoundingBox var6 = var5.f();
         int var7 = var6.g();
         int var8 = var6.i();
         EnumDirection var9 = EnumDirection.EnumDirectionLimit.a.a(var4);
         EnumDirection var10 = Objects.requireNonNullElse(var5.i(), var9);
         StructurePiece var11 = new OceanMonumentPieces.h(var4, var7, var8, var10);
         StructurePiecesBuilder var12 = new StructurePiecesBuilder();
         var12.a(var11);
         return var12.a();
      }
   }

   @Override
   public StructureType<?> e() {
      return StructureType.j;
   }
}
