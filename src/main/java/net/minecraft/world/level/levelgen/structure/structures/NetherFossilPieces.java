package net.minecraft.world.level.levelgen.structure.structures;

import net.minecraft.SystemUtils;
import net.minecraft.core.BlockPosition;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkCoordIntPair;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldAccess;
import net.minecraft.world.level.block.EnumBlockMirror;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.DefinedStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureInfo;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorBlockIgnore;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class NetherFossilPieces {
   private static final MinecraftKey[] a = new MinecraftKey[]{
      new MinecraftKey("nether_fossils/fossil_1"),
      new MinecraftKey("nether_fossils/fossil_2"),
      new MinecraftKey("nether_fossils/fossil_3"),
      new MinecraftKey("nether_fossils/fossil_4"),
      new MinecraftKey("nether_fossils/fossil_5"),
      new MinecraftKey("nether_fossils/fossil_6"),
      new MinecraftKey("nether_fossils/fossil_7"),
      new MinecraftKey("nether_fossils/fossil_8"),
      new MinecraftKey("nether_fossils/fossil_9"),
      new MinecraftKey("nether_fossils/fossil_10"),
      new MinecraftKey("nether_fossils/fossil_11"),
      new MinecraftKey("nether_fossils/fossil_12"),
      new MinecraftKey("nether_fossils/fossil_13"),
      new MinecraftKey("nether_fossils/fossil_14")
   };

   public static void a(StructureTemplateManager var0, StructurePieceAccessor var1, RandomSource var2, BlockPosition var3) {
      EnumBlockRotation var4 = EnumBlockRotation.a(var2);
      var1.a(new NetherFossilPieces.a(var0, SystemUtils.a(a, var2), var3, var4));
   }

   public static class a extends DefinedStructurePiece {
      public a(StructureTemplateManager var0, MinecraftKey var1, BlockPosition var2, EnumBlockRotation var3) {
         super(WorldGenFeatureStructurePieceType.ac, 0, var0, var1, var1.toString(), a(var3), var2);
      }

      public a(StructureTemplateManager var0, NBTTagCompound var1) {
         super(WorldGenFeatureStructurePieceType.ac, var1, var0, var1x -> a(EnumBlockRotation.valueOf(var1.l("Rot"))));
      }

      private static DefinedStructureInfo a(EnumBlockRotation var0) {
         return new DefinedStructureInfo().a(var0).a(EnumBlockMirror.a).a(DefinedStructureProcessorBlockIgnore.d);
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("Rot", this.c.d().name());
      }

      @Override
      protected void a(String var0, BlockPosition var1, WorldAccess var2, RandomSource var3, StructureBoundingBox var4) {
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
         var4.b(this.b.b(this.c, this.d));
         super.a(var0, var1, var2, var3, var4, var5, var6);
      }
   }
}
