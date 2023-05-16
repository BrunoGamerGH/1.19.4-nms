package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolStructure;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorList;

public class WorldGenFeatureBastionTreasure {
   public static void a(BootstapContext<WorldGenFeatureDefinedStructurePoolTemplate> var0) {
      HolderGetter<ProcessorList> var1 = var0.a(Registries.ay);
      Holder<ProcessorList> var2 = var1.b(ProcessorLists.s);
      Holder<ProcessorList> var3 = var1.b(ProcessorLists.B);
      Holder<ProcessorList> var4 = var1.b(ProcessorLists.r);
      Holder<ProcessorList> var5 = var1.b(ProcessorLists.C);
      Holder<ProcessorList> var6 = var1.b(ProcessorLists.A);
      HolderGetter<WorldGenFeatureDefinedStructurePoolTemplate> var7 = var0.a(Registries.aA);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var8 = var7.b(WorldGenFeaturePieces.a);
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/bases",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/bases/lava_basin", var2), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/stairs",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/stairs/lower_stairs", var2), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/bases/centers",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/bases/centers/center_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/bases/centers/center_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/bases/centers/center_2", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/bases/centers/center_3", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/brains",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/brains/center_brain", var2), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/walls",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/lava_wall", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/entrance_wall", var3), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/walls/outer",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/outer/top_corner", var3), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/outer/mid_corner", var3), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/outer/bottom_corner", var3), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/outer/outer_wall", var3), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/outer/medium_outer_wall", var3), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/outer/tall_outer_wall", var3), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/walls/bottom",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/bottom/wall_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/bottom/wall_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/bottom/wall_2", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/bottom/wall_3", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/walls/mid",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/mid/wall_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/mid/wall_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/mid/wall_2", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/walls/top",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/top/main_entrance", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/top/wall_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/walls/top/wall_1", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/connectors",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/connectors/center_to_wall_middle", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/connectors/center_to_wall_top", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/connectors/center_to_wall_top_entrance", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/entrances",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/entrances/entrance_0", var2), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/ramparts",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/ramparts/mid_wall_main", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/ramparts/mid_wall_side", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/ramparts/bottom_wall_0", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/ramparts/top_wall", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/ramparts/lava_basin_side", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/ramparts/lava_basin_main", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/corners/bottom",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/corners/bottom/corner_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/corners/bottom/corner_1", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/corners/edges",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/corners/edges/bottom", var3), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/corners/edges/middle", var3), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/corners/edges/top", var3), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/corners/middle",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/corners/middle/corner_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/corners/middle/corner_1", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/corners/top",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/corners/top/corner_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/corners/top/corner_1", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/extensions/large_pool",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/empty", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/empty", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/fire_room", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/large_bridge_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/large_bridge_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/large_bridge_2", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/large_bridge_3", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/roofed_bridge", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/empty", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/extensions/small_pool",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/empty", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/fire_room", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/empty", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/small_bridge_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/small_bridge_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/small_bridge_2", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/small_bridge_3", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/extensions/houses",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/house_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/extensions/house_1", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/treasure/roofs",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/roofs/wall_roof", var6), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/roofs/corner_roof", var6), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/treasure/roofs/center_roof", var6), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
   }
}
