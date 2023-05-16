package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class SwampHutStructure extends Structure {
   public static final Codec<SwampHutStructure> d = a(SwampHutStructure::new);

   public SwampHutStructure(Structure.c var0) {
      super(var0);
   }

   @Override
   public Optional<Structure.b> a(Structure.a var0) {
      return a(var0, HeightMap.Type.a, var1x -> a(var1x, var0));
   }

   private static void a(StructurePiecesBuilder var0, Structure.a var1) {
      var0.a(new SwampHutPiece(var1.f(), var1.h().d(), var1.h().e()));
   }

   @Override
   public StructureType<?> e() {
      return StructureType.o;
   }
}
