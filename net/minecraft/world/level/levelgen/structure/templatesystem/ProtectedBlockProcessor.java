package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.WorldGenerator;

public class ProtectedBlockProcessor extends DefinedStructureProcessor {
   public final TagKey<Block> a;
   public static final Codec<ProtectedBlockProcessor> b = TagKey.b(Registries.e).xmap(ProtectedBlockProcessor::new, var0 -> var0.a);

   public ProtectedBlockProcessor(TagKey<Block> var0) {
      this.a = var0;
   }

   @Nullable
   @Override
   public DefinedStructure.BlockInfo a(
      IWorldReader var0, BlockPosition var1, BlockPosition var2, DefinedStructure.BlockInfo var3, DefinedStructure.BlockInfo var4, DefinedStructureInfo var5
   ) {
      return WorldGenerator.a(this.a).test(var0.a_(var4.a)) ? var4 : null;
   }

   @Override
   protected DefinedStructureStructureProcessorType<?> a() {
      return DefinedStructureStructureProcessorType.j;
   }
}
