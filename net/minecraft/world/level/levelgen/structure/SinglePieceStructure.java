package net.minecraft.world.level.levelgen.structure;

import java.util.Optional;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public abstract class SinglePieceStructure extends Structure {
   private final SinglePieceStructure.a d;
   private final int e;
   private final int f;

   protected SinglePieceStructure(SinglePieceStructure.a var0, int var1, int var2, Structure.c var3) {
      super(var3);
      this.d = var0;
      this.e = var1;
      this.f = var2;
   }

   @Override
   public Optional<Structure.b> a(Structure.a var0) {
      return a(var0, this.e, this.f) < var0.b().e() ? Optional.empty() : a(var0, HeightMap.Type.a, var1x -> this.a(var1x, var0));
   }

   private void a(StructurePiecesBuilder var0, Structure.a var1) {
      ChunkCoordIntPair var2 = var1.h();
      var0.a(this.d.construct(var1.f(), var2.d(), var2.e()));
   }

   @FunctionalInterface
   protected interface a {
      StructurePiece construct(SeededRandom var1, int var2, int var3);
   }
}
