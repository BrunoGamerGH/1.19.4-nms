package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.SeededRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class IglooStructure extends Structure {
   public static final Codec<IglooStructure> d = a(IglooStructure::new);

   public IglooStructure(Structure.c var0) {
      super(var0);
   }

   @Override
   public Optional<Structure.b> a(Structure.a var0) {
      return a(var0, HeightMap.Type.a, var1x -> this.a(var1x, var0));
   }

   private void a(StructurePiecesBuilder var0, Structure.a var1) {
      ChunkCoordIntPair var2 = var1.h();
      SeededRandom var3 = var1.f();
      BlockPosition var4 = new BlockPosition(var2.d(), 90, var2.e());
      EnumBlockRotation var5 = EnumBlockRotation.a(var3);
      IglooPieces.a(var1.e(), var4, var5, var0, var3);
   }

   @Override
   public StructureType<?> e() {
      return StructureType.e;
   }
}
