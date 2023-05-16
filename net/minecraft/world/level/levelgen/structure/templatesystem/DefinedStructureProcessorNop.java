package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.IWorldReader;

public class DefinedStructureProcessorNop extends DefinedStructureProcessor {
   public static final Codec<DefinedStructureProcessorNop> a = Codec.unit(() -> DefinedStructureProcessorNop.b);
   public static final DefinedStructureProcessorNop b = new DefinedStructureProcessorNop();

   private DefinedStructureProcessorNop() {
   }

   @Nullable
   @Override
   public DefinedStructure.BlockInfo a(
      IWorldReader var0, BlockPosition var1, BlockPosition var2, DefinedStructure.BlockInfo var3, DefinedStructure.BlockInfo var4, DefinedStructureInfo var5
   ) {
      return var4;
   }

   @Override
   protected DefinedStructureStructureProcessorType<?> a() {
      return DefinedStructureStructureProcessorType.f;
   }
}
