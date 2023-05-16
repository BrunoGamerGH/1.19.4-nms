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

public class WorldGenFeatureVillageSavanna {
   public static final ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> a = WorldGenFeaturePieces.a("village/savanna/town_centers");
   private static final ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> b = WorldGenFeaturePieces.a("village/savanna/terminators");
   private static final ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> c = WorldGenFeaturePieces.a("village/savanna/zombie/terminators");

   public static void a(BootstapContext<WorldGenFeatureDefinedStructurePoolTemplate> var0) {
      HolderGetter<PlacedFeature> var1 = var0.a(Registries.aw);
      Holder<PlacedFeature> var2 = var1.b(VillagePlacements.g);
      Holder<PlacedFeature> var3 = var1.b(VillagePlacements.a);
      Holder<PlacedFeature> var4 = var1.b(VillagePlacements.b);
      HolderGetter<ProcessorList> var5 = var0.a(Registries.ay);
      Holder<ProcessorList> var6 = var5.b(ProcessorLists.b);
      Holder<ProcessorList> var7 = var5.b(ProcessorLists.j);
      Holder<ProcessorList> var8 = var5.b(ProcessorLists.m);
      HolderGetter<WorldGenFeatureDefinedStructurePoolTemplate> var9 = var0.a(Registries.aA);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var10 = var9.b(WorldGenFeaturePieces.a);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var11 = var9.b(b);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var12 = var9.b(c);
      var0.a(
         a,
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var10,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/town_centers/savanna_meeting_point_1"), 100),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/town_centers/savanna_meeting_point_2"), 50),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/town_centers/savanna_meeting_point_3"), 150),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/town_centers/savanna_meeting_point_4"), 150),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/town_centers/savanna_meeting_point_1", var6), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/town_centers/savanna_meeting_point_2", var6), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/town_centers/savanna_meeting_point_3", var6), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/town_centers/savanna_meeting_point_4", var6), 3)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/savanna/streets",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var11,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/corner_01", var7), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/corner_03", var7), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/straight_02", var7), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/straight_04", var7), 7),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/straight_05", var7), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/straight_06", var7), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/straight_08", var7), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/straight_09", var7), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/straight_10", var7), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/straight_11", var7), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/crossroad_02", var7), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/crossroad_03", var7), 2),
               new Pair[]{
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/crossroad_04", var7), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/crossroad_05", var7), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/crossroad_06", var7), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/crossroad_07", var7), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/split_01", var7), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/split_02", var7), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/streets/turn_01", var7), 3)
               }
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.a
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/savanna/zombie/streets",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var12,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/corner_01", var7), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/corner_03", var7), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/straight_02", var7), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/straight_04", var7), 7),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/straight_05", var7), 3),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/straight_06", var7), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/straight_08", var7), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/straight_09", var7), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/straight_10", var7), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/straight_11", var7), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/crossroad_02", var7), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/crossroad_03", var7), 2),
               new Pair[]{
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/crossroad_04", var7), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/crossroad_05", var7), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/crossroad_06", var7), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/crossroad_07", var7), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/split_01", var7), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/split_02", var7), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/streets/turn_01", var7), 3)
               }
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.a
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/savanna/houses",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var11,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_small_house_1"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_small_house_2"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_small_house_3"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_small_house_4"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_small_house_5"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_small_house_6"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_small_house_7"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_small_house_8"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_medium_house_1"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_medium_house_2"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_butchers_shop_1"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_butchers_shop_2"), 2),
               new Pair[]{
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_tool_smith_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_fletcher_house_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_shepherd_1"), 7),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_armorer_1"), 1),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_fisher_cottage_1"), 3),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_tannery_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_cartographer_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_library_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_mason_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_weaponsmith_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_weaponsmith_2"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_temple_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_temple_2"), 3),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_large_farm_1", var8), 4),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_large_farm_2", var8), 6),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_small_farm", var8), 4),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_animal_pen_1"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_animal_pen_2"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_animal_pen_3"), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.g(), 5)
               }
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/savanna/zombie/houses",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var12,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/houses/savanna_small_house_1", var6), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/houses/savanna_small_house_2", var6), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/houses/savanna_small_house_3", var6), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/houses/savanna_small_house_4", var6), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/houses/savanna_small_house_5", var6), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/houses/savanna_small_house_6", var6), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/houses/savanna_small_house_7", var6), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/houses/savanna_small_house_8", var6), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/houses/savanna_medium_house_1", var6), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/houses/savanna_medium_house_2", var6), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_butchers_shop_1", var6), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_butchers_shop_2", var6), 2),
               new Pair[]{
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_tool_smith_1", var6), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_fletcher_house_1", var6), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_shepherd_1", var6), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_armorer_1", var6), 1),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_fisher_cottage_1", var6), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_tannery_1", var6), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_cartographer_1", var6), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_library_1", var6), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_mason_1", var6), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_weaponsmith_1", var6), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_weaponsmith_2", var6), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_temple_1", var6), 1),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_temple_2", var6), 3),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_large_farm_1", var6), 4),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/houses/savanna_large_farm_2", var6), 4),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_small_farm", var6), 4),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/houses/savanna_animal_pen_1", var6), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/houses/savanna_animal_pen_2", var6), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/houses/savanna_animal_pen_3", var6), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.g(), 5)
               }
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      var0.a(
         b,
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var10,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/plains/terminators/terminator_01", var7), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/plains/terminators/terminator_02", var7), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/plains/terminators/terminator_03", var7), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/plains/terminators/terminator_04", var7), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/terminators/terminator_05", var7), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.a
         )
      );
      var0.a(
         c,
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var10,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/plains/terminators/terminator_01", var7), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/plains/terminators/terminator_02", var7), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/plains/terminators/terminator_03", var7), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/plains/terminators/terminator_04", var7), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/terminators/terminator_05", var7), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.a
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/savanna/trees",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var10, ImmutableList.of(Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var2), 1)), WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/savanna/decor",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var10,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/savanna_lamp_post_01"), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var2), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var3), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.g(), 4)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/savanna/zombie/decor",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var10,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/savanna_lamp_post_01", var6), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var2), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var3), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var4), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.g(), 4)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/savanna/villagers",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var10,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/villagers/nitwit"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/villagers/baby"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/villagers/unemployed"), 10)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/savanna/zombie/villagers",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var10,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/villagers/nitwit"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/savanna/zombie/villagers/unemployed"), 10)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
   }
}
