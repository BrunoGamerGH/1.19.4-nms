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

public class WorldGenFeatureVillageTaiga {
   public static final ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> a = WorldGenFeaturePieces.a("village/taiga/town_centers");
   private static final ResourceKey<WorldGenFeatureDefinedStructurePoolTemplate> b = WorldGenFeaturePieces.a("village/taiga/terminators");

   public static void a(BootstapContext<WorldGenFeatureDefinedStructurePoolTemplate> var0) {
      HolderGetter<PlacedFeature> var1 = var0.a(Registries.aw);
      Holder<PlacedFeature> var2 = var1.b(VillagePlacements.h);
      Holder<PlacedFeature> var3 = var1.b(VillagePlacements.i);
      Holder<PlacedFeature> var4 = var1.b(VillagePlacements.e);
      Holder<PlacedFeature> var5 = var1.b(VillagePlacements.l);
      Holder<PlacedFeature> var6 = var1.b(VillagePlacements.m);
      HolderGetter<ProcessorList> var7 = var0.a(Registries.ay);
      Holder<ProcessorList> var8 = var7.b(ProcessorLists.f);
      Holder<ProcessorList> var9 = var7.b(ProcessorLists.d);
      Holder<ProcessorList> var10 = var7.b(ProcessorLists.k);
      Holder<ProcessorList> var11 = var7.b(ProcessorLists.o);
      HolderGetter<WorldGenFeatureDefinedStructurePoolTemplate> var12 = var0.a(Registries.aA);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var13 = var12.b(WorldGenFeaturePieces.a);
      Holder<WorldGenFeatureDefinedStructurePoolTemplate> var14 = var12.b(b);
      var0.a(
         a,
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var13,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/town_centers/taiga_meeting_point_1", var8), 49),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/town_centers/taiga_meeting_point_2", var8), 49),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/town_centers/taiga_meeting_point_1", var9), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/town_centers/taiga_meeting_point_2", var9), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/taiga/streets",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var14,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/streets/corner_01", var10), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/streets/corner_02", var10), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/streets/corner_03", var10), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/streets/straight_01", var10), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/streets/straight_02", var10), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/streets/straight_03", var10), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/streets/straight_04", var10), 7),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/streets/straight_05", var10), 7),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/streets/straight_06", var10), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/streets/crossroad_01", var10), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/streets/crossroad_02", var10), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/streets/crossroad_03", var10), 2),
               new Pair[]{
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/streets/crossroad_04", var10), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/streets/crossroad_05", var10), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/streets/crossroad_06", var10), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/streets/turn_01", var10), 3)
               }
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.a
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/taiga/zombie/streets",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var14,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/streets/corner_01", var10), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/streets/corner_02", var10), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/streets/corner_03", var10), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/streets/straight_01", var10), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/streets/straight_02", var10), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/streets/straight_03", var10), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/streets/straight_04", var10), 7),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/streets/straight_05", var10), 7),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/streets/straight_06", var10), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/streets/crossroad_01", var10), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/streets/crossroad_02", var10), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/streets/crossroad_03", var10), 2),
               new Pair[]{
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/streets/crossroad_04", var10), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/streets/crossroad_05", var10), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/streets/crossroad_06", var10), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/streets/turn_01", var10), 3)
               }
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.a
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/taiga/houses",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var14,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_small_house_1", var8), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_small_house_2", var8), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_small_house_3", var8), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_small_house_4", var8), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_small_house_5", var8), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_medium_house_1", var8), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_medium_house_2", var8), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_medium_house_3", var8), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_medium_house_4", var8), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_butcher_shop_1", var8), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_tool_smith_1", var8), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_fletcher_house_1", var8), 2),
               new Pair[]{
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_shepherds_house_1", var8), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_armorer_house_1", var8), 1),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_armorer_2", var8), 1),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_fisher_cottage_1", var8), 3),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_tannery_1", var8), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_cartographer_house_1", var8), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_library_1", var8), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_masons_house_1", var8), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_weaponsmith_1", var8), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_weaponsmith_2", var8), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_temple_1", var8), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_large_farm_1", var11), 6),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_large_farm_2", var11), 6),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_small_farm_1", var8), 1),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_animal_pen_1", var8), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.g(), 6)
               }
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/taiga/zombie/houses",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var14,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/houses/taiga_small_house_1", var9), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/houses/taiga_small_house_2", var9), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/houses/taiga_small_house_3", var9), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/houses/taiga_small_house_4", var9), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/houses/taiga_small_house_5", var9), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/houses/taiga_medium_house_1", var9), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/houses/taiga_medium_house_2", var9), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/houses/taiga_medium_house_3", var9), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/houses/taiga_medium_house_4", var9), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_butcher_shop_1", var9), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/houses/taiga_tool_smith_1", var9), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_fletcher_house_1", var9), 2),
               new Pair[]{
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/houses/taiga_shepherds_house_1", var9), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_armorer_house_1", var9), 1),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/houses/taiga_fisher_cottage_1", var9), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_tannery_1", var9), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/houses/taiga_cartographer_house_1", var9), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/houses/taiga_library_1", var9), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_masons_house_1", var9), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_weaponsmith_1", var9), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/houses/taiga_weaponsmith_2", var9), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/houses/taiga_temple_1", var9), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_large_farm_1", var9), 6),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/houses/taiga_large_farm_2", var9), 6),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_small_farm_1", var9), 1),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/houses/taiga_animal_pen_1", var9), 2),
                  Pair.of(WorldGenFeatureDefinedStructurePoolStructure.g(), 6)
               }
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      var0.a(
         b,
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var13,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/plains/terminators/terminator_01", var10), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/plains/terminators/terminator_02", var10), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/plains/terminators/terminator_03", var10), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/plains/terminators/terminator_04", var10), 1)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.a
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/taiga/decor",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var13,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/taiga_lamp_post_1"), 10),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/taiga_decoration_1"), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/taiga_decoration_2"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/taiga_decoration_3"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/taiga_decoration_4"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/taiga_decoration_5"), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/taiga_decoration_6"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var2), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var3), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var4), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var5), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var6), 1),
               new Pair[]{Pair.of(WorldGenFeatureDefinedStructurePoolStructure.g(), 4)}
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/taiga/zombie/decor",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var13,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/taiga_decoration_1"), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/taiga_decoration_2"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/taiga_decoration_3"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/taiga_decoration_4"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var2), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var3), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var4), 2),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var5), 4),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a(var6), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.g(), 4)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/taiga/villagers",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var13,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/villagers/nitwit"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/villagers/baby"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/villagers/unemployed"), 10)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
      WorldGenFeaturePieces.a(
         var0,
         "village/taiga/zombie/villagers",
         new WorldGenFeatureDefinedStructurePoolTemplate(
            var13,
            ImmutableList.of(
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/villagers/nitwit"), 1),
               Pair.of(WorldGenFeatureDefinedStructurePoolStructure.a("village/taiga/zombie/villagers/unemployed"), 10)
            ),
            WorldGenFeatureDefinedStructurePoolTemplate.Matching.b
         )
      );
   }
}
