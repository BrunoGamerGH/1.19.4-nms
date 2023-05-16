package net.minecraft.world.level.levelgen.structure.templatesystem;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPosition;
import net.minecraft.world.level.GeneratorAccess;
import net.minecraft.world.level.IWorldReader;

public abstract class DefinedStructureProcessor {
   @Nullable
   public abstract DefinedStructure.BlockInfo a(
      IWorldReader var1, BlockPosition var2, BlockPosition var3, DefinedStructure.BlockInfo var4, DefinedStructure.BlockInfo var5, DefinedStructureInfo var6
   );

   protected abstract DefinedStructureStructureProcessorType<?> a();

   public void a(GeneratorAccess var0, BlockPosition var1, BlockPosition var2, DefinedStructureInfo var3, List<DefinedStructure.BlockInfo> var4) {
   }
}
