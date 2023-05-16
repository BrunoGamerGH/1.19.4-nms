package net.minecraft.data.worldgen;

import net.minecraft.world.level.levelgen.structure.pools.WorldGenFeatureDefinedStructurePoolTemplate;

public class WorldGenFeatureVillages {
   public static void a(BootstapContext<WorldGenFeatureDefinedStructurePoolTemplate> var0) {
      WorldGenFeatureVillagePlain.a(var0);
      WorldGenFeatureVillageSnowy.a(var0);
      WorldGenFeatureVillageSavanna.a(var0);
      WorldGenFeatureDesertVillage.a(var0);
      WorldGenFeatureVillageTaiga.a(var0);
   }
}
