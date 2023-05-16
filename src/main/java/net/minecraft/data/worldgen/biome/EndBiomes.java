package net.minecraft.data.worldgen.biome;

import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeSettings;
import net.minecraft.data.worldgen.placement.EndPlacements;
import net.minecraft.world.level.biome.BiomeBase;
import net.minecraft.world.level.biome.BiomeFog;
import net.minecraft.world.level.biome.BiomeSettingsGeneration;
import net.minecraft.world.level.biome.BiomeSettingsMobs;
import net.minecraft.world.level.biome.CaveSoundSettings;
import net.minecraft.world.level.levelgen.WorldGenStage;
import net.minecraft.world.level.levelgen.carver.WorldGenCarverWrapper;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class EndBiomes {
   private static BiomeBase a(BiomeSettingsGeneration.a var0) {
      BiomeSettingsMobs.a var1 = new BiomeSettingsMobs.a();
      BiomeSettings.j(var1);
      return new BiomeBase.a()
         .a(false)
         .a(0.5F)
         .b(0.5F)
         .a(new BiomeFog.a().b(4159204).c(329011).a(10518688).d(0).a(CaveSoundSettings.b).a())
         .a(var1.a())
         .a(var0.a())
         .a();
   }

   public static BiomeBase a(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsGeneration.a var2 = new BiomeSettingsGeneration.a(var0, var1);
      return a(var2);
   }

   public static BiomeBase b(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsGeneration.a var2 = new BiomeSettingsGeneration.a(var0, var1).a(WorldGenStage.Decoration.e, EndPlacements.a);
      return a(var2);
   }

   public static BiomeBase c(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsGeneration.a var2 = new BiomeSettingsGeneration.a(var0, var1);
      return a(var2);
   }

   public static BiomeBase d(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsGeneration.a var2 = new BiomeSettingsGeneration.a(var0, var1)
         .a(WorldGenStage.Decoration.e, EndPlacements.b)
         .a(WorldGenStage.Decoration.j, EndPlacements.c);
      return a(var2);
   }

   public static BiomeBase e(HolderGetter<PlacedFeature> var0, HolderGetter<WorldGenCarverWrapper<?>> var1) {
      BiomeSettingsGeneration.a var2 = new BiomeSettingsGeneration.a(var0, var1).a(WorldGenStage.Decoration.a, EndPlacements.d);
      return a(var2);
   }
}
