package net.minecraft.world.level.levelgen.structure.structures;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.RandomSource;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.BlockStairs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.WorldGenScatteredPiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;
import net.minecraft.world.level.storage.loot.LootTables;

public class DesertPyramidPiece extends WorldGenScatteredPiece {
   public static final int h = 21;
   public static final int i = 21;
   private final boolean[] j = new boolean[4];
   private final List<BlockPosition> k = new ArrayList<>();

   public DesertPyramidPiece(RandomSource var0, int var1, int var2) {
      super(WorldGenFeatureStructurePieceType.L, var1, 64, var2, 21, 15, 21, a(var0));
   }

   public DesertPyramidPiece(NBTTagCompound var0) {
      super(WorldGenFeatureStructurePieceType.L, var0);
      this.j[0] = var0.q("hasPlacedChest0");
      this.j[1] = var0.q("hasPlacedChest1");
      this.j[2] = var0.q("hasPlacedChest2");
      this.j[3] = var0.q("hasPlacedChest3");
   }

   @Override
   protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
      super.a(var0, var1);
      var1.a("hasPlacedChest0", this.j[0]);
      var1.a("hasPlacedChest1", this.j[1]);
      var1.a("hasPlacedChest2", this.j[2]);
      var1.a("hasPlacedChest3", this.j[3]);
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
      if (this.a(var0, -var3.a(3))) {
         this.a(var0, var4, 0, -4, 0, this.a - 1, 0, this.c - 1, Blocks.aU.o(), Blocks.aU.o(), false);

         for(int var7 = 1; var7 <= 9; ++var7) {
            this.a(var0, var4, var7, var7, var7, this.a - 1 - var7, var7, this.c - 1 - var7, Blocks.aU.o(), Blocks.aU.o(), false);
            this.a(var0, var4, var7 + 1, var7, var7 + 1, this.a - 2 - var7, var7, this.c - 2 - var7, Blocks.a.o(), Blocks.a.o(), false);
         }

         for(int var7 = 0; var7 < this.a; ++var7) {
            for(int var8 = 0; var8 < this.c; ++var8) {
               int var9 = -5;
               this.b(var0, Blocks.aU.o(), var7, -5, var8, var4);
            }
         }

         IBlockData var7 = Blocks.fC.o().a(BlockStairs.a, EnumDirection.c);
         IBlockData var8 = Blocks.fC.o().a(BlockStairs.a, EnumDirection.d);
         IBlockData var9 = Blocks.fC.o().a(BlockStairs.a, EnumDirection.f);
         IBlockData var10 = Blocks.fC.o().a(BlockStairs.a, EnumDirection.e);
         this.a(var0, var4, 0, 0, 0, 4, 9, 4, Blocks.aU.o(), Blocks.a.o(), false);
         this.a(var0, var4, 1, 10, 1, 3, 10, 3, Blocks.aU.o(), Blocks.aU.o(), false);
         this.a(var0, var7, 2, 10, 0, var4);
         this.a(var0, var8, 2, 10, 4, var4);
         this.a(var0, var9, 0, 10, 2, var4);
         this.a(var0, var10, 4, 10, 2, var4);
         this.a(var0, var4, this.a - 5, 0, 0, this.a - 1, 9, 4, Blocks.aU.o(), Blocks.a.o(), false);
         this.a(var0, var4, this.a - 4, 10, 1, this.a - 2, 10, 3, Blocks.aU.o(), Blocks.aU.o(), false);
         this.a(var0, var7, this.a - 3, 10, 0, var4);
         this.a(var0, var8, this.a - 3, 10, 4, var4);
         this.a(var0, var9, this.a - 5, 10, 2, var4);
         this.a(var0, var10, this.a - 1, 10, 2, var4);
         this.a(var0, var4, 8, 0, 0, 12, 4, 4, Blocks.aU.o(), Blocks.a.o(), false);
         this.a(var0, var4, 9, 1, 0, 11, 3, 4, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, Blocks.aW.o(), 9, 1, 1, var4);
         this.a(var0, Blocks.aW.o(), 9, 2, 1, var4);
         this.a(var0, Blocks.aW.o(), 9, 3, 1, var4);
         this.a(var0, Blocks.aW.o(), 10, 3, 1, var4);
         this.a(var0, Blocks.aW.o(), 11, 3, 1, var4);
         this.a(var0, Blocks.aW.o(), 11, 2, 1, var4);
         this.a(var0, Blocks.aW.o(), 11, 1, 1, var4);
         this.a(var0, var4, 4, 1, 1, 8, 3, 3, Blocks.aU.o(), Blocks.a.o(), false);
         this.a(var0, var4, 4, 1, 2, 8, 2, 2, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, 12, 1, 1, 16, 3, 3, Blocks.aU.o(), Blocks.a.o(), false);
         this.a(var0, var4, 12, 1, 2, 16, 2, 2, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, 5, 4, 5, this.a - 6, 4, this.c - 6, Blocks.aU.o(), Blocks.aU.o(), false);
         this.a(var0, var4, 9, 4, 9, 11, 4, 11, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, 8, 1, 8, 8, 3, 8, Blocks.aW.o(), Blocks.aW.o(), false);
         this.a(var0, var4, 12, 1, 8, 12, 3, 8, Blocks.aW.o(), Blocks.aW.o(), false);
         this.a(var0, var4, 8, 1, 12, 8, 3, 12, Blocks.aW.o(), Blocks.aW.o(), false);
         this.a(var0, var4, 12, 1, 12, 12, 3, 12, Blocks.aW.o(), Blocks.aW.o(), false);
         this.a(var0, var4, 1, 1, 5, 4, 4, 11, Blocks.aU.o(), Blocks.aU.o(), false);
         this.a(var0, var4, this.a - 5, 1, 5, this.a - 2, 4, 11, Blocks.aU.o(), Blocks.aU.o(), false);
         this.a(var0, var4, 6, 7, 9, 6, 7, 11, Blocks.aU.o(), Blocks.aU.o(), false);
         this.a(var0, var4, this.a - 7, 7, 9, this.a - 7, 7, 11, Blocks.aU.o(), Blocks.aU.o(), false);
         this.a(var0, var4, 5, 5, 9, 5, 7, 11, Blocks.aW.o(), Blocks.aW.o(), false);
         this.a(var0, var4, this.a - 6, 5, 9, this.a - 6, 7, 11, Blocks.aW.o(), Blocks.aW.o(), false);
         this.a(var0, Blocks.a.o(), 5, 5, 10, var4);
         this.a(var0, Blocks.a.o(), 5, 6, 10, var4);
         this.a(var0, Blocks.a.o(), 6, 6, 10, var4);
         this.a(var0, Blocks.a.o(), this.a - 6, 5, 10, var4);
         this.a(var0, Blocks.a.o(), this.a - 6, 6, 10, var4);
         this.a(var0, Blocks.a.o(), this.a - 7, 6, 10, var4);
         this.a(var0, var4, 2, 4, 4, 2, 6, 4, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, this.a - 3, 4, 4, this.a - 3, 6, 4, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var7, 2, 4, 5, var4);
         this.a(var0, var7, 2, 3, 4, var4);
         this.a(var0, var7, this.a - 3, 4, 5, var4);
         this.a(var0, var7, this.a - 3, 3, 4, var4);
         this.a(var0, var4, 1, 1, 3, 2, 2, 3, Blocks.aU.o(), Blocks.aU.o(), false);
         this.a(var0, var4, this.a - 3, 1, 3, this.a - 2, 2, 3, Blocks.aU.o(), Blocks.aU.o(), false);
         this.a(var0, Blocks.aU.o(), 1, 1, 2, var4);
         this.a(var0, Blocks.aU.o(), this.a - 2, 1, 2, var4);
         this.a(var0, Blocks.jE.o(), 1, 2, 2, var4);
         this.a(var0, Blocks.jE.o(), this.a - 2, 2, 2, var4);
         this.a(var0, var10, 2, 1, 2, var4);
         this.a(var0, var9, this.a - 3, 1, 2, var4);
         this.a(var0, var4, 4, 3, 5, 4, 3, 17, Blocks.aU.o(), Blocks.aU.o(), false);
         this.a(var0, var4, this.a - 5, 3, 5, this.a - 5, 3, 17, Blocks.aU.o(), Blocks.aU.o(), false);
         this.a(var0, var4, 3, 1, 5, 4, 2, 16, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, this.a - 6, 1, 5, this.a - 5, 2, 16, Blocks.a.o(), Blocks.a.o(), false);

         for(int var11 = 5; var11 <= 17; var11 += 2) {
            this.a(var0, Blocks.aW.o(), 4, 1, var11, var4);
            this.a(var0, Blocks.aV.o(), 4, 2, var11, var4);
            this.a(var0, Blocks.aW.o(), this.a - 5, 1, var11, var4);
            this.a(var0, Blocks.aV.o(), this.a - 5, 2, var11, var4);
         }

         this.a(var0, Blocks.hj.o(), 10, 0, 7, var4);
         this.a(var0, Blocks.hj.o(), 10, 0, 8, var4);
         this.a(var0, Blocks.hj.o(), 9, 0, 9, var4);
         this.a(var0, Blocks.hj.o(), 11, 0, 9, var4);
         this.a(var0, Blocks.hj.o(), 8, 0, 10, var4);
         this.a(var0, Blocks.hj.o(), 12, 0, 10, var4);
         this.a(var0, Blocks.hj.o(), 7, 0, 10, var4);
         this.a(var0, Blocks.hj.o(), 13, 0, 10, var4);
         this.a(var0, Blocks.hj.o(), 9, 0, 11, var4);
         this.a(var0, Blocks.hj.o(), 11, 0, 11, var4);
         this.a(var0, Blocks.hj.o(), 10, 0, 12, var4);
         this.a(var0, Blocks.hj.o(), 10, 0, 13, var4);
         this.a(var0, Blocks.ht.o(), 10, 0, 10, var4);

         for(int var11 = 0; var11 <= this.a - 1; var11 += this.a - 1) {
            this.a(var0, Blocks.aW.o(), var11, 2, 1, var4);
            this.a(var0, Blocks.hj.o(), var11, 2, 2, var4);
            this.a(var0, Blocks.aW.o(), var11, 2, 3, var4);
            this.a(var0, Blocks.aW.o(), var11, 3, 1, var4);
            this.a(var0, Blocks.hj.o(), var11, 3, 2, var4);
            this.a(var0, Blocks.aW.o(), var11, 3, 3, var4);
            this.a(var0, Blocks.hj.o(), var11, 4, 1, var4);
            this.a(var0, Blocks.aV.o(), var11, 4, 2, var4);
            this.a(var0, Blocks.hj.o(), var11, 4, 3, var4);
            this.a(var0, Blocks.aW.o(), var11, 5, 1, var4);
            this.a(var0, Blocks.hj.o(), var11, 5, 2, var4);
            this.a(var0, Blocks.aW.o(), var11, 5, 3, var4);
            this.a(var0, Blocks.hj.o(), var11, 6, 1, var4);
            this.a(var0, Blocks.aV.o(), var11, 6, 2, var4);
            this.a(var0, Blocks.hj.o(), var11, 6, 3, var4);
            this.a(var0, Blocks.hj.o(), var11, 7, 1, var4);
            this.a(var0, Blocks.hj.o(), var11, 7, 2, var4);
            this.a(var0, Blocks.hj.o(), var11, 7, 3, var4);
            this.a(var0, Blocks.aW.o(), var11, 8, 1, var4);
            this.a(var0, Blocks.aW.o(), var11, 8, 2, var4);
            this.a(var0, Blocks.aW.o(), var11, 8, 3, var4);
         }

         for(int var11 = 2; var11 <= this.a - 3; var11 += this.a - 3 - 2) {
            this.a(var0, Blocks.aW.o(), var11 - 1, 2, 0, var4);
            this.a(var0, Blocks.hj.o(), var11, 2, 0, var4);
            this.a(var0, Blocks.aW.o(), var11 + 1, 2, 0, var4);
            this.a(var0, Blocks.aW.o(), var11 - 1, 3, 0, var4);
            this.a(var0, Blocks.hj.o(), var11, 3, 0, var4);
            this.a(var0, Blocks.aW.o(), var11 + 1, 3, 0, var4);
            this.a(var0, Blocks.hj.o(), var11 - 1, 4, 0, var4);
            this.a(var0, Blocks.aV.o(), var11, 4, 0, var4);
            this.a(var0, Blocks.hj.o(), var11 + 1, 4, 0, var4);
            this.a(var0, Blocks.aW.o(), var11 - 1, 5, 0, var4);
            this.a(var0, Blocks.hj.o(), var11, 5, 0, var4);
            this.a(var0, Blocks.aW.o(), var11 + 1, 5, 0, var4);
            this.a(var0, Blocks.hj.o(), var11 - 1, 6, 0, var4);
            this.a(var0, Blocks.aV.o(), var11, 6, 0, var4);
            this.a(var0, Blocks.hj.o(), var11 + 1, 6, 0, var4);
            this.a(var0, Blocks.hj.o(), var11 - 1, 7, 0, var4);
            this.a(var0, Blocks.hj.o(), var11, 7, 0, var4);
            this.a(var0, Blocks.hj.o(), var11 + 1, 7, 0, var4);
            this.a(var0, Blocks.aW.o(), var11 - 1, 8, 0, var4);
            this.a(var0, Blocks.aW.o(), var11, 8, 0, var4);
            this.a(var0, Blocks.aW.o(), var11 + 1, 8, 0, var4);
         }

         this.a(var0, var4, 8, 4, 0, 12, 6, 0, Blocks.aW.o(), Blocks.aW.o(), false);
         this.a(var0, Blocks.a.o(), 8, 6, 0, var4);
         this.a(var0, Blocks.a.o(), 12, 6, 0, var4);
         this.a(var0, Blocks.hj.o(), 9, 5, 0, var4);
         this.a(var0, Blocks.aV.o(), 10, 5, 0, var4);
         this.a(var0, Blocks.hj.o(), 11, 5, 0, var4);
         this.a(var0, var4, 8, -14, 8, 12, -11, 12, Blocks.aW.o(), Blocks.aW.o(), false);
         this.a(var0, var4, 8, -10, 8, 12, -10, 12, Blocks.aV.o(), Blocks.aV.o(), false);
         this.a(var0, var4, 8, -9, 8, 12, -9, 12, Blocks.aW.o(), Blocks.aW.o(), false);
         this.a(var0, var4, 8, -8, 8, 12, -1, 12, Blocks.aU.o(), Blocks.aU.o(), false);
         this.a(var0, var4, 9, -11, 9, 11, -1, 11, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, Blocks.dw.o(), 10, -11, 10, var4);
         this.a(var0, var4, 9, -13, 9, 11, -13, 11, Blocks.cj.o(), Blocks.a.o(), false);
         this.a(var0, Blocks.a.o(), 8, -11, 10, var4);
         this.a(var0, Blocks.a.o(), 8, -10, 10, var4);
         this.a(var0, Blocks.aV.o(), 7, -10, 10, var4);
         this.a(var0, Blocks.aW.o(), 7, -11, 10, var4);
         this.a(var0, Blocks.a.o(), 12, -11, 10, var4);
         this.a(var0, Blocks.a.o(), 12, -10, 10, var4);
         this.a(var0, Blocks.aV.o(), 13, -10, 10, var4);
         this.a(var0, Blocks.aW.o(), 13, -11, 10, var4);
         this.a(var0, Blocks.a.o(), 10, -11, 8, var4);
         this.a(var0, Blocks.a.o(), 10, -10, 8, var4);
         this.a(var0, Blocks.aV.o(), 10, -10, 7, var4);
         this.a(var0, Blocks.aW.o(), 10, -11, 7, var4);
         this.a(var0, Blocks.a.o(), 10, -11, 12, var4);
         this.a(var0, Blocks.a.o(), 10, -10, 12, var4);
         this.a(var0, Blocks.aV.o(), 10, -10, 13, var4);
         this.a(var0, Blocks.aW.o(), 10, -11, 13, var4);

         for(EnumDirection var12 : EnumDirection.EnumDirectionLimit.a) {
            if (!this.j[var12.e()]) {
               int var13 = var12.j() * 2;
               int var14 = var12.l() * 2;
               this.j[var12.e()] = this.a(var0, var4, var3, 10 + var13, -11, 10 + var14, LootTables.z);
            }
         }

         if (var0.G().b(FeatureFlags.c)) {
            this.a(var0, var4);
         }
      }
   }

   private void a(GeneratorAccessSeed var0, StructureBoundingBox var1) {
      BlockPosition var2 = new BlockPosition(16, -4, 13);
      this.a(var2, var0, var1);
      this.b(var2, var0, var1);
   }

   private void a(BlockPosition var0, GeneratorAccessSeed var1, StructureBoundingBox var2) {
      int var3 = var0.u();
      int var4 = var0.v();
      int var5 = var0.w();
      IBlockData var6 = Blocks.fC.o();
      this.a(var1, var6.a(EnumBlockRotation.d), 12, 0, 17, var2);
      this.a(var1, var6.a(EnumBlockRotation.d), 13, -1, 17, var2);
      this.a(var1, var6.a(EnumBlockRotation.d), 14, -2, 17, var2);
      this.a(var1, var6.a(EnumBlockRotation.d), 15, -3, 17, var2);
      IBlockData var7 = Blocks.I.o();
      IBlockData var8 = Blocks.aU.o();
      boolean var9 = var1.r_().h();
      this.a(var1, var7, var3 - 4, var4 + 4, var5 + 4, var2);
      this.a(var1, var7, var3 - 3, var4 + 4, var5 + 4, var2);
      this.a(var1, var7, var3 - 2, var4 + 4, var5 + 4, var2);
      this.a(var1, var7, var3 - 1, var4 + 4, var5 + 4, var2);
      this.a(var1, var7, var3, var4 + 4, var5 + 4, var2);
      this.a(var1, var7, var3 - 2, var4 + 3, var5 + 4, var2);
      this.a(var1, var7, var3 - 1, var4 + 3, var5 + 4, var2);
      this.a(var1, var7, var3, var4 + 3, var5 + 4, var2);
      this.a(var1, var7, var3 - 1, var4 + 2, var5 + 4, var2);
      this.a(var1, var9 ? var7 : var8, var3, var4 + 2, var5 + 4, var2);
      this.a(var1, var9 ? var7 : var8, var3, var4 + 1, var5 + 4, var2);
   }

   private void b(BlockPosition var0, GeneratorAccessSeed var1, StructureBoundingBox var2) {
      int var3 = var0.u();
      int var4 = var0.v();
      int var5 = var0.w();
      IBlockData var6 = Blocks.aW.o();
      IBlockData var7 = Blocks.aV.o();
      this.a(var1, var2, var3 - 3, var4 + 1, var5 - 3, var3 - 3, var4 + 1, var5 + 2, var6, var6, true);
      this.a(var1, var2, var3 + 3, var4 + 1, var5 - 3, var3 + 3, var4 + 1, var5 + 2, var6, var6, true);
      this.a(var1, var2, var3 - 3, var4 + 1, var5 - 3, var3 + 3, var4 + 1, var5 - 2, var6, var6, true);
      this.a(var1, var2, var3 - 3, var4 + 1, var5 + 3, var3 + 3, var4 + 1, var5 + 3, var6, var6, true);
      this.a(var1, var2, var3 - 3, var4 + 2, var5 - 3, var3 - 3, var4 + 2, var5 + 2, var7, var7, true);
      this.a(var1, var2, var3 + 3, var4 + 2, var5 - 3, var3 + 3, var4 + 2, var5 + 2, var7, var7, true);
      this.a(var1, var2, var3 - 3, var4 + 2, var5 - 3, var3 + 3, var4 + 2, var5 - 2, var7, var7, true);
      this.a(var1, var2, var3 - 3, var4 + 2, var5 + 3, var3 + 3, var4 + 2, var5 + 3, var7, var7, true);
      this.a(var1, var2, var3 - 3, -1, var5 - 3, var3 - 3, -1, var5 + 2, var6, var6, true);
      this.a(var1, var2, var3 + 3, -1, var5 - 3, var3 + 3, -1, var5 + 2, var6, var6, true);
      this.a(var1, var2, var3 - 3, -1, var5 - 3, var3 + 3, -1, var5 - 2, var6, var6, true);
      this.a(var1, var2, var3 - 3, -1, var5 + 3, var3 + 3, -1, var5 + 3, var6, var6, true);
      this.a(var2, var3 - 2, var4 + 1, var5 - 2, var3 + 2, var4 + 3, var5 + 2);
      this.a(var1, var2, var3 - 2, var4 + 4, var5 - 2, var3 + 2, var5 + 2);
      IBlockData var8 = Blocks.hj.o();
      IBlockData var9 = Blocks.ht.o();
      this.a(var1, var9, var3, var4, var5, var2);
      this.a(var1, var8, var3 + 1, var4, var5 - 1, var2);
      this.a(var1, var8, var3 + 1, var4, var5 + 1, var2);
      this.a(var1, var8, var3 - 1, var4, var5 - 1, var2);
      this.a(var1, var8, var3 - 1, var4, var5 + 1, var2);
      this.a(var1, var8, var3 + 2, var4, var5, var2);
      this.a(var1, var8, var3 - 2, var4, var5, var2);
      this.a(var1, var8, var3, var4, var5 + 2, var2);
      this.a(var1, var8, var3, var4, var5 - 2, var2);
      this.a(var1, var8, var3 + 3, var4, var5, var2);
      this.a(var3 + 3, var4 + 1, var5, var2);
      this.a(var3 + 3, var4 + 2, var5, var2);
      this.a(var1, var6, var3 + 4, var4 + 1, var5, var2);
      this.a(var1, var7, var3 + 4, var4 + 2, var5, var2);
      this.a(var1, var8, var3 - 3, var4, var5, var2);
      this.a(var3 - 3, var4 + 1, var5, var2);
      this.a(var3 - 3, var4 + 2, var5, var2);
      this.a(var1, var6, var3 - 4, var4 + 1, var5, var2);
      this.a(var1, var7, var3 - 4, var4 + 2, var5, var2);
      this.a(var1, var8, var3, var4, var5 + 3, var2);
      this.a(var3, var4 + 1, var5 + 3, var2);
      this.a(var3, var4 + 2, var5 + 3, var2);
      this.a(var1, var8, var3, var4, var5 - 3, var2);
      this.a(var3, var4 + 1, var5 - 3, var2);
      this.a(var3, var4 + 2, var5 - 3, var2);
      this.a(var1, var6, var3, var4 + 1, var5 - 4, var2);
      this.a(var1, var7, var3, -2, var5 - 4, var2);
   }

   private void a(int var0, int var1, int var2, StructureBoundingBox var3) {
      BlockPosition var4 = this.b(var0, var1, var2);
      if (var3.b(var4)) {
         this.k.add(var4);
      }
   }

   private void a(StructureBoundingBox var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      for(int var7 = var2; var7 <= var5; ++var7) {
         for(int var8 = var1; var8 <= var4; ++var8) {
            for(int var9 = var3; var9 <= var6; ++var9) {
               this.a(var8, var7, var9, var0);
            }
         }
      }
   }

   private void a(GeneratorAccessSeed var0, int var1, int var2, int var3, StructureBoundingBox var4) {
      if (var0.r_().h()) {
         IBlockData var5 = Blocks.aU.o();
         this.a(var0, var5, var1, var2, var3, var4);
      } else {
         IBlockData var5 = Blocks.I.o();
         this.a(var0, var5, var1, var2, var3, var4);
      }
   }

   private void a(GeneratorAccessSeed var0, StructureBoundingBox var1, int var2, int var3, int var4, int var5, int var6) {
      for(int var7 = var2; var7 <= var5; ++var7) {
         for(int var8 = var4; var8 <= var6; ++var8) {
            this.a(var0, var7, var3, var8, var1);
         }
      }
   }

   public List<BlockPosition> b() {
      return this.k;
   }
}
