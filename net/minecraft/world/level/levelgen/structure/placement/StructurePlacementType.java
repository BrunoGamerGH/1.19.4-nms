package net.minecraft.world.level.levelgen.structure.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public interface StructurePlacementType<SP extends StructurePlacement> {
   StructurePlacementType<RandomSpreadStructurePlacement> a = a("random_spread", RandomSpreadStructurePlacement.a);
   StructurePlacementType<ConcentricRingsStructurePlacement> b = a("concentric_rings", ConcentricRingsStructurePlacement.a);

   Codec<SP> codec();

   private static <SP extends StructurePlacement> StructurePlacementType<SP> a(String var0, Codec<SP> var1) {
      return IRegistry.a(BuiltInRegistries.R, var0, () -> var1);
   }
}
