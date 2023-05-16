package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.List;
import java.util.Set;
import net.minecraft.SystemUtils;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.monster.EntityGuardianElder;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.World;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;

public class OceanMonumentPieces {
   private OceanMonumentPieces() {
   }

   static class a implements OceanMonumentPieces.i {
      @Override
      public boolean a(OceanMonumentPieces.v var0) {
         return var0.c[EnumDirection.f.d()] && !var0.b[EnumDirection.f.d()].d;
      }

      @Override
      public OceanMonumentPieces.r a(EnumDirection var0, OceanMonumentPieces.v var1, RandomSource var2) {
         var1.d = true;
         var1.b[EnumDirection.f.d()].d = true;
         return new OceanMonumentPieces.k(var0, var1);
      }
   }

   static class b implements OceanMonumentPieces.i {
      @Override
      public boolean a(OceanMonumentPieces.v var0) {
         if (var0.c[EnumDirection.f.d()] && !var0.b[EnumDirection.f.d()].d && var0.c[EnumDirection.b.d()] && !var0.b[EnumDirection.b.d()].d) {
            OceanMonumentPieces.v var1 = var0.b[EnumDirection.f.d()];
            return var1.c[EnumDirection.b.d()] && !var1.b[EnumDirection.b.d()].d;
         } else {
            return false;
         }
      }

      @Override
      public OceanMonumentPieces.r a(EnumDirection var0, OceanMonumentPieces.v var1, RandomSource var2) {
         var1.d = true;
         var1.b[EnumDirection.f.d()].d = true;
         var1.b[EnumDirection.b.d()].d = true;
         var1.b[EnumDirection.f.d()].b[EnumDirection.b.d()].d = true;
         return new OceanMonumentPieces.l(var0, var1);
      }
   }

   static class c implements OceanMonumentPieces.i {
      @Override
      public boolean a(OceanMonumentPieces.v var0) {
         return var0.c[EnumDirection.b.d()] && !var0.b[EnumDirection.b.d()].d;
      }

      @Override
      public OceanMonumentPieces.r a(EnumDirection var0, OceanMonumentPieces.v var1, RandomSource var2) {
         var1.d = true;
         var1.b[EnumDirection.b.d()].d = true;
         return new OceanMonumentPieces.m(var0, var1);
      }
   }

   static class d implements OceanMonumentPieces.i {
      @Override
      public boolean a(OceanMonumentPieces.v var0) {
         if (var0.c[EnumDirection.c.d()] && !var0.b[EnumDirection.c.d()].d && var0.c[EnumDirection.b.d()] && !var0.b[EnumDirection.b.d()].d) {
            OceanMonumentPieces.v var1 = var0.b[EnumDirection.c.d()];
            return var1.c[EnumDirection.b.d()] && !var1.b[EnumDirection.b.d()].d;
         } else {
            return false;
         }
      }

      @Override
      public OceanMonumentPieces.r a(EnumDirection var0, OceanMonumentPieces.v var1, RandomSource var2) {
         var1.d = true;
         var1.b[EnumDirection.c.d()].d = true;
         var1.b[EnumDirection.b.d()].d = true;
         var1.b[EnumDirection.c.d()].b[EnumDirection.b.d()].d = true;
         return new OceanMonumentPieces.n(var0, var1);
      }
   }

   static class e implements OceanMonumentPieces.i {
      @Override
      public boolean a(OceanMonumentPieces.v var0) {
         return var0.c[EnumDirection.c.d()] && !var0.b[EnumDirection.c.d()].d;
      }

      @Override
      public OceanMonumentPieces.r a(EnumDirection var0, OceanMonumentPieces.v var1, RandomSource var2) {
         OceanMonumentPieces.v var3 = var1;
         if (!var1.c[EnumDirection.c.d()] || var1.b[EnumDirection.c.d()].d) {
            var3 = var1.b[EnumDirection.d.d()];
         }

         var3.d = true;
         var3.b[EnumDirection.c.d()].d = true;
         return new OceanMonumentPieces.o(var0, var3);
      }
   }

   static class f implements OceanMonumentPieces.i {
      @Override
      public boolean a(OceanMonumentPieces.v var0) {
         return true;
      }

      @Override
      public OceanMonumentPieces.r a(EnumDirection var0, OceanMonumentPieces.v var1, RandomSource var2) {
         var1.d = true;
         return new OceanMonumentPieces.s(var0, var1, var2);
      }
   }

   static class g implements OceanMonumentPieces.i {
      @Override
      public boolean a(OceanMonumentPieces.v var0) {
         return !var0.c[EnumDirection.e.d()]
            && !var0.c[EnumDirection.f.d()]
            && !var0.c[EnumDirection.c.d()]
            && !var0.c[EnumDirection.d.d()]
            && !var0.c[EnumDirection.b.d()];
      }

      @Override
      public OceanMonumentPieces.r a(EnumDirection var0, OceanMonumentPieces.v var1, RandomSource var2) {
         var1.d = true;
         return new OceanMonumentPieces.t(var0, var1);
      }
   }

   public static class h extends OceanMonumentPieces.r {
      private static final int C = 58;
      private static final int D = 22;
      private static final int E = 58;
      public static final int a = 29;
      private static final int F = 61;
      private OceanMonumentPieces.v G;
      private OceanMonumentPieces.v H;
      private final List<OceanMonumentPieces.r> I = Lists.newArrayList();

      public h(RandomSource var0, int var1, int var2, EnumDirection var3) {
         super(WorldGenFeatureStructurePieceType.M, var3, 0, a(var1, 39, var2, var3, 58, 23, 58));
         this.a(var3);
         List<OceanMonumentPieces.v> var4 = this.b(var0);
         this.G.d = true;
         this.I.add(new OceanMonumentPieces.p(var3, this.G));
         this.I.add(new OceanMonumentPieces.j(var3, this.H));
         List<OceanMonumentPieces.i> var5 = Lists.newArrayList();
         var5.add(new OceanMonumentPieces.b());
         var5.add(new OceanMonumentPieces.d());
         var5.add(new OceanMonumentPieces.e());
         var5.add(new OceanMonumentPieces.a());
         var5.add(new OceanMonumentPieces.c());
         var5.add(new OceanMonumentPieces.g());
         var5.add(new OceanMonumentPieces.f());

         for(OceanMonumentPieces.v var7 : var4) {
            if (!var7.d && !var7.b()) {
               for(OceanMonumentPieces.i var9 : var5) {
                  if (var9.a(var7)) {
                     this.I.add(var9.a(var3, var7, var0));
                     break;
                  }
               }
            }
         }

         BlockPosition var6 = this.b(9, 0, 22);

         for(OceanMonumentPieces.r var8 : this.I) {
            var8.f().a((BaseBlockPosition)var6);
         }

         StructureBoundingBox var7 = StructureBoundingBox.a(this.b(1, 1, 1), this.b(23, 8, 21));
         StructureBoundingBox var8 = StructureBoundingBox.a(this.b(34, 1, 1), this.b(56, 8, 21));
         StructureBoundingBox var9 = StructureBoundingBox.a(this.b(22, 13, 22), this.b(35, 17, 35));
         int var10 = var0.f();
         this.I.add(new OceanMonumentPieces.u(var3, var7, var10++));
         this.I.add(new OceanMonumentPieces.u(var3, var8, var10++));
         this.I.add(new OceanMonumentPieces.q(var3, var9));
      }

      public h(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.M, var0);
      }

      private List<OceanMonumentPieces.v> b(RandomSource var0) {
         OceanMonumentPieces.v[] var1 = new OceanMonumentPieces.v[75];

         for(int var2 = 0; var2 < 5; ++var2) {
            for(int var3 = 0; var3 < 4; ++var3) {
               int var4 = 0;
               int var5 = c(var2, 0, var3);
               var1[var5] = new OceanMonumentPieces.v(var5);
            }
         }

         for(int var2 = 0; var2 < 5; ++var2) {
            for(int var3 = 0; var3 < 4; ++var3) {
               int var4 = 1;
               int var5 = c(var2, 1, var3);
               var1[var5] = new OceanMonumentPieces.v(var5);
            }
         }

         for(int var2 = 1; var2 < 4; ++var2) {
            for(int var3 = 0; var3 < 2; ++var3) {
               int var4 = 2;
               int var5 = c(var2, 2, var3);
               var1[var5] = new OceanMonumentPieces.v(var5);
            }
         }

         this.G = var1[u];

         for(int var2 = 0; var2 < 5; ++var2) {
            for(int var3 = 0; var3 < 5; ++var3) {
               for(int var4 = 0; var4 < 3; ++var4) {
                  int var5 = c(var2, var4, var3);
                  if (var1[var5] != null) {
                     for(EnumDirection var9 : EnumDirection.values()) {
                        int var10 = var2 + var9.j();
                        int var11 = var4 + var9.k();
                        int var12 = var3 + var9.l();
                        if (var10 >= 0 && var10 < 5 && var12 >= 0 && var12 < 5 && var11 >= 0 && var11 < 3) {
                           int var13 = c(var10, var11, var12);
                           if (var1[var13] != null) {
                              if (var12 == var3) {
                                 var1[var5].a(var9, var1[var13]);
                              } else {
                                 var1[var5].a(var9.g(), var1[var13]);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         OceanMonumentPieces.v var2 = new OceanMonumentPieces.v(1003);
         OceanMonumentPieces.v var3 = new OceanMonumentPieces.v(1001);
         OceanMonumentPieces.v var4 = new OceanMonumentPieces.v(1002);
         var1[v].a(EnumDirection.b, var2);
         var1[w].a(EnumDirection.d, var3);
         var1[x].a(EnumDirection.d, var4);
         var2.d = true;
         var3.d = true;
         var4.d = true;
         this.G.e = true;
         this.H = var1[c(var0.a(4), 0, 2)];
         this.H.d = true;
         this.H.b[EnumDirection.f.d()].d = true;
         this.H.b[EnumDirection.c.d()].d = true;
         this.H.b[EnumDirection.f.d()].b[EnumDirection.c.d()].d = true;
         this.H.b[EnumDirection.b.d()].d = true;
         this.H.b[EnumDirection.f.d()].b[EnumDirection.b.d()].d = true;
         this.H.b[EnumDirection.c.d()].b[EnumDirection.b.d()].d = true;
         this.H.b[EnumDirection.f.d()].b[EnumDirection.c.d()].b[EnumDirection.b.d()].d = true;
         ObjectArrayList<OceanMonumentPieces.v> var5 = new ObjectArrayList();

         for(OceanMonumentPieces.v var9 : var1) {
            if (var9 != null) {
               var9.a();
               var5.add(var9);
            }
         }

         var2.a();
         SystemUtils.b(var5, var0);
         int var6 = 1;
         ObjectListIterator var34 = var5.iterator();

         while(var34.hasNext()) {
            OceanMonumentPieces.v var8 = (OceanMonumentPieces.v)var34.next();
            int var9 = 0;
            int var10 = 0;

            while(var9 < 2 && var10 < 5) {
               ++var10;
               int var11 = var0.a(6);
               if (var8.c[var11]) {
                  int var12 = EnumDirection.a(var11).g().d();
                  var8.c[var11] = false;
                  var8.b[var11].c[var12] = false;
                  if (var8.a(var6++) && var8.b[var11].a(var6++)) {
                     ++var9;
                  } else {
                     var8.c[var11] = true;
                     var8.b[var11].c[var12] = true;
                  }
               }
            }
         }

         var5.add(var2);
         var5.add(var3);
         var5.add(var4);
         return var5;
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
         int var7 = Math.max(var0.m_(), 64) - this.f.h();
         this.b(var0, var4, 0, 0, 0, 58, var7, 58);
         this.a(false, 0, var0, var3, var4);
         this.a(true, 33, var0, var3, var4);
         this.a(var0, var3, var4);
         this.b(var0, var3, var4);
         this.c(var0, var3, var4);
         this.d(var0, var3, var4);
         this.e(var0, var3, var4);
         this.f(var0, var3, var4);

         for(int var8 = 0; var8 < 7; ++var8) {
            int var9 = 0;

            while(var9 < 7) {
               if (var9 == 0 && var8 == 3) {
                  var9 = 6;
               }

               int var10 = var8 * 9;
               int var11 = var9 * 9;

               for(int var12 = 0; var12 < 4; ++var12) {
                  for(int var13 = 0; var13 < 4; ++var13) {
                     this.a(var0, c, var10 + var12, 0, var11 + var13, var4);
                     this.b(var0, c, var10 + var12, -1, var11 + var13, var4);
                  }
               }

               if (var8 != 0 && var8 != 6) {
                  var9 += 6;
               } else {
                  ++var9;
               }
            }
         }

         for(int var8 = 0; var8 < 5; ++var8) {
            this.b(var0, var4, -1 - var8, 0 + var8 * 2, -1 - var8, -1 - var8, 23, 58 + var8);
            this.b(var0, var4, 58 + var8, 0 + var8 * 2, -1 - var8, 58 + var8, 23, 58 + var8);
            this.b(var0, var4, 0 - var8, 0 + var8 * 2, -1 - var8, 57 + var8, 23, -1 - var8);
            this.b(var0, var4, 0 - var8, 0 + var8 * 2, 58 + var8, 57 + var8, 23, 58 + var8);
         }

         for(OceanMonumentPieces.r var9 : this.I) {
            if (var9.f().a(var4)) {
               var9.a(var0, var1, var2, var3, var4, var5, var6);
            }
         }
      }

      private void a(boolean var0, int var1, GeneratorAccessSeed var2, RandomSource var3, StructureBoundingBox var4) {
         int var5 = 24;
         if (this.a(var4, var1, 0, var1 + 23, 20)) {
            this.a(var2, var4, var1 + 0, 0, 0, var1 + 24, 0, 20, b, b, false);
            this.b(var2, var4, var1 + 0, 1, 0, var1 + 24, 10, 20);

            for(int var6 = 0; var6 < 4; ++var6) {
               this.a(var2, var4, var1 + var6, var6 + 1, var6, var1 + var6, var6 + 1, 20, c, c, false);
               this.a(var2, var4, var1 + var6 + 7, var6 + 5, var6 + 7, var1 + var6 + 7, var6 + 5, 20, c, c, false);
               this.a(var2, var4, var1 + 17 - var6, var6 + 5, var6 + 7, var1 + 17 - var6, var6 + 5, 20, c, c, false);
               this.a(var2, var4, var1 + 24 - var6, var6 + 1, var6, var1 + 24 - var6, var6 + 1, 20, c, c, false);
               this.a(var2, var4, var1 + var6 + 1, var6 + 1, var6, var1 + 23 - var6, var6 + 1, var6, c, c, false);
               this.a(var2, var4, var1 + var6 + 8, var6 + 5, var6 + 7, var1 + 16 - var6, var6 + 5, var6 + 7, c, c, false);
            }

            this.a(var2, var4, var1 + 4, 4, 4, var1 + 6, 4, 20, b, b, false);
            this.a(var2, var4, var1 + 7, 4, 4, var1 + 17, 4, 6, b, b, false);
            this.a(var2, var4, var1 + 18, 4, 4, var1 + 20, 4, 20, b, b, false);
            this.a(var2, var4, var1 + 11, 8, 11, var1 + 13, 8, 20, b, b, false);
            this.a(var2, h, var1 + 12, 9, 12, var4);
            this.a(var2, h, var1 + 12, 9, 15, var4);
            this.a(var2, h, var1 + 12, 9, 18, var4);
            int var6 = var1 + (var0 ? 19 : 5);
            int var7 = var1 + (var0 ? 5 : 19);

            for(int var8 = 20; var8 >= 5; var8 -= 3) {
               this.a(var2, h, var6, 5, var8, var4);
            }

            for(int var8 = 19; var8 >= 7; var8 -= 3) {
               this.a(var2, h, var7, 5, var8, var4);
            }

            for(int var8 = 0; var8 < 4; ++var8) {
               int var9 = var0 ? var1 + 24 - (17 - var8 * 3) : var1 + 17 - var8 * 3;
               this.a(var2, h, var9, 5, 5, var4);
            }

            this.a(var2, h, var7, 5, 5, var4);
            this.a(var2, var4, var1 + 11, 1, 12, var1 + 13, 7, 12, b, b, false);
            this.a(var2, var4, var1 + 12, 1, 11, var1 + 12, 7, 13, b, b, false);
         }
      }

      private void a(GeneratorAccessSeed var0, RandomSource var1, StructureBoundingBox var2) {
         if (this.a(var2, 22, 5, 35, 17)) {
            this.b(var0, var2, 25, 0, 0, 32, 8, 20);

            for(int var3 = 0; var3 < 4; ++var3) {
               this.a(var0, var2, 24, 2, 5 + var3 * 4, 24, 4, 5 + var3 * 4, c, c, false);
               this.a(var0, var2, 22, 4, 5 + var3 * 4, 23, 4, 5 + var3 * 4, c, c, false);
               this.a(var0, c, 25, 5, 5 + var3 * 4, var2);
               this.a(var0, c, 26, 6, 5 + var3 * 4, var2);
               this.a(var0, i, 26, 5, 5 + var3 * 4, var2);
               this.a(var0, var2, 33, 2, 5 + var3 * 4, 33, 4, 5 + var3 * 4, c, c, false);
               this.a(var0, var2, 34, 4, 5 + var3 * 4, 35, 4, 5 + var3 * 4, c, c, false);
               this.a(var0, c, 32, 5, 5 + var3 * 4, var2);
               this.a(var0, c, 31, 6, 5 + var3 * 4, var2);
               this.a(var0, i, 31, 5, 5 + var3 * 4, var2);
               this.a(var0, var2, 27, 6, 5 + var3 * 4, 30, 6, 5 + var3 * 4, b, b, false);
            }
         }
      }

      private void b(GeneratorAccessSeed var0, RandomSource var1, StructureBoundingBox var2) {
         if (this.a(var2, 15, 20, 42, 21)) {
            this.a(var0, var2, 15, 0, 21, 42, 0, 21, b, b, false);
            this.b(var0, var2, 26, 1, 21, 31, 3, 21);
            this.a(var0, var2, 21, 12, 21, 36, 12, 21, b, b, false);
            this.a(var0, var2, 17, 11, 21, 40, 11, 21, b, b, false);
            this.a(var0, var2, 16, 10, 21, 41, 10, 21, b, b, false);
            this.a(var0, var2, 15, 7, 21, 42, 9, 21, b, b, false);
            this.a(var0, var2, 16, 6, 21, 41, 6, 21, b, b, false);
            this.a(var0, var2, 17, 5, 21, 40, 5, 21, b, b, false);
            this.a(var0, var2, 21, 4, 21, 36, 4, 21, b, b, false);
            this.a(var0, var2, 22, 3, 21, 26, 3, 21, b, b, false);
            this.a(var0, var2, 31, 3, 21, 35, 3, 21, b, b, false);
            this.a(var0, var2, 23, 2, 21, 25, 2, 21, b, b, false);
            this.a(var0, var2, 32, 2, 21, 34, 2, 21, b, b, false);
            this.a(var0, var2, 28, 4, 20, 29, 4, 21, c, c, false);
            this.a(var0, c, 27, 3, 21, var2);
            this.a(var0, c, 30, 3, 21, var2);
            this.a(var0, c, 26, 2, 21, var2);
            this.a(var0, c, 31, 2, 21, var2);
            this.a(var0, c, 25, 1, 21, var2);
            this.a(var0, c, 32, 1, 21, var2);

            for(int var3 = 0; var3 < 7; ++var3) {
               this.a(var0, d, 28 - var3, 6 + var3, 21, var2);
               this.a(var0, d, 29 + var3, 6 + var3, 21, var2);
            }

            for(int var3 = 0; var3 < 4; ++var3) {
               this.a(var0, d, 28 - var3, 9 + var3, 21, var2);
               this.a(var0, d, 29 + var3, 9 + var3, 21, var2);
            }

            this.a(var0, d, 28, 12, 21, var2);
            this.a(var0, d, 29, 12, 21, var2);

            for(int var3 = 0; var3 < 3; ++var3) {
               this.a(var0, d, 22 - var3 * 2, 8, 21, var2);
               this.a(var0, d, 22 - var3 * 2, 9, 21, var2);
               this.a(var0, d, 35 + var3 * 2, 8, 21, var2);
               this.a(var0, d, 35 + var3 * 2, 9, 21, var2);
            }

            this.b(var0, var2, 15, 13, 21, 42, 15, 21);
            this.b(var0, var2, 15, 1, 21, 15, 6, 21);
            this.b(var0, var2, 16, 1, 21, 16, 5, 21);
            this.b(var0, var2, 17, 1, 21, 20, 4, 21);
            this.b(var0, var2, 21, 1, 21, 21, 3, 21);
            this.b(var0, var2, 22, 1, 21, 22, 2, 21);
            this.b(var0, var2, 23, 1, 21, 24, 1, 21);
            this.b(var0, var2, 42, 1, 21, 42, 6, 21);
            this.b(var0, var2, 41, 1, 21, 41, 5, 21);
            this.b(var0, var2, 37, 1, 21, 40, 4, 21);
            this.b(var0, var2, 36, 1, 21, 36, 3, 21);
            this.b(var0, var2, 33, 1, 21, 34, 1, 21);
            this.b(var0, var2, 35, 1, 21, 35, 2, 21);
         }
      }

      private void c(GeneratorAccessSeed var0, RandomSource var1, StructureBoundingBox var2) {
         if (this.a(var2, 21, 21, 36, 36)) {
            this.a(var0, var2, 21, 0, 22, 36, 0, 36, b, b, false);
            this.b(var0, var2, 21, 1, 22, 36, 23, 36);

            for(int var3 = 0; var3 < 4; ++var3) {
               this.a(var0, var2, 21 + var3, 13 + var3, 21 + var3, 36 - var3, 13 + var3, 21 + var3, c, c, false);
               this.a(var0, var2, 21 + var3, 13 + var3, 36 - var3, 36 - var3, 13 + var3, 36 - var3, c, c, false);
               this.a(var0, var2, 21 + var3, 13 + var3, 22 + var3, 21 + var3, 13 + var3, 35 - var3, c, c, false);
               this.a(var0, var2, 36 - var3, 13 + var3, 22 + var3, 36 - var3, 13 + var3, 35 - var3, c, c, false);
            }

            this.a(var0, var2, 25, 16, 25, 32, 16, 32, b, b, false);
            this.a(var0, var2, 25, 17, 25, 25, 19, 25, c, c, false);
            this.a(var0, var2, 32, 17, 25, 32, 19, 25, c, c, false);
            this.a(var0, var2, 25, 17, 32, 25, 19, 32, c, c, false);
            this.a(var0, var2, 32, 17, 32, 32, 19, 32, c, c, false);
            this.a(var0, c, 26, 20, 26, var2);
            this.a(var0, c, 27, 21, 27, var2);
            this.a(var0, i, 27, 20, 27, var2);
            this.a(var0, c, 26, 20, 31, var2);
            this.a(var0, c, 27, 21, 30, var2);
            this.a(var0, i, 27, 20, 30, var2);
            this.a(var0, c, 31, 20, 31, var2);
            this.a(var0, c, 30, 21, 30, var2);
            this.a(var0, i, 30, 20, 30, var2);
            this.a(var0, c, 31, 20, 26, var2);
            this.a(var0, c, 30, 21, 27, var2);
            this.a(var0, i, 30, 20, 27, var2);
            this.a(var0, var2, 28, 21, 27, 29, 21, 27, b, b, false);
            this.a(var0, var2, 27, 21, 28, 27, 21, 29, b, b, false);
            this.a(var0, var2, 28, 21, 30, 29, 21, 30, b, b, false);
            this.a(var0, var2, 30, 21, 28, 30, 21, 29, b, b, false);
         }
      }

      private void d(GeneratorAccessSeed var0, RandomSource var1, StructureBoundingBox var2) {
         if (this.a(var2, 0, 21, 6, 58)) {
            this.a(var0, var2, 0, 0, 21, 6, 0, 57, b, b, false);
            this.b(var0, var2, 0, 1, 21, 6, 7, 57);
            this.a(var0, var2, 4, 4, 21, 6, 4, 53, b, b, false);

            for(int var3 = 0; var3 < 4; ++var3) {
               this.a(var0, var2, var3, var3 + 1, 21, var3, var3 + 1, 57 - var3, c, c, false);
            }

            for(int var3 = 23; var3 < 53; var3 += 3) {
               this.a(var0, h, 5, 5, var3, var2);
            }

            this.a(var0, h, 5, 5, 52, var2);

            for(int var3 = 0; var3 < 4; ++var3) {
               this.a(var0, var2, var3, var3 + 1, 21, var3, var3 + 1, 57 - var3, c, c, false);
            }

            this.a(var0, var2, 4, 1, 52, 6, 3, 52, b, b, false);
            this.a(var0, var2, 5, 1, 51, 5, 3, 53, b, b, false);
         }

         if (this.a(var2, 51, 21, 58, 58)) {
            this.a(var0, var2, 51, 0, 21, 57, 0, 57, b, b, false);
            this.b(var0, var2, 51, 1, 21, 57, 7, 57);
            this.a(var0, var2, 51, 4, 21, 53, 4, 53, b, b, false);

            for(int var3 = 0; var3 < 4; ++var3) {
               this.a(var0, var2, 57 - var3, var3 + 1, 21, 57 - var3, var3 + 1, 57 - var3, c, c, false);
            }

            for(int var3 = 23; var3 < 53; var3 += 3) {
               this.a(var0, h, 52, 5, var3, var2);
            }

            this.a(var0, h, 52, 5, 52, var2);
            this.a(var0, var2, 51, 1, 52, 53, 3, 52, b, b, false);
            this.a(var0, var2, 52, 1, 51, 52, 3, 53, b, b, false);
         }

         if (this.a(var2, 0, 51, 57, 57)) {
            this.a(var0, var2, 7, 0, 51, 50, 0, 57, b, b, false);
            this.b(var0, var2, 7, 1, 51, 50, 10, 57);

            for(int var3 = 0; var3 < 4; ++var3) {
               this.a(var0, var2, var3 + 1, var3 + 1, 57 - var3, 56 - var3, var3 + 1, 57 - var3, c, c, false);
            }
         }
      }

      private void e(GeneratorAccessSeed var0, RandomSource var1, StructureBoundingBox var2) {
         if (this.a(var2, 7, 21, 13, 50)) {
            this.a(var0, var2, 7, 0, 21, 13, 0, 50, b, b, false);
            this.b(var0, var2, 7, 1, 21, 13, 10, 50);
            this.a(var0, var2, 11, 8, 21, 13, 8, 53, b, b, false);

            for(int var3 = 0; var3 < 4; ++var3) {
               this.a(var0, var2, var3 + 7, var3 + 5, 21, var3 + 7, var3 + 5, 54, c, c, false);
            }

            for(int var3 = 21; var3 <= 45; var3 += 3) {
               this.a(var0, h, 12, 9, var3, var2);
            }
         }

         if (this.a(var2, 44, 21, 50, 54)) {
            this.a(var0, var2, 44, 0, 21, 50, 0, 50, b, b, false);
            this.b(var0, var2, 44, 1, 21, 50, 10, 50);
            this.a(var0, var2, 44, 8, 21, 46, 8, 53, b, b, false);

            for(int var3 = 0; var3 < 4; ++var3) {
               this.a(var0, var2, 50 - var3, var3 + 5, 21, 50 - var3, var3 + 5, 54, c, c, false);
            }

            for(int var3 = 21; var3 <= 45; var3 += 3) {
               this.a(var0, h, 45, 9, var3, var2);
            }
         }

         if (this.a(var2, 8, 44, 49, 54)) {
            this.a(var0, var2, 14, 0, 44, 43, 0, 50, b, b, false);
            this.b(var0, var2, 14, 1, 44, 43, 10, 50);

            for(int var3 = 12; var3 <= 45; var3 += 3) {
               this.a(var0, h, var3, 9, 45, var2);
               this.a(var0, h, var3, 9, 52, var2);
               if (var3 == 12 || var3 == 18 || var3 == 24 || var3 == 33 || var3 == 39 || var3 == 45) {
                  this.a(var0, h, var3, 9, 47, var2);
                  this.a(var0, h, var3, 9, 50, var2);
                  this.a(var0, h, var3, 10, 45, var2);
                  this.a(var0, h, var3, 10, 46, var2);
                  this.a(var0, h, var3, 10, 51, var2);
                  this.a(var0, h, var3, 10, 52, var2);
                  this.a(var0, h, var3, 11, 47, var2);
                  this.a(var0, h, var3, 11, 50, var2);
                  this.a(var0, h, var3, 12, 48, var2);
                  this.a(var0, h, var3, 12, 49, var2);
               }
            }

            for(int var3 = 0; var3 < 3; ++var3) {
               this.a(var0, var2, 8 + var3, 5 + var3, 54, 49 - var3, 5 + var3, 54, b, b, false);
            }

            this.a(var0, var2, 11, 8, 54, 46, 8, 54, c, c, false);
            this.a(var0, var2, 14, 8, 44, 43, 8, 53, b, b, false);
         }
      }

      private void f(GeneratorAccessSeed var0, RandomSource var1, StructureBoundingBox var2) {
         if (this.a(var2, 14, 21, 20, 43)) {
            this.a(var0, var2, 14, 0, 21, 20, 0, 43, b, b, false);
            this.b(var0, var2, 14, 1, 22, 20, 14, 43);
            this.a(var0, var2, 18, 12, 22, 20, 12, 39, b, b, false);
            this.a(var0, var2, 18, 12, 21, 20, 12, 21, c, c, false);

            for(int var3 = 0; var3 < 4; ++var3) {
               this.a(var0, var2, var3 + 14, var3 + 9, 21, var3 + 14, var3 + 9, 43 - var3, c, c, false);
            }

            for(int var3 = 23; var3 <= 39; var3 += 3) {
               this.a(var0, h, 19, 13, var3, var2);
            }
         }

         if (this.a(var2, 37, 21, 43, 43)) {
            this.a(var0, var2, 37, 0, 21, 43, 0, 43, b, b, false);
            this.b(var0, var2, 37, 1, 22, 43, 14, 43);
            this.a(var0, var2, 37, 12, 22, 39, 12, 39, b, b, false);
            this.a(var0, var2, 37, 12, 21, 39, 12, 21, c, c, false);

            for(int var3 = 0; var3 < 4; ++var3) {
               this.a(var0, var2, 43 - var3, var3 + 9, 21, 43 - var3, var3 + 9, 43 - var3, c, c, false);
            }

            for(int var3 = 23; var3 <= 39; var3 += 3) {
               this.a(var0, h, 38, 13, var3, var2);
            }
         }

         if (this.a(var2, 15, 37, 42, 43)) {
            this.a(var0, var2, 21, 0, 37, 36, 0, 43, b, b, false);
            this.b(var0, var2, 21, 1, 37, 36, 14, 43);
            this.a(var0, var2, 21, 12, 37, 36, 12, 39, b, b, false);

            for(int var3 = 0; var3 < 4; ++var3) {
               this.a(var0, var2, 15 + var3, var3 + 9, 43 - var3, 42 - var3, var3 + 9, 43 - var3, c, c, false);
            }

            for(int var3 = 21; var3 <= 36; var3 += 3) {
               this.a(var0, h, var3, 13, 38, var2);
            }
         }
      }
   }

   interface i {
      boolean a(OceanMonumentPieces.v var1);

      OceanMonumentPieces.r a(EnumDirection var1, OceanMonumentPieces.v var2, RandomSource var3);
   }

   public static class j extends OceanMonumentPieces.r {
      public j(EnumDirection var0, OceanMonumentPieces.v var1) {
         super(WorldGenFeatureStructurePieceType.N, 1, var0, var1, 2, 2, 2);
      }

      public j(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.N, var0);
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
         this.a(var0, var4, 1, 8, 0, 14, 8, 14, b);
         int var7 = 7;
         IBlockData var8 = c;
         this.a(var0, var4, 0, 7, 0, 0, 7, 15, var8, var8, false);
         this.a(var0, var4, 15, 7, 0, 15, 7, 15, var8, var8, false);
         this.a(var0, var4, 1, 7, 0, 15, 7, 0, var8, var8, false);
         this.a(var0, var4, 1, 7, 15, 14, 7, 15, var8, var8, false);

         for(int var7x = 1; var7x <= 6; ++var7x) {
            var8 = c;
            if (var7x == 2 || var7x == 6) {
               var8 = b;
            }

            for(int var9 = 0; var9 <= 15; var9 += 15) {
               this.a(var0, var4, var9, var7x, 0, var9, var7x, 1, var8, var8, false);
               this.a(var0, var4, var9, var7x, 6, var9, var7x, 9, var8, var8, false);
               this.a(var0, var4, var9, var7x, 14, var9, var7x, 15, var8, var8, false);
            }

            this.a(var0, var4, 1, var7x, 0, 1, var7x, 0, var8, var8, false);
            this.a(var0, var4, 6, var7x, 0, 9, var7x, 0, var8, var8, false);
            this.a(var0, var4, 14, var7x, 0, 14, var7x, 0, var8, var8, false);
            this.a(var0, var4, 1, var7x, 15, 14, var7x, 15, var8, var8, false);
         }

         this.a(var0, var4, 6, 3, 6, 9, 6, 9, d, d, false);
         this.a(var0, var4, 7, 4, 7, 8, 5, 8, Blocks.cg.o(), Blocks.cg.o(), false);

         for(int var7x = 3; var7x <= 6; var7x += 3) {
            for(int var8x = 6; var8x <= 9; var8x += 3) {
               this.a(var0, i, var8x, var7x, 6, var4);
               this.a(var0, i, var8x, var7x, 9, var4);
            }
         }

         this.a(var0, var4, 5, 1, 6, 5, 2, 6, c, c, false);
         this.a(var0, var4, 5, 1, 9, 5, 2, 9, c, c, false);
         this.a(var0, var4, 10, 1, 6, 10, 2, 6, c, c, false);
         this.a(var0, var4, 10, 1, 9, 10, 2, 9, c, c, false);
         this.a(var0, var4, 6, 1, 5, 6, 2, 5, c, c, false);
         this.a(var0, var4, 9, 1, 5, 9, 2, 5, c, c, false);
         this.a(var0, var4, 6, 1, 10, 6, 2, 10, c, c, false);
         this.a(var0, var4, 9, 1, 10, 9, 2, 10, c, c, false);
         this.a(var0, var4, 5, 2, 5, 5, 6, 5, c, c, false);
         this.a(var0, var4, 5, 2, 10, 5, 6, 10, c, c, false);
         this.a(var0, var4, 10, 2, 5, 10, 6, 5, c, c, false);
         this.a(var0, var4, 10, 2, 10, 10, 6, 10, c, c, false);
         this.a(var0, var4, 5, 7, 1, 5, 7, 6, c, c, false);
         this.a(var0, var4, 10, 7, 1, 10, 7, 6, c, c, false);
         this.a(var0, var4, 5, 7, 9, 5, 7, 14, c, c, false);
         this.a(var0, var4, 10, 7, 9, 10, 7, 14, c, c, false);
         this.a(var0, var4, 1, 7, 5, 6, 7, 5, c, c, false);
         this.a(var0, var4, 1, 7, 10, 6, 7, 10, c, c, false);
         this.a(var0, var4, 9, 7, 5, 14, 7, 5, c, c, false);
         this.a(var0, var4, 9, 7, 10, 14, 7, 10, c, c, false);
         this.a(var0, var4, 2, 1, 2, 2, 1, 3, c, c, false);
         this.a(var0, var4, 3, 1, 2, 3, 1, 2, c, c, false);
         this.a(var0, var4, 13, 1, 2, 13, 1, 3, c, c, false);
         this.a(var0, var4, 12, 1, 2, 12, 1, 2, c, c, false);
         this.a(var0, var4, 2, 1, 12, 2, 1, 13, c, c, false);
         this.a(var0, var4, 3, 1, 13, 3, 1, 13, c, c, false);
         this.a(var0, var4, 13, 1, 12, 13, 1, 13, c, c, false);
         this.a(var0, var4, 12, 1, 13, 12, 1, 13, c, c, false);
      }
   }

   public static class k extends OceanMonumentPieces.r {
      public k(EnumDirection var0, OceanMonumentPieces.v var1) {
         super(WorldGenFeatureStructurePieceType.O, 1, var0, var1, 2, 1, 1);
      }

      public k(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.O, var0);
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
         OceanMonumentPieces.v var7 = this.B.b[EnumDirection.f.d()];
         OceanMonumentPieces.v var8 = this.B;
         if (this.B.a / 25 > 0) {
            this.a(var0, var4, 8, 0, var7.c[EnumDirection.a.d()]);
            this.a(var0, var4, 0, 0, var8.c[EnumDirection.a.d()]);
         }

         if (var8.b[EnumDirection.b.d()] == null) {
            this.a(var0, var4, 1, 4, 1, 7, 4, 6, b);
         }

         if (var7.b[EnumDirection.b.d()] == null) {
            this.a(var0, var4, 8, 4, 1, 14, 4, 6, b);
         }

         this.a(var0, var4, 0, 3, 0, 0, 3, 7, c, c, false);
         this.a(var0, var4, 15, 3, 0, 15, 3, 7, c, c, false);
         this.a(var0, var4, 1, 3, 0, 15, 3, 0, c, c, false);
         this.a(var0, var4, 1, 3, 7, 14, 3, 7, c, c, false);
         this.a(var0, var4, 0, 2, 0, 0, 2, 7, b, b, false);
         this.a(var0, var4, 15, 2, 0, 15, 2, 7, b, b, false);
         this.a(var0, var4, 1, 2, 0, 15, 2, 0, b, b, false);
         this.a(var0, var4, 1, 2, 7, 14, 2, 7, b, b, false);
         this.a(var0, var4, 0, 1, 0, 0, 1, 7, c, c, false);
         this.a(var0, var4, 15, 1, 0, 15, 1, 7, c, c, false);
         this.a(var0, var4, 1, 1, 0, 15, 1, 0, c, c, false);
         this.a(var0, var4, 1, 1, 7, 14, 1, 7, c, c, false);
         this.a(var0, var4, 5, 1, 0, 10, 1, 4, c, c, false);
         this.a(var0, var4, 6, 2, 0, 9, 2, 3, b, b, false);
         this.a(var0, var4, 5, 3, 0, 10, 3, 4, c, c, false);
         this.a(var0, i, 6, 2, 3, var4);
         this.a(var0, i, 9, 2, 3, var4);
         if (var8.c[EnumDirection.d.d()]) {
            this.b(var0, var4, 3, 1, 0, 4, 2, 0);
         }

         if (var8.c[EnumDirection.c.d()]) {
            this.b(var0, var4, 3, 1, 7, 4, 2, 7);
         }

         if (var8.c[EnumDirection.e.d()]) {
            this.b(var0, var4, 0, 1, 3, 0, 2, 4);
         }

         if (var7.c[EnumDirection.d.d()]) {
            this.b(var0, var4, 11, 1, 0, 12, 2, 0);
         }

         if (var7.c[EnumDirection.c.d()]) {
            this.b(var0, var4, 11, 1, 7, 12, 2, 7);
         }

         if (var7.c[EnumDirection.f.d()]) {
            this.b(var0, var4, 15, 1, 3, 15, 2, 4);
         }
      }
   }

   public static class l extends OceanMonumentPieces.r {
      public l(EnumDirection var0, OceanMonumentPieces.v var1) {
         super(WorldGenFeatureStructurePieceType.P, 1, var0, var1, 2, 2, 1);
      }

      public l(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.P, var0);
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
         OceanMonumentPieces.v var7 = this.B.b[EnumDirection.f.d()];
         OceanMonumentPieces.v var8 = this.B;
         OceanMonumentPieces.v var9 = var8.b[EnumDirection.b.d()];
         OceanMonumentPieces.v var10 = var7.b[EnumDirection.b.d()];
         if (this.B.a / 25 > 0) {
            this.a(var0, var4, 8, 0, var7.c[EnumDirection.a.d()]);
            this.a(var0, var4, 0, 0, var8.c[EnumDirection.a.d()]);
         }

         if (var9.b[EnumDirection.b.d()] == null) {
            this.a(var0, var4, 1, 8, 1, 7, 8, 6, b);
         }

         if (var10.b[EnumDirection.b.d()] == null) {
            this.a(var0, var4, 8, 8, 1, 14, 8, 6, b);
         }

         for(int var11 = 1; var11 <= 7; ++var11) {
            IBlockData var12 = c;
            if (var11 == 2 || var11 == 6) {
               var12 = b;
            }

            this.a(var0, var4, 0, var11, 0, 0, var11, 7, var12, var12, false);
            this.a(var0, var4, 15, var11, 0, 15, var11, 7, var12, var12, false);
            this.a(var0, var4, 1, var11, 0, 15, var11, 0, var12, var12, false);
            this.a(var0, var4, 1, var11, 7, 14, var11, 7, var12, var12, false);
         }

         this.a(var0, var4, 2, 1, 3, 2, 7, 4, c, c, false);
         this.a(var0, var4, 3, 1, 2, 4, 7, 2, c, c, false);
         this.a(var0, var4, 3, 1, 5, 4, 7, 5, c, c, false);
         this.a(var0, var4, 13, 1, 3, 13, 7, 4, c, c, false);
         this.a(var0, var4, 11, 1, 2, 12, 7, 2, c, c, false);
         this.a(var0, var4, 11, 1, 5, 12, 7, 5, c, c, false);
         this.a(var0, var4, 5, 1, 3, 5, 3, 4, c, c, false);
         this.a(var0, var4, 10, 1, 3, 10, 3, 4, c, c, false);
         this.a(var0, var4, 5, 7, 2, 10, 7, 5, c, c, false);
         this.a(var0, var4, 5, 5, 2, 5, 7, 2, c, c, false);
         this.a(var0, var4, 10, 5, 2, 10, 7, 2, c, c, false);
         this.a(var0, var4, 5, 5, 5, 5, 7, 5, c, c, false);
         this.a(var0, var4, 10, 5, 5, 10, 7, 5, c, c, false);
         this.a(var0, c, 6, 6, 2, var4);
         this.a(var0, c, 9, 6, 2, var4);
         this.a(var0, c, 6, 6, 5, var4);
         this.a(var0, c, 9, 6, 5, var4);
         this.a(var0, var4, 5, 4, 3, 6, 4, 4, c, c, false);
         this.a(var0, var4, 9, 4, 3, 10, 4, 4, c, c, false);
         this.a(var0, i, 5, 4, 2, var4);
         this.a(var0, i, 5, 4, 5, var4);
         this.a(var0, i, 10, 4, 2, var4);
         this.a(var0, i, 10, 4, 5, var4);
         if (var8.c[EnumDirection.d.d()]) {
            this.b(var0, var4, 3, 1, 0, 4, 2, 0);
         }

         if (var8.c[EnumDirection.c.d()]) {
            this.b(var0, var4, 3, 1, 7, 4, 2, 7);
         }

         if (var8.c[EnumDirection.e.d()]) {
            this.b(var0, var4, 0, 1, 3, 0, 2, 4);
         }

         if (var7.c[EnumDirection.d.d()]) {
            this.b(var0, var4, 11, 1, 0, 12, 2, 0);
         }

         if (var7.c[EnumDirection.c.d()]) {
            this.b(var0, var4, 11, 1, 7, 12, 2, 7);
         }

         if (var7.c[EnumDirection.f.d()]) {
            this.b(var0, var4, 15, 1, 3, 15, 2, 4);
         }

         if (var9.c[EnumDirection.d.d()]) {
            this.b(var0, var4, 3, 5, 0, 4, 6, 0);
         }

         if (var9.c[EnumDirection.c.d()]) {
            this.b(var0, var4, 3, 5, 7, 4, 6, 7);
         }

         if (var9.c[EnumDirection.e.d()]) {
            this.b(var0, var4, 0, 5, 3, 0, 6, 4);
         }

         if (var10.c[EnumDirection.d.d()]) {
            this.b(var0, var4, 11, 5, 0, 12, 6, 0);
         }

         if (var10.c[EnumDirection.c.d()]) {
            this.b(var0, var4, 11, 5, 7, 12, 6, 7);
         }

         if (var10.c[EnumDirection.f.d()]) {
            this.b(var0, var4, 15, 5, 3, 15, 6, 4);
         }
      }
   }

   public static class m extends OceanMonumentPieces.r {
      public m(EnumDirection var0, OceanMonumentPieces.v var1) {
         super(WorldGenFeatureStructurePieceType.Q, 1, var0, var1, 1, 2, 1);
      }

      public m(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.Q, var0);
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
         if (this.B.a / 25 > 0) {
            this.a(var0, var4, 0, 0, this.B.c[EnumDirection.a.d()]);
         }

         OceanMonumentPieces.v var7 = this.B.b[EnumDirection.b.d()];
         if (var7.b[EnumDirection.b.d()] == null) {
            this.a(var0, var4, 1, 8, 1, 6, 8, 6, b);
         }

         this.a(var0, var4, 0, 4, 0, 0, 4, 7, c, c, false);
         this.a(var0, var4, 7, 4, 0, 7, 4, 7, c, c, false);
         this.a(var0, var4, 1, 4, 0, 6, 4, 0, c, c, false);
         this.a(var0, var4, 1, 4, 7, 6, 4, 7, c, c, false);
         this.a(var0, var4, 2, 4, 1, 2, 4, 2, c, c, false);
         this.a(var0, var4, 1, 4, 2, 1, 4, 2, c, c, false);
         this.a(var0, var4, 5, 4, 1, 5, 4, 2, c, c, false);
         this.a(var0, var4, 6, 4, 2, 6, 4, 2, c, c, false);
         this.a(var0, var4, 2, 4, 5, 2, 4, 6, c, c, false);
         this.a(var0, var4, 1, 4, 5, 1, 4, 5, c, c, false);
         this.a(var0, var4, 5, 4, 5, 5, 4, 6, c, c, false);
         this.a(var0, var4, 6, 4, 5, 6, 4, 5, c, c, false);
         OceanMonumentPieces.v var8 = this.B;

         for(int var9 = 1; var9 <= 5; var9 += 4) {
            int var10 = 0;
            if (var8.c[EnumDirection.d.d()]) {
               this.a(var0, var4, 2, var9, var10, 2, var9 + 2, var10, c, c, false);
               this.a(var0, var4, 5, var9, var10, 5, var9 + 2, var10, c, c, false);
               this.a(var0, var4, 3, var9 + 2, var10, 4, var9 + 2, var10, c, c, false);
            } else {
               this.a(var0, var4, 0, var9, var10, 7, var9 + 2, var10, c, c, false);
               this.a(var0, var4, 0, var9 + 1, var10, 7, var9 + 1, var10, b, b, false);
            }

            int var13 = 7;
            if (var8.c[EnumDirection.c.d()]) {
               this.a(var0, var4, 2, var9, var13, 2, var9 + 2, var13, c, c, false);
               this.a(var0, var4, 5, var9, var13, 5, var9 + 2, var13, c, c, false);
               this.a(var0, var4, 3, var9 + 2, var13, 4, var9 + 2, var13, c, c, false);
            } else {
               this.a(var0, var4, 0, var9, var13, 7, var9 + 2, var13, c, c, false);
               this.a(var0, var4, 0, var9 + 1, var13, 7, var9 + 1, var13, b, b, false);
            }

            int var11 = 0;
            if (var8.c[EnumDirection.e.d()]) {
               this.a(var0, var4, var11, var9, 2, var11, var9 + 2, 2, c, c, false);
               this.a(var0, var4, var11, var9, 5, var11, var9 + 2, 5, c, c, false);
               this.a(var0, var4, var11, var9 + 2, 3, var11, var9 + 2, 4, c, c, false);
            } else {
               this.a(var0, var4, var11, var9, 0, var11, var9 + 2, 7, c, c, false);
               this.a(var0, var4, var11, var9 + 1, 0, var11, var9 + 1, 7, b, b, false);
            }

            int var14 = 7;
            if (var8.c[EnumDirection.f.d()]) {
               this.a(var0, var4, var14, var9, 2, var14, var9 + 2, 2, c, c, false);
               this.a(var0, var4, var14, var9, 5, var14, var9 + 2, 5, c, c, false);
               this.a(var0, var4, var14, var9 + 2, 3, var14, var9 + 2, 4, c, c, false);
            } else {
               this.a(var0, var4, var14, var9, 0, var14, var9 + 2, 7, c, c, false);
               this.a(var0, var4, var14, var9 + 1, 0, var14, var9 + 1, 7, b, b, false);
            }

            var8 = var7;
         }
      }
   }

   public static class n extends OceanMonumentPieces.r {
      public n(EnumDirection var0, OceanMonumentPieces.v var1) {
         super(WorldGenFeatureStructurePieceType.R, 1, var0, var1, 1, 2, 2);
      }

      public n(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.R, var0);
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
         OceanMonumentPieces.v var7 = this.B.b[EnumDirection.c.d()];
         OceanMonumentPieces.v var8 = this.B;
         OceanMonumentPieces.v var9 = var7.b[EnumDirection.b.d()];
         OceanMonumentPieces.v var10 = var8.b[EnumDirection.b.d()];
         if (this.B.a / 25 > 0) {
            this.a(var0, var4, 0, 8, var7.c[EnumDirection.a.d()]);
            this.a(var0, var4, 0, 0, var8.c[EnumDirection.a.d()]);
         }

         if (var10.b[EnumDirection.b.d()] == null) {
            this.a(var0, var4, 1, 8, 1, 6, 8, 7, b);
         }

         if (var9.b[EnumDirection.b.d()] == null) {
            this.a(var0, var4, 1, 8, 8, 6, 8, 14, b);
         }

         for(int var11 = 1; var11 <= 7; ++var11) {
            IBlockData var12 = c;
            if (var11 == 2 || var11 == 6) {
               var12 = b;
            }

            this.a(var0, var4, 0, var11, 0, 0, var11, 15, var12, var12, false);
            this.a(var0, var4, 7, var11, 0, 7, var11, 15, var12, var12, false);
            this.a(var0, var4, 1, var11, 0, 6, var11, 0, var12, var12, false);
            this.a(var0, var4, 1, var11, 15, 6, var11, 15, var12, var12, false);
         }

         for(int var11 = 1; var11 <= 7; ++var11) {
            IBlockData var12 = d;
            if (var11 == 2 || var11 == 6) {
               var12 = i;
            }

            this.a(var0, var4, 3, var11, 7, 4, var11, 8, var12, var12, false);
         }

         if (var8.c[EnumDirection.d.d()]) {
            this.b(var0, var4, 3, 1, 0, 4, 2, 0);
         }

         if (var8.c[EnumDirection.f.d()]) {
            this.b(var0, var4, 7, 1, 3, 7, 2, 4);
         }

         if (var8.c[EnumDirection.e.d()]) {
            this.b(var0, var4, 0, 1, 3, 0, 2, 4);
         }

         if (var7.c[EnumDirection.c.d()]) {
            this.b(var0, var4, 3, 1, 15, 4, 2, 15);
         }

         if (var7.c[EnumDirection.e.d()]) {
            this.b(var0, var4, 0, 1, 11, 0, 2, 12);
         }

         if (var7.c[EnumDirection.f.d()]) {
            this.b(var0, var4, 7, 1, 11, 7, 2, 12);
         }

         if (var10.c[EnumDirection.d.d()]) {
            this.b(var0, var4, 3, 5, 0, 4, 6, 0);
         }

         if (var10.c[EnumDirection.f.d()]) {
            this.b(var0, var4, 7, 5, 3, 7, 6, 4);
            this.a(var0, var4, 5, 4, 2, 6, 4, 5, c, c, false);
            this.a(var0, var4, 6, 1, 2, 6, 3, 2, c, c, false);
            this.a(var0, var4, 6, 1, 5, 6, 3, 5, c, c, false);
         }

         if (var10.c[EnumDirection.e.d()]) {
            this.b(var0, var4, 0, 5, 3, 0, 6, 4);
            this.a(var0, var4, 1, 4, 2, 2, 4, 5, c, c, false);
            this.a(var0, var4, 1, 1, 2, 1, 3, 2, c, c, false);
            this.a(var0, var4, 1, 1, 5, 1, 3, 5, c, c, false);
         }

         if (var9.c[EnumDirection.c.d()]) {
            this.b(var0, var4, 3, 5, 15, 4, 6, 15);
         }

         if (var9.c[EnumDirection.e.d()]) {
            this.b(var0, var4, 0, 5, 11, 0, 6, 12);
            this.a(var0, var4, 1, 4, 10, 2, 4, 13, c, c, false);
            this.a(var0, var4, 1, 1, 10, 1, 3, 10, c, c, false);
            this.a(var0, var4, 1, 1, 13, 1, 3, 13, c, c, false);
         }

         if (var9.c[EnumDirection.f.d()]) {
            this.b(var0, var4, 7, 5, 11, 7, 6, 12);
            this.a(var0, var4, 5, 4, 10, 6, 4, 13, c, c, false);
            this.a(var0, var4, 6, 1, 10, 6, 3, 10, c, c, false);
            this.a(var0, var4, 6, 1, 13, 6, 3, 13, c, c, false);
         }
      }
   }

   public static class o extends OceanMonumentPieces.r {
      public o(EnumDirection var0, OceanMonumentPieces.v var1) {
         super(WorldGenFeatureStructurePieceType.S, 1, var0, var1, 1, 1, 2);
      }

      public o(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.S, var0);
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
         OceanMonumentPieces.v var7 = this.B.b[EnumDirection.c.d()];
         OceanMonumentPieces.v var8 = this.B;
         if (this.B.a / 25 > 0) {
            this.a(var0, var4, 0, 8, var7.c[EnumDirection.a.d()]);
            this.a(var0, var4, 0, 0, var8.c[EnumDirection.a.d()]);
         }

         if (var8.b[EnumDirection.b.d()] == null) {
            this.a(var0, var4, 1, 4, 1, 6, 4, 7, b);
         }

         if (var7.b[EnumDirection.b.d()] == null) {
            this.a(var0, var4, 1, 4, 8, 6, 4, 14, b);
         }

         this.a(var0, var4, 0, 3, 0, 0, 3, 15, c, c, false);
         this.a(var0, var4, 7, 3, 0, 7, 3, 15, c, c, false);
         this.a(var0, var4, 1, 3, 0, 7, 3, 0, c, c, false);
         this.a(var0, var4, 1, 3, 15, 6, 3, 15, c, c, false);
         this.a(var0, var4, 0, 2, 0, 0, 2, 15, b, b, false);
         this.a(var0, var4, 7, 2, 0, 7, 2, 15, b, b, false);
         this.a(var0, var4, 1, 2, 0, 7, 2, 0, b, b, false);
         this.a(var0, var4, 1, 2, 15, 6, 2, 15, b, b, false);
         this.a(var0, var4, 0, 1, 0, 0, 1, 15, c, c, false);
         this.a(var0, var4, 7, 1, 0, 7, 1, 15, c, c, false);
         this.a(var0, var4, 1, 1, 0, 7, 1, 0, c, c, false);
         this.a(var0, var4, 1, 1, 15, 6, 1, 15, c, c, false);
         this.a(var0, var4, 1, 1, 1, 1, 1, 2, c, c, false);
         this.a(var0, var4, 6, 1, 1, 6, 1, 2, c, c, false);
         this.a(var0, var4, 1, 3, 1, 1, 3, 2, c, c, false);
         this.a(var0, var4, 6, 3, 1, 6, 3, 2, c, c, false);
         this.a(var0, var4, 1, 1, 13, 1, 1, 14, c, c, false);
         this.a(var0, var4, 6, 1, 13, 6, 1, 14, c, c, false);
         this.a(var0, var4, 1, 3, 13, 1, 3, 14, c, c, false);
         this.a(var0, var4, 6, 3, 13, 6, 3, 14, c, c, false);
         this.a(var0, var4, 2, 1, 6, 2, 3, 6, c, c, false);
         this.a(var0, var4, 5, 1, 6, 5, 3, 6, c, c, false);
         this.a(var0, var4, 2, 1, 9, 2, 3, 9, c, c, false);
         this.a(var0, var4, 5, 1, 9, 5, 3, 9, c, c, false);
         this.a(var0, var4, 3, 2, 6, 4, 2, 6, c, c, false);
         this.a(var0, var4, 3, 2, 9, 4, 2, 9, c, c, false);
         this.a(var0, var4, 2, 2, 7, 2, 2, 8, c, c, false);
         this.a(var0, var4, 5, 2, 7, 5, 2, 8, c, c, false);
         this.a(var0, i, 2, 2, 5, var4);
         this.a(var0, i, 5, 2, 5, var4);
         this.a(var0, i, 2, 2, 10, var4);
         this.a(var0, i, 5, 2, 10, var4);
         this.a(var0, c, 2, 3, 5, var4);
         this.a(var0, c, 5, 3, 5, var4);
         this.a(var0, c, 2, 3, 10, var4);
         this.a(var0, c, 5, 3, 10, var4);
         if (var8.c[EnumDirection.d.d()]) {
            this.b(var0, var4, 3, 1, 0, 4, 2, 0);
         }

         if (var8.c[EnumDirection.f.d()]) {
            this.b(var0, var4, 7, 1, 3, 7, 2, 4);
         }

         if (var8.c[EnumDirection.e.d()]) {
            this.b(var0, var4, 0, 1, 3, 0, 2, 4);
         }

         if (var7.c[EnumDirection.c.d()]) {
            this.b(var0, var4, 3, 1, 15, 4, 2, 15);
         }

         if (var7.c[EnumDirection.e.d()]) {
            this.b(var0, var4, 0, 1, 11, 0, 2, 12);
         }

         if (var7.c[EnumDirection.f.d()]) {
            this.b(var0, var4, 7, 1, 11, 7, 2, 12);
         }
      }
   }

   public static class p extends OceanMonumentPieces.r {
      public p(EnumDirection var0, OceanMonumentPieces.v var1) {
         super(WorldGenFeatureStructurePieceType.T, 1, var0, var1, 1, 1, 1);
      }

      public p(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.T, var0);
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
         this.a(var0, var4, 0, 3, 0, 2, 3, 7, c, c, false);
         this.a(var0, var4, 5, 3, 0, 7, 3, 7, c, c, false);
         this.a(var0, var4, 0, 2, 0, 1, 2, 7, c, c, false);
         this.a(var0, var4, 6, 2, 0, 7, 2, 7, c, c, false);
         this.a(var0, var4, 0, 1, 0, 0, 1, 7, c, c, false);
         this.a(var0, var4, 7, 1, 0, 7, 1, 7, c, c, false);
         this.a(var0, var4, 0, 1, 7, 7, 3, 7, c, c, false);
         this.a(var0, var4, 1, 1, 0, 2, 3, 0, c, c, false);
         this.a(var0, var4, 5, 1, 0, 6, 3, 0, c, c, false);
         if (this.B.c[EnumDirection.c.d()]) {
            this.b(var0, var4, 3, 1, 7, 4, 2, 7);
         }

         if (this.B.c[EnumDirection.e.d()]) {
            this.b(var0, var4, 0, 1, 3, 1, 2, 4);
         }

         if (this.B.c[EnumDirection.f.d()]) {
            this.b(var0, var4, 6, 1, 3, 7, 2, 4);
         }
      }
   }

   public static class q extends OceanMonumentPieces.r {
      public q(EnumDirection var0, StructureBoundingBox var1) {
         super(WorldGenFeatureStructurePieceType.U, var0, 1, var1);
      }

      public q(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.U, var0);
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
         this.a(var0, var4, 2, -1, 2, 11, -1, 11, c, c, false);
         this.a(var0, var4, 0, -1, 0, 1, -1, 11, b, b, false);
         this.a(var0, var4, 12, -1, 0, 13, -1, 11, b, b, false);
         this.a(var0, var4, 2, -1, 0, 11, -1, 1, b, b, false);
         this.a(var0, var4, 2, -1, 12, 11, -1, 13, b, b, false);
         this.a(var0, var4, 0, 0, 0, 0, 0, 13, c, c, false);
         this.a(var0, var4, 13, 0, 0, 13, 0, 13, c, c, false);
         this.a(var0, var4, 1, 0, 0, 12, 0, 0, c, c, false);
         this.a(var0, var4, 1, 0, 13, 12, 0, 13, c, c, false);

         for(int var7 = 2; var7 <= 11; var7 += 3) {
            this.a(var0, i, 0, 0, var7, var4);
            this.a(var0, i, 13, 0, var7, var4);
            this.a(var0, i, var7, 0, 0, var4);
         }

         this.a(var0, var4, 2, 0, 3, 4, 0, 9, c, c, false);
         this.a(var0, var4, 9, 0, 3, 11, 0, 9, c, c, false);
         this.a(var0, var4, 4, 0, 9, 9, 0, 11, c, c, false);
         this.a(var0, c, 5, 0, 8, var4);
         this.a(var0, c, 8, 0, 8, var4);
         this.a(var0, c, 10, 0, 10, var4);
         this.a(var0, c, 3, 0, 10, var4);
         this.a(var0, var4, 3, 0, 3, 3, 0, 7, d, d, false);
         this.a(var0, var4, 10, 0, 3, 10, 0, 7, d, d, false);
         this.a(var0, var4, 6, 0, 10, 7, 0, 10, d, d, false);
         int var7 = 3;

         for(int var8 = 0; var8 < 2; ++var8) {
            for(int var9 = 2; var9 <= 8; var9 += 3) {
               this.a(var0, var4, var7, 0, var9, var7, 2, var9, c, c, false);
            }

            var7 = 10;
         }

         this.a(var0, var4, 5, 0, 10, 5, 2, 10, c, c, false);
         this.a(var0, var4, 8, 0, 10, 8, 2, 10, c, c, false);
         this.a(var0, var4, 6, -1, 7, 7, -1, 8, d, d, false);
         this.b(var0, var4, 6, -1, 3, 7, -1, 4);
         this.a(var0, var4, 6, 1, 6);
      }
   }

   protected abstract static class r extends StructurePiece {
      protected static final IBlockData b = Blocks.hY.o();
      protected static final IBlockData c = Blocks.hZ.o();
      protected static final IBlockData d = Blocks.ia.o();
      protected static final IBlockData h = c;
      protected static final IBlockData i = Blocks.ih.o();
      protected static final boolean j = true;
      protected static final IBlockData k = Blocks.G.o();
      protected static final Set<Block> l = ImmutableSet.builder().add(Blocks.dN).add(Blocks.iB).add(Blocks.mS).add(k.b()).build();
      protected static final int m = 8;
      protected static final int n = 8;
      protected static final int o = 4;
      protected static final int p = 5;
      protected static final int q = 5;
      protected static final int r = 3;
      protected static final int s = 25;
      protected static final int t = 75;
      protected static final int u = c(2, 0, 0);
      protected static final int v = c(2, 2, 0);
      protected static final int w = c(0, 1, 0);
      protected static final int x = c(4, 1, 0);
      protected static final int y = 1001;
      protected static final int z = 1002;
      protected static final int A = 1003;
      protected OceanMonumentPieces.v B;

      protected static int c(int var0, int var1, int var2) {
         return var1 * 25 + var2 * 5 + var0;
      }

      public r(WorldGenFeatureStructurePieceType var0, EnumDirection var1, int var2, StructureBoundingBox var3) {
         super(var0, var2, var3);
         this.a(var1);
      }

      protected r(WorldGenFeatureStructurePieceType var0, int var1, EnumDirection var2, OceanMonumentPieces.v var3, int var4, int var5, int var6) {
         super(var0, var1, a(var2, var3, var4, var5, var6));
         this.a(var2);
         this.B = var3;
      }

      private static StructureBoundingBox a(EnumDirection var0, OceanMonumentPieces.v var1, int var2, int var3, int var4) {
         int var5 = var1.a;
         int var6 = var5 % 5;
         int var7 = var5 / 5 % 5;
         int var8 = var5 / 25;
         StructureBoundingBox var9 = a(0, 0, 0, var0, var2 * 8, var3 * 4, var4 * 8);
         switch(var0) {
            case c:
               var9.a(var6 * 8, var8 * 4, -(var7 + var4) * 8 + 1);
               break;
            case d:
               var9.a(var6 * 8, var8 * 4, var7 * 8);
               break;
            case e:
               var9.a(-(var7 + var4) * 8 + 1, var8 * 4, var6 * 8);
               break;
            case f:
            default:
               var9.a(var7 * 8, var8 * 4, var6 * 8);
         }

         return var9;
      }

      public r(WorldGenFeatureStructurePieceType var0, NBTTagCompound var1) {
         super(var0, var1);
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
      }

      protected void b(GeneratorAccessSeed var0, StructureBoundingBox var1, int var2, int var3, int var4, int var5, int var6, int var7) {
         for(int var8 = var3; var8 <= var6; ++var8) {
            for(int var9 = var2; var9 <= var5; ++var9) {
               for(int var10 = var4; var10 <= var7; ++var10) {
                  IBlockData var11 = this.a(var0, var9, var8, var10, var1);
                  if (!l.contains(var11.b())) {
                     if (this.b(var8) >= var0.m_() && var11 != k) {
                        this.a(var0, Blocks.a.o(), var9, var8, var10, var1);
                     } else {
                        this.a(var0, k, var9, var8, var10, var1);
                     }
                  }
               }
            }
         }
      }

      protected void a(GeneratorAccessSeed var0, StructureBoundingBox var1, int var2, int var3, boolean var4) {
         if (var4) {
            this.a(var0, var1, var2 + 0, 0, var3 + 0, var2 + 2, 0, var3 + 8 - 1, b, b, false);
            this.a(var0, var1, var2 + 5, 0, var3 + 0, var2 + 8 - 1, 0, var3 + 8 - 1, b, b, false);
            this.a(var0, var1, var2 + 3, 0, var3 + 0, var2 + 4, 0, var3 + 2, b, b, false);
            this.a(var0, var1, var2 + 3, 0, var3 + 5, var2 + 4, 0, var3 + 8 - 1, b, b, false);
            this.a(var0, var1, var2 + 3, 0, var3 + 2, var2 + 4, 0, var3 + 2, c, c, false);
            this.a(var0, var1, var2 + 3, 0, var3 + 5, var2 + 4, 0, var3 + 5, c, c, false);
            this.a(var0, var1, var2 + 2, 0, var3 + 3, var2 + 2, 0, var3 + 4, c, c, false);
            this.a(var0, var1, var2 + 5, 0, var3 + 3, var2 + 5, 0, var3 + 4, c, c, false);
         } else {
            this.a(var0, var1, var2 + 0, 0, var3 + 0, var2 + 8 - 1, 0, var3 + 8 - 1, b, b, false);
         }
      }

      protected void a(GeneratorAccessSeed var0, StructureBoundingBox var1, int var2, int var3, int var4, int var5, int var6, int var7, IBlockData var8) {
         for(int var9 = var3; var9 <= var6; ++var9) {
            for(int var10 = var2; var10 <= var5; ++var10) {
               for(int var11 = var4; var11 <= var7; ++var11) {
                  if (this.a(var0, var10, var9, var11, var1) == k) {
                     this.a(var0, var8, var10, var9, var11, var1);
                  }
               }
            }
         }
      }

      protected boolean a(StructureBoundingBox var0, int var1, int var2, int var3, int var4) {
         int var5 = this.a(var1, var2);
         int var6 = this.b(var1, var2);
         int var7 = this.a(var3, var4);
         int var8 = this.b(var3, var4);
         return var0.a(Math.min(var5, var7), Math.min(var6, var8), Math.max(var5, var7), Math.max(var6, var8));
      }

      protected void a(GeneratorAccessSeed var0, StructureBoundingBox var1, int var2, int var3, int var4) {
         BlockPosition var5 = this.b(var2, var3, var4);
         if (var1.b(var5)) {
            EntityGuardianElder var6 = EntityTypes.A.a((World)var0.C());
            if (var6 != null) {
               var6.b(var6.eE());
               var6.b((double)var5.u() + 0.5, (double)var5.v(), (double)var5.w() + 0.5, 0.0F, 0.0F);
               var6.a(var0, var0.d_(var6.dg()), EnumMobSpawn.d, null, null);
               var0.a_(var6);
            }
         }
      }
   }

   public static class s extends OceanMonumentPieces.r {
      private int a;

      public s(EnumDirection var0, OceanMonumentPieces.v var1, RandomSource var2) {
         super(WorldGenFeatureStructurePieceType.V, 1, var0, var1, 1, 1, 1);
         this.a = var2.a(3);
      }

      public s(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.V, var0);
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
         if (this.B.a / 25 > 0) {
            this.a(var0, var4, 0, 0, this.B.c[EnumDirection.a.d()]);
         }

         if (this.B.b[EnumDirection.b.d()] == null) {
            this.a(var0, var4, 1, 4, 1, 6, 4, 6, b);
         }

         boolean var7 = this.a != 0 && var3.h() && !this.B.c[EnumDirection.a.d()] && !this.B.c[EnumDirection.b.d()] && this.B.c() > 1;
         if (this.a == 0) {
            this.a(var0, var4, 0, 1, 0, 2, 1, 2, c, c, false);
            this.a(var0, var4, 0, 3, 0, 2, 3, 2, c, c, false);
            this.a(var0, var4, 0, 2, 0, 0, 2, 2, b, b, false);
            this.a(var0, var4, 1, 2, 0, 2, 2, 0, b, b, false);
            this.a(var0, i, 1, 2, 1, var4);
            this.a(var0, var4, 5, 1, 0, 7, 1, 2, c, c, false);
            this.a(var0, var4, 5, 3, 0, 7, 3, 2, c, c, false);
            this.a(var0, var4, 7, 2, 0, 7, 2, 2, b, b, false);
            this.a(var0, var4, 5, 2, 0, 6, 2, 0, b, b, false);
            this.a(var0, i, 6, 2, 1, var4);
            this.a(var0, var4, 0, 1, 5, 2, 1, 7, c, c, false);
            this.a(var0, var4, 0, 3, 5, 2, 3, 7, c, c, false);
            this.a(var0, var4, 0, 2, 5, 0, 2, 7, b, b, false);
            this.a(var0, var4, 1, 2, 7, 2, 2, 7, b, b, false);
            this.a(var0, i, 1, 2, 6, var4);
            this.a(var0, var4, 5, 1, 5, 7, 1, 7, c, c, false);
            this.a(var0, var4, 5, 3, 5, 7, 3, 7, c, c, false);
            this.a(var0, var4, 7, 2, 5, 7, 2, 7, b, b, false);
            this.a(var0, var4, 5, 2, 7, 6, 2, 7, b, b, false);
            this.a(var0, i, 6, 2, 6, var4);
            if (this.B.c[EnumDirection.d.d()]) {
               this.a(var0, var4, 3, 3, 0, 4, 3, 0, c, c, false);
            } else {
               this.a(var0, var4, 3, 3, 0, 4, 3, 1, c, c, false);
               this.a(var0, var4, 3, 2, 0, 4, 2, 0, b, b, false);
               this.a(var0, var4, 3, 1, 0, 4, 1, 1, c, c, false);
            }

            if (this.B.c[EnumDirection.c.d()]) {
               this.a(var0, var4, 3, 3, 7, 4, 3, 7, c, c, false);
            } else {
               this.a(var0, var4, 3, 3, 6, 4, 3, 7, c, c, false);
               this.a(var0, var4, 3, 2, 7, 4, 2, 7, b, b, false);
               this.a(var0, var4, 3, 1, 6, 4, 1, 7, c, c, false);
            }

            if (this.B.c[EnumDirection.e.d()]) {
               this.a(var0, var4, 0, 3, 3, 0, 3, 4, c, c, false);
            } else {
               this.a(var0, var4, 0, 3, 3, 1, 3, 4, c, c, false);
               this.a(var0, var4, 0, 2, 3, 0, 2, 4, b, b, false);
               this.a(var0, var4, 0, 1, 3, 1, 1, 4, c, c, false);
            }

            if (this.B.c[EnumDirection.f.d()]) {
               this.a(var0, var4, 7, 3, 3, 7, 3, 4, c, c, false);
            } else {
               this.a(var0, var4, 6, 3, 3, 7, 3, 4, c, c, false);
               this.a(var0, var4, 7, 2, 3, 7, 2, 4, b, b, false);
               this.a(var0, var4, 6, 1, 3, 7, 1, 4, c, c, false);
            }
         } else if (this.a == 1) {
            this.a(var0, var4, 2, 1, 2, 2, 3, 2, c, c, false);
            this.a(var0, var4, 2, 1, 5, 2, 3, 5, c, c, false);
            this.a(var0, var4, 5, 1, 5, 5, 3, 5, c, c, false);
            this.a(var0, var4, 5, 1, 2, 5, 3, 2, c, c, false);
            this.a(var0, i, 2, 2, 2, var4);
            this.a(var0, i, 2, 2, 5, var4);
            this.a(var0, i, 5, 2, 5, var4);
            this.a(var0, i, 5, 2, 2, var4);
            this.a(var0, var4, 0, 1, 0, 1, 3, 0, c, c, false);
            this.a(var0, var4, 0, 1, 1, 0, 3, 1, c, c, false);
            this.a(var0, var4, 0, 1, 7, 1, 3, 7, c, c, false);
            this.a(var0, var4, 0, 1, 6, 0, 3, 6, c, c, false);
            this.a(var0, var4, 6, 1, 7, 7, 3, 7, c, c, false);
            this.a(var0, var4, 7, 1, 6, 7, 3, 6, c, c, false);
            this.a(var0, var4, 6, 1, 0, 7, 3, 0, c, c, false);
            this.a(var0, var4, 7, 1, 1, 7, 3, 1, c, c, false);
            this.a(var0, b, 1, 2, 0, var4);
            this.a(var0, b, 0, 2, 1, var4);
            this.a(var0, b, 1, 2, 7, var4);
            this.a(var0, b, 0, 2, 6, var4);
            this.a(var0, b, 6, 2, 7, var4);
            this.a(var0, b, 7, 2, 6, var4);
            this.a(var0, b, 6, 2, 0, var4);
            this.a(var0, b, 7, 2, 1, var4);
            if (!this.B.c[EnumDirection.d.d()]) {
               this.a(var0, var4, 1, 3, 0, 6, 3, 0, c, c, false);
               this.a(var0, var4, 1, 2, 0, 6, 2, 0, b, b, false);
               this.a(var0, var4, 1, 1, 0, 6, 1, 0, c, c, false);
            }

            if (!this.B.c[EnumDirection.c.d()]) {
               this.a(var0, var4, 1, 3, 7, 6, 3, 7, c, c, false);
               this.a(var0, var4, 1, 2, 7, 6, 2, 7, b, b, false);
               this.a(var0, var4, 1, 1, 7, 6, 1, 7, c, c, false);
            }

            if (!this.B.c[EnumDirection.e.d()]) {
               this.a(var0, var4, 0, 3, 1, 0, 3, 6, c, c, false);
               this.a(var0, var4, 0, 2, 1, 0, 2, 6, b, b, false);
               this.a(var0, var4, 0, 1, 1, 0, 1, 6, c, c, false);
            }

            if (!this.B.c[EnumDirection.f.d()]) {
               this.a(var0, var4, 7, 3, 1, 7, 3, 6, c, c, false);
               this.a(var0, var4, 7, 2, 1, 7, 2, 6, b, b, false);
               this.a(var0, var4, 7, 1, 1, 7, 1, 6, c, c, false);
            }
         } else if (this.a == 2) {
            this.a(var0, var4, 0, 1, 0, 0, 1, 7, c, c, false);
            this.a(var0, var4, 7, 1, 0, 7, 1, 7, c, c, false);
            this.a(var0, var4, 1, 1, 0, 6, 1, 0, c, c, false);
            this.a(var0, var4, 1, 1, 7, 6, 1, 7, c, c, false);
            this.a(var0, var4, 0, 2, 0, 0, 2, 7, d, d, false);
            this.a(var0, var4, 7, 2, 0, 7, 2, 7, d, d, false);
            this.a(var0, var4, 1, 2, 0, 6, 2, 0, d, d, false);
            this.a(var0, var4, 1, 2, 7, 6, 2, 7, d, d, false);
            this.a(var0, var4, 0, 3, 0, 0, 3, 7, c, c, false);
            this.a(var0, var4, 7, 3, 0, 7, 3, 7, c, c, false);
            this.a(var0, var4, 1, 3, 0, 6, 3, 0, c, c, false);
            this.a(var0, var4, 1, 3, 7, 6, 3, 7, c, c, false);
            this.a(var0, var4, 0, 1, 3, 0, 2, 4, d, d, false);
            this.a(var0, var4, 7, 1, 3, 7, 2, 4, d, d, false);
            this.a(var0, var4, 3, 1, 0, 4, 2, 0, d, d, false);
            this.a(var0, var4, 3, 1, 7, 4, 2, 7, d, d, false);
            if (this.B.c[EnumDirection.d.d()]) {
               this.b(var0, var4, 3, 1, 0, 4, 2, 0);
            }

            if (this.B.c[EnumDirection.c.d()]) {
               this.b(var0, var4, 3, 1, 7, 4, 2, 7);
            }

            if (this.B.c[EnumDirection.e.d()]) {
               this.b(var0, var4, 0, 1, 3, 0, 2, 4);
            }

            if (this.B.c[EnumDirection.f.d()]) {
               this.b(var0, var4, 7, 1, 3, 7, 2, 4);
            }
         }

         if (var7) {
            this.a(var0, var4, 3, 1, 3, 4, 1, 4, c, c, false);
            this.a(var0, var4, 3, 2, 3, 4, 2, 4, b, b, false);
            this.a(var0, var4, 3, 3, 3, 4, 3, 4, c, c, false);
         }
      }
   }

   public static class t extends OceanMonumentPieces.r {
      public t(EnumDirection var0, OceanMonumentPieces.v var1) {
         super(WorldGenFeatureStructurePieceType.W, 1, var0, var1, 1, 1, 1);
      }

      public t(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.W, var0);
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
         if (this.B.a / 25 > 0) {
            this.a(var0, var4, 0, 0, this.B.c[EnumDirection.a.d()]);
         }

         if (this.B.b[EnumDirection.b.d()] == null) {
            this.a(var0, var4, 1, 4, 1, 6, 4, 6, b);
         }

         for(int var7 = 1; var7 <= 6; ++var7) {
            for(int var8 = 1; var8 <= 6; ++var8) {
               if (var3.a(3) != 0) {
                  int var9 = 2 + (var3.a(4) == 0 ? 0 : 1);
                  IBlockData var10 = Blocks.aO.o();
                  this.a(var0, var4, var7, var9, var8, var7, 3, var8, var10, var10, false);
               }
            }
         }

         this.a(var0, var4, 0, 1, 0, 0, 1, 7, c, c, false);
         this.a(var0, var4, 7, 1, 0, 7, 1, 7, c, c, false);
         this.a(var0, var4, 1, 1, 0, 6, 1, 0, c, c, false);
         this.a(var0, var4, 1, 1, 7, 6, 1, 7, c, c, false);
         this.a(var0, var4, 0, 2, 0, 0, 2, 7, d, d, false);
         this.a(var0, var4, 7, 2, 0, 7, 2, 7, d, d, false);
         this.a(var0, var4, 1, 2, 0, 6, 2, 0, d, d, false);
         this.a(var0, var4, 1, 2, 7, 6, 2, 7, d, d, false);
         this.a(var0, var4, 0, 3, 0, 0, 3, 7, c, c, false);
         this.a(var0, var4, 7, 3, 0, 7, 3, 7, c, c, false);
         this.a(var0, var4, 1, 3, 0, 6, 3, 0, c, c, false);
         this.a(var0, var4, 1, 3, 7, 6, 3, 7, c, c, false);
         this.a(var0, var4, 0, 1, 3, 0, 2, 4, d, d, false);
         this.a(var0, var4, 7, 1, 3, 7, 2, 4, d, d, false);
         this.a(var0, var4, 3, 1, 0, 4, 2, 0, d, d, false);
         this.a(var0, var4, 3, 1, 7, 4, 2, 7, d, d, false);
         if (this.B.c[EnumDirection.d.d()]) {
            this.b(var0, var4, 3, 1, 0, 4, 2, 0);
         }
      }
   }

   public static class u extends OceanMonumentPieces.r {
      private int a;

      public u(EnumDirection var0, StructureBoundingBox var1, int var2) {
         super(WorldGenFeatureStructurePieceType.X, var0, 1, var1);
         this.a = var2 & 1;
      }

      public u(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.X, var0);
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
         if (this.a == 0) {
            for(int var7 = 0; var7 < 4; ++var7) {
               this.a(var0, var4, 10 - var7, 3 - var7, 20 - var7, 12 + var7, 3 - var7, 20, c, c, false);
            }

            this.a(var0, var4, 7, 0, 6, 15, 0, 16, c, c, false);
            this.a(var0, var4, 6, 0, 6, 6, 3, 20, c, c, false);
            this.a(var0, var4, 16, 0, 6, 16, 3, 20, c, c, false);
            this.a(var0, var4, 7, 1, 7, 7, 1, 20, c, c, false);
            this.a(var0, var4, 15, 1, 7, 15, 1, 20, c, c, false);
            this.a(var0, var4, 7, 1, 6, 9, 3, 6, c, c, false);
            this.a(var0, var4, 13, 1, 6, 15, 3, 6, c, c, false);
            this.a(var0, var4, 8, 1, 7, 9, 1, 7, c, c, false);
            this.a(var0, var4, 13, 1, 7, 14, 1, 7, c, c, false);
            this.a(var0, var4, 9, 0, 5, 13, 0, 5, c, c, false);
            this.a(var0, var4, 10, 0, 7, 12, 0, 7, d, d, false);
            this.a(var0, var4, 8, 0, 10, 8, 0, 12, d, d, false);
            this.a(var0, var4, 14, 0, 10, 14, 0, 12, d, d, false);

            for(int var7 = 18; var7 >= 7; var7 -= 3) {
               this.a(var0, i, 6, 3, var7, var4);
               this.a(var0, i, 16, 3, var7, var4);
            }

            this.a(var0, i, 10, 0, 10, var4);
            this.a(var0, i, 12, 0, 10, var4);
            this.a(var0, i, 10, 0, 12, var4);
            this.a(var0, i, 12, 0, 12, var4);
            this.a(var0, i, 8, 3, 6, var4);
            this.a(var0, i, 14, 3, 6, var4);
            this.a(var0, c, 4, 2, 4, var4);
            this.a(var0, i, 4, 1, 4, var4);
            this.a(var0, c, 4, 0, 4, var4);
            this.a(var0, c, 18, 2, 4, var4);
            this.a(var0, i, 18, 1, 4, var4);
            this.a(var0, c, 18, 0, 4, var4);
            this.a(var0, c, 4, 2, 18, var4);
            this.a(var0, i, 4, 1, 18, var4);
            this.a(var0, c, 4, 0, 18, var4);
            this.a(var0, c, 18, 2, 18, var4);
            this.a(var0, i, 18, 1, 18, var4);
            this.a(var0, c, 18, 0, 18, var4);
            this.a(var0, c, 9, 7, 20, var4);
            this.a(var0, c, 13, 7, 20, var4);
            this.a(var0, var4, 6, 0, 21, 7, 4, 21, c, c, false);
            this.a(var0, var4, 15, 0, 21, 16, 4, 21, c, c, false);
            this.a(var0, var4, 11, 2, 16);
         } else if (this.a == 1) {
            this.a(var0, var4, 9, 3, 18, 13, 3, 20, c, c, false);
            this.a(var0, var4, 9, 0, 18, 9, 2, 18, c, c, false);
            this.a(var0, var4, 13, 0, 18, 13, 2, 18, c, c, false);
            int var7 = 9;
            int var8 = 20;
            int var9 = 5;

            for(int var10 = 0; var10 < 2; ++var10) {
               this.a(var0, c, var7, 6, 20, var4);
               this.a(var0, i, var7, 5, 20, var4);
               this.a(var0, c, var7, 4, 20, var4);
               var7 = 13;
            }

            this.a(var0, var4, 7, 3, 7, 15, 3, 14, c, c, false);
            int var14 = 10;

            for(int var10 = 0; var10 < 2; ++var10) {
               this.a(var0, var4, var14, 0, 10, var14, 6, 10, c, c, false);
               this.a(var0, var4, var14, 0, 12, var14, 6, 12, c, c, false);
               this.a(var0, i, var14, 0, 10, var4);
               this.a(var0, i, var14, 0, 12, var4);
               this.a(var0, i, var14, 4, 10, var4);
               this.a(var0, i, var14, 4, 12, var4);
               var14 = 12;
            }

            var14 = 8;

            for(int var10 = 0; var10 < 2; ++var10) {
               this.a(var0, var4, var14, 0, 7, var14, 2, 7, c, c, false);
               this.a(var0, var4, var14, 0, 14, var14, 2, 14, c, c, false);
               var14 = 14;
            }

            this.a(var0, var4, 8, 3, 8, 8, 3, 13, d, d, false);
            this.a(var0, var4, 14, 3, 8, 14, 3, 13, d, d, false);
            this.a(var0, var4, 11, 5, 13);
         }
      }
   }

   static class v {
      final int a;
      final OceanMonumentPieces.v[] b = new OceanMonumentPieces.v[6];
      final boolean[] c = new boolean[6];
      boolean d;
      boolean e;
      private int f;

      public v(int var0) {
         this.a = var0;
      }

      public void a(EnumDirection var0, OceanMonumentPieces.v var1) {
         this.b[var0.d()] = var1;
         var1.b[var0.g().d()] = this;
      }

      public void a() {
         for(int var0 = 0; var0 < 6; ++var0) {
            this.c[var0] = this.b[var0] != null;
         }
      }

      public boolean a(int var0) {
         if (this.e) {
            return true;
         } else {
            this.f = var0;

            for(int var1 = 0; var1 < 6; ++var1) {
               if (this.b[var1] != null && this.c[var1] && this.b[var1].f != var0 && this.b[var1].a(var0)) {
                  return true;
               }
            }

            return false;
         }
      }

      public boolean b() {
         return this.a >= 75;
      }

      public int c() {
         int var0 = 0;

         for(int var1 = 0; var1 < 6; ++var1) {
            if (this.c[var1]) {
               ++var0;
            }
         }

         return var0;
      }
   }
}
