package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

public class WoodlandMansionStructure extends Structure {
   public static final Codec<WoodlandMansionStructure> d = a(WoodlandMansionStructure::new);

   public WoodlandMansionStructure(Structure.c var0) {
      super(var0);
   }

   @Override
   public Optional<Structure.b> a(Structure.a var0) {
      EnumBlockRotation var1 = EnumBlockRotation.a(var0.f());
      BlockPosition var2 = this.a(var0, var1);
      return var2.v() < 60
         ? Optional.empty()
         : Optional.of(new Structure.b(var2, (Consumer<StructurePiecesBuilder>)(var3x -> this.a(var3x, var0, var2, var1))));
   }

   private void a(StructurePiecesBuilder var0, Structure.a var1, BlockPosition var2, EnumBlockRotation var3) {
      List<WoodlandMansionPieces.i> var4 = Lists.newLinkedList();
      WoodlandMansionPieces.a(var1.e(), var2, var3, var4, var1.f());
      var4.forEach(var0::a);
   }

   @Override
   public void a(
      GeneratorAccessSeed var0,
      StructureManager var1,
      ChunkGenerator var2,
      RandomSource var3,
      StructureBoundingBox var4,
      ChunkCoordIntPair var5,
      PiecesContainer var6
   ) {
      BlockPosition.MutableBlockPosition var7 = new BlockPosition.MutableBlockPosition();
      int var8 = var0.v_();
      StructureBoundingBox var9 = var6.b();
      int var10 = var9.h();

      for(int var11 = var4.g(); var11 <= var4.j(); ++var11) {
         for(int var12 = var4.i(); var12 <= var4.l(); ++var12) {
            var7.d(var11, var10, var12);
            if (!var0.w(var7) && var9.b(var7) && var6.a(var7)) {
               for(int var13 = var10 - 1; var13 > var8; --var13) {
                  var7.q(var13);
                  if (!var0.w(var7) && !var0.a_(var7).d().a()) {
                     break;
                  }

                  var0.a(var7, Blocks.m.o(), 2);
               }
            }
         }
      }
   }

   @Override
   public StructureType<?> e() {
      return StructureType.p;
   }
}
