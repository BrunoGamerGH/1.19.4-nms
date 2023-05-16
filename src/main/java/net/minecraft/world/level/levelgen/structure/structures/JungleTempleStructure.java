package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class JungleTempleStructure extends SinglePieceStructure {
   public static final Codec<JungleTempleStructure> d = a(JungleTempleStructure::new);

   public JungleTempleStructure(Structure.c var0) {
      super(JungleTemplePiece::new, 12, 15, var0);
   }

   @Override
   public StructureType<?> e() {
      return StructureType.g;
   }
}
