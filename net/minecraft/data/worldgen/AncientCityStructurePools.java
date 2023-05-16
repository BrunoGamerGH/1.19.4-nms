package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolStructure;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorList;

public class AncientCityStructurePools {
   public static void a(BootstapContext<WorldGenFeatureDefinedStructurePoolTemplate> var0) {
      HolderGetter<PlacedFeature> var1 = var0.a(Registries.aw);
      Holder<PlacedFeature> var2 = var1.b(CavePlacements.s);
      HolderGetter<ProcessorList> var3 = var0.a(Registries.ay);
      Holder<ProcessorList> var4 = var3.b(ProcessorLists.H);
      Holder<ProcessorList> var5 = var3.b(ProcessorLists.I);
      HolderGetter<WorldGenFeatureDefinedStructurePoolTemplate> var6 = var0.a(Registries.aA);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var7 = var6.b(WorldGenFeaturePieces.a);
      WorldGenFeaturePieces.a(
         var0,
         "ancient_city/structures",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var7,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.g(), 7),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/barracks", var4), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/chamber_1", var4), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/chamber_2", var4), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/chamber_3", var4), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/sauna_1", var4), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/small_statue", var4), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/large_ruin_1", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/tall_ruin_1", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/tall_ruin_2", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/tall_ruin_3", var4), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/tall_ruin_4", var4), 2),
               new Pair[]{
                  Pair.of(
                     WorldGenFeatureDefinedStructurePoolStructure.a(
                        ImmutableList.of(
                           WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/camp_1", var4),
                           WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/camp_2", var4),
                           WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/camp_3", var4)
                        )
                     ),
                     1
                  ),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/medium_ruin_1", var4), 1),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/medium_ruin_2", var4), 1),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/small_ruin_1", var4), 1),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/small_ruin_2", var4), 1),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/large_pillar_1", var4), 1),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/medium_pillar_1", var4), 1),
                  Pair.of(
                     WorldGenFeatureDefinedStructurePoolStructure.a(
                        ImmutableList.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/structures/ice_box_1"))
                     ),
                     1
                  )
               }
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "ancient_city/sculk",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var7,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var2), 6), Pair.of(WorldGenFeatureDefinedStructurePoolStructure.g(), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "ancient_city/walls",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var7,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_corner_wall_1", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_intersection_wall_1", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_lshape_wall_1", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_horizontal_wall_1", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_horizontal_wall_2", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_horizontal_wall_stairs_1", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_horizontal_wall_stairs_2", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_horizontal_wall_stairs_3", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_horizontal_wall_stairs_4", var5), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_horizontal_wall_passage_1", var5), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/ruined_corner_wall_1", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/ruined_corner_wall_2", var5), 1),
               new Pair[]{
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/ruined_horizontal_wall_stairs_1", var5), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/ruined_horizontal_wall_stairs_2", var5), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/ruined_horizontal_wall_stairs_3", var5), 3),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/ruined_horizontal_wall_stairs_4", var5), 3)
               }
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "ancient_city/walls/no_corners",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var7,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_horizontal_wall_1", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_horizontal_wall_2", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_horizontal_wall_stairs_1", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_horizontal_wall_stairs_2", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_horizontal_wall_stairs_3", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_horizontal_wall_stairs_4", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_horizontal_wall_stairs_5", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/walls/intact_horizontal_wall_bridge", var5), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "ancient_city/city_center/walls",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var7,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city_center/walls/bottom_1", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city_center/walls/bottom_2", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city_center/walls/bottom_left_corner", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city_center/walls/bottom_right_corner_1", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city_center/walls/bottom_right_corner_2", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city_center/walls/left", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city_center/walls/right", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city_center/walls/top", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city_center/walls/top_right_corner", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city_center/walls/top_left_corner", var4), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "ancient_city/city/entrance",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var7,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city/entrance/entrance_connector", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city/entrance/entrance_path_1", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city/entrance/entrance_path_2", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city/entrance/entrance_path_3", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city/entrance/entrance_path_4", var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.b("ancient_city/city/entrance/entrance_path_5", var4), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
   }
}
