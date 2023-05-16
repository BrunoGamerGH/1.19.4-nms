package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.tags.TagsBlock;
import net.minecraft.tags.TagsFluid;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EnumMobSpawn;
import net.minecraft.world.entity.monster.EntityDrowned;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.IBlockAccess;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.World;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.BlockChest;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.block.entity.TileEntity;
import net.minecraft.world.level.block.entity.TileEntityChest;
import net.minecraft.world.level.block.state.IBlockData;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.structure.DefinedStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureInfo;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorBlockIgnore;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorRotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.LootTables;

public class OceanRuinPieces {
   private static final MinecraftKey[] a = new MinecraftKey[]{
      new MinecraftKey("underwater_ruin/warm_1"),
      new MinecraftKey("underwater_ruin/warm_2"),
      new MinecraftKey("underwater_ruin/warm_3"),
      new MinecraftKey("underwater_ruin/warm_4"),
      new MinecraftKey("underwater_ruin/warm_5"),
      new MinecraftKey("underwater_ruin/warm_6"),
      new MinecraftKey("underwater_ruin/warm_7"),
      new MinecraftKey("underwater_ruin/warm_8")
   };
   private static final MinecraftKey[] b = new MinecraftKey[]{
      new MinecraftKey("underwater_ruin/brick_1"),
      new MinecraftKey("underwater_ruin/brick_2"),
      new MinecraftKey("underwater_ruin/brick_3"),
      new MinecraftKey("underwater_ruin/brick_4"),
      new MinecraftKey("underwater_ruin/brick_5"),
      new MinecraftKey("underwater_ruin/brick_6"),
      new MinecraftKey("underwater_ruin/brick_7"),
      new MinecraftKey("underwater_ruin/brick_8")
   };
   private static final MinecraftKey[] c = new MinecraftKey[]{
      new MinecraftKey("underwater_ruin/cracked_1"),
      new MinecraftKey("underwater_ruin/cracked_2"),
      new MinecraftKey("underwater_ruin/cracked_3"),
      new MinecraftKey("underwater_ruin/cracked_4"),
      new MinecraftKey("underwater_ruin/cracked_5"),
      new MinecraftKey("underwater_ruin/cracked_6"),
      new MinecraftKey("underwater_ruin/cracked_7"),
      new MinecraftKey("underwater_ruin/cracked_8")
   };
   private static final MinecraftKey[] d = new MinecraftKey[]{
      new MinecraftKey("underwater_ruin/mossy_1"),
      new MinecraftKey("underwater_ruin/mossy_2"),
      new MinecraftKey("underwater_ruin/mossy_3"),
      new MinecraftKey("underwater_ruin/mossy_4"),
      new MinecraftKey("underwater_ruin/mossy_5"),
      new MinecraftKey("underwater_ruin/mossy_6"),
      new MinecraftKey("underwater_ruin/mossy_7"),
      new MinecraftKey("underwater_ruin/mossy_8")
   };
   private static final MinecraftKey[] e = new MinecraftKey[]{
      new MinecraftKey("underwater_ruin/big_brick_1"),
      new MinecraftKey("underwater_ruin/big_brick_2"),
      new MinecraftKey("underwater_ruin/big_brick_3"),
      new MinecraftKey("underwater_ruin/big_brick_8")
   };
   private static final MinecraftKey[] f = new MinecraftKey[]{
      new MinecraftKey("underwater_ruin/big_mossy_1"),
      new MinecraftKey("underwater_ruin/big_mossy_2"),
      new MinecraftKey("underwater_ruin/big_mossy_3"),
      new MinecraftKey("underwater_ruin/big_mossy_8")
   };
   private static final MinecraftKey[] g = new MinecraftKey[]{
      new MinecraftKey("underwater_ruin/big_cracked_1"),
      new MinecraftKey("underwater_ruin/big_cracked_2"),
      new MinecraftKey("underwater_ruin/big_cracked_3"),
      new MinecraftKey("underwater_ruin/big_cracked_8")
   };
   private static final MinecraftKey[] h = new MinecraftKey[]{
      new MinecraftKey("underwater_ruin/big_warm_4"),
      new MinecraftKey("underwater_ruin/big_warm_5"),
      new MinecraftKey("underwater_ruin/big_warm_6"),
      new MinecraftKey("underwater_ruin/big_warm_7")
   };

   private static MinecraftKey a(RandomSource var0) {
      return SystemUtils.a(a, var0);
   }

   private static MinecraftKey b(RandomSource var0) {
      return SystemUtils.a(h, var0);
   }

   public static void a(
      StructureTemplateManager var0, BlockPosition var1, EnumBlockRotation var2, StructurePieceAccessor var3, RandomSource var4, OceanRuinStructure var5
   ) {
      boolean var6 = var4.i() <= var5.f;
      float var7 = var6 ? 0.9F : 0.8F;
      a(var0, var1, var2, var3, var4, var5, var6, var7);
      if (var6 && var4.i() <= var5.g) {
         a(var0, var4, var2, var1, var5, var3);
      }
   }

   private static void a(
      StructureTemplateManager var0, RandomSource var1, EnumBlockRotation var2, BlockPosition var3, OceanRuinStructure var4, StructurePieceAccessor var5
   ) {
      BlockPosition var6 = new BlockPosition(var3.u(), 90, var3.w());
      BlockPosition var7 = DefinedStructure.a(new BlockPosition(15, 0, 15), EnumBlockMirror.a, var2, BlockPosition.b).a(var6);
      StructureBoundingBox var8 = StructureBoundingBox.a(var6, var7);
      BlockPosition var9 = new BlockPosition(Math.min(var6.u(), var7.u()), var6.v(), Math.min(var6.w(), var7.w()));
      List<BlockPosition> var10 = a(var1, var9);
      int var11 = MathHelper.a(var1, 4, 8);

      for(int var12 = 0; var12 < var11; ++var12) {
         if (!var10.isEmpty()) {
            int var13 = var1.a(var10.size());
            BlockPosition var14 = var10.remove(var13);
            EnumBlockRotation var15 = EnumBlockRotation.a(var1);
            BlockPosition var16 = DefinedStructure.a(new BlockPosition(5, 0, 6), EnumBlockMirror.a, var15, BlockPosition.b).a(var14);
            StructureBoundingBox var17 = StructureBoundingBox.a(var14, var16);
            if (!var17.a(var8)) {
               a(var0, var14, var15, var5, var1, var4, false, 0.8F);
            }
         }
      }
   }

   private static List<BlockPosition> a(RandomSource var0, BlockPosition var1) {
      List<BlockPosition> var2 = Lists.newArrayList();
      var2.add(var1.b(-16 + MathHelper.a(var0, 1, 8), 0, 16 + MathHelper.a(var0, 1, 7)));
      var2.add(var1.b(-16 + MathHelper.a(var0, 1, 8), 0, MathHelper.a(var0, 1, 7)));
      var2.add(var1.b(-16 + MathHelper.a(var0, 1, 8), 0, -16 + MathHelper.a(var0, 4, 8)));
      var2.add(var1.b(MathHelper.a(var0, 1, 7), 0, 16 + MathHelper.a(var0, 1, 7)));
      var2.add(var1.b(MathHelper.a(var0, 1, 7), 0, -16 + MathHelper.a(var0, 4, 6)));
      var2.add(var1.b(16 + MathHelper.a(var0, 1, 7), 0, 16 + MathHelper.a(var0, 3, 8)));
      var2.add(var1.b(16 + MathHelper.a(var0, 1, 7), 0, MathHelper.a(var0, 1, 7)));
      var2.add(var1.b(16 + MathHelper.a(var0, 1, 7), 0, -16 + MathHelper.a(var0, 4, 8)));
      return var2;
   }

   private static void a(
      StructureTemplateManager var0,
      BlockPosition var1,
      EnumBlockRotation var2,
      StructurePieceAccessor var3,
      RandomSource var4,
      OceanRuinStructure var5,
      boolean var6,
      float var7
   ) {
      switch(var5.e) {
         case a:
         default:
            MinecraftKey var8 = var6 ? b(var4) : a(var4);
            var3.a(new OceanRuinPieces.a(var0, var8, var1, var2, var7, var5.e, var6));
            break;
         case b:
            MinecraftKey[] var9 = var6 ? e : b;
            MinecraftKey[] var10 = var6 ? g : c;
            MinecraftKey[] var11 = var6 ? f : d;
            int var12 = var4.a(var9.length);
            var3.a(new OceanRuinPieces.a(var0, var9[var12], var1, var2, var7, var5.e, var6));
            var3.a(new OceanRuinPieces.a(var0, var10[var12], var1, var2, 0.7F, var5.e, var6));
            var3.a(new OceanRuinPieces.a(var0, var11[var12], var1, var2, 0.5F, var5.e, var6));
      }
   }

   public static class a extends DefinedStructurePiece {
      private final OceanRuinStructure.a h;
      private final float i;
      private final boolean j;

      public a(
         StructureTemplateManager var0, MinecraftKey var1, BlockPosition var2, EnumBlockRotation var3, float var4, OceanRuinStructure.a var5, boolean var6
      ) {
         super(WorldGenFeatureStructurePieceType.H, 0, var0, var1, var1.toString(), a(var3), var2);
         this.i = var4;
         this.h = var5;
         this.j = var6;
      }

      public a(StructureTemplateManager var0, NBTTagCompound var1) {
         super(WorldGenFeatureStructurePieceType.H, var1, var0, var1x -> a(EnumBlockRotation.valueOf(var1.l("Rot"))));
         this.i = var1.j("Integrity");
         this.h = OceanRuinStructure.a.valueOf(var1.l("BiomeType"));
         this.j = var1.q("IsLarge");
      }

      private static DefinedStructureInfo a(EnumBlockRotation var0) {
         return new DefinedStructureInfo().a(var0).a(EnumBlockMirror.a).a(DefinedStructureProcessorBlockIgnore.d);
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("Rot", this.c.d().name());
         var1.a("Integrity", this.i);
         var1.a("BiomeType", this.h.toString());
         var1.a("IsLarge", this.j);
      }

      @Override
      protected void a(String var0, BlockPosition var1, WorldAccess var2, RandomSource var3, StructureBoundingBox var4) {
         if ("chest".equals(var0)) {
            var2.a(var1, Blocks.cu.o().a(BlockChest.d, Boolean.valueOf(var2.b_(var1).a(TagsFluid.a))), 2);
            TileEntity var5 = var2.c_(var1);
            if (var5 instanceof TileEntityChest) {
               ((TileEntityChest)var5).a(this.j ? LootTables.F : LootTables.E, var3.g());
            }
         } else if ("drowned".equals(var0)) {
            EntityDrowned var5 = EntityTypes.y.a((World)var2.C());
            if (var5 != null) {
               var5.fz();
               var5.a(var1, 0.0F, 0.0F);
               var5.a(var2, var2.d_(var1), EnumMobSpawn.d, null, null);
               var2.a_(var5);
               if (var1.v() > var2.m_()) {
                  var2.a(var1, Blocks.a.o(), 2);
               } else {
                  var2.a(var1, Blocks.G.o(), 2);
               }
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
         this.c.b().a(new DefinedStructureProcessorRotation(this.i)).a(DefinedStructureProcessorBlockIgnore.d);
         int var7 = var0.a(HeightMap.Type.c, this.d.u(), this.d.w());
         this.d = new BlockPosition(this.d.u(), var7, this.d.w());
         BlockPosition var8 = DefinedStructure.a(new BlockPosition(this.b.a().u() - 1, 0, this.b.a().w() - 1), EnumBlockMirror.a, this.c.d(), BlockPosition.b)
            .a(this.d);
         this.d = new BlockPosition(this.d.u(), this.a(this.d, var0, var8), this.d.w());
         super.a(var0, var1, var2, var3, var4, var5, var6);
      }

      private int a(BlockPosition var0, IBlockAccess var1, BlockPosition var2) {
         int var3 = var0.v();
         int var4 = 512;
         int var5 = var3 - 1;
         int var6 = 0;

         for(BlockPosition var8 : BlockPosition.a(var0, var2)) {
            int var9 = var8.u();
            int var10 = var8.w();
            int var11 = var0.v() - 1;
            BlockPosition.MutableBlockPosition var12 = new BlockPosition.MutableBlockPosition(var9, var11, var10);
            IBlockData var13 = var1.a_(var12);

            for(Fluid var14 = var1.b_(var12); (var13.h() || var14.a(TagsFluid.a) || var13.a(TagsBlock.aj)) && var11 > var1.v_() + 1; var14 = var1.b_(var12)) {
               var12.d(var9, --var11, var10);
               var13 = var1.a_(var12);
            }

            var4 = Math.min(var4, var11);
            if (var11 < var5 - 2) {
               ++var6;
            }
         }

         int var7 = Math.abs(var0.u() - var2.u());
         if (var5 - var4 > 2 && var6 > var7 - 2) {
            var3 = var4 + 1;
         }

         return var3;
      }
   }
}
