package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolStructure;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorList;

public class WorldGenFeatureBastionUnits {
   public static void a(BootstapContext<WorldGenFeatureDefinedStructurePoolTemplate> var0) {
      HolderGetter<ProcessorList> var1 = var0.a(Registries.ay);
      Holder<ProcessorList> var2 = var1.b(ProcessorLists.t);
      HolderGetter<WorldGenFeatureDefinedStructurePoolTemplate> var3 = var0.a(Registries.aA);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var4 = var3.b(WorldGenFeaturePieces.a);
      WorldGenFeaturePieces.a(
         var0,
         "bastion/units/center_pieces",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/center_pieces/center_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/center_pieces/center_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/center_pieces/center_2", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/units/pathways",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/pathways/pathway_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/pathways/pathway_wall_0", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/units/walls/wall_bases",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/walls/wall_base", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/walls/connected_wall", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/units/stages/stage_0",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/stages/stage_0_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/stages/stage_0_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/stages/stage_0_2", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/stages/stage_0_3", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/units/stages/stage_1",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/stages/stage_1_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/stages/stage_1_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/stages/stage_1_2", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/stages/stage_1_3", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/units/stages/rot/stage_1",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/stages/rot/stage_1_0", var2), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/units/stages/stage_2",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/stages/stage_2_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/stages/stage_2_1", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/units/stages/stage_3",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/stages/stage_3_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/stages/stage_3_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/stages/stage_3_2", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/stages/stage_3_3", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/units/fillers/stage_0",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/fillers/stage_0", var2), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/units/edges",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/edges/edge_0", var2), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/units/wall_units",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/wall_units/unit_0", var2), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/units/edge_wall_units",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/wall_units/edge_0_large", var2), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/units/ramparts",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/ramparts/ramparts_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/ramparts/ramparts_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/ramparts/ramparts_2", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/units/large_ramparts",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/ramparts/ramparts_0", var2), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/units/rampart_plates",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var4,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/units/rampart_plates/plate_0", var2), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
   }
}
