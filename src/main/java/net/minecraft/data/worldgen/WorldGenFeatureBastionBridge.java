package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolStructure;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorList;

public class WorldGenFeatureBastionBridge {
   public static void a(BootstapContext<WorldGenFeatureDefinedStructurePoolTemplate> var0) {
      HolderGetter<ProcessorList> var1 = var0.a(Registries.ay);
      Holder<ProcessorList> var2 = var1.b(ProcessorLists.y);
      Holder<ProcessorList> var3 = var1.b(ProcessorLists.w);
      Holder<ProcessorList> var4 = var1.b(ProcessorLists.z);
      Holder<ProcessorList> var5 = var1.b(ProcessorLists.x);
      HolderGetter<WorldGenFeatureDefinedStructurePoolTemplate> var6 = var0.a(Registries.aA);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var7 = var6.b(WorldGenFeaturePieces.a);
      WorldGenFeaturePieces.a(
         var0,
         "bastion/bridge/starting_pieces",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var7,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/bridge/starting_pieces/entrance", var2), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/bridge/starting_pieces/entrance_face", var3), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/bridge/bridge_pieces",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var7,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/bridge/bridge_pieces/bridge", var4), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/bridge/legs",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var7,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/bridge/legs/leg_0", var3), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/bridge/legs/leg_1", var3), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/bridge/walls",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var7,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/bridge/walls/wall_base_0", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/bridge/walls/wall_base_1", var5), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/bridge/ramparts",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var7,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/bridge/ramparts/rampart_0", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/bridge/ramparts/rampart_1", var5), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/bridge/rampart_plates",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var7,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/bridge/rampart_plates/plate_0", var5), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "bastion/bridge/connectors",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var7,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/bridge/connectors/back_bridge_top", var3), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("bastion/bridge/connectors/back_bridge_bottom", var3), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
   }
}
