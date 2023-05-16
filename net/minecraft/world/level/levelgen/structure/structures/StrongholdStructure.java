package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class StrongholdStructure extends Structure {
   public static final Codec<StrongholdStructure> d = a(StrongholdStructure::new);

   public StrongholdStructure(Structure.c var0) {
      super(var0);
   }

   @Override
   public Optional<Structure.b> a(Structure.a var0) {
      return Optional.of(new Structure.b(var0.h().l(), (Consumer<StructurePiecesBuilder>)(var1x -> a(var1x, var0))));
   }

   private static void a(StructurePiecesBuilder var0, Structure.a var1) {
      int var2 = 0;

      StrongholdPieces.m var3;
      do {
         var0.b();
         var1.f().c(var1.g() + (long)(var2++), var1.h().e, var1.h().f);
         StrongholdPieces.a();
         var3 = new StrongholdPieces.m(var1.f(), var1.h().a(2), var1.h().b(2));
         var0.a(var3);
         var3.a(var3, var0, var1.f());
         List<StructurePiece> var4 = var3.c;

         while(!var4.isEmpty()) {
            int var5 = var1.f().a(var4.size());
            StructurePiece var6 = var4.remove(var5);
            var6.a(var3, var0, var1.f());
         }

         var0.a(var1.b().e(), var1.b().f(), var1.f(), 10);
      } while(var0.c() || var3.b == null);
   }

   @Override
   public StructureType<?> e() {
      return StructureType.n;
   }
}
