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

public class WorldGenFeaturePillagerOutpostPieces {
   public static final ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> a = WorldGenFeaturePieces.a("pillager_outpost/base_plates");

   public static void a(BootstapContext<WorldGenFeatureDefinedStructurePoolTemplate> var0) {
      HolderGetter<ProcessorList> var1 = var0.a(Registries.ay);
      Holder<ProcessorList> var2 = var1.b(ProcessorLists.q);
      HolderGetter<WorldGenFeatureDefinedStructurePoolTemplate> var3 = var0.a(Registries.aA);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var4 = var3.b(WorldGenFeaturePieces.a);
      var0.a(
         a,
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("pillager_outpost/base_plate"), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "pillager_outpost/towers",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(
               Pair.of(
                  WorldGenFeatureDefinedStructurePoolStructure.a(
                     ImmutableList.of(
                        WorldGenFeatureDefinedStructurePoolStructure.a("pillager_outpost/watchtower"),
                        WorldGenFeatureDefinedStructurePoolStructure.a("pillager_outpost/watchtower_overgrown", var2)
                     )
                  ),
                  1
               )
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "pillager_outpost/feature_plates",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("pillager_outpost/feature_plate"), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.a
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "pillager_outpost/features",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("pillager_outpost/feature_cage1"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("pillager_outpost/feature_cage2"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("pillager_outpost/feature_cage_with_allays"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("pillager_outpost/feature_logs"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("pillager_outpost/feature_tent1"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("pillager_outpost/feature_tent2"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("pillager_outpost/feature_targets"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.g(), 6)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
   }
}
