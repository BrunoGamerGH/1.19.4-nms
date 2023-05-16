package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class BuriedTreasureStructure extends Structure {
   public static final Codec<BuriedTreasureStructure> d = a(BuriedTreasureStructure::new);

   public BuriedTreasureStructure(Structure.c var0) {
      super(var0);
   }

   @Override
   public Optional<Structure.b> a(Structure.a var0) {
      return a(var0, HeightMap.Type.c, var1x -> a(var1x, var0));
   }

   private static void a(StructurePiecesBuilder var0, Structure.a var1) {
      BlockPosition var2 = new BlockPosition(var1.h().a(9), 90, var1.h().b(9));
      var0.a(new BuriedTreasurePieces.a(var2));
   }

   @Override
   public StructureType<?> e() {
      return StructureType.a;
   }
}
