package net.minecraft.world.level.levelgen.structure;

import com.google.common.collect.ImmutableSet;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockDispenser;
import net.minecraft.world.level.block.BlockFacingHorizontal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityChest;
import net.minecraft.world.level.block.entity.TileEntityDispenser;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;
import net.minecraft.world.level.material.Fluid;
import org.slf4j.Logger;

public abstract class StructurePiece {
   private static final Logger a = LogUtils.getLogger();
   protected static final IBlockData e = Blocks.mY.o();
   protected StructureBoundingBox f;
   @Nullable
   private EnumDirection b;
   private EnumBlockMirror c;
   private EnumBlockRotation d;
   protected int g;
   private final WorldGenFeatureStructurePieceType h;
   private static final Set<Block> i = ImmutableSet.builder()
      .add(Blocks.fn)
      .add(Blocks.co)
      .add(Blocks.cp)
      .add(Blocks.dT)
      .add(Blocks.kc)
      .add(Blocks.kh)
      .add(Blocks.kf)
      .add(Blocks.kd)
      .add(Blocks.ke)
      .add(Blocks.cN)
      .add(Blocks.eW)
      .build();

   protected StructurePiece(WorldGenFeatureStructurePieceType var0, int var1, StructureBoundingBox var2) {
      this.h = var0;
      this.g = var1;
      this.f = var2;
   }

   public StructurePiece(WorldGenFeatureStructurePieceType var0, NBTTagCompound var1) {
      this(
         var0,
         var1.h("GD"),
         (StructureBoundingBox)StructureBoundingBox.a
            .parse(DynamicOpsNBT.a, var1.c("BB"))
            .resultOrPartial(a::error)
            .orElseThrow(() -> new IllegalArgumentException("Invalid boundingbox"))
      );
      int var2 = var1.h("O");
      this.a(var2 == -1 ? null : EnumDirection.b(var2));
   }

   protected static StructureBoundingBox a(int var0, int var1, int var2, EnumDirection var3, int var4, int var5, int var6) {
      return var3.o() == EnumDirection.EnumAxis.c
         ? new StructureBoundingBox(var0, var1, var2, var0 + var4 - 1, var1 + var5 - 1, var2 + var6 - 1)
         : new StructureBoundingBox(var0, var1, var2, var0 + var6 - 1, var1 + var5 - 1, var2 + var4 - 1);
   }

   protected static EnumDirection a(RandomSource var0) {
      return EnumDirection.EnumDirectionLimit.a.a(var0);
   }

   public final NBTTagCompound a(StructurePieceSerializationContext var0) {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.a("id", BuiltInRegistries.S.b(this.k()).toString());
      StructureBoundingBox.a.encodeStart(DynamicOpsNBT.a, this.f).resultOrPartial(a::error).ifPresent(var1x -> var1.a("BB", var1x));
      EnumDirection var2 = this.i();
      var1.a("O", var2 == null ? -1 : var2.e());
      var1.a("GD", this.g);
      this.a(var0, var1);
      return var1;
   }

   protected abstract void a(StructurePieceSerializationContext var1, NBTTagCompound var2);

   public void a(StructurePiece var0, StructurePieceAccessor var1, RandomSource var2) {
   }

   public abstract void a(
      GeneratorAccessSeed var1,
      StructureManager var2,
      ChunkGenerator var3,
      RandomSource var4,
      StructureBoundingBox var5,
      ChunkCoordIntPair var6,
      BlockPosition var7
   );

   public StructureBoundingBox f() {
      return this.f;
   }

   public int g() {
      return this.g;
   }

   public void a(int var0) {
      this.g = var0;
   }

   public boolean a(ChunkCoordIntPair var0, int var1) {
      int var2 = var0.d();
      int var3 = var0.e();
      return this.f.a(var2 - var1, var3 - var1, var2 + 15 + var1, var3 + 15 + var1);
   }

   public BlockPosition h() {
      return new BlockPosition(this.f.f());
   }

   protected BlockPosition.MutableBlockPosition b(int var0, int var1, int var2) {
      return new BlockPosition.MutableBlockPosition(this.a(var0, var2), this.b(var1), this.b(var0, var2));
   }

   protected int a(int var0, int var1) {
      EnumDirection var2 = this.i();
      if (var2 == null) {
         return var0;
      } else {
         switch(var2) {
            case c:
            case d:
               return this.f.g() + var0;
            case e:
               return this.f.j() - var1;
            case f:
               return this.f.g() + var1;
            default:
               return var0;
         }
      }
   }

   protected int b(int var0) {
      return this.i() == null ? var0 : var0 + this.f.h();
   }

   protected int b(int var0, int var1) {
      EnumDirection var2 = this.i();
      if (var2 == null) {
         return var1;
      } else {
         switch(var2) {
            case c:
               return this.f.l() - var1;
            case d:
               return this.f.i() + var1;
            case e:
            case f:
               return this.f.i() + var0;
            default:
               return var1;
         }
      }
   }

   protected void a(GeneratorAccessSeed var0, IBlockData var1, int var2, int var3, int var4, StructureBoundingBox var5) {
      BlockPosition var6 = this.b(var2, var3, var4);
      if (var5.b(var6)) {
         if (this.a((IWorldReader)var0, var2, var3, var4, var5)) {
            if (this.c != EnumBlockMirror.a) {
               var1 = var1.a(this.c);
            }

            if (this.d != EnumBlockRotation.a) {
               var1 = var1.a(this.d);
            }

            var0.a(var6, var1, 2);
            Fluid var7 = var0.b_(var6);
            if (!var7.c()) {
               var0.a(var6, var7.a(), 0);
            }

            if (i.contains(var1.b())) {
               var0.A(var6).e(var6);
            }
         }
      }
   }

   protected boolean a(IWorldReader var0, int var1, int var2, int var3, StructureBoundingBox var4) {
      return true;
   }

   protected IBlockData a(IBlockAccess var0, int var1, int var2, int var3, StructureBoundingBox var4) {
      BlockPosition var5 = this.b(var1, var2, var3);
      return !var4.b(var5) ? Blocks.a.o() : var0.a_(var5);
   }

   protected boolean b(IWorldReader var0, int var1, int var2, int var3, StructureBoundingBox var4) {
      BlockPosition var5 = this.b(var1, var2 + 1, var3);
      if (!var4.b(var5)) {
         return false;
      } else {
         return var5.v() < var0.a(HeightMap.Type.c, var5.u(), var5.w());
      }
   }

   protected void a(GeneratorAccessSeed var0, StructureBoundingBox var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      for(int var8 = var3; var8 <= var6; ++var8) {
         for(int var9 = var2; var9 <= var5; ++var9) {
            for(int var10 = var4; var10 <= var7; ++var10) {
               this.a(var0, Blocks.a.o(), var9, var8, var10, var1);
            }
         }
      }
   }

   protected void a(
      GeneratorAccessSeed var0,
      StructureBoundingBox var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      IBlockData var8,
      IBlockData var9,
      boolean var10
   ) {
      for(int var11 = var3; var11 <= var6; ++var11) {
         for(int var12 = var2; var12 <= var5; ++var12) {
            for(int var13 = var4; var13 <= var7; ++var13) {
               if (!var10 || !this.a((IBlockAccess)var0, var12, var11, var13, var1).h()) {
                  if (var11 != var3 && var11 != var6 && var12 != var2 && var12 != var5 && var13 != var4 && var13 != var7) {
                     this.a(var0, var9, var12, var11, var13, var1);
                  } else {
                     this.a(var0, var8, var12, var11, var13, var1);
                  }
               }
            }
         }
      }
   }

   protected void a(GeneratorAccessSeed var0, StructureBoundingBox var1, StructureBoundingBox var2, IBlockData var3, IBlockData var4, boolean var5) {
      this.a(var0, var1, var2.g(), var2.h(), var2.i(), var2.j(), var2.k(), var2.l(), var3, var4, var5);
   }

   protected void a(
      GeneratorAccessSeed var0,
      StructureBoundingBox var1,
      int var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      boolean var8,
      RandomSource var9,
      StructurePiece.StructurePieceBlockSelector var10
   ) {
      for(int var11 = var3; var11 <= var6; ++var11) {
         for(int var12 = var2; var12 <= var5; ++var12) {
            for(int var13 = var4; var13 <= var7; ++var13) {
               if (!var8 || !this.a((IBlockAccess)var0, var12, var11, var13, var1).h()) {
                  var10.a(var9, var12, var11, var13, var11 == var3 || var11 == var6 || var12 == var2 || var12 == var5 || var13 == var4 || var13 == var7);
                  this.a(var0, var10.a(), var12, var11, var13, var1);
               }
            }
         }
      }
   }

   protected void a(
      GeneratorAccessSeed var0,
      StructureBoundingBox var1,
      StructureBoundingBox var2,
      boolean var3,
      RandomSource var4,
      StructurePiece.StructurePieceBlockSelector var5
   ) {
      this.a(var0, var1, var2.g(), var2.h(), var2.i(), var2.j(), var2.k(), var2.l(), var3, var4, var5);
   }

   protected void a(
      GeneratorAccessSeed var0,
      StructureBoundingBox var1,
      RandomSource var2,
      float var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      IBlockData var10,
      IBlockData var11,
      boolean var12,
      boolean var13
   ) {
      for(int var14 = var5; var14 <= var8; ++var14) {
         for(int var15 = var4; var15 <= var7; ++var15) {
            for(int var16 = var6; var16 <= var9; ++var16) {
               if (!(var2.i() > var3)
                  && (!var12 || !this.a((IBlockAccess)var0, var15, var14, var16, var1).h())
                  && (!var13 || this.b(var0, var15, var14, var16, var1))) {
                  if (var14 != var5 && var14 != var8 && var15 != var4 && var15 != var7 && var16 != var6 && var16 != var9) {
                     this.a(var0, var11, var15, var14, var16, var1);
                  } else {
                     this.a(var0, var10, var15, var14, var16, var1);
                  }
               }
            }
         }
      }
   }

   protected void a(GeneratorAccessSeed var0, StructureBoundingBox var1, RandomSource var2, float var3, int var4, int var5, int var6, IBlockData var7) {
      if (var2.i() < var3) {
         this.a(var0, var7, var4, var5, var6, var1);
      }
   }

   protected void a(
      GeneratorAccessSeed var0, StructureBoundingBox var1, int var2, int var3, int var4, int var5, int var6, int var7, IBlockData var8, boolean var9
   ) {
      float var10 = (float)(var5 - var2 + 1);
      float var11 = (float)(var6 - var3 + 1);
      float var12 = (float)(var7 - var4 + 1);
      float var13 = (float)var2 + var10 / 2.0F;
      float var14 = (float)var4 + var12 / 2.0F;

      for(int var15 = var3; var15 <= var6; ++var15) {
         float var16 = (float)(var15 - var3) / var11;

         for(int var17 = var2; var17 <= var5; ++var17) {
            float var18 = ((float)var17 - var13) / (var10 * 0.5F);

            for(int var19 = var4; var19 <= var7; ++var19) {
               float var20 = ((float)var19 - var14) / (var12 * 0.5F);
               if (!var9 || !this.a((IBlockAccess)var0, var17, var15, var19, var1).h()) {
                  float var21 = var18 * var18 + var16 * var16 + var20 * var20;
                  if (var21 <= 1.05F) {
                     this.a(var0, var8, var17, var15, var19, var1);
                  }
               }
            }
         }
      }
   }

   protected void b(GeneratorAccessSeed var0, IBlockData var1, int var2, int var3, int var4, StructureBoundingBox var5) {
      BlockPosition.MutableBlockPosition var6 = this.b(var2, var3, var4);
      if (var5.b(var6)) {
         while(this.a(var0.a_(var6)) && var6.v() > var0.v_() + 1) {
            var0.a(var6, var1, 2);
            var6.c(EnumDirection.a);
         }
      }
   }

   protected boolean a(IBlockData var0) {
      return var0.h() || var0.d().a() || var0.a(Blocks.ff) || var0.a(Blocks.bv) || var0.a(Blocks.bw);
   }

   protected boolean a(GeneratorAccessSeed var0, StructureBoundingBox var1, RandomSource var2, int var3, int var4, int var5, MinecraftKey var6) {
      return this.a(var0, var1, var2, this.b(var3, var4, var5), var6, null);
   }

   public static IBlockData a(IBlockAccess var0, BlockPosition var1, IBlockData var2) {
      EnumDirection var3 = null;

      for(EnumDirection var5 : EnumDirection.EnumDirectionLimit.a) {
         BlockPosition var6 = var1.a(var5);
         IBlockData var7 = var0.a_(var6);
         if (var7.a(Blocks.cu)) {
            return var2;
         }

         if (var7.i(var0, var6)) {
            if (var3 != null) {
               var3 = null;
               break;
            }

            var3 = var5;
         }
      }

      if (var3 != null) {
         return var2.a(BlockFacingHorizontal.aD, var3.g());
      } else {
         EnumDirection var4 = var2.c(BlockFacingHorizontal.aD);
         BlockPosition var5 = var1.a(var4);
         if (var0.a_(var5).i(var0, var5)) {
            var4 = var4.g();
            var5 = var1.a(var4);
         }

         if (var0.a_(var5).i(var0, var5)) {
            var4 = var4.h();
            var5 = var1.a(var4);
         }

         if (var0.a_(var5).i(var0, var5)) {
            var4 = var4.g();
            var5 = var1.a(var4);
         }

         return var2.a(BlockFacingHorizontal.aD, var4);
      }
   }

   protected boolean a(WorldAccess var0, StructureBoundingBox var1, RandomSource var2, BlockPosition var3, MinecraftKey var4, @Nullable IBlockData var5) {
      if (var1.b(var3) && !var0.a_(var3).a(Blocks.cu)) {
         if (var5 == null) {
            var5 = a(var0, var3, Blocks.cu.o());
         }

         var0.a(var3, var5, 2);
         TileEntity var6 = var0.c_(var3);
         if (var6 instanceof TileEntityChest) {
            ((TileEntityChest)var6).a(var4, var2.g());
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean a(
      GeneratorAccessSeed var0, StructureBoundingBox var1, RandomSource var2, int var3, int var4, int var5, EnumDirection var6, MinecraftKey var7
   ) {
      BlockPosition var8 = this.b(var3, var4, var5);
      if (var1.b(var8) && !var0.a_(var8).a(Blocks.aT)) {
         this.a(var0, Blocks.aT.o().a(BlockDispenser.a, var6), var3, var4, var5, var1);
         TileEntity var9 = var0.c_(var8);
         if (var9 instanceof TileEntityDispenser) {
            ((TileEntityDispenser)var9).a(var7, var2.g());
         }

         return true;
      } else {
         return false;
      }
   }

   public void a(int var0, int var1, int var2) {
      this.f.a(var0, var1, var2);
   }

   public static StructureBoundingBox a(Stream<StructurePiece> var0) {
      return StructureBoundingBox.b(var0.map(StructurePiece::f)::iterator)
         .orElseThrow(() -> new IllegalStateException("Unable to calculate boundingbox without pieces"));
   }

   @Nullable
   public static StructurePiece a(List<StructurePiece> var0, StructureBoundingBox var1) {
      for(StructurePiece var3 : var0) {
         if (var3.f().a(var1)) {
            return var3;
         }
      }

      return null;
   }

   @Nullable
   public EnumDirection i() {
      return this.b;
   }

   public void a(@Nullable EnumDirection var0) {
      this.b = var0;
      if (var0 == null) {
         this.d = EnumBlockRotation.a;
         this.c = EnumBlockMirror.a;
      } else {
         switch(var0) {
            case d:
               this.c = EnumBlockMirror.b;
               this.d = EnumBlockRotation.a;
               break;
            case e:
               this.c = EnumBlockMirror.b;
               this.d = EnumBlockRotation.b;
               break;
            case f:
               this.c = EnumBlockMirror.a;
               this.d = EnumBlockRotation.b;
               break;
            default:
               this.c = EnumBlockMirror.a;
               this.d = EnumBlockRotation.a;
         }
      }
   }

   public EnumBlockRotation a() {
      return this.d;
   }

   public EnumBlockMirror j() {
      return this.c;
   }

   public WorldGenFeatureStructurePieceType k() {
      return this.h;
   }

   public abstract static class StructurePieceBlockSelector {
      protected IBlockData a = Blocks.a.o();

      public abstract void a(RandomSource var1, int var2, int var3, int var4, boolean var5);

      public IBlockData a() {
         return this.a;
      }
   }
}
