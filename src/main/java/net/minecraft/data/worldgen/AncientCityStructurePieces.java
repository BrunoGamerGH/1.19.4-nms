package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolStructure;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorList;

public class AncientCityStructurePieces {
   public static final ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> a = WorldGenFeaturePieces.a("ancient_city/city_center");

   public static void a(BootstapContext<WorldGenFeatureDefinedStructurePoolTemplate> var0) {
      HolderGetter<ProcessorList> var1 = var0.a(Registries.ay);
      Holder<ProcessorList> var2 = var1.b(ProcessorLists.G);
      HolderGetter<WorldGenFeatureDefinedStructurePoolTemplate> var3 = var0.a(Registries.aA);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var4 = var3.b(WorldGenFeaturePieces.a);
      var0.a(
         a,
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city_center/city_center_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city_center/city_center_2", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city_center/city_center_3", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      AncientCityStructurePools.a(var0);
   }
}
