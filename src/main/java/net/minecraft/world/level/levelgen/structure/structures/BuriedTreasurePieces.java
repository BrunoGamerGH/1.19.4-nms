package net.minecraft.world.level.levelgen.structure.structures;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;
import net.minecraft.world.level.storage.loot.LootTables;

public class BuriedTreasurePieces {
   public static class a extends StructurePiece {
      public a(BlockPosition var0) {
         super(WorldGenFeatureStructurePieceType.aa, 0, new StructureBoundingBox(var0));
      }

      public a(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.aa, var0);
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
      }

      @Override
      public void a(
         GeneratorAccessSeed var0,
         StructureManager var1,
         ChunkGenerator var2,
         RandomSource var3,
         StructureBoundingBox var4,
         ChunkCoordIntPair var5,
         BlockPosition var6
      ) {
         int var7 = var0.a(HeightMap.Type.c, this.f.g(), this.f.i());
         BlockPosition.MutableBlockPosition var8 = new BlockPosition.MutableBlockPosition(this.f.g(), var7, this.f.i());

         while(var8.v() > var0.v_()) {
            IBlockData var9 = var0.a_(var8);
            IBlockData var10 = var0.a_(var8.d());
            if (var10 == Blocks.aU.o() || var10 == Blocks.b.o() || var10 == Blocks.g.o() || var10 == Blocks.c.o() || var10 == Blocks.e.o()) {
               IBlockData var11 = !var9.h() && !this.b(var9) ? var9 : Blocks.I.o();

               for(EnumDirection var15 : EnumDirection.values()) {
                  BlockPosition var16 = var8.a(var15);
                  IBlockData var17 = var0.a_(var16);
                  if (var17.h() || this.b(var17)) {
                     BlockPosition var18 = var16.d();
                     IBlockData var19 = var0.a_(var18);
                     if ((var19.h() || this.b(var19)) && var15 != EnumDirection.b) {
                        var0.a(var16, var10, 3);
                     } else {
                        var0.a(var16, var11, 3);
                     }
                  }
               }

               this.f = new StructureBoundingBox(var8);
               this.a(var0, var4, var3, var8, LootTables.G, null);
               return;
            }

            var8.e(0, -1, 0);
         }
      }

      private boolean b(IBlockData var0) {
         return var0 == Blocks.G.o() || var0 == Blocks.H.o();
      }
   }
}
