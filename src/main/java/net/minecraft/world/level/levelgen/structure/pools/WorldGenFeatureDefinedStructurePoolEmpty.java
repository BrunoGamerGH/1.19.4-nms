package net.minecraft.world.level.levelgen.structure.pools;

import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BaseBlockPosition;
import net.minecraft.core.BlockPosition;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.GeneratorAccessSeed;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class WorldGenFeatureDefinedStructurePoolEmpty extends WorldGenFeatureDefinedStructurePoolStructure {
   public static final Codec<WorldGenFeatureDefinedStructurePoolEmpty> a = Codec.unit(() -> WorldGenFeatureDefinedStructurePoolEmpty.b);
   public static final WorldGenFeatureDefinedStructurePoolEmpty b = new WorldGenFeatureDefinedStructurePoolEmpty();

   private WorldGenFeatureDefinedStructurePoolEmpty() {
      super(WorldGenFeatureDefinedStructurePoolTemplate.Matching.a);
   }

   @Override
   public BaseBlockPosition a(StructureTemplateManager var0, EnumBlockRotation var1) {
      return BaseBlockPosition.g;
   }

   @Override
   public List<DefinedStructure.BlockInfo> a(StructureTemplateManager var0, BlockPosition var1, EnumBlockRotation var2, RandomSource var3) {
      return Collections.emptyList();
   }

   @Override
   public StructureBoundingBox a(StructureTemplateManager var0, BlockPosition var1, EnumBlockRotation var2) {
      throw new IllegalStateException("Invalid call to EmtyPoolElement.getBoundingBox, filter me!");
   }

   @Override
   public boolean a(
      StructureTemplateManager var0,
      GeneratorAccessSeed var1,
      StructureManager var2,
      ChunkGenerator var3,
      BlockPosition var4,
      BlockPosition var5,
      EnumBlockRotation var6,
      StructureBoundingBox var7,
      RandomSource var8,
      boolean var9
   ) {
      return true;
   }

   @Override
   public WorldGenFeatureDefinedStructurePools<?> a() {
      return WorldGenFeatureDefinedStructurePools.d;
   }

   @Override
   public String toString() {
      return "Empty";
   }
}
