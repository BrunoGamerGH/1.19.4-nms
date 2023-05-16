package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolStructure;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolTemplate;

public class WorldGenFeatureBastionExtra {
   public static void a(BootstapContext<WorldGenFeatureDefinedStructurePoolTemplate> var0) {
      HolderGetter<WorldGenFeatureDefinedStructurePoolTemplate> var1 = var0.a(Registries.aA);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var2 = var1.b(WorldGenFeaturePieces.a);
      WorldGenFeaturePieces.a(
         var0,
         "bastion/mobs/piglin",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var2,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/mobs/melee_piglin"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/mobs/sword_piglin"), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/mobs/crossbow_piglin"), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/mobs/empty"), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/mobs/hoglin",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var2,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/mobs/hoglin"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/mobs/empty"), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/blocks/gold",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var2,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/blocks/air"), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/blocks/gold"), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/mobs/piglin_melee",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var2,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/mobs/melee_piglin_always"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/mobs/melee_piglin"), 5),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/mobs/sword_piglin"), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
   }
}
