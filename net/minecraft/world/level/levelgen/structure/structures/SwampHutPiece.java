package net.minecraft.world.level.levelgen.structure.structures;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.animal.EntityCat;
import net.minecraft.world.entity.monster.EntityWitch;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.BlockStairs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyStairsShape;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.WorldGenScatteredPiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class SwampHutPiece extends WorldGenScatteredPiece {
   private boolean h;
   private boolean i;

   public SwampHutPiece(RandomSource randomsource, int i, int j) {
      super(WorldGenFeatureStructurePieceType.K, i, 64, j, 7, 7, 9, a(randomsource));
   }

   public SwampHutPiece(NBTTagCompound nbttagcompound) {
      super(WorldGenFeatureStructurePieceType.K, nbttagcompound);
      this.h = nbttagcompound.q("Witch");
      this.i = nbttagcompound.q("Cat");
   }

   @Override
   protected void a(StructurePieceSerializationContext structurepieceserializationcontext, NBTTagCompound nbttagcompound) {
      super.a(structurepieceserializationcontext, nbttagcompound);
      nbttagcompound.a("Witch", this.h);
      nbttagcompound.a("Cat", this.i);
   }

   @Override
   public void a(
      GeneratorAccessSeed generatoraccessseed,
      StructureManager structuremanager,
      ChunkGenerator chunkgenerator,
      RandomSource randomsource,
      StructureBoundingBox structureboundingbox,
      ChunkCoordIntPair chunkcoordintpair,
      BlockPosition blockposition
   ) {
      if (this.a(generatoraccessseed, structureboundingbox, 0)) {
         this.a(generatoraccessseed, structureboundingbox, 1, 1, 1, 5, 1, 7, Blocks.o.o(), Blocks.o.o(), false);
         this.a(generatoraccessseed, structureboundingbox, 1, 4, 2, 5, 4, 7, Blocks.o.o(), Blocks.o.o(), false);
         this.a(generatoraccessseed, structureboundingbox, 2, 1, 0, 4, 1, 0, Blocks.o.o(), Blocks.o.o(), false);
         this.a(generatoraccessseed, structureboundingbox, 2, 2, 2, 3, 3, 2, Blocks.o.o(), Blocks.o.o(), false);
         this.a(generatoraccessseed, structureboundingbox, 1, 2, 3, 1, 3, 6, Blocks.o.o(), Blocks.o.o(), false);
         this.a(generatoraccessseed, structureboundingbox, 5, 2, 3, 5, 3, 6, Blocks.o.o(), Blocks.o.o(), false);
         this.a(generatoraccessseed, structureboundingbox, 2, 2, 7, 4, 3, 7, Blocks.o.o(), Blocks.o.o(), false);
         this.a(generatoraccessseed, structureboundingbox, 1, 0, 2, 1, 3, 2, Blocks.T.o(), Blocks.T.o(), false);
         this.a(generatoraccessseed, structureboundingbox, 5, 0, 2, 5, 3, 2, Blocks.T.o(), Blocks.T.o(), false);
         this.a(generatoraccessseed, structureboundingbox, 1, 0, 7, 1, 3, 7, Blocks.T.o(), Blocks.T.o(), false);
         this.a(generatoraccessseed, structureboundingbox, 5, 0, 7, 5, 3, 7, Blocks.T.o(), Blocks.T.o(), false);
         this.a(generatoraccessseed, Blocks.dT.o(), 2, 3, 2, structureboundingbox);
         this.a(generatoraccessseed, Blocks.dT.o(), 3, 3, 7, structureboundingbox);
         this.a(generatoraccessseed, Blocks.a.o(), 1, 3, 4, structureboundingbox);
         this.a(generatoraccessseed, Blocks.a.o(), 5, 3, 4, structureboundingbox);
         this.a(generatoraccessseed, Blocks.a.o(), 5, 3, 5, structureboundingbox);
         this.a(generatoraccessseed, Blocks.go.o(), 1, 3, 5, structureboundingbox);
         this.a(generatoraccessseed, Blocks.cz.o(), 3, 2, 6, structureboundingbox);
         this.a(generatoraccessseed, Blocks.fs.o(), 4, 2, 6, structureboundingbox);
         this.a(generatoraccessseed, Blocks.dT.o(), 1, 2, 1, structureboundingbox);
         this.a(generatoraccessseed, Blocks.dT.o(), 5, 2, 1, structureboundingbox);
         IBlockData iblockdata = Blocks.fJ.o().a(BlockStairs.a, EnumDirection.c);
         IBlockData iblockdata1 = Blocks.fJ.o().a(BlockStairs.a, EnumDirection.f);
         IBlockData iblockdata2 = Blocks.fJ.o().a(BlockStairs.a, EnumDirection.e);
         IBlockData iblockdata3 = Blocks.fJ.o().a(BlockStairs.a, EnumDirection.d);
         this.a(generatoraccessseed, structureboundingbox, 0, 4, 1, 6, 4, 1, iblockdata, iblockdata, false);
         this.a(generatoraccessseed, structureboundingbox, 0, 4, 2, 0, 4, 7, iblockdata1, iblockdata1, false);
         this.a(generatoraccessseed, structureboundingbox, 6, 4, 2, 6, 4, 7, iblockdata2, iblockdata2, false);
         this.a(generatoraccessseed, structureboundingbox, 0, 4, 8, 6, 4, 8, iblockdata3, iblockdata3, false);
         this.a(generatoraccessseed, iblockdata.a(BlockStairs.c, BlockPropertyStairsShape.e), 0, 4, 1, structureboundingbox);
         this.a(generatoraccessseed, iblockdata.a(BlockStairs.c, BlockPropertyStairsShape.d), 6, 4, 1, structureboundingbox);
         this.a(generatoraccessseed, iblockdata3.a(BlockStairs.c, BlockPropertyStairsShape.d), 0, 4, 8, structureboundingbox);
         this.a(generatoraccessseed, iblockdata3.a(BlockStairs.c, BlockPropertyStairsShape.e), 6, 4, 8, structureboundingbox);

         for(int i = 2; i <= 7; i += 5) {
            for(int j = 1; j <= 5; j += 4) {
               this.b(generatoraccessseed, Blocks.T.o(), j, -1, i, structureboundingbox);
            }
         }

         if (!this.h) {
            BlockPosition.MutableBlockPosition blockposition_mutableblockposition = this.b(2, 2, 5);
            if (structureboundingbox.b(blockposition_mutableblockposition)) {
               this.h = true;
               EntityWitch entitywitch = EntityTypes.bj.a((World)generatoraccessseed.C());
               if (entitywitch != null) {
                  entitywitch.fz();
                  entitywitch.b(
                     (double)blockposition_mutableblockposition.u() + 0.5,
                     (double)blockposition_mutableblockposition.v(),
                     (double)blockposition_mutableblockposition.w() + 0.5,
                     0.0F,
                     0.0F
                  );
                  entitywitch.a(generatoraccessseed, generatoraccessseed.d_(blockposition_mutableblockposition), EnumMobSpawn.d, null, null);
                  generatoraccessseed.addFreshEntityWithPassengers(entitywitch, SpawnReason.CHUNK_GEN);
               }
            }
         }

         this.a(generatoraccessseed, structureboundingbox);
      }
   }

   private void a(WorldAccess worldaccess, StructureBoundingBox structureboundingbox) {
      if (!this.i) {
         BlockPosition.MutableBlockPosition blockposition_mutableblockposition = this.b(2, 2, 5);
         if (structureboundingbox.b(blockposition_mutableblockposition)) {
            this.i = true;
            EntityCat entitycat = EntityTypes.m.a((World)worldaccess.C());
            if (entitycat != null) {
               entitycat.fz();
               entitycat.b(
                  (double)blockposition_mutableblockposition.u() + 0.5,
                  (double)blockposition_mutableblockposition.v(),
                  (double)blockposition_mutableblockposition.w() + 0.5,
                  0.0F,
                  0.0F
               );
               entitycat.a(worldaccess, worldaccess.d_(blockposition_mutableblockposition), EnumMobSpawn.d, null, null);
               worldaccess.addFreshEntityWithPassengers(entitycat, SpawnReason.CHUNK_GEN);
            }
         }
      }
   }
}
