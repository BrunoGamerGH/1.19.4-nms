package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.EnumDirection;
import net.minecraft.nbt.DynamicOpsNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.tags.TagsBlock;
import net.minecraft.util.INamable;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockLeaves;
import net.minecraft.world.level.block.BlockVine;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.block.state.properties.BlockStateBoolean;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.structure.DefinedStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureInfo;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorBlackstoneReplace;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorBlockAge;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorBlockIgnore;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorLavaSubmergedBlock;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorPredicates;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureTestBlock;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureTestRandomBlock;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureTestTrue;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.slf4j.Logger;

public class RuinedPortalPiece extends DefinedStructurePiece {
   private static final Logger h = LogUtils.getLogger();
   private static final float i = 0.3F;
   private static final float j = 0.07F;
   private static final float k = 0.2F;
   private final RuinedPortalPiece.b l;
   private final RuinedPortalPiece.a m;

   public RuinedPortalPiece(
      StructureTemplateManager var0,
      BlockPosition var1,
      RuinedPortalPiece.b var2,
      RuinedPortalPiece.a var3,
      MinecraftKey var4,
      DefinedStructure var5,
      EnumBlockRotation var6,
      EnumBlockMirror var7,
      BlockPosition var8
   ) {
      super(WorldGenFeatureStructurePieceType.J, 0, var0, var4, var4.toString(), a(var7, var6, var2, var8, var3), var1);
      this.l = var2;
      this.m = var3;
   }

   public RuinedPortalPiece(StructureTemplateManager var0, NBTTagCompound var1) {
      super(WorldGenFeatureStructurePieceType.J, var1, var0, var2x -> a(var0, var1, var2x));
      this.l = RuinedPortalPiece.b.a(var1.l("VerticalPlacement"));
      this.m = (RuinedPortalPiece.a)RuinedPortalPiece.a.a.parse(new Dynamic(DynamicOpsNBT.a, var1.c("Properties"))).getOrThrow(true, h::error);
   }

   @Override
   protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
      super.a(var0, var1);
      var1.a("Rotation", this.c.d().name());
      var1.a("Mirror", this.c.c().name());
      var1.a("VerticalPlacement", this.l.a());
      RuinedPortalPiece.a.a.encodeStart(DynamicOpsNBT.a, this.m).resultOrPartial(h::error).ifPresent(var1x -> var1.a("Properties", var1x));
   }

   private static DefinedStructureInfo a(StructureTemplateManager var0, NBTTagCompound var1, MinecraftKey var2) {
      DefinedStructure var3 = var0.a(var2);
      BlockPosition var4 = new BlockPosition(var3.a().u() / 2, 0, var3.a().w() / 2);
      return a(
         EnumBlockMirror.valueOf(var1.l("Mirror")),
         EnumBlockRotation.valueOf(var1.l("Rotation")),
         RuinedPortalPiece.b.a(var1.l("VerticalPlacement")),
         var4,
         (RuinedPortalPiece.a)RuinedPortalPiece.a.a.parse(new Dynamic(DynamicOpsNBT.a, var1.c("Properties"))).getOrThrow(true, h::error)
      );
   }

   private static DefinedStructureInfo a(EnumBlockMirror var0, EnumBlockRotation var1, RuinedPortalPiece.b var2, BlockPosition var3, RuinedPortalPiece.a var4) {
      DefinedStructureProcessorBlockIgnore var5 = var4.d ? DefinedStructureProcessorBlockIgnore.b : DefinedStructureProcessorBlockIgnore.d;
      List<DefinedStructureProcessorPredicates> var6 = Lists.newArrayList();
      var6.add(a(Blocks.cg, 0.3F, Blocks.a));
      var6.add(a(var2, var4));
      if (!var4.b) {
         var6.add(a(Blocks.dV, 0.07F, Blocks.kG));
      }

      DefinedStructureInfo var7 = new DefinedStructureInfo()
         .a(var1)
         .a(var0)
         .a(var3)
         .a(var5)
         .a(new DefinedStructureProcessorRule(var6))
         .a(new DefinedStructureProcessorBlockAge(var4.c))
         .a(new ProtectedBlockProcessor(TagsBlock.bC))
         .a(new DefinedStructureProcessorLavaSubmergedBlock());
      if (var4.g) {
         var7.a(DefinedStructureProcessorBlackstoneReplace.b);
      }

      return var7;
   }

   private static DefinedStructureProcessorPredicates a(RuinedPortalPiece.b var0, RuinedPortalPiece.a var1) {
      if (var0 == RuinedPortalPiece.b.c) {
         return a(Blocks.H, Blocks.kG);
      } else {
         return var1.b ? a(Blocks.H, Blocks.dV) : a(Blocks.H, 0.2F, Blocks.kG);
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
      StructureBoundingBox var7 = this.b.b(this.c, this.d);
      if (var4.b(var7.f())) {
         var4.b(var7);
         super.a(var0, var1, var2, var3, var4, var5, var6);
         this.b(var3, var0);
         this.a(var3, var0);
         if (this.m.f || this.m.e) {
            BlockPosition.a(this.f()).forEach(var2x -> {
               if (this.m.f) {
                  this.a(var3, (GeneratorAccess)var0, var2x);
               }

               if (this.m.e) {
                  this.b(var3, var0, var2x);
               }
            });
         }
      }
   }

   @Override
   protected void a(String var0, BlockPosition var1, WorldAccess var2, RandomSource var3, StructureBoundingBox var4) {
   }

   private void a(RandomSource var0, GeneratorAccess var1, BlockPosition var2) {
      IBlockData var3 = var1.a_(var2);
      if (!var3.h() && !var3.a(Blocks.fe)) {
         EnumDirection var4 = a(var0);
         BlockPosition var5 = var2.a(var4);
         IBlockData var6 = var1.a_(var5);
         if (var6.h()) {
            if (Block.a(var3.k(var1, var2), var4)) {
               BlockStateBoolean var7 = BlockVine.a(var4.g());
               var1.a(var5, Blocks.fe.o().a(var7, Boolean.valueOf(true)), 3);
            }
         }
      }
   }

   private void b(RandomSource var0, GeneratorAccess var1, BlockPosition var2) {
      if (var0.i() < 0.5F && var1.a_(var2).a(Blocks.dV) && var1.a_(var2.c()).h()) {
         var1.a(var2.c(), Blocks.aG.o().a(BlockLeaves.c, Boolean.valueOf(true)), 3);
      }
   }

   private void a(RandomSource var0, GeneratorAccess var1) {
      for(int var2 = this.f.g() + 1; var2 < this.f.j(); ++var2) {
         for(int var3 = this.f.i() + 1; var3 < this.f.l(); ++var3) {
            BlockPosition var4 = new BlockPosition(var2, this.f.h(), var3);
            if (var1.a_(var4).a(Blocks.dV)) {
               this.c(var0, var1, var4.d());
            }
         }
      }
   }

   private void c(RandomSource var0, GeneratorAccess var1, BlockPosition var2) {
      BlockPosition.MutableBlockPosition var3 = var2.j();
      this.d(var0, var1, var3);
      int var4 = 8;

      while(var4 > 0 && var0.i() < 0.5F) {
         var3.c(EnumDirection.a);
         --var4;
         this.d(var0, var1, var3);
      }
   }

   private void b(RandomSource var0, GeneratorAccess var1) {
      boolean var2 = this.l == RuinedPortalPiece.b.a || this.l == RuinedPortalPiece.b.c;
      BlockPosition var3 = this.f.f();
      int var4 = var3.u();
      int var5 = var3.w();
      float[] var6 = new float[]{1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.9F, 0.9F, 0.8F, 0.7F, 0.6F, 0.4F, 0.2F};
      int var7 = var6.length;
      int var8 = (this.f.c() + this.f.e()) / 2;
      int var9 = var0.a(Math.max(1, 8 - var8 / 2));
      int var10 = 3;
      BlockPosition.MutableBlockPosition var11 = BlockPosition.b.j();

      for(int var12 = var4 - var7; var12 <= var4 + var7; ++var12) {
         for(int var13 = var5 - var7; var13 <= var5 + var7; ++var13) {
            int var14 = Math.abs(var12 - var4) + Math.abs(var13 - var5);
            int var15 = Math.max(0, var14 + var9);
            if (var15 < var7) {
               float var16 = var6[var15];
               if (var0.j() < (double)var16) {
                  int var17 = a(var1, var12, var13, this.l);
                  int var18 = var2 ? var17 : Math.min(this.f.h(), var17);
                  var11.d(var12, var18, var13);
                  if (Math.abs(var18 - this.f.h()) <= 3 && this.a(var1, var11)) {
                     this.d(var0, var1, var11);
                     if (this.m.e) {
                        this.b(var0, var1, var11);
                     }

                     this.c(var0, var1, var11.d());
                  }
               }
            }
         }
      }
   }

   private boolean a(GeneratorAccess var0, BlockPosition var1) {
      IBlockData var2 = var0.a_(var1);
      return !var2.a(Blocks.a) && !var2.a(Blocks.cn) && !var2.a(TagsBlock.bC) && (this.l == RuinedPortalPiece.b.f || !var2.a(Blocks.H));
   }

   private void d(RandomSource var0, GeneratorAccess var1, BlockPosition var2) {
      if (!this.m.b && var0.i() < 0.07F) {
         var1.a(var2, Blocks.kG.o(), 3);
      } else {
         var1.a(var2, Blocks.dV.o(), 3);
      }
   }

   private static int a(GeneratorAccess var0, int var1, int var2, RuinedPortalPiece.b var3) {
      return var0.a(a(var3), var1, var2) - 1;
   }

   public static HeightMap.Type a(RuinedPortalPiece.b var0) {
      return var0 == RuinedPortalPiece.b.c ? HeightMap.Type.c : HeightMap.Type.a;
   }

   private static DefinedStructureProcessorPredicates a(Block var0, float var1, Block var2) {
      return new DefinedStructureProcessorPredicates(new DefinedStructureTestRandomBlock(var0, var1), DefinedStructureTestTrue.b, var2.o());
   }

   private static DefinedStructureProcessorPredicates a(Block var0, Block var1) {
      return new DefinedStructureProcessorPredicates(new DefinedStructureTestBlock(var0), DefinedStructureTestTrue.b, var1.o());
   }

   public static class a {
      public static final Codec<RuinedPortalPiece.a> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.BOOL.fieldOf("cold").forGetter(var0x -> var0x.b),
                  Codec.FLOAT.fieldOf("mossiness").forGetter(var0x -> var0x.c),
                  Codec.BOOL.fieldOf("air_pocket").forGetter(var0x -> var0x.d),
                  Codec.BOOL.fieldOf("overgrown").forGetter(var0x -> var0x.e),
                  Codec.BOOL.fieldOf("vines").forGetter(var0x -> var0x.f),
                  Codec.BOOL.fieldOf("replace_with_blackstone").forGetter(var0x -> var0x.g)
               )
               .apply(var0, RuinedPortalPiece.a::new)
      );
      public boolean b;
      public float c;
      public boolean d;
      public boolean e;
      public boolean f;
      public boolean g;

      public a() {
      }

      public a(boolean var0, float var1, boolean var2, boolean var3, boolean var4, boolean var5) {
         this.b = var0;
         this.c = var1;
         this.d = var2;
         this.e = var3;
         this.f = var4;
         this.g = var5;
      }
   }

   public static enum b implements INamable {
      a("on_land_surface"),
      b("partly_buried"),
      c("on_ocean_floor"),
      d("in_mountain"),
      e("underground"),
      f("in_nether");

      public static final INamable.a<RuinedPortalPiece.b> g = INamable.a(RuinedPortalPiece.b::values);
      private final String h;

      private b(String var2) {
         this.h = var2;
      }

      public String a() {
         return this.h;
      }

      public static RuinedPortalPiece.b a(String var0) {
         return g.a(var0);
      }

      @Override
      public String c() {
         return this.h;
      }
   }
}
