package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolStructure;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorList;

public class WorldGenFeatureBastionHoglinStable {
   public static void a(BootstapContext<WorldGenFeatureDefinedStructurePoolTemplate> var0) {
      HolderGetter<ProcessorList> var1 = var0.a(Registries.ay);
      Holder<ProcessorList> var2 = var1.b(ProcessorLists.v);
      Holder<ProcessorList> var3 = var1.b(ProcessorLists.u);
      HolderGetter<WorldGenFeatureDefinedStructurePoolTemplate> var4 = var0.a(Registries.aA);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var5 = var4.b(WorldGenFeaturePieces.a);
      WorldGenFeaturePieces.a(
         var0,
         "bastion/hoglin_stable/starting_pieces",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var5,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/starting_pieces/starting_stairs_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/starting_pieces/starting_stairs_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/starting_pieces/starting_stairs_2", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/starting_pieces/starting_stairs_3", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/starting_pieces/starting_stairs_4", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/hoglin_stable/mirrored_starting_pieces",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var5,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/starting_pieces/stairs_0_mirrored", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/starting_pieces/stairs_1_mirrored", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/starting_pieces/stairs_2_mirrored", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/starting_pieces/stairs_3_mirrored", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/starting_pieces/stairs_4_mirrored", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/hoglin_stable/wall_bases",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var5,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/walls/wall_base", var2), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/hoglin_stable/walls",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var5,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/walls/side_wall_0", var3), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/walls/side_wall_1", var3), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/hoglin_stable/stairs",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var5,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/stairs/stairs_1_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/stairs/stairs_1_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/stairs/stairs_1_2", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/stairs/stairs_1_3", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/stairs/stairs_1_4", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/stairs/stairs_2_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/stairs/stairs_2_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/stairs/stairs_2_2", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/stairs/stairs_2_3", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/stairs/stairs_2_4", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/stairs/stairs_3_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/stairs/stairs_3_1", var2), 1),
               new Pair[]{
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/stairs/stairs_3_2", var2), 1),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/stairs/stairs_3_3", var2), 1),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/stairs/stairs_3_4", var2), 1)
               }
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/hoglin_stable/small_stables/inner",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var5,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/small_stables/inner_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/small_stables/inner_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/small_stables/inner_2", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/small_stables/inner_3", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/hoglin_stable/small_stables/outer",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var5,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/small_stables/outer_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/small_stables/outer_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/small_stables/outer_2", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/small_stables/outer_3", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/hoglin_stable/large_stables/inner",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var5,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/large_stables/inner_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/large_stables/inner_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/large_stables/inner_2", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/large_stables/inner_3", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/large_stables/inner_4", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/hoglin_stable/large_stables/outer",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var5,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/large_stables/outer_0", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/large_stables/outer_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/large_stables/outer_2", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/large_stables/outer_3", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/large_stables/outer_4", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/hoglin_stable/posts",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var5,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/posts/stair_post", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/posts/end_post", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/hoglin_stable/ramparts",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var5,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/ramparts/ramparts_1", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/ramparts/ramparts_2", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/ramparts/ramparts_3", var2), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/hoglin_stable/rampart_plates",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var5,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/rampart_plates/rampart_plate_1", var2), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/hoglin_stable/connectors",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var5,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/hoglin_stable/connectors/end_post_connector", var2), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
   }
}
