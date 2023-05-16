package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.vehicle.EntityMinecartChest;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockFalling;
import net.minecraft.world.level.block.BlockFence;
import net.minecraft.world.level.block.BlockMinecartTrack;
import net.minecraft.world.level.block.BlockTorchWall;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityMobSpawner;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockPropertyTrackPosition;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;
import net.minecraft.world.level.storage.loot.LootTables;
import org.slf4j.Logger;

public class MineshaftPieces {
   static final Logger b = LogUtils.getLogger();
   private static final int c = 3;
   private static final int d = 3;
   private static final int e = 5;
   private static final int f = 20;
   private static final int g = 50;
   private static final int h = 8;
   public static final int a = 50;

   private static MineshaftPieces.c a(
      StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, @Nullable EnumDirection var5, int var6, MineshaftStructure.a var7
   ) {
      int var8 = var1.a(100);
      if (var8 >= 80) {
         StructureBoundingBox var9 = MineshaftPieces.b.a(var0, var1, var2, var3, var4, var5);
         if (var9 != null) {
            return new MineshaftPieces.b(var6, var9, var5, var7);
         }
      } else if (var8 >= 70) {
         StructureBoundingBox var9 = MineshaftPieces.e.a(var0, var1, var2, var3, var4, var5);
         if (var9 != null) {
            return new MineshaftPieces.e(var6, var9, var5, var7);
         }
      } else {
         StructureBoundingBox var9 = MineshaftPieces.a.a(var0, var1, var2, var3, var4, var5);
         if (var9 != null) {
            return new MineshaftPieces.a(var6, var1, var9, var5, var7);
         }
      }

      return null;
   }

   static MineshaftPieces.c a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2, int var3, int var4, int var5, EnumDirection var6, int var7) {
      if (var7 > 8) {
         return null;
      } else if (Math.abs(var3 - var0.f().g()) <= 80 && Math.abs(var5 - var0.f().i()) <= 80) {
         MineshaftStructure.a var8 = ((MineshaftPieces.c)var0).a;
         MineshaftPieces.c var9 = a(var1, var2, var3, var4, var5, var6, var7 + 1, var8);
         if (var9 != null) {
            var1.a(var9);
            var9.a(var0, var1, var2);
         }

         return var9;
      } else {
         return null;
      }
   }

   public static class a extends MineshaftPieces.c {
      private final boolean b;
      private final boolean c;
      private boolean d;
      private final int h;

      public a(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.a, var0);
         this.b = var0.q("hr");
         this.c = var0.q("sc");
         this.d = var0.q("hps");
         this.h = var0.h("Num");
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("hr", this.b);
         var1.a("sc", this.c);
         var1.a("hps", this.d);
         var1.a("Num", this.h);
      }

      public a(int var0, RandomSource var1, StructureBoundingBox var2, EnumDirection var3, MineshaftStructure.a var4) {
         super(WorldGenFeatureStructurePieceType.a, var0, var4, var2);
         this.a(var3);
         this.b = var1.a(3) == 0;
         this.c = !this.b && var1.a(23) == 0;
         if (this.i().o() == EnumDirection.EnumAxis.c) {
            this.h = var2.e() / 5;
         } else {
            this.h = var2.c() / 5;
         }
      }

      @Nullable
      public static StructureBoundingBox a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5) {
         for(int var6 = var1.a(3) + 2; var6 > 0; --var6) {
            int var8 = var6 * 5;

            StructureBoundingBox var7 = switch(var5) {
               default -> new StructureBoundingBox(0, 0, -(var8 - 1), 2, 2, 0);
               case d -> new StructureBoundingBox(0, 0, 0, 2, 2, var8 - 1);
               case e -> new StructureBoundingBox(-(var8 - 1), 0, 0, 0, 2, 2);
               case f -> new StructureBoundingBox(0, 0, 0, var8 - 1, 2, 2);
            };
            var7.a(var2, var3, var4);
            if (var0.a(var7) == null) {
               return var7;
            }
         }

         return null;
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         int var3 = this.g();
         int var4 = var2.a(4);
         EnumDirection var5 = this.i();
         if (var5 != null) {
            switch(var5) {
               case c:
               default:
                  if (var4 <= 1) {
                     MineshaftPieces.a(var0, var1, var2, this.f.g(), this.f.h() - 1 + var2.a(3), this.f.i() - 1, var5, var3);
                  } else if (var4 == 2) {
                     MineshaftPieces.a(var0, var1, var2, this.f.g() - 1, this.f.h() - 1 + var2.a(3), this.f.i(), EnumDirection.e, var3);
                  } else {
                     MineshaftPieces.a(var0, var1, var2, this.f.j() + 1, this.f.h() - 1 + var2.a(3), this.f.i(), EnumDirection.f, var3);
                  }
                  break;
               case d:
                  if (var4 <= 1) {
                     MineshaftPieces.a(var0, var1, var2, this.f.g(), this.f.h() - 1 + var2.a(3), this.f.l() + 1, var5, var3);
                  } else if (var4 == 2) {
                     MineshaftPieces.a(var0, var1, var2, this.f.g() - 1, this.f.h() - 1 + var2.a(3), this.f.l() - 3, EnumDirection.e, var3);
                  } else {
                     MineshaftPieces.a(var0, var1, var2, this.f.j() + 1, this.f.h() - 1 + var2.a(3), this.f.l() - 3, EnumDirection.f, var3);
                  }
                  break;
               case e:
                  if (var4 <= 1) {
                     MineshaftPieces.a(var0, var1, var2, this.f.g() - 1, this.f.h() - 1 + var2.a(3), this.f.i(), var5, var3);
                  } else if (var4 == 2) {
                     MineshaftPieces.a(var0, var1, var2, this.f.g(), this.f.h() - 1 + var2.a(3), this.f.i() - 1, EnumDirection.c, var3);
                  } else {
                     MineshaftPieces.a(var0, var1, var2, this.f.g(), this.f.h() - 1 + var2.a(3), this.f.l() + 1, EnumDirection.d, var3);
                  }
                  break;
               case f:
                  if (var4 <= 1) {
                     MineshaftPieces.a(var0, var1, var2, this.f.j() + 1, this.f.h() - 1 + var2.a(3), this.f.i(), var5, var3);
                  } else if (var4 == 2) {
                     MineshaftPieces.a(var0, var1, var2, this.f.j() - 3, this.f.h() - 1 + var2.a(3), this.f.i() - 1, EnumDirection.c, var3);
                  } else {
                     MineshaftPieces.a(var0, var1, var2, this.f.j() - 3, this.f.h() - 1 + var2.a(3), this.f.l() + 1, EnumDirection.d, var3);
                  }
            }
         }

         if (var3 < 8) {
            if (var5 != EnumDirection.c && var5 != EnumDirection.d) {
               for(int var6 = this.f.g() + 3; var6 + 3 <= this.f.j(); var6 += 5) {
                  int var7 = var2.a(5);
                  if (var7 == 0) {
                     MineshaftPieces.a(var0, var1, var2, var6, this.f.h(), this.f.i() - 1, EnumDirection.c, var3 + 1);
                  } else if (var7 == 1) {
                     MineshaftPieces.a(var0, var1, var2, var6, this.f.h(), this.f.l() + 1, EnumDirection.d, var3 + 1);
                  }
               }
            } else {
               for(int var6 = this.f.i() + 3; var6 + 3 <= this.f.l(); var6 += 5) {
                  int var7 = var2.a(5);
                  if (var7 == 0) {
                     MineshaftPieces.a(var0, var1, var2, this.f.g() - 1, this.f.h(), var6, EnumDirection.e, var3 + 1);
                  } else if (var7 == 1) {
                     MineshaftPieces.a(var0, var1, var2, this.f.j() + 1, this.f.h(), var6, EnumDirection.f, var3 + 1);
                  }
               }
            }
         }
      }

      @Override
      protected boolean a(GeneratorAccessSeed var0, StructureBoundingBox var1, RandomSource var2, int var3, int var4, int var5, MinecraftKey var6) {
         BlockPosition var7 = this.b(var3, var4, var5);
         if (var1.b(var7) && var0.a_(var7).h() && !var0.a_(var7.d()).h()) {
            IBlockData var8 = Blocks.cO.o().a(BlockMinecartTrack.d, var2.h() ? BlockPropertyTrackPosition.a : BlockPropertyTrackPosition.b);
            this.a(var0, var8, var3, var4, var5, var1);
            EntityMinecartChest var9 = new EntityMinecartChest(var0.C(), (double)var7.u() + 0.5, (double)var7.v() + 0.5, (double)var7.w() + 0.5);
            var9.a(var6, var2.g());
            var0.b(var9);
            return true;
         } else {
            return false;
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
         if (!this.a(var0, var4)) {
            int var7 = 0;
            int var8 = 2;
            int var9 = 0;
            int var10 = 2;
            int var11 = this.h * 5 - 1;
            IBlockData var12 = this.a.d();
            this.a(var0, var4, 0, 0, 0, 2, 1, var11, e, e, false);
            this.a(var0, var4, var3, 0.8F, 0, 2, 0, 2, 2, var11, e, e, false, false);
            if (this.c) {
               this.a(var0, var4, var3, 0.6F, 0, 0, 0, 2, 1, var11, Blocks.br.o(), e, false, true);
            }

            for(int var13 = 0; var13 < this.h; ++var13) {
               int var14 = 2 + var13 * 5;
               this.a(var0, var4, 0, 0, var14, 2, 2, var3);
               this.a(var0, var4, var3, 0.1F, 0, 2, var14 - 1);
               this.a(var0, var4, var3, 0.1F, 2, 2, var14 - 1);
               this.a(var0, var4, var3, 0.1F, 0, 2, var14 + 1);
               this.a(var0, var4, var3, 0.1F, 2, 2, var14 + 1);
               this.a(var0, var4, var3, 0.05F, 0, 2, var14 - 2);
               this.a(var0, var4, var3, 0.05F, 2, 2, var14 - 2);
               this.a(var0, var4, var3, 0.05F, 0, 2, var14 + 2);
               this.a(var0, var4, var3, 0.05F, 2, 2, var14 + 2);
               if (var3.a(100) == 0) {
                  this.a(var0, var4, var3, 2, 0, var14 - 1, LootTables.u);
               }

               if (var3.a(100) == 0) {
                  this.a(var0, var4, var3, 0, 0, var14 + 1, LootTables.u);
               }

               if (this.c && !this.d) {
                  int var15 = 1;
                  int var16 = var14 - 1 + var3.a(3);
                  BlockPosition var17 = this.b(1, 0, var16);
                  if (var4.b(var17) && this.b(var0, 1, 0, var16, var4)) {
                     this.d = true;
                     var0.a(var17, Blocks.cs.o(), 2);
                     TileEntity var18 = var0.c_(var17);
                     if (var18 instanceof TileEntityMobSpawner var19) {
                        var19.a(EntityTypes.n, var3);
                     }
                  }
               }
            }

            for(int var13 = 0; var13 <= 2; ++var13) {
               for(int var14 = 0; var14 <= var11; ++var14) {
                  this.a(var0, var4, var12, var13, -1, var14);
               }
            }

            int var13 = 2;
            this.a(var0, var4, 0, -1, 2);
            if (this.h > 1) {
               int var14 = var11 - 2;
               this.a(var0, var4, 0, -1, var14);
            }

            if (this.b) {
               IBlockData var14 = Blocks.cO.o().a(BlockMinecartTrack.d, BlockPropertyTrackPosition.a);

               for(int var15 = 0; var15 <= var11; ++var15) {
                  IBlockData var16 = this.a(var0, 1, -1, var15, var4);
                  if (!var16.h() && var16.i(var0, this.b(1, -1, var15))) {
                     float var17 = this.b(var0, 1, 0, var15, var4) ? 0.7F : 0.9F;
                     this.a(var0, var4, var3, var17, 1, 0, var15, var14);
                  }
               }
            }
         }
      }

      private void a(GeneratorAccessSeed var0, StructureBoundingBox var1, int var2, int var3, int var4) {
         IBlockData var5 = this.a.b();
         IBlockData var6 = this.a.d();
         if (this.a(var0, var2, var3, var4, var1).a(var6.b())) {
            this.c(var0, var5, var2, var3, var4, var1);
         }

         if (this.a(var0, var2 + 2, var3, var4, var1).a(var6.b())) {
            this.c(var0, var5, var2 + 2, var3, var4, var1);
         }
      }

      @Override
      protected void b(GeneratorAccessSeed var0, IBlockData var1, int var2, int var3, int var4, StructureBoundingBox var5) {
         BlockPosition.MutableBlockPosition var6 = this.b(var2, var3, var4);
         if (var5.b(var6)) {
            int var7 = var6.v();

            while(this.a(var0.a_(var6)) && var6.v() > var0.v_() + 1) {
               var6.c(EnumDirection.a);
            }

            if (this.a(var0, var6, var0.a_(var6))) {
               while(var6.v() < var7) {
                  var6.c(EnumDirection.b);
                  var0.a(var6, var1, 2);
               }
            }
         }
      }

      protected void c(GeneratorAccessSeed var0, IBlockData var1, int var2, int var3, int var4, StructureBoundingBox var5) {
         BlockPosition.MutableBlockPosition var6 = this.b(var2, var3, var4);
         if (var5.b(var6)) {
            int var7 = var6.v();
            int var8 = 1;
            boolean var9 = true;

            for(boolean var10 = true; var9 || var10; ++var8) {
               if (var9) {
                  var6.q(var7 - var8);
                  IBlockData var11 = var0.a_(var6);
                  boolean var12 = this.a(var11) && !var11.a(Blocks.H);
                  if (!var12 && this.a(var0, var6, var11)) {
                     a(var0, var1, var6, var7 - var8 + 1, var7);
                     return;
                  }

                  var9 = var8 <= 20 && var12 && var6.v() > var0.v_() + 1;
               }

               if (var10) {
                  var6.q(var7 + var8);
                  IBlockData var11 = var0.a_(var6);
                  boolean var12 = this.a(var11);
                  if (!var12 && this.b(var0, var6, var11)) {
                     var0.a(var6.q(var7 + 1), this.a.e(), 2);
                     a(var0, Blocks.eX.o(), var6, var7 + 2, var7 + var8);
                     return;
                  }

                  var10 = var8 <= 50 && var12 && var6.v() < var0.ai() - 1;
               }
            }
         }
      }

      private static void a(GeneratorAccessSeed var0, IBlockData var1, BlockPosition.MutableBlockPosition var2, int var3, int var4) {
         for(int var5 = var3; var5 < var4; ++var5) {
            var0.a(var2.q(var5), var1, 2);
         }
      }

      private boolean a(IWorldReader var0, BlockPosition var1, IBlockData var2) {
         return var2.d(var0, var1, EnumDirection.b);
      }

      private boolean b(IWorldReader var0, BlockPosition var1, IBlockData var2) {
         return Block.a(var0, var1, EnumDirection.a) && !(var2.b() instanceof BlockFalling);
      }

      private void a(GeneratorAccessSeed var0, StructureBoundingBox var1, int var2, int var3, int var4, int var5, int var6, RandomSource var7) {
         if (this.a(var0, var1, var2, var6, var5, var4)) {
            IBlockData var8 = this.a.d();
            IBlockData var9 = this.a.e();
            this.a(var0, var1, var2, var3, var4, var2, var5 - 1, var4, var9.a(BlockFence.d, Boolean.valueOf(true)), e, false);
            this.a(var0, var1, var6, var3, var4, var6, var5 - 1, var4, var9.a(BlockFence.b, Boolean.valueOf(true)), e, false);
            if (var7.a(4) == 0) {
               this.a(var0, var1, var2, var5, var4, var2, var5, var4, var8, e, false);
               this.a(var0, var1, var6, var5, var4, var6, var5, var4, var8, e, false);
            } else {
               this.a(var0, var1, var2, var5, var4, var6, var5, var4, var8, e, false);
               this.a(var0, var1, var7, 0.05F, var2 + 1, var5, var4 - 1, Blocks.cp.o().a(BlockTorchWall.a, EnumDirection.d));
               this.a(var0, var1, var7, 0.05F, var2 + 1, var5, var4 + 1, Blocks.cp.o().a(BlockTorchWall.a, EnumDirection.c));
            }
         }
      }

      private void a(GeneratorAccessSeed var0, StructureBoundingBox var1, RandomSource var2, float var3, int var4, int var5, int var6) {
         if (this.b(var0, var4, var5, var6, var1) && var2.i() < var3 && this.a(var0, var1, var4, var5, var6, 2)) {
            this.a(var0, Blocks.br.o(), var4, var5, var6, var1);
         }
      }

      private boolean a(GeneratorAccessSeed var0, StructureBoundingBox var1, int var2, int var3, int var4, int var5) {
         BlockPosition.MutableBlockPosition var6 = this.b(var2, var3, var4);
         int var7 = 0;

         for(EnumDirection var11 : EnumDirection.values()) {
            var6.c(var11);
            if (var1.b(var6) && var0.a_(var6).d(var0, var6, var11.g())) {
               if (++var7 >= var5) {
                  return true;
               }
            }

            var6.c(var11.g());
         }

         return false;
      }
   }

   public static class b extends MineshaftPieces.c {
      private final EnumDirection b;
      private final boolean c;

      public b(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.b, var0);
         this.c = var0.q("tf");
         this.b = EnumDirection.b(var0.h("D"));
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("tf", this.c);
         var1.a("D", this.b.e());
      }

      public b(int var0, StructureBoundingBox var1, @Nullable EnumDirection var2, MineshaftStructure.a var3) {
         super(WorldGenFeatureStructurePieceType.b, var0, var3, var1);
         this.b = var2;
         this.c = var1.d() > 3;
      }

      @Nullable
      public static StructureBoundingBox a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5) {
         int var6;
         if (var1.a(4) == 0) {
            var6 = 6;
         } else {
            var6 = 2;
         }
         StructureBoundingBox var7 = switch(var5) {
            default -> new StructureBoundingBox(-1, 0, -4, 3, var6, 0);
            case d -> new StructureBoundingBox(-1, 0, 0, 3, var6, 4);
            case e -> new StructureBoundingBox(-4, 0, -1, 0, var6, 3);
            case f -> new StructureBoundingBox(0, 0, -1, 4, var6, 3);
         };
         var7.a(var2, var3, var4);
         return var0.a(var7) != null ? null : var7;
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         int var3 = this.g();
         switch(this.b) {
            case c:
            default:
               MineshaftPieces.a(var0, var1, var2, this.f.g() + 1, this.f.h(), this.f.i() - 1, EnumDirection.c, var3);
               MineshaftPieces.a(var0, var1, var2, this.f.g() - 1, this.f.h(), this.f.i() + 1, EnumDirection.e, var3);
               MineshaftPieces.a(var0, var1, var2, this.f.j() + 1, this.f.h(), this.f.i() + 1, EnumDirection.f, var3);
               break;
            case d:
               MineshaftPieces.a(var0, var1, var2, this.f.g() + 1, this.f.h(), this.f.l() + 1, EnumDirection.d, var3);
               MineshaftPieces.a(var0, var1, var2, this.f.g() - 1, this.f.h(), this.f.i() + 1, EnumDirection.e, var3);
               MineshaftPieces.a(var0, var1, var2, this.f.j() + 1, this.f.h(), this.f.i() + 1, EnumDirection.f, var3);
               break;
            case e:
               MineshaftPieces.a(var0, var1, var2, this.f.g() + 1, this.f.h(), this.f.i() - 1, EnumDirection.c, var3);
               MineshaftPieces.a(var0, var1, var2, this.f.g() + 1, this.f.h(), this.f.l() + 1, EnumDirection.d, var3);
               MineshaftPieces.a(var0, var1, var2, this.f.g() - 1, this.f.h(), this.f.i() + 1, EnumDirection.e, var3);
               break;
            case f:
               MineshaftPieces.a(var0, var1, var2, this.f.g() + 1, this.f.h(), this.f.i() - 1, EnumDirection.c, var3);
               MineshaftPieces.a(var0, var1, var2, this.f.g() + 1, this.f.h(), this.f.l() + 1, EnumDirection.d, var3);
               MineshaftPieces.a(var0, var1, var2, this.f.j() + 1, this.f.h(), this.f.i() + 1, EnumDirection.f, var3);
         }

         if (this.c) {
            if (var2.h()) {
               MineshaftPieces.a(var0, var1, var2, this.f.g() + 1, this.f.h() + 3 + 1, this.f.i() - 1, EnumDirection.c, var3);
            }

            if (var2.h()) {
               MineshaftPieces.a(var0, var1, var2, this.f.g() - 1, this.f.h() + 3 + 1, this.f.i() + 1, EnumDirection.e, var3);
            }

            if (var2.h()) {
               MineshaftPieces.a(var0, var1, var2, this.f.j() + 1, this.f.h() + 3 + 1, this.f.i() + 1, EnumDirection.f, var3);
            }

            if (var2.h()) {
               MineshaftPieces.a(var0, var1, var2, this.f.g() + 1, this.f.h() + 3 + 1, this.f.l() + 1, EnumDirection.d, var3);
            }
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
         if (!this.a(var0, var4)) {
            IBlockData var7 = this.a.d();
            if (this.c) {
               this.a(var0, var4, this.f.g() + 1, this.f.h(), this.f.i(), this.f.j() - 1, this.f.h() + 3 - 1, this.f.l(), e, e, false);
               this.a(var0, var4, this.f.g(), this.f.h(), this.f.i() + 1, this.f.j(), this.f.h() + 3 - 1, this.f.l() - 1, e, e, false);
               this.a(var0, var4, this.f.g() + 1, this.f.k() - 2, this.f.i(), this.f.j() - 1, this.f.k(), this.f.l(), e, e, false);
               this.a(var0, var4, this.f.g(), this.f.k() - 2, this.f.i() + 1, this.f.j(), this.f.k(), this.f.l() - 1, e, e, false);
               this.a(var0, var4, this.f.g() + 1, this.f.h() + 3, this.f.i() + 1, this.f.j() - 1, this.f.h() + 3, this.f.l() - 1, e, e, false);
            } else {
               this.a(var0, var4, this.f.g() + 1, this.f.h(), this.f.i(), this.f.j() - 1, this.f.k(), this.f.l(), e, e, false);
               this.a(var0, var4, this.f.g(), this.f.h(), this.f.i() + 1, this.f.j(), this.f.k(), this.f.l() - 1, e, e, false);
            }

            this.a(var0, var4, this.f.g() + 1, this.f.h(), this.f.i() + 1, this.f.k());
            this.a(var0, var4, this.f.g() + 1, this.f.h(), this.f.l() - 1, this.f.k());
            this.a(var0, var4, this.f.j() - 1, this.f.h(), this.f.i() + 1, this.f.k());
            this.a(var0, var4, this.f.j() - 1, this.f.h(), this.f.l() - 1, this.f.k());
            int var8 = this.f.h() - 1;

            for(int var9 = this.f.g(); var9 <= this.f.j(); ++var9) {
               for(int var10 = this.f.i(); var10 <= this.f.l(); ++var10) {
                  this.a(var0, var4, var7, var9, var8, var10);
               }
            }
         }
      }

      private void a(GeneratorAccessSeed var0, StructureBoundingBox var1, int var2, int var3, int var4, int var5) {
         if (!this.a(var0, var2, var5 + 1, var4, var1).h()) {
            this.a(var0, var1, var2, var3, var4, var2, var5, var4, this.a.d(), e, false);
         }
      }
   }

   abstract static class c extends StructurePiece {
      protected MineshaftStructure.a a;

      public c(WorldGenFeatureStructurePieceType var0, int var1, MineshaftStructure.a var2, StructureBoundingBox var3) {
         super(var0, var1, var3);
         this.a = var2;
      }

      public c(WorldGenFeatureStructurePieceType var0, NBTTagCompound var1) {
         super(var0, var1);
         this.a = MineshaftStructure.a.a(var1.h("MST"));
      }

      @Override
      protected boolean a(IWorldReader var0, int var1, int var2, int var3, StructureBoundingBox var4) {
         IBlockData var5 = this.a(var0, var1, var2, var3, var4);
         return !var5.a(this.a.d().b()) && !var5.a(this.a.b().b()) && !var5.a(this.a.e().b()) && !var5.a(Blocks.eX);
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         var1.a("MST", this.a.ordinal());
      }

      protected boolean a(IBlockAccess var0, StructureBoundingBox var1, int var2, int var3, int var4, int var5) {
         for(int var6 = var2; var6 <= var3; ++var6) {
            if (this.a(var0, var6, var4 + 1, var5, var1).h()) {
               return false;
            }
         }

         return true;
      }

      protected boolean a(GeneratorAccess var0, StructureBoundingBox var1) {
         int var2 = Math.max(this.f.g() - 1, var1.g());
         int var3 = Math.max(this.f.h() - 1, var1.h());
         int var4 = Math.max(this.f.i() - 1, var1.i());
         int var5 = Math.min(this.f.j() + 1, var1.j());
         int var6 = Math.min(this.f.k() + 1, var1.k());
         int var7 = Math.min(this.f.l() + 1, var1.l());
         BlockPosition.MutableBlockPosition var8 = new BlockPosition.MutableBlockPosition((var2 + var5) / 2, (var3 + var6) / 2, (var4 + var7) / 2);
         if (var0.v(var8).a(BiomeTags.W)) {
            return true;
         } else {
            for(int var9 = var2; var9 <= var5; ++var9) {
               for(int var10 = var4; var10 <= var7; ++var10) {
                  if (var0.a_(var8.d(var9, var3, var10)).d().a()) {
                     return true;
                  }

                  if (var0.a_(var8.d(var9, var6, var10)).d().a()) {
                     return true;
                  }
               }
            }

            for(int var9 = var2; var9 <= var5; ++var9) {
               for(int var10 = var3; var10 <= var6; ++var10) {
                  if (var0.a_(var8.d(var9, var10, var4)).d().a()) {
                     return true;
                  }

                  if (var0.a_(var8.d(var9, var10, var7)).d().a()) {
                     return true;
                  }
               }
            }

            for(int var9 = var4; var9 <= var7; ++var9) {
               for(int var10 = var3; var10 <= var6; ++var10) {
                  if (var0.a_(var8.d(var2, var10, var9)).d().a()) {
                     return true;
                  }

                  if (var0.a_(var8.d(var5, var10, var9)).d().a()) {
                     return true;
                  }
               }
            }

            return false;
         }
      }

      protected void a(GeneratorAccessSeed var0, StructureBoundingBox var1, IBlockData var2, int var3, int var4, int var5) {
         if (this.b(var0, var3, var4, var5, var1)) {
            BlockPosition var6 = this.b(var3, var4, var5);
            IBlockData var7 = var0.a_(var6);
            if (!var7.d(var0, var6, EnumDirection.b)) {
               var0.a(var6, var2, 2);
            }
         }
      }
   }

   public static class d extends MineshaftPieces.c {
      private final List<StructureBoundingBox> b = Lists.newLinkedList();

      public d(int var0, RandomSource var1, int var2, int var3, MineshaftStructure.a var4) {
         super(
            WorldGenFeatureStructurePieceType.c,
            var0,
            var4,
            new StructureBoundingBox(var2, 50, var3, var2 + 7 + var1.a(6), 54 + var1.a(6), var3 + 7 + var1.a(6))
         );
         this.a = var4;
      }

      public d(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.c, var0);
         StructureBoundingBox.a.listOf().parse(DynamicOpsNBT.a, var0.c("Entrances", 11)).resultOrPartial(MineshaftPieces.b::error).ifPresent(this.b::addAll);
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         int var3 = this.g();
         int var5 = this.f.d() - 3 - 1;
         if (var5 <= 0) {
            var5 = 1;
         }

         int var4;
         for(var4 = 0; var4 < this.f.c(); var4 += 4) {
            var4 += var2.a(this.f.c());
            if (var4 + 3 > this.f.c()) {
               break;
            }

            MineshaftPieces.c var6 = MineshaftPieces.a(
               var0, var1, var2, this.f.g() + var4, this.f.h() + var2.a(var5) + 1, this.f.i() - 1, EnumDirection.c, var3
            );
            if (var6 != null) {
               StructureBoundingBox var7 = var6.f();
               this.b.add(new StructureBoundingBox(var7.g(), var7.h(), this.f.i(), var7.j(), var7.k(), this.f.i() + 1));
            }
         }

         for(var4 = 0; var4 < this.f.c(); var4 += 4) {
            var4 += var2.a(this.f.c());
            if (var4 + 3 > this.f.c()) {
               break;
            }

            MineshaftPieces.c var6 = MineshaftPieces.a(
               var0, var1, var2, this.f.g() + var4, this.f.h() + var2.a(var5) + 1, this.f.l() + 1, EnumDirection.d, var3
            );
            if (var6 != null) {
               StructureBoundingBox var7 = var6.f();
               this.b.add(new StructureBoundingBox(var7.g(), var7.h(), this.f.l() - 1, var7.j(), var7.k(), this.f.l()));
            }
         }

         for(var4 = 0; var4 < this.f.e(); var4 += 4) {
            var4 += var2.a(this.f.e());
            if (var4 + 3 > this.f.e()) {
               break;
            }

            MineshaftPieces.c var6 = MineshaftPieces.a(
               var0, var1, var2, this.f.g() - 1, this.f.h() + var2.a(var5) + 1, this.f.i() + var4, EnumDirection.e, var3
            );
            if (var6 != null) {
               StructureBoundingBox var7 = var6.f();
               this.b.add(new StructureBoundingBox(this.f.g(), var7.h(), var7.i(), this.f.g() + 1, var7.k(), var7.l()));
            }
         }

         for(var4 = 0; var4 < this.f.e(); var4 += 4) {
            var4 += var2.a(this.f.e());
            if (var4 + 3 > this.f.e()) {
               break;
            }

            StructurePiece var6 = MineshaftPieces.a(var0, var1, var2, this.f.j() + 1, this.f.h() + var2.a(var5) + 1, this.f.i() + var4, EnumDirection.f, var3);
            if (var6 != null) {
               StructureBoundingBox var7 = var6.f();
               this.b.add(new StructureBoundingBox(this.f.j() - 1, var7.h(), var7.i(), this.f.j(), var7.k(), var7.l()));
            }
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
         if (!this.a(var0, var4)) {
            this.a(var0, var4, this.f.g(), this.f.h() + 1, this.f.i(), this.f.j(), Math.min(this.f.h() + 3, this.f.k()), this.f.l(), e, e, false);

            for(StructureBoundingBox var8 : this.b) {
               this.a(var0, var4, var8.g(), var8.k() - 2, var8.i(), var8.j(), var8.k(), var8.l(), e, e, false);
            }

            this.a(var0, var4, this.f.g(), this.f.h() + 4, this.f.i(), this.f.j(), this.f.k(), this.f.l(), e, false);
         }
      }

      @Override
      public void a(int var0, int var1, int var2) {
         super.a(var0, var1, var2);

         for(StructureBoundingBox var4 : this.b) {
            var4.a(var0, var1, var2);
         }
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         StructureBoundingBox.a
            .listOf()
            .encodeStart(DynamicOpsNBT.a, this.b)
            .resultOrPartial(MineshaftPieces.b::error)
            .ifPresent(var1x -> var1.a("Entrances", var1x));
      }
   }

   public static class e extends MineshaftPieces.c {
      public e(int var0, StructureBoundingBox var1, EnumDirection var2, MineshaftStructure.a var3) {
         super(WorldGenFeatureStructurePieceType.d, var0, var3, var1);
         this.a(var2);
      }

      public e(NBTTagCompound var0) {
         super(WorldGenFeatureStructurePieceType.d, var0);
      }

      @Nullable
      public static StructureBoundingBox a(StructurePieceAccessor var0, RandomSource var1, int var2, int var3, int var4, EnumDirection var5) {
         StructureBoundingBox var6 = switch(var5) {
            default -> new StructureBoundingBox(0, -5, -8, 2, 2, 0);
            case d -> new StructureBoundingBox(0, -5, 0, 2, 2, 8);
            case e -> new StructureBoundingBox(-8, -5, 0, 0, 2, 2);
            case f -> new StructureBoundingBox(0, -5, 0, 8, 2, 2);
         };
         var6.a(var2, var3, var4);
         return var0.a(var6) != null ? null : var6;
      }

      @Override
      public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
         int var3 = this.g();
         EnumDirection var4 = this.i();
         if (var4 != null) {
            switch(var4) {
               case c:
               default:
                  MineshaftPieces.a(var0, var1, var2, this.f.g(), this.f.h(), this.f.i() - 1, EnumDirection.c, var3);
                  break;
               case d:
                  MineshaftPieces.a(var0, var1, var2, this.f.g(), this.f.h(), this.f.l() + 1, EnumDirection.d, var3);
                  break;
               case e:
                  MineshaftPieces.a(var0, var1, var2, this.f.g() - 1, this.f.h(), this.f.i(), EnumDirection.e, var3);
                  break;
               case f:
                  MineshaftPieces.a(var0, var1, var2, this.f.j() + 1, this.f.h(), this.f.i(), EnumDirection.f, var3);
            }
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
         if (!this.a(var0, var4)) {
            this.a(var0, var4, 0, 5, 0, 2, 7, 1, e, e, false);
            this.a(var0, var4, 0, 0, 7, 2, 2, 8, e, e, false);

            for(int var7 = 0; var7 < 5; ++var7) {
               this.a(var0, var4, 0, 5 - var7 - (var7 < 4 ? 1 : 0), 2 + var7, 2, 7 - var7, 2 + var7, e, e, false);
            }
         }
      }
   }
}
