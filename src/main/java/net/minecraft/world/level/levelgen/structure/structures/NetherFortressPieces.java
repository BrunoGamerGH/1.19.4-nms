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
import net.minecraft.world.level.block.BlockFence;
import net.minecraft.world.level.block.BlockStairs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityMobSpawner;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;
import net.minecraft.world.level.material.FluidTypes;
import net.minecraft.world.level.storage.loot.LootTables;

public class NetherFortressPieces {
   private static final int b = 30;
   private static final int c = 10;
   public static final int a = 64;
   static final NetherFortressPieces.n[] d = new NetherFortressPieces.n[]{
      new NetherFortressPieces.n(NetherFortressPieces.c.class, 30, 0, true),
      new NetherFortressPieces.n(NetherFortressPieces.a.class, 10, 4),
      new NetherFortressPieces.n(NetherFortressPieces.o.class, 10, 4),
      new NetherFortressPieces.n(NetherFortressPieces.p.class, 10, 3),
      new NetherFortressPieces.n(NetherFortressPieces.l.class, 5, 2),
      new NetherFortressPieces.n(NetherFortressPieces.f.class, 5, 1)
   };
   static final NetherFortressPieces.n[] e = new NetherFortressPieces.n[]{
      new NetherFortressPieces.n(NetherFortressPieces.i.class, 25, 0, true),
      new NetherFortressPieces.n(NetherFortressPieces.g.class, 15, 5),
      new NetherFortressPieces.n(NetherFortressPieces.j.class, 5, 10),
      new NetherFortressPieces.n(NetherFortressPieces.h.class, 5, 10),
      new NetherFortressPieces.n(NetherFortressPieces.d.class, 10, 3, true),
      new NetherFortressPieces.n(NetherFortressPieces.e.class, 7, 2),
      new NetherFortressPieces.n(NetherFortressPieces.k.class, 5, 2)
   };

   static NetherFortressPieces.m a(
      NetherFortressPieces.n var0, StructurePieceAccessor var1, RandomSource var2, int var3, int var4, int var5, EnumDirection var6, int var7
   ) {
      Class<? extends NetherFortressPieces.m> var8 = var0.a;
      NetherFortressPieces.m var9 = null;
      if (var8 == NetherFortressPieces.c.class) {
         var9 = NetherFortressPieces.c.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var8 == NetherFortressPieces.a.class) {
         var9 = NetherFortressPieces.a.a(var1, var3, var4, var5, var6, var7);
      } else if (var8 == NetherFortressPieces.o.class) {
         var9 = NetherFortressPieces.o.a(var1, var3, var4, var5, var6, var7);
      } else if (var8 == NetherFortressPieces.p.class) {
         var9 = NetherFortressPieces.p.a(var1, var3, var4, var5, var7, var6);
      } else if (var8 == NetherFortressPieces.l.class) {
         var9 = NetherFortressPieces.l.a(var1, var3, var4, var5, var7, var6);
      } else if (var8 == NetherFortressPieces.f.class) {
         var9 = NetherFortressPieces.f.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var8 == NetherFortressPieces.i.class) {
         var9 = NetherFortressPieces.i.a(var1, var3, var4, var5, var6, var7);
      } else if (var8 == NetherFortressPieces.j.class) {
         var9 = NetherFortressPieces.j.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var8 == NetherFortressPieces.h.class) {
         var9 = NetherFortressPieces.h.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var8 == NetherFortressPieces.d.class) {
         var9 = NetherFortressPieces.d.a(var1, var3, var4, var5, var6, var7);
      } else if (var8 == NetherFortressPieces.e.class) {
         var9 = NetherFortressPieces.e.a(var1, var3, var4, var5, var6, var7);
      } else if (var8 == NetherFortressPieces.g.class) {
         var9 = NetherFortressPieces.g.a(var1, var3, var4, var5, var6, var7);
      } else if (var8 == NetherFortressPieces.k.class) {
         var9 = NetherFortressPieces.k.a(var1, var3, var4, var5, var6, var7);
      }

      return var9;
   }

   public static class a extends NetherFortressPieces.m {
      private static final int a = 19;
      private static final int b = 10;
      private static final int c = 19;

      public a(int var0, StructureBoundingBox var1, EnumDirection var2) {
         super(WorldGenFeatureStructurePieceType.e, var0, var1);
         this.a(var2);
      }

      protected a(int var0, int var1, EnumDirection var2) {
         super(WorldGenFeatureStructurePieceType.e, 0, StructurePiece.a(var0, 64, var1, var2, 19, 10, 19));
         this.a(var2);
      }

      protected a(WorldGenFeatureStructurePieceType var0, NBTTagCompound var1) {
         super(var0, var1);
      }

      public a(NBTTagCompound var0) {
         this(WorldGenFeatureStructurePieceType.e, var0);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         this.a((NetherFortressPieces.q)var0, var1, var2, 8, 3, false);
         this.b((NetherFortressPieces.q)var0, var1, var2, 3, 8, false);
         this.c((NetherFortressPieces.q)var0, var1, var2, 3, 8, false);
      }

      public static NetherFortressPieces.a a(StructurePieceAccessor var0, int var1, int var2, int var3, EnumDirection var4, int var5) {
         StructureBoundingBox var6 = StructureBoundingBox.a(var1, var2, var3, -8, -3, 0, 19, 10, 19, var4);
         return a(var6) && var0.a(var6) == null ? new NetherFortressPieces.a(var5, var6, var4) : null;
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
         this.a(var0, var4, 7, 3, 0, 11, 4, 18, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 3, 7, 18, 4, 11, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 8, 5, 0, 10, 7, 18, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, 0, 5, 8, 18, 7, 10, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, 7, 5, 0, 7, 5, 7, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 7, 5, 11, 7, 5, 18, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 11, 5, 0, 11, 5, 7, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 11, 5, 11, 11, 5, 18, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 5, 7, 7, 5, 7, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 11, 5, 7, 18, 5, 7, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 5, 11, 7, 5, 11, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 11, 5, 11, 18, 5, 11, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 7, 2, 0, 11, 2, 5, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 7, 2, 13, 11, 2, 18, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 7, 0, 0, 11, 1, 3, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 7, 0, 15, 11, 1, 18, Blocks.fm.o(), Blocks.fm.o(), false);

         for(int var7 = 7; var7 <= 11; ++var7) {
            for(int var8 = 0; var8 <= 2; ++var8) {
               this.b(var0, Blocks.fm.o(), var7, -1, var8, var4);
               this.b(var0, Blocks.fm.o(), var7, -1, 18 - var8, var4);
            }
         }

         this.a(var0, var4, 0, 2, 7, 5, 2, 11, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 13, 2, 7, 18, 2, 11, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 0, 7, 3, 1, 11, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 15, 0, 7, 18, 1, 11, Blocks.fm.o(), Blocks.fm.o(), false);

         for(int var7 = 0; var7 <= 2; ++var7) {
            for(int var8 = 7; var8 <= 11; ++var8) {
               this.b(var0, Blocks.fm.o(), var7, -1, var8, var4);
               this.b(var0, Blocks.fm.o(), 18 - var7, -1, var8, var4);
            }
         }
      }
   }

   public static class b extends NetherFortressPieces.m {
      private static final int a = 5;
      private static final int b = 10;
      private static final int c = 8;
      private final int d;

      public b(int var0, RandomSource var1, StructureBoundingBox var2, EnumDirection var3) {
         super(WorldGenFeatureStructurePieceType.f, var0, var2);
         this.a(var3);
         this.d = var1.f();
      }

      public b(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.f, var0);
         this.d = var0.h("Seed");
      }

      public static NetherFortressPieces.b a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -3, 0, 5, 10, 8, var5);
         return a(var7) && var0.a(var7) == null ? new NetherFortressPieces.b(var6, var1, var7, var5) : null;
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("Seed", this.d);
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
         RandomSource var7 = RandomSource.a((long)this.d);

         for(int var8 = 0; var8 <= 4; ++var8) {
            for(int var9 = 3; var9 <= 4; ++var9) {
               int var10 = var7.a(8);
               this.a(var0, var4, var8, var9, 0, var8, var9, var10, Blocks.fm.o(), Blocks.fm.o(), false);
            }
         }

         int var8 = var7.a(8);
         this.a(var0, var4, 0, 5, 0, 0, 5, var8, Blocks.fm.o(), Blocks.fm.o(), false);
         var8 = var7.a(8);
         this.a(var0, var4, 4, 5, 0, 4, 5, var8, Blocks.fm.o(), Blocks.fm.o(), false);

         for(int var8x = 0; var8x <= 4; ++var8x) {
            int var9 = var7.a(5);
            this.a(var0, var4, var8x, 2, 0, var8x, 2, var9, Blocks.fm.o(), Blocks.fm.o(), false);
         }

         for(int var8x = 0; var8x <= 4; ++var8x) {
            for(int var9 = 0; var9 <= 1; ++var9) {
               int var10 = var7.a(3);
               this.a(var0, var4, var8x, var9, 0, var8x, var9, var10, Blocks.fm.o(), Blocks.fm.o(), false);
            }
         }
      }
   }

   public static class c extends NetherFortressPieces.m {
      private static final int a = 5;
      private static final int b = 10;
      private static final int c = 19;

      public c(int var0, RandomSource var1, StructureBoundingBox var2, EnumDirection var3) {
         super(WorldGenFeatureStructurePieceType.g, var0, var2);
         this.a(var3);
      }

      public c(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.g, var0);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         this.a((NetherFortressPieces.q)var0, var1, var2, 1, 3, false);
      }

      public static NetherFortressPieces.c a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -3, 0, 5, 10, 19, var5);
         return a(var7) && var0.a(var7) == null ? new NetherFortressPieces.c(var6, var1, var7, var5) : null;
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
         this.a(var0, var4, 0, 3, 0, 4, 4, 18, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 1, 5, 0, 3, 7, 18, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, 0, 5, 0, 0, 5, 18, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 4, 5, 0, 4, 5, 18, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 0, 4, 2, 5, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 13, 4, 2, 18, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 0, 0, 4, 1, 3, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 0, 15, 4, 1, 18, Blocks.fm.o(), Blocks.fm.o(), false);

         for(int var7 = 0; var7 <= 4; ++var7) {
            for(int var8 = 0; var8 <= 2; ++var8) {
               this.b(var0, Blocks.fm.o(), var7, -1, var8, var4);
               this.b(var0, Blocks.fm.o(), var7, -1, 18 - var8, var4);
            }
         }

         IBlockData var7 = Blocks.fn.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.c, Boolean.valueOf(true));
         IBlockData var8 = var7.a(BlockFence.b, Boolean.valueOf(true));
         IBlockData var9 = var7.a(BlockFence.d, Boolean.valueOf(true));
         this.a(var0, var4, 0, 1, 1, 0, 4, 1, var8, var8, false);
         this.a(var0, var4, 0, 3, 4, 0, 4, 4, var8, var8, false);
         this.a(var0, var4, 0, 3, 14, 0, 4, 14, var8, var8, false);
         this.a(var0, var4, 0, 1, 17, 0, 4, 17, var8, var8, false);
         this.a(var0, var4, 4, 1, 1, 4, 4, 1, var9, var9, false);
         this.a(var0, var4, 4, 3, 4, 4, 4, 4, var9, var9, false);
         this.a(var0, var4, 4, 3, 14, 4, 4, 14, var9, var9, false);
         this.a(var0, var4, 4, 1, 17, 4, 4, 17, var9, var9, false);
      }
   }

   public static class d extends NetherFortressPieces.m {
      private static final int a = 5;
      private static final int b = 14;
      private static final int c = 10;

      public d(int var0, StructureBoundingBox var1, EnumDirection var2) {
         super(WorldGenFeatureStructurePieceType.h, var0, var1);
         this.a(var2);
      }

      public d(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.h, var0);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         this.a((NetherFortressPieces.q)var0, var1, var2, 1, 0, true);
      }

      public static NetherFortressPieces.d a(StructurePieceAccessor var0, int var1, int var2, int var3, EnumDirection var4, int var5) {
         StructureBoundingBox var6 = StructureBoundingBox.a(var1, var2, var3, -1, -7, 0, 5, 14, 10, var4);
         return a(var6) && var0.a(var6) == null ? new NetherFortressPieces.d(var5, var6, var4) : null;
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
         IBlockData var7 = Blocks.fo.o().a(BlockStairs.a, EnumDirection.d);
         IBlockData var8 = Blocks.fn.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.c, Boolean.valueOf(true));

         for(int var9 = 0; var9 <= 9; ++var9) {
            int var10 = Math.max(1, 7 - var9);
            int var11 = Math.min(Math.max(var10 + 5, 14 - var9), 13);
            int var12 = var9;
            this.a(var0, var4, 0, 0, var9, 4, var10, var9, Blocks.fm.o(), Blocks.fm.o(), false);
            this.a(var0, var4, 1, var10 + 1, var9, 3, var11 - 1, var9, Blocks.a.o(), Blocks.a.o(), false);
            if (var9 <= 6) {
               this.a(var0, var7, 1, var10 + 1, var9, var4);
               this.a(var0, var7, 2, var10 + 1, var9, var4);
               this.a(var0, var7, 3, var10 + 1, var9, var4);
            }

            this.a(var0, var4, 0, var11, var9, 4, var11, var9, Blocks.fm.o(), Blocks.fm.o(), false);
            this.a(var0, var4, 0, var10 + 1, var9, 0, var11 - 1, var9, Blocks.fm.o(), Blocks.fm.o(), false);
            this.a(var0, var4, 4, var10 + 1, var9, 4, var11 - 1, var9, Blocks.fm.o(), Blocks.fm.o(), false);
            if ((var9 & 1) == 0) {
               this.a(var0, var4, 0, var10 + 2, var9, 0, var10 + 3, var9, var8, var8, false);
               this.a(var0, var4, 4, var10 + 2, var9, 4, var10 + 3, var9, var8, var8, false);
            }

            for(int var13 = 0; var13 <= 4; ++var13) {
               this.b(var0, Blocks.fm.o(), var13, -1, var12, var4);
            }
         }
      }
   }

   public static class e extends NetherFortressPieces.m {
      private static final int a = 9;
      private static final int b = 7;
      private static final int c = 9;

      public e(int var0, StructureBoundingBox var1, EnumDirection var2) {
         super(WorldGenFeatureStructurePieceType.i, var0, var1);
         this.a(var2);
      }

      public e(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.i, var0);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         int var3 = 1;
         EnumDirection var4 = this.i();
         if (var4 == EnumDirection.e || var4 == EnumDirection.c) {
            var3 = 5;
         }

         this.b((NetherFortressPieces.q)var0, var1, var2, 0, var3, var2.a(8) > 0);
         this.c((NetherFortressPieces.q)var0, var1, var2, 0, var3, var2.a(8) > 0);
      }

      public static NetherFortressPieces.e a(StructurePieceAccessor var0, int var1, int var2, int var3, EnumDirection var4, int var5) {
         StructureBoundingBox var6 = StructureBoundingBox.a(var1, var2, var3, -3, 0, 0, 9, 7, 9, var4);
         return a(var6) && var0.a(var6) == null ? new NetherFortressPieces.e(var5, var6, var4) : null;
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
         IBlockData var7 = Blocks.fn.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.c, Boolean.valueOf(true));
         IBlockData var8 = Blocks.fn.o().a(BlockFence.d, Boolean.valueOf(true)).a(BlockFence.b, Boolean.valueOf(true));
         this.a(var0, var4, 0, 0, 0, 8, 1, 8, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 0, 8, 5, 8, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, 0, 6, 0, 8, 6, 5, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 0, 2, 5, 0, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 6, 2, 0, 8, 5, 0, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 1, 3, 0, 1, 4, 0, var8, var8, false);
         this.a(var0, var4, 7, 3, 0, 7, 4, 0, var8, var8, false);
         this.a(var0, var4, 0, 2, 4, 8, 2, 8, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 1, 1, 4, 2, 2, 4, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, 6, 1, 4, 7, 2, 4, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, 1, 3, 8, 7, 3, 8, var8, var8, false);
         this.a(var0, Blocks.fn.o().a(BlockFence.b, Boolean.valueOf(true)).a(BlockFence.c, Boolean.valueOf(true)), 0, 3, 8, var4);
         this.a(var0, Blocks.fn.o().a(BlockFence.d, Boolean.valueOf(true)).a(BlockFence.c, Boolean.valueOf(true)), 8, 3, 8, var4);
         this.a(var0, var4, 0, 3, 6, 0, 3, 7, var7, var7, false);
         this.a(var0, var4, 8, 3, 6, 8, 3, 7, var7, var7, false);
         this.a(var0, var4, 0, 3, 4, 0, 5, 5, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 8, 3, 4, 8, 5, 5, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 1, 3, 5, 2, 5, 5, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 6, 3, 5, 7, 5, 5, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 1, 4, 5, 1, 5, 5, var8, var8, false);
         this.a(var0, var4, 7, 4, 5, 7, 5, 5, var8, var8, false);

         for(int var9 = 0; var9 <= 5; ++var9) {
            for(int var10 = 0; var10 <= 8; ++var10) {
               this.b(var0, Blocks.fm.o(), var10, -1, var9, var4);
            }
         }
      }
   }

   public static class f extends NetherFortressPieces.m {
      private static final int a = 13;
      private static final int b = 14;
      private static final int c = 13;

      public f(int var0, RandomSource var1, StructureBoundingBox var2, EnumDirection var3) {
         super(WorldGenFeatureStructurePieceType.j, var0, var2);
         this.a(var3);
      }

      public f(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.j, var0);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         this.a((NetherFortressPieces.q)var0, var1, var2, 5, 3, true);
      }

      public static NetherFortressPieces.f a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -5, -3, 0, 13, 14, 13, var5);
         return a(var7) && var0.a(var7) == null ? new NetherFortressPieces.f(var6, var1, var7, var5) : null;
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
         this.a(var0, var4, 0, 3, 0, 12, 4, 12, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 5, 0, 12, 13, 12, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, 0, 5, 0, 1, 12, 12, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 11, 5, 0, 12, 12, 12, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 2, 5, 11, 4, 12, 12, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 8, 5, 11, 10, 12, 12, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 5, 9, 11, 7, 12, 12, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 2, 5, 0, 4, 12, 1, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 8, 5, 0, 10, 12, 1, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 5, 9, 0, 7, 12, 1, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 2, 11, 2, 10, 12, 10, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 5, 8, 0, 7, 8, 0, Blocks.fn.o(), Blocks.fn.o(), false);
         IBlockData var7 = Blocks.fn.o().a(BlockFence.d, Boolean.valueOf(true)).a(BlockFence.b, Boolean.valueOf(true));
         IBlockData var8 = Blocks.fn.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.c, Boolean.valueOf(true));

         for(int var9 = 1; var9 <= 11; var9 += 2) {
            this.a(var0, var4, var9, 10, 0, var9, 11, 0, var7, var7, false);
            this.a(var0, var4, var9, 10, 12, var9, 11, 12, var7, var7, false);
            this.a(var0, var4, 0, 10, var9, 0, 11, var9, var8, var8, false);
            this.a(var0, var4, 12, 10, var9, 12, 11, var9, var8, var8, false);
            this.a(var0, Blocks.fm.o(), var9, 13, 0, var4);
            this.a(var0, Blocks.fm.o(), var9, 13, 12, var4);
            this.a(var0, Blocks.fm.o(), 0, 13, var9, var4);
            this.a(var0, Blocks.fm.o(), 12, 13, var9, var4);
            if (var9 != 11) {
               this.a(var0, var7, var9 + 1, 13, 0, var4);
               this.a(var0, var7, var9 + 1, 13, 12, var4);
               this.a(var0, var8, 0, 13, var9 + 1, var4);
               this.a(var0, var8, 12, 13, var9 + 1, var4);
            }
         }

         this.a(var0, Blocks.fn.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.b, Boolean.valueOf(true)), 0, 13, 0, var4);
         this.a(var0, Blocks.fn.o().a(BlockFence.c, Boolean.valueOf(true)).a(BlockFence.b, Boolean.valueOf(true)), 0, 13, 12, var4);
         this.a(var0, Blocks.fn.o().a(BlockFence.c, Boolean.valueOf(true)).a(BlockFence.d, Boolean.valueOf(true)), 12, 13, 12, var4);
         this.a(var0, Blocks.fn.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.d, Boolean.valueOf(true)), 12, 13, 0, var4);

         for(int var9 = 3; var9 <= 9; var9 += 2) {
            this.a(var0, var4, 1, 7, var9, 1, 8, var9, var8.a(BlockFence.d, Boolean.valueOf(true)), var8.a(BlockFence.d, Boolean.valueOf(true)), false);
            this.a(var0, var4, 11, 7, var9, 11, 8, var9, var8.a(BlockFence.b, Boolean.valueOf(true)), var8.a(BlockFence.b, Boolean.valueOf(true)), false);
         }

         this.a(var0, var4, 4, 2, 0, 8, 2, 12, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 4, 12, 2, 8, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 4, 0, 0, 8, 1, 3, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 4, 0, 9, 8, 1, 12, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 0, 4, 3, 1, 8, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 9, 0, 4, 12, 1, 8, Blocks.fm.o(), Blocks.fm.o(), false);

         for(int var9 = 4; var9 <= 8; ++var9) {
            for(int var10 = 0; var10 <= 2; ++var10) {
               this.b(var0, Blocks.fm.o(), var9, -1, var10, var4);
               this.b(var0, Blocks.fm.o(), var9, -1, 12 - var10, var4);
            }
         }

         for(int var9 = 0; var9 <= 2; ++var9) {
            for(int var10 = 4; var10 <= 8; ++var10) {
               this.b(var0, Blocks.fm.o(), var9, -1, var10, var4);
               this.b(var0, Blocks.fm.o(), 12 - var9, -1, var10, var4);
            }
         }

         this.a(var0, var4, 5, 5, 5, 7, 5, 7, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 6, 1, 6, 6, 4, 6, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, Blocks.fm.o(), 6, 0, 6, var4);
         this.a(var0, Blocks.H.o(), 6, 5, 6, var4);
         BlockPosition var9 = this.b(6, 5, 6);
         if (var4.b(var9)) {
            var0.a(var9, FluidTypes.e, 0);
         }
      }
   }

   public static class g extends NetherFortressPieces.m {
      private static final int a = 5;
      private static final int b = 7;
      private static final int c = 5;

      public g(int var0, StructureBoundingBox var1, EnumDirection var2) {
         super(WorldGenFeatureStructurePieceType.k, var0, var1);
         this.a(var2);
      }

      public g(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.k, var0);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         this.a((NetherFortressPieces.q)var0, var1, var2, 1, 0, true);
         this.b((NetherFortressPieces.q)var0, var1, var2, 0, 1, true);
         this.c((NetherFortressPieces.q)var0, var1, var2, 0, 1, true);
      }

      public static NetherFortressPieces.g a(StructurePieceAccessor var0, int var1, int var2, int var3, EnumDirection var4, int var5) {
         StructureBoundingBox var6 = StructureBoundingBox.a(var1, var2, var3, -1, 0, 0, 5, 7, 5, var4);
         return a(var6) && var0.a(var6) == null ? new NetherFortressPieces.g(var5, var6, var4) : null;
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
         this.a(var0, var4, 0, 0, 0, 4, 1, 4, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 0, 4, 5, 4, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, 0, 2, 0, 0, 5, 0, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 4, 2, 0, 4, 5, 0, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 4, 0, 5, 4, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 4, 2, 4, 4, 5, 4, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 6, 0, 4, 6, 4, Blocks.fm.o(), Blocks.fm.o(), false);

         for(int var7 = 0; var7 <= 4; ++var7) {
            for(int var8 = 0; var8 <= 4; ++var8) {
               this.b(var0, Blocks.fm.o(), var7, -1, var8, var4);
            }
         }
      }
   }

   public static class h extends NetherFortressPieces.m {
      private static final int a = 5;
      private static final int b = 7;
      private static final int c = 5;
      private boolean d;

      public h(int var0, RandomSource var1, StructureBoundingBox var2, EnumDirection var3) {
         super(WorldGenFeatureStructurePieceType.l, var0, var2);
         this.a(var3);
         this.d = var1.a(3) == 0;
      }

      public h(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.l, var0);
         this.d = var0.q("Chest");
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("Chest", this.d);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         this.b((NetherFortressPieces.q)var0, var1, var2, 0, 1, true);
      }

      public static NetherFortressPieces.h a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, 0, 0, 5, 7, 5, var5);
         return a(var7) && var0.a(var7) == null ? new NetherFortressPieces.h(var6, var1, var7, var5) : null;
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
         this.a(var0, var4, 0, 0, 0, 4, 1, 4, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 0, 4, 5, 4, Blocks.a.o(), Blocks.a.o(), false);
         IBlockData var7 = Blocks.fn.o().a(BlockFence.d, Boolean.valueOf(true)).a(BlockFence.b, Boolean.valueOf(true));
         IBlockData var8 = Blocks.fn.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.c, Boolean.valueOf(true));
         this.a(var0, var4, 4, 2, 0, 4, 5, 4, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 4, 3, 1, 4, 4, 1, var8, var8, false);
         this.a(var0, var4, 4, 3, 3, 4, 4, 3, var8, var8, false);
         this.a(var0, var4, 0, 2, 0, 0, 5, 0, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 4, 3, 5, 4, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 1, 3, 4, 1, 4, 4, var7, var7, false);
         this.a(var0, var4, 3, 3, 4, 3, 4, 4, var7, var7, false);
         if (this.d && var4.b(this.b(3, 2, 3))) {
            this.d = false;
            this.a(var0, var4, var3, 3, 2, 3, LootTables.v);
         }

         this.a(var0, var4, 0, 6, 0, 4, 6, 4, Blocks.fm.o(), Blocks.fm.o(), false);

         for(int var9 = 0; var9 <= 4; ++var9) {
            for(int var10 = 0; var10 <= 4; ++var10) {
               this.b(var0, Blocks.fm.o(), var9, -1, var10, var4);
            }
         }
      }
   }

   public static class i extends NetherFortressPieces.m {
      private static final int a = 5;
      private static final int b = 7;
      private static final int c = 5;

      public i(int var0, StructureBoundingBox var1, EnumDirection var2) {
         super(WorldGenFeatureStructurePieceType.m, var0, var1);
         this.a(var2);
      }

      public i(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.m, var0);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         this.a((NetherFortressPieces.q)var0, var1, var2, 1, 0, true);
      }

      public static NetherFortressPieces.i a(StructurePieceAccessor var0, int var1, int var2, int var3, EnumDirection var4, int var5) {
         StructureBoundingBox var6 = StructureBoundingBox.a(var1, var2, var3, -1, 0, 0, 5, 7, 5, var4);
         return a(var6) && var0.a(var6) == null ? new NetherFortressPieces.i(var5, var6, var4) : null;
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
         this.a(var0, var4, 0, 0, 0, 4, 1, 4, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 0, 4, 5, 4, Blocks.a.o(), Blocks.a.o(), false);
         IBlockData var7 = Blocks.fn.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.c, Boolean.valueOf(true));
         this.a(var0, var4, 0, 2, 0, 0, 5, 4, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 4, 2, 0, 4, 5, 4, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 3, 1, 0, 4, 1, var7, var7, false);
         this.a(var0, var4, 0, 3, 3, 0, 4, 3, var7, var7, false);
         this.a(var0, var4, 4, 3, 1, 4, 4, 1, var7, var7, false);
         this.a(var0, var4, 4, 3, 3, 4, 4, 3, var7, var7, false);
         this.a(var0, var4, 0, 6, 0, 4, 6, 4, Blocks.fm.o(), Blocks.fm.o(), false);

         for(int var8 = 0; var8 <= 4; ++var8) {
            for(int var9 = 0; var9 <= 4; ++var9) {
               this.b(var0, Blocks.fm.o(), var8, -1, var9, var4);
            }
         }
      }
   }

   public static class j extends NetherFortressPieces.m {
      private static final int a = 5;
      private static final int b = 7;
      private static final int c = 5;
      private boolean d;

      public j(int var0, RandomSource var1, StructureBoundingBox var2, EnumDirection var3) {
         super(WorldGenFeatureStructurePieceType.n, var0, var2);
         this.a(var3);
         this.d = var1.a(3) == 0;
      }

      public j(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.n, var0);
         this.d = var0.q("Chest");
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("Chest", this.d);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         this.c((NetherFortressPieces.q)var0, var1, var2, 0, 1, true);
      }

      public static NetherFortressPieces.j a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, 0, 0, 5, 7, 5, var5);
         return a(var7) && var0.a(var7) == null ? new NetherFortressPieces.j(var6, var1, var7, var5) : null;
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
         this.a(var0, var4, 0, 0, 0, 4, 1, 4, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 0, 4, 5, 4, Blocks.a.o(), Blocks.a.o(), false);
         IBlockData var7 = Blocks.fn.o().a(BlockFence.d, Boolean.valueOf(true)).a(BlockFence.b, Boolean.valueOf(true));
         IBlockData var8 = Blocks.fn.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.c, Boolean.valueOf(true));
         this.a(var0, var4, 0, 2, 0, 0, 5, 4, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 3, 1, 0, 4, 1, var8, var8, false);
         this.a(var0, var4, 0, 3, 3, 0, 4, 3, var8, var8, false);
         this.a(var0, var4, 4, 2, 0, 4, 5, 0, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 1, 2, 4, 4, 5, 4, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 1, 3, 4, 1, 4, 4, var7, var7, false);
         this.a(var0, var4, 3, 3, 4, 3, 4, 4, var7, var7, false);
         if (this.d && var4.b(this.b(1, 2, 3))) {
            this.d = false;
            this.a(var0, var4, var3, 1, 2, 3, LootTables.v);
         }

         this.a(var0, var4, 0, 6, 0, 4, 6, 4, Blocks.fm.o(), Blocks.fm.o(), false);

         for(int var9 = 0; var9 <= 4; ++var9) {
            for(int var10 = 0; var10 <= 4; ++var10) {
               this.b(var0, Blocks.fm.o(), var9, -1, var10, var4);
            }
         }
      }
   }

   public static class k extends NetherFortressPieces.m {
      private static final int a = 13;
      private static final int b = 14;
      private static final int c = 13;

      public k(int var0, StructureBoundingBox var1, EnumDirection var2) {
         super(WorldGenFeatureStructurePieceType.o, var0, var1);
         this.a(var2);
      }

      public k(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.o, var0);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         this.a((NetherFortressPieces.q)var0, var1, var2, 5, 3, true);
         this.a((NetherFortressPieces.q)var0, var1, var2, 5, 11, true);
      }

      public static NetherFortressPieces.k a(StructurePieceAccessor var0, int var1, int var2, int var3, EnumDirection var4, int var5) {
         StructureBoundingBox var6 = StructureBoundingBox.a(var1, var2, var3, -5, -3, 0, 13, 14, 13, var4);
         return a(var6) && var0.a(var6) == null ? new NetherFortressPieces.k(var5, var6, var4) : null;
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
         this.a(var0, var4, 0, 3, 0, 12, 4, 12, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 5, 0, 12, 13, 12, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, 0, 5, 0, 1, 12, 12, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 11, 5, 0, 12, 12, 12, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 2, 5, 11, 4, 12, 12, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 8, 5, 11, 10, 12, 12, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 5, 9, 11, 7, 12, 12, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 2, 5, 0, 4, 12, 1, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 8, 5, 0, 10, 12, 1, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 5, 9, 0, 7, 12, 1, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 2, 11, 2, 10, 12, 10, Blocks.fm.o(), Blocks.fm.o(), false);
         IBlockData var7 = Blocks.fn.o().a(BlockFence.d, Boolean.valueOf(true)).a(BlockFence.b, Boolean.valueOf(true));
         IBlockData var8 = Blocks.fn.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.c, Boolean.valueOf(true));
         IBlockData var9 = var8.a(BlockFence.d, Boolean.valueOf(true));
         IBlockData var10 = var8.a(BlockFence.b, Boolean.valueOf(true));

         for(int var11 = 1; var11 <= 11; var11 += 2) {
            this.a(var0, var4, var11, 10, 0, var11, 11, 0, var7, var7, false);
            this.a(var0, var4, var11, 10, 12, var11, 11, 12, var7, var7, false);
            this.a(var0, var4, 0, 10, var11, 0, 11, var11, var8, var8, false);
            this.a(var0, var4, 12, 10, var11, 12, 11, var11, var8, var8, false);
            this.a(var0, Blocks.fm.o(), var11, 13, 0, var4);
            this.a(var0, Blocks.fm.o(), var11, 13, 12, var4);
            this.a(var0, Blocks.fm.o(), 0, 13, var11, var4);
            this.a(var0, Blocks.fm.o(), 12, 13, var11, var4);
            if (var11 != 11) {
               this.a(var0, var7, var11 + 1, 13, 0, var4);
               this.a(var0, var7, var11 + 1, 13, 12, var4);
               this.a(var0, var8, 0, 13, var11 + 1, var4);
               this.a(var0, var8, 12, 13, var11 + 1, var4);
            }
         }

         this.a(var0, Blocks.fn.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.b, Boolean.valueOf(true)), 0, 13, 0, var4);
         this.a(var0, Blocks.fn.o().a(BlockFence.c, Boolean.valueOf(true)).a(BlockFence.b, Boolean.valueOf(true)), 0, 13, 12, var4);
         this.a(var0, Blocks.fn.o().a(BlockFence.c, Boolean.valueOf(true)).a(BlockFence.d, Boolean.valueOf(true)), 12, 13, 12, var4);
         this.a(var0, Blocks.fn.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.d, Boolean.valueOf(true)), 12, 13, 0, var4);

         for(int var11 = 3; var11 <= 9; var11 += 2) {
            this.a(var0, var4, 1, 7, var11, 1, 8, var11, var9, var9, false);
            this.a(var0, var4, 11, 7, var11, 11, 8, var11, var10, var10, false);
         }

         IBlockData var11 = Blocks.fo.o().a(BlockStairs.a, EnumDirection.c);

         for(int var12 = 0; var12 <= 6; ++var12) {
            int var13 = var12 + 4;

            for(int var14 = 5; var14 <= 7; ++var14) {
               this.a(var0, var11, var14, 5 + var12, var13, var4);
            }

            if (var13 >= 5 && var13 <= 8) {
               this.a(var0, var4, 5, 5, var13, 7, var12 + 4, var13, Blocks.fm.o(), Blocks.fm.o(), false);
            } else if (var13 >= 9 && var13 <= 10) {
               this.a(var0, var4, 5, 8, var13, 7, var12 + 4, var13, Blocks.fm.o(), Blocks.fm.o(), false);
            }

            if (var12 >= 1) {
               this.a(var0, var4, 5, 6 + var12, var13, 7, 9 + var12, var13, Blocks.a.o(), Blocks.a.o(), false);
            }
         }

         for(int var12 = 5; var12 <= 7; ++var12) {
            this.a(var0, var11, var12, 12, 11, var4);
         }

         this.a(var0, var4, 5, 6, 7, 5, 7, 7, var10, var10, false);
         this.a(var0, var4, 7, 6, 7, 7, 7, 7, var9, var9, false);
         this.a(var0, var4, 5, 13, 12, 7, 13, 12, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, 2, 5, 2, 3, 5, 3, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 2, 5, 9, 3, 5, 10, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 2, 5, 4, 2, 5, 8, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 9, 5, 2, 10, 5, 3, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 9, 5, 9, 10, 5, 10, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 10, 5, 4, 10, 5, 8, Blocks.fm.o(), Blocks.fm.o(), false);
         IBlockData var12 = var11.a(BlockStairs.a, EnumDirection.f);
         IBlockData var13 = var11.a(BlockStairs.a, EnumDirection.e);
         this.a(var0, var13, 4, 5, 2, var4);
         this.a(var0, var13, 4, 5, 3, var4);
         this.a(var0, var13, 4, 5, 9, var4);
         this.a(var0, var13, 4, 5, 10, var4);
         this.a(var0, var12, 8, 5, 2, var4);
         this.a(var0, var12, 8, 5, 3, var4);
         this.a(var0, var12, 8, 5, 9, var4);
         this.a(var0, var12, 8, 5, 10, var4);
         this.a(var0, var4, 3, 4, 4, 4, 4, 8, Blocks.dW.o(), Blocks.dW.o(), false);
         this.a(var0, var4, 8, 4, 4, 9, 4, 8, Blocks.dW.o(), Blocks.dW.o(), false);
         this.a(var0, var4, 3, 5, 4, 4, 5, 8, Blocks.fp.o(), Blocks.fp.o(), false);
         this.a(var0, var4, 8, 5, 4, 9, 5, 8, Blocks.fp.o(), Blocks.fp.o(), false);
         this.a(var0, var4, 4, 2, 0, 8, 2, 12, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 4, 12, 2, 8, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 4, 0, 0, 8, 1, 3, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 4, 0, 9, 8, 1, 12, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 0, 4, 3, 1, 8, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 9, 0, 4, 12, 1, 8, Blocks.fm.o(), Blocks.fm.o(), false);

         for(int var14 = 4; var14 <= 8; ++var14) {
            for(int var15 = 0; var15 <= 2; ++var15) {
               this.b(var0, Blocks.fm.o(), var14, -1, var15, var4);
               this.b(var0, Blocks.fm.o(), var14, -1, 12 - var15, var4);
            }
         }

         for(int var14 = 0; var14 <= 2; ++var14) {
            for(int var15 = 4; var15 <= 8; ++var15) {
               this.b(var0, Blocks.fm.o(), var14, -1, var15, var4);
               this.b(var0, Blocks.fm.o(), 12 - var14, -1, var15, var4);
            }
         }
      }
   }

   public static class l extends NetherFortressPieces.m {
      private static final int a = 7;
      private static final int b = 8;
      private static final int c = 9;
      private boolean d;

      public l(int var0, StructureBoundingBox var1, EnumDirection var2) {
         super(WorldGenFeatureStructurePieceType.p, var0, var1);
         this.a(var2);
      }

      public l(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.p, var0);
         this.d = var0.q("Mob");
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("Mob", this.d);
      }

      public static NetherFortressPieces.l a(StructurePieceAccessor var0, int var1, int var2, int var3, int var4, EnumDirection var5) {
         StructureBoundingBox var6 = StructureBoundingBox.a(var1, var2, var3, -2, 0, 0, 7, 8, 9, var5);
         return a(var6) && var0.a(var6) == null ? new NetherFortressPieces.l(var4, var6, var5) : null;
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
         this.a(var0, var4, 0, 2, 0, 6, 7, 7, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, 1, 0, 0, 5, 1, 7, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 1, 2, 1, 5, 2, 7, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 1, 3, 2, 5, 3, 7, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 1, 4, 3, 5, 4, 7, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 1, 2, 0, 1, 4, 2, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 5, 2, 0, 5, 4, 2, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 1, 5, 2, 1, 5, 3, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 5, 5, 2, 5, 5, 3, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 5, 3, 0, 5, 8, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 6, 5, 3, 6, 5, 8, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 1, 5, 8, 5, 5, 8, Blocks.fm.o(), Blocks.fm.o(), false);
         IBlockData var7 = Blocks.fn.o().a(BlockFence.d, Boolean.valueOf(true)).a(BlockFence.b, Boolean.valueOf(true));
         IBlockData var8 = Blocks.fn.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.c, Boolean.valueOf(true));
         this.a(var0, Blocks.fn.o().a(BlockFence.d, Boolean.valueOf(true)), 1, 6, 3, var4);
         this.a(var0, Blocks.fn.o().a(BlockFence.b, Boolean.valueOf(true)), 5, 6, 3, var4);
         this.a(var0, Blocks.fn.o().a(BlockFence.b, Boolean.valueOf(true)).a(BlockFence.a, Boolean.valueOf(true)), 0, 6, 3, var4);
         this.a(var0, Blocks.fn.o().a(BlockFence.d, Boolean.valueOf(true)).a(BlockFence.a, Boolean.valueOf(true)), 6, 6, 3, var4);
         this.a(var0, var4, 0, 6, 4, 0, 6, 7, var8, var8, false);
         this.a(var0, var4, 6, 6, 4, 6, 6, 7, var8, var8, false);
         this.a(var0, Blocks.fn.o().a(BlockFence.b, Boolean.valueOf(true)).a(BlockFence.c, Boolean.valueOf(true)), 0, 6, 8, var4);
         this.a(var0, Blocks.fn.o().a(BlockFence.d, Boolean.valueOf(true)).a(BlockFence.c, Boolean.valueOf(true)), 6, 6, 8, var4);
         this.a(var0, var4, 1, 6, 8, 5, 6, 8, var7, var7, false);
         this.a(var0, Blocks.fn.o().a(BlockFence.b, Boolean.valueOf(true)), 1, 7, 8, var4);
         this.a(var0, var4, 2, 7, 8, 4, 7, 8, var7, var7, false);
         this.a(var0, Blocks.fn.o().a(BlockFence.d, Boolean.valueOf(true)), 5, 7, 8, var4);
         this.a(var0, Blocks.fn.o().a(BlockFence.b, Boolean.valueOf(true)), 2, 8, 8, var4);
         this.a(var0, var7, 3, 8, 8, var4);
         this.a(var0, Blocks.fn.o().a(BlockFence.d, Boolean.valueOf(true)), 4, 8, 8, var4);
         if (!this.d) {
            BlockPosition var9 = this.b(3, 5, 5);
            if (var4.b(var9)) {
               this.d = true;
               var0.a(var9, Blocks.cs.o(), 2);
               TileEntity var10 = var0.c_(var9);
               if (var10 instanceof TileEntityMobSpawner var11) {
                  var11.a(EntityTypes.i, var3);
               }
            }
         }

         for(int var9 = 0; var9 <= 6; ++var9) {
            for(int var10 = 0; var10 <= 6; ++var10) {
               this.b(var0, Blocks.fm.o(), var9, -1, var10, var4);
            }
         }
      }
   }

   abstract static class m extends StructurePiece {
      protected m(WorldGenFeatureStructurePieceType var0, int var1, StructureBoundingBox var2) {
         super(var0, var1, var2);
      }

      public m(WorldGenFeatureStructurePieceType var0, NBTTagCompound var1) {
         super(var0, var1);
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
      }

      private int a(List<NetherFortressPieces.n> var0) {
         boolean var1 = false;
         int var2 = 0;

         for(NetherFortressPieces.n var4 : var0) {
            if (var4.d > 0 && var4.c < var4.d) {
               var1 = true;
            }

            var2 += var4.b;
         }

         return var1 ? var2 : -1;
      }

      private NetherFortressPieces.m a(
         NetherFortressPieces.q var0,
         List<NetherFortressPieces.n> var1,
         StructurePieceAccessor var2,
         RandomSource var3,
         int var4,
         int var5,
         int var6,
         EnumDirection var7,
         int var8
      ) {
         int var9 = this.a(var1);
         boolean var10 = var9 > 0 && var8 <= 30;
         int var11 = 0;

         while(var11 < 5 && var10) {
            ++var11;
            int var12 = var3.a(var9);

            for(NetherFortressPieces.n var14 : var1) {
               var12 -= var14.b;
               if (var12 < 0) {
                  if (!var14.a(var8) || var14 == var0.a && !var14.e) {
                     break;
                  }

                  NetherFortressPieces.m var15 = NetherFortressPieces.a(var14, var2, var3, var4, var5, var6, var7, var8);
                  if (var15 != null) {
                     ++var14.c;
                     var0.a = var14;
                     if (!var14.a()) {
                        var1.remove(var14);
                     }

                     return var15;
                  }
               }
            }
         }

         return NetherFortressPieces.b.a(var2, var3, var4, var5, var6, var7, var8);
      }

      private StructurePiece a(
         NetherFortressPieces.q var0,
         StructurePieceAccessor var1,
         RandomSource var2,
         int var3,
         int var4,
         int var5,
         @Nullable EnumDirection var6,
         int var7,
         boolean var8
      ) {
         if (Math.abs(var3 - var0.f().g()) <= 112 && Math.abs(var5 - var0.f().i()) <= 112) {
            List<NetherFortressPieces.n> var9 = var0.b;
            if (var8) {
               var9 = var0.c;
            }

            StructurePiece var10 = this.a(var0, var9, var1, var2, var3, var4, var5, var6, var7 + 1);
            if (var10 != null) {
               var1.a(var10);
               var0.d.add(var10);
            }

            return var10;
         } else {
            return NetherFortressPieces.b.a(var1, var2, var3, var4, var5, var6, var7);
         }
      }

      @Nullable
      protected StructurePiece a(NetherFortressPieces.q var0, StructurePieceAccessor var1, RandomSource var2, int var3, int var4, boolean var5) {
         EnumDirection var6 = this.i();
         if (var6 != null) {
            switch(var6) {
               case c:
                  return this.a(var0, var1, var2, this.f.g() + var3, this.f.h() + var4, this.f.i() - 1, var6, this.g(), var5);
               case d:
                  return this.a(var0, var1, var2, this.f.g() + var3, this.f.h() + var4, this.f.l() + 1, var6, this.g(), var5);
               case e:
                  return this.a(var0, var1, var2, this.f.g() - 1, this.f.h() + var4, this.f.i() + var3, var6, this.g(), var5);
               case f:
                  return this.a(var0, var1, var2, this.f.j() + 1, this.f.h() + var4, this.f.i() + var3, var6, this.g(), var5);
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece b(NetherFortressPieces.q var0, StructurePieceAccessor var1, RandomSource var2, int var3, int var4, boolean var5) {
         EnumDirection var6 = this.i();
         if (var6 != null) {
            switch(var6) {
               case c:
                  return this.a(var0, var1, var2, this.f.g() - 1, this.f.h() + var3, this.f.i() + var4, EnumDirection.e, this.g(), var5);
               case d:
                  return this.a(var0, var1, var2, this.f.g() - 1, this.f.h() + var3, this.f.i() + var4, EnumDirection.e, this.g(), var5);
               case e:
                  return this.a(var0, var1, var2, this.f.g() + var4, this.f.h() + var3, this.f.i() - 1, EnumDirection.c, this.g(), var5);
               case f:
                  return this.a(var0, var1, var2, this.f.g() + var4, this.f.h() + var3, this.f.i() - 1, EnumDirection.c, this.g(), var5);
            }
         }

         return null;
      }

      @Nullable
      protected StructurePiece c(NetherFortressPieces.q var0, StructurePieceAccessor var1, RandomSource var2, int var3, int var4, boolean var5) {
         EnumDirection var6 = this.i();
         if (var6 != null) {
            switch(var6) {
               case c:
                  return this.a(var0, var1, var2, this.f.j() + 1, this.f.h() + var3, this.f.i() + var4, EnumDirection.f, this.g(), var5);
               case d:
                  return this.a(var0, var1, var2, this.f.j() + 1, this.f.h() + var3, this.f.i() + var4, EnumDirection.f, this.g(), var5);
               case e:
                  return this.a(var0, var1, var2, this.f.g() + var4, this.f.h() + var3, this.f.l() + 1, EnumDirection.d, this.g(), var5);
               case f:
                  return this.a(var0, var1, var2, this.f.g() + var4, this.f.h() + var3, this.f.l() + 1, EnumDirection.d, this.g(), var5);
            }
         }

         return null;
      }

      protected static boolean a(StructureBoundingBox var0) {
         return var0 != null && var0.h() > 10;
      }
   }

   static class n {
      public final Class<? extends NetherFortressPieces.m> a;
      public final int b;
      public int c;
      public final int d;
      public final boolean e;

      public n(Class<? extends NetherFortressPieces.m> var0, int var1, int var2, boolean var3) {
         this.a = var0;
         this.b = var1;
         this.d = var2;
         this.e = var3;
      }

      public n(Class<? extends NetherFortressPieces.m> var0, int var1, int var2) {
         this(var0, var1, var2, false);
      }

      public boolean a(int var0) {
         return this.d == 0 || this.c < this.d;
      }

      public boolean a() {
         return this.d == 0 || this.c < this.d;
      }
   }

   public static class o extends NetherFortressPieces.m {
      private static final int a = 7;
      private static final int b = 9;
      private static final int c = 7;

      public o(int var0, StructureBoundingBox var1, EnumDirection var2) {
         super(WorldGenFeatureStructurePieceType.q, var0, var1);
         this.a(var2);
      }

      public o(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.q, var0);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         this.a((NetherFortressPieces.q)var0, var1, var2, 2, 0, false);
         this.b((NetherFortressPieces.q)var0, var1, var2, 0, 2, false);
         this.c((NetherFortressPieces.q)var0, var1, var2, 0, 2, false);
      }

      public static NetherFortressPieces.o a(StructurePieceAccessor var0, int var1, int var2, int var3, EnumDirection var4, int var5) {
         StructureBoundingBox var6 = StructureBoundingBox.a(var1, var2, var3, -2, 0, 0, 7, 9, 7, var4);
         return a(var6) && var0.a(var6) == null ? new NetherFortressPieces.o(var5, var6, var4) : null;
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
         this.a(var0, var4, 0, 0, 0, 6, 1, 6, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 0, 6, 7, 6, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, 0, 2, 0, 1, 6, 0, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 6, 1, 6, 6, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 5, 2, 0, 6, 6, 0, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 5, 2, 6, 6, 6, 6, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 0, 0, 6, 1, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 5, 0, 6, 6, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 6, 2, 0, 6, 6, 1, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 6, 2, 5, 6, 6, 6, Blocks.fm.o(), Blocks.fm.o(), false);
         IBlockData var7 = Blocks.fn.o().a(BlockFence.d, Boolean.valueOf(true)).a(BlockFence.b, Boolean.valueOf(true));
         IBlockData var8 = Blocks.fn.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.c, Boolean.valueOf(true));
         this.a(var0, var4, 2, 6, 0, 4, 6, 0, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 2, 5, 0, 4, 5, 0, var7, var7, false);
         this.a(var0, var4, 2, 6, 6, 4, 6, 6, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 2, 5, 6, 4, 5, 6, var7, var7, false);
         this.a(var0, var4, 0, 6, 2, 0, 6, 4, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 5, 2, 0, 5, 4, var8, var8, false);
         this.a(var0, var4, 6, 6, 2, 6, 6, 4, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 6, 5, 2, 6, 5, 4, var8, var8, false);

         for(int var9 = 0; var9 <= 6; ++var9) {
            for(int var10 = 0; var10 <= 6; ++var10) {
               this.b(var0, Blocks.fm.o(), var9, -1, var10, var4);
            }
         }
      }
   }

   public static class p extends NetherFortressPieces.m {
      private static final int a = 7;
      private static final int b = 11;
      private static final int c = 7;

      public p(int var0, StructureBoundingBox var1, EnumDirection var2) {
         super(WorldGenFeatureStructurePieceType.r, var0, var1);
         this.a(var2);
      }

      public p(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.r, var0);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         this.c((NetherFortressPieces.q)var0, var1, var2, 6, 2, false);
      }

      public static NetherFortressPieces.p a(StructurePieceAccessor var0, int var1, int var2, int var3, int var4, EnumDirection var5) {
         StructureBoundingBox var6 = StructureBoundingBox.a(var1, var2, var3, -2, 0, 0, 7, 11, 7, var5);
         return a(var6) && var0.a(var6) == null ? new NetherFortressPieces.p(var4, var6, var5) : null;
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
         this.a(var0, var4, 0, 0, 0, 6, 1, 6, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 0, 6, 10, 6, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, 0, 2, 0, 1, 8, 0, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 5, 2, 0, 6, 8, 0, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 0, 2, 1, 0, 8, 6, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 6, 2, 1, 6, 8, 6, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 1, 2, 6, 5, 8, 6, Blocks.fm.o(), Blocks.fm.o(), false);
         IBlockData var7 = Blocks.fn.o().a(BlockFence.d, Boolean.valueOf(true)).a(BlockFence.b, Boolean.valueOf(true));
         IBlockData var8 = Blocks.fn.o().a(BlockFence.a, Boolean.valueOf(true)).a(BlockFence.c, Boolean.valueOf(true));
         this.a(var0, var4, 0, 3, 2, 0, 5, 4, var8, var8, false);
         this.a(var0, var4, 6, 3, 2, 6, 5, 2, var8, var8, false);
         this.a(var0, var4, 6, 3, 4, 6, 5, 4, var8, var8, false);
         this.a(var0, Blocks.fm.o(), 5, 2, 5, var4);
         this.a(var0, var4, 4, 2, 5, 4, 3, 5, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 3, 2, 5, 3, 4, 5, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 2, 2, 5, 2, 5, 5, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 1, 2, 5, 1, 6, 5, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 1, 7, 1, 5, 7, 4, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 6, 8, 2, 6, 8, 4, Blocks.a.o(), Blocks.a.o(), false);
         this.a(var0, var4, 2, 6, 0, 4, 8, 0, Blocks.fm.o(), Blocks.fm.o(), false);
         this.a(var0, var4, 2, 5, 0, 4, 5, 0, var7, var7, false);

         for(int var9 = 0; var9 <= 6; ++var9) {
            for(int var10 = 0; var10 <= 6; ++var10) {
               this.b(var0, Blocks.fm.o(), var9, -1, var10, var4);
            }
         }
      }
   }

   public static class q extends NetherFortressPieces.a {
      public NetherFortressPieces.n a;
      public List<NetherFortressPieces.n> b;
      public List<NetherFortressPieces.n> c;
      public final List<StructurePiece> d = Lists.newArrayList();

      public q(RandomSource var0, int var1, int var2) {
         super(var1, var2, a(var0));
         this.b = Lists.newArrayList();

         for(NetherFortressPieces.n var6 : NetherFortressPieces.d) {
            var6.c = 0;
            this.b.add(var6);
         }

         this.c = Lists.newArrayList();

         for(NetherFortressPieces.n var6 : NetherFortressPieces.e) {
            var6.c = 0;
            this.c.add(var6);
         }
      }

      public q(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.s, var0);
      }
   }
}
