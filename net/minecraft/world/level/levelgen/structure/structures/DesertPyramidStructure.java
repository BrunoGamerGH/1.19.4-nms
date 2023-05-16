package net.minecraft.world.level.levelgen.structure.structures;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Set;
import net.minecraft.SystemUtils;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.data.loot.packs.UpdateOneTwentyBuiltInLootTables;
import net.minecraft.util.ArraySetSorted;
import net.minecraft.util.RandomSource;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntityTypes;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;

public class DesertPyramidStructure extends SinglePieceStructure {
   public static final Codec<DesertPyramidStructure> d = a(DesertPyramidStructure::new);

   public DesertPyramidStructure(Structure.c var0) {
      super(DesertPyramidPiece::new, 21, 21, var0);
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
      if (var0.G().b(FeatureFlags.c)) {
         Set<BlockPosition> var7 = ArraySetSorted.a(BaseBlockPosition::i);

         for(StructurePiece var9 : var6.c()) {
            if (var9 instanceof DesertPyramidPiece var10) {
               var7.addAll(var10.b());
            }
         }

         ObjectArrayList<BlockPosition> var8 = new ObjectArrayList(var7.stream().toList());
         SystemUtils.b(var8, var3);
         int var9 = Math.min(var7.size(), var3.b(5, 8));
         ObjectListIterator var15 = var8.iterator();

         while(var15.hasNext()) {
            BlockPosition var11 = (BlockPosition)var15.next();
            if (var9 > 0) {
               --var9;
               var0.a(var11, Blocks.J.o(), 2);
               var0.a(var11, TileEntityTypes.M).ifPresent(var1x -> var1x.a(UpdateOneTwentyBuiltInLootTables.b, var11.a()));
            } else {
               var0.a(var11, Blocks.I.o(), 2);
            }
         }
      }
   }

   @Override
   public StructureType<?> e() {
      return StructureType.b;
   }
}
