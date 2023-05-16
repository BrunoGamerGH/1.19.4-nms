package net.minecraft.data.worldgen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.VillagePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolStructure;
import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorList;

public class WorldGenFeatureDesertVillage {
   public static final ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> a = WorldGenFeaturePieces.a("village/desert/town_centers");
   private static final ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> b = WorldGenFeaturePieces.a("village/desert/terminators");
   private static final ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> c = WorldGenFeaturePieces.a("village/desert/zombie/terminators");

   public static void a(BootstapContext<WorldGenFeatureDefinedStructurePoolTemplate> var0) {
      HolderGetter<PlacedFeature> var1 = var0.a(Registries.aw);
      Holder<PlacedFeature> var2 = var1.b(VillagePlacements.j);
      Holder<PlacedFeature> var3 = var1.b(VillagePlacements.a);
      HolderGetter<ProcessorList> var4 = var0.a(Registries.ay);
      Holder<ProcessorList> var5 = var4.b(ProcessorLists.e);
      Holder<ProcessorList> var6 = var4.b(ProcessorLists.p);
      HolderGetter<WorldGenFeatureDefinedStructurePoolTemplate> var7 = var0.a(Registries.aA);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var8 = var7.b(WorldGenFeaturePieces.a);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var9 = var7.b(b);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var10 = var7.b(c);
      var0.a(
         a,
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/town_centers/desert_meeting_point_1"), 98),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/town_centers/desert_meeting_point_2"), 98),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/town_centers/desert_meeting_point_3"), 49),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/town_centers/desert_meeting_point_1", var5), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/town_centers/desert_meeting_point_2", var5), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/town_centers/desert_meeting_point_3", var5), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/desert/streets",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var9,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/streets/corner_01"), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/streets/corner_02"), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/streets/straight_01"), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/streets/straight_02"), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/streets/straight_03"), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/streets/crossroad_01"), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/streets/crossroad_02"), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/streets/crossroad_03"), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/streets/square_01"), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/streets/square_02"), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/streets/turn_01"), 3)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.a
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/desert/zombie/streets",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var10,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/streets/corner_01"), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/streets/corner_02"), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/streets/straight_01"), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/streets/straight_02"), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/streets/straight_03"), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/streets/crossroad_01"), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/streets/crossroad_02"), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/streets/crossroad_03"), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/streets/square_01"), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/streets/square_02"), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/streets/turn_01"), 3)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.a
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/desert/houses",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var9,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_small_house_1"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_small_house_2"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_small_house_3"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_small_house_4"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_small_house_5"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_small_house_6"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_small_house_7"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_small_house_8"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_medium_house_1"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_medium_house_2"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_butcher_shop_1"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_tool_smith_1"), 2),
               new Pair[]{
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_fletcher_house_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_shepherd_house_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_armorer_1"), 1),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_fisher_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_tannery_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_cartographer_house_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_library_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_mason_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_weaponsmith_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_temple_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_temple_2"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_large_farm_1", var6), 11),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_farm_1", var6), 4),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_farm_2", var6), 4),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_animal_pen_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_animal_pen_2"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.g(), 5)
               }
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/desert/zombie/houses",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var10,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/houses/desert_small_house_1", var5), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/houses/desert_small_house_2", var5), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/houses/desert_small_house_3", var5), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/houses/desert_small_house_4", var5), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/houses/desert_small_house_5", var5), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/houses/desert_small_house_6", var5), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/houses/desert_small_house_7", var5), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/houses/desert_small_house_8", var5), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/houses/desert_medium_house_1", var5), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/houses/desert_medium_house_2", var5), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_butcher_shop_1", var5), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_tool_smith_1", var5), 2),
               new Pair[]{
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_fletcher_house_1", var5), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_shepherd_house_1", var5), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_armorer_1", var5), 1),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_fisher_1", var5), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_tannery_1", var5), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_cartographer_house_1", var5), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_library_1", var5), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_mason_1", var5), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_weaponsmith_1", var5), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_temple_1", var5), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_temple_2", var5), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_large_farm_1", var5), 7),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_farm_1", var5), 4),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_farm_2", var5), 4),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_animal_pen_1", var5), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/houses/desert_animal_pen_2", var5), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.g(), 5)
               }
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      var0.a(
         b,
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/terminators/terminator_01"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/terminators/terminator_02"), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.a
         )
      );
      var0.a(
         c,
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/terminators/terminator_01"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/terminators/terminator_02"), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.a
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/desert/decor",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/desert_lamp_1"), 10),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var2), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var3), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.g(), 10)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/desert/zombie/decor",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/desert_lamp_1", var5), 10),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var2), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var3), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.g(), 10)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/desert/villagers",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/villagers/nitwit"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/villagers/baby"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/villagers/unemployed"), 10)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/desert/camel",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/camel_spawn"), 1)),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/desert/zombie/villagers",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var8,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/villagers/nitwit"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/desert/zombie/villagers/unemployed"), 10)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
   }
}
