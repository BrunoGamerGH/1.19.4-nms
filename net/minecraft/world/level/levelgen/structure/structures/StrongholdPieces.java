package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.BlockButtonAbstract;
import net.minecraft.world.level.block.BlockDoor;
import net.minecraft.world.level.block.BlockEnderPortalFrame;
import net.minecraft.world.level.block.BlockFence;
import net.minecraft.world.level.block.BlockIronBars;
import net.minecraft.world.level.block.BlockLadder;
import net.minecraft.world.level.block.BlockStairs;
import net.minecraft.world.level.block.BlockStepAbstract;
import net.minecraft.world.level.block.BlockTorchWall;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityMobSpawner;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyDoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.BlockPropertySlabType;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;
import net.minecraft.world.level.storage.loot.LootTables;

public class StrongholdPieces {
   private static final int b = 3;
   private static final int c = 3;
   private static final int d = 50;
   private static final int e = 10;
   private static final boolean f = true;
   public static final int a = 64;
   private static final StrongholdPieces.f[] g = new StrongholdPieces.f[]{
      new StrongholdPieces.f(StrongholdPieces.n.class, 40, 0),
      new StrongholdPieces.f(StrongholdPieces.h.class, 5, 5),
      new StrongholdPieces.f(StrongholdPieces.d.class, 20, 0),
      new StrongholdPieces.f(StrongholdPieces.i.class, 20, 0),
      new StrongholdPieces.f(StrongholdPieces.j.class, 10, 6),
      new StrongholdPieces.f(StrongholdPieces.o.class, 5, 5),
      new StrongholdPieces.f(StrongholdPieces.l.class, 5, 5),
      new StrongholdPieces.f(StrongholdPieces.c.class, 5, 4),
      new StrongholdPieces.f(StrongholdPieces.a.class, 5, 4),
      new StrongholdPieces.f(StrongholdPieces.e.class, 10, 2) {
         @Override
         public boolean a(int var0) {
            return super.a(var0) && var0 > 4;
         }
      },
      new StrongholdPieces.f(StrongholdPieces.g.class, 20, 1) {
         @Override
         public boolean a(int var0) {
            return super.a(var0) && var0 > 5;
         }
      }
   };
   private static List<StrongholdPieces.f> h;
   static Class<? extends StrongholdPieces.p> i;
   private static int j;
   static final StrongholdPieces.k k = new StrongholdPieces.k();

   public static void a() {
      h = Lists.newArrayList();

      for(StrongholdPieces.f var3 : g) {
         var3.c = 0;
         h.add(var3);
      }

      i = null;
   }

   private static boolean b() {
      boolean var0 = false;
      j = 0;

      for(StrongholdPieces.f var2 : h) {
         if (var2.d > 0 && var2.c < var2.d) {
            var0 = true;
         }

         j += var2.b;
      }

      return var0;
   }

   private static StrongholdPieces.p a(
      Class<? extends StrongholdPieces.p> var0,
      StructurePieceAccessor var1,
      RandomSource var2,
      int var3,
      int var4,
      int var5,
      @Nullable EnumDirection var6,
      int var7
   ) {
      StrongholdPieces.p var8 = null;
      if (var0 == StrongholdPieces.n.class) {
         var8 = StrongholdPieces.n.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StrongholdPieces.h.class) {
         var8 = StrongholdPieces.h.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StrongholdPieces.d.class) {
         var8 = StrongholdPieces.d.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StrongholdPieces.i.class) {
         var8 = StrongholdPieces.i.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StrongholdPieces.j.class) {
         var8 = StrongholdPieces.j.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StrongholdPieces.o.class) {
         var8 = StrongholdPieces.o.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StrongholdPieces.l.class) {
         var8 = StrongholdPieces.l.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StrongholdPieces.c.class) {
         var8 = StrongholdPieces.c.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StrongholdPieces.a.class) {
         var8 = StrongholdPieces.a.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StrongholdPieces.e.class) {
         var8 = StrongholdPieces.e.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StrongholdPieces.g.class) {
         var8 = StrongholdPieces.g.a(var1, var3, var4, var5, var6, var7);
      }

      return var8;
   }

   private static StrongholdPieces.p a(
      StrongholdPieces.m var0, StructurePieceAccessor var1, RandomSource var2, int var3, int var4, int var5, EnumDirection var6, int var7
   ) {
      if (!b()) {
         return null;
      } else {
         if (i != null) {
            StrongholdPieces.p var8 = a(i, var1, var2, var3, var4, var5, var6, var7);
            i = null;
            if (var8 != null) {
               return var8;
            }
         }

         int var8 = 0;

         while(var8 < 5) {
            ++var8;
            int var9 = var2.a(j);

            for(StrongholdPieces.f var11 : h) {
               var9 -= var11.b;
               if (var9 < 0) {
                  if (!var11.a(var7) || var11 == var0.a) {
                     break;
                  }

                  StrongholdPieces.p var12 = a(var11.a, var1, var2, var3, var4, var5, var6, var7);
                  if (var12 != null) {
                     ++var11.c;
                     var0.a = var11;
                     if (!var11.a()) {
                        h.remove(var11);
                     }

                     return var12;
                  }
               }
            }
         }

         StructureBoundingBox var9 = StrongholdPieces.b.a(var1, var2, var3, var4, var5, var6);
         return var9 != null && var9.h() > 1 ? new StrongholdPieces.b(var7, var9, var6) : null;
      }
   }

   static StructurePiece b(
      StrongholdPieces.m var0, StructurePieceAccessor var1, RandomSource var2, int var3, int var4, int var5, @Nullable EnumDirection var6, int var7
   ) {
      if (var7 > 50) {
         return null;
      } else if (Math.abs(var3 - var0.f().g()) <= 112 && Math.abs(var5 - var0.f().i()) <= 112) {
         StructurePiece var8 = a(var0, var1, var2, var3, var4, var5, var6, var7 + 1);
         if (var8 != null) {
            var1.a(var8);
            var0.c.add(var8);
         }

         return var8;
      } else {
         return null;
      }
   }

   public static class a extends StrongholdPieces.p {
      private static final int a = 5;
      private static final int b = 5;
      private static final int c = 7;
      private boolean d;

      public a(int var0, RandomSource var1, StructureBoundingBox var2, EnumDirection var3) {
         super(WorldGenFeatureStructurePieceType.t, var0, var2);
         this.a(var3);
         this.h = this.b(var1);
      }

      public a(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.t, var0);
         this.d = var0.q("Chest");
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("Chest", this.d);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         this.a((StrongholdPieces.m)var0, var1, var2, 1, 1);
      }

      public static StrongholdPieces.a a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -1, 0, 5, 5, 7, var5);
         return a(var7) && var0.a(var7) == null ? new StrongholdPieces.a(var6, var1, var7, var5) : null;
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
         this.a(var0, var4, 0, 0, 0, 4, 4, 6, true, var3, StrongholdPieces.k);
         this.a(var0, var3, var4, this.h, 1, 1, 0);
         this.a(var0, var3, var4, StrongholdPieces.p.a.a, 1, 1, 6);
         this.a(var0, var4, 3, 1, 2, 3, 1, 4, Blocks.eH.o(), Blocks.eH.o(), false);
         this.a(var0, Blocks.jJ.o(), 3, 1, 1, var4);
         this.a(var0, Blocks.jJ.o(), 3, 1, 5, var4);
         this.a(var0, Blocks.jJ.o(), 3, 2, 2, var4);
         this.a(var0, Blocks.jJ.o(), 3, 2, 4, var4);

         for(int var7 = 2; var7 <= 4; ++var7) {
            this.a(var0, Blocks.jJ.o(), 2, 1, var7, var4);
         }

         if (!this.d && var4.b(this.b(3, 2, 3))) {
            this.d = true;
            this.a(var0, var4, var3, 3, 2, 3, LootTables.y);
         }
      }
   }

   public static class b extends StrongholdPieces.p {
      private final int a;

      public b(int var0, StructureBoundingBox var1, EnumDirection var2) {
         super(WorldGenFeatureStructurePieceType.u, var0, var1);
         this.a(var2);
         this.a = var2 != EnumDirection.c && var2 != EnumDirection.d ? var1.c() : var1.e();
      }

      public b(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.u, var0);
         this.a = var0.h("Steps");
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("Steps", this.a);
      }

      public static StructureBoundingBox a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5) {
         int var6 = 3;
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -1, 0, 5, 5, 4, var5);
         StructurePiece var8 = var0.a(var7);
         if (var8 == null) {
            return null;
         } else {
            if (var8.f().h() == var7.h()) {
               for(int var9 = 2; var9 >= 1; --var9) {
                  var7 = StructureBoundingBox.a(var2, var3, var4, -1, -1, 0, 5, 5, var9, var5);
                  if (!var8.f().a(var7)) {
                     return StructureBoundingBox.a(var2, var3, var4, -1, -1, 0, 5, 5, var9 + 1, var5);
                  }
               }
            }

            return null;
         }
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
         for(int var7 = 0; var7 < this.a; ++var7) {
            this.a(var0, Blocks.eH.o(), 0, 0, var7, var4);
            this.a(var0, Blocks.eH.o(), 1, 0, var7, var4);
            this.a(var0, Blocks.eH.o(), 2, 0, var7, var4);
            this.a(var0, Blocks.eH.o(), 3, 0, var7, var4);
            this.a(var0, Blocks.eH.o(), 4, 0, var7, var4);

            for(int var8 = 1; var8 <= 3; ++var8) {
               this.a(var0, Blocks.eH.o(), 0, var8, var7, var4);
               this.a(var0, Blocks.mY.o(), 1, var8, var7, var4);
               this.a(var0, Blocks.mY.o(), 2, var8, var7, var4);
               this.a(var0, Blocks.mY.o(), 3, var8, var7, var4);
               this.a(var0, Blocks.eH.o(), 4, var8, var7, var4);
            }

            this.a(var0, Blocks.eH.o(), 0, 4, var7, var4);
            this.a(var0, Blocks.eH.o(), 1, 4, var7, var4);
            this.a(var0, Blocks.eH.o(), 2, 4, var7, var4);
            this.a(var0, Blocks.eH.o(), 3, 4, var7, var4);
            this.a(var0, Blocks.eH.o(), 4, 4, var7, var4);
         }
      }
   }

   public static class c extends StrongholdPieces.p {
      protected static final int a = 10;
      protected static final int b = 9;
      protected static final int c = 11;
      private final boolean d;
      private final boolean i;
      private final boolean j;
      private final boolean k;

      public c(int var0, RandomSource var1, StructureBoundingBox var2, EnumDirection var3) {
         super(WorldGenFeatureStructurePieceType.v, var0, var2);
         this.a(var3);
         this.h = this.b(var1);
         this.d = var1.h();
         this.i = var1.h();
         this.j = var1.h();
         this.k = var1.a(3) > 0;
      }

      public c(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.v, var0);
         this.d = var0.q("leftLow");
         this.i = var0.q("leftHigh");
         this.j = var0.q("rightLow");
         this.k = var0.q("rightHigh");
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("leftLow", this.d);
         var1.a("leftHigh", this.i);
         var1.a("rightLow", this.j);
         var1.a("rightHigh", this.k);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         int var3 = 3;
         int var4 = 5;
         EnumDirection var5 = this.i();
         if (var5 == EnumDirection.e || var5 == EnumDirection.c) {
            var3 = 8 - var3;
            var4 = 8 - var4;
         }

         this.a((StrongholdPieces.m)var0, var1, var2, 5, 1);
         if (this.d) {
            this.b((StrongholdPieces.m)var0, var1, var2, var3, 1);
         }

         if (this.i) {
            this.b((StrongholdPieces.m)var0, var1, var2, var4, 7);
         }

         if (this.j) {
            this.c((StrongholdPieces.m)var0, var1, var2, var3, 1);
         }

         if (this.k) {
            this.c((StrongholdPieces.m)var0, var1, var2, var4, 7);
         }
      }

      public static StrongholdPieces.c a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -4, -3, 0, 10, 9, 11, var5);
         return a(var7) && var0.a(var7) == null ? new StrongholdPieces.c(var6, var1, var7, var5) : null;
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
         this.a(var0, var4, 0, 0, 0, 9, 8, 10, true, var3, StrongholdPieces.k);
         this.a(var0, var3, var4, this.h, 4, 3, 0);
         if (this.d) {
            this.a(var0, var4, 0, 3, 1, 0, 5, 3, e, e, false);
         }

         if (this.j) {
            this.a(var0, var4, 9, 3, 1, 9, 5, 3, e, e, false);
         }

         if (this.i) {
            this.a(var0, var4, 0, 5, 7, 0, 7, 9, e, e, false);
         }

         if (this.k) {
            this.a(var0, var4, 9, 5, 7, 9, 7, 9, e, e, false);
         }

         this.a(var0, var4, 5, 1, 10, 7, 3, 10, e, e, false);
         this.a(var0, var4, 1, 2, 1, 8, 2, 6, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 4, 1, 5, 4, 4, 9, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 8, 1, 5, 8, 4, 9, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 1, 4, 7, 3, 4, 9, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 1, 3, 5, 3, 3, 6, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 1, 3, 4, 3, 3, 4, Blocks.jD.o(), Blocks.jD.o(), false);
         this.a(var0, var4, 1, 4, 6, 3, 4, 6, Blocks.jD.o(), Blocks.jD.o(), false);
         this.a(var0, var4, 5, 1, 7, 7, 1, 8, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 5, 1, 9, 7, 1, 9, Blocks.jD.o(), Blocks.jD.o(), false);
         this.a(var0, var4, 5, 2, 7, 7, 2, 7, Blocks.jD.o(), Blocks.jD.o(), false);
         this.a(var0, var4, 4, 5, 7, 4, 5, 9, Blocks.jD.o(), Blocks.jD.o(), false);
         this.a(var0, var4, 8, 5, 7, 8, 5, 9, Blocks.jD.o(), Blocks.jD.o(), false);
         this.a(
            var0,
            var4,
            5,
            5,
            7,
            7,
            5,
            9,
            Blocks.jD.o().a(BlockStepAbstract.a, BlockPropertySlabType.c),
            Blocks.jD.o().a(BlockStepAbstract.a, BlockPropertySlabType.c),
            false
         );
         this.a(var0, Blocks.cp.o().a(BlockTorchWall.a, EnumDirection.d), 6, 5, 6, var4);
      }
   }

   public static class d extends StrongholdPieces.q {
      public d(int var0, RandomSource var1, StructureBoundingBox var2, EnumDirection var3) {
         super(WorldGenFeatureStructurePieceType.w, var0, var2);
         this.a(var3);
         this.h = this.b(var1);
      }

      public d(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.w, var0);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         EnumDirection var3 = this.i();
         if (var3 != EnumDirection.c && var3 != EnumDirection.f) {
            this.c((StrongholdPieces.m)var0, var1, var2, 1, 1);
         } else {
            this.b((StrongholdPieces.m)var0, var1, var2, 1, 1);
         }
      }

      public static StrongholdPieces.d a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -1, 0, 5, 5, 5, var5);
         return a(var7) && var0.a(var7) == null ? new StrongholdPieces.d(var6, var1, var7, var5) : null;
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
         this.a(var0, var4, 0, 0, 0, 4, 4, 4, true, var3, StrongholdPieces.k);
         this.a(var0, var3, var4, this.h, 1, 1, 0);
         EnumDirection var7 = this.i();
         if (var7 != EnumDirection.c && var7 != EnumDirection.f) {
            this.a(var0, var4, 4, 1, 1, 4, 3, 3, e, e, false);
         } else {
            this.a(var0, var4, 0, 1, 1, 0, 3, 3, e, e, false);
         }
      }
   }

   public static class e extends StrongholdPieces.p {
      protected static final int a = 14;
      protected static final int b = 6;
      protected static final int c = 11;
      protected static final int d = 15;
      private final boolean i;

      public e(int var0, RandomSource var1, StructureBoundingBox var2, EnumDirection var3) {
         super(WorldGenFeatureStructurePieceType.x, var0, var2);
         this.a(var3);
         this.h = this.b(var1);
         this.i = var2.d() > 6;
      }

      public e(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.x, var0);
         this.i = var0.q("Tall");
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("Tall", this.i);
      }

      public static StrongholdPieces.e a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -4, -1, 0, 14, 11, 15, var5);
         if (!a(var7) || var0.a(var7) != null) {
            var7 = StructureBoundingBox.a(var2, var3, var4, -4, -1, 0, 14, 6, 15, var5);
            if (!a(var7) || var0.a(var7) != null) {
               return null;
            }
         }

         return new StrongholdPieces.e(var6, var1, var7, var5);
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
         int var7 = 11;
         if (!this.i) {
            var7 = 6;
         }

         this.a(var0, var4, 0, 0, 0, 13, var7 - 1, 14, true, var3, StrongholdPieces.k);
         this.a(var0, var3, var4, this.h, 4, 1, 0);
         this.a(var0, var4, var3, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.br.o(), Blocks.br.o(), false, false);
         int var8 = 1;
         int var9 = 12;

         for(int var10 = 1; var10 <= 13; ++var10) {
            if ((var10 - 1) % 4 == 0) {
               this.a(var0, var4, 1, 1, var10, 1, 4, var10, Blocks.n.o(), Blocks.n.o(), false);
               this.a(var0, var4, 12, 1, var10, 12, 4, var10, Blocks.n.o(), Blocks.n.o(), false);
               this.a(var0, Blocks.cp.o().a(BlockTorchWall.a, EnumDirection.f), 2, 3, var10, var4);
               this.a(var0, Blocks.cp.o().a(BlockTorchWall.a, EnumDirection.e), 11, 3, var10, var4);
               if (this.i) {
                  this.a(var0, var4, 1, 6, var10, 1, 9, var10, Blocks.n.o(), Blocks.n.o(), false);
                  this.a(var0, var4, 12, 6, var10, 12, 9, var10, Blocks.n.o(), Blocks.n.o(), false);
               }
            } else {
               this.a(var0, var4, 1, 1, var10, 1, 4, var10, Blocks.ck.o(), Blocks.ck.o(), false);
               this.a(var0, var4, 12, 1, var10, 12, 4, var10, Blocks.ck.o(), Blocks.ck.o(), false);
               if (this.i) {
                  this.a(var0, var4, 1, 6, var10, 1, 9, var10, Blocks.ck.o(), Blocks.ck.o(), false);
                  this.a(var0, var4, 12, 6, var10, 12, 9, var10, Blocks.ck.o(), Blocks.ck.o(), false);
               }
            }
         }

         for(int var10 = 3; var10 < 12; var10 += 2) {
            this.a(var0, var4, 3, 1, var10, 4, 3, var10, Blocks.ck.o(), Blocks.ck.o(), false);
            this.a(var0, var4, 6, 1, var10, 7, 3, var10, Blocks.ck.o(), Blocks.ck.o(), false);
            this.a(var0, var4, 9, 1, var10, 10, 3, var10, Blocks.ck.o(), Blocks.ck.o(), false);
         }

         if (this.i) {
            this.a(var0, var4, 1, 5, 1, 3, 5, 13, Blocks.n.o(), Blocks.n.o(), false);
            this.a(var0, var4, 10, 5, 1, 12, 5, 13, Blocks.n.o(), Blocks.n.o(), false);
            this.a(var0, var4, 4, 5, 1, 9, 5, 2, Blocks.n.o(), Blocks.n.o(), false);
            this.a(var0, var4, 4, 5, 12, 9, 5, 13, Blocks.n.o(), Blocks.n.o(), false);
            this.a(var0, Blocks.n.o(), 9, 5, 11, var4);
            this.a(var0, Blocks.n.o(), 8, 5, 11, var4);
            this.a(var0, Blocks.n.o(), 9, 5, 10, var4);
            IBlockData var10 = Blocks.dT.o().a(BlockFence.d, Boolean.valueOf(true)).a(BlockFence.b, Boolean.valueOf(true));
            IBlockData var11 = Blocks.dT.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.c, Boolean.valueOf(true));
            this.a(var0, var4, 3, 6, 3, 3, 6, 11, var11, var11, false);
            this.a(var0, var4, 10, 6, 3, 10, 6, 9, var11, var11, false);
            this.a(var0, var4, 4, 6, 2, 9, 6, 2, var10, var10, false);
            this.a(var0, var4, 4, 6, 12, 7, 6, 12, var10, var10, false);
            this.a(var0, Blocks.dT.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.b, Boolean.valueOf(true)), 3, 6, 2, var4);
            this.a(var0, Blocks.dT.o().a(BlockFence.c, Boolean.valueOf(true)).a(BlockFence.b, Boolean.valueOf(true)), 3, 6, 12, var4);
            this.a(var0, Blocks.dT.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.d, Boolean.valueOf(true)), 10, 6, 2, var4);

            for(int var12 = 0; var12 <= 2; ++var12) {
               this.a(var0, Blocks.dT.o().a(BlockFence.c, Boolean.valueOf(true)).a(BlockFence.d, Boolean.valueOf(true)), 8 + var12, 6, 12 - var12, var4);
               if (var12 != 2) {
                  this.a(var0, Blocks.dT.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.b, Boolean.valueOf(true)), 8 + var12, 6, 11 - var12, var4);
               }
            }

            IBlockData var12 = Blocks.cN.o().a(BlockLadder.a, EnumDirection.d);
            this.a(var0, var12, 10, 1, 13, var4);
            this.a(var0, var12, 10, 2, 13, var4);
            this.a(var0, var12, 10, 3, 13, var4);
            this.a(var0, var12, 10, 4, 13, var4);
            this.a(var0, var12, 10, 5, 13, var4);
            this.a(var0, var12, 10, 6, 13, var4);
            this.a(var0, var12, 10, 7, 13, var4);
            int var13 = 7;
            int var14 = 7;
            IBlockData var15 = Blocks.dT.o().a(BlockFence.b, Boolean.valueOf(true));
            this.a(var0, var15, 6, 9, 7, var4);
            IBlockData var16 = Blocks.dT.o().a(BlockFence.d, Boolean.valueOf(true));
            this.a(var0, var16, 7, 9, 7, var4);
            this.a(var0, var15, 6, 8, 7, var4);
            this.a(var0, var16, 7, 8, 7, var4);
            IBlockData var17 = var11.a(BlockFence.d, Boolean.valueOf(true)).a(BlockFence.b, Boolean.valueOf(true));
            this.a(var0, var17, 6, 7, 7, var4);
            this.a(var0, var17, 7, 7, 7, var4);
            this.a(var0, var15, 5, 7, 7, var4);
            this.a(var0, var16, 8, 7, 7, var4);
            this.a(var0, var15.a(BlockFence.a, Boolean.valueOf(true)), 6, 7, 6, var4);
            this.a(var0, var15.a(BlockFence.c, Boolean.valueOf(true)), 6, 7, 8, var4);
            this.a(var0, var16.a(BlockFence.a, Boolean.valueOf(true)), 7, 7, 6, var4);
            this.a(var0, var16.a(BlockFence.c, Boolean.valueOf(true)), 7, 7, 8, var4);
            IBlockData var18 = Blocks.co.o();
            this.a(var0, var18, 5, 8, 7, var4);
            this.a(var0, var18, 8, 8, 7, var4);
            this.a(var0, var18, 6, 8, 6, var4);
            this.a(var0, var18, 6, 8, 8, var4);
            this.a(var0, var18, 7, 8, 6, var4);
            this.a(var0, var18, 7, 8, 8, var4);
         }

         this.a(var0, var4, var3, 3, 3, 5, LootTables.w);
         if (this.i) {
            this.a(var0, e, 12, 9, 1, var4);
            this.a(var0, var4, var3, 12, 8, 1, LootTables.w);
         }
      }
   }

   static class f {
      public final Class<? extends StrongholdPieces.p> a;
      public final int b;
      public int c;
      public final int d;

      public f(Class<? extends StrongholdPieces.p> var0, int var1, int var2) {
         this.a = var0;
         this.b = var1;
         this.d = var2;
      }

      public boolean a(int var0) {
         return this.d == 0 || this.c < this.d;
      }

      public boolean a() {
         return this.d == 0 || this.c < this.d;
      }
   }

   public static class g extends StrongholdPieces.p {
      protected static final int a = 11;
      protected static final int b = 8;
      protected static final int c = 16;
      private boolean d;

      public g(int var0, StructureBoundingBox var1, EnumDirection var2) {
         super(WorldGenFeatureStructurePieceType.y, var0, var1);
         this.a(var2);
      }

      public g(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.y, var0);
         this.d = var0.q("Mob");
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("Mob", this.d);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         if (var0 != null) {
            ((StrongholdPieces.m)var0).b = this;
         }
      }

      public static StrongholdPieces.g a(StructurePieceAccessor var0, int var1, int var2, int var3, EnumDirection var4, int var5) {
         StructureBoundingBox var6 = StructureBoundingBox.a(var1, var2, var3, -4, -1, 0, 11, 8, 16, var4);
         return a(var6) && var0.a(var6) == null ? new StrongholdPieces.g(var5, var6, var4) : null;
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
         this.a(var0, var4, 0, 0, 0, 10, 7, 15, false, var3, StrongholdPieces.k);
         this.a(var0, var3, var4, StrongholdPieces.p.a.c, 4, 1, 0);
         int var7 = 6;
         this.a(var0, var4, 1, 6, 1, 1, 6, 14, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 9, 6, 1, 9, 6, 14, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 2, 6, 1, 8, 6, 2, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 2, 6, 14, 8, 6, 14, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 1, 1, 1, 2, 1, 4, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 8, 1, 1, 9, 1, 4, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 1, 1, 1, 1, 1, 3, Blocks.H.o(), Blocks.H.o(), false);
         this.a(var0, var4, 9, 1, 1, 9, 1, 3, Blocks.H.o(), Blocks.H.o(), false);
         this.a(var0, var4, 3, 1, 8, 7, 1, 12, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 4, 1, 9, 6, 1, 11, Blocks.H.o(), Blocks.H.o(), false);
         IBlockData var8 = Blocks.eW.o().a(BlockIronBars.a, Boolean.valueOf(true)).a(BlockIronBars.c, Boolean.valueOf(true));
         IBlockData var9 = Blocks.eW.o().a(BlockIronBars.d, Boolean.valueOf(true)).a(BlockIronBars.b, Boolean.valueOf(true));

         for(int var10 = 3; var10 < 14; var10 += 2) {
            this.a(var0, var4, 0, 3, var10, 0, 4, var10, var8, var8, false);
            this.a(var0, var4, 10, 3, var10, 10, 4, var10, var8, var8, false);
         }

         for(int var10 = 2; var10 < 9; var10 += 2) {
            this.a(var0, var4, var10, 3, 15, var10, 4, 15, var9, var9, false);
         }

         IBlockData var10 = Blocks.fi.o().a(BlockStairs.a, EnumDirection.c);
         this.a(var0, var4, 4, 1, 5, 6, 1, 7, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 4, 2, 6, 6, 2, 7, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 4, 3, 7, 6, 3, 7, false, var3, StrongholdPieces.k);

         for(int var11 = 4; var11 <= 6; ++var11) {
            this.a(var0, var10, var11, 1, 4, var4);
            this.a(var0, var10, var11, 2, 5, var4);
            this.a(var0, var10, var11, 3, 6, var4);
         }

         IBlockData var11 = Blocks.fx.o().a(BlockEnderPortalFrame.a, EnumDirection.c);
         IBlockData var12 = Blocks.fx.o().a(BlockEnderPortalFrame.a, EnumDirection.d);
         IBlockData var13 = Blocks.fx.o().a(BlockEnderPortalFrame.a, EnumDirection.f);
         IBlockData var14 = Blocks.fx.o().a(BlockEnderPortalFrame.a, EnumDirection.e);
         boolean var15 = true;
         boolean[] var16 = new boolean[12];

         for(int var17 = 0; var17 < var16.length; ++var17) {
            var16[var17] = var3.i() > 0.9F;
            var15 &= var16[var17];
         }

         this.a(var0, var11.a(BlockEnderPortalFrame.b, Boolean.valueOf(var16[0])), 4, 3, 8, var4);
         this.a(var0, var11.a(BlockEnderPortalFrame.b, Boolean.valueOf(var16[1])), 5, 3, 8, var4);
         this.a(var0, var11.a(BlockEnderPortalFrame.b, Boolean.valueOf(var16[2])), 6, 3, 8, var4);
         this.a(var0, var12.a(BlockEnderPortalFrame.b, Boolean.valueOf(var16[3])), 4, 3, 12, var4);
         this.a(var0, var12.a(BlockEnderPortalFrame.b, Boolean.valueOf(var16[4])), 5, 3, 12, var4);
         this.a(var0, var12.a(BlockEnderPortalFrame.b, Boolean.valueOf(var16[5])), 6, 3, 12, var4);
         this.a(var0, var13.a(BlockEnderPortalFrame.b, Boolean.valueOf(var16[6])), 3, 3, 9, var4);
         this.a(var0, var13.a(BlockEnderPortalFrame.b, Boolean.valueOf(var16[7])), 3, 3, 10, var4);
         this.a(var0, var13.a(BlockEnderPortalFrame.b, Boolean.valueOf(var16[8])), 3, 3, 11, var4);
         this.a(var0, var14.a(BlockEnderPortalFrame.b, Boolean.valueOf(var16[9])), 7, 3, 9, var4);
         this.a(var0, var14.a(BlockEnderPortalFrame.b, Boolean.valueOf(var16[10])), 7, 3, 10, var4);
         this.a(var0, var14.a(BlockEnderPortalFrame.b, Boolean.valueOf(var16[11])), 7, 3, 11, var4);
         if (var15) {
            IBlockData var17 = Blocks.fw.o();
            this.a(var0, var17, 4, 3, 9, var4);
            this.a(var0, var17, 5, 3, 9, var4);
            this.a(var0, var17, 6, 3, 9, var4);
            this.a(var0, var17, 4, 3, 10, var4);
            this.a(var0, var17, 5, 3, 10, var4);
            this.a(var0, var17, 6, 3, 10, var4);
            this.a(var0, var17, 4, 3, 11, var4);
            this.a(var0, var17, 5, 3, 11, var4);
            this.a(var0, var17, 6, 3, 11, var4);
         }

         if (!this.d) {
            BlockPosition var17 = this.b(5, 3, 6);
            if (var4.b(var17)) {
               this.d = true;
               var0.a(var17, Blocks.cs.o(), 2);
               TileEntity var18 = var0.c_(var17);
               if (var18 instanceof TileEntityMobSpawner var19) {
                  var19.a(EntityTypes.aI, var3);
               }
            }
         }
      }
   }

   public static class h extends StrongholdPieces.p {
      protected static final int a = 9;
      protected static final int b = 5;
      protected static final int c = 11;

      public h(int var0, RandomSource var1, StructureBoundingBox var2, EnumDirection var3) {
         super(WorldGenFeatureStructurePieceType.z, var0, var2);
         this.a(var3);
         this.h = this.b(var1);
      }

      public h(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.z, var0);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         this.a((StrongholdPieces.m)var0, var1, var2, 1, 1);
      }

      public static StrongholdPieces.h a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -1, 0, 9, 5, 11, var5);
         return a(var7) && var0.a(var7) == null ? new StrongholdPieces.h(var6, var1, var7, var5) : null;
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
         this.a(var0, var4, 0, 0, 0, 8, 4, 10, true, var3, StrongholdPieces.k);
         this.a(var0, var3, var4, this.h, 1, 1, 0);
         this.a(var0, var4, 1, 1, 10, 3, 3, 10, e, e, false);
         this.a(var0, var4, 4, 1, 1, 4, 3, 1, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 4, 1, 3, 4, 3, 3, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 4, 1, 7, 4, 3, 7, false, var3, StrongholdPieces.k);
         this.a(var0, var4, 4, 1, 9, 4, 3, 9, false, var3, StrongholdPieces.k);

         for(int var7 = 1; var7 <= 3; ++var7) {
            this.a(var0, Blocks.eW.o().a(BlockIronBars.a, Boolean.valueOf(true)).a(BlockIronBars.c, Boolean.valueOf(true)), 4, var7, 4, var4);
            this.a(
               var0,
               Blocks.eW.o().a(BlockIronBars.a, Boolean.valueOf(true)).a(BlockIronBars.c, Boolean.valueOf(true)).a(BlockIronBars.b, Boolean.valueOf(true)),
               4,
               var7,
               5,
               var4
            );
            this.a(var0, Blocks.eW.o().a(BlockIronBars.a, Boolean.valueOf(true)).a(BlockIronBars.c, Boolean.valueOf(true)), 4, var7, 6, var4);
            this.a(var0, Blocks.eW.o().a(BlockIronBars.d, Boolean.valueOf(true)).a(BlockIronBars.b, Boolean.valueOf(true)), 5, var7, 5, var4);
            this.a(var0, Blocks.eW.o().a(BlockIronBars.d, Boolean.valueOf(true)).a(BlockIronBars.b, Boolean.valueOf(true)), 6, var7, 5, var4);
            this.a(var0, Blocks.eW.o().a(BlockIronBars.d, Boolean.valueOf(true)).a(BlockIronBars.b, Boolean.valueOf(true)), 7, var7, 5, var4);
         }

         this.a(var0, Blocks.eW.o().a(BlockIronBars.a, Boolean.valueOf(true)).a(BlockIronBars.c, Boolean.valueOf(true)), 4, 3, 2, var4);
         this.a(var0, Blocks.eW.o().a(BlockIronBars.a, Boolean.valueOf(true)).a(BlockIronBars.c, Boolean.valueOf(true)), 4, 3, 8, var4);
         IBlockData var7 = Blocks.dx.o().a(BlockDoor.a, EnumDirection.e);
         IBlockData var8 = Blocks.dx.o().a(BlockDoor.a, EnumDirection.e).a(BlockDoor.e, BlockPropertyDoubleBlockHalf.a);
         this.a(var0, var7, 4, 1, 2, var4);
         this.a(var0, var8, 4, 2, 2, var4);
         this.a(var0, var7, 4, 1, 8, var4);
         this.a(var0, var8, 4, 2, 8, var4);
      }
   }

   public static class i extends StrongholdPieces.q {
      public i(int var0, RandomSource var1, StructureBoundingBox var2, EnumDirection var3) {
         super(WorldGenFeatureStructurePieceType.A, var0, var2);
         this.a(var3);
         this.h = this.b(var1);
      }

      public i(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.A, var0);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         EnumDirection var3 = this.i();
         if (var3 != EnumDirection.c && var3 != EnumDirection.f) {
            this.b((StrongholdPieces.m)var0, var1, var2, 1, 1);
         } else {
            this.c((StrongholdPieces.m)var0, var1, var2, 1, 1);
         }
      }

      public static StrongholdPieces.i a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -1, 0, 5, 5, 5, var5);
         return a(var7) && var0.a(var7) == null ? new StrongholdPieces.i(var6, var1, var7, var5) : null;
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
         this.a(var0, var4, 0, 0, 0, 4, 4, 4, true, var3, StrongholdPieces.k);
         this.a(var0, var3, var4, this.h, 1, 1, 0);
         EnumDirection var7 = this.i();
         if (var7 != EnumDirection.c && var7 != EnumDirection.f) {
            this.a(var0, var4, 0, 1, 1, 0, 3, 3, e, e, false);
         } else {
            this.a(var0, var4, 4, 1, 1, 4, 3, 3, e, e, false);
         }
      }
   }

   public static class j extends StrongholdPieces.p {
      protected static final int a = 11;
      protected static final int b = 7;
      protected static final int c = 11;
      protected final int d;

      public j(int var0, RandomSource var1, StructureBoundingBox var2, EnumDirection var3) {
         super(WorldGenFeatureStructurePieceType.B, var0, var2);
         this.a(var3);
         this.h = this.b(var1);
         this.d = var1.a(5);
      }

      public j(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.B, var0);
         this.d = var0.h("Type");
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("Type", this.d);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         this.a((StrongholdPieces.m)var0, var1, var2, 4, 1);
         this.b((StrongholdPieces.m)var0, var1, var2, 1, 4);
         this.c((StrongholdPieces.m)var0, var1, var2, 1, 4);
      }

      public static StrongholdPieces.j a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -4, -1, 0, 11, 7, 11, var5);
         return a(var7) && var0.a(var7) == null ? new StrongholdPieces.j(var6, var1, var7, var5) : null;
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
         this.a(var0, var4, 0, 0, 0, 10, 6, 10, true, var3, StrongholdPieces.k);
         this.a(var0, var3, var4, this.h, 4, 1, 0);
         this.a(var0, var4, 4, 1, 10, 6, 3, 10, e, e, false);
         this.a(var0, var4, 0, 1, 4, 0, 3, 6, e, e, false);
         this.a(var0, var4, 10, 1, 4, 10, 3, 6, e, e, false);
         switch(this.d) {
            case 0:
               this.a(var0, Blocks.eH.o(), 5, 1, 5, var4);
               this.a(var0, Blocks.eH.o(), 5, 2, 5, var4);
               this.a(var0, Blocks.eH.o(), 5, 3, 5, var4);
               this.a(var0, Blocks.cp.o().a(BlockTorchWall.a, EnumDirection.e), 4, 3, 5, var4);
               this.a(var0, Blocks.cp.o().a(BlockTorchWall.a, EnumDirection.f), 6, 3, 5, var4);
               this.a(var0, Blocks.cp.o().a(BlockTorchWall.a, EnumDirection.d), 5, 3, 4, var4);
               this.a(var0, Blocks.cp.o().a(BlockTorchWall.a, EnumDirection.c), 5, 3, 6, var4);
               this.a(var0, Blocks.jD.o(), 4, 1, 4, var4);
               this.a(var0, Blocks.jD.o(), 4, 1, 5, var4);
               this.a(var0, Blocks.jD.o(), 4, 1, 6, var4);
               this.a(var0, Blocks.jD.o(), 6, 1, 4, var4);
               this.a(var0, Blocks.jD.o(), 6, 1, 5, var4);
               this.a(var0, Blocks.jD.o(), 6, 1, 6, var4);
               this.a(var0, Blocks.jD.o(), 5, 1, 4, var4);
               this.a(var0, Blocks.jD.o(), 5, 1, 6, var4);
               break;
            case 1:
               for(int var7 = 0; var7 < 5; ++var7) {
                  this.a(var0, Blocks.eH.o(), 3, 1, 3 + var7, var4);
                  this.a(var0, Blocks.eH.o(), 7, 1, 3 + var7, var4);
                  this.a(var0, Blocks.eH.o(), 3 + var7, 1, 3, var4);
                  this.a(var0, Blocks.eH.o(), 3 + var7, 1, 7, var4);
               }

               this.a(var0, Blocks.eH.o(), 5, 1, 5, var4);
               this.a(var0, Blocks.eH.o(), 5, 2, 5, var4);
               this.a(var0, Blocks.eH.o(), 5, 3, 5, var4);
               this.a(var0, Blocks.G.o(), 5, 4, 5, var4);
               break;
            case 2:
               for(int var7 = 1; var7 <= 9; ++var7) {
                  this.a(var0, Blocks.m.o(), 1, 3, var7, var4);
                  this.a(var0, Blocks.m.o(), 9, 3, var7, var4);
               }

               for(int var7 = 1; var7 <= 9; ++var7) {
                  this.a(var0, Blocks.m.o(), var7, 3, 1, var4);
                  this.a(var0, Blocks.m.o(), var7, 3, 9, var4);
               }

               this.a(var0, Blocks.m.o(), 5, 1, 4, var4);
               this.a(var0, Blocks.m.o(), 5, 1, 6, var4);
               this.a(var0, Blocks.m.o(), 5, 3, 4, var4);
               this.a(var0, Blocks.m.o(), 5, 3, 6, var4);
               this.a(var0, Blocks.m.o(), 4, 1, 5, var4);
               this.a(var0, Blocks.m.o(), 6, 1, 5, var4);
               this.a(var0, Blocks.m.o(), 4, 3, 5, var4);
               this.a(var0, Blocks.m.o(), 6, 3, 5, var4);

               for(int var7 = 1; var7 <= 3; ++var7) {
                  this.a(var0, Blocks.m.o(), 4, var7, 4, var4);
                  this.a(var0, Blocks.m.o(), 6, var7, 4, var4);
                  this.a(var0, Blocks.m.o(), 4, var7, 6, var4);
                  this.a(var0, Blocks.m.o(), 6, var7, 6, var4);
               }

               this.a(var0, Blocks.co.o(), 5, 3, 5, var4);

               for(int var7 = 2; var7 <= 8; ++var7) {
                  this.a(var0, Blocks.n.o(), 2, 3, var7, var4);
                  this.a(var0, Blocks.n.o(), 3, 3, var7, var4);
                  if (var7 <= 3 || var7 >= 7) {
                     this.a(var0, Blocks.n.o(), 4, 3, var7, var4);
                     this.a(var0, Blocks.n.o(), 5, 3, var7, var4);
                     this.a(var0, Blocks.n.o(), 6, 3, var7, var4);
                  }

                  this.a(var0, Blocks.n.o(), 7, 3, var7, var4);
                  this.a(var0, Blocks.n.o(), 8, 3, var7, var4);
               }

               IBlockData var7 = Blocks.cN.o().a(BlockLadder.a, EnumDirection.e);
               this.a(var0, var7, 9, 1, 3, var4);
               this.a(var0, var7, 9, 2, 3, var4);
               this.a(var0, var7, 9, 3, 3, var4);
               this.a(var0, var4, var3, 3, 4, 8, LootTables.x);
         }
      }
   }

   static class k extends StructurePiece.StructurePieceBlockSelector {
      @Override
      public void a(RandomSource var0, int var1, int var2, int var3, boolean var4) {
         if (var4) {
            float var5 = var0.i();
            if (var5 < 0.2F) {
               this.a = Blocks.eJ.o();
            } else if (var5 < 0.5F) {
               this.a = Blocks.eI.o();
            } else if (var5 < 0.55F) {
               this.a = Blocks.eP.o();
            } else {
               this.a = Blocks.eH.o();
            }
         } else {
            this.a = Blocks.mY.o();
         }
      }
   }

   public static class l extends StrongholdPieces.p {
      private static final int a = 5;
      private static final int b = 11;
      private static final int c = 5;
      private final boolean d;

      public l(WorldGenFeatureStructurePieceType var0, int var1, int var2, int var3, EnumDirection var4) {
         super(var0, var1, a(var2, 64, var3, var4, 5, 11, 5));
         this.d = true;
         this.a(var4);
         this.h = StrongholdPieces.p.a.a;
      }

      public l(int var0, RandomSource var1, StructureBoundingBox var2, EnumDirection var3) {
         super(WorldGenFeatureStructurePieceType.C, var0, var2);
         this.d = false;
         this.a(var3);
         this.h = this.b(var1);
      }

      public l(WorldGenFeatureStructurePieceType var0, NBTTagCompound var1) {
         super(var0, var1);
         this.d = var1.q("Source");
      }

      public l(NBTTagCompound var0) {
         this(WorldGenFeatureStructurePieceType.C, var0);
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("Source", this.d);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         if (this.d) {
            StrongholdPieces.i = StrongholdPieces.c.class;
         }

         this.a((StrongholdPieces.m)var0, var1, var2, 1, 1);
      }

      public static StrongholdPieces.l a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -7, 0, 5, 11, 5, var5);
         return a(var7) && var0.a(var7) == null ? new StrongholdPieces.l(var6, var1, var7, var5) : null;
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
         this.a(var0, var4, 0, 0, 0, 4, 10, 4, true, var3, StrongholdPieces.k);
         this.a(var0, var3, var4, this.h, 1, 7, 0);
         this.a(var0, var3, var4, StrongholdPieces.p.a.a, 1, 1, 4);
         this.a(var0, Blocks.eH.o(), 2, 6, 1, var4);
         this.a(var0, Blocks.eH.o(), 1, 5, 1, var4);
         this.a(var0, Blocks.jD.o(), 1, 6, 1, var4);
         this.a(var0, Blocks.eH.o(), 1, 5, 2, var4);
         this.a(var0, Blocks.eH.o(), 1, 4, 3, var4);
         this.a(var0, Blocks.jD.o(), 1, 5, 3, var4);
         this.a(var0, Blocks.eH.o(), 2, 4, 3, var4);
         this.a(var0, Blocks.eH.o(), 3, 3, 3, var4);
         this.a(var0, Blocks.jD.o(), 3, 4, 3, var4);
         this.a(var0, Blocks.eH.o(), 3, 3, 2, var4);
         this.a(var0, Blocks.eH.o(), 3, 2, 1, var4);
         this.a(var0, Blocks.jD.o(), 3, 3, 1, var4);
         this.a(var0, Blocks.eH.o(), 2, 2, 1, var4);
         this.a(var0, Blocks.eH.o(), 1, 1, 1, var4);
         this.a(var0, Blocks.jD.o(), 1, 2, 1, var4);
         this.a(var0, Blocks.eH.o(), 1, 1, 2, var4);
         this.a(var0, Blocks.jD.o(), 1, 1, 3, var4);
      }
   }

   public static class m extends StrongholdPieces.l {
      public StrongholdPieces.f a;
      @Nullable
      public StrongholdPieces.g b;
      public final List<StructurePiece> c = Lists.newArrayList();

      public m(RandomSource var0, int var1, int var2) {
         super(WorldGenFeatureStructurePieceType.D, 0, var1, var2, a(var0));
      }

      public m(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.D, var0);
      }

      @Override
      public BlockPosition h() {
         return this.b != null ? this.b.h() : super.h();
      }
   }

   public static class n extends StrongholdPieces.p {
      private static final int a = 5;
      private static final int b = 5;
      private static final int c = 7;
      private final boolean d;
      private final boolean i;

      public n(int var0, RandomSource var1, StructureBoundingBox var2, EnumDirection var3) {
         super(WorldGenFeatureStructurePieceType.E, var0, var2);
         this.a(var3);
         this.h = this.b(var1);
         this.d = var1.a(2) == 0;
         this.i = var1.a(2) == 0;
      }

      public n(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.E, var0);
         this.d = var0.q("Left");
         this.i = var0.q("Right");
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("Left", this.d);
         var1.a("Right", this.i);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         this.a((StrongholdPieces.m)var0, var1, var2, 1, 1);
         if (this.d) {
            this.b((StrongholdPieces.m)var0, var1, var2, 1, 2);
         }

         if (this.i) {
            this.c((StrongholdPieces.m)var0, var1, var2, 1, 2);
         }
      }

      public static StrongholdPieces.n a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -1, 0, 5, 5, 7, var5);
         return a(var7) && var0.a(var7) == null ? new StrongholdPieces.n(var6, var1, var7, var5) : null;
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
         this.a(var0, var4, 0, 0, 0, 4, 4, 6, true, var3, StrongholdPieces.k);
         this.a(var0, var3, var4, this.h, 1, 1, 0);
         this.a(var0, var3, var4, StrongholdPieces.p.a.a, 1, 1, 6);
         IBlockData var7 = Blocks.cp.o().a(BlockTorchWall.a, EnumDirection.f);
         IBlockData var8 = Blocks.cp.o().a(BlockTorchWall.a, EnumDirection.e);
         this.a(var0, var4, var3, 0.1F, 1, 2, 1, var7);
         this.a(var0, var4, var3, 0.1F, 3, 2, 1, var8);
         this.a(var0, var4, var3, 0.1F, 1, 2, 5, var7);
         this.a(var0, var4, var3, 0.1F, 3, 2, 5, var8);
         if (this.d) {
            this.a(var0, var4, 0, 1, 2, 0, 3, 4, e, e, false);
         }

         if (this.i) {
            this.a(var0, var4, 4, 1, 2, 4, 3, 4, e, e, false);
         }
      }
   }

   public static class o extends StrongholdPieces.p {
      private static final int a = 5;
      private static final int b = 11;
      private static final int c = 8;

      public o(int var0, RandomSource var1, StructureBoundingBox var2, EnumDirection var3) {
         super(WorldGenFeatureStructurePieceType.F, var0, var2);
         this.a(var3);
         this.h = this.b(var1);
      }

      public o(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.F, var0);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         this.a((StrongholdPieces.m)var0, var1, var2, 1, 1);
      }

      public static StrongholdPieces.o a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -7, 0, 5, 11, 8, var5);
         return a(var7) && var0.a(var7) == null ? new StrongholdPieces.o(var6, var1, var7, var5) : null;
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
         this.a(var0, var4, 0, 0, 0, 4, 10, 7, true, var3, StrongholdPieces.k);
         this.a(var0, var3, var4, this.h, 1, 7, 0);
         this.a(var0, var3, var4, StrongholdPieces.p.a.a, 1, 1, 7);
         IBlockData var7 = Blocks.cP.o().a(BlockStairs.a, EnumDirection.d);

         for(int var8 = 0; var8 < 6; ++var8) {
            this.a(var0, var7, 1, 6 - var8, 1 + var8, var4);
            this.a(var0, var7, 2, 6 - var8, 1 + var8, var4);
            this.a(var0, var7, 3, 6 - var8, 1 + var8, var4);
            if (var8 < 5) {
               this.a(var0, Blocks.eH.o(), 1, 5 - var8, 1 + var8, var4);
               this.a(var0, Blocks.eH.o(), 2, 5 - var8, 1 + var8, var4);
               this.a(var0, Blocks.eH.o(), 3, 5 - var8, 1 + var8, var4);
            }
         }
      }
   }

   abstract static class p extends StructurePiece {
      protected StrongholdPieces.p.a h = StrongholdPieces.p.a.a;

      protected p(WorldGenFeatureStructurePieceType var0, int var1, StructureBoundingBox var2) {
         super(var0, var1, var2);
      }

      public p(WorldGenFeatureStructurePieceType var0, NBTTagCompound var1) {
         super(var0, var1);
         this.h = StrongholdPieces.p.a.valueOf(var1.l("EntryDoor"));
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         var1.a("EntryDoor", this.h.name());
      }

      protected void a(GeneratorAccessSeed var0, RandomSource var1, StructureBoundingBox var2, StrongholdPieces.p.a var3, int var4, int var5, int var6) {
         switch(var3) {
            case a:
               this.a(var0, var2, var4, var5, var6, var4 + 3 - 1, var5 + 3 - 1, var6, e, e, false);
               break;
            case b:
               this.a(var0, Blocks.eH.o(), var4, var5, var6, var2);
               this.a(var0, Blocks.eH.o(), var4, var5 + 1, var6, var2);
               this.a(var0, Blocks.eH.o(), var4, var5 + 2, var6, var2);
               this.a(var0, Blocks.eH.o(), var4 + 1, var5 + 2, var6, var2);
               this.a(var0, Blocks.eH.o(), var4 + 2, var5 + 2, var6, var2);
               this.a(var0, Blocks.eH.o(), var4 + 2, var5 + 1, var6, var2);
               this.a(var0, Blocks.eH.o(), var4 + 2, var5, var6, var2);
               this.a(var0, Blocks.cM.o(), var4 + 1, var5, var6, var2);
               this.a(var0, Blocks.cM.o().a(BlockDoor.e, BlockPropertyDoubleBlockHalf.a), var4 + 1, var5 + 1, var6, var2);
               break;
            case c:
               this.a(var0, Blocks.mY.o(), var4 + 1, var5, var6, var2);
               this.a(var0, Blocks.mY.o(), var4 + 1, var5 + 1, var6, var2);
               this.a(var0, Blocks.eW.o().a(BlockIronBars.d, Boolean.valueOf(true)), var4, var5, var6, var2);
               this.a(var0, Blocks.eW.o().a(BlockIronBars.d, Boolean.valueOf(true)), var4, var5 + 1, var6, var2);
               this.a(var0, Blocks.eW.o().a(BlockIronBars.b, Boolean.valueOf(true)).a(BlockIronBars.d, Boolean.valueOf(true)), var4, var5 + 2, var6, var2);
               this.a(var0, Blocks.eW.o().a(BlockIronBars.b, Boolean.valueOf(true)).a(BlockIronBars.d, Boolean.valueOf(true)), var4 + 1, var5 + 2, var6, var2);
               this.a(var0, Blocks.eW.o().a(BlockIronBars.b, Boolean.valueOf(true)).a(BlockIronBars.d, Boolean.valueOf(true)), var4 + 2, var5 + 2, var6, var2);
               this.a(var0, Blocks.eW.o().a(BlockIronBars.b, Boolean.valueOf(true)), var4 + 2, var5 + 1, var6, var2);
               this.a(var0, Blocks.eW.o().a(BlockIronBars.b, Boolean.valueOf(true)), var4 + 2, var5, var6, var2);
               break;
            case d:
               this.a(var0, Blocks.eH.o(), var4, var5, var6, var2);
               this.a(var0, Blocks.eH.o(), var4, var5 + 1, var6, var2);
               this.a(var0, Blocks.eH.o(), var4, var5 + 2, var6, var2);
               this.a(var0, Blocks.eH.o(), var4 + 1, var5 + 2, var6, var2);
               this.a(var0, Blocks.eH.o(), var4 + 2, var5 + 2, var6, var2);
               this.a(var0, Blocks.eH.o(), var4 + 2, var5 + 1, var6, var2);
               this.a(var0, Blocks.eH.o(), var4 + 2, var5, var6, var2);
               this.a(var0, Blocks.dx.o(), var4 + 1, var5, var6, var2);
               this.a(var0, Blocks.dx.o().a(BlockDoor.e, BlockPropertyDoubleBlockHalf.a), var4 + 1, var5 + 1, var6, var2);
               this.a(var0, Blocks.dL.o().a(BlockButtonAbstract.aD, EnumDirection.c), var4 + 2, var5 + 1, var6 + 1, var2);
               this.a(var0, Blocks.dL.o().a(BlockButtonAbstract.aD, EnumDirection.d), var4 + 2, var5 + 1, var6 - 1, var2);
         }
      }

      protected StrongholdPieces.p.a b(RandomSource var0) {
         int var1 = var0.a(5);
         switch(var1) {
            case 0:
            case 1:
            default:
               return StrongholdPieces.p.a.a;
            case 2:
               return StrongholdPieces.p.a.b;
            case 3:
               return StrongholdPieces.p.a.c;
            case 4:
               return StrongholdPieces.p.a.d;
         }
      }

      @Nullable
      protected StructurePiece a(StrongholdPieces.m var0, StructurePieceAccessor var1, RandomSource var2, int var3, int var4) {
         EnumDirection var5 = this.i();
         if (var5 != null) {
            switch(var5) {
               case c:
                  return StrongholdPieces.b(var0, var1, var2, this.f.g() + var3, this.f.h() + var4, this.f.i() - 1, var5, this.g());
               case d:
                  return StrongholdPieces.b(var0, var1, var2, this.f.g() + var3, this.f.h() + var4, this.f.l() + 1, var5, this.g());
               case e:
                  return StrongholdPieces.b(var0, var1, var2, this.f.g() - 1, this.f.h() + var4, this.f.i() + var3, var5, this.g());
               case f:
                  return StrongholdPieces.b(var0, var1, var2, this.f.j() + 1, this.f.h() + var4, this.f.i() + var3, var5, this.g());
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece b(StrongholdPieces.m var0, StructurePieceAccessor var1, RandomSource var2, int var3, int var4) {
         EnumDirection var5 = this.i();
         if (var5 != null) {
            switch(var5) {
               case c:
                  return StrongholdPieces.b(var0, var1, var2, this.f.g() - 1, this.f.h() + var3, this.f.i() + var4, EnumDirection.e, this.g());
               case d:
                  return StrongholdPieces.b(var0, var1, var2, this.f.g() - 1, this.f.h() + var3, this.f.i() + var4, EnumDirection.e, this.g());
               case e:
                  return StrongholdPieces.b(var0, var1, var2, this.f.g() + var4, this.f.h() + var3, this.f.i() - 1, EnumDirection.c, this.g());
               case f:
                  return StrongholdPieces.b(var0, var1, var2, this.f.g() + var4, this.f.h() + var3, this.f.i() - 1, EnumDirection.c, this.g());
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece c(StrongholdPieces.m var0, StructurePieceAccessor var1, RandomSource var2, int var3, int var4) {
         EnumDirection var5 = this.i();
         if (var5 != null) {
            switch(var5) {
               case c:
                  return StrongholdPieces.b(var0, var1, var2, this.f.j() + 1, this.f.h() + var3, this.f.i() + var4, EnumDirection.f, this.g());
               case d:
                  return StrongholdPieces.b(var0, var1, var2, this.f.j() + 1, this.f.h() + var3, this.f.i() + var4, EnumDirection.f, this.g());
               case e:
                  return StrongholdPieces.b(var0, var1, var2, this.f.g() + var4, this.f.h() + var3, this.f.l() + 1, EnumDirection.d, this.g());
               case f:
                  return StrongholdPieces.b(var0, var1, var2, this.f.g() + var4, this.f.h() + var3, this.f.l() + 1, EnumDirection.d, this.g());
            }
         }

         return null;
      }

      protected static boolean a(StructureBoundingBox var0) {
         return var0 != null && var0.h() > 10;
      }

      protected static enum a {
         a,
         b,
         c,
         d;
      }
   }

   public abstract static class q extends StrongholdPieces.p {
      protected static final int a = 5;
      protected static final int b = 5;
      protected static final int c = 5;

      protected q(WorldGenFeatureStructurePieceType var0, int var1, StructureBoundingBox var2) {
         super(var0, var1, var2);
      }

      public q(WorldGenFeatureStructurePieceType var0, NBTTagCompound var1) {
         super(var0, var1);
      }
   }
}
