package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.IWorldReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBase;
import net.minecraft.world.level.block.state.IBlockData;

public class DefinedStructureProcessorBlockIgnore extends DefinedStructureProcessor {
   public static final Codec<DefinedStructureProcessorBlockIgnore> a = IBlockData.b
      .xmap(BlockBase.BlockData::b, Block::o)
      .listOf()
      .fieldOf("blocks")
      .xmap(DefinedStructureProcessorBlockIgnore::new, var0 -> var0.e)
      .codec();
   public static final DefinedStructureProcessorBlockIgnore b = new DefinedStructureProcessorBlockIgnore(ImmutableList.of(Blocks.oW));
   public static final DefinedStructureProcessorBlockIgnore c = new DefinedStructureProcessorBlockIgnore(ImmutableList.of(Blocks.a));
   public static final DefinedStructureProcessorBlockIgnore d = new DefinedStructureProcessorBlockIgnore(ImmutableList.of(Blocks.a, Blocks.oW));
   private final ImmutableList<Block> e;

   public DefinedStructureProcessorBlockIgnore(List<Block> var0) {
      this.e = ImmutableList.copyOf(var0);
   }

   @Nullable
   @Override
   public DefinedStructure.BlockInfo a(
      IWorldReader var0, BlockPosition var1, BlockPosition var2, DefinedStructure.BlockInfo var3, DefinedStructure.BlockInfo var4, DefinedStructureInfo var5
   ) {
      return this.e.contains(var4.b.b()) ? null : var4;
   }

   @Override
   protected DefinedStructureStructureProcessorType<?> a() {
      return DefinedStructureStructureProcessorType.a;
   }
}
