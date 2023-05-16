package net.minecraft.world.level.levelgen.structure.pools;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.world.level.block.EnumBlockRotation;
import net.minecraft.world.level.levelgen.structure.StructureBoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureInfo;
import net.minecraft.world.level.levelgen.structure.templatesystem.DefinedStructureProcessorBlockIgnore;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorList;

public class WorldGenFeatureDefinedStructurePoolLegacySingle extends WorldGenFeatureDefinedStructurePoolSingle {
   public static final Codec<WorldGenFeatureDefinedStructurePoolLegacySingle> a = RecordCodecBuilder.create(
      var0 -> var0.group(c(), b(), d()).apply(var0, WorldGenFeatureDefinedStructurePoolLegacySingle::new)
   );

   protected WorldGenFeatureDefinedStructurePoolLegacySingle(
      Either<MinecraftKey, DefinedStructure> var0, Holder<ProcessorList> var1, WorldGenFeatureDefinedStructurePoolTemplate.Matching var2
   ) {
      super(var0, var1, var2);
   }

   @Override
   protected DefinedStructureInfo a(EnumBlockRotation var0, StructureBoundingBox var1, boolean var2) {
      DefinedStructureInfo var3 = super.a(var0, var1, var2);
      var3.b(DefinedStructureProcessorBlockIgnore.b);
      var3.a(DefinedStructureProcessorBlockIgnore.d);
      return var3;
   }

   @Override
   public WorldGenFeatureDefinedStructurePools<?> a() {
      return WorldGenFeatureDefinedStructurePools.e;
   }

   @Override
   public String toString() {
      return "LegacySingle[" + this.c + "]";
   }
}
