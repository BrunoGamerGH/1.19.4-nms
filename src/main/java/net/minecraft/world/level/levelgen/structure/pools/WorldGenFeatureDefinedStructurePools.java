package net.minecraft.world.level.levelgen.structure.pools;

import com.mojang.serialization.Codec;
import net.minecraft.core.IRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public interface WorldGenFeatureDefinedStructurePools<P extends WorldGenFeatureDefinedStructurePoolStructure> {
   WorldGenFeatureDefinedStructurePools<WorldGenFeatureDefinedStructurePoolSingle> a = a("single_pool_element", WorldGenFeatureDefinedStructurePoolSingle.b);
   WorldGenFeatureDefinedStructurePools<WorldGenFeatureDefinedStructurePoolList> b = a("list_pool_element", WorldGenFeatureDefinedStructurePoolList.a);
   WorldGenFeatureDefinedStructurePools<WorldGenFeatureDefinedStructurePoolFeature> c = a("feature_pool_element", WorldGenFeatureDefinedStructurePoolFeature.a);
   WorldGenFeatureDefinedStructurePools<WorldGenFeatureDefinedStructurePoolEmpty> d = a("empty_pool_element", WorldGenFeatureDefinedStructurePoolEmpty.a);
   WorldGenFeatureDefinedStructurePools<WorldGenFeatureDefinedStructurePoolLegacySingle> e = a(
      "legacy_single_pool_element", WorldGenFeatureDefinedStructurePoolLegacySingle.a
   );

   Codec<P> codec();

   static <P extends WorldGenFeatureDefinedStructurePoolStructure> WorldGenFeatureDefinedStructurePools<P> a(String var0, Codec<P> var1) {
      return IRegistry.a(BuiltInRegistries.ah, var0, () -> var1);
   }
}
