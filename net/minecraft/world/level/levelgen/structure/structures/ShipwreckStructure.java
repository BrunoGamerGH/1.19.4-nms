package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class ShipwreckStructure extends Structure {
   public static final Codec<ShipwreckStructure> d = RecordCodecBuilder.create(
      var0 -> var0.group(a(var0), Codec.BOOL.fieldOf("is_beached").forGetter(var0x -> var0x.e)).apply(var0, ShipwreckStructure::new)
   );
   public final boolean e;

   public ShipwreckStructure(Structure.c var0, boolean var1) {
      super(var0);
      this.e = var1;
   }

   @Override
   public Optional<Structure.b> a(Structure.a var0) {
      HeightMap.Type var1 = this.e ? HeightMap.Type.a : HeightMap.Type.c;
      return a(var0, var1, var1x -> this.a(var1x, var0));
   }

   private void a(StructurePiecesBuilder var0, Structure.a var1) {
      EnumBlockRotation var2 = EnumBlockRotation.a(var1.f());
      BlockPosition var3 = new BlockPosition(var1.h().d(), 90, var1.h().e());
      ShipwreckPieces.a(var1.e(), var3, var2, var0, var1.f(), this.e);
   }

   @Override
   public StructureType<?> e() {
      return StructureType.m;
   }
}
