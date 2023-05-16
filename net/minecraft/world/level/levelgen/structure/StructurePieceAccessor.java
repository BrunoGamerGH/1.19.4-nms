package net.minecraft.world.level.levelgen.structure;

import javax.annotation.Nullable;

public interface StructurePieceAccessor {
   void a(StructurePiece var1);

   @Nullable
   StructurePiece a(StructureBoundingBox var1);
}
