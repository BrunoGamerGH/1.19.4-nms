package net.minecraft.world.level.levelgen.structure.structures;

import java.util.Map;
import net.minecraft.SystemUtils;
import net.minecraft.core.BaseBlockPosition;
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
import net.minecraft.world.level.block.entity.TileEntityLootable;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.HeightMap;
import net.minecraft.world.level.levelgen.structure.DefinedStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.WorldGenFeatureStructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureInfo;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorBlockIgnore;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.loot.LootTables;

public class ShipwreckPieces {
   static final BlockPosition a = new BlockPosition(4, 0, 15);
   private static final MinecraftKey[] b = new MinecraftKey[]{
      new MinecraftKey("shipwreck/with_mast"),
      new MinecraftKey("shipwreck/sideways_full"),
      new MinecraftKey("shipwreck/sideways_fronthalf"),
      new MinecraftKey("shipwreck/sideways_backhalf"),
      new MinecraftKey("shipwreck/rightsideup_full"),
      new MinecraftKey("shipwreck/rightsideup_fronthalf"),
      new MinecraftKey("shipwreck/rightsideup_backhalf"),
      new MinecraftKey("shipwreck/with_mast_degraded"),
      new MinecraftKey("shipwreck/rightsideup_full_degraded"),
      new MinecraftKey("shipwreck/rightsideup_fronthalf_degraded"),
      new MinecraftKey("shipwreck/rightsideup_backhalf_degraded")
   };
   private static final MinecraftKey[] c = new MinecraftKey[]{
      new MinecraftKey("shipwreck/with_mast"),
      new MinecraftKey("shipwreck/upsidedown_full"),
      new MinecraftKey("shipwreck/upsidedown_fronthalf"),
      new MinecraftKey("shipwreck/upsidedown_backhalf"),
      new MinecraftKey("shipwreck/sideways_full"),
      new MinecraftKey("shipwreck/sideways_fronthalf"),
      new MinecraftKey("shipwreck/sideways_backhalf"),
      new MinecraftKey("shipwreck/rightsideup_full"),
      new MinecraftKey("shipwreck/rightsideup_fronthalf"),
      new MinecraftKey("shipwreck/rightsideup_backhalf"),
      new MinecraftKey("shipwreck/with_mast_degraded"),
      new MinecraftKey("shipwreck/upsidedown_full_degraded"),
      new MinecraftKey("shipwreck/upsidedown_fronthalf_degraded"),
      new MinecraftKey("shipwreck/upsidedown_backhalf_degraded"),
      new MinecraftKey("shipwreck/sideways_full_degraded"),
      new MinecraftKey("shipwreck/sideways_fronthalf_degraded"),
      new MinecraftKey("shipwreck/sideways_backhalf_degraded"),
      new MinecraftKey("shipwreck/rightsideup_full_degraded"),
      new MinecraftKey("shipwreck/rightsideup_fronthalf_degraded"),
      new MinecraftKey("shipwreck/rightsideup_backhalf_degraded")
   };
   static final Map<String, MinecraftKey> d = Map.of("map_chest", LootTables.H, "treasure_chest", LootTables.J, "supply_chest", LootTables.I);

   public static void a(
      StructureTemplateManager var0, BlockPosition var1, EnumBlockRotation var2, StructurePieceAccessor var3, RandomSource var4, boolean var5
   ) {
      MinecraftKey var6 = SystemUtils.a(var5 ? b : c, var4);
      var3.a(new ShipwreckPieces.a(var0, var6, var1, var2, var5));
   }

   public static class a extends DefinedStructurePiece {
      private final boolean h;

      public a(StructureTemplateManager var0, MinecraftKey var1, BlockPosition var2, EnumBlockRotation var3, boolean var4) {
         super(WorldGenFeatureStructurePieceType.ab, 0, var0, var1, var1.toString(), a(var3), var2);
         this.h = var4;
      }

      public a(StructureTemplateManager var0, NBTTagCompound var1) {
         super(WorldGenFeatureStructurePieceType.ab, var1, var0, var1x -> a(EnumBlockRotation.valueOf(var1.l("Rot"))));
         this.h = var1.q("isBeached");
      }

      @Override
      protected void a(StructurePieceSerializationContext var0, NBTTagCompound var1) {
         super.a(var0, var1);
         var1.a("isBeached", this.h);
         var1.a("Rot", this.c.d().name());
      }

      private static DefinedStructureInfo a(EnumBlockRotation var0) {
         return new DefinedStructureInfo().a(var0).a(EnumBlockMirror.a).a(ShipwreckPieces.a).a(DefinedStructureProcessorBlockIgnore.d);
      }

      @Override
      protected void a(String var0, BlockPosition var1, WorldAccess var2, RandomSource var3, StructureBoundingBox var4) {
         MinecraftKey var5 = ShipwreckPieces.d.get(var0);
         if (var5 != null) {
            TileEntityLootable.a(var2, var3, var1.d(), var5);
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
         int var7 = var0.ai();
         int var8 = 0;
         BaseBlockPosition var9 = this.b.a();
         HeightMap.Type var10 = this.h ? HeightMap.Type.a : HeightMap.Type.c;
         int var11 = var9.u() * var9.w();
         if (var11 == 0) {
            var8 = var0.a(var10, this.d.u(), this.d.w());
         } else {
            BlockPosition var12 = this.d.b(var9.u() - 1, 0, var9.w() - 1);

            for(BlockPosition var14 : BlockPosition.a(this.d, var12)) {
               int var15 = var0.a(var10, var14.u(), var14.w());
               var8 += var15;
               var7 = Math.min(var7, var15);
            }

            var8 /= var11;
         }

         int var12 = this.h ? var7 - var9.v() / 2 - var3.a(3) : var8;
         this.d = new BlockPosition(this.d.u(), var12, this.d.w());
         super.a(var0, var1, var2, var3, var4, var5, var6);
      }
   }
}
