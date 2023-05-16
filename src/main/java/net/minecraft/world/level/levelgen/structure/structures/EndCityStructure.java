package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class EndCityStructure extends Structure {
   public static final Codec<EndCityStructure> d = a(EndCityStructure::new);

   public EndCityStructure(Structure.c var0) {
      super(var0);
   }

   @Override
   public Optional<Structure.b> a(Structure.a var0) {
      EnumBlockRotation var1 = EnumBlockRotation.a(var0.f());
      BlockPosition var2 = this.a(var0, var1);
      return var2.v() < 60
         ? Optional.empty()
         : Optional.of(new Structure.b(var2, (Consumer<StructurePiecesBuilder>)(var3x -> this.a(var3x, var2, var1, var0))));
   }

   private void a(StructurePiecesBuilder var0, BlockPosition var1, EnumBlockRotation var2, Structure.a var3) {
      List<StructurePiece> var4 = Lists.newArrayList();
      EndCityPieces.a(var3.e(), var1, var2, var4, var3.f());
      var4.forEach(var0::a);
   }

   @Override
   public StructureType<?> e() {
      return StructureType.c;
   }
}
