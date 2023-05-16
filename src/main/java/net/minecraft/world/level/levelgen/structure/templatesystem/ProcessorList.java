package net.minecraft.world.level.levelgen.structure.templatesystem;

import java.util.List;

public class ProcessorList {
   private final List<DefinedStructureProcessor> a;

   public ProcessorList(List<DefinedStructureProcessor> var0) {
      this.a = var0;
   }

   public List<DefinedStructureProcessor> a() {
      return this.a;
   }

   @Override
   public String toString() {
      return "ProcessorList[" + this.a + "]";
   }
}
