package net.minecraft.world.level.levelgen.structure.structures;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldAccess;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.loot.LootTables;

public class IglooPieces {
   public static final int a = 90;
   static final MinecraftKey b = new MinecraftKey("igloo/top");
   private static final MinecraftKey c = new MinecraftKey("igloo/middle");
   private static final MinecraftKey d = new MinecraftKey("igloo/bottom");
   static final Map<MinecraftKey, BlockPosition> e = ImmutableMap.of(
      b, new BlockPosition(3, 5, 5), c, new BlockPosition(1, 3, 1), d, new BlockPosition(3, 6, 7)
   );
   static final Map<MinecraftKey, BlockPosition> f = ImmutableMap.of(b, BlockPosition.b, c, new BlockPosition(2, -3, 4), d, new BlockPosition(0, -3, -2));

   public static void a(StructureTemplateManager var0, BlockPosition var1, EnumBlockRotation var2, StructurePieceAccessor var3, RandomSource var4) {
      if (var4.j() < 0.5) {
         int var5 = var4.a(8) + 4;
         var3.a(new IglooPieces.a(var0, d, var1, var2, var5 * 3));

         for(int var6 = 0; var6 < var5 - 1; ++var6) {
            var3.a(new IglooPieces.a(var0, c, var1, var2, var6 * 3));
         }
      }

      var3.a(new IglooPieces.a(var0, b, var1, var2, 0));
   }

   public static class a extends DefinedStructurePiece {
      public a(StructureTemplateManager var0, MinecraftKey var1, BlockPosition var2, EnumBlockRotation var3, int var4) {
         super(WorldGenFeatureStructurePieceType.I, 0, var0, var1, var1.toString(), a(var3, var1), a(var1, var2, var4));
      }

      public a(StructureTemplateManager var0, NBTTagCompound var1) {
         super(WorldGenFeatureStructurePieceType.I, var1, var0, var1x -> a(EnumBlockRotation.valueOf(var1.l("Rot")), var1x));
      }

      private static DefinedStructureInfo a(EnumBlockRotation var0, MinecraftKey var1) {
         return new DefinedStructureInfo().a(var0).a(EnumBlockMirror.a).a(IglooPieces.e.get(var1)).a(DefinedStructureProcessorBlockIgnore.b);
      }

      private static BlockPosition a(MinecraftKey var0, BlockPosition var1, int var2) {
         return var1.a(IglooPieces.f.get(var0)).c(var2);
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("Rot", this.c.d().name());
      }

      @Override
      protected void a(String var0, BlockPosition var1, WorldAccess var2, RandomSource var3, StructureBoundingBox var4) {
         if ("chest".equals(var0)) {
            var2.a(var1, Blocks.a.o(), 3);
            TileEntity var5 = var2.c_(var1.d());
            if (var5 instanceof TileEntityChest) {
               ((TileEntityChest)var5).a(LootTables.C, var3.g());
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
         MinecraftKey var7 = new MinecraftKey(this.a);
         DefinedStructureInfo var8 = a(this.c.d(), var7);
         BlockPosition var9 = IglooPieces.f.get(var7);
         BlockPosition var10 = this.d.a(DefinedStructure.a(var8, new BlockPosition(3 - var9.u(), 0, -var9.w())));
         int var11 = var0.a(HeightMap.Type.a, var10.u(), var10.w());
         BlockPosition var12 = this.d;
         this.d = this.d.b(0, var11 - 90 - 1, 0);
         super.a(var0, var1, var2, var3, var4, var5, var6);
         if (var7.equals(IglooPieces.b)) {
            BlockPosition var13 = this.d.a(DefinedStructure.a(var8, new BlockPosition(3, 0, 5)));
            IBlockData var14 = var0.a_(var13.d());
            if (!var14.h() && !var14.a(Blocks.cN)) {
               var0.a(var13, Blocks.dO.o(), 3);
            }
         }

         this.d = var12;
      }
   }
}
