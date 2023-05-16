package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class DefinedStructureProcessorLavaSubmergedBlock extends DefinedStructureProcessor {
   public static final Codec<DefinedStructureProcessorLavaSubmergedBlock> a = Codec.unit(() -> DefinedStructureProcessorLavaSubmergedBlock.b);
   public static final DefinedStructureProcessorLavaSubmergedBlock b = new DefinedStructureProcessorLavaSubmergedBlock();

   @Nullable
   @Override
   public DefinedStructure.BlockInfo a(
      IWorldReader var0, BlockPosition var1, BlockPosition var2, DefinedStructure.BlockInfo var3, DefinedStructure.BlockInfo var4, DefinedStructureInfo var5
   ) {
      BlockPosition var6 = var4.a;
      boolean var7 = var0.a_(var6).a(Blocks.H);
      return var7 && !Block.a(var4.b.j(var0, var6)) ? new DefinedStructure.BlockInfo(var6, Blocks.H.o(), var4.c) : var4;
   }

   @Override
   protected DefinedStructureStructureProcessorType<?> a() {
      return DefinedStructureStructureProcessorType.i;
   }
}
