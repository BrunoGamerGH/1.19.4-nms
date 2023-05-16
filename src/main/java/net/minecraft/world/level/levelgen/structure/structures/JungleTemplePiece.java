package net.minecraft.world.level.levelgen.structure.structures;

import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.BlockLever;
import net.minecraft.world.level.block.BlockRedstoneWire;
import net.minecraft.world.level.block.BlockRepeater;
import net.minecraft.world.level.block.BlockStairs;
import net.minecraft.world.level.block.BlockTripwire;
import net.minecraft.world.level.block.BlockTripwireHook;
import net.minecraft.world.level.block.BlockVine;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.BlockPiston;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyAttachPosition;
import net.minecraft.world.level.block.state.properties.BlockPropertyRedstoneSide;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.WorldGenScatteredPiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;
import net.minecraft.world.level.storage.loot.LootTables;

public class JungleTemplePiece extends WorldGenScatteredPiece {
   public static final int h = 12;
   public static final int i = 15;
   private boolean j;
   private boolean k;
   private boolean l;
   private boolean m;
   private static final JungleTemplePiece.a n = new JungleTemplePiece.a();

   public JungleTemplePiece(RandomSource var0, int var1, int var2) {
      super(WorldGenFeatureStructurePieceType.G, var1, 64, var2, 12, 10, 15, a(var0));
   }

   public JungleTemplePiece(NBTTagCompound var0) {
      super(WorldGenFeatureStructurePieceType.G, var0);
      this.j = var0.q("placedMainChest");
      this.k = var0.q("placedHiddenChest");
      this.l = var0.q("placedTrap1");
      this.m = var0.q("placedTrap2");
   }

   @Override
   protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
      super.a(var0, var1);
      var1.a("placedMainChest", this.j);
      var1.a("placedHiddenChest", this.k);
      var1.a("placedTrap1", this.l);
      var1.a("placedTrap2", this.m);
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
      if (this.a(var0, var4, 0)) {
         this.a(var0, var4, 0, -4, 0, this.a - 1, 0, this.c - 1, false, var3, n);
         this.a(var0, var4, 2, 1, 2, 9, 2, 2, false, var3, n);
         this.a(var0, var4, 2, 1, 12, 9, 2, 12, false, var3, n);
         this.a(var0, var4, 2, 1, 3, 2, 2, 11, false, var3, n);
         this.a(var0, var4, 9, 1, 3, 9, 2, 11, false, var3, n);
         this.a(var0, var4, 1, 3, 1, 10, 6, 1, false, var3, n);
         this.a(var0, var4, 1, 3, 13, 10, 6, 13, false, var3, n);
         this.a(var0, var4, 1, 3, 2, 1, 6, 12, false, var3, n);
         this.a(var0, var4, 10, 3, 2, 10, 6, 12, false, var3, n);
         this.a(var0, var4, 2, 3, 2, 9, 3, 12, false, var3, n);
         this.a(var0, var4, 2, 6, 2, 9, 6, 12, false, var3, n);
         this.a(var0, var4, 3, 7, 3, 8, 7, 11, false, var3, n);
         this.a(var0, var4, 4, 8, 4, 7, 8, 10, false, var3, n);
         this.a(var0, var4, 3, 1, 3, 8, 2, 11);
         this.a(var0, var4, 4, 3, 6, 7, 3, 9);
         this.a(var0, var4, 2, 4, 2, 9, 5, 12);
         this.a(var0, var4, 4, 6, 5, 7, 6, 9);
         this.a(var0, var4, 5, 7, 6, 6, 7, 8);
         this.a(var0, var4, 5, 1, 2, 6, 2, 2);
         this.a(var0, var4, 5, 2, 12, 6, 2, 12);
         this.a(var0, var4, 5, 5, 1, 6, 5, 1);
         this.a(var0, var4, 5, 5, 13, 6, 5, 13);
         this.a(var0, Blocks.a.o(), 1, 5, 5, var4);
         this.a(var0, Blocks.a.o(), 10, 5, 5, var4);
         this.a(var0, Blocks.a.o(), 1, 5, 9, var4);
         this.a(var0, Blocks.a.o(), 10, 5, 9, var4);

         for(int var7 = 0; var7 <= 14; var7 += 14) {
            this.a(var0, var4, 2, 4, var7, 2, 5, var7, false, var3, n);
            this.a(var0, var4, 4, 4, var7, 4, 5, var7, false, var3, n);
            this.a(var0, var4, 7, 4, var7, 7, 5, var7, false, var3, n);
            this.a(var0, var4, 9, 4, var7, 9, 5, var7, false, var3, n);
         }

         this.a(var0, var4, 5, 6, 0, 6, 6, 0, false, var3, n);

         for(int var7 = 0; var7 <= 11; var7 += 11) {
            for(int var8 = 2; var8 <= 12; var8 += 2) {
               this.a(var0, var4, var7, 4, var8, var7, 5, var8, false, var3, n);
            }

            this.a(var0, var4, var7, 6, 5, var7, 6, 5, false, var3, n);
            this.a(var0, var4, var7, 6, 9, var7, 6, 9, false, var3, n);
         }

         this.a(var0, var4, 2, 7, 2, 2, 9, 2, false, var3, n);
         this.a(var0, var4, 9, 7, 2, 9, 9, 2, false, var3, n);
         this.a(var0, var4, 2, 7, 12, 2, 9, 12, false, var3, n);
         this.a(var0, var4, 9, 7, 12, 9, 9, 12, false, var3, n);
         this.a(var0, var4, 4, 9, 4, 4, 9, 4, false, var3, n);
         this.a(var0, var4, 7, 9, 4, 7, 9, 4, false, var3, n);
         this.a(var0, var4, 4, 9, 10, 4, 9, 10, false, var3, n);
         this.a(var0, var4, 7, 9, 10, 7, 9, 10, false, var3, n);
         this.a(var0, var4, 5, 9, 7, 6, 9, 7, false, var3, n);
         IBlockData var7 = Blocks.cP.o().a(BlockStairs.a, EnumDirection.f);
         IBlockData var8 = Blocks.cP.o().a(BlockStairs.a, EnumDirection.e);
         IBlockData var9 = Blocks.cP.o().a(BlockStairs.a, EnumDirection.d);
         IBlockData var10 = Blocks.cP.o().a(BlockStairs.a, EnumDirection.c);
         this.a(var0, var10, 5, 9, 6, var4);
         this.a(var0, var10, 6, 9, 6, var4);
         this.a(var0, var9, 5, 9, 8, var4);
         this.a(var0, var9, 6, 9, 8, var4);
         this.a(var0, var10, 4, 0, 0, var4);
         this.a(var0, var10, 5, 0, 0, var4);
         this.a(var0, var10, 6, 0, 0, var4);
         this.a(var0, var10, 7, 0, 0, var4);
         this.a(var0, var10, 4, 1, 8, var4);
         this.a(var0, var10, 4, 2, 9, var4);
         this.a(var0, var10, 4, 3, 10, var4);
         this.a(var0, var10, 7, 1, 8, var4);
         this.a(var0, var10, 7, 2, 9, var4);
         this.a(var0, var10, 7, 3, 10, var4);
         this.a(var0, var4, 4, 1, 9, 4, 1, 9, false, var3, n);
         this.a(var0, var4, 7, 1, 9, 7, 1, 9, false, var3, n);
         this.a(var0, var4, 4, 1, 10, 7, 2, 10, false, var3, n);
         this.a(var0, var4, 5, 4, 5, 6, 4, 5, false, var3, n);
         this.a(var0, var7, 4, 4, 5, var4);
         this.a(var0, var8, 7, 4, 5, var4);

         for(int var11 = 0; var11 < 4; ++var11) {
            this.a(var0, var9, 5, 0 - var11, 6 + var11, var4);
            this.a(var0, var9, 6, 0 - var11, 6 + var11, var4);
            this.a(var0, var4, 5, 0 - var11, 7 + var11, 6, 0 - var11, 9 + var11);
         }

         this.a(var0, var4, 1, -3, 12, 10, -1, 13);
         this.a(var0, var4, 1, -3, 1, 3, -1, 13);
         this.a(var0, var4, 1, -3, 1, 9, -1, 5);

         for(int var11 = 1; var11 <= 13; var11 += 2) {
            this.a(var0, var4, 1, -3, var11, 1, -2, var11, false, var3, n);
         }

         for(int var11 = 2; var11 <= 12; var11 += 2) {
            this.a(var0, var4, 1, -1, var11, 3, -1, var11, false, var3, n);
         }

         this.a(var0, var4, 2, -2, 1, 5, -2, 1, false, var3, n);
         this.a(var0, var4, 7, -2, 1, 9, -2, 1, false, var3, n);
         this.a(var0, var4, 6, -3, 1, 6, -3, 1, false, var3, n);
         this.a(var0, var4, 6, -1, 1, 6, -1, 1, false, var3, n);
         this.a(var0, Blocks.fG.o().a(BlockTripwireHook.a, EnumDirection.f).a(BlockTripwireHook.c, Boolean.valueOf(true)), 1, -3, 8, var4);
         this.a(var0, Blocks.fG.o().a(BlockTripwireHook.a, EnumDirection.e).a(BlockTripwireHook.c, Boolean.valueOf(true)), 4, -3, 8, var4);
         this.a(
            var0,
            Blocks.fH.o().a(BlockTripwire.e, Boolean.valueOf(true)).a(BlockTripwire.g, Boolean.valueOf(true)).a(BlockTripwire.b, Boolean.valueOf(true)),
            2,
            -3,
            8,
            var4
         );
         this.a(
            var0,
            Blocks.fH.o().a(BlockTripwire.e, Boolean.valueOf(true)).a(BlockTripwire.g, Boolean.valueOf(true)).a(BlockTripwire.b, Boolean.valueOf(true)),
            3,
            -3,
            8,
            var4
         );
         IBlockData var11 = Blocks.cv.o().a(BlockRedstoneWire.a, BlockPropertyRedstoneSide.b).a(BlockRedstoneWire.c, BlockPropertyRedstoneSide.b);
         this.a(var0, var11, 5, -3, 7, var4);
         this.a(var0, var11, 5, -3, 6, var4);
         this.a(var0, var11, 5, -3, 5, var4);
         this.a(var0, var11, 5, -3, 4, var4);
         this.a(var0, var11, 5, -3, 3, var4);
         this.a(var0, var11, 5, -3, 2, var4);
         this.a(var0, Blocks.cv.o().a(BlockRedstoneWire.a, BlockPropertyRedstoneSide.b).a(BlockRedstoneWire.d, BlockPropertyRedstoneSide.b), 5, -3, 1, var4);
         this.a(var0, Blocks.cv.o().a(BlockRedstoneWire.b, BlockPropertyRedstoneSide.b).a(BlockRedstoneWire.d, BlockPropertyRedstoneSide.b), 4, -3, 1, var4);
         this.a(var0, Blocks.cm.o(), 3, -3, 1, var4);
         if (!this.l) {
            this.l = this.a(var0, var4, var3, 3, -2, 1, EnumDirection.c, LootTables.B);
         }

         this.a(var0, Blocks.fe.o().a(BlockVine.d, Boolean.valueOf(true)), 3, -2, 2, var4);
         this.a(var0, Blocks.fG.o().a(BlockTripwireHook.a, EnumDirection.c).a(BlockTripwireHook.c, Boolean.valueOf(true)), 7, -3, 1, var4);
         this.a(var0, Blocks.fG.o().a(BlockTripwireHook.a, EnumDirection.d).a(BlockTripwireHook.c, Boolean.valueOf(true)), 7, -3, 5, var4);
         this.a(
            var0,
            Blocks.fH.o().a(BlockTripwire.d, Boolean.valueOf(true)).a(BlockTripwire.f, Boolean.valueOf(true)).a(BlockTripwire.b, Boolean.valueOf(true)),
            7,
            -3,
            2,
            var4
         );
         this.a(
            var0,
            Blocks.fH.o().a(BlockTripwire.d, Boolean.valueOf(true)).a(BlockTripwire.f, Boolean.valueOf(true)).a(BlockTripwire.b, Boolean.valueOf(true)),
            7,
            -3,
            3,
            var4
         );
         this.a(
            var0,
            Blocks.fH.o().a(BlockTripwire.d, Boolean.valueOf(true)).a(BlockTripwire.f, Boolean.valueOf(true)).a(BlockTripwire.b, Boolean.valueOf(true)),
            7,
            -3,
            4,
            var4
         );
         this.a(var0, Blocks.cv.o().a(BlockRedstoneWire.b, BlockPropertyRedstoneSide.b).a(BlockRedstoneWire.d, BlockPropertyRedstoneSide.b), 8, -3, 6, var4);
         this.a(var0, Blocks.cv.o().a(BlockRedstoneWire.d, BlockPropertyRedstoneSide.b).a(BlockRedstoneWire.c, BlockPropertyRedstoneSide.b), 9, -3, 6, var4);
         this.a(var0, Blocks.cv.o().a(BlockRedstoneWire.a, BlockPropertyRedstoneSide.b).a(BlockRedstoneWire.c, BlockPropertyRedstoneSide.a), 9, -3, 5, var4);
         this.a(var0, Blocks.cm.o(), 9, -3, 4, var4);
         this.a(var0, var11, 9, -2, 4, var4);
         if (!this.m) {
            this.m = this.a(var0, var4, var3, 9, -2, 3, EnumDirection.e, LootTables.B);
         }

         this.a(var0, Blocks.fe.o().a(BlockVine.c, Boolean.valueOf(true)), 8, -1, 3, var4);
         this.a(var0, Blocks.fe.o().a(BlockVine.c, Boolean.valueOf(true)), 8, -2, 3, var4);
         if (!this.j) {
            this.j = this.a(var0, var4, var3, 8, -3, 3, LootTables.A);
         }

         this.a(var0, Blocks.cm.o(), 9, -3, 2, var4);
         this.a(var0, Blocks.cm.o(), 8, -3, 1, var4);
         this.a(var0, Blocks.cm.o(), 4, -3, 5, var4);
         this.a(var0, Blocks.cm.o(), 5, -2, 5, var4);
         this.a(var0, Blocks.cm.o(), 5, -1, 5, var4);
         this.a(var0, Blocks.cm.o(), 6, -3, 5, var4);
         this.a(var0, Blocks.cm.o(), 7, -2, 5, var4);
         this.a(var0, Blocks.cm.o(), 7, -1, 5, var4);
         this.a(var0, Blocks.cm.o(), 8, -3, 5, var4);
         this.a(var0, var4, 9, -1, 1, 9, -1, 5, false, var3, n);
         this.a(var0, var4, 8, -3, 8, 10, -1, 10);
         this.a(var0, Blocks.eK.o(), 8, -2, 11, var4);
         this.a(var0, Blocks.eK.o(), 9, -2, 11, var4);
         this.a(var0, Blocks.eK.o(), 10, -2, 11, var4);
         IBlockData var12 = Blocks.dv.o().a(BlockLever.aD, EnumDirection.c).a(BlockLever.J, BlockPropertyAttachPosition.b);
         this.a(var0, var12, 8, -2, 12, var4);
         this.a(var0, var12, 9, -2, 12, var4);
         this.a(var0, var12, 10, -2, 12, var4);
         this.a(var0, var4, 8, -3, 8, 8, -3, 10, false, var3, n);
         this.a(var0, var4, 10, -3, 8, 10, -3, 10, false, var3, n);
         this.a(var0, Blocks.cm.o(), 10, -2, 9, var4);
         this.a(var0, var11, 8, -2, 9, var4);
         this.a(var0, var11, 8, -2, 10, var4);
         this.a(
            var0,
            Blocks.cv
               .o()
               .a(BlockRedstoneWire.a, BlockPropertyRedstoneSide.b)
               .a(BlockRedstoneWire.c, BlockPropertyRedstoneSide.b)
               .a(BlockRedstoneWire.b, BlockPropertyRedstoneSide.b)
               .a(BlockRedstoneWire.d, BlockPropertyRedstoneSide.b),
            10,
            -1,
            9,
            var4
         );
         this.a(var0, Blocks.bq.o().a(BlockPiston.a, EnumDirection.b), 9, -2, 8, var4);
         this.a(var0, Blocks.bq.o().a(BlockPiston.a, EnumDirection.e), 10, -2, 8, var4);
         this.a(var0, Blocks.bq.o().a(BlockPiston.a, EnumDirection.e), 10, -1, 8, var4);
         this.a(var0, Blocks.eh.o().a(BlockRepeater.aD, EnumDirection.c), 10, -2, 10, var4);
         if (!this.k) {
            this.k = this.a(var0, var4, var3, 9, -3, 10, LootTables.A);
         }
      }
   }

   static class a extends StructurePiece.StructurePieceBlockSelector {
      @Override
      public void a(RandomSource var0, int var1, int var2, int var3, boolean var4) {
         if (var0.i() < 0.4F) {
            this.a = Blocks.m.o();
         } else {
            this.a = Blocks.cm.o();
         }
      }
   }
}
